package in.discountmart.business.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import in.discountmart.R;
import in.discountmart.business.model_business.responsemodel.ComplaintDetailItemViewResponse;

import java.util.ArrayList;

public class ComplaintDetailViewAdapter extends RecyclerView.Adapter {
    @NonNull
    ArrayList<ComplaintDetailItemViewResponse.ComplaintReplyDetail> complaintDetailsList;
    private Context mContext;
    private Resources mResources;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewReply;
        public TextView textViewReplyDate;
        public CardView cardViewItem;

        public TextView Status;
        public TextView Sno;


        public MyViewHolder(View view) {
            super(view);

            textViewReply = (TextView) view.findViewById(R.id.complaint_detail_view_item_text_test);
            textViewReplyDate = (TextView) view.findViewById(R.id.complaint_detail_view_item_text_replydate);

        }
    }

    public ComplaintDetailViewAdapter(ArrayList<ComplaintDetailItemViewResponse.ComplaintReplyDetail> arrayList, Context context) {
        this.complaintDetailsList = arrayList;
        this.mContext = context;
        //this.recyclerView = recyclerView;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        View view = LayoutInflater.from(mContext).inflate(R.layout.business_complaint_detail_view_item, parent, false);
        vh= new MyViewHolder(view);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {

            ComplaintDetailItemViewResponse.ComplaintReplyDetail leaderList = complaintDetailsList.get(position);
            MyViewHolder myViewHolder = (MyViewHolder) holder;

            try{
                //myViewHolder.textViewSno.setText(dailyIncentiveList.get(position).getScratchno());
                myViewHolder.textViewReply.setText(complaintDetailsList.get(position).getReply());
                myViewHolder.textViewReplyDate.setText(complaintDetailsList.get(position).getReplydate());
            }catch (Exception e){
                e.printStackTrace();
            }




        }
    }

    @Override
    public int getItemCount() {
        return complaintDetailsList.size();
    }

}
