package aprikat.androidphototest;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements PhotoOptionsFragment.PhotoOptionsDialogListener{

    private FloatingActionButton photoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photoButton = (FloatingActionButton) findViewById(R.id.fab);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("hi");
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
        System.out.println("called onTakePhotoClick");
    }

    @Override
    public void onUploadPhotoClick(DialogFragment dialog) {
        System.out.println("called onUploadPhotoClick");
    }
}
