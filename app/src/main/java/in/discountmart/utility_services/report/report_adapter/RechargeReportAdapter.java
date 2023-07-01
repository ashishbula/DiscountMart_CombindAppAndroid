package in.discountmart.utility_services.report.report_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.report.custom_class.BaseViewHolder;
import in.discountmart.utility_services.report.report_model.response.RechargeReportResponse;

/**
 * Created by Suleiman on 19/10/16.
 */

public class RechargeReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private ArrayList<RechargeReportResponse.RechargeReport> reportArrayList;
    private Context context;
    public String type;

    private boolean isLoadingAdded = false;
    private RechargeReportListener listener;

    public RechargeReportAdapter(Context context,RechargeReportListener reportListener ) {
        this.context = context;
        this.listener=reportListener;
        reportArrayList = new ArrayList<>();
    }

    public ArrayList<RechargeReportResponse.RechargeReport> getMovies() {
        return reportArrayList;
    }

    public void setData(ArrayList<RechargeReportResponse.RechargeReport> reportsList) {
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
        View v1 = inflater.inflate(R.layout.utility_recharge_report_list_item, parent, false);
        viewHolder = new ViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        try {
            RechargeReportResponse.RechargeReport rechargeReport = reportArrayList.get(position);

            switch (getItemViewType(position)) {
                case ITEM:
                    ViewHolder viewHolder = (ViewHolder) holder;

                    if(type.contentEquals("0")){
                        if(reportArrayList.get(position).getService().contentEquals("DTH")){
                            viewHolder.imgType.setImageResource(R.mipmap.utility_ic_dth);
                            viewHolder.txtServiceType.setText(reportArrayList.get(position).getService());
                            viewHolder.txtStatusNote.setText("DTH Recharge : - ");
                            viewHolder.txtStatus.setText(reportArrayList.get(position).getStatus());
                            if(reportArrayList.get(position).getStatus().contentEquals("SUCCESS")){
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtDebitFrom.setText("Debited form");
                                viewHolder.imgWallet.setVisibility(View.VISIBLE);
                                viewHolder.imgWallet.setImageResource(R.drawable.ic_wallet);
                            }
                            else {
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtDebitFrom.setText("");
                                viewHolder.imgWallet.setVisibility(View.GONE);
                            }
                            viewHolder.txtPrice.setText(context.getResources().getString(R.string.rs_symbol)+" "+reportArrayList.get(position).getAmount());
                            viewHolder.txtDate.setText(reportArrayList.get(position).getRechargeDate());
                            viewHolder.txtNumber.setText(reportArrayList.get(position).getMobileNo());
                        }
                        else if(reportArrayList.get(position).getService().contentEquals("POSTPAID")){

                            viewHolder.imgType.setImageResource(R.mipmap.utility_ic_postpaid);
                            viewHolder.txtServiceType.setText("Mobile "+reportArrayList.get(position).getService());
                            viewHolder.txtStatusNote.setText("Postpaid bill Pay : - ");
                            viewHolder.txtStatus.setText(reportArrayList.get(position).getStatus());
                            if(reportArrayList.get(position).getStatus().contentEquals("SUCCESS")){
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtDebitFrom.setText("Debited from");
                                viewHolder.imgWallet.setVisibility(View.VISIBLE);
                                viewHolder.imgWallet.setImageResource(R.drawable.ic_wallet);
                            }
                            else {
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtDebitFrom.setText("");
                                viewHolder.imgWallet.setVisibility(View.GONE);
                            }
                            viewHolder.txtPrice.setText(context.getResources().getString(R.string.rs_symbol)+" "+reportArrayList.get(position).getAmount());
                            viewHolder.txtDate.setText(reportArrayList.get(position).getRechargeDate());
                            viewHolder.txtNumber.setText(reportArrayList.get(position).getMobileNo());


                        }
                        else if(reportArrayList.get(position).getService().contentEquals("PREPAID")){

                            viewHolder.imgType.setImageResource(R.mipmap.utility_ic_prepaid);
                            viewHolder.txtServiceType.setText("Mobile "+reportArrayList.get(position).getService());
                            viewHolder.txtStatusNote.setText("Mobile Recharge : - ");
                            viewHolder.txtStatus.setText(reportArrayList.get(position).getStatus());
                            if(reportArrayList.get(position).getStatus().contentEquals("SUCCESS")){
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtDebitFrom.setText("Debited form");
                                viewHolder.imgWallet.setVisibility(View.VISIBLE);
                                viewHolder.imgWallet.setImageResource(R.drawable.ic_wallet);
                            }
                            else {
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtDebitFrom.setText("");
                                viewHolder.imgWallet.setVisibility(View.GONE);
                            }
                            viewHolder.txtPrice.setText(context.getResources().getString(R.string.rs_symbol)+" "+reportArrayList.get(position).getAmount());
                            viewHolder.txtDate.setText(reportArrayList.get(position).getRechargeDate());
                            viewHolder.txtNumber.setText(reportArrayList.get(position).getMobileNo());


                        }
                    }
                    else if(type.contentEquals("T")){
                         if(reportArrayList.get(position).getService().contentEquals("POSTPAID")){

                            viewHolder.imgType.setImageResource(R.mipmap.utility_ic_postpaid);
                            viewHolder.txtServiceType.setText("Mobile "+reportArrayList.get(position).getService());
                            viewHolder.txtStatusNote.setText("Postpaid bill Pay : - ");
                            viewHolder.txtStatus.setText(reportArrayList.get(position).getStatus());
                            if(reportArrayList.get(position).getStatus().contentEquals("SUCCESS")){
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtDebitFrom.setText("Debited form");
                                viewHolder.imgWallet.setVisibility(View.VISIBLE);
                                viewHolder.imgWallet.setImageResource(R.drawable.ic_wallet);
                            }
                            else {
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtDebitFrom.setText("");
                                viewHolder.imgWallet.setVisibility(View.GONE);
                            }
                            viewHolder.txtPrice.setText(context.getResources().getString(R.string.rs_symbol)+" "+reportArrayList.get(position).getAmount());
                            viewHolder.txtDate.setText(reportArrayList.get(position).getRechargeDate());
                             viewHolder.txtNumber.setText(reportArrayList.get(position).getMobileNo());


                        }
                    }
                    else if(type.contentEquals("M")){
                       if(reportArrayList.get(position).getService().contentEquals("PREPAID")){

                            viewHolder.imgType.setImageResource(R.mipmap.utility_ic_prepaid);
                            viewHolder.txtServiceType.setText("Mobile "+reportArrayList.get(position).getService());
                            viewHolder.txtStatusNote.setText("Mobile Recharge : - ");
                            viewHolder.txtStatus.setText(reportArrayList.get(position).getStatus());
                            if(reportArrayList.get(position).getStatus().contentEquals("SUCCESS")){
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtDebitFrom.setText("Debited form");
                                viewHolder.imgWallet.setVisibility(View.VISIBLE);
                                viewHolder.imgWallet.setImageResource(R.drawable.ic_wallet);
                            }
                            else {
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtDebitFrom.setText("");
                                viewHolder.imgWallet.setVisibility(View.GONE);
                            }
                            viewHolder.txtPrice.setText(context.getResources().getString(R.string.rs_symbol)+" "+reportArrayList.get(position).getAmount());
                            viewHolder.txtDate.setText(reportArrayList.get(position).getRechargeDate());
                           viewHolder.txtNumber.setText(reportArrayList.get(position).getMobileNo());


                        }
                    }
                    else if(type.contentEquals("D")){
                        if(reportArrayList.get(position).getService().contentEquals("DTH")){
                            viewHolder.imgType.setImageResource(R.mipmap.utility_ic_dth);
                            viewHolder.txtServiceType.setText(reportArrayList.get(position).getService());
                            viewHolder.txtStatusNote.setText("DTH Recharge : - ");
                            viewHolder.txtStatus.setText(reportArrayList.get(position).getStatus());
                            if(reportArrayList.get(position).getStatus().contentEquals("SUCCESS")){
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtDebitFrom.setText("Debited form");
                                viewHolder.imgWallet.setVisibility(View.VISIBLE);
                                viewHolder.imgWallet.setImageResource(R.drawable.ic_wallet);
                            }
                            else {
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.red));
                                viewHolder.txtDebitFrom.setText("");
                                viewHolder.imgWallet.setVisibility(View.GONE);
                            }
                            viewHolder.txtPrice.setText(context.getResources().getString(R.string.rs_symbol)+" "+reportArrayList.get(position).getAmount());
                            viewHolder.txtDate.setText(reportArrayList.get(position).getRechargeDate());
                            viewHolder.txtNumber.setText(reportArrayList.get(position).getMobileNo());
                        }
                    }

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

    public void add(RechargeReportResponse.RechargeReport mc) {
        reportArrayList.add(mc);
        notifyItemInserted(reportArrayList.size() - 1);
    }

    public void addAll(ArrayList<RechargeReportResponse.RechargeReport> mcList,String type) {
        this.type=type;
        for (RechargeReportResponse.RechargeReport mc : mcList) {
            add(mc);
        }
    }

    public void remove(RechargeReportResponse.RechargeReport city) {
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
        add(new RechargeReportResponse.RechargeReport());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = reportArrayList.size() - 1;
        RechargeReportResponse.RechargeReport item = getItem(position);

        if (item != null) {
            reportArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public RechargeReportResponse.RechargeReport getItem(int position) {
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
        public TextView txtStatusNote;
        public TextView txtDebitFrom;
        public TextView landmark;
        public TextView date;
        public ImageView imgType;
        public ImageView imgWallet;

        public ViewHolder(View view) {
            super(view);
            txtServiceType = view.findViewById(R.id.recharge_report_item_txt_serrvice_type);
            //txtServiceName = view.findViewById(R.id.recharge_report_item_txt_service_number);
            txtNumber = view.findViewById(R.id.recharge_report_item_txt_service_number);
            txtPrice = view.findViewById(R.id.recharge_report_item_txt_price);
            txtDate = view.findViewById(R.id.recharge_report_item_txt_date);
            txtStatusNote = view.findViewById(R.id.recharge_report_item_txt_statusnote);
            txtStatus = view.findViewById(R.id.recharge_report_item_txt_status);
            txtDebitFrom = view.findViewById(R.id.recharge_report_item_txt_wallet);
            imgType=view.findViewById(R.id.recharge_report_item_img);
            imgWallet=view.findViewById(R.id.recharge_report_item_img_wallet);

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

    public interface RechargeReportListener {
        void onItemSelected(RechargeReportResponse.RechargeReport list);

    }


}
