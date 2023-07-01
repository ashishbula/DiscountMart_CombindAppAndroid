package in.discountmart.utility_services.recharge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.recharge.recharge_model.recharge_response_model.GetServiceProviderResponse;

public class ServiceProviderListAdapter extends RecyclerView.Adapter<ServiceProviderListAdapter.MyViewHolder> {

    public Context mContext;
    public ArrayList<GetServiceProviderResponse> serviceList;
    private ServiceProviderAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName, phone;
        public ImageView img;

        public MyViewHolder(View view) {
            super(view);
            txtName = view.findViewById(R.id.layout_content_txt);
            img = view.findViewById(R.id.layout_content_img);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onSelected(serviceList.get(getAdapterPosition()));
                }
            });
        }
    }


    public ServiceProviderListAdapter(Context context, ArrayList<GetServiceProviderResponse> list, ServiceProviderAdapterListener listener) {
        this.mContext = context;
        this.listener = listener;
        this.serviceList = list;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.utility_layout_content_img_txt_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final GetServiceProviderResponse promo = serviceList.get(position);
        holder.txtName.setText(serviceList.get(position).getCompanyName());
        //Picasso.with(mContext).load(serviceList.get(position).getHotelPicture()).into(myViewHolder.imgHotelImg);

        if(serviceList.get(position).getLogo().contentEquals("") && serviceList.get(position).getLogo() == null)
            Picasso.with(mContext).load(R.mipmap.utility_ic_prepaid).into(holder.img);
        else {
            Picasso.with(mContext).load(serviceList.get(position).getLogo()).into(holder.img);
        }


       /* Glide.with(context)
                .load(contact.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);*/
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }


    public interface ServiceProviderAdapterListener {
        void onSelected(GetServiceProviderResponse item);
    }
}
