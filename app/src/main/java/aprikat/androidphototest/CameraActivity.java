package aprikat.androidphototest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraActivity extends AppCompatActivity implements Camera.PictureCallback {

    private Camera mCamera;
    private CameraPreview mCameraPreview;

    protected static final String EXTRA_IMAGE_PATH = "aprikat.androidphototest.EXTRA_IMAGE_PATH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        setResult(RESULT_CANCELED);
        // Camera may be in use by another activity or the system or not available at all
        mCamera = getFrontFacingCamera();
        // if(cameraAvailable(mCamera)){
            initCameraPreview();
//        } else {
//            finish();
//        }
    }

    // Show the camera view on the activity
    private void initCameraPreview() {
        mCameraPreview = (CameraPreview) findViewById(R.id.camera_preview);
        mCameraPreview.init(mCamera);
    }

    public void onCaptureClick(View button){
        // Take a picture with a callback when the photo has been created
        // Here you can add callbacks if you want to give feedback when the picture is being taken
        mCamera.takePicture(null, null, this);
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        Log.d("AndroidPhotoTest", "Picture taken");
//        String path = savePictureToFileSystem(data);
//        setResult(path);
        savePictureToFileSystem(data);
        finish();
    }

    private void savePictureToFileSystem(byte[] data) {
        Log.d("AndroidPhotoTest", "saving picture to file system");
//        File file = getOutputMediaFile();
//        saveToFile(data, file);
//        return file.getAbsolutePath();

//        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//        if (bitmap!=null){
//
//            File file=new File(Environment.getExternalStorageDirectory()+"/dirr");
//            if(!file.isDirectory()){
//                file.mkdir();
//            }
//
//            file=new File(Environment.getExternalStorageDirectory()+"/dirr",System.currentTimeMillis()+".jpg");
//
//            try {
//                FileOutputStream fileOutputStream =new FileOutputStream(file);
//                bitmap.compress(Bitmap.CompressFormat.JPEG,100, fileOutputStream);
//                fileOutputStream.flush();
//                fileOutputStream.close();
//            }
//            catch(IOException e) {
//                e.printStackTrace();
//            }
//            catch(Exception exception) {
//                exception.printStackTrace();
//            }
//        }
    }

    private void setResult(String path) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_IMAGE_PATH, path);
        setResult(RESULT_OK, intent);
    }

    // ALWAYS remember to release the camera when you are finished
    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    private void releaseCamera() {
        if(mCamera != null){
            mCamera.release();
            mCamera = null;
        }
    }

    private Camera getFrontFacingCamera() {
        int cameraCount = 0;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        Log.e("AndroidPhotoTest", "cameraCount = " + Integer.toString(cameraCount));
        for (int camIdx = 0; camIdx<cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                Log.e("AndroidPhotoTest", "found front facing camera");
                try {
                    cam = Camera.open(camIdx);
                    cam.setDisplayOrientation(90);
                } catch (RuntimeException e) {
                    Log.e("AndroidPhotoTest", "Camera failed to open: " + e.getLocalizedMessage());
                }
            }
        }
        return cam;
    }
}
