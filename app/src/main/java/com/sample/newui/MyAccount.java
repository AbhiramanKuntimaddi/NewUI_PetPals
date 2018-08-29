package com.sample.newui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyAccount extends AppCompatActivity {

    private float x1,x2,y1,y2;
    private float RESET_VALUES = 0;
    private static final int GALLERY = 1;
    private static final int CAMERA = 2;
    //test folder ref
    private static final String IMAGE_DIRECTORY = "/PetPals/Profile_Pictures";

    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        //imageview reference
        imageView = findViewById(R.id.imageView2);

        //onClick for FAB
        /**This onClick points to the FaceRecognition Function which would eventually searches for face
         * in the image taken from either camera or gallery.
         * */
        FloatingActionButton floatingActionButton_face = findViewById(R.id.floatingActionButton);
        floatingActionButton_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicDiag();
            }
        });

        FaceDetector faceDetector = new FaceDetector.Builder(getApplicationContext())
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.FAST_MODE)
                .build();

        Toast.makeText(this,"Face_"+faceDetector.isOperational(),Toast.LENGTH_LONG).show();
    }

    private void showPicDiag(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select Photo From Gallery",
                "Take A Picture From Camera"
        };
        pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        choosePhotoFromGallery();
                        break;
                    case 1:
                        takePhotoFromCamera();
                        break;
                }
            }
        });
        pictureDialog.show();
    }

    private void takePhotoFromCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAMERA);
    }

    private void choosePhotoFromGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == this.RESULT_CANCELED){
            return;
        }
        if(requestCode == GALLERY){
            if(data != null){
                Uri contextURI = data.getData();
                try{
                  final Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),contextURI);
                   Face_Recognition(bitmap);
                } catch (IOException e){
                    e.printStackTrace();
                    Toast.makeText(MyAccount.this,"Failed!",Toast.LENGTH_LONG).show();
                }
            }
        } else if(requestCode == CAMERA){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                Face_Recognition(bitmap);
        }
    }

    private void Face_Recognition(final Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);

        FaceDetector faceDetector = new FaceDetector.Builder(getApplicationContext())
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.FAST_MODE)
                .build();

        if(!faceDetector.isOperational())
        {
            Toast.makeText(this, "Face Detector could not be setup on your device",Toast.LENGTH_LONG).show();
            return;
        }

        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<Face> sparseArray = faceDetector.detect(frame);
        x1 = x2 = y1 = y2 = RESET_VALUES;
        for (int i=0;i<sparseArray.size();i++){
            Face face = sparseArray.valueAt(i);
            x1 = face.getPosition().x;
            y1 =face.getPosition().y;
            x2 = x1 + face.getWidth();
            y2 = x2 + face.getHeight();
        }

        if(x2 == 0 && y2 == 0){
            Toast.makeText(MyAccount.this, "No Face Detected",Toast.LENGTH_LONG).show();
            
            AlertDialog.Builder nofaceDiag = new AlertDialog.Builder(this);
            nofaceDiag.setTitle("No Face Detected, Are you sure you want to upload your profile picture?");

            nofaceDiag.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    saveImage(bitmap);
                    Toast.makeText(MyAccount.this,"yes" , Toast.LENGTH_SHORT).show();
                } });
            nofaceDiag.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                } });
            nofaceDiag.show();

        }else{
            Toast.makeText(MyAccount.this, "Face Detected",Toast.LENGTH_LONG).show();
            //upload the image to firebase database
            /**should add SaveImage function for testing the function and later should add the code
             * for uploading the image to the firebase databbase.
             * */
            //Saves image to the internal directory i.e, /PetPals/Profile_Pictures
            saveImage(bitmap);
        }

    }


    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() +"_PetPals_Profile_pic_temp"+".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
}
