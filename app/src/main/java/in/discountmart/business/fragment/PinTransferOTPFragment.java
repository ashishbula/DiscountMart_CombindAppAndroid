package in.discountmart.business.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import in.base.network.NetworkClient;
import in.base.ui.BaseFragment;
import in.discountmart.R;
import in.discountmart.business.activity.BusinessDashboardActivity;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.EpinServices;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.requestmodel.PinTransferWithOtpRequest;
import in.discountmart.business.model_business.responsemodel.BaseResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinTransferOTPFragment extends BaseFragment {

    //GoogleApiClient mGoogleApiClient;
    //SmsBroadcastReceiver mSmsBroadcastReceiver;
    private int RESOLVE_HINT = 2;

    EditText edTxtOtp;
    TextView txtNumber;
    TextView txtMsg;
    Button btnSubmit;
    Button btnResendOTP;
    ProgressDialog pDialog;
    View view;

    String strKitId="";
    String strKitName="";
    String strKitQty="";
    String strIdNo="";
    String strMemberName="";
    String strQuantity="";
    String strRemark="";
    String strMobile="";
    String strApiKey="";
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    Context context;

    public  PinTransferOTPFragment(){
        //empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        View rootView = inflater.inflate(R.layout.business_pin_transfer_opt_fragment, container, false);

        try {

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);


            Bundle bundle=getArguments();

            if(bundle != null){
                strIdNo=bundle.getString("toid");
                strKitId=bundle.getString("pckid");
                strQuantity=bundle.getString("qty");
                strRemark=bundle.getString("remark");
            }
            else {
                Toast.makeText(context,"Bundle value null", Toast.LENGTH_SHORT).show();
            }

            context = getActivity();
            view=getActivity().findViewById(android.R.id.content);
            edTxtOtp=(EditText)rootView.findViewById(R.id.pin_transfer_otp_frag_edtxt_otp);
            txtMsg=(TextView) rootView.findViewById(R.id.pin_transfer_otp_frag_txt_msg);
            txtNumber=(TextView)rootView.findViewById(R.id.pin_transfer_otp_frag_txt_num);
            btnSubmit=(Button)rootView.findViewById(R.id.pin_transfer_otp_frag_btn_submitt);
            btnResendOTP=(Button)rootView.findViewById(R.id.pin_transfer_otp_frag_btn_resend);

            strMobile= SharedPrefrence_Business.getUserMobileNumber(context);

            /*SEt Otp Mobile number*/
            if(! strMobile.equals("")){
                String mobile= strMobile;
                String mobNumber=mobile.substring(7,10);
                txtNumber.setText(getResources().getString(R.string.str_otp_send_to)+"*******"+mobNumber);
                //layoutOtpBtnResend.setVisibility(View.GONE);
                // layoutOtpBtnVerify.setVisibility(View.VISIBLE);
                txtMsg.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.VISIBLE);
            }
            else {
                String mobile= "0000000000";
                String mobNumber=mobile.substring(7,10);
                txtNumber.setText(getResources().getString(R.string.str_otp_send_to)+"*******"+mobNumber);

                txtMsg.setText("Can't process Your mobile no. is not available,");
                btnSubmit.setVisibility(View.GONE);
                //layoutOtpBtnResend.setVisibility(View.GONE);
                //layoutOtpBtnVerify.setVisibility(View.GONE);

            }

            /*Button submitt click listener*/
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(edTxtOtp.getText().toString().equals("")){
                        edTxtOtp.setError("Plz Enter Otp number");
                        edTxtOtp.requestFocus();
                    }
                    else {
                        if(!ConnectivityUtils.isNetworkEnabled(context)){
                            Toast.makeText(context,getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            getPinTransferResponse();
                        }
                    }
                }
            });

            /*Button resend otp click listener*/
            btnResendOTP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
               /* if(edTxtOtp.getText().toString().equals("")){
                    edTxtOtp.setError("Plz Enter Otp number");
                    edTxtOtp.requestFocus();
                }*/

                    if(!ConnectivityUtils.isNetworkEnabled(context)){
                        Toast.makeText(context,getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    }
                    else {
                       // getPinTransferforOTP();
                    }

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }



        return rootView;
    }


    /*Pin Transfer api request and response*/
    private void getPinTransferforOTP(){
        pDialog=new ProgressDialog(context);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        BaseRequest baseRequest=new BaseRequest();
        /*Set value in Entity class*/
        String stringRequestType="";
        stringRequestType= ApiConstants.PIN_TRANSFER_OTP;
        baseRequest.setReqtype(stringRequestType);
        baseRequest.setPasswd(SharedPrefrence_Business.getPassword(context));
        baseRequest.setUserid(SharedPrefrence_Business.getUserID(context));
        baseRequest.setIslogin(SharedPrefrence_Business.getIsLogin(context));

        Call<BaseResponse> callPinTransferOTP=
                NetworkClient.getInstance(context).create(EpinServices.class).fetchPinTransOTP(baseRequest,strApiKey);

        callPinTransferOTP.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                try{
                    BaseResponse Response=new BaseResponse();
                    Response=response.body();

                    if(Response.getResponse().equals("OK")){


                    }
                    else {
                        Toast.makeText(context,Response.getResponse(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG)
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

    /*PinTransfer Api Requests and response*/
    private void getPinTransferResponse(){
        pDialog=new ProgressDialog(context);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        PinTransferWithOtpRequest request=new PinTransferWithOtpRequest();
        request.setReqtype(ApiConstants.REQUEST_PIN_TRANSFER);
        request.setPasswd(SharedPrefrence_Business.getPassword(context));
        request.setUserid(SharedPrefrence_Business.getUserID(context));
        request.setIslogin(SharedPrefrence_Business.getIsLogin(context));
        request.setToid(strIdNo);
        request.setOtp(edTxtOtp.getText().toString());
        request.setPkgid(strKitId);
        request.setQty(strQuantity);
        request.setRemark(strRemark);

        Call<BaseResponse> callPinTransfer=
                NetworkClient.getInstance(context).create(EpinServices.class).fetchPinTrans(request,strApiKey);

        callPinTransfer.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();

                BaseResponse Response=new BaseResponse();
                Response=response.body();
                if(Response != null){
                    if(Response.getResponse().equals("OK")){

                    /*AlertDialogUtils.showDialogWithOneButton(context, new AlertDialogButtonListener() {
                        @Override
                        public void onButtontapped(String btnText) {
                            ((BusinessDashboardActivity)context).loadHomeFragment();
                        }
                    },Response.getMsg());*/
                        SuccessMsgFragment fragment=new SuccessMsgFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("Msg",Response.getMsg());
                        fragment.setArguments(bundle);
                        ((BusinessDashboardActivity)context).replaceFragment_business(fragment,"SuccessMsgFragment",bundle);

                    }
                    else {
                        Toast.makeText(context,Response.getResponse() +":"+ Response.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    String toast=  "Something went wrong.." ;

                    Snackbar.make(view, toast, Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();
                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                if(pDialog.isShowing())
                    pDialog.dismiss();
                Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG)
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
