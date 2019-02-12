package com.jazzb.alireza.imagecropper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.fenchtose.nocropper.CropperView;

public class MainActivity extends AppCompatActivity {

    Button btnCrop, btnToggle;
    ImageView btnSnap, btnRotate;
    CropperView cropperView;
    Bitmap mbitMap;

    boolean isSnappedToCenter = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        Bitmap orginalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.pushupwide);
        cropperView.setImageBitmap(orginalBitmap);

        btnCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropImage();
            }
        });

        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean enabled = cropperView.isGestureEnabled();
                enabled = !enabled;
                cropperView.setGestureEnabled(enabled);
                Toast.makeText(getBaseContext(), "Gesture : "+(enabled?"Enabled":"Disabled"), Toast.LENGTH_SHORT).show();
            }
        });

        btnSnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSnappedToCenter)
                    cropperView.cropToCenter();
                else
                    cropperView.fitToCenter();
                isSnappedToCenter = !isSnappedToCenter;
            }
        });

        btnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropperView.setImageBitmap(rotateBitmap(mbitMap,90));
            }
        });

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

    private  void  initView(){
        btnCrop = findViewById(R.id.btnCrop);
        btnToggle = findViewById(R.id.btnToggle);
        btnSnap = findViewById(R.id.snap_button);
        btnRotate = findViewById(R.id.rotate_button);
        cropperView = findViewById(R.id.imageView);
    }
}
