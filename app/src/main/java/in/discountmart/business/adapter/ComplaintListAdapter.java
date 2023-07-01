package in.discountmart.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import in.discountmart.R;
import in.discountmart.business.model_business.responsemodel.ComplaintTypeListResponse;

import java.util.ArrayList;

public class ComplaintListAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<ComplaintTypeListResponse.ComplaintList> complaintListArrayList;
    Context mContext;

    public ComplaintListAdapter(Context context, ArrayList<ComplaintTypeListResponse.ComplaintList> complaintLists) {
        super();
        this.mContext = context;
        this.complaintListArrayList = complaintLists;

    }

    @Override
    public int getCount() {
        return complaintListArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return complaintListArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_list_item_layout, parent, false);
        ComplaintTypeListResponse.ComplaintList bankListRes= complaintListArrayList.get(position);
       /* if(position % 2==0){

            itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
        }
        else {

            itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.gray5));
        }*/

        TextView textViewNameList = (TextView) itemView.findViewById(R.id.list_namelist);
        textViewNameList.setText(complaintListArrayList.get(position).getComplaintname());



        return itemView;
    }
}
