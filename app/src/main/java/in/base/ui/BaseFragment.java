package in.base.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    private final String LOG_TAG = getClass().getSimpleName();

    public BaseActivity mActivity;
    private float density;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        density = mActivity.getResources().getDisplayMetrics().density;
    }

    public float getDensity() {
        return density;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseActivity) context;
    }

    public BaseActivity getMainActivity() {
        if (mActivity instanceof BaseActivity) {
            return (BaseActivity) mActivity;
        }
        return null;
    }

    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    public void showProgressDialog(){
        if(getMainActivity() != null){
            getMainActivity().showProgressDialog();
        }
    }

    public void hideProgressDialog(){
        if(getMainActivity() != null){
            getMainActivity().hideProgressDialog();
        }
    }

    public void showSnackbar(String message){
        if(getMainActivity() != null){
            getMainActivity().showSnackbar(message);
        }
    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                onActivityResultDelegate(requestCode, resultCode, data);
            }
        }, 500);
    }

    public void onActivityResultDelegate(int requestCode, int resultCode, Intent data) {
        // do nothing
    }


   /* public void finish() {
        mActivity.finish();
        overridePendingTransitionExit();
    }*/

    /*@Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }*/

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    /*protected void overridePendingTransitionEnter() {
        if(getMainActivity() != null){
            getMainActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
        }

    }*/

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
   /* protected void overridePendingTransitionExit() {
        if(getMainActivity() != null){
            getMainActivity().overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);
        }

    }*/

    /*protected void openActivityfromBottom() {

        if(getMainActivity() != null){
            getMainActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
        }

    }*/

   /* protected void openActivityFromTop() {
        if(getMainActivity() != null){
            getMainActivity(). overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        }

    }*/
}
