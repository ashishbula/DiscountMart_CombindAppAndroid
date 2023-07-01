package in.discountmart.business.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.base.network.NetworkClient;
import in.discountmart.R;
import in.discountmart.business.adapter.ComplaintDetailViewAdapter;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.business_constants.AppConstants;
import in.discountmart.business.call_api.ComplaintServices;
import in.discountmart.business.model_business.requestmodel.ComplaintReplyRequest;
import in.discountmart.business.model_business.responsemodel.ComplaintDetailItemViewResponse;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplaintDetailViewFragment extends DialogFragment {

    EditText edTxtComplaint;
    EditText edTxtComplaintType;
    EditText edTxtPreviousReply;
    ImageView btnClose;
    RecyclerView recyclerView;
    Context context;

    ArrayList<ComplaintDetailItemViewResponse.ComplaintReplyDetail> arrayList;
    ComplaintDetailItemViewResponse.ComplaintReplyDetail replyDetails[];
    ComplaintDetailViewAdapter adapter;
    ProgressDialog pDialog;

    String strComplaintId="";
    String strApiKey="";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0); // remove title from dialogfragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.business_complaint_detail_view_fragment, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        try{
            context=getActivity();
            edTxtComplaint=(EditText)view.findViewById(R.id.complaint_detail_view_dilog_complaint);
            edTxtComplaintType=(EditText)view.findViewById(R.id.complaint_detail_view_dilog_complaint_type);
            recyclerView=(RecyclerView) view.findViewById(R.id.complaint_detail_view_dilog_pre_reply);
            btnClose=(ImageView) view.findViewById(R.id.complaint_detail_view_dilog_cross);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);

            Bundle bundle=getArguments();

            if(bundle!= null){
                strComplaintId=bundle.getString(AppConstants.EXTRA_STIRNG_COMPLAINT_ID);
            }
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(context);
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);

            if (!ConnectivityUtils.isNetworkEnabled(context)) {
                Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            } else {

                //new getDownlineLeftDetails().execute();
                getComplaintReplyDetail();
            }

            /*Close Dialog*/
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }



        return view;
    }

    /* Get Complaint Detail Api*/
    private void getComplaintReplyDetail(){

      /*  pDialog=new ProgressDialog(getActivity());
        pDialog.setMessage("please wait..");
        pDialog.setCancelable(false);
        pDialog.show();*/
        ComplaintReplyRequest Request = new ComplaintReplyRequest();

        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_COMPLAINT_REPLY);
        Request.setPasswd(SharedPrefrence_Business.getPassword(context));
        Request.setUserid(SharedPrefrence_Business.getUserID(context));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(context));
        Request.setComplaintid(strComplaintId);


        Call<ComplaintDetailItemViewResponse> downlineDetailResponseCall
                = NetworkClient.getInstance(context).create(ComplaintServices.class).fetchComplaintDetailView(Request,strApiKey);

        downlineDetailResponseCall.enqueue(new Callback<ComplaintDetailItemViewResponse>() {
            @Override
            public void onResponse(Call<ComplaintDetailItemViewResponse> call, Response<ComplaintDetailItemViewResponse> response) {

                /*if(pDialog.isShowing())
                    pDialog.dismiss();*/
                try {

                    ComplaintDetailItemViewResponse detailResponse=new ComplaintDetailItemViewResponse();
                    detailResponse=response.body();

                    if (detailResponse.getResponse().equals("OK")) {

                        replyDetails = detailResponse.getComplaintreplydetail();

                        if( replyDetails.length > 0){

                            edTxtComplaint.setText(detailResponse.getComplaint());
                            edTxtComplaintType.setText(detailResponse.getComplainttype());
                            arrayList=new ArrayList<ComplaintDetailItemViewResponse.ComplaintReplyDetail>(Arrays.asList(replyDetails));
                            adapter=new ComplaintDetailViewAdapter(arrayList,context);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }
                        else {
                            Toast.makeText(context,"Record is empty"  +detailResponse.getMsg(), Toast.LENGTH_SHORT).show();
                            arrayList=new ArrayList<ComplaintDetailItemViewResponse.ComplaintReplyDetail>(Arrays.asList(replyDetails));
                            arrayList.clear();
                            adapter=new ComplaintDetailViewAdapter(arrayList,context);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }

                    }
                    else {
                        Toast.makeText(context, detailResponse.getResponse(), Toast.LENGTH_SHORT).show();

                        //editTextConfirmCode.setText("");
                        //Toast.makeText(LoginActivity.this, loginUserGetResponseEntity.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ComplaintDetailItemViewResponse> call, Throwable t) {

               /* if(pDialog.isShowing())
                    pDialog.dismiss();*/
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
