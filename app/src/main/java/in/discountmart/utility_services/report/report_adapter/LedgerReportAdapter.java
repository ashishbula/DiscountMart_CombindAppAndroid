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
import in.discountmart.utility_services.report.report_model.response.LedgerReportResponse;

public class LedgerReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private ArrayList<LedgerReportResponse.LedgerReport> reportArrayList;
    private Context context;
    public String type;

    private boolean isLoadingAdded = false;
    private LedgerReportListener listener;

    public LedgerReportAdapter(Context context, LedgerReportListener reportListener ) {
        this.context = context;
        this.listener=reportListener;
        reportArrayList = new ArrayList<>();
    }

    public ArrayList<LedgerReportResponse.LedgerReport> getMovies() {
        return reportArrayList;
    }

    public void setData(ArrayList<LedgerReportResponse.LedgerReport> reportsList) {
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
        View v1 = inflater.inflate(R.layout.utility_ledger_report_item, parent, false);
        viewHolder = new ViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        try {
            LedgerReportResponse.LedgerReport rechargeReport = reportArrayList.get(position);

            switch (getItemViewType(position)) {
                case ITEM:
                    ViewHolder viewHolder = (ViewHolder) holder;

                    viewHolder.txtBal.setText("Balance :- "+context.getResources().getString(R.string.rs_symbol)+" "+reportArrayList.get(position).getBalalnce());
                    if(reportArrayList.get(position).getStatus().contains("Debit")){
                        viewHolder.txtDrCr.setText("Debit : - "+context.getResources().getString(R.string.rs_symbol)+" "+reportArrayList.get(position).getDebit());

                    }
                    else {
                        viewHolder.txtDrCr.setText("Credit : - "+context.getResources().getString(R.string.rs_symbol)+" "+reportArrayList.get(position).getCredit());

                    }
                    viewHolder.txtDate.setText(reportArrayList.get(position).getTransactionDTime());
                    viewHolder.txtUserName.setText(reportArrayList.get(position).getUserName());
                    viewHolder.txtRemark.setText(reportArrayList.get(position).getTransactionRemark());
                    if(!reportArrayList.get(position).getServiceType().equals("")){
                        viewHolder.txtServiceType.setText("Service : - "+reportArrayList.get(position).getServiceType());
                    }
                    else {
                        viewHolder.txtServiceType.setText("");
                    }


                    /*if(type.contentEquals("All")){
                        if(reportArrayList.get(position).getServiceType().contentEquals("Electricity")){
                            viewHolder.imgType.setImageResource(R.mipmap.utility_home_icon_electricity);
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

                            viewHolder.imgType.setImageResource(R.mipmap.utility_home_icon_insurance);
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

                            viewHolder.imgType.setImageResource(R.mipmap.utility_home_icon_gas);
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

                            viewHolder.imgType.setImageResource(R.mipmap.utility_home_icon_credit_card);
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

                            viewHolder.imgType.setImageResource(R.mipmap.utility_home_icon_gas);
                            viewHolder.txtServiceType.setText(reportArrayList.get(position).getServiceType());

                            if(reportArrayList.get(position).getUserStatus().contentEquals("SUCCESS")){
                                viewHolder.txtStatusNote.setText("Gas Bill Pay : - ");
                                viewHolder.txtStatus.setText(reportArrayList.get(position).getUserStatus());
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtDebitFrom.setText("Debited form");
                                viewHolder.imgWallet.setVisibility(View.VISIBLE);
                                viewHolder.imgWallet.setImageResource(R.drawable.ic_wallet);
                            }
                            else {
                                viewHolder.txtStatusNote.setText("Gas Bill Pay : - ");
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

                            viewHolder.imgType.setImageResource(R.mipmap.utility_home_icon_insurance);
                            viewHolder.txtServiceType.setText(reportArrayList.get(position).getServiceType());

                            if(reportArrayList.get(position).getUserStatus().contentEquals("SUCCESS")){
                                viewHolder.txtStatusNote.setText("Insurance bill Pay : - ");
                                viewHolder.txtStatus.setText(reportArrayList.get(position).getUserStatus());
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtDebitFrom.setText("Debited form");
                                viewHolder.imgWallet.setVisibility(View.VISIBLE);
                                viewHolder.imgWallet.setImageResource(R.drawable.ic_wallet);
                            }
                            else {
                                viewHolder.txtStatusNote.setText("Insurance bill Pay : - ");
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
                            viewHolder.imgType.setImageResource(R.mipmap.utility_home_icon_electricity);
                            viewHolder.txtServiceType.setText(reportArrayList.get(position).getServiceType());
                            if(reportArrayList.get(position).getUserStatus().contentEquals("SUCCESS")){
                                viewHolder.txtStatusNote.setText("Electricity bill Pay : - ");
                                viewHolder.txtStatus.setText(reportArrayList.get(position).getUserStatus());
                                viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtStatusNote.setTextColor(context.getResources().getColor(R.color.green1));
                                viewHolder.txtDebitFrom.setText("Debited form");
                                viewHolder.imgWallet.setVisibility(View.VISIBLE);
                                viewHolder.imgWallet.setImageResource(R.drawable.ic_wallet);
                            }
                            else {
                                viewHolder.txtStatusNote.setText("Electricity bill Pay : - ");
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

                            viewHolder.imgType.setImageResource(R.mipmap.utility_home_icon_credit_card);
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
                    }*/

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

    public void add(LedgerReportResponse.LedgerReport mc) {
        reportArrayList.add(mc);
        notifyItemInserted(reportArrayList.size() - 1);
    }

    public void addAll(ArrayList<LedgerReportResponse.LedgerReport> mcList,String type) {
        this.type=type;
        for (LedgerReportResponse.LedgerReport mc : mcList) {
            add(mc);
        }
    }

    public void remove(LedgerReportResponse.LedgerReport city) {
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
        add(new LedgerReportResponse.LedgerReport());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = reportArrayList.size() - 1;
        LedgerReportResponse.LedgerReport item = getItem(position);

        if (item != null) {
            reportArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public LedgerReportResponse.LedgerReport getItem(int position) {
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
        public TextView txtUserName;
        public TextView txtMemberName;
        public TextView txtNumber;
        public TextView txtPrice;
        public TextView txtDate;
        public TextView txtStatus;
        public TextView txtStatusNote;
        public TextView txtDrCr;
        public TextView txtBal;
        public TextView txtRemark;
        public TextView date;
        public ImageView imgType;
        public ImageView imgWallet;

        public ViewHolder(View view) {
            super(view);
            txtServiceType = view.findViewById(R.id.ledger_report_item_txt_servie);
           // txtServiceName = view.findViewById(R.id.ledger_report_item_txt_servi);
            txtUserName = view.findViewById(R.id.ledger_report_item_txt_username);
            //txtMemberName = view.findViewById(R.id.ledger_report_item_txt_nme);
            txtDate = view.findViewById(R.id.ledger_report_item_txt_date);
            txtDrCr = view.findViewById(R.id.ledger_report_item_txt_drcr);
            txtBal = view.findViewById(R.id.ledger_report_item_txt_bal);
            txtRemark = view.findViewById(R.id.ledger_report_item_txt_remark);


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

    public interface LedgerReportListener {
        void onItemSelected(LedgerReportResponse.LedgerReport list);

    }
}
