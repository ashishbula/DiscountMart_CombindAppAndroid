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
import in.discountmart.business.model_business.responsemodel.DownlinePurchaseResponse;

import java.util.ArrayList;

public class DownlinePurchaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    ArrayList<DownlinePurchaseResponse.DownlinePurchaseItem> detailArrayList;
    private Context mContext;
    DownlineFragmentGroupA fragment1;
    DownlineFragmentGroupB fragment2;

    private boolean isLoadingAdded = false;


    public DownlinePurchaseAdapter(Context context) {
        //this.fragment1=fragmet;
        this.mContext = context;
        detailArrayList=new ArrayList<>();
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

    public ArrayList<DownlinePurchaseResponse.DownlinePurchaseItem> getList() {
        return detailArrayList;
    }

    public void setList(ArrayList<DownlinePurchaseResponse.DownlinePurchaseItem> list) {
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
        View v1 = inflater.inflate(R.layout.downline_purchase_item_layout, parent, false);
        viewHolder = new MyViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        DownlinePurchaseResponse.DownlinePurchaseItem downlineDetails = detailArrayList.get(position);
        switch (getItemViewType(position)) {
            case ITEM:

                MyViewHolder myViewHolder = (MyViewHolder) holder;

                final int pos=position;
                if(position % 2==0){
                    myViewHolder.cardViewItem.setBackgroundColor(ContextCompat.getColor(mContext,R.color.LightGray));
                    myViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
                }
                else {
                    myViewHolder.cardViewItem.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
                    myViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.LightGray));
                }
                myViewHolder.textViewSno.setText(detailArrayList.get(position).getSno());
                myViewHolder.textViewIdNo.setText(detailArrayList.get(position).getIdno());
                myViewHolder.textViewBillDate.setText(detailArrayList.get(position).getBilldate());
               // myViewHolder.textViewBillNo.setText(detailArrayList.get(position).getb());
                //myViewHolder.textViewPackage.setText(downlineRepurchaseList.get(position).getPkgname());
                myViewHolder.textViewMemberName.setText(detailArrayList.get(position).getMembername());
                myViewHolder.textViewLeg.setText(detailArrayList.get(position).getGroupname());
                myViewHolder.textViewBillamount.setText(detailArrayList.get(position).getBilamount());
                myViewHolder.textViewVRP.setText(detailArrayList.get(position).getRepurchasebv());
                //myViewHolder.textViewStatus.setText(detailArrayList.get(position).gets());
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

    public void add(DownlinePurchaseResponse.DownlinePurchaseItem mc) {
        detailArrayList.add(mc);
        notifyItemInserted(detailArrayList.size() - 1);
    }

    public void addAll(ArrayList<DownlinePurchaseResponse.DownlinePurchaseItem> mcList) {
        for (DownlinePurchaseResponse.DownlinePurchaseItem mc : mcList) {
            add(mc);
        }
    }

    public void remove(DownlinePurchaseResponse.DownlinePurchaseItem city) {
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
        add(new DownlinePurchaseResponse.DownlinePurchaseItem());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = detailArrayList.size() - 1;
        DownlinePurchaseResponse.DownlinePurchaseItem item = getItem(position);

        if (item != null) {
            detailArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public DownlinePurchaseResponse.DownlinePurchaseItem getItem(int position) {
        return detailArrayList.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewIdNo;
        public TextView textViewBillDate;
        public TextView textViewBillNo;
        public TextView textViewMemberName;
        public TextView textViewLeg;
        public TextView textViewBillamount;
        public TextView textViewVRP;
        public TextView textViewPackage;
        public TextView textViewSno;

        public TextView textViewStatus;
        public TextView Sno;
        public CardView cardViewItem;


        public MyViewHolder(View view) {
            super(view);

            textViewIdNo = (TextView) view.findViewById(R.id.prchase_downline_item_txtView_idno);
            textViewSno = (TextView) view.findViewById(R.id.prchase_downline_item_txtView_sno);
            textViewBillDate = (TextView) view.findViewById(R.id.prchase_downline_item_txtView_billdate);
            textViewBillNo = (TextView) view.findViewById(R.id.prchase_downline_item_txtView_billno);
            textViewMemberName = (TextView) view.findViewById(R.id.prchase_downline_item_txtView_member);
            textViewVRP = (TextView) view.findViewById(R.id.prchase_downline_item_txtView_bv);
            textViewLeg = (TextView) view.findViewById(R.id.prchase_downline_item_txtView_leg);
            textViewBillamount = (TextView) view.findViewById(R.id.prchase_downline_item_txtView_bill_amt);
            textViewStatus = (TextView) view.findViewById(R.id.prchase_downline_item_txtView_status);
            textViewPackage = (TextView) view.findViewById(R.id.prchase_downline_item_txtView_packgeNme);
            cardViewItem=(CardView)view.findViewById(R.id.prchase_downline_item_cardView_item);
        }

        protected void clear() {

        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}
