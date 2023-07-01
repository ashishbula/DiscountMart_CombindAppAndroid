package in.discountmart.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import in.discountmart.R;
import in.discountmart.business.model_business.responsemodel.MonthSessionResponse;

import java.util.ArrayList;

public class DateSessionAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<MonthSessionResponse.MonthSession> sessionArrayList;

    Context mContext;

    public DateSessionAdapter(Context context, ArrayList<MonthSessionResponse.MonthSession> monthSessions) {
        super();
        this.mContext = context;
        this.sessionArrayList = monthSessions;

    }

    @Override
    public int getCount() {
        return sessionArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return sessionArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_list_item_layout, parent, false);
        MonthSessionResponse.MonthSession bankListRes= sessionArrayList.get(position);

       /* if(position % 2==0){
            itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
        }
        else {
            itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.appBar_color_light));
        }*/

        TextView textViewNameList = (TextView) itemView.findViewById(R.id.list_namelist);
        textViewNameList.setText(sessionArrayList.get(position).getYear());



        return itemView;
    }
}
