package com.pacinho.doctorapp.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pacinho.doctorapp.GlobalParameters;
import com.pacinho.doctorapp.POJO.Examination;
import com.pacinho.doctorapp.R;
import com.pacinho.doctorapp.WS.WSConnector;

import java.io.ByteArrayOutputStream;

/**
 * Created by patryk on 2018-11-20.
 */

public class ExaminationActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int RESULT_LOAD_IMG = 1;
    String photo="";
    ImageView imageView;

    private EditText nameET;
    private EditText descriptionET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examination_layout);

        imageView = (ImageView) findViewById(R.id.imageView);


        TextView examTV = (TextView) findViewById(R.id.examTV);
        TextView addPhotoTv = (TextView) findViewById(R.id.addPhotoTV);

        Button cameraBtn = (Button) findViewById(R.id.cameraBtn);
        Button galeryBtn = (Button) findViewById(R.id.galeryBtn);
        Button addBtn = (Button) findViewById(R.id.addPhotoBtn);

        nameET = (EditText)  findViewById(R.id.nameET);
        descriptionET = (EditText)  findViewById(R.id.descriptionET);


        Examination exam = GlobalParameters.selectedExamination;
        examTV.setText(exam.getName() + " | " + exam.getCustomName());

        galeryBtn.setOnClickListener(v -> getImageFromAlbum());
        cameraBtn.setOnClickListener(v -> getImageFromCamera());
        addBtn.setOnClickListener(v -> addPhoto());

    }

    private void addPhoto(){

        if(photo.isEmpty() || nameET.getText().toString().isEmpty() || descriptionET.getText().toString().isEmpty()){
            AlertDialog alertDialog = new AlertDialog.Builder(ExaminationActivity.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Picture, Name and descripton is required ! Please complete this filed !");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return;
        }

        if(photo.length()>131000){
            AlertDialog alertDialog = new AlertDialog.Builder(ExaminationActivity.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("To big Picture ! Please resize or load other !");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return;
        }

        WSConnector.addPhoto(nameET.getText().toString(),photo,descriptionET.getText().toString());
        AlertDialog alertDialog = new AlertDialog.Builder(ExaminationActivity.this).create();
        alertDialog.setTitle("Add Photo");
        alertDialog.setMessage("Add Photo Finish");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

        photo="";
        imageView.setImageBitmap(null);
        nameET.setText("");
        descriptionET.setText("");

    }

    private void getImageFromCamera() {
        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_PERMISSION_CODE);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    private void getImageFromAlbum() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    private String encodeImage(Bitmap bitmap) {

        String encodedString = null;

        BitmapFactory.Options options = null;
        options = new BitmapFactory.Options();
        options.inSampleSize = 3;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
        } catch (Exception e1) {
        }

        byte[] byte_arr = stream.toByteArray();

        try {
            encodedString = Base64.encodeToString(byte_arr, 0);
        } catch (Exception e) {
        }

        return encodedString;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            Bitmap bitmap = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

            } catch (Exception e) {

                AlertDialog alertDialog = new AlertDialog.Builder(ExaminationActivity.this.getApplicationContext()).create();
                alertDialog.setTitle("Select Image");
                alertDialog.setMessage("Error ! Select other image !");

                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertDialog.show();
            }


            photo = encodeImage(bitmap);

            try {

                //imageView = (ImageView) myView.findViewById(R.id.imageView2);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);

            } catch (Exception e) {

                AlertDialog alertDialog = new AlertDialog.Builder(ExaminationActivity.this.getApplicationContext()).create();
                alertDialog.setTitle("Select Image");
                alertDialog.setMessage("Error ! Select other image !");

                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        }

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photoCamera = (Bitmap) data.getExtras().get("data");
            photo = encodeImage(photoCamera);
            imageView.setImageBitmap(photoCamera);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }

}