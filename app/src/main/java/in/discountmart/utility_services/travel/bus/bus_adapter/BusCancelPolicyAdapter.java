package in.discountmart.utility_services.travel.bus.bus_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.discountmart.R;
import in.discountmart.utility_services.travel.bus.bus_model.bus_response.BusCityListResponse;
import in.discountmart.utility_services.travel.bus.bus_model.bus_response.BusSearchListResponse;


public class BusCancelPolicyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Context mContext;
    public List<BusCityListResponse> contacList;
    int selectedPosition=-1;

    private List<BusSearchListResponse.CancellationCharge> cancellationArrayList;

    public BusCancelPolicyAdapter(Context context, ArrayList<BusSearchListResponse.CancellationCharge> cancelPointsList) {
        this.mContext = context;
        this.cancellationArrayList=cancelPointsList;

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.utility_bus_cancel_policy_item, parent, false);
        return new MyViewHolder(layoutView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if(holder instanceof MyViewHolder){

            final MyViewHolder dropHolder= (MyViewHolder)holder;
            dropHolder.cancelCharge.setText(cancellationArrayList.get(position).getCancelCharge());
            dropHolder.cancelTime.setText(cancellationArrayList.get(position).getCancelTime());

        }
    }

    @Override
    public int getItemCount() {

        return cancellationArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView cancelCharge;
        public TextView cancelTime;

        public MyViewHolder(View view) {
            super(view);
            cancelCharge = view.findViewById(R.id.layout_content_txt_cancelcharge);
            cancelTime = view.findViewById(R.id.layout_content_txt_canceltime);

        }
    }
}
