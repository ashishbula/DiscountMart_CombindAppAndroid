package in.discountmart.utility_services.recharge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.recharge.recharge_model.recharge_response_model.RechargePlanDetail;

public class RechargePlanDetailAdapter extends RecyclerView.Adapter<RechargePlanDetailAdapter.MyViewHolder> {

    public Context mContext;
    public ArrayList<RechargePlanDetail> serviceList;
    private PlanDetalListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtPlanDetail;
        public TextView txtValue;
        public TextView txtValidity;
        public ImageView img;

        public MyViewHolder(View view) {
            super(view);
            txtPlanDetail = view.findViewById(R.id.plan_detail_item_txt_detail);
            txtValue = view.findViewById(R.id.plan_detail_item_txt_amount);
            txtValidity = view.findViewById(R.id.plan_detail_item_txt_plan_validity);
            //img = view.findViewById(R.id.layout_content_img);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onSelected(serviceList.get(getAdapterPosition()));
                }
            });
        }
    }


    public RechargePlanDetailAdapter(Context context, ArrayList<RechargePlanDetail> list, PlanDetalListener listener) {
        this.mContext = context;
        this.listener = listener;
        this.serviceList = list;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recharge_plan_detail_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final RechargePlanDetail promo = serviceList.get(position);
        holder.txtPlanDetail.setText(serviceList.get(position).getRecharge_description());
        holder.txtValue.setText(mContext.getResources().getString(R.string.rs_symbol)+""+serviceList.get(position).getRecharge_value());
        holder.txtValidity.setText("Validity : "+serviceList.get(position).getRecharge_validity());
        if(serviceList.get(position).getRecharge_validity().equals("") ||
            serviceList.get(position).getRecharge_validity().equals("-"))
            holder.txtValidity.setVisibility(View.GONE);
        else
            holder.txtValidity.setVisibility(View.VISIBLE);


    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }


    public interface PlanDetalListener {
        void onSelected(RechargePlanDetail item);
    }
}
