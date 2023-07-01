package in.discountmart.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import in.discountmart.R;
import in.discountmart.business.model_business.responsemodel.IdProofListResponse;

import java.util.ArrayList;

public class IdProofAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<IdProofListResponse.IdProofList> idProofListArrayList;
    Context mContext;

    public IdProofAdapter(Context context, ArrayList<IdProofListResponse.IdProofList> idProofLists) {
        super();
        this.mContext = context;
        this.idProofListArrayList = idProofLists;

    }

    @Override
    public int getCount() {
        return idProofListArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return idProofListArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_list_item_layout, parent, false);
        IdProofListResponse.IdProofList bankListRes= idProofListArrayList.get(position);

        //View rowview = flater.inflate(R.layout.listitems_layout,null,true);
        //View view = mInflater.inflate(mResource, parent, false);
        // LayoutInflater inflater = ( mcontext.getLayoutInflater();
        //View row = inflater.inflate(R.layout.row, parent, false);

        TextView textViewNameList = (TextView) itemView.findViewById(R.id.list_namelist);
        textViewNameList.setText(idProofListArrayList.get(position).getIdtype());



        return itemView;
    }
}
