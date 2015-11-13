package aprikat.androidphototest;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by aprilyluo on 11/5/15.
 */
public class PhotoOptionsFragment extends DialogFragment {

    String[] photoOptions = {"Take a photo", "Choose from gallery"};

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // builder.setTitle("Upload a photo");
        builder.setItems(photoOptions, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        mListener.onTakePhotoClick(PhotoOptionsFragment.this);
                        break;
                    case 1:
                        mListener.onUploadPhotoClick(PhotoOptionsFragment.this);
                        break;
                    default:
                        break;
                }
            }
        });
        return builder.create();
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface PhotoOptionsDialogListener {
        public void onTakePhotoClick(DialogFragment dialog);
        public void onUploadPhotoClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    PhotoOptionsDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (PhotoOptionsDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement PhotoOptionsDialogListener");
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        getDialog().getWindow().setLayout(700, 440);
    }

}
