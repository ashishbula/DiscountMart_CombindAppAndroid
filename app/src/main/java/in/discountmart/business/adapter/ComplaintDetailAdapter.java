package in.discountmart.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import in.discountmart.R;
import in.discountmart.business.activity.BusinessDashboardActivity;
import in.discountmart.business.fragment.DownlineFragmentGroupA;
import in.discountmart.business.fragment.DownlineFragmentGroupB;
import in.discountmart.business.model_business.responsemodel.ComplaintDetailResponse;

import java.util.ArrayList;

public class ComplaintDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    ArrayList<ComplaintDetailResponse.ComplaintDetails> DetailsList;
    private Context mContext;
    DownlineFragmentGroupA fragment1;
    DownlineFragmentGroupB fragment2;

    private boolean isLoadingAdded = false;


    public ComplaintDetailAdapter(Context context) {
        //this.fragment1=fragmet;
        this.mContext = context;
        DetailsList=new ArrayList<>();
        // weeklyIncenLists = list;
        //this.act=act;
        //this.fragment=frag;

    }
   /* public DownlineDetailAdapter(Context context) {
        //this.fragment2=fragmet;
        this.mContext = context;
        downlineDetailsList=new ArrayList<DownlineDetailResponse.DownlineDetails>();
        // weeklyIncenLists = list;
        //this.act=act;
        //this.fragment=frag;

    }*/

    public ArrayList<ComplaintDetailResponse.ComplaintDetails> getList() {
        return DetailsList;
    }

    public void setList(ArrayList<ComplaintDetailResponse.ComplaintDetails> list) {
        this.DetailsList = list;
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
                View v2 = inflater.inflate(R.layout.load_more_progressbar, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.business_complain_detail_item, parent, false);
        viewHolder = new MyViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        ComplaintDetailResponse.ComplaintDetails downlineDetails = DetailsList.get(position);
        switch (getItemViewType(position)) {
            case ITEM:

                MyViewHolder myViewHolder = (MyViewHolder) holder;
                if(position % 2==0){
                    myViewHolder.cardViewItem.setBackgroundColor(ContextCompat.getColor(mContext,R.color.LightGray));
                    myViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
                }
                else {
                    myViewHolder.cardViewItem.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
                    myViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.LightGray));
                }
                myViewHolder.textViewComplaint.setText(DetailsList.get(position).getComplaint());
                //myViewHolder.textViewToDate.setText(dailyIncentiveList.get(position).getTodate());
                myViewHolder.textViewComplaintId.setText(DetailsList.get(position).getComplaintid());
                myViewHolder.textViewComplaintDate.setText(DetailsList.get(position).getComplaintdate());
                myViewHolder.textViewReply.setText(DetailsList.get(position).getReply());
                myViewHolder.textViewReplyDate.setText(DetailsList.get(position).getReplydate());


                myViewHolder.textViewView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String strID="";
                        strID=DetailsList.get(position).getComplaintid();
                        if(!strID.equals(""))
                            ((BusinessDashboardActivity)mContext).showComplaintDetalViewDialog(strID);
                        else
                            Toast.makeText(mContext,"Complaint Id Not Received", Toast.LENGTH_SHORT).show();




                    }
                });
                break;
            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return DetailsList == null ? 0 : DetailsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == DetailsList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(ComplaintDetailResponse.ComplaintDetails mc) {
        DetailsList.add(mc);
        notifyItemInserted(DetailsList.size() - 1);
    }

    public void addAll(ArrayList<ComplaintDetailResponse.ComplaintDetails> mcList) {
        for (ComplaintDetailResponse.ComplaintDetails mc : mcList) {
            add(mc);
        }
    }

    public void remove(ComplaintDetailResponse.ComplaintDetails city) {
        int position = DetailsList.indexOf(city);
        if (position > -1) {
            DetailsList.remove(position);
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
        add(new ComplaintDetailResponse.ComplaintDetails());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = DetailsList.size() - 1;
        ComplaintDetailResponse.ComplaintDetails item = getItem(position);

        if (item != null) {
            DetailsList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public ComplaintDetailResponse.ComplaintDetails getItem(int position) {
        return DetailsList.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewComplaint;
        public TextView textViewComplaintId;
        public TextView textViewComplaintDate;
        public TextView textViewReply;
        public TextView textViewReplyDate;
        public TextView textViewView;


        public CardView cardViewItem;


        public TextView Status;
        public TextView Sno;


        public MyViewHolder(View view) {
            super(view);
            textViewComplaint = (TextView) view.findViewById(R.id.complain_detail_item_txtView_complain);
            textViewComplaintId = (TextView) view.findViewById(R.id.complain_detail_item_txtView_complain_id);
            textViewComplaintDate = (TextView) view.findViewById(R.id.complain_detail_item_txtView_complain_date);
            textViewReply = (TextView) view.findViewById(R.id.complain_detail_item_txtView_reply);
            textViewReplyDate = (TextView) view.findViewById(R.id.complain_detail_item_txtView_reply_date);
            textViewView = (TextView) view.findViewById(R.id.complain_detail_item_txtView_view);
            cardViewItem=(CardView)view.findViewById(R.id.complain_detail_item_cardView_item);




        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}
