package in.discountmart.utility_services.travel.bus.bus_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.travel.bus.bus_activity.BusPassengerInfoActivity;
import in.discountmart.utility_services.travel.bus.bus_model.BusPassengerModel;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.ErrorMsgModel;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.PessengerInfo;

public class BusPassengerInfoAdapter extends RecyclerView.Adapter {

    private LayoutInflater inflater;
    static Context context;
    static BusPassengerInfoActivity activity;
    public static ArrayList<BusPassengerModel> pessengerInfosList;
    public  ArrayList<ErrorMsgModel> errorMsgList;

    int total_type;
    public static int yyFromDate ;
    public static int mmFromDate ;
    public static int ddFromDate ;
    public static String dob;
    public static int pos;

    TextView textView;
    public static class HeaderHolder extends RecyclerView.ViewHolder {

        LinearLayout layoutHeader;
        public HeaderHolder(@NonNull View itemHeaderView) {
            super(itemHeaderView);
            layoutHeader= itemHeaderView.findViewById(R.id.bus_passenger_item_layout_header);

        }
    }

    public static class ItemHolder extends RecyclerView.ViewHolder{

        EditText editName;

        EditText editAge;
        EditText editMobile;
        EditText editEmail;

        RadioGroup rdgGender;
        RadioButton rdbMale;
        RadioButton rdbFemale;
        TextView txtSeatName;
        TextView txtSeat;
        TextView txtError;

        LinearLayout layoutItemView;
        LinearLayout layoutHeader;


        public ItemHolder(View itemView) {
            super(itemView);

            editName = itemView.findViewById(R.id.bus_passenger_item_edtxt_name);
            editMobile = itemView.findViewById(R.id.bus_passenger_item_edtxt_mobile);
            editEmail = itemView.findViewById(R.id.bus_passenger_item_edtxt_email);
            editAge = itemView.findViewById(R.id.bus_passenger_item_edtxt_dob);

            txtSeatName= itemView.findViewById(R.id.bus_passenger_item_txt_seat_name);
            txtSeat= itemView.findViewById(R.id.bus_passenger_item_txt_seat);
            txtError= itemView.findViewById(R.id.bus_passenger_item_txt_error);

            rdgGender= itemView.findViewById(R.id.bus_passenger_item_rdg_gender);
            rdbFemale= itemView.findViewById(R.id.bus_passenger_item_rdb_female);
            rdbMale= itemView.findViewById(R.id.bus_passenger_item_rdb_male);

            layoutItemView= itemView.findViewById(R.id.bus_passenger_item_layout_itemview);
            layoutHeader= itemView.findViewById(R.id.bus_passenger_item_layout_header);

            InputFilter letterFilter = new InputFilter() {
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    String filtered = "";
                    for (int i = start; i < end; i++) {
                        char character = source.charAt(i);
                        int index=i+1;
                        //if(index)
                       /* if (Character.isWhitespace(character)) {
                            return "";
                        }*/
                        if (Character.isWhitespace(source.charAt(i))) {
                            if (dstart == 0)
                                return "";
                        }
                        else if (!Character.isLetter(source.charAt(i))) {
                            return "";
                        }

                    }

                    return null;
                }

            };
            InputFilter filter1 = new InputFilter() {
                public CharSequence filter(CharSequence source, int start, int end,
                                           Spanned dest, int dstart, int dend) {
                    for (int i = start; i < end; i++) {
                        if (Character.isWhitespace(source.charAt(i))) {
                            return "";
                        }
                    }
                    return null;
                }
            };

            /*TExt show error on text change listener*/
            txtError.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(getAdapterPosition()!=-1){
                        pessengerInfosList.get(getAdapterPosition()).setErrorMsg(s.toString());
                    }

                }
            });

            //editName.setFilters(new InputFilter[]{letterFilter});
            /*EditText NAme  on text change listener*/
            editName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void afterTextChanged(Editable editable) {

                    pessengerInfosList.get(getAdapterPosition()).setName(editName.getText().toString());
                }

            });
            editName.addTextChangedListener(new MyTextWatcher(editName));

            /*EditText Mobile  on text change listener*/
            editMobile.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void afterTextChanged(Editable editable) {

                    pessengerInfosList.get(getAdapterPosition()).setMobile(editMobile.getText().toString());
                }

            });

            editEmail.setFilters(new InputFilter[]{filter1});

            /*EditText NAme  on text change listener*/
            editEmail.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void afterTextChanged(Editable editable) {

                    pessengerInfosList.get(getAdapterPosition()).setEmail(editEmail.getText().toString());
                }

            });

            /*EditText age on text change listener*/
            editAge.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void afterTextChanged(Editable editable) {


                    pessengerInfosList.get(getAdapterPosition()).setAge(editAge.getText().toString());
                }
            });

            /*RadioGroup Adult gender*/
            rdgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    //Integer pos = (Integer) group.getTag();
                    Integer pos = getAdapterPosition();
                   // Toast.makeText(context, pos + " clicked!", Toast.LENGTH_SHORT).show();
                    RadioButton rb = group.findViewById(checkedId);
                    pessengerInfosList.get(pos).setGender(String.valueOf(rb.getText()));



                    if(rb.getText().toString().equals("Male")){
                        pessengerInfosList.get(pos).setGenderChecked(true);

                    }
                    else if(rb.getText().toString().equals("Female")){
                        pessengerInfosList.get(pos).setGenderChecked(true);

                    }

                }
            });

        }
    }
    public static class MyTextWatcher implements TextWatcher {
        private EditText editText;

        public MyTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String text = editText.getText().toString();
            if (text.startsWith(" ")) {
                editText.setText(text.trim());

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    public static class FooterHolder extends RecyclerView.ViewHolder{
        public static EditText editMobile;
        public static EditText editEmail;

        public static TextView textErrorFooter;


        LinearLayout layoutFooter;
        LinearLayout layoutFooterCcontent;
        Button btnContinue;

        public FooterHolder(@NonNull View itemFooterView) {
            super(itemFooterView);

            editMobile = itemFooterView.findViewById(R.id.bus_passenger_info_footer_edtxt_mobileno);
            editEmail = itemFooterView.findViewById(R.id.bus_passenger_info_footer_edtxt_emailid);

            textErrorFooter= itemFooterView.findViewById(R.id.bus_passenger_info_footer_txt_error);

            //btnContinue= itemFooterView.findViewById(R.id.flight_passenger_info_footer_btn_continue);


            //layoutHeader=(LinearLayout)itemView.findViewById(R.id.flight_passenger_item_layout_header);
            //EditText Email
            editEmail.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void afterTextChanged(Editable editable) {
                    //pessengerInfo.setEmail(editEmail.getText().toString());


                    if(!editable.toString().equals("")){
                        textErrorFooter.setText("");
                        pessengerInfosList.get(getAdapterPosition()).setEmail(editEmail.getText().toString());
                    }
                    else {
                        textErrorFooter.setText("Enter Email Id");
                        pessengerInfosList.get(getAdapterPosition()).setEmail(editEmail.getText().toString());
                    }
                }
            });

            //*EditText Mobile*//*
            editMobile.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void afterTextChanged(Editable editable) {
                    //pessengerInfo.setMobile(editMobile.getText().toString());

                    if(!editable.toString().equals("")){
                        textErrorFooter.setText("");
                        pessengerInfosList.get(getAdapterPosition()).setMobile(editMobile.getText().toString());
                    }
                    else {
                        textErrorFooter.setText("Enter Mobile No.");
                        pessengerInfosList.get(getAdapterPosition()).setMobile(editMobile.getText().toString());
                    }
                }
            });



        }
    }

    public BusPassengerInfoAdapter(Context ctx, ArrayList<BusPassengerModel> infoArrayList,
                                   ArrayList<ErrorMsgModel> errorList, BusPassengerInfoActivity act){

        inflater = LayoutInflater.from(ctx);
        context=ctx;
        activity=act;
        this.total_type=infoArrayList.size();
        pessengerInfosList = infoArrayList;
        this.errorMsgList=errorList;
        //this.footerInfoArrayList=footerlist;
        // this.headerInfoArrayList=headerInfosList;
    }

    public void setData(Context ctx,ArrayList<ErrorMsgModel> errorList){
        context=ctx;
        //pessengerInfosList = infoArrayList;
        errorMsgList=errorList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view;
        switch (viewType) {
            /*case PessengerInfo.HEADER_TYPE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.utility_flight_passenger_info_header, viewGroup, false);
                return new HeaderHolder(view);*/
            case PessengerInfo.ITEM_TYPE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.utility_bus_passenger_info_item, viewGroup, false);
                return new ItemHolder(view);
            case PessengerInfo.FOOTER_TYPE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.utility_bus_passenger_info_footer_item, viewGroup, false);
                return new FooterHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        switch (pessengerInfosList.get(position).getType()) {
            case 1:
                return PessengerInfo.ITEM_TYPE;
            case 2:
                return PessengerInfo.FOOTER_TYPE;

            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        try{
            BusPassengerModel pessengerInfo= pessengerInfosList.get(position);
            pos=position;
            if(pessengerInfo !=null){

                switch (pessengerInfo.getType()) {

               /* case PessengerInfo.HEADER_TYPE:
                    HeaderHolder headerHolder= (HeaderHolder)viewHolder;
                    //headerHolder.layoutHeader.setVisibility(View.VISIBLE);
                    break;*/

               /*Case for Item */
                    case BusPassengerModel.ITEM_TYPE:
                        ItemHolder itemHolder = (ItemHolder) viewHolder;


                        int seatcount = pessengerInfosList.get(position).getTotalSeat();
                        itemHolder.txtSeat.setText(String.valueOf(seatcount) +").");

                        if (seatcount == 1) {
                            itemHolder.layoutHeader.setVisibility(View.VISIBLE);
                        }
                        else {
                            itemHolder.layoutHeader.setVisibility(View.GONE);
                        }

                        itemHolder.txtSeatName.setText(pessengerInfosList.get(position).getSeat());
                        itemHolder.editAge.setText(pessengerInfosList.get(position).getAge());
                        itemHolder.editName.setText(pessengerInfosList.get(position).getName());
                        itemHolder.editEmail.setText(pessengerInfosList.get(position).getEmail());
                        itemHolder.editMobile.setText(pessengerInfosList.get(position).getMobile());

                        itemHolder.rdgGender.setTag(position);
                        if(pessengerInfosList.get(position).getGender().equals("Male")) {
                            itemHolder.rdbMale.setChecked(true);

                        }
                        else if(pessengerInfosList.get(position).getGender().equals("Female")) {
                            itemHolder.rdbFemale.setChecked(true);

                        }

                        //Radio Group Male and Female

                        if(itemHolder.rdbMale.isChecked()){
                            //editModelArrayList.get(getAdapterPosition()).isChecked();
                            pessengerInfosList.get(position).setGenderChecked(true);
                            //pessengerInfosList.get(position-1).setCheckedId(myViewHolder.rdbMale.getId());
                            pessengerInfosList.get(position).setGender(String.valueOf(itemHolder.rdbMale.getText()));
                        }

                        else if(itemHolder.rdbFemale.isChecked()){
                            //editModelArrayList.get(getAdapterPosition()).isChecked();
                            pessengerInfosList.get(position).setGenderChecked(true);
                            // pessengerInfosList.get(position-1).setCheckedId(myViewHolder.rdbFemale.getId());
                            pessengerInfosList.get(position).setGender(String.valueOf(itemHolder.rdbFemale.getText()));
                            //myViewHolder.rdbFemale.setText(pessengerInfosList.get(position-1).getGender());
                        }

                        String error="";
                        itemHolder.txtError.setText(errorMsgList.get(position).getErrorMsg());

                        break;

                    /*Case for Footer */
                    case BusPassengerModel.FOOTER_TYPE:

                        FooterHolder footerHolder=(FooterHolder)viewHolder;

                        footerHolder.editMobile.setText(pessengerInfosList.get(position).getMobile());
                        footerHolder.editEmail.setText(pessengerInfosList.get(position).getEmail());
                        footerHolder.textErrorFooter.setText(errorMsgList.get(position).getErrorMsg());

                        break;


                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return pessengerInfosList.size();
    }


    public interface getErrorList {
        void ErrorList(ArrayList<ErrorMsgModel> list);

    }
}
