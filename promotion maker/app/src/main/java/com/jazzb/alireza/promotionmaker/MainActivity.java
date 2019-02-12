package com.jazzb.alireza.promotionmaker;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fenchtose.nocropper.CropperView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import static android.support.v4.view.ViewCompat.setBackgroundTintList;

public class MainActivity extends AppCompatActivity {


    private static final int MY_PERMISSION_REQUEST = 1;
    private static final int RESULT_LOAD_IMAGE = 2;
    private static final int REQUEST_IMAGE_CAMERA = 100;

    final Context context = this;

    Button btnTakePicture, btnSavePromotion, btnShare, btnReset, btnCrop, btnSnap, btnRotate;
    TextView textView2;
    EditText editText1, editText2, editText3 ;
   // ImageView imageView;
    String currentImage = "";

    CropperView cropperView;
    Bitmap mbitMap, mainBitmap;
    boolean isSnappedToCenter = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        // get permission for write on external storage
        permissionGranted();

        mainBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.iu);
        cropperView.setImageBitmap(mainBitmap);

      //  buttonDefine();

    }

    @Override
    protected void onResume() {
        super.onResume();

        buttonDefine();

    }

    private void buttonDefine() {

        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogFunction();

                btnSavePromotion.setEnabled(true);
                btnSavePromotion.setBackgroundTintList(getResources().getColorStateList(R.color.colorEnableSavePromo));

                btnTakePicture.setEnabled(false);
                btnTakePicture.setBackgroundTintList(getResources().getColorStateList(R.color.colorDisable));

                btnReset.setEnabled(true);
                btnReset.setBackgroundTintList(getResources().getColorStateList(R.color.colorEnableReset));

                btnSnap.setEnabled(true);
                btnSnap.setBackgroundTintList(getResources().getColorStateList(R.color.colorEnableSnap));

                btnCrop.setEnabled(true);
                btnCrop.setBackgroundTintList(getResources().getColorStateList(R.color.colorEnableCrop));

            }
        });

        btnSavePromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View content = findViewById(R.id.promo_layout);
                Bitmap bitmap=getScreenShot(content);
                currentImage = "PROMO" + System.currentTimeMillis() + ".png";
                store(bitmap, currentImage);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cropperView.setImageBitmap(mainBitmap);

//                editText1.setText("");
//                editText2.setText("");
//                editText3.setText("");

                btnReset.setEnabled(false);
                btnReset.setBackgroundTintList(getResources().getColorStateList(R.color.colorDisable));

                btnTakePicture.setEnabled(true);
                btnTakePicture.setBackgroundTintList(getResources().getColorStateList(R.color.colorEnableTakePic));
            }
        });

        btnCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropImage();

                btnRotate.setEnabled(true);
                btnRotate.setBackgroundTintList(getResources().getColorStateList(R.color.colorEnableRotate));
            }
        });

//        btnToggle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                boolean enabled = cropperView.isGestureEnabled();
//                enabled = !enabled;
//                cropperView.setGestureEnabled(enabled);
//                Toast.makeText(getBaseContext(), "Gesture : "+(enabled?"Enabled":"Disabled"), Toast.LENGTH_SHORT).show();
//            }
//        });

        btnSnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSnappedToCenter)
                    cropperView.cropToCenter();
                else
                    cropperView.fitToCenter();
                isSnappedToCenter = !isSnappedToCenter;

                btnReset.setEnabled(true);
                btnReset.setBackgroundTintList(getResources().getColorStateList(R.color.colorEnableReset));
            }
        });

        btnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                cropperView.setImageBitmap(rotateBitmap(mbitMap,90));
                cropperView.setRotation(cropperView.getRotation() + 90);

            }
        });
    }

    private void permissionGranted() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }
        } else {
            // do nothing
        }
    }

    public static Bitmap getScreenShot(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public void store (Bitmap bm, String fileName) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PROMO";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dirPath, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 10, fos);
            fos.flush();
            fos.close();
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "Error saving!", Toast.LENGTH_SHORT).show();
        }
    }
    private void sharedImage (String fileName) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/PROMO";
        Uri uri = Uri.fromFile(new File(dirPath, fileName));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        
        try {
            startActivity(Intent.createChooser(intent, "share via"));
        } catch (ActivityNotFoundException e){
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//       // super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//            cropperView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//
//
//
//
//        }
//    }

    Uri imageUri;

    String imageurl;



    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){

//

//                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                    thumbnail.compress(Bitmap.CompressFormat.PNG, 90, bytes);
//                    File destination = new File(Environment.getExternalStorageDirectory(),
//                            System.currentTimeMillis() + ".jpg");
//                    FileOutputStream fo;
//                    try {
//                        destination.createNewFile();
//                        fo = new FileOutputStream(destination);
//                        fo.write(bytes.toByteArray());
//                        fo.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    cropperView.setImageBitmap(thumbnail);
//
//                      cropperView.setImageBitmap(BitmapFactory.decodeFile(String.valueOf(destination)));

                    Bitmap thumbnail;
                    try {
                        thumbnail = MediaStore.Images.Media.getBitmap(
                                getContentResolver(), imageUri);
                        cropperView.setImageBitmap(thumbnail);
                        imageurl = getRealPathFromURI(imageUri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
//                    Uri selectedImage = imageReturnedIntent.getData();
//                    imageview.setImageURI(selectedImage);
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    cropperView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    //save.setEnabled(true);
                    //share.setEnabled(false);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        // do nothing
                    }
                } else {
                    Toast.makeText(this, "NO Permission granted!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }

    private  void  initView(){

        // button for crop the picture
        btnCrop = findViewById(R.id.btnCrop);
        btnCrop.setEnabled(false);
        btnCrop.setBackgroundTintList(getResources().getColorStateList(R.color.colorDisable));
        // button for choose picture gallery or camera
        btnTakePicture = findViewById(R.id.btnToggle);
        btnTakePicture.setEnabled(true);
        btnTakePicture.setBackgroundTintList(getResources().getColorStateList(R.color.colorEnableTakePic));
        // button for snap the picture between crop or fit center
        btnSnap = findViewById(R.id.btnSnap);
        btnSnap.setEnabled(false);
        btnSnap.setBackgroundTintList(getResources().getColorStateList(R.color.colorDisable));
        // button for rotate the picture
        btnRotate = findViewById(R.id.btnRotate);
        btnRotate.setEnabled(false);
        btnRotate.setBackgroundTintList(getResources().getColorStateList(R.color.colorDisable));
        // button for reset
        btnReset = findViewById(R.id.button1);
        btnReset.setEnabled(false);
        btnReset.setBackgroundTintList(getResources().getColorStateList(R.color.colorDisable));
        // button save promotion
        btnSavePromotion = findViewById(R.id.button3);
        btnSavePromotion.setEnabled(false);
        btnSavePromotion.setBackgroundTintList(getResources().getColorStateList(R.color.colorDisable));
        // button for share
//        btnShare = findViewById(R.id.button2);
//        btnShare.setEnabled(false);
//        btnShare.setBackgroundTintList(getResources().getColorStateList(R.color.colorDisable));




        // image view
        cropperView = findViewById(R.id.imageView);

        mainBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.iu);
        cropperView.setImageBitmap(mainBitmap);
        // edit text
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);

    }

    private Bitmap rotateBitmap(Bitmap mbitMap, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(mbitMap,0,0,mbitMap.getWidth(),mbitMap.getHeight(),matrix,true);
    }

    private void cropImage() {
        mbitMap = cropperView.getCroppedBitmap();
        if (mbitMap != null) {
            cropperView.setImageBitmap(mbitMap);
        }
    }

    /**
     * Method for making dialog *
     */

    public void dialogFunction() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.take_photo);
        dialog.setTitle("Take Photo");

        Button btnGallery = dialog.findViewById(R.id.btnGallery);
        Button btnCamera = dialog.findViewById(R.id.btnCamera);

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               // startActivityForResult(pickPhoto, 2);
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
                dialog.dismiss();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    Intent pickPhoto = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(pickPhoto, 0);
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                startActivityForResult(takePicture, 0);//zero can be replaced with any action code
                dialog.dismiss();

            }
        });

        //AlertDialog dialog = mBuilder.create();
        dialog.show();

    }

    private  void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(pictureIntent, REQUEST_IMAGE_CAMERA);

        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


}
