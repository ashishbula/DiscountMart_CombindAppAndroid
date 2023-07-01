package in.discountmart.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import in.discountmart.R;
import in.discountmart.business.fragment.DownlineFragmentGroupA;
import in.discountmart.business.fragment.DownlineFragmentGroupB;
import in.discountmart.business.model_business.responsemodel.DownlineDetailResponse;

import java.util.ArrayList;

public class DownlineDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    ArrayList<DownlineDetailResponse.DownlineDetails> downlineDetailsList;
    private Context mContext;
    DownlineFragmentGroupA fragment1;
    DownlineFragmentGroupB fragment2;

    private boolean isLoadingAdded = false;


    public DownlineDetailAdapter(Context context) {
        //this.fragment1=fragmet;
        this.mContext = context;
        downlineDetailsList=new ArrayList<>();
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

    public ArrayList<DownlineDetailResponse.DownlineDetails> getList() {
        return downlineDetailsList;
    }

    public void setList(ArrayList<DownlineDetailResponse.DownlineDetails> list) {
        this.downlineDetailsList = list;
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
        View v1 = inflater.inflate(R.layout.business_downline_item, parent, false);
        viewHolder = new MyViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        DownlineDetailResponse.DownlineDetails downlineDetails = downlineDetailsList.get(position);
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
                myViewHolder.textViewIDNo.setText(downlineDetailsList.get(position).getIdno());
                myViewHolder.textViewMemberName.setText(downlineDetailsList.get(position).getMember());
                myViewHolder.textViewPlacementId.setText(downlineDetailsList.get(position).getUplineid());
                myViewHolder.textViewSponsorID.setText(downlineDetailsList.get(position).getRefid());
                myViewHolder.textViewDateOfJoin.setText(downlineDetailsList.get(position).getDoj());
                myViewHolder.textViewPackage.setText(downlineDetailsList.get(position).getKitname());
                myViewHolder.textViewTopupDate.setText(downlineDetailsList.get(position).getUpgradedate());
                myViewHolder.textViewPackageMRP.setText(downlineDetailsList.get(position).getKitamount());
                myViewHolder.textViewPackageMRP.setText(downlineDetailsList.get(position).getKitamount());
                myViewHolder.textViewSno.setText(downlineDetailsList.get(position).getSno());
                myViewHolder.textViewBV.setText(downlineDetailsList.get(position).getBv());
                break;
            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return downlineDetailsList == null ? 0 : downlineDetailsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == downlineDetailsList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(DownlineDetailResponse.DownlineDetails mc) {
        downlineDetailsList.add(mc);
        notifyItemInserted(downlineDetailsList.size() - 1);
    }

    public void addAll(ArrayList<DownlineDetailResponse.DownlineDetails> mcList) {
        for (DownlineDetailResponse.DownlineDetails mc : mcList) {
            add(mc);
        }
    }

    public void remove(DownlineDetailResponse.DownlineDetails city) {
        int position = downlineDetailsList.indexOf(city);
        if (position > -1) {
            downlineDetailsList.remove(position);
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
        add(new DownlineDetailResponse.DownlineDetails());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = downlineDetailsList.size() - 1;
        DownlineDetailResponse.DownlineDetails item = getItem(position);

        if (item != null) {
            downlineDetailsList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public DownlineDetailResponse.DownlineDetails getItem(int position) {
        return downlineDetailsList.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewIDNo;
        TextView textViewMemberName;
        TextView textViewPlacementId;
        TextView textViewSponsorID;
        TextView textViewDateOfJoin;
        TextView textViewPackage;
        TextView textViewTopupDate;
        TextView textViewPackageMRP;
        TextView textViewBV;
        TextView textViewSno;
        LinearLayout cardViewItem;

        public MyViewHolder(View view) {
            super(view);

            textViewIDNo = (TextView) view.findViewById(R.id.txtrightdownid);
            textViewMemberName = (TextView) view.findViewById(R.id.downline_detail_memberName);
            textViewPlacementId = (TextView) view.findViewById(R.id.downline_detail_txtView_placementId);
            textViewSponsorID = (TextView) view.findViewById(R.id.downline_detail_txtView_sponsorId);
            textViewDateOfJoin = (TextView) view.findViewById(R.id.downline_detail_txtView_doj);
            textViewPackage = (TextView) view.findViewById(R.id.downline_detail_txtView_packge);
            textViewTopupDate = (TextView) view.findViewById(R.id.downline_detail_txtView_topupdate);
            textViewPackageMRP = (TextView) view.findViewById(R.id.downline_detail_txtView_pkgMrp);
            textViewBV = (TextView) view.findViewById(R.id.downline_detail_txtView_bv);
            textViewSno = (TextView) view.findViewById(R.id.downline_detail_sno1);
            cardViewItem=(LinearLayout) view.findViewById(R.id.downline_detail_item_cardView);
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
