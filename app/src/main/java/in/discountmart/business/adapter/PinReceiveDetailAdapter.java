package in.discountmart.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import in.discountmart.R;
import in.discountmart.business.fragment.DownlineFragmentGroupA;
import in.discountmart.business.fragment.DownlineFragmentGroupB;
import in.discountmart.business.model_business.responsemodel.PinReceiveDetailResponse;

import java.util.ArrayList;

public class PinReceiveDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    ArrayList<PinReceiveDetailResponse.PinReceiveDetail> detailArrayList;
    private Context mContext;
    DownlineFragmentGroupA fragment1;
    DownlineFragmentGroupB fragment2;

    private boolean isLoadingAdded = false;


    public PinReceiveDetailAdapter(Context context) {
        //this.fragment1=fragmet;
        this.mContext = context;
        detailArrayList=new ArrayList<>();
        // weeklyIncenLists = list;
        //this.act=act;
        //this.fragment=frag;

    }


    public ArrayList<PinReceiveDetailResponse.PinReceiveDetail> getList() {
        return detailArrayList;
    }

    public void setList(ArrayList<PinReceiveDetailResponse.PinReceiveDetail> list) {
        this.detailArrayList = list;
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
        View v1 = inflater.inflate(R.layout.business_pinrecievedetailitem, parent, false);
        viewHolder = new MyViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        PinReceiveDetailResponse.PinReceiveDetail downlineDetails = detailArrayList.get(position);
        switch (getItemViewType(position)) {
            case ITEM:

                MyViewHolder myViewHolder = (MyViewHolder) holder;
                if(position % 2==0){
                    myViewHolder.cardView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.LightGray));
                    myViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
                }
                else {
                    myViewHolder.cardView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
                    myViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.LightGray));
                }
                myViewHolder.textViewSno.setText(detailArrayList.get(position).getSno());
                //myViewHolder.textViewContest.setText(monthlyIncentiveList.get(position).get());
                myViewHolder.textViewFromId.setText(detailArrayList.get(position).getFromidno());
                myViewHolder.textViewFromMember.setText(detailArrayList.get(position).getFrommember());
                myViewHolder.textViewProduct.setText(detailArrayList.get(position).getKitname());
                myViewHolder.textViewPinNo.setText(detailArrayList.get(position).getPinno());
                myViewHolder.textViewScratchNo.setText(detailArrayList.get(position).getScratchno());
                myViewHolder.textViewDate.setText(detailArrayList.get(position).getPindate());
                myViewHolder.textViewStatus.setText(detailArrayList.get(position).getPinStatus());
                break;
            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return detailArrayList == null ? 0 : detailArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == detailArrayList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(PinReceiveDetailResponse.PinReceiveDetail mc) {
        detailArrayList.add(mc);
        notifyItemInserted(detailArrayList.size() - 1);
    }

    public void addAll(ArrayList<PinReceiveDetailResponse.PinReceiveDetail> mcList) {
        for (PinReceiveDetailResponse.PinReceiveDetail mc : mcList) {
            add(mc);
        }
    }

    public void remove(PinReceiveDetailResponse.PinReceiveDetail city) {
        int position = detailArrayList.indexOf(city);
        if (position > -1) {
            detailArrayList.remove(position);
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
        add(new PinReceiveDetailResponse.PinReceiveDetail());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = detailArrayList.size() - 1;
        PinReceiveDetailResponse.PinReceiveDetail item = getItem(position);

        if (item != null) {
            detailArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public PinReceiveDetailResponse.PinReceiveDetail getItem(int position) {
        return detailArrayList.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewSno;
        public TextView textViewFromId;
        public TextView textViewFromMember;
        public TextView textViewProduct;
        public TextView textViewPinNo;
        public TextView textViewScratchNo;
        public TextView textViewDate;
        public TextView textViewStatus;
        public CardView cardView;


        public TextView Status;
        public TextView Sno;


        public MyViewHolder(View view) {
            super(view);
            textViewSno = (TextView) view.findViewById(R.id.pin_receive_detail_item_txtView_sno);
            textViewFromId = (TextView) view.findViewById(R.id.pin_receive_detail_item_txtView_fromId);
            textViewFromMember = (TextView) view.findViewById(R.id.pin_receive_detail_item_txtView_fromMember);
            textViewProduct = (TextView) view.findViewById(R.id.pin_receive_detail_item_txtView_product_name);
            textViewPinNo = (TextView) view.findViewById(R.id.pin_receive_detail_item_txtView_pinNo);
            textViewScratchNo = (TextView) view.findViewById(R.id.pin_receive_detail_item_txtView_scratchNo);
            textViewDate = (TextView) view.findViewById(R.id.pin_receive_detail_item_txtView_date);
            textViewStatus = (TextView) view.findViewById(R.id.pin_receive_detail_item_txtView_status);
            cardView=(CardView)view.findViewById(R.id.pin_receive_detail_item_cardView_item);

        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}
