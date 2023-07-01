package in.discountmart.utility_services.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.model.NameListModel;

public class NameWithCheckboxListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<NameListModel> stList;
    Context mcontext;

    public NameWithCheckboxListAdapter(Context context,ArrayList<NameListModel> students) {
        this.stList = students;
        this.mcontext=context;

    }

    // Create new views
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                     int viewType) {
        // create a new view
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.utility_name_with_checkbox_item, parent, false);
        return new MyViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        final int pos = position;
        if(viewHolder instanceof MyViewHolder){
            final MyViewHolder myViewHolder= (MyViewHolder)viewHolder;

            myViewHolder.tvName.setText(stList.get(position).getName());

            myViewHolder.chkSelected.setChecked(stList.get(position).isSelected());

            myViewHolder.chkSelected.setTag(stList.get(position));

            myViewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    NameListModel contact = (NameListModel) cb.getTag();

                    contact.setSelected(cb.isChecked());
                    stList.get(pos).setSelected(cb.isChecked());

                    Toast.makeText(v.getContext(), "Clicked on Checkbox: " + cb.getText() + " is "
                            + cb.isChecked(), Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return stList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvEmailId;
        public CheckBox chkSelected;

        public NameListModel singlestudent;

        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvName = (TextView) itemLayoutView.findViewById(R.id.namelist_item_txt_name);
            chkSelected = (CheckBox) itemLayoutView.findViewById(R.id.namelist_item_checkbox_chk);
        }
    }

    // method to access in activity after updating selection
    public ArrayList<NameListModel> getNameList() {
        return stList;
    }
}
