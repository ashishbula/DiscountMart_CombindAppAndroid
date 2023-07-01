package in.discountmart.utility_services.report.report_adapter;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.report.custom_class.BaseViewHolder;
import in.discountmart.utility_services.report.report_model.response.BillPaymentReportResponse;

public class  BillPaymentReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private ArrayList<BillPaymentReportResponse.BillPayment> reportArrayList;
    private Context context;
    public String type;

    private boolean isLoadingAdded = false;
    private BillPaymentReportListener listener;

    public BillPaymentReportAdapter(Context context, BillPaymentReportListener reportListener ) {
        this.context = context;
        this.listener=reportListener;
        reportArrayList = new ArrayList<>();
    }

    public ArrayList<BillPaymentReportResponse.BillPayment> getMovies() {
        return reportArrayList;
    }

    public void setData(ArrayList<BillPaymentReportResponse.BillPayment> reportsList) {
        this.reportArrayList = reportsList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.utility_item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.utility_billpay_report_item, parent, false);
        viewHolder = new ViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        try {
            BillPaymentReportResponse.BillPayment rechargeReport = reportArrayList.get(position);

            switch (getItemViewType(position)) {
                case ITEM:
                    ViewHolder viewHolder = (ViewHolder) holder;

                    if(type.contentEquals("All")){
                        if(reportArrayList.get(position).getUserStatus().contentEquals("SUCCESS")){
                            viewHolder.layoutDownload.setVisibility(View.VISIBLE);
                            viewHolder.layoutRemark.setVisibility(View.GONE);
                        }
                        else {
                            viewHolder.layoutDownload.setVisibility(View.GONE);
                            viewHolder.layoutRemark.setVisibility(View.VISIBLE);
                            viewHolder.txtRemark.setText(reportArrayList.get(position).getRemark());
                        }
                        if(reportArrayList.get(position).getServiceType().contentEquals("Electricity")){
                            viewHolder.imgType.setImageResource(R.mipmap.utility_ic_electricity);
                            viewHolder.txtServiceType.setText(reportArrayList.get(position).getServiceType());
                            if(reportArrayList.get(position).getUserStatus().contentEquals("SUCCESS")){

                                viewHolder.txtStatus.setText(reportArrayList.get(position).getUserStatus());
                                viewHolder.txtStatusNote.setText("Electricity bill pay - ");
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtDebitFrom.setText("Debited form");
                                viewHolder.imgWallet.setVisibility(View.VISIBLE);
                                viewHolder.imgWallet.setImageResource(R.drawable.ic_wallet);
                            }
                            else {
                                viewHolder.txtStatusNote.setText("Electricity bill pay - ");
                                viewHolder.txtStatus.setText(reportArrayList.get(position).getUserStatus());
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtDebitFrom.setText("");
                                viewHolder.imgWallet.setVisibility(View.GONE);
                            }
                            viewHolder.txtPrice.setText(context.getResources().getString(R.string.rs_symbol)+" "+reportArrayList.get(position).getAmount());
                            viewHolder.txtDate.setText(reportArrayList.get(position).getReqDate());
                            viewHolder.txtNumber.setText(reportArrayList.get(position).getOperatorId());
                        }
                        else if(reportArrayList.get(position).getServiceType().contentEquals("Insurance")){

                            viewHolder.imgType.setImageResource(R.mipmap.utility_ic_insurance);
                            viewHolder.txtServiceType.setText(reportArrayList.get(position).getServiceType());

                            if(reportArrayList.get(position).getUserStatus().contentEquals("SUCCESS")){
                                viewHolder.txtStatusNote.setText("Insurance bill pay - ");
                                viewHolder.txtStatus.setText(reportArrayList.get(position).getUserStatus());
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtDebitFrom.setText("Debited form");
                                viewHolder.imgWallet.setVisibility(View.VISIBLE);
                                viewHolder.imgWallet.setImageResource(R.drawable.ic_wallet);
                            }
                            else {
                                viewHolder.txtStatusNote.setText("Insurance bill pay - ");
                                viewHolder.txtStatus.setText(reportArrayList.get(position).getUserStatus());
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtDebitFrom.setText("");
                                viewHolder.imgWallet.setVisibility(View.GONE);
                            }
                            viewHolder.txtPrice.setText(context.getResources().getString(R.string.rs_symbol)+" "+reportArrayList.get(position).getAmount());
                           viewHolder.txtDate.setText(reportArrayList.get(position).getReqDate());
                            viewHolder.txtNumber.setText(reportArrayList.get(position).getOperatorId());


                        }
                        else if(reportArrayList.get(position).getServiceType().contentEquals("Gas")){

                            viewHolder.imgType.setImageResource(R.mipmap.utility_ic_gas);
                            viewHolder.txtServiceType.setText(reportArrayList.get(position).getServiceType());

                            if(reportArrayList.get(position).getUserStatus().contentEquals("SUCCESS")){
                                viewHolder.txtStatusNote.setText("Gas bill pay : - ");
                                viewHolder.txtStatus.setText(reportArrayList.get(position).getUserStatus());
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtDebitFrom.setText("Debited form");
                                viewHolder.imgWallet.setVisibility(View.VISIBLE);
                                viewHolder.imgWallet.setImageResource(R.drawable.ic_wallet);
                            }
                            else {
                                viewHolder.txtStatusNote.setText("Gas bill pay : - ");
                                viewHolder.txtStatus.setText(reportArrayList.get(position).getUserStatus());
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtDebitFrom.setText("");
                                viewHolder.imgWallet.setVisibility(View.GONE);
                            }
                            viewHolder.txtPrice.setText(context.getResources().getString(R.string.rs_symbol)+" "+reportArrayList.get(position).getAmount());
                            viewHolder.txtDate.setText(reportArrayList.get(position).getReqDate());
                            viewHolder.txtNumber.setText(reportArrayList.get(position).getOperatorId());


                        }

                        else if(reportArrayList.get(position).getServiceType().contentEquals("Credit Card")){

                            viewHolder.imgType.setImageResource(R.mipmap.utility_ic_credit);
                            viewHolder.txtServiceType.setText(reportArrayList.get(position).getServiceType());

                            if(reportArrayList.get(position).getUserStatus().contentEquals("SUCCESS")){
                                viewHolder.txtStatusNote.setText("Credit Card Bill Pay : - ");
                                viewHolder.txtStatus.setText(reportArrayList.get(position).getUserStatus());
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtDebitFrom.setText("Debited form");
                                viewHolder.imgWallet.setVisibility(View.VISIBLE);
                                viewHolder.imgWallet.setImageResource(R.drawable.ic_wallet);
                            }
                            else {
                                viewHolder.txtStatusNote.setText("Credit Card Bill Pay : - ");
                                viewHolder.txtStatus.setText(reportArrayList.get(position).getUserStatus());
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtDebitFrom.setText("");
                                viewHolder.imgWallet.setVisibility(View.GONE);
                            }
                            viewHolder.txtPrice.setText(context.getResources().getString(R.string.rs_symbol)+" "+reportArrayList.get(position).getAmount());
                            viewHolder.txtDate.setText(reportArrayList.get(position).getReqDate());
                            viewHolder.txtNumber.setText(reportArrayList.get(position).getOperatorId());


                        }

                    }
                    else if(type.contentEquals("Gas")){
                        if(reportArrayList.get(position).getServiceType().contentEquals("Gas")){

                            viewHolder.imgType.setImageResource(R.mipmap.utility_ic_gas);
                            viewHolder.txtServiceType.setText(reportArrayList.get(position).getServiceType());

                            if(reportArrayList.get(position).getUserStatus().contentEquals("SUCCESS")){
                                viewHolder.layoutDownload.setVisibility(View.VISIBLE);
                                viewHolder.layoutRemark.setVisibility(View.GONE);
                                viewHolder.txtStatusNote.setText("Gas Bill Pay : - ");
                                viewHolder.txtStatus.setText(reportArrayList.get(position).getUserStatus());
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtDebitFrom.setText("Debited form");
                                viewHolder.imgWallet.setVisibility(View.VISIBLE);
                                viewHolder.imgWallet.setImageResource(R.drawable.ic_wallet);
                            }
                            else {
                                viewHolder.layoutRemark.setVisibility(View.VISIBLE);
                                viewHolder.txtRemark.setText(reportArrayList.get(position).getRemark());
                                viewHolder.txtStatusNote.setText("Gas Bill Pay : - ");
                                viewHolder.layoutDownload.setVisibility(View.GONE);
                                viewHolder.txtStatus.setText(reportArrayList.get(position).getUserStatus());
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtDebitFrom.setText("");
                                viewHolder.imgWallet.setVisibility(View.GONE);
                            }
                            viewHolder.txtPrice.setText(context.getResources().getString(R.string.rs_symbol)+" "+reportArrayList.get(position).getAmount());
                            viewHolder.txtDate.setText(reportArrayList.get(position).getReqDate());
                            viewHolder.txtNumber.setText(reportArrayList.get(position).getOperatorId());


                        }
                    }
                    else if(type.contentEquals("Insurance")){
                        if(reportArrayList.get(position).getServiceType().contentEquals("Insurance")){

                            viewHolder.imgType.setImageResource(R.mipmap.utility_ic_insurance);
                            viewHolder.txtServiceType.setText(reportArrayList.get(position).getServiceType());

                            if(reportArrayList.get(position).getUserStatus().contentEquals("SUCCESS")){
                                viewHolder.layoutDownload.setVisibility(View.VISIBLE);
                                viewHolder.layoutRemark.setVisibility(View.GONE);
                                viewHolder.txtStatusNote.setText("Insurance bill Pay : - ");
                                viewHolder.txtStatus.setText(reportArrayList.get(position).getUserStatus());
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtDebitFrom.setText("Debited form");
                                viewHolder.imgWallet.setVisibility(View.VISIBLE);
                                viewHolder.imgWallet.setImageResource(R.drawable.ic_wallet);
                            }
                            else {
                                viewHolder.layoutRemark.setVisibility(View.VISIBLE);
                                viewHolder.txtRemark.setText(reportArrayList.get(position).getRemark());
                                viewHolder.txtStatusNote.setText("Insurance bill Pay : - ");
                                viewHolder.layoutDownload.setVisibility(View.GONE);
                                viewHolder.txtStatus.setText(reportArrayList.get(position).getUserStatus());
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtDebitFrom.setText("");
                                viewHolder.imgWallet.setVisibility(View.GONE);
                            }
                            viewHolder.txtPrice.setText(context.getResources().getString(R.string.rs_symbol)+" "+reportArrayList.get(position).getAmount());
                            viewHolder.txtDate.setText(reportArrayList.get(position).getReqDate());
                            viewHolder.txtNumber.setText(reportArrayList.get(position).getOperatorId());


                        }
                    }
                    else if(type.contentEquals("Electricity")){
                        if(reportArrayList.get(position).getServiceType().contentEquals("Electricity")){
                            viewHolder.imgType.setImageResource(R.mipmap.utility_ic_electricity);
                            viewHolder.txtServiceType.setText(reportArrayList.get(position).getServiceType());
                            if(reportArrayList.get(position).getUserStatus().contentEquals("SUCCESS")){
                                viewHolder.layoutDownload.setVisibility(View.VISIBLE);
                                viewHolder.layoutRemark.setVisibility(View.GONE);
                                viewHolder.txtStatusNote.setText("Electricity bill Pay : - ");
                                viewHolder.txtStatus.setText(reportArrayList.get(position).getUserStatus());
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtDebitFrom.setText("Debited form");
                                viewHolder.imgWallet.setVisibility(View.VISIBLE);
                                viewHolder.imgWallet.setImageResource(R.drawable.ic_wallet);
                            }
                            else {
                                viewHolder.layoutRemark.setVisibility(View.VISIBLE);
                                viewHolder.txtRemark.setText(reportArrayList.get(position).getRemark());
                                viewHolder.txtStatusNote.setText("Electricity bill Pay : - ");
                                viewHolder.layoutDownload.setVisibility(View.GONE);
                                viewHolder.txtStatus.setText(reportArrayList.get(position).getUserStatus());
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtDebitFrom.setText("");
                                viewHolder.imgWallet.setVisibility(View.GONE);
                            }
                            viewHolder.txtPrice.setText(context.getResources().getString(R.string.rs_symbol)+" "+reportArrayList.get(position).getAmount());
                            viewHolder.txtDate.setText(reportArrayList.get(position).getReqDate());
                            viewHolder.txtNumber.setText(reportArrayList.get(position).getOperatorId());
                        }
                    }
                    else if(type.contentEquals("Credit")){
                        if(reportArrayList.get(position).getServiceType().contentEquals("Credit Card")){

                            viewHolder.imgType.setImageResource(R.mipmap.utility_ic_credit);
                            viewHolder.txtServiceType.setText(reportArrayList.get(position).getServiceType());

                            if(reportArrayList.get(position).getUserStatus().contentEquals("SUCCESS")){
                                viewHolder.layoutDownload.setVisibility(View.VISIBLE);
                                viewHolder.layoutRemark.setVisibility(View.GONE);
                                viewHolder.txtStatusNote.setText("Credit Card Bill Pay : - ");
                                viewHolder.txtStatus.setText(reportArrayList.get(position).getUserStatus());
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtDebitFrom.setText("Debited form");
                                viewHolder.imgWallet.setVisibility(View.VISIBLE);
                                viewHolder.imgWallet.setImageResource(R.drawable.ic_wallet);
                            }
                            else {
                                viewHolder.layoutRemark.setVisibility(View.VISIBLE);
                                viewHolder.txtRemark.setText(reportArrayList.get(position).getRemark());
                                viewHolder.txtStatusNote.setText("Credit Card Bill Pay : - ");
                                viewHolder.layoutDownload.setVisibility(View.GONE);
                                viewHolder.txtStatus.setText(reportArrayList.get(position).getUserStatus());
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtDebitFrom.setText("");
                                viewHolder.imgWallet.setVisibility(View.GONE);
                            }
                            viewHolder.txtPrice.setText(context.getResources().getString(R.string.rs_symbol)+" "+reportArrayList.get(position).getAmount());
                            viewHolder.txtDate.setText(reportArrayList.get(position).getReqDate());
                            viewHolder.txtNumber.setText(reportArrayList.get(position).getOperatorId());


                        }
                    }

                    viewHolder.layoutDownload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {


                                    String file=reportArrayList.get(position).getFileurl();
                                    Uri path = Uri.parse(file);
                                    Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                                    pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    pdfOpenintent.setDataAndType(path, "application/pdf");
                                    try {
                                        context.startActivity(pdfOpenintent);
                                    }
                                    catch (ActivityNotFoundException e) {
                                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }


                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });

                    break;
                case LOADING:
//                Do nothing
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return reportArrayList == null ? 0 : reportArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == reportArrayList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(BillPaymentReportResponse.BillPayment mc) {
        reportArrayList.add(mc);
        notifyItemInserted(reportArrayList.size() - 1);
    }

    public void addAll(ArrayList<BillPaymentReportResponse.BillPayment> mcList,String type) {
        this.type=type;
        for (BillPaymentReportResponse.BillPayment mc : mcList) {
            add(mc);
        }
    }

    public void remove(BillPaymentReportResponse.BillPayment city) {
        int position = reportArrayList.indexOf(city);
        if (position > -1) {
            reportArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new BillPaymentReportResponse.BillPayment());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = reportArrayList.size() - 1;
        BillPaymentReportResponse.BillPayment item = getItem(position);

        if (item != null) {
            reportArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public BillPaymentReportResponse.BillPayment getItem(int position) {
        return reportArrayList.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    public class ViewHolder extends BaseViewHolder {
        public TextView txtServiceType;
        public TextView txtServiceName;
        public TextView txtNumber;
        public TextView txtPrice;
        public TextView txtDate;
        public TextView txtStatus;
        public TextView txtRemark;
        public TextView txtStatusNote;
        public TextView txtDebitFrom;
        public TextView landmark;
        public TextView date;
        public ImageView imgType;
        public ImageView imgWallet;
        LinearLayout layoutDownload;
        LinearLayout layoutRemark;

        public ViewHolder(View view) {
            super(view);
            txtServiceType = view.findViewById(R.id.billpay_report_item_txt_service_type);
            //txtServiceName = view.findViewById(R.id.recharge_report_item_txt_service_number);
            txtNumber = view.findViewById(R.id.billpay_report_item_txt_operatorid);
            txtPrice = view.findViewById(R.id.billpay_report_item_txt_price);
            txtDate = view.findViewById(R.id.billpay_report_item_txt_date);
            txtStatusNote = view.findViewById(R.id.billpay_report_item_txt_statusnote);
            txtStatus = view.findViewById(R.id.billpay_report_item_txt_status);
            txtRemark = view.findViewById(R.id.billpay_report_item_txt_remark);
            txtDebitFrom = view.findViewById(R.id.billpay_report_item_txt_wallet);
            imgType=view.findViewById(R.id.billpay_report_item_img);
            imgWallet=view.findViewById(R.id.billpay_report_item_img_wallet);
            layoutDownload=view.findViewById(R.id.billpay_report_item_download);
            layoutRemark=view.findViewById(R.id.billpay_report_item_remark);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onItemSelected(reportArrayList.get(getAdapterPosition()));
                }
            });

        }

        protected void clear() {

        }

       /* public void onBind(int position) {
            super.onBind(position);
            RechargeReportResponse.RechargeReport item = mPostItems.get(position);

            textViewTitle.setText(item.getTitle());
            textViewDescription.setText(item.getDescription());
        }*/
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

    public interface BillPaymentReportListener {
        void onItemSelected(BillPaymentReportResponse.BillPayment list);

    }
}
