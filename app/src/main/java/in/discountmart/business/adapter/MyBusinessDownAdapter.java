package in.discountmart.business.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.discountmart.R;
import in.discountmart.business.activity.BusinessDashboardActivity;
import in.discountmart.business.fragment.MyDownBusinessNextFragment;
import in.discountmart.business.model_business.responsemodel.DownBusinessResponse;

import java.util.ArrayList;

public class MyBusinessDownAdapter extends RecyclerView.Adapter {


    ArrayList<DownBusinessResponse.DownBusiness> binaryIncomeArrayList;
    private Context mContext;
    private Resources mResources;

    ProgressBar imgProgressBar;

    RecyclerView recyclerView;
    String type;
    String fromDate;
    String toDate;
    //DesignationLeaderRes leaderRes=new DesignationLeaderRes();

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;


    int count;
    int from=0;
    int end=10;

    private int visibleThreshold = 5;
    private int previousTotal = 0;
    private int lastVisibleItem, totalItemCount;
    int visibleItemCount;
    private boolean isLoading;
    private OnLoadMoreListener onLoadMoreListener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewSNo;
        public TextView textViewMemberName;
        public TextView textViewMembId;
        public TextView textViewSponserId;
        public TextView textViewTotalBV;
        public TextView textViewLevel;
        public TextView textViewSelfBv;
        public TextView textViewGroupBv;
        public TextView textViewPrePV;
        public CardView imgDown;
        public LinearLayout layoutItem;

        public CardView cardViewItem;


        public TextView Status;
        public TextView Sno;


        public MyViewHolder(View view) {
            super(view);
            textViewMemberName = (TextView) view.findViewById(R.id.mybusiness_frag_txtview_membername);
            textViewMembId = (TextView) view.findViewById(R.id.mybusiness_frag_txtview_memberId);

            textViewSponserId = (TextView) view.findViewById(R.id.mybusiness_frag_txtview_sponserID);
            textViewTotalBV = (TextView) view.findViewById(R.id.mybusiness_frag_txtview_total);
            textViewLevel = (TextView) view.findViewById(R.id.mybusiness_frag_txtview_level);
            textViewSelfBv = (TextView) view.findViewById(R.id.mybusiness_frag_txtview_self);
            textViewGroupBv = (TextView) view.findViewById(R.id.mybusiness_frag_txtview_group);
            textViewPrePV = (TextView) view.findViewById(R.id.mybusiness_frag_txtview_prepv);
            imgDown = (CardView) view.findViewById(R.id.mybusiness_down_item_cardView_img);
           layoutItem=(LinearLayout)view.findViewById(R.id.mybusiness_down_item_Layout_item);
            cardViewItem=(CardView)view.findViewById(R.id.mybusiness_down_item_cardView);



        }
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        final public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        }
    }

    public MyBusinessDownAdapter(Context context, String type, String fromdate, String todate) {
        //this.designationLeadersList = designationLeadersList;
        this.mContext = context;
        //this.recyclerView = recyclerView;
        this.toDate=todate;
        this.fromDate=fromdate;
        this.type=type;

    }

    public void setData1(ArrayList<DownBusinessResponse.DownBusiness> binaryIncomes,Context context){
        this.binaryIncomeArrayList=binaryIncomes;
        this.mContext=context;
        isLoading=false;
    }
    public void setData(ArrayList<DownBusinessResponse.DownBusiness> binaryIncomes, RecyclerView recyclerView){
        binaryIncomeArrayList = binaryIncomes;
        notifyDataSetChanged();
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if(totalItemCount == 0 && visibleItemCount ==0){
                    isLoading=false;

                }
               /* else if(totalItemCount <= 10){
                    isLoading=false;
                }*/
                else {
                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                }

            }
        });




    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.mybusiness_down_item, parent, false);
            vh= new MyViewHolder(view);
            //return new MyViewHolder(view);
        }
        else  //(viewType == VIEW_TYPE_LOADING)
        {

            View view = LayoutInflater.from(mContext).inflate(R.layout.load_more_progressbar, parent, false);
            vh= new LoadingViewHolder(view);
            //return new LoadingViewHolder(view);
        }
        return vh;
    }

   /* @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rank_list_item, parent, false);

            return new MyViewHolder(itemView);
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more_progressbar, parent, false);
            return new LoadingViewHolder(itemView);
        }
        return null;
    }*/


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {

            DownBusinessResponse.DownBusiness leaderList = binaryIncomeArrayList.get(position);
            MyViewHolder myViewHolder = (MyViewHolder) holder;
           /* if(position % 2==0){
                myViewHolder.cardViewItem.setBackgroundColor(ContextCompat.getColor(mContext,R.color.appBar_color_light));
                myViewHolder.imgDown.setCardBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
                //myViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
            }
            else {
                myViewHolder.cardViewItem.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
                myViewHolder.imgDown.setCardBackgroundColor(ContextCompat.getColor(mContext,R.color.appBar_color1));
                //myViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.appBar_color_light));
            }*/
            //myViewHolder.textViewSno.setText(dailyIncentiveList.get(position).getScratchno());
            myViewHolder.textViewMemberName.setText(binaryIncomeArrayList.get(position).getMembername());
            myViewHolder.textViewMembId.setText(binaryIncomeArrayList.get(position).getIdno());
            myViewHolder.textViewSponserId.setText(binaryIncomeArrayList.get(position).getSponsorid());
            //myViewHolder.textViewDirectIncome.setText(binaryIncomeArrayList.get(position).getNetincome());
            myViewHolder.textViewTotalBV.setText(binaryIncomeArrayList.get(position).getTotalbv());
            myViewHolder.textViewLevel.setText(binaryIncomeArrayList.get(position).getLevel());
            myViewHolder.textViewSelfBv.setText(binaryIncomeArrayList.get(position).getSelfbv());
            myViewHolder.textViewGroupBv.setText(binaryIncomeArrayList.get(position).getGroupbv());
            myViewHolder.textViewPrePV.setText(binaryIncomeArrayList.get(position).getPreviousbv());

            myViewHolder.imgDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyDownBusinessNextFragment fragment=new MyDownBusinessNextFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("MemberID",binaryIncomeArrayList.get(position).getIdno());
                    bundle.putString("Type",type);
                    bundle.putString("FromDate",fromDate);
                    bundle.putString("ToDate",toDate);
                    fragment.setArguments(bundle);
                    ((BusinessDashboardActivity)mContext).replaceFragment_business(fragment,"MyDownBusinessNext",bundle);
                }
            });


            }
        else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;

            loadingViewHolder.progressBar.setIndeterminate(true);
        }



    }

    @Override
    public int getItemViewType(int position) {
        return binaryIncomeArrayList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setLoaded() {
        isLoading = false;
    }

    @Override
    public int getItemCount() {
        return binaryIncomeArrayList == null ? 0 : binaryIncomeArrayList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
