package in.base.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import in.discountmart.R;


public class BaseProgressDialog  extends DialogFragment {
    View rootView;
    private static BaseProgressDialog progressDialog = null;

    public static String TAG = BaseProgressDialog.class.getSimpleName();

    public BaseProgressDialog() {
        // Exists only to defeat instantiation.
    }

    public static synchronized BaseProgressDialog getInstance() {
        if (progressDialog == null) {
            progressDialog = new BaseProgressDialog();
        }
        return progressDialog;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_loader, container, false);

        //Dialog Properties
        Dialog d = getDialog();
        assert d != null;
        d.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //d.getWindow().setTitle("Please wait..");
        //d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
        d.setCancelable(false);
        d.setCanceledOnTouchOutside(false);

        d.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    // do nothing, consume back press
                    dismiss();
                    return true;
                }
                return false;
            }
        });

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }

}
