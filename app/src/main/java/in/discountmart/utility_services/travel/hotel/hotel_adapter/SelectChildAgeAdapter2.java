package in.discountmart.utility_services.travel.hotel.hotel_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.discountmart.R;

public class SelectChildAgeAdapter2 extends RecyclerView.Adapter<SelectChildAgeAdapter2.MyViewHolder> {

    public Context mContext;
    public List<Integer> agaList;
    private ChildAgeAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtShort, phone;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            txtShort = view.findViewById(R.id.list_namelist);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onSelected(agaList.get(getAdapterPosition()));
                }
            });
        }
    }


    public SelectChildAgeAdapter2(Context context, List<Integer> shortList, ChildAgeAdapterListener listener) {
        this.mContext = context;
        this.listener = listener;
        this.agaList = shortList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.utility_list_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Integer promo = agaList.get(position);
        holder.txtShort.setText(String.valueOf(agaList.get(position)));


       /* Glide.with(context)
                .load(contact.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);*/
    }

    @Override
    public int getItemCount() {
        return agaList.size();
    }


    public interface ChildAgeAdapterListener {
        void onSelected(Integer item);
    }


}
