package boats.boat.speed;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
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
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 1;
    private CustomView mCustomView;
    private String selectedImagePath;
    private ImageView img;
    Context context = this;

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
                        findViewById(R.id.setButton).setVisibility(View.VISIBLE);
                        mCustomView = findViewById(R.id.customViewDad);
                        mCustomView.setVisibility(View.VISIBLE);
                        mCustomView.setBackgroundColor(Color.TRANSPARENT);
                        startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
                        findViewById(R.id.catchButton).setVisibility(View.VISIBLE);
                        findViewById(R.id.finishButton).setVisibility(View.VISIBLE);
                        findViewById(R.id.dotCreate).setVisibility(View.VISIBLE);
                    }
                });

        findViewById(R.id.dotCreate).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomView.setCreate(true);
                mCustomView.drawCirclePlease();
            }
        });

        Button setButton = findViewById(R.id.setButton);
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomView.setSecure(true);
            }
        });

        findViewById(R.id.catchButton).setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                promptUser("kneePrompt");
            }
        });
    }


    public void promptUser(String promptToShow) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptsView = layoutInflater.inflate(R.layout.prompts, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(promptsView);
        if (promptToShow.equals("kneePrompt")) {
            promptsView.findViewById(R.id.kneePrompt).setVisibility(View.VISIBLE);
        } else if (promptToShow.equals("hipPrompt")) {
            promptsView.findViewById(R.id.hipPrompt).setVisibility(View.VISIBLE);

        } else if (promptToShow.equals("shoulerPrompt")) {
            promptsView.findViewById(R.id.shoulerPrompt).setVisibility(View.VISIBLE);

        }
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
