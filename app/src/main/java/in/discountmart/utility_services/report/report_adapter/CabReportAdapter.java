package in.discountmart.utility_services.report.report_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.discountmart.R;
import in.discountmart.utility_services.report.custom_class.BaseViewHolder;
import in.discountmart.utility_services.report.report_model.response.CabReportResponse;

public class CabReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private ArrayList<CabReportResponse.CabReport> reportArrayList;
    private Context context;
    public String type;

    private boolean isLoadingAdded = false;
    private CabReportListener listener;

    public CabReportAdapter(Context context, CabReportListener reportListener ) {
        this.context = context;
        this.listener=reportListener;
        reportArrayList = new ArrayList<>();
    }

    public ArrayList<CabReportResponse.CabReport> getMovies() {
        return reportArrayList;
    }

    public void setData(ArrayList<CabReportResponse.CabReport> reportsList) {
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
        View v1 = inflater.inflate(R.layout.utility_cab_report_item, parent, false);
        viewHolder = new ViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        try {
            CabReportResponse.CabReport rechargeReport = reportArrayList.get(position);

            switch (getItemViewType(position)) {
                case ITEM:
                    ViewHolder viewHolder = (ViewHolder) holder;

                    if(reportArrayList.get(position).getStatus().contains("REFUND")){
                        viewHolder.cardView.setBackgroundColor(context.getResources().getColor(R.color.gray5));
                    }
                    else {
                        viewHolder.cardView.setBackgroundColor(context.getResources().getColor(R.color.white));
                    }

                   // viewHolder.txtCabName.setText(reportArrayList.get(position).getTravels());
                    viewHolder.txtTotAmount.setText("Amount :  "+context.getResources().getString(R.string.rs_symbol)+" "+reportArrayList.get(position).getTotalAmount());
                    viewHolder.txtBookDate.setText("Booking Date : "+reportArrayList.get(position).getTransactionDate());

                    viewHolder.txtStatus.setText(reportArrayList.get(position).getStatus());
                    viewHolder.txtFrom.setText(reportArrayList.get(position).getSource());
                    viewHolder.txtTo.setText(reportArrayList.get(position).getDestination());


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

    public static String convert(String dateString) throws ParseException {
        System.out.println("Given date is " + dateString);

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = sdf.parse(dateString);

        return new SimpleDateFormat("dd/MMM/yyyy").format(date);


    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(CabReportResponse.CabReport mc) {
        reportArrayList.add(mc);
        notifyItemInserted(reportArrayList.size() - 1);
    }

    public void addAll(ArrayList<CabReportResponse.CabReport> mcList,String type) {
        this.type=type;
        for (CabReportResponse.CabReport mc : mcList) {
            add(mc);
        }
    }

    public void remove(CabReportResponse.CabReport city) {
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
        add(new CabReportResponse.CabReport());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = reportArrayList.size() - 1;
        CabReportResponse.CabReport item = getItem(position);

        if (item != null) {
            reportArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public CabReportResponse.CabReport getItem(int position) {
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
        public TextView txtCabName;
        public TextView txtBookDate;
        public TextView txtTotSeat;
        public TextView txtTotAmount;
        public TextView txtStatus;
        public TextView txtFrom;
        public TextView txtTo;
        public ImageView imgType;
        public ImageView imgWallet;
        CardView cardView;

        public ViewHolder(View view) {
            super(view);
            txtCabName = view.findViewById(R.id.cab_report_item_txt_cab);
            txtBookDate = view.findViewById(R.id.cab_report_item_txt_book_date);
            txtFrom = view.findViewById(R.id.cab_report_item_txt_fromcity);
            txtTo = view.findViewById(R.id.cab_report_item_txt_tocity);
            txtTotAmount = view.findViewById(R.id.cab_report_item_txt_amount);
            txtStatus = view.findViewById(R.id.cab_report_item_txt_status);
            cardView = view.findViewById(R.id.cab_report_item_card);


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

    public interface CabReportListener {
        void onItemSelected(CabReportResponse.CabReport list);

    }
}
