package boats.boat.speed;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 1;
    private CustomView mCustomView;
    private Analysis analysis;
    private String selectedImagePath;
    private ImageView img;
    Context context = this;
    public float[][] indexes;
    private String catchOrFinish;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //sets image to uploaded image
        img = findViewById(R.id.imageView);
        findViewById(R.id.uploadImage).setVisibility(View.VISIBLE);
        findViewById(R.id.uploadImage).setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        findViewById(R.id.uploadImage).setVisibility(View.INVISIBLE);
                        mCustomView = findViewById(R.id.customViewDad);
                        mCustomView.setVisibility(View.VISIBLE);
                        mCustomView.setBackgroundColor(Color.TRANSPARENT);
                        startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
                        findViewById(R.id.catchButton).setVisibility(View.VISIBLE);
                        findViewById(R.id.finishButton).setVisibility(View.VISIBLE);
                        findViewById(R.id.manipulatePhoto).setVisibility(View.VISIBLE);
                    }
                });
        findViewById(R.id.manipulatePhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptUser("edit");
                findViewById(R.id.manipulatePhoto).setVisibility(View.INVISIBLE);
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
                findViewById(R.id.setButtonYellow).setVisibility(View.INVISIBLE);
                findViewById(R.id.drawLines).setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.catchButton).setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                promptUser("regular");
                findViewById(R.id.setButtonPurple).setVisibility(View.VISIBLE);
                findViewById(R.id.setButtonGreen).setVisibility(View.VISIBLE);
                findViewById(R.id.setButtonRed).setVisibility(View.VISIBLE);
                findViewById(R.id.setButtonYellow).setVisibility(View.VISIBLE);
                findViewById(R.id.catchEx).setVisibility(View.VISIBLE);
                findViewById(R.id.catchButton).setVisibility(View.INVISIBLE);
                findViewById(R.id.finishButton).setVisibility(View.INVISIBLE);
                findViewById(R.id.manipulatePhoto).setVisibility(View.INVISIBLE);
                catchOrFinish = "catch";
                mCustomView.setCreate(true);
            }
        });

        findViewById(R.id.finishButton).setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                promptUser("regular");
                findViewById(R.id.setButtonPurple).setVisibility(View.VISIBLE);
                findViewById(R.id.setButtonGreen).setVisibility(View.VISIBLE);
                findViewById(R.id.setButtonRed).setVisibility(View.VISIBLE);
                findViewById(R.id.setButtonYellow).setVisibility(View.VISIBLE);
                findViewById(R.id.finishEx).setVisibility(View.VISIBLE);
                findViewById(R.id.finishButton).setVisibility(View.INVISIBLE);
                findViewById(R.id.catchButton).setVisibility(View.INVISIBLE);
                findViewById(R.id.manipulatePhoto).setVisibility(View.INVISIBLE);
                catchOrFinish = "finish";
                mCustomView.setCreate(true);
            }
        });
        findViewById(R.id.drawLines).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomView.drawLines();
                findViewById(R.id.drawLines).setVisibility(View.INVISIBLE);
                findViewById(R.id.analysisStart).setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.analysisStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexes = getDotIndexes();
                analysis = new Analysis(indexes);
                if (catchOrFinish.equals("catch")) {
                    if (analysis.shinsVertical()) {
                        promptUser("shin");
                    } else {
                        promptUser("antishin");
                    }
                    if (analysis.bodyAngle() <= -1) {
                        promptUser("goodForWardBodyAngle");
                    } else {
                        promptUser("notEnoughForwardBodyAngle");
                    }
                } else {
                    if (analysis.bodyAngle() == 1) {
                        promptUser("layback");
                    } else if (analysis.bodyAngle() <= -1) {
                        promptUser("notEnough");
                    } else if (analysis.bodyAngle() >= 1){
                        promptUser("tooMuch");
                    }
                }
            }
        });
    }

    public float[][] getDotIndexes() {
        indexes = mCustomView.getIndexes();
        return mCustomView.getIndexes();
    }

    public void promptUser(String prompt) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptsView = layoutInflater.inflate(R.layout.prompts, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (prompt.equals("regular")) {
            promptsView.findViewById(R.id.circlePrompt).setVisibility(View.VISIBLE);
        }
        if (prompt.equals("shin")) {
            promptsView.findViewById(R.id.circlePrompt).setVisibility(View.INVISIBLE);
            promptsView.findViewById(R.id.shinPrompt).setVisibility(View.VISIBLE);
        }
        if (prompt.equals("antishin")) {
            promptsView.findViewById(R.id.circlePrompt).setVisibility(View.INVISIBLE);
            promptsView.findViewById(R.id.antiShinPrompt).setVisibility(View.VISIBLE);
        }
        if (prompt.equals("layback")) {
            promptsView.findViewById(R.id.circlePrompt).setVisibility(View.INVISIBLE);
            promptsView.findViewById(R.id.laybackPrompt).setVisibility(View.VISIBLE);
        }
        if (prompt.equals("notEnough")) {
            promptsView.findViewById(R.id.circlePrompt).setVisibility(View.INVISIBLE);
            promptsView.findViewById(R.id.antiLaybackPrompt).setVisibility(View.VISIBLE);
        }
        if (prompt.equals("tooMuch")) {
            promptsView.findViewById(R.id.circlePrompt).setVisibility(View.INVISIBLE);
            promptsView.findViewById(R.id.contraLaybackPrompt).setVisibility(View.VISIBLE);
        }
        if (prompt.equals("goodForWardBodyAngle")) {
            promptsView.findViewById(R.id.circlePrompt).setVisibility(View.INVISIBLE);
            promptsView.findViewById(R.id.compressionPrompt).setVisibility(View.VISIBLE);
        }
        if (prompt.equals("notEnoughForwardBodyAngle")) {
            promptsView.findViewById(R.id.circlePrompt).setVisibility(View.INVISIBLE);
            promptsView.findViewById(R.id.anticompressionPrompt).setVisibility(View.VISIBLE);
        }

        builder.setView(promptsView);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        if (prompt.equals("edit"))
            startEditMode();
    }

    private void startEditMode() {
        Intent intention = new Intent(this, EditImage.class);
        startActivity(intention);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                System.out.println("Image Path : " + selectedImagePath);
                img.setImageURI(selectedImageUri);
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
