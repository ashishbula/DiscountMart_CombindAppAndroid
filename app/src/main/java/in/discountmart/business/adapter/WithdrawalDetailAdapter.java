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
import in.discountmart.business.model_business.responsemodel.WithdrawalDetailResponse;

import java.util.ArrayList;

public class WithdrawalDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    ArrayList<WithdrawalDetailResponse.WithdrawalDetail> detailArrayList;
    private Context mContext;
    DownlineFragmentGroupA fragment1;
    DownlineFragmentGroupB fragment2;

    private boolean isLoadingAdded = false;


    public WithdrawalDetailAdapter(Context context) {
        //this.fragment1=fragmet;
        this.mContext = context;
        detailArrayList = new ArrayList<>();
        // weeklyIncenLists = list;
        //this.act=act;
        //this.fragment=frag;

    }


    public ArrayList<WithdrawalDetailResponse.WithdrawalDetail> getList() {
        return detailArrayList;
    }

    public void setList(ArrayList<WithdrawalDetailResponse.WithdrawalDetail> list) {
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
        View v1 = inflater.inflate(R.layout.withdrawal_detail_item, parent, false);
        viewHolder = new MyViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        WithdrawalDetailResponse.WithdrawalDetail leaderList = detailArrayList.get(position);
        switch (getItemViewType(position)) {
            case ITEM:


                MyViewHolder myViewHolder = (MyViewHolder) holder;
                if(position % 2==0){
                    myViewHolder.cardViewItem.setCardBackgroundColor(ContextCompat.getColor(mContext,R.color.LightGray));
                    myViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
                }
                else {
                    myViewHolder.cardViewItem.setCardBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
                    myViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.LightGray));
                }
                myViewHolder.txtReqId.setText(detailArrayList.get(position).getReqid());
                myViewHolder.txtPayeeName.setText(detailArrayList.get(position).getPayeename());
                myViewHolder.txtAcNo.setText(detailArrayList.get(position).getAccountno());
                myViewHolder.txtBankName.setText(detailArrayList.get(position).getBankname());
                myViewHolder.txtBranchName.setText(detailArrayList.get(position).getBranchname());
                myViewHolder.txtIFSC.setText(detailArrayList.get(position).getIfsccode());
                myViewHolder.txtReqAmount.setText(detailArrayList.get(position).getReqamount());
                myViewHolder.txtTDSAmont.setText(detailArrayList.get(position).getTdsamount());
                myViewHolder.txtBankService.setText(detailArrayList.get(position).getBankservice());
                myViewHolder.txtNetAmount.setText(detailArrayList.get(position).getNetamount());
                myViewHolder.txtReqDate.setText(detailArrayList.get(position).getReqdate());
                myViewHolder.txtIssueDate.setText(detailArrayList.get(position).getIssuedate());
                myViewHolder.txtStatus.setText(detailArrayList.get(position).getStatus());
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

    public void add(WithdrawalDetailResponse.WithdrawalDetail mc) {
        detailArrayList.add(mc);
        notifyItemInserted(detailArrayList.size() - 1);
    }

    public void addAll(ArrayList<WithdrawalDetailResponse.WithdrawalDetail> mcList) {
        for (WithdrawalDetailResponse.WithdrawalDetail mc : mcList) {
            add(mc);
        }
    }

    public void remove(WithdrawalDetailResponse.WithdrawalDetail list) {
        int position = detailArrayList.indexOf(list);
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
        add(new WithdrawalDetailResponse.WithdrawalDetail());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = detailArrayList.size() - 1;
        WithdrawalDetailResponse.WithdrawalDetail item = getItem(position);

        if (item != null) {
            detailArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public WithdrawalDetailResponse.WithdrawalDetail getItem(int position) {
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
        public TextView txtReqId;
        public TextView txtPayeeName;
        public TextView txtAcNo;
        public TextView txtBankName;
        public TextView txtBranchName;
        public TextView txtIFSC;
        public TextView txtReqAmount;
        public TextView txtTDSAmont;
        public TextView txtBankService;
        public TextView txtNetAmount;
        public TextView txtReqDate;
        public TextView txtIssueDate;
        public TextView txtStatus;

        public CardView cardViewItem;

        public TextView Status;
        public TextView Sno;

        public MyViewHolder(View view) {
            super(view);
            txtReqId = (TextView) view.findViewById(R.id.withdrawal_item_txt_req_id);
            txtPayeeName = (TextView) view.findViewById(R.id.withdrawal_item_txt_payee_name);
            txtAcNo = (TextView) view.findViewById(R.id.withdrawal_item_txt_ac_no);
            txtBankName = (TextView) view.findViewById(R.id.withdrawal_item_txt_bank_name);
            txtBranchName = (TextView) view.findViewById(R.id.withdrawal_item_txt_branch_name);
            txtIFSC = (TextView) view.findViewById(R.id.withdrawal_item_txt_ifsc);
            txtReqAmount = (TextView) view.findViewById(R.id.withdrawal_item_txt_req_amount);
            txtTDSAmont = (TextView) view.findViewById(R.id.withdrawal_item_txt_tds_amount);
            txtBankService = (TextView) view.findViewById(R.id.withdrawal_item_txt_bank_service);;
            txtNetAmount = (TextView) view.findViewById(R.id.withdrawal_item_txt_net_amnt);
            txtReqDate = (TextView) view.findViewById(R.id.withdrawal_item_txt_req_date);
            txtIssueDate = (TextView) view.findViewById(R.id.withdrawal_item_txt_issue_date);
            txtStatus = (TextView) view.findViewById(R.id.withdrawal_item_txt_status);
            cardViewItem=(CardView)view.findViewById(R.id.withdrawal_item_cardView_item);

        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}