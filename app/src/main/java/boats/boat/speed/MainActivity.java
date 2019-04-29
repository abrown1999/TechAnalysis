package boats.boat.speed;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CustomView mCustomView;
    private Analysis analysis;
    private ImageView img;
    public float[][] indexes;
    private String catchOrFinish;

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 100);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        final TextView instruction = findViewById(R.id.instruction);
        //sets image to uploaded image
        img = findViewById(R.id.imageView);
        final ImageView takePictureButton = findViewById(R.id.camera);
        takePictureButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                openGallery();
                mCustomView = findViewById(R.id.customViewDad);
                mCustomView.setVisibility(View.VISIBLE);
                mCustomView.setBackgroundColor(Color.TRANSPARENT);
                findViewById(R.id.logo).setVisibility(View.INVISIBLE);
                findViewById(R.id.tech_bar).setVisibility(View.VISIBLE);
                instruction.setText("Decide which part of the stroke you would like to analyze");
            }
        });
        findViewById(R.id.catchButton).setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                instruction.setText("Drag and select the colors in the order they appear");
                findViewById(R.id.color_buttons).setVisibility(View.VISIBLE);
                catchOrFinish = "catch";
                findViewById(R.id.catchButton).setVisibility(View.INVISIBLE);
                findViewById(R.id.finishButton).setVisibility(View.INVISIBLE);
                findViewById(R.id.catchEx).setVisibility(View.VISIBLE);
                mCustomView.setCreate(true);
            }
        });
        findViewById(R.id.finishButton).setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                instruction.setText("Drag and select the colors in the order they appear");
                findViewById(R.id.color_buttons).setVisibility(View.VISIBLE);
                catchOrFinish = "finish";
                findViewById(R.id.finishButton).setVisibility(View.INVISIBLE);
                findViewById(R.id.catchButton).setVisibility(View.INVISIBLE);
                findViewById(R.id.catchEx).setVisibility(View.VISIBLE);
                mCustomView.setCreate(true);
            }
        });
        findViewById(R.id.setButtonPurple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomView.setSecureB(true);
                findViewById(R.id.setButtonPurple).setVisibility(View.INVISIBLE);
            }
        });
        findViewById(R.id.setButtonRed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomView.setSecureR(true);
                findViewById(R.id.setButtonRed).setVisibility(View.INVISIBLE);
            }
        });
        findViewById(R.id.setButtonGreen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomView.setSecureG(true);
                findViewById(R.id.setButtonGreen).setVisibility(View.INVISIBLE);
            }
        });
        findViewById(R.id.setButtonYellow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomView.setSecureY(true);
                findViewById(R.id.catchEx).setVisibility(View.INVISIBLE);
                findViewById(R.id.finishEx).setVisibility(View.INVISIBLE);
                findViewById(R.id.color_buttons).setVisibility(View.INVISIBLE);
                findViewById(R.id.end_analysis_bar).setVisibility(View.VISIBLE);
                findViewById(R.id.analysisStart).setVisibility(View.INVISIBLE);
                instruction.setText("Click 'Draw Lines' to connect the joints");
            }
        });
        findViewById(R.id.drawLines).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomView.drawLines();
                findViewById(R.id.drawLines).setVisibility(View.INVISIBLE);
                findViewById(R.id.analysisStart).setVisibility(View.VISIBLE);
                instruction.setText("Click 'Start Analysis' to get feedback on your stroke!");
            }
        });
        findViewById(R.id.analysisStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instruction.setText("");
                indexes = getDotIndexes();
                analysis = new Analysis(indexes);
                TextView feedback = findViewById(R.id.techAdvice);
                feedback.setVisibility(View.VISIBLE);
                TextView feedbackB = findViewById(R.id.techAdvice2);
                feedbackB.setVisibility(View.VISIBLE);
                if (catchOrFinish.equals("catch")) {
                    if (analysis.shinsVertical()) {
                        feedback.setText("Shins are vertical! Good compression");
                    } else {
                        feedback.setText("Shins are not vertical. More compression required at the catch");
                    }
                }
                if (analysis.bodyAngle() == 1) {
                    feedbackB.setText("Good layback");
                } else if (analysis.bodyAngle() < 1) {
                    feedbackB.setText("Not enough layback");
                } else if (analysis.bodyAngle() > 1) {
                    feedbackB.setText("Too much layback");
                }
                findViewById(R.id.analysisStart).setVisibility(View.INVISIBLE);
            }
        });
    }

    public float[][] getDotIndexes() {
        indexes = mCustomView.getIndexes();
        return mCustomView.getIndexes();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            Uri selectedImageUri = data.getData();
            System.out.println("Image Path : " + selectedImageUri);
            img.setImageURI(selectedImageUri);
        }
    }
}
