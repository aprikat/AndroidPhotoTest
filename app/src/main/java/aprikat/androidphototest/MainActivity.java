package aprikat.androidphototest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity implements PhotoOptionsFragment.PhotoOptionsDialogListener{

    private ImageView mImageView;
    private FloatingActionButton mPhotoButton;

    private static final int REQUEST_SIMPLE_CAMERA = 0;
    private static final int REQUEST_PHOTO_GALLERY = 1;
    private static final int REQUEST_CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.imageViewPhoto);
        mPhotoButton = (FloatingActionButton) findViewById(R.id.fab);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoOptionsDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showPhotoOptionsDialog() {
        DialogFragment dialog = new PhotoOptionsFragment();
        dialog.show(getSupportFragmentManager(), "PhotoOptionsFragment");
    }

    @Override
    public void onTakePhotoClick(DialogFragment dialog) {
        // dispatchSimpleCameraIntent();
        dispatchCameraActivityIntent();
    }

    @Override
    public void onUploadPhotoClick(DialogFragment dialog) {
        dispatchPhotoGalleryIntent();
    }

    /****************
     * CAMERA
     ****************/

    private void dispatchSimpleCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_SIMPLE_CAMERA);
        }
    }

    private void dispatchCameraActivityIntent() {
        Intent cameraActivityIntent = new Intent(this, CameraActivity.class);
        startActivityForResult(cameraActivityIntent, REQUEST_CAMERA);
    }

    /****************
     * PHOTO GALLERY
     ****************/

    private void dispatchPhotoGalleryIntent() {
        Intent photoGalleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(photoGalleryIntent, REQUEST_PHOTO_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            Log.e("AndroidPhotoTest", "annie are you ok");
            return;
        }
        if (requestCode == REQUEST_SIMPLE_CAMERA) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
        else if (requestCode == REQUEST_PHOTO_GALLERY) {
            Uri targetUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                mImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == REQUEST_CAMERA) {
//            String imgPath = data.getStringExtra(CameraActivity.EXTRA_IMAGE_PATH);
//            Log.i("Got image path: "+ imgPath);
//            displayImage(imgPath);
        }
        else {
            Log.e("AndroidPhotoTest", "unknown request code");
            return;
        }
    }
}
