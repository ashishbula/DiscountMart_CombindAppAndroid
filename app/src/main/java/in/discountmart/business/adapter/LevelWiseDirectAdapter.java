package in.discountmart.business.adapter;

import android.content.Context;
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
import in.discountmart.business.fragment.DownlineFragmentGroupA;
import in.discountmart.business.fragment.DownlineFragmentGroupB;
import in.discountmart.business.model_business.responsemodel.LevelWiseReportResponse;

import java.util.ArrayList;

public class LevelWiseDirectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    ArrayList<LevelWiseReportResponse.LevelWiseReport> levelReportArrayList;
    private Context mContext;
    DownlineFragmentGroupA fragment1;
    DownlineFragmentGroupB fragment2;
    String mFlag;

    private boolean isLoadingAdded = false;


    public LevelWiseDirectAdapter(Context context,String flag) {
        //this.fragment1=fragmet;
        this.mContext = context;
        levelReportArrayList = new ArrayList<>();
        this.mFlag=flag;
        // weeklyIncenLists = list;
        //this.act=act;
        //this.fragment=frag;

    }


    public ArrayList<LevelWiseReportResponse.LevelWiseReport> getList() {
        return levelReportArrayList;
    }

    public void setList(ArrayList<LevelWiseReportResponse.LevelWiseReport> list) {
        this.levelReportArrayList = list;
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
        View v1 = inflater.inflate(R.layout.business_level_wise_direct_item, parent, false);
        viewHolder = new MyViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        LevelWiseReportResponse.LevelWiseReport leaderList = levelReportArrayList.get(position);
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
                myViewHolder.textViewSno.setText(levelReportArrayList.get(position).getSno());
                myViewHolder.textViewIdno.setText(levelReportArrayList.get(position).getIdno());
                myViewHolder.textViewMemName.setText(levelReportArrayList.get(position).getMembername());
                myViewHolder.txtSponsorId.setText(levelReportArrayList.get(position).getSponsorid());
                myViewHolder.txtSponsorName.setText(levelReportArrayList.get(position).getSponsorname());
                myViewHolder.textViewPackage.setText(levelReportArrayList.get(position).getPackagename());
                myViewHolder.textViewStatus.setText(levelReportArrayList.get(position).getActive());
                myViewHolder.textViewActiveDate.setText(levelReportArrayList.get(position).getUpgradedate());
                myViewHolder.textViewtotbv.setText(levelReportArrayList.get(position).getBv());

                if(mFlag.equals("act")){

                    myViewHolder.layoutBV.setVisibility(View.VISIBLE);
                    myViewHolder.layoutStatus.setVisibility(View.VISIBLE);
                    myViewHolder.layoutPkg.setVisibility(View.VISIBLE);
                    myViewHolder.layoutDate.setVisibility(View.VISIBLE);
                }
                else {
                    myViewHolder.layoutBV.setVisibility(View.GONE);
                    myViewHolder.layoutStatus.setVisibility(View.GONE);
                    myViewHolder.layoutPkg.setVisibility(View.GONE);
                    myViewHolder.layoutDate.setVisibility(View.GONE);
                }

                break;
            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return levelReportArrayList == null ? 0 : levelReportArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == levelReportArrayList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(LevelWiseReportResponse.LevelWiseReport mc) {
        levelReportArrayList.add(mc);
        notifyItemInserted(levelReportArrayList.size() - 1);
    }

    public void addAll(ArrayList<LevelWiseReportResponse.LevelWiseReport> mcList) {
        for (LevelWiseReportResponse.LevelWiseReport mc : mcList) {
            add(mc);
        }
    }

    public void remove(LevelWiseReportResponse.LevelWiseReport list) {
        int position = levelReportArrayList.indexOf(list);
        if (position > -1) {
            levelReportArrayList.remove(position);
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
        add(new LevelWiseReportResponse.LevelWiseReport());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = levelReportArrayList.size() - 1;
        LevelWiseReportResponse.LevelWiseReport item = getItem(position);

        if (item != null) {
            levelReportArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public LevelWiseReportResponse.LevelWiseReport getItem(int position) {
        return levelReportArrayList.get(position);
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
        public TextView textViewLevel;
        public TextView textViewIdno;
        public TextView textViewMemName;
        public TextView textViewPackage;
        public TextView textViewStatus;
        public TextView txtSponsorId;
        public TextView txtSponsorName;
        public TextView textViewActiveDate;
        public TextView textViewtotbv;
        public CardView cardViewItem;
        public LinearLayout layoutBV;
        public LinearLayout layoutPkg;
        public LinearLayout layoutStatus;
        public LinearLayout layoutDate;


        public TextView Status;
        public TextView Sno;


        public MyViewHolder(View view) {
            super(view);
            textViewSno = (TextView) view.findViewById(R.id.level_direct_item_txt_sno);
            textViewLevel = (TextView) view.findViewById(R.id.level_direct_item_txt_level);
            textViewIdno = (TextView) view.findViewById(R.id.level_direct_item_txt_idno);
            textViewMemName = (TextView) view.findViewById(R.id.level_direct_item_txt_mem_name);
            textViewPackage = (TextView) view.findViewById(R.id.level_direct_item_txt_pckg);
            textViewStatus = (TextView) view.findViewById(R.id.level_direct_item_txt_status);
            txtSponsorId = (TextView) view.findViewById(R.id.level_direct_item_txt_sponsor_id);
            textViewActiveDate = (TextView) view.findViewById(R.id.level_direct_item_txt_activeDate);
            txtSponsorName = (TextView) view.findViewById(R.id.level_direct_item_txt_sponsor_name);;
            textViewtotbv = (TextView) view.findViewById(R.id.level_direct_item_txt_bv);
            cardViewItem=(CardView)view.findViewById(R.id.level_direct_item_cardView_item);
            layoutBV=(LinearLayout)view.findViewById(R.id.level_direct_item_layout_bv);
            layoutPkg=(LinearLayout)view.findViewById(R.id.level_direct_item_layout_pkg);
            layoutStatus=(LinearLayout)view.findViewById(R.id.level_direct_item_layout_status);
            layoutDate=(LinearLayout)view.findViewById(R.id.level_direct_item_layout_date);



        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}