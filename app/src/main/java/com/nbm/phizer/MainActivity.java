package com.nbm.phizer;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private ImageSurfaceView previewView;
    private Camera camera;

    private FrameLayout cameraLayout;
    private ImageView capturedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        cameraLayout = (FrameLayout)findViewById(R.id.previewLayout);
        capturedImage = (ImageView)findViewById(R.id.imageView);

        camera = checkDeviceCamera();
        previewView = new ImageSurfaceView(MainActivity.this, camera);

        cameraLayout.addView(previewView);

    }

    public void captureImage(View v) {
        camera.takePicture(null, null, pictureCallback);
    }

    private Camera checkDeviceCamera() {
        Camera cam = null;
        try {
            cam = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();;
        }
        return cam;
    }

    PictureCallback pictureCallback = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            if (bitmap == null) {
                Toast.makeText(MainActivity.this, "Empty Image", Toast.LENGTH_LONG).show();
                return;
            }
            capturedImage.setImageBitmap(bitmap);
        }
    };
}
