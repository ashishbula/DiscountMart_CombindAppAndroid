package in.discountmart.business.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import in.base.network.NetworkClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import in.discountmart.R;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.EpinServices;
import in.discountmart.business.model_business.requestmodel.TopupRequest;
import in.discountmart.business.model_business.requestmodel.ValidateKitRequest;
import in.discountmart.business.model_business.responsemodel.ValidateKitResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TopupFragment extends Fragment {

    Context context;
    EditText editTextIdNo;
    EditText editTextScratchNo;
    EditText editTextSponserId;
    EditText editTextMembName;
    EditText editTextTopup;
    LinearLayout layoutMemberName;
    LinearLayout layoutSponserId;
    LinearLayout layoutProcess;
    LinearLayout layoutSubmit;
    LinearLayout layoutTopup;
    Button btnSubmitt;
    Button btnProcess;
    TextView txtNote;
    View view1;

    String strPinNo="";
    String strScratchNo="";
    String strPackage="";
    String strId="";
    String strSponserID="";
    boolean validKit;

    ProgressDialog pDialog;
    //Empty Constructor
    public TopupFragment(){
        // Empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.business_topup_fragment, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context=getActivity();
        try {
            view1=getActivity().findViewById(android.R.id.content);
            editTextIdNo=(EditText)v.findViewById(R.id.topup_fragment_editxt_Idno);
            editTextScratchNo=(EditText)v.findViewById(R.id.topup_fragment_editxt_scratchno);
            editTextSponserId=(EditText)v.findViewById(R.id.topup_fragment_editxt_sponserId);
            editTextTopup=(EditText)v.findViewById(R.id.topup_fragment_editxt_topupby);
            editTextMembName=(EditText)v.findViewById(R.id.topup_fragment_editxt_memb_nme);
            layoutMemberName= (LinearLayout)v.findViewById(R.id.topup_fragment_layout_memb_name);
            layoutTopup= (LinearLayout)v.findViewById(R.id.topup_fragment_layout_topupby);
            layoutSponserId= (LinearLayout)v.findViewById(R.id.topup_fragment_layout_sponsrid);
            layoutProcess= (LinearLayout)v.findViewById(R.id.topup_fragment_layout_process);
            layoutSubmit= (LinearLayout)v.findViewById(R.id.topup_fragment_layout_submit);
            btnSubmitt=(Button)v.findViewById(R.id.topup_fragment_btn_submit);
            btnProcess=(Button)v.findViewById(R.id.topup_fragment_btn_process);
            txtNote=(TextView)v.findViewById(R.id.topup_fragment_txt_note);

            Bundle bundle= getArguments();

            if(bundle != null){
                strPinNo=bundle.getString("PinNo");
                strScratchNo=bundle.getString("ScracthNo");

                editTextScratchNo.setText(strScratchNo);
            }
            else {
                Toast.makeText(context,"Bundle Null", Toast.LENGTH_SHORT).show();
            }

            /*Layout click Liastner for get validae kit*/
            btnProcess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(editTextIdNo.getText().toString().equals("")){
                        Toast.makeText(context,"Plz Enter Id No", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        strId=editTextIdNo.getText().toString();

                        /*Call Valide Kit APi*/
                        if(!ConnectivityUtils.isNetworkEnabled(getActivity())){
                            Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //new getValidateKitDetails().execute();
                            getValideKit();
                        }

                    }
                }
            });



            /*Button Submmit for Confrim Topup*/
            btnSubmitt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(editTextIdNo.getText().toString().equals("")){
                        Toast.makeText(context,"Plz Enter ID No and Click to Blank Space ", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(validKit){
                            strId=editTextIdNo.getText().toString();

                            // call Top up api

                            getTopupDetail();
                            /*ConfirmTopupFragment fragment=new ConfirmTopupFragment();
                            Bundle bundle1=new Bundle();
                            bundle1.putString("ID",editTextIdNo.getText().toString());
                            bundle1.putString("PIN",strPinNo);
                            bundle1.putString("Name",editTextMembName.getText().toString());
                            bundle1.putString("Package",strPackage);
                            fragment.setArguments(bundle1);*/
                            //((BusinessDashboardActivity)context).replaceFragment(fragment, AppConstants.CONFIRM_TOPUP,null);
                        }
                        else
                            {
                                Toast.makeText(context,"Kit not valid please try again. ", Toast.LENGTH_SHORT).show();
                            }

                    }

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }


        return v;
    }


    /*Get Validate Kit APi*/
    private void getValideKit(){

        pDialog=new ProgressDialog(context);
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();

        final ValidateKitRequest Request = new ValidateKitRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_VALIDATE_KIT);
        Request.setPasswd(SharedPrefrence_Business.getPassword(context));
        Request.setUserid(SharedPrefrence_Business.getUserID(context));
        Request.setMemberid(strId);
        Request.setPinno(strPinNo);

        //1. Convert object to JSON string
        Gson gson = new Gson();
        String Get_Paramenter = gson.toJson(Request);
        Log.e("ReqValideKit->", Get_Paramenter);

        Call<ValidateKitResponse> validateKitResponseCall=
                NetworkClient.getInstance(context).create(EpinServices.class).fetchValidateKit(Request);
        validateKitResponseCall.enqueue(new Callback<ValidateKitResponse>() {
            @Override
            public void onResponse(Call<ValidateKitResponse> call, Response<ValidateKitResponse> response) {

               if (pDialog.isShowing())
                   pDialog.dismiss();
                try {

                    ValidateKitResponse Response =new  ValidateKitResponse();
                    Response=response.body();
                    if(Response != null){
                        if (Response.getResponse().equals("OK")) {

                            layoutMemberName.setVisibility(View.VISIBLE);
                            layoutSponserId.setVisibility(View.VISIBLE);
                            layoutSubmit.setVisibility(View.VISIBLE);
                            layoutTopup.setVisibility(View.VISIBLE);
                            txtNote.setVisibility(View.GONE);
                            validKit=true;

                            editTextSponserId.setText(Response.getSponsorid());
                            strSponserID = Response.getSponsorid();
                            editTextMembName.setText(Response.getMembername());
                            editTextTopup.setText(Response.getNewpkgname());
                            strPackage=Response.getNewpkgname();
                            btnProcess.setEnabled(false);
                            btnProcess.setClickable(false);

                        }
                        else {

                            validKit=false;
                            btnProcess.setEnabled(true);
                            btnProcess.setClickable(true);
                            layoutMemberName.setVisibility(View.GONE);
                            layoutSponserId.setVisibility(View.GONE);
                            layoutTopup.setVisibility(View.GONE);
                            layoutSubmit.setVisibility(View.GONE);
                            String toast= Response.getResponse()+ ":" + Response.getMsg();
                           Toast.makeText(context,toast,Toast.LENGTH_SHORT).show();
                            /*Snackbar.make(view1, toast, Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                    .show();
                            txtNote.setVisibility(View.VISIBLE);*/

                        }
                    }
                    else {
                        String toast="Something went wrong..";
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
            public void onFailure(Call<ValidateKitResponse> call, Throwable t) {

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

                txtNote.setVisibility(View.VISIBLE);
                layoutSubmit.setVisibility(View.GONE);
            }
        });
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
        Request.setMemberid(strId);
        Log.e("TopupRequest:-", Request.toString());

       /* Call<BaseResponse> callTopupResponse=
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
                            ((BusinessDashboardActivity)context).replaceFragment(fragment,"SuccessMsgFragment",bundle);

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
        });*/

    }
}
