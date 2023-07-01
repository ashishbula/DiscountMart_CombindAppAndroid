package in.discountmart.business.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import in.base.network.NetworkClient;
import in.base.ui.BaseFragment;
import com.squareup.picasso.Picasso;
import in.discountmart.R;
import in.discountmart.business.business_constants.ApiConstants;
import in.discountmart.business.call_api.WalletServices;
import in.discountmart.business.model_business.requestmodel.BaseRequest;
import in.discountmart.business.model_business.responsemodel.WalletRequestDetail;
import in.discountmart.business.shared_pref.SharedPrefrence_Business;
import in.discountmart.business.utility.ConnectivityUtils;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by The Rock on 5/11/2018.
 */

public class WalletRequestDetailFragment extends BaseFragment {

    TextView textViewRecord;
    TableLayout tableLayoutRecord;

    LinearLayout linearLayoutNoREcord;
    LinearLayout linearLayoutTable;
    Context context;
    String strApiKey="";

    ArrayList<WalletRequestDetail.WalletRequest> walletRequestArrayList;

    WalletRequestDetail.WalletRequest walletRequest[];
    ProgressDialog pDialog;
    //Empty constructor
    public WalletRequestDetailFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.business_wallet_equest_detail,container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context=getActivity();
        try {
            textViewRecord=(TextView)view.findViewById(R.id.wallet_request_detail_fragment_record);
            linearLayoutNoREcord=(LinearLayout)view.findViewById(R.id.wallet_request_detail_fragment_layout_noRecord);
            linearLayoutTable=(LinearLayout)view.findViewById(R.id.wallet_request_detail_fragment_layout_table);
            tableLayoutRecord=(TableLayout) view.findViewById(R.id.wallet_request_detail_fragment_tablelayout_record);

            /* Get api key value from Shared Preference */
            strApiKey=SharedPrefrence_Business.getApiKey(context);

            if(!ConnectivityUtils.isNetworkEnabled(context)){
                Toast.makeText(context,getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
            else {
                //new getWalletRequestDetails().execute();
                getWalletRequestDetail();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

    }



    public void initTable(final ArrayList<WalletRequestDetail.WalletRequest> wallet) {
        //remove all rows if exist already

        tableLayoutRecord.setVisibility(View.VISIBLE);

        tableLayoutRecord.removeAllViews();


        TableRow tbrow0 = new TableRow(context);

        TextView tvh_1 = new TextView(context);

            tvh_1.setText(" ");

            tvh_1.setGravity(Gravity.CENTER);

            tvh_1.setPadding(10, 10, 10, 10);

            tbrow0.addView(tvh_1, TableRow.LayoutParams.WRAP_CONTENT,100);

        /*Heading Text S No*/
            TextView tvh_2 = new TextView(context);

            tvh_2.setText("S.No");

            tvh_2.setTextColor(getResources().getColor(R.color.black));

            tvh_2.setBackgroundResource(R.drawable.white_bg_box_black_border);

            tvh_2.setGravity(Gravity.CENTER);

            tvh_2.setPadding(10, 10, 10, 10);

            tvh_2.setTypeface(null, Typeface.BOLD);

            tbrow0.addView(tvh_2, TableRow.LayoutParams.WRAP_CONTENT,100);

        /*Heading Text Req.No*/
            TextView tvh_3 = new TextView(context);


            tvh_3.setText("Req.No");

            tvh_3.setTextColor(getResources().getColor(R.color.black));

            tvh_3.setBackgroundResource(R.drawable.white_bg_box_black_border);

            tvh_3.setGravity(Gravity.CENTER);

            tvh_3.setPadding(10, 10, 10, 10);

            tvh_3.setTypeface(null, Typeface.BOLD);

            tbrow0.addView(tvh_3, TableRow.LayoutParams.WRAP_CONTENT,100);

        /*Heading Text Request Date*/
            TextView tvh_4 = new TextView(context);

            tvh_4.setText("Req.Date");

            tvh_4.setTextColor(getResources().getColor(R.color.black));

            tvh_4.setBackgroundResource(R.drawable.white_bg_box_black_border);

            tvh_4.setGravity(Gravity.CENTER);

            tvh_4.setPadding(10, 10, 10, 10);

            tvh_4.setTypeface(null, Typeface.BOLD);

            tbrow0.addView(tvh_4, TableRow.LayoutParams.WRAP_CONTENT,100);

        /*Heading Text Paymnet Mode*/
            TextView tvh_5 = new TextView(context);

            tvh_5.setText("Payment Mode");

            tvh_5.setTextColor(getResources().getColor(R.color.black));

            tvh_5.setBackgroundResource(R.drawable.white_bg_box_black_border);

            tvh_5.setGravity(Gravity.CENTER);

            tvh_5.setPadding(10, 10, 10, 10);

            tvh_5.setTypeface(null, Typeface.BOLD);

            tbrow0.addView(tvh_5, TableRow.LayoutParams.WRAP_CONTENT,100);

        /*Heading Text Cheque/Transaction No*/
        TextView tvh_6 = new TextView(context);

        tvh_6.setText("Cheque/Transaction No.");

        tvh_6.setTextColor(getResources().getColor(R.color.black));

        tvh_6.setBackgroundResource(R.drawable.white_bg_box_black_border);

        tvh_6.setGravity(Gravity.CENTER);

        tvh_6.setPadding(10, 10, 10, 10);

        tvh_6.setTypeface(null, Typeface.BOLD);

        tbrow0.addView(tvh_6, TableRow.LayoutParams.WRAP_CONTENT,100);

        /*Heading Text Cheque/Transaction Date*/
        TextView tvh_7 = new TextView(context);

        tvh_7.setText("Cheque/Transaction Date");

        tvh_7.setTextColor(getResources().getColor(R.color.black));

        tvh_7.setBackgroundResource(R.drawable.white_bg_box_black_border);

        tvh_7.setGravity(Gravity.CENTER);

        tvh_7.setPadding(10, 10, 10, 10);

        tvh_7.setTypeface(null, Typeface.BOLD);

        tbrow0.addView(tvh_7, TableRow.LayoutParams.WRAP_CONTENT,100);

        /*Heading Text Bank Name*/
            TextView tvh_8 = new TextView(context);

        tvh_8.setText("Bank Name");

        tvh_8.setTextColor(getResources().getColor(R.color.black));

        tvh_8.setBackgroundResource(R.drawable.white_bg_box_black_border);

        tvh_8.setGravity(Gravity.CENTER);

        tvh_8.setPadding(10, 10, 10, 10);

        tvh_8.setTypeface(null, Typeface.BOLD);

            tbrow0.addView(tvh_8, TableRow.LayoutParams.WRAP_CONTENT,100);

        /*Heading Text Branch Name*/
            TextView tvh_9 = new TextView(context);

        tvh_9.setText("Branch Name");

        tvh_9.setTextColor(getResources().getColor(R.color.black));

        tvh_9.setBackgroundResource(R.drawable.white_bg_box_black_border);

        tvh_9.setGravity(Gravity.CENTER);

        tvh_9.setPadding(10, 10, 10, 10);

        tvh_9.setTypeface(null, Typeface.BOLD);

            tbrow0.addView(tvh_9, TableRow.LayoutParams.WRAP_CONTENT,100);


            /*Heading Text Transaction Amount*/
            TextView tvh_10 = new TextView(context);

        tvh_10.setText("Amount");

        tvh_10.setTextColor(getResources().getColor(R.color.black));

        tvh_10.setBackgroundResource(R.drawable.white_bg_box_black_border);

        tvh_10.setGravity(Gravity.CENTER);

        tvh_10.setPadding(10, 10, 10, 10);

        tvh_10.setTypeface(null, Typeface.BOLD);

            tbrow0.addView(tvh_10, TableRow.LayoutParams.WRAP_CONTENT,100);

            /*Heading Text  Remark*/
            TextView tvh_11 = new TextView(context);

        tvh_11.setText("Remark");

        tvh_11.setTextColor(getResources().getColor(R.color.black));

        tvh_11.setBackgroundResource(R.drawable.white_bg_box_black_border);

        tvh_11.setGravity(Gravity.CENTER);

        tvh_11.setPadding(10, 10, 10, 10);

        tvh_11.setTypeface(null, Typeface.BOLD);

            tbrow0.addView(tvh_11, TableRow.LayoutParams.WRAP_CONTENT,100);

            /*Heading Text Status*/
            TextView tvh_12 = new TextView(context);

        tvh_12.setText("Status");

        tvh_12.setTextColor(getResources().getColor(R.color.black));

        tvh_12.setBackgroundResource(R.drawable.white_bg_box_black_border);

        tvh_12.setGravity(Gravity.CENTER);

        tvh_12.setPadding(10, 10, 10, 10);

        tvh_12.setTypeface(null, Typeface.BOLD);

            tbrow0.addView(tvh_12, TableRow.LayoutParams.WRAP_CONTENT,100);


            /*Heading Text Image	*/
        TextView tvh_13 = new TextView(context);
        tvh_13.setBackgroundResource(R.drawable.white_bg_box_black_border);
        tvh_13.setPadding(10, 10, 10, 10);

        tvh_13.setTextColor(getResources().getColor(R.color.black));
        tvh_13.setGravity(Gravity.CENTER);

        tvh_13.setTypeface(null, Typeface.BOLD);
        tvh_13.setText("Document file");
        tvh_13.setTextSize(12);

        tbrow0.addView(tvh_13, TableRow.LayoutParams.WRAP_CONTENT,100);




            tableLayoutRecord.addView(tbrow0);

        /*Add Data*/
            for (int i = 0; i < wallet.size(); i++) {

                TableRow tbrow1 = new TableRow(context);

                final int index = i;

                TextView tvd_1 = new TextView(context);
                tvd_1.setText(" ");
                tvd_1.setGravity(Gravity.CENTER);
                tvd_1.setPadding(10, 10, 10, 10);
                tbrow1.addView(tvd_1, TableRow.LayoutParams.WRAP_CONTENT,80);

            /*Serial Numaber*/
                try{
                    TextView tvd_2 = new TextView(context);
                    tvd_2.setEllipsize(TextUtils.TruncateAt.END);
                    tvd_2.setGravity(Gravity.CENTER| Gravity.LEFT);
                    tvd_2.setBackgroundResource(R.drawable.white_bg_box_black_border);
                    tvd_2.setPadding(10, 10, 10, 10);
                    //tvd_2.setText(index);
                    tvd_2.setText(String.valueOf(index+1));
                    RelativeLayout.LayoutParams layoutParams2=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 80);
                    //layoutParams1.=Gravity.CENTER;
                    layoutParams2.addRule(RelativeLayout.ALIGN_LEFT,tvd_2.getId() );
                    //tvd_2.setText(wallet.get(i).getAcno());
                    tbrow1.addView(tvd_2, TableRow.LayoutParams.WRAP_CONTENT,80);
                }catch (Exception e){
                    e.printStackTrace();
                }




            /*Request No*/
                TextView tvd_3 = new TextView(context);

                tvd_3.setEllipsize(TextUtils.TruncateAt.END);

                tvd_3.setTextColor(Color.BLACK);

                tvd_3.setBackgroundResource(R.drawable.white_bg_box_black_border);

                tvd_3.setGravity(Gravity.CENTER);

                tvd_3.setPadding(10, 10, 10, 10);
                tvd_3.setText(wallet.get(i).getReqno());

                tbrow1.addView(tvd_3, TableRow.LayoutParams.WRAP_CONTENT,80);

             /*Request Date*/

                TextView tvd_4 = new TextView(context);

                tvd_4.setEllipsize(TextUtils.TruncateAt.END);

                tvd_4.setTextColor(Color.BLACK);

                tvd_4.setBackgroundResource(R.drawable.white_bg_box_black_border);

                tvd_4.setGravity(Gravity.CENTER);

                tvd_4.setPadding(10, 10, 10, 10);


                //SpannableString spanString = new SpannableString(compList[i].getStatus());

                //spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);

                //spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);

                tvd_4.setText(wallet.get(i).getReqdate());

                tbrow1.addView(tvd_4, TableRow.LayoutParams.WRAP_CONTENT,80);


            /*Payment Mode*/

                TextView tvd_5= new TextView(context);

                tvd_5.setEllipsize(TextUtils.TruncateAt.END);

                tvd_5.setBackgroundResource(R.drawable.white_bg_box_black_border);
                //tv14.setBackgroundColor(getResources().getColor(R.color.gray));

                tvd_5.setGravity(Gravity.CENTER);

                tvd_5.setPadding(10, 10, 10, 10);

                tvd_5.setText(wallet.get(i).getPaymode());

                tvd_5.setTextColor(Color.BLACK);

                tbrow1.addView(tvd_5, TableRow.LayoutParams.WRAP_CONTENT,80);

             /*Transaction Number*/

                TextView tvd_6= new TextView(context);

                tvd_6.setEllipsize(TextUtils.TruncateAt.END);

                // tv12.setMaxLines(1);

                tvd_6.setTextColor(Color.BLACK);
                tvd_6.setBackgroundResource(R.drawable.white_bg_box_black_border);
                //tv14.setBackgroundColor(getResources().getColor(R.color.gray));

                tvd_6.setGravity(Gravity.CENTER );

                tvd_6.setPadding(10, 10, 10, 10);

                tvd_6.setText(wallet.get(i).getTransno());
                RelativeLayout.LayoutParams layoutParams6=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 80);
                //layoutParams1.=Gravity.CENTER;
                layoutParams6.addRule(RelativeLayout.ALIGN_LEFT,tvd_6.getId() );

                tbrow1.addView(tvd_6, TableRow.LayoutParams.WRAP_CONTENT,80);

             /* Transaction Date*/

                TextView tvd_7= new TextView(context);

                tvd_7.setEllipsize(TextUtils.TruncateAt.END);

                tvd_7.setBackgroundResource(R.drawable.white_bg_box_black_border);
                //tv14.setBackgroundColor(getResources().getColor(R.color.gray));
                tvd_7.setPadding(10, 10, 10, 10);

                tvd_7.setGravity(Gravity.CENTER);
                tvd_7.setText(wallet.get(i).getTransdate());

                tvd_7.setTextColor(Color.BLACK);

                tbrow1.addView(tvd_7, TableRow.LayoutParams.WRAP_CONTENT,80);

                /* Bank Name*/

                TextView tvd_8= new TextView(context);

                tvd_8.setEllipsize(TextUtils.TruncateAt.END);
                tvd_8.setBackgroundResource(R.drawable.white_bg_box_black_border);
                //tv14.setBackgroundColor(getResources().getColor(R.color.gray));

                tvd_8.setGravity(Gravity.CENTER);

                tvd_8.setPadding(10, 10, 10, 10);
                tvd_8.setTextColor(Color.BLACK);

                tvd_8.setText(wallet.get(i).getBankname());

                tbrow1.addView(tvd_8, TableRow.LayoutParams.WRAP_CONTENT,80);

                /* Branch Name*/

                TextView tvd_9= new TextView(context);

                tvd_9.setEllipsize(TextUtils.TruncateAt.END);
                tvd_9.setBackgroundResource(R.drawable.white_bg_box_black_border);
                //tv14.setBackgroundColor(getResources().getColor(R.color.gray));

                tvd_9.setGravity(Gravity.CENTER );

                tvd_9.setPadding(10, 10, 10, 10);
                tvd_9.setText(wallet.get(i).getBranchname());

                tvd_9.setTextColor(Color.BLACK);
                RelativeLayout.LayoutParams layoutParams9=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 80);
                //layoutParams1.=Gravity.CENTER;
                layoutParams9.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,tvd_9.getId() );


                tbrow1.addView(tvd_9, TableRow.LayoutParams.WRAP_CONTENT,80);

                /* Amount*/

                TextView tvd_10= new TextView(context);

                tvd_10.setEllipsize(TextUtils.TruncateAt.END);
                tvd_10.setBackgroundResource(R.drawable.white_bg_box_black_border);

                tvd_10.setGravity(Gravity.CENTER | Gravity.RIGHT);

                tvd_10.setPadding(10, 10, 10, 10);

                tvd_10.setText(wallet.get(i).getAmount());

                tvd_10.setTextColor(Color.BLACK);
                RelativeLayout.LayoutParams layoutParams10=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 80);
                //layoutParams1.=Gravity.CENTER;

                layoutParams10.addRule(RelativeLayout.ALIGN_LEFT,tvd_10.getId() );

                tbrow1.addView(tvd_10, TableRow.LayoutParams.WRAP_CONTENT,80);

                /* Remark*/

                TextView tvd_11= new TextView(context);

                tvd_11.setEllipsize(TextUtils.TruncateAt.END);

                // tv12.setMaxLines(1);

                tvd_11.setBackgroundResource(R.drawable.white_bg_box_black_border);

                tvd_11.setGravity(Gravity.CENTER );

                tvd_11.setPadding(10, 10, 10, 10);
                tvd_11.setText(wallet.get(i).getRemarks());

                tvd_11.setTextColor(Color.BLACK);
                RelativeLayout.LayoutParams layoutParams11=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 80);
                //layoutParams1.=Gravity.CENTER;
                layoutParams11.addRule(RelativeLayout.ALIGN_LEFT,tvd_11.getId() );
                tbrow1.addView(tvd_11, TableRow.LayoutParams.WRAP_CONTENT,80);

                /* Status*/

                TextView tvd_12= new TextView(context);

                tvd_12.setEllipsize(TextUtils.TruncateAt.END);

                tvd_12.setBackgroundResource(R.drawable.white_bg_box_black_border);
                //tv14.setBackgroundColor(getResources().getColor(R.color.gray));
                tvd_12.setGravity(Gravity.CENTER );

                tvd_12.setPadding(10, 10, 10, 10);

                tvd_12.setText(wallet.get(i).getStatus());

                tvd_12.setTextColor(Color.BLACK);
                RelativeLayout.LayoutParams layoutParams12=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 80);
                //layoutParams1.=Gravity.CENTER;
                layoutParams12.addRule(RelativeLayout.ALIGN_LEFT,tvd_12.getId() );
                tbrow1.addView(tvd_12, TableRow.LayoutParams.WRAP_CONTENT,80);

                final ImageView imageView= new ImageView(context);

                // imageView.setImageResource(Integer.parseInt(achivedDetails[i].getStatus()));
                if(wallet.get(i).getScannedfile().equals("")){
                    imageView.setImageResource(R.mipmap.login_logo);
                }
                else {
                    Picasso.with(context).load(wallet.get(i).getScannedfile()).placeholder(R.mipmap.login_logo).into(imageView);

                }

                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(150, 80);
                layoutParams.gravity= Gravity.CENTER;
                imageView.setLayoutParams(layoutParams);

                imageView.setBackgroundResource(R.drawable.white_bg_box_black_border);

                //tv14.setBackgroundColor(getResources().getColor(R.color.gray));

                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                imageView.setPadding(10, 10, 10, 10);

                tbrow1.addView(imageView,150,80);

                tableLayoutRecord.addView(tbrow1);

                final int finalI = i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(imageView.getDrawable()==null){

                        }
                        else {
                            final Dialog dialog = new Dialog(context);
                            // Include dialog.xml file
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.business_showimage_dialog);
                            // Set dialog title


                            // set values for custom dialog components - text, image and
                            // button

                            ImageView image = (ImageView) dialog
                                    .findViewById(R.id.crossexpand);
                            ImageView expandimage = (ImageView) dialog
                                    .findViewById(R.id.expandedimage);
                            Picasso.with(context).load(wallet.get(finalI).getScannedfile()).placeholder(R.mipmap.login_logo).into(expandimage);

                            // if decline button is clicked, close the custom dialog
                            dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);


                            image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Close dialog

                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }



                    }
                });



            }







    }

    /*WalletRequestDetail Api REquest and Reposne*/

    private void getWalletRequestDetail(){

        showProgressDialog();
        BaseRequest Request = new BaseRequest();
        /*Set value in Entity class*/
        Request.setReqtype(ApiConstants.REQUEST_WALLET_REQUEST_DETAIL);
        Request.setPasswd(SharedPrefrence_Business.getPassword(context));
        Request.setUserid(SharedPrefrence_Business.getUserID(context));
        Request.setIslogin(SharedPrefrence_Business.getIsLogin(context));

        Call<WalletRequestDetail> walletRequestDetailCall=
                NetworkClient.getInstance(context).create(WalletServices.class).fetchWalletRequestDetail(Request,strApiKey);
        walletRequestDetailCall.enqueue(new Callback<WalletRequestDetail>() {
            @Override
            public void onResponse(Call<WalletRequestDetail> call, Response<WalletRequestDetail> response) {
                hideProgressDialog();
                try {

                   WalletRequestDetail walletReportResponse =new WalletRequestDetail();
                   walletReportResponse=response.body();

                    if (walletReportResponse.getResponse().equals("OK")) {

                        walletRequest= walletReportResponse.getPymtreqdetail();
                        textViewRecord.setText(walletReportResponse.getRecordcount());

                        if(walletRequest.length > 0) {
                            //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            linearLayoutTable.setVisibility(View.VISIBLE);
                            linearLayoutNoREcord.setVisibility(View.GONE);

                            walletRequestArrayList=new ArrayList<WalletRequestDetail.WalletRequest>(Arrays.asList(walletRequest));

                            initTable(walletRequestArrayList);
                            //linearLayoutNext.setVisibility(View.VISIBLE);

                        }
                        else {
                            //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            linearLayoutTable.setVisibility(View.GONE);
                            linearLayoutNoREcord.setVisibility(View.VISIBLE);
                            Toast.makeText(context, "Wallet Request Detail Record Not Found", Toast.LENGTH_SHORT).show();
                        }
                        //ArrayList<Element> arrayList = new ArrayList<Element>(Arrays.asList(array));
                    }
                    else {
                        Toast.makeText(context, walletReportResponse.getResponse(), Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<WalletRequestDetail> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }
}
