package in.discountmart.business.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import in.discountmart.R;
import in.discountmart.business.activity.WebViewActivity;
import in.discountmart.business.fragment.DownlineFragmentGroupA;
import in.discountmart.business.fragment.DownlineFragmentGroupB;
import in.discountmart.business.model_business.responsemodel.DailyIncentiveResponse;

import java.util.ArrayList;

public class DailyIncentiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    ArrayList<DailyIncentiveResponse.DailyIncentive> detailArrayList;
    private Context mContext;
    DownlineFragmentGroupA fragment1;
    DownlineFragmentGroupB fragment2;

    private boolean isLoadingAdded = false;


    public DailyIncentiveAdapter(Context context) {
        //this.fragment1=fragmet;
        this.mContext = context;
        detailArrayList=new ArrayList<>();
        // weeklyIncenLists = list;
        //this.act=act;
        //this.fragment=frag;

    }


    public ArrayList<DailyIncentiveResponse.DailyIncentive> getList() {
        return detailArrayList;
    }

    public void setList(ArrayList<DailyIncentiveResponse.DailyIncentive> list) {
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
        View v1 = inflater.inflate(R.layout.business_daily_incentive_item, parent, false);
        viewHolder = new MyViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        DailyIncentiveResponse.DailyIncentive downlineDetails = detailArrayList.get(position);
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
                myViewHolder.txtPayoutDate.setText(detailArrayList.get(position).getPayoutdate());
                myViewHolder.txtMatchIncome.setText(detailArrayList.get(position).getMatchingbonus());
                myViewHolder.txtDirectIncome.setText(detailArrayList.get(position).getDirectincome());
                myViewHolder.txtSponsorLevelIncome.setText(detailArrayList.get(position).getSponsorlevelincome());
                myViewHolder.txtRoyltyIncome.setText(detailArrayList.get(position).getRoyaltyincome());
                myViewHolder.txtGrossIncome.setText(detailArrayList.get(position).getGrossincome());
                myViewHolder.txtTDSAmnt.setText(detailArrayList.get(position).getTdsamount());
                myViewHolder.txtAdminCharge.setText(detailArrayList.get(position).getAdmincharge());
               // myViewHolder.txtSocialCharge.setText(detailArrayList.get(position).getAdmincharge());
                myViewHolder.txtTotDeduction.setText(detailArrayList.get(position).getDeduction());
                myViewHolder.txtPreBal.setText(detailArrayList.get(position).getPrevious());
                myViewHolder.txtNetIncome.setText(detailArrayList.get(position).getNetamount());
                myViewHolder.txtClosingBal.setText(detailArrayList.get(position).getClosingbal());

                myViewHolder.layoutStatement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(mContext, WebViewActivity.class);
                        i.putExtra("URL", detailArrayList.get(position).getUrl());
                        i.putExtra("Type","Statement");
                        mContext.startActivity(i);
                        //mContext.getApplicationContext().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_right);
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

    public void add(DailyIncentiveResponse.DailyIncentive mc) {
        detailArrayList.add(mc);
        notifyItemInserted(detailArrayList.size() - 1);
    }

    public void addAll(ArrayList<DailyIncentiveResponse.DailyIncentive> mcList) {
        for (DailyIncentiveResponse.DailyIncentive mc : mcList) {
            add(mc);
        }
    }

    public void remove(DailyIncentiveResponse.DailyIncentive city) {
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
        add(new DailyIncentiveResponse.DailyIncentive());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = detailArrayList.size() - 1;
        DailyIncentiveResponse.DailyIncentive item = getItem(position);

        if (item != null) {
            detailArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public DailyIncentiveResponse.DailyIncentive getItem(int position) {
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

        public TextView textViewSNo;
        public TextView txtPayoutDate;
        public TextView txtMatchIncome;
        public TextView txtDirectIncome;
        public TextView txtSponsorLevelIncome;
        public TextView txtRoyltyIncome;
        public TextView txtGrossIncome;
        public TextView txtTDSAmnt;
        public TextView txtAdminCharge;
        public TextView txtSocialCharge;
        public TextView txtTotDeduction;
        public TextView txtPreBal;
        public TextView txtNetIncome;
        public TextView txtClosingBal;

        public TextView txtPromotionalIncome;

        public LinearLayout layoutStatement;
        public CardView cardViewItem;
        public TextView Status;
        public TextView Sno;


        public MyViewHolder(View view) {
            super(view);
            txtPayoutDate = (TextView) view.findViewById(R.id.daily_incentive_item_txt_payoutDate);
            txtMatchIncome = (TextView) view.findViewById(R.id.daily_incentive_item_txt_match_pair);
            txtDirectIncome = (TextView) view.findViewById(R.id.daily_incentive_item_txt_dir_income);
            txtSponsorLevelIncome = (TextView) view.findViewById(R.id.daily_incentive_item_txt_sponsr_lvel_income);
            txtRoyltyIncome = (TextView) view.findViewById(R.id.daily_incentive_item_txt_royalty_income);
            txtGrossIncome = (TextView) view.findViewById(R.id.daily_incentive_item_txt_gross);
            txtTDSAmnt = (TextView) view.findViewById(R.id.daily_incentive_item_txt_tds);
            txtAdminCharge = (TextView) view.findViewById(R.id.daily_incentive_item_txt_admin_charge);
            //txtSocialCharge = (TextView) view.findViewById(R.id.weekly_incentive_item_txt_social_charge);
            txtTotDeduction = (TextView) view.findViewById(R.id.daily_incentive_item_txt_deduction);
            txtPreBal = (TextView) view.findViewById(R.id.daily_incentive_item_txtView_pre_bal);
            //txtRoyltyIncome = (TextView) view.findViewById(R.id.weekly_incentive_item_txt_repurchase_deduct);
            txtNetIncome = (TextView) view.findViewById(R.id.daily_incentive_item_txt_newIncome);
            txtClosingBal = (TextView) view.findViewById(R.id.daily_incentive_item_txt_closing_bal);
            cardViewItem=(CardView)view.findViewById(R.id.daily_incentive_item_cardView_item);
            layoutStatement=(LinearLayout)view.findViewById(R.id.daily_incentive_item_layout_statement);



        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}
