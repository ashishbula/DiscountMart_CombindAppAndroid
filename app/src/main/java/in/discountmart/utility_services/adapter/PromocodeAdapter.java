package in.discountmart.utility_services.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.discountmart.R;
import in.discountmart.utility_services.model.response_model.PromocodeRespose;

public class PromocodeAdapter extends RecyclerView.Adapter<PromocodeAdapter.MyViewHolder>{

    public Context mContext;
    public List<PromocodeRespose> promocodeList;
    private PromoCodeAdapterListener listener;
    boolean mPromoUse;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtPromocode;
        public TextView txtStatus;
        public TextView txtApply;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            txtPromocode = view.findViewById(R.id.promocode_item_txt_promo);
            txtStatus = view.findViewById(R.id.promocode_item_txt_status);
            txtApply = view.findViewById(R.id.promocode_item_txt_apply);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                   /* if(mPromoUse){
                        view.setEnabled(false);
                        view.setFocusable(false);
                    }
                    else {
                        view.setFocusable(true);
                        view.setEnabled(true);

                    }*/

                    listener.onPromoSelected(promocodeList.get(getAdapterPosition()));

                }
            });
        }
    }


    public PromocodeAdapter(Context context, List<PromocodeRespose> promoList, PromoCodeAdapterListener listener) {
        this.mContext = context;
        this.listener = listener;
        this.promocodeList = promoList;

    }

    public void setPromoVerify(boolean promoUse,Context context){
        this.mContext=context;
        this.mPromoUse=promoUse;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.utility_promocode_item_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final PromocodeRespose promo = promocodeList.get(position);

        if(mPromoUse){
            holder.itemView.setEnabled(true);
            holder.itemView.setClickable(true);
            holder.itemView.setFocusable(true);
            //holder.itemView.getRootView().setEnabled(false);


        }
        else {
            holder.itemView.setEnabled(false);
            holder.itemView.setClickable(false);
            holder.itemView.setFocusable(false);
        }

        if(promocodeList.get(position).getStatus().contentEquals("Used")){
            holder.txtPromocode.setText(promocodeList.get(position).getVoucherCode());
            holder.txtStatus.setText("Already Used");
            holder.txtApply.setText("Don't Apply");


        }
        else {

            holder.txtPromocode.setText(promocodeList.get(position).getVoucherCode());
            holder.txtStatus.setText("");
            holder.txtApply.setText("Apply");
        }


       /* Glide.with(context)
                .load(contact.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);*/
    }

    @Override
    public int getItemCount() {
        return promocodeList.size();
    }


    public interface PromoCodeAdapterListener {
        void onPromoSelected(PromocodeRespose promocode);
    }
}
