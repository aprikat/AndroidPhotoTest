package aprikat.androidphototest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity implements PhotoOptionsFragment.PhotoOptionsDialogListener{

    private ImageView mImageView;
    private FloatingActionButton photoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.imageViewPhotoTaken);

        photoButton = (FloatingActionButton) findViewById(R.id.fab);
        // photoButton.setRippleColor(R.color.mauve);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("hi");
                photoButton.setElevation(12);
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
        DialogFragment dialogFragment = new PhotoOptionsFragment();
        dialogFragment.show(getSupportFragmentManager(), "PhotoOptionsFragment");
        // dialogFragment.getDialog().getWindow().setLayout(600, 400);
    }

    @Override
    public void onTakePhotoClick(DialogFragment dialog) {
        System.out.println("called onTakePhotoClick");
        dispatchTakePictureIntent();
    }

    @Override
    public void onUploadPhotoClick(DialogFragment dialog) {
        System.out.println("called onUploadPhotoClick");
        dispatchPhotoGalleryIntent();
    }

    /**
     * CAMERA
     */

    static final int REQUEST_IMAGE_CAPTURE = 0;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * PHOTO GALLERY
     */

    static final int REQUEST_PHOTO_GALLERY = 1;

    private void dispatchPhotoGalleryIntent() {
        Intent photoGalleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(photoGalleryIntent, REQUEST_PHOTO_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            System.out.println("annie are you ok");
            return;
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
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
        else {
            System.out.println("unknown request code");
            return;
        }
    }
}
