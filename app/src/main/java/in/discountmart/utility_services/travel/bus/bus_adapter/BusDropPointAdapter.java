package in.discountmart.utility_services.travel.bus.bus_adapter;

import android.content.Context;
import android.os.Build;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import in.discountmart.R;
import in.discountmart.utility_services.travel.bus.bus_activity.BusBoarding_DropPointActivity;
import in.discountmart.utility_services.travel.bus.bus_model.bus_response.BusCityListResponse;
import in.discountmart.utility_services.travel.bus.bus_model.bus_response.BusSearchListResponse;
import in.discountmart.utility_services.travel.bus.bus_sharedprference.BusSharedValues;


public class BusDropPointAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Context mContext;
    public List<BusCityListResponse> contactList;
    int pos;
    private int selectedPosition = -1;

    private List<BusSearchListResponse.DropingPoints> dropingPointsArrayList;

    public BusDropPointAdapter(Context context, ArrayList<BusSearchListResponse.DropingPoints> dropingPointsList) {
        this.mContext = context;
        this.dropingPointsArrayList=dropingPointsList;

    }

    public void setDropPointData(ArrayList<BusSearchListResponse.DropingPoints> dropingPointsList){

        this.dropingPointsArrayList=dropingPointsList;
        notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.utility_boaring_drop_point_item, parent, false);
        return new MyViewHolder(layoutView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if(holder instanceof MyViewHolder){

            final MyViewHolder dropHolder= (MyViewHolder)holder;
            dropHolder.address.setText(dropingPointsArrayList.get(position).getAddresss());
            dropHolder.landmark.setText(dropingPointsArrayList.get(position).getLandMark());
            dropHolder.date.setText(dropingPointsArrayList.get(position).getDroppingTime());

            pos=position;
//            BusSharedValues.getInstance().busDropPoint="";
//            BusSharedValues.getInstance().busBoardPoint="";

            //dropHolder.itemView.setSelected(dropingPointsArrayList.get(position));
            dropHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPosition = position;
                    //notifyItemChanged(selectedPosition);
                    notifyDataSetChanged();
                    BusBoarding_DropPointActivity.dropingPointsList=new ArrayList<BusSearchListResponse.DropingPoints>(Collections.singleton(dropingPointsArrayList.get(selectedPosition)));

                    BusSharedValues.getInstance().busDropPoint=dropingPointsArrayList.get(selectedPosition).getBpID();

                    if(!BusSharedValues.getInstance().busDropPoint.equals("") &&
                        ! BusSharedValues.getInstance().busBoardPoint.equals("")){

                            BusBoarding_DropPointActivity.layoutcontinue.setVisibility(View.VISIBLE);

                    }
                    else  if(!BusSharedValues.getInstance().busDropPoint.equals("") &&
                             BusSharedValues.getInstance().busBoardPoint.equals("")){
                        BusBoarding_DropPointActivity.layoutcontinue.setVisibility(View.GONE);
                        BusBoarding_DropPointActivity.layoutBoarding.setVisibility(View.VISIBLE);
                        BusBoarding_DropPointActivity.layoutDrop.setVisibility(View.GONE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            BusBoarding_DropPointActivity.txtDropPoint.setBackground(mContext.getResources().getDrawable(R.drawable.transparent_bg));
                            BusBoarding_DropPointActivity.txtBoardPoint.setBackground(mContext.getResources().getDrawable(R.drawable.colorprimary_bg_box));
                            BusBoarding_DropPointActivity.txtBoardPoint.setTextColor(mContext.getResources().getColor(R.color.white));
                            BusBoarding_DropPointActivity.txtDropPoint.setTextColor(mContext.getResources().getColor(R.color.gray));

                        }

                    }
                }
            });

            if(selectedPosition==position){
                dropHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
                //holder.tv1.setTextColor(Color.parseColor("#ffffff"));
            }
            else
            {
                dropHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }



        }
    }

    @Override
    public int getItemCount() {

        return dropingPointsArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView address;
        public TextView landmark;
        public TextView date;
        private SparseBooleanArray selectedItems = new SparseBooleanArray();

        public MyViewHolder(View view) {
            super(view);
            //view.setOnClickListener(this);
            address = view.findViewById(R.id.board_drop_point_txt_address);
            landmark = view.findViewById(R.id.board_drop_point_txt_landmark);
            date = view.findViewById(R.id.board_drop_point_txt_date);


        }


    }


}
