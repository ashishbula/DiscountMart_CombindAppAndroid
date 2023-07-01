package in.discountmart.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import in.discountmart.R;
import in.discountmart.business.model_business.responsemodel.PincodeDetailRespose;

import java.util.ArrayList;


public class PincodeAreaListAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<PincodeDetailRespose.PincodeDetail> areaArrayList;
    Context mContext;

    public PincodeAreaListAdapter(Context context, ArrayList<PincodeDetailRespose.PincodeDetail> aeraList) {
        super();
        this.mContext = context;
        this.areaArrayList = aeraList;

    }

    @Override
    public int getCount() {
        return areaArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return areaArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_list_item_layout, parent, false);
        PincodeDetailRespose.PincodeDetail bankListRes= areaArrayList.get(position);
       /* if(position % 2==0){

            itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
        }
        else {

            itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.gray5));
        }*/

        TextView textViewNameList = (TextView) itemView.findViewById(R.id.list_namelist);
        textViewNameList.setText(areaArrayList.get(position).getAreaname());



        return itemView;
    }
}
