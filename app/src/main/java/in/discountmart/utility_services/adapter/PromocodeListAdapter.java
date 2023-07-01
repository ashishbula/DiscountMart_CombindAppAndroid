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

public class PromocodeListAdapter extends RecyclerView.Adapter<PromocodeListAdapter.MyViewHolder>{

    public Context mContext;
    public List<PromocodeRespose> promocodeList;
    private PromoListAdapterListener listener;
    boolean mPromoUse;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtPromocode;
        public TextView txtStatus;
        public TextView txtValidity;
        public TextView txtValue;
        public TextView txtService;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            txtPromocode = view.findViewById(R.id.promo_list_item_txt_promocode);
            txtStatus = view.findViewById(R.id.promo_list_item_txt_status);
            txtValidity = view.findViewById(R.id.promo_list_item_txt_validity);
            txtValue = view.findViewById(R.id.promo_list_item_txt_value);
            txtService = view.findViewById(R.id.promo_list_item_txt_service);



        }
    }


    public PromocodeListAdapter(Context context, List<PromocodeRespose> promoList) {
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
                .inflate(R.layout.utility_promo_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final PromocodeRespose promo = promocodeList.get(position);

        holder.txtPromocode.setText(promocodeList.get(position).getVoucherCode());
        holder.txtService.setText(promocodeList.get(position).getService());
        holder.txtValidity.setText(promocodeList.get(position).getFromDate() + " - "+promocodeList.get(position).getToDate());
        holder.txtValue.setText(promocodeList.get(position).getAmount());
        holder.txtStatus.setText(promocodeList.get(position).getStatus());

        /* Glide.with(context)
                .load(contact.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);*/
    }

    @Override
    public int getItemCount() {
        return promocodeList.size();
    }


    public interface PromoListAdapterListener {
        void onPromoSelected(PromocodeRespose promocode);
    }
}
