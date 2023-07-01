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

import com.squareup.picasso.Picasso;
import in.discountmart.R;
import in.discountmart.business.fragment.DownlineFragmentGroupA;
import in.discountmart.business.fragment.DownlineFragmentGroupB;
import in.discountmart.business.model_business.responsemodel.PinTransferDetailReaponse;

import java.util.ArrayList;

public class PinTransferDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    ArrayList<PinTransferDetailReaponse.PinTransferDetail> detailArrayList;
    private Context mContext;
    DownlineFragmentGroupA fragment1;
    DownlineFragmentGroupB fragment2;

    private boolean isLoadingAdded = false;


    public PinTransferDetailAdapter(Context context) {
        //this.fragment1=fragmet;
        this.mContext = context;
        detailArrayList=new ArrayList<>();
        // weeklyIncenLists = list;
        //this.act=act;
        //this.fragment=frag;

    }


    public ArrayList<PinTransferDetailReaponse.PinTransferDetail> getList() {
        return detailArrayList;
    }

    public void setList(ArrayList<PinTransferDetailReaponse.PinTransferDetail> list) {
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
        View v1 = inflater.inflate(R.layout.business_pintransfer_detail_item, parent, false);
        viewHolder = new MyViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        PinTransferDetailReaponse.PinTransferDetail downlineDetails = detailArrayList.get(position);
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
                myViewHolder.txtSNo.setText(detailArrayList.get(position).getSno());
                myViewHolder.txtTransToId.setText(detailArrayList.get(position).getToidno());
                myViewHolder.txtTransToMember.setText(detailArrayList.get(position).getTomember());
                myViewHolder.txtPinNo.setText(detailArrayList.get(position).getPinno());
                myViewHolder.txtPackgeName.setText(detailArrayList.get(position).getKitname());
                myViewHolder.txtScratchNo.setText(detailArrayList.get(position).getScratchno());
                myViewHolder.txtPinStatus.setText(detailArrayList.get(position).getPinStatus());
                myViewHolder.txtPinDate.setText(detailArrayList.get(position).getPindate());
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

    public void add(PinTransferDetailReaponse.PinTransferDetail mc) {
        detailArrayList.add(mc);
        notifyItemInserted(detailArrayList.size() - 1);
    }

    public void addAll(ArrayList<PinTransferDetailReaponse.PinTransferDetail> mcList) {
        for (PinTransferDetailReaponse.PinTransferDetail mc : mcList) {
            add(mc);
        }
    }

    public void remove(PinTransferDetailReaponse.PinTransferDetail list) {
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
        add(new PinTransferDetailReaponse.PinTransferDetail());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = detailArrayList.size() - 1;
        PinTransferDetailReaponse.PinTransferDetail item = getItem(position);

        if (item != null) {
            detailArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public PinTransferDetailReaponse.PinTransferDetail getItem(int position) {
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


        public TextView txtTransToId;
        public TextView txtTransToMember;
        public TextView txtPackgeName;
        public TextView txtPinNo;
        public TextView txtSNo;
        public TextView txtScratchNo;
        public TextView txtPinStatus;
        public TextView txtPinDate;

        public CardView cardView;



        public TextView wishTime;

        Picasso.RequestTransformer transformer;

        public MyViewHolder(View view) {
            super(view);
            txtTransToId = (TextView) view.findViewById(R.id.pintransfer_detail_item_txtView_toidno);
            txtTransToMember = (TextView) view.findViewById(R.id.pintransfer_detail_item_txtView_tomember);
            txtPackgeName = (TextView) view.findViewById(R.id.pintransfer_detail_item_txtView_kitname);
            txtPinNo = (TextView) view.findViewById(R.id.pintransfer_detail_item_txtView_pinno);
            txtSNo = (TextView) view.findViewById(R.id.pintransfer_detail_item_txtView_sno);
            txtScratchNo = (TextView) view.findViewById(R.id.pintransfer_detail_item_txtView_scratchno);
            txtPinStatus = (TextView) view.findViewById(R.id.pintransfer_detail_item_txtView_pinstatus);
            txtPinDate = (TextView) view.findViewById(R.id.pintransfer_detail_item_txtView_pindate);


            cardView=(CardView)view.findViewById(R.id.pintransfer_detail_item_cardView_item);
            //linearLayoutView = (LinearLayout) view.findViewById(R.id.pin_request_detail_item_layout_view);



        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}
