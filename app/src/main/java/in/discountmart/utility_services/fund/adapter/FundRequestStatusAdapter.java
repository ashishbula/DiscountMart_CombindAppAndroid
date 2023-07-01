package in.discountmart.utility_services.fund.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.discountmart.R;
import in.discountmart.utility_services.fund.fund_model.response.FundRequestStatusResponse;

public class FundRequestStatusAdapter extends RecyclerView.Adapter<FundRequestStatusAdapter.MyViewHolder> implements Filterable {

    public Context mContext;
    public List<FundRequestStatusResponse> statusList;
    private List<FundRequestStatusResponse>statusListFiltered;
    private FundRequestStatusAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtAmount;
        public TextView txtReqDate;
        public TextView txtRemark;
        public TextView txtAdmRemark;
        public TextView txtStatus;
        public TextView txtReqstBy;
        public TextView txtMobile;
        public ImageView thumbnail;
        public LinearLayout layoutApprove;
        public LinearLayout layoutStatus;
        public LinearLayout layoutRemark;
        public LinearLayout layoutMobile;

        public MyViewHolder(View view) {
            super(view);
            txtAmount = view.findViewById(R.id.fund_req_status_txt_amount);
            txtReqDate = view.findViewById(R.id.fund_req_status_txt_reqdate);
            txtRemark = view.findViewById(R.id.fund_req_status_txt_remark);
            txtAdmRemark = view.findViewById(R.id.fund_req_status_txt_admremark);
            txtStatus = view.findViewById(R.id.fund_req_status_txt_status);
            txtReqstBy = view.findViewById(R.id.fund_req_status_txt_reqby);
            txtMobile = view.findViewById(R.id.fund_req_status_txt_mobile);
            layoutApprove=view.findViewById(R.id.fund_req_status_layout_approve);
            layoutRemark=view.findViewById(R.id.fund_req_status_layout_remark);
            layoutStatus=view.findViewById(R.id.fund_req_status_layout_status);
            layoutMobile=view.findViewById(R.id.fund_req_status_layout_mobile);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onItemSelected(statusListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public FundRequestStatusAdapter(Context context, List<FundRequestStatusResponse> list,FundRequestStatusAdapterListener listener) {
        this.mContext = context;
        this.listener = listener;
        this.statusList = list;
        this.statusListFiltered = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.utility_fund_request_status_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final FundRequestStatusResponse contact = statusListFiltered.get(position);

        if(contact.getAdminRemark().contentEquals("")){
            holder.layoutApprove.setVisibility(View.GONE);
        }
        else {
            holder.layoutApprove.setVisibility(View.VISIBLE);
        }
        if(contact.getStatus().contentEquals(""))
            holder.layoutStatus.setVisibility(View.GONE);
        else
            holder.layoutStatus.setVisibility(View.VISIBLE);
        if(contact.getRemark().contentEquals(""))
            holder.layoutRemark.setVisibility(View.GONE);
        else
            holder.layoutRemark.setVisibility(View.VISIBLE);

        holder.txtAmount.setText(mContext.getResources().getString(R.string.rs_symbol) +" "+contact.getAmount());
        holder.txtReqstBy.setText(contact.getRequestedBy());
        holder.txtAdmRemark.setText(contact.getAdminRemark());
        holder.txtStatus.setText(contact.getStatus());
        holder.txtReqDate.setText(contact.getRequestDate());
       holder.txtRemark.setText(contact.getRemark());



       /* Glide.with(context)
                .load(contact.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);*/
    }

    @Override
    public int getItemCount() {
        return statusListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    statusListFiltered = statusList;
                } else {
                    List<FundRequestStatusResponse> filteredList = new ArrayList<>();
                    for (FundRequestStatusResponse row : statusList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        /*if (row.getCityName().toLowerCase().contains(charString.toLowerCase()) || row.getPhone().contains(charSequence)) {
                            filteredList.add(row);

                        }*/

                        if (row.getAmount().toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                        }
                       else if(row.getAdminRemark().toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(row);
                        }
                        else if(row.getRemark().toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(row);
                        }

                        else if(row.getRequestDate().toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(row);
                        }
                        else if(row.getRequestedBy().toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(row);
                        }
                        else if(row.getStatus().toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(row);
                        }
                    }

                    statusListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = statusListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                statusListFiltered = (ArrayList<FundRequestStatusResponse>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface FundRequestStatusAdapterListener {
        void onItemSelected(FundRequestStatusResponse response);
    }
}