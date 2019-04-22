package boats.boat.speed;


import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.ImageView;
import boats.boat.speed.views.CustomView;

public class MainActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 1;
    private CustomView mCustomView;

    private String selectedImagePath;
    private ImageView img;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button setButton = (Button) findViewById(R.id.setButton);

        mCustomView = findViewById(R.id.customViewDad);
        mCustomView.setBackgroundColor(Color.TRANSPARENT);
        //sets image to uploaded image
        img = findViewById(R.id.imageView);
        findViewById(R.id.uploadImage).setVisibility(View.VISIBLE);
        findViewById(R.id.uploadImage).setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        findViewById(R.id.uploadImage).setVisibility(View.INVISIBLE);
                        startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
                    }
                });

        setButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                View promptsView = layoutInflater.inflate(R.layout.prompts, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setView(promptsView);
                findViewById(R.id.anklePrompt).setVisibility(View.VISIBLE);
                builder.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


            }
        });
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
