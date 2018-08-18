package com.sample.newui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import java.io.IOException;

public class MyAccount extends AppCompatActivity {

    private float x1,x2,y1,y2;
    private float RESET_VALUES = 0;
    private static final int GALLERY = 1;
    private static final int CAMERA = 2;
    //test folder ref
    private static final String IMAGE_DIREECTORY = "/PetPals/Profile_Pictures";

    ImageView imageView;

    RectF rectF;
    Canvas canvas;
    //Test rectangle paint tool
    Paint rectPaint = new Paint();

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
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),contextURI);
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

    private void Face_Recognition(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);

        FaceDetector faceDetector = new FaceDetector.Builder(getApplicationContext())
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.FAST_MODE)
                .build();
        if(faceDetector.isOperational())
        {
            Toast.makeText(MyAccount.this, "Face Detector could not be setup on your device",Toast.LENGTH_LONG).show();
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
        }else{
            Toast.makeText(MyAccount.this, "Face Detected",Toast.LENGTH_LONG).show();
            //upload the image to firebase database
            /**should add SaveImage function for testing the function and later should add the code
             * for uploading the image to the firebase databbase.
             * */
        }

    }
}
