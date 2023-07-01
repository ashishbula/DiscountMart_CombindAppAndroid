package in.discountmart.business.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import in.base.network.NetworkClient;
import in.base.ui.BaseFragment;
import com.google.android.material.snackbar.Snackbar;
import in.discountmart.R;
import in.discountmart.business.activity.BusinessDashboardActivity;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.EpinServices;
import in.discountmart.business.model_business.requestmodel.TopupRequest;
import in.discountmart.business.model_business.responsemodel.BaseResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmTopupFragment extends BaseFragment {

    EditText edTxtName;
    EditText edTxtIdNo;
    EditText edTxtTopupby;

    Button btnSubmitt;
    Button btnCancel;

    String strID="";
    String strName="";
    String strTopupBy="";
    String strPinNo="";

    Context context;
    View view1;

    ProgressDialog pDialog;
    public ConfirmTopupFragment(){
        // Empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.business_confirm_toup_fragment, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        try {
            context=getActivity();
            view1=getActivity().findViewById(android.R.id.content);
            edTxtIdNo=(EditText)v.findViewById(R.id.confirm_topup_fragment_editxt_Idno);
            edTxtName=(EditText)v.findViewById(R.id.confirm_topup_fragment_editxt_name);
            edTxtTopupby=(EditText)v.findViewById(R.id.confirm_topup_fragment_editxt_topupby);
            btnSubmitt=(Button)v.findViewById(R.id.confirm_topup_fragment_btn_topup);
            btnCancel=(Button)v.findViewById(R.id.confirm_topup_fragment_btn_cancel);

            Bundle bundle=getArguments();

            if(bundle != null){

                strID=bundle.getString("ID");
                strPinNo=bundle.getString("PIN");
                strName=bundle.getString("Name");
                strTopupBy=bundle.getString("Package");

                edTxtIdNo.setText(strID);
                edTxtName.setText(strName);
                edTxtTopupby.setText(strTopupBy);
            }
            else {
                Toast.makeText(context,"Bundle Null", Toast.LENGTH_SHORT).show();
            }

            btnSubmitt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(edTxtIdNo.getText().toString().equals("")){
                        Toast.makeText(context,"Plz Enter Member Id No", Toast.LENGTH_SHORT).show();
                        edTxtIdNo.requestFocus();
                    }
                    else if(edTxtName.getText().toString().equals("")){
                        Toast.makeText(context,"Plz Enter Member Name", Toast.LENGTH_SHORT).show();
                        edTxtName.requestFocus();
                    }
                    else if(edTxtTopupby.getText().toString().equals("")){
                        Toast.makeText(context,"Plz Enter Member Name", Toast.LENGTH_SHORT).show();
                        edTxtName.requestFocus();
                    }
                    else {

                        /*Call Topup  APi*/
                        if(!ConnectivityUtils.isNetworkEnabled(getActivity())){
                            Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //new getTopupResponse().execute();
                            getTopupDetail();
                        }

                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        return v;
    }

    /*Call Topup Api and get Response*/
    private void getTopupDetail(){

        pDialog=new ProgressDialog(context);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        TopupRequest Request = new TopupRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_TOPUP_ID);
        Request.setPasswd(SharedPrefrence_Business.getPassword(context));
        Request.setUserid(SharedPrefrence_Business.getUserID(context));
        Request.setPinno(strPinNo);
        Request.setMemberid(strID);
        Log.e("TopupRequest:-", Request.toString());

        Call<BaseResponse> callTopupResponse=
                NetworkClient.getInstance(context).create(EpinServices.class).fetchTopupDetail(Request);

        callTopupResponse.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

               if(pDialog.isShowing())
                   pDialog.dismiss();
                try {
                    BaseResponse Response=new BaseResponse();
                   Response=response.body();
                   if(Response != null){
                       if (Response.getResponse().equals("OK")) {

                           //String toast= Response.getResponse()+ ":" + Response.getMsg();
                           //Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                           SuccessMsgFragment fragment=new SuccessMsgFragment();
                           Bundle bundle=new Bundle();
                           bundle.putString("Msg",Response.getMsg());
                           fragment.setArguments(bundle);
                           ((BusinessDashboardActivity)context).replaceFragment_business(fragment,"SuccessMsgFragment",bundle);

                       }
                       else {
                           String toast= Response.getResponse()+ ":" + Response.getMsg();
                           Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();

                       }
                   }
                   else {
                       String toast= Response.getResponse()+  "Something went wrong..";
                       Snackbar.make(view1, toast, Snackbar.LENGTH_LONG)
                               .setAction("CLOSE", new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {

                                   }
                               })
                               .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                               .show();
                   }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

                if(pDialog.isShowing())
                    pDialog.dismiss();
                Snackbar.make(view1, t.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                        .show();
            }
        });

    }
}
