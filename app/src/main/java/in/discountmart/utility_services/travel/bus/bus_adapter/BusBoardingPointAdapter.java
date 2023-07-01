package in.discountmart.utility_services.travel.bus.bus_adapter;

import android.content.Context;
import android.os.Build;
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


public class BusBoardingPointAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Context mContext;
    public List<BusCityListResponse> contactList;
    int selectedPosition=-1;

    private List<BusSearchListResponse.BoardingPoints> boardPointsArrayList;

    public BusBoardingPointAdapter(Context context, ArrayList<BusSearchListResponse.BoardingPoints> dropingPointsList) {
        this.mContext = context;
        this.boardPointsArrayList=dropingPointsList;

    }

    public void setBoardPointData(ArrayList<BusSearchListResponse.BoardingPoints> dropingPointsList){

        this.boardPointsArrayList=dropingPointsList;
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
            dropHolder.address.setText(boardPointsArrayList.get(position).getAddresss());
            dropHolder.landmark.setText(boardPointsArrayList.get(position).getLandMark());
            dropHolder.date.setText(boardPointsArrayList.get(position).getBoardingTime());

            //BusSharedValues.getInstance().busDropPoint="";
            //BusSharedValues.getInstance().busBoardPoint="";

            dropHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPosition = position;
                    //notifyItemChanged(selectedPosition);
                    notifyDataSetChanged();
                    BusBoarding_DropPointActivity.boardingPointsList=new ArrayList<BusSearchListResponse.BoardingPoints>(Collections.singleton(boardPointsArrayList.get(selectedPosition)));
                    BusSharedValues.getInstance().busBoardPoint=boardPointsArrayList.get(selectedPosition).getBpID();

                    if(!BusSharedValues.getInstance().busBoardPoint.equals("") &&
                        BusSharedValues.getInstance().busDropPoint.equals("")){

                        BusBoarding_DropPointActivity.layoutcontinue.setVisibility(View.GONE);
                        BusBoarding_DropPointActivity.layoutBoarding.setVisibility(View.GONE);
                        BusBoarding_DropPointActivity.layoutDrop.setVisibility(View.VISIBLE);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            BusBoarding_DropPointActivity.txtDropPoint.setBackground(mContext.getResources().getDrawable(R.drawable.colorprimary_bg_box));
                            BusBoarding_DropPointActivity.txtBoardPoint.setBackground(mContext.getResources().getDrawable(R.drawable.transparent_bg));
                            BusBoarding_DropPointActivity.txtBoardPoint.setTextColor(mContext.getResources().getColor(R.color.gray));
                            BusBoarding_DropPointActivity.txtDropPoint.setTextColor(mContext.getResources().getColor(R.color.white));

                        }


                    }
                    else if(!BusSharedValues.getInstance().busBoardPoint.equals("") &&
                            !BusSharedValues.getInstance().busDropPoint.equals("")) {

                        BusBoarding_DropPointActivity.layoutcontinue.setVisibility(View.VISIBLE);
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

        return boardPointsArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView address;
        public TextView landmark;
        public TextView date;

        public MyViewHolder(View view) {
            super(view);
            address = view.findViewById(R.id.board_drop_point_txt_address);
            landmark = view.findViewById(R.id.board_drop_point_txt_landmark);
            date = view.findViewById(R.id.board_drop_point_txt_date);

        }
    }
}
