package in.discountmart.business.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import in.discountmart.R;
import in.discountmart.business.model_business.responsemodel.MyPurchaseDetailResponse;

import java.util.ArrayList;

public class MyPurchaseDetailAdapter extends RecyclerView.Adapter {
    @NonNull
    ArrayList<MyPurchaseDetailResponse.MyPurchaseDetail> detailArrayList;
    private Context mContext;
    private Resources mResources;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewReply;
        public TextView textViewReplyDate;
        public CardView cardViewItem;
        public TextView txtStatus;
        public TextView txtOrderNo;
        public TextView txtOrderAmount;
        public TextView txtOrderDate;
        public TextView txtRequest;
        public TextView txtShopWallet;
        public TextView txtCourier;
        public TextView txtDocketNo;
        public TextView txtDocketDate;
        public TextView txtBV;
        public TextView txtSno;

        public MyViewHolder(View view) {
            super(view);
            txtSno = (TextView) view.findViewById(R.id.mypurchase_detail_item_txt_sno);
            txtOrderNo = (TextView) view.findViewById(R.id.mypurchase_detail_item_txt_orderno);
            txtOrderDate = (TextView) view.findViewById(R.id.mypurchase_detail_item_txt_order_date);
            txtOrderAmount = (TextView) view.findViewById(R.id.mypurchase_detail_item_txt_order_amnt);
            txtRequest = (TextView) view.findViewById(R.id.mypurchase_detail_item_txt_request);
            txtShopWallet = (TextView) view.findViewById(R.id.mypurchase_detail_item_txt_shop_wallet);
            txtCourier = (TextView) view.findViewById(R.id.mypurchase_detail_item_txt_courier);
            txtDocketNo = (TextView) view.findViewById(R.id.mypurchase_detail_item_txt_docketno);
            txtDocketDate = (TextView) view.findViewById(R.id.mypurchase_detail_item_txt_docket_date);
            txtStatus = (TextView) view.findViewById(R.id.mypurchase_detail_item_txt_status);
            txtBV = (TextView) view.findViewById(R.id.mypurchase_detail_item_txt_bv);
            cardViewItem = (CardView) view.findViewById(R.id.mypurchase_detail_item_cardView_item);

        }
    }

    public MyPurchaseDetailAdapter(ArrayList<MyPurchaseDetailResponse.MyPurchaseDetail> arrayList, Context context) {
        this.detailArrayList = arrayList;
        this.mContext = context;
        //this.recyclerView = recyclerView;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh;
        View view = LayoutInflater.from(mContext).inflate(R.layout.business_mypurchase_detail_item, parent, false);
        vh= new MyViewHolder(view);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {

            MyPurchaseDetailResponse.MyPurchaseDetail leaderList = detailArrayList.get(position);
            MyViewHolder myViewHolder = (MyViewHolder) holder;

            try{
                if(position % 2==0){
                    myViewHolder.cardViewItem.setCardBackgroundColor(ContextCompat.getColor(mContext,R.color.LightGray));
                    myViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
                }
                else {
                    myViewHolder.cardViewItem.setCardBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
                    myViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.LightGray));
                }
                myViewHolder.txtSno.setText(detailArrayList.get(position).getSno());
                myViewHolder.txtOrderNo.setText(detailArrayList.get(position).getBillnoorderno());
                myViewHolder.txtOrderDate.setText(detailArrayList.get(position).getBilldate());
                myViewHolder.txtOrderAmount.setText(detailArrayList.get(position).getOrderamount());
                myViewHolder.txtRequest.setText(detailArrayList.get(position).getRequest());
                myViewHolder.txtShopWallet.setText(detailArrayList.get(position).getShopingwallet());
                //myViewHolder.txtCourier.setText(detailArrayList.get(position).getc);
                //myViewHolder.txtDocketNo.setText(detailArrayList.get(position).getReplydate());
               // myViewHolder.txtDocketDate.setText(detailArrayList.get(position).getReplydate());
                myViewHolder.txtStatus.setText(detailArrayList.get(position).getStatus());
                myViewHolder.txtBV.setText(detailArrayList.get(position).getBv());
            }catch (Exception e){
                e.printStackTrace();
            }




        }
    }

    @Override
    public int getItemCount() {
        return detailArrayList.size();
    }

}
