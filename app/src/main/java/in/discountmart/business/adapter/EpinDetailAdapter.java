package in.discountmart.business.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.business.activity.BusinessDashboardActivity;
import in.discountmart.business.business_constants.AppConstants;
import in.discountmart.business.fragment.DownlineFragmentGroupA;
import in.discountmart.business.fragment.DownlineFragmentGroupB;
import in.discountmart.business.fragment.TopupFragment;
import in.discountmart.business.model_business.responsemodel.EpinDetailResponse;

public class EpinDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    ArrayList<EpinDetailResponse.EpinDetail> detailArrayList;
    private Context mContext;
    DownlineFragmentGroupA fragment1;
    DownlineFragmentGroupB fragment2;

    private boolean isLoadingAdded = false;


    public EpinDetailAdapter(Context context) {
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

    public ArrayList<EpinDetailResponse.EpinDetail> getList() {
        return detailArrayList;
    }

    public void setList(ArrayList<EpinDetailResponse.EpinDetail> list) {
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
        View v1 = inflater.inflate(R.layout.business_epin_detial_item, parent, false);
        viewHolder = new MyViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        EpinDetailResponse.EpinDetail downlineDetails = detailArrayList.get(position);
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
                //myViewHolder.textViewSno.setText(epinDetailList.get(position).getScratchno());
                myViewHolder.textViewPinno.setText(detailArrayList.get(position).getPinno());
                myViewHolder.textViewProductNme.setText(detailArrayList.get(position).getProductname());
                myViewHolder.textViewEpinStatus.setText(detailArrayList.get(position).getEpinstatus());
                myViewHolder.textViewIssuedDate.setText(detailArrayList.get(position).getIssuedate());

                if(detailArrayList.get(position).getUsedby().equals("")){

                    myViewHolder.layoutUsedBy.setVisibility(View.GONE);
                }
                else {
                    myViewHolder.layoutUsedBy.setVisibility(View.VISIBLE);
                    myViewHolder.textViewEpinUsedBy.setText(detailArrayList.get(position).getUsedby());
                }

                if(detailArrayList.get(position).getUseddate().equals("")){

                    myViewHolder.layoutUsedDate.setVisibility(View.GONE);
                }
                else {
                    myViewHolder.layoutUsedDate.setVisibility(View.VISIBLE);
                    myViewHolder.textViewUsedDAte.setText(detailArrayList.get(position).getUseddate());
                }

                if(detailArrayList.get(position).getUseddate().equals("")){

                    myViewHolder.layoutName.setVisibility(View.GONE);
                }
                else {
                    myViewHolder.layoutName.setVisibility(View.VISIBLE);
                    myViewHolder.textViewName.setText(detailArrayList.get(position).getMname());
                }


                if(detailArrayList.get(position).getEpinstatus().equals("Used")){
                    LinearLayout.LayoutParams lay = (LinearLayout.LayoutParams) myViewHolder.cardViewItem.getLayoutParams();
                /*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.weight = 1.0f;*/
                    lay.weight=1;


                    myViewHolder.linearLayoutButton.setVisibility(View.GONE);
                    myViewHolder.linearLayoutItem.setWeightSum(1);
                    myViewHolder.cardViewItem.setLayoutParams(lay);
                }
                else {
                    LinearLayout.LayoutParams lay = (LinearLayout.LayoutParams) myViewHolder.cardViewItem.getLayoutParams();
                /*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.weight = 1.0f;*/
                    lay.weight=1.6f;


                    myViewHolder.linearLayoutButton.setVisibility(View.VISIBLE);
                    myViewHolder.linearLayoutItem.setWeightSum(2);
                    myViewHolder.cardViewItem.setLayoutParams(lay);

                    LinearLayout.LayoutParams lay1 =
                            (LinearLayout.LayoutParams) myViewHolder.linearLayoutButton.getLayoutParams();
                    lay1.weight=0.4f;
                    myViewHolder.linearLayoutButton.setWeightSum(2);
                    myViewHolder.textViewViewProdt.setVisibility(View.VISIBLE);

                }
               /* myViewHolder.textViewViewProdt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    PinWiseProductDetailFragment fragment=new PinWiseProductDetailFragment();
                    Bundle pinBundle= new Bundle();
                    pinBundle.clear();
                    if(pinBundle.isEmpty()){
                        pinBundle.putString("PinNo",epinDetailList.get(position).getPinno());
                        fragment.setArguments(pinBundle);
                        ((BusinessMainActivity)mContext).replaceFragment(fragment,
                                AppConstants.TAG_PIN_WISE_PRODUCT_DETAIL,null);
                    }
                   else {
                        Toast.makeText(mContext,"No Detail Found",Toast.LENGTH_SHORT).show();
                    }

                    }
                });*/

                myViewHolder.textViewTopup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(mContext,,Toast.LENGTH_SHORT).show();

                        TopupFragment topupFragment=new TopupFragment();
                        Bundle bundleTopup=new Bundle();
                        bundleTopup.putString("PinNo",detailArrayList.get(pos).getPinno());
                        bundleTopup.putString("PkgType", AppConstants.PkgType);
                        bundleTopup.putString("ScracthNo",detailArrayList.get(pos).getScratchno());
                        topupFragment.setArguments(bundleTopup);

                        if(!bundleTopup.isEmpty()){
                            ((BusinessDashboardActivity)mContext).replaceFragment_business(topupFragment,
                                    AppConstants.TAG_TOPUPFRAGMENT,null);
                        }
                        else {
                            Toast.makeText(mContext,"Bundle Empty",Toast.LENGTH_SHORT).show();

                        }



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

    public void add(EpinDetailResponse.EpinDetail mc) {
        detailArrayList.add(mc);
        notifyItemInserted(detailArrayList.size() - 1);
    }

    public void addAll(ArrayList<EpinDetailResponse.EpinDetail> mcList) {
        for (EpinDetailResponse.EpinDetail mc : mcList) {
            add(mc);
        }
    }

    public void remove(EpinDetailResponse.EpinDetail city) {
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
        add(new EpinDetailResponse.EpinDetail());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = detailArrayList.size() - 1;
        EpinDetailResponse.EpinDetail item = getItem(position);

        if (item != null) {
            detailArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public EpinDetailResponse.EpinDetail getItem(int position) {
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
        public TextView textViewSno;
        public TextView textViewPinno;
        public TextView textViewName;
        public TextView textViewIssuedDate;
        public TextView textViewEpinStatus;
        public TextView textViewEpinUsedBy;
        public TextView textViewUsedDAte;
        public TextView textViewJoinNow;
        public TextView textViewTopup;
        public TextView textViewViewProdt;
        public TextView textViewProductNme;
        ImageView imageViewName;
        LinearLayout linearLayoutItem;
        LinearLayout linearLayoutButton;
        LinearLayout layoutUsedDate;
        LinearLayout layoutUsedBy;
        LinearLayout layoutName;

        CardView cardViewItem;
        public TextView Rank;
        public TextView Status;
        public TextView Sno;

        public MyViewHolder(View view) {
            super(view);

            textViewEpinStatus = (TextView) view.findViewById(R.id.epin_detail_item_txtView_epin_status);
            textViewEpinUsedBy = (TextView) view.findViewById(R.id.epin_detail_item_txtView_usedby);
            textViewIssuedDate = (TextView) view.findViewById(R.id.epin_detail_item_txtView_issueDate);
            textViewUsedDAte = (TextView) view.findViewById(R.id.epin_detail_item_txtView_used_date);
            textViewName = (TextView) view.findViewById(R.id.epin_detail_item_txtView_name);
            textViewPinno = (TextView) view.findViewById(R.id.epin_detail_item_txtView_pinno);
            textViewProductNme = (TextView) view.findViewById(R.id.epin_detail_item_txtView_pro_name);
            //textViewJoinNow = (TextView) view.findViewById(R.id.epin_detail_item_txtView_join);
            textViewTopup = (TextView) view.findViewById(R.id.epin_detail_item_txtView_topup);
            textViewViewProdt = (TextView) view.findViewById(R.id.epin_detail_item_txtView_view);
            linearLayoutItem=(LinearLayout)view.findViewById(R.id.epin_detail_item_liner_layout_item);
            linearLayoutButton=(LinearLayout)view.findViewById(R.id.epin_detail_item_liner_layout_button);
            layoutUsedDate=(LinearLayout)view.findViewById(R.id.epin_detail_item_layout_useddate);
            layoutUsedBy=(LinearLayout)view.findViewById(R.id.epin_detail_item_layout_usedby);
            layoutName=(LinearLayout)view.findViewById(R.id.epin_detail_item_layout_name);
            cardViewItem=(CardView)view.findViewById(R.id.epin_detail_item_cardView_item);
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
