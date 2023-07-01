package in.discountmart.business.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import in.base.ui.BaseFragment;
import in.discountmart.R;
import in.discountmart.activity.LoginActivity;
import in.discountmart.business.activity.BusinessDashboardActivity;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;

public class SuccessMsgFragment extends BaseFragment {

    Context context;
    TextView textViewMsg;
    Button btnHome;

    String stringMsg="";
    boolean doubleBackToExit = false;

    //Empty Constructor
    public SuccessMsgFragment(){
        //empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.business_success_msg_fragment, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        context = getActivity();

        textViewMsg=(TextView)v.findViewById(R.id.success_msg_frag_txtview_msg);
        btnHome=(Button)v.findViewById(R.id.success_msg_frag_btn_home);

        Bundle bundle=getArguments();

        if(bundle != null){
            stringMsg=bundle.getString("Msg");

            textViewMsg.setText(stringMsg);
        }
        else {
            Toast.makeText(context,"Bundle Empty", Toast.LENGTH_SHORT).show();
        }

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BusinessDashboardActivity)context).loadHomeFragment_Business();
            }
        });
        return v;
    }

    public void onResume(){
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

               /* if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                    if(SharedPrefrence.getUserID(getActivity()).equals("") ||
                            SharedPrefrence.getPassword(getActivity()).equals("")){
                        Intent intent=new Intent(getActivity(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(getActivity()).finish();
                        }
                    }
                    else {
                        Intent intent=new Intent(getActivity(), BusinessDashboardActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(getActivity()).finish();
                        }
                    }



                    //Toast.makeText(context,"Press Button Continue Shop Or View MyOrder. for continue",Toast.LENGTH_SHORT).show();
                    return true;
                }

                return false;*/

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    if (doubleBackToExit) {

                        if(SharedPrefrence_Business.getUserID(context).equals("")){
                            Intent intent=new Intent(context, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                Objects.requireNonNull(getActivity()).finish();
                            }
                        }
                        else {
                            Intent intent=new Intent(context, BusinessDashboardActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                Objects.requireNonNull(getActivity()).finish();
                            }
                        }
                    }
                    else {
                        doubleBackToExit = true;
                        Toast.makeText(context, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                    /*new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExit = false;
                        }
                    }, 2000);*/
                    }

                }
                return true;
            }
        });
    }
}
