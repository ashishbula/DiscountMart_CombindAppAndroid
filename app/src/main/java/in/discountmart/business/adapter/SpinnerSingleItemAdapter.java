package in.discountmart.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import in.discountmart.R;
import in.discountmart.business.model_business.SpinnerSingleItemModel;

import java.util.ArrayList;


public class SpinnerSingleItemAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<SpinnerSingleItemModel> stateListArrayList;
    Context mContext;

    public SpinnerSingleItemAdapter(Context context, ArrayList<SpinnerSingleItemModel> stateLists) {
        super();
        this.mContext = context;
        this.stateListArrayList = stateLists;

    }

    @Override
    public int getCount() {
        return stateListArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return stateListArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_single_text_item, parent, false);
        SpinnerSingleItemModel bankListRes= stateListArrayList.get(position);

       /* if(position % 2==0){
            itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
        }
        else {
            itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.appBar_color_light));
        }*/

        TextView textViewNameList = (TextView) itemView.findViewById(R.id.txt_name);
        textViewNameList.setText(stateListArrayList.get(position).getName());



        return itemView;
    }
}
