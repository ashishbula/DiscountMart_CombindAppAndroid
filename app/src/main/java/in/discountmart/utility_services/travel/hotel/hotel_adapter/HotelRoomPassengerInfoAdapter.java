package in.discountmart.utility_services.travel.hotel.hotel_adapter;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.model.response_model.LoginResponse;
import in.discountmart.utility_services.sharedpreferences.LoginPreferences_Utility;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.ErrorMsgModel;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.PassengerFooterInfo;
import in.discountmart.utility_services.travel.flight.flight_model.flight_request_model.PessengerInfo;
import in.discountmart.utility_services.travel.hotel.hotel_activity.HotelPassengerInfoActivity;
import in.discountmart.utility_services.travel.hotel.hotel_model.HotelPassengerModel;

public class HotelRoomPassengerInfoAdapter extends RecyclerView.Adapter{
    private LayoutInflater inflater;
    static Context context;
    static HotelPassengerInfoActivity activity;
    public static ArrayList<HotelPassengerModel> pessengerInfosList;
    public  ArrayList<ErrorMsgModel> errorMsgList;
    public static ArrayList<PassengerFooterInfo> footerInfoArrayList =new ArrayList<PassengerFooterInfo>();
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
            layoutHeader= itemHeaderView.findViewById(R.id.flight_passenger_item_layout_header);

        }
    }

    public static class ItemHolder extends RecyclerView.ViewHolder{

        EditText editName;
        EditText editSurName;
        EditText editDob;
        RadioGroup rdgAdultTitle;
        RadioGroup rdgChildTitle;
        RadioGroup rdgGender;
        RadioButton rdbMale;
        RadioButton rdbFemale;
        RadioButton rdbMs;
        RadioButton rdbMrs;
        RadioButton rdbMr;
        RadioButton rdbMstr;
        RadioButton rdbMiss;
        TextView txtTitle;
        TextView txtCount;
        TextView txtError;

        LinearLayout layoutItemView;
        LinearLayout layoutHeader;


        public ItemHolder(View itemView) {
            super(itemView);
            try {
                editName = itemView.findViewById(R.id.room_passenger_item_edtxt_name);
                editSurName = itemView.findViewById(R.id.room_passenger_item_edtxt_surname);
                editDob = itemView.findViewById(R.id.room_passenger_item_edtxt_dob);

                txtTitle= itemView.findViewById(R.id.room_passenger_item_txt_paasenger_title);
                txtCount= itemView.findViewById(R.id.room_passenger_item_txt_paasenger_count);
                txtError= itemView.findViewById(R.id.room_passenger_item_txt_error);
                rdgAdultTitle= itemView.findViewById(R.id.room_passenger_item_rdg_adult_title);
                rdgChildTitle= itemView.findViewById(R.id.room_passenger_item_rdg_child_title);
                rdgGender= itemView.findViewById(R.id.room_passenger_item_rdg_gender);
                rdbFemale= itemView.findViewById(R.id.room_passenger_item_rdb_female);
                rdbMale= itemView.findViewById(R.id.room_passenger_item_rdb_male);
                rdbMiss= itemView.findViewById(R.id.room_passenger_item_rdb_miss);
                rdbMstr= itemView.findViewById(R.id.room_passenger_item_rdb_mstr);
                rdbMr= itemView.findViewById(R.id.room_passenger_item_rdb_mr);
                rdbMs= itemView.findViewById(R.id.room_passenger_item_rdb_ms);
                rdbMrs= itemView.findViewById(R.id.room_passenger_item_rdb_mrs);
                layoutItemView= itemView.findViewById(R.id.room_passenger_item_layout_itemview);
                layoutHeader= itemView.findViewById(R.id.room_passenger_item_layout_header);

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

           /* if (!pessengerInfosList.get(getAdapterPosition()).getErrorMsg().equals("")){
                txtError.setText(pessengerInfosList.get(getAdapterPosition()).getErrorMsg());

            }
            else {
                txtError.setText("");
            }*/
           /* editDob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DateUtilities.edttxtDobUtility1 = editDob;

                    //DialogFragment newFragment = new DateUtilities.SelectDateFragment();
                    //newFragment.show(activity.getSupportFragmentManager(), "DatePicker");

                    DialogFragment newFragment = new DateUtilities.SelectDateFragment2();
                    newFragment.show(activity.getSupportFragmentManager(), "DatePicker");
                    // pos=getAdapterPosition();

                    //DialogFragment newFragment = new SelectDepartureDateFragment();
                    //newFragment.show(activity.getSupportFragmentManager(), "DatePicker");
                }
            });*/

                /*EditText DOB*/
                editDob.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {



                        //dobList.add(getAdapterPosition()-1,editDob.getText().toString());

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        //pos=getAdapterPosition();
                        pessengerInfosList.get(getAdapterPosition()).setDob(editDob.getText().toString());

                    }
                });

                //editName.setFilters(new InputFilter[]{letterFilter});
                /*EditText NAme*/
                editName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                        //nameList.add(getAdapterPosition()-1,editName.getText().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                        pessengerInfosList.get(getAdapterPosition()).setName(editName.getText().toString());
                    }

                });
                editName.addTextChangedListener(new MyTextWatcher(editName));

                editSurName.setFilters(new InputFilter[]{letterFilter});
                /*EditText SurNAme*/
                editSurName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                        //surnameList.add(getAdapterPosition()-1,editSurName.getText().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {


                        pessengerInfosList.get(getAdapterPosition()).setSurName(editSurName.getText().toString());
                    }
                });

                /*RadioGroup Adult Name Title*/
                rdgAdultTitle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        Integer pos = getAdapterPosition();

                        RadioButton rb = group.findViewById(checkedId);

                        PessengerInfo pessengerInfo= (PessengerInfo)rb.getTag();
                       // Toast.makeText(context, pos  +""+rb.getText().toString(), Toast.LENGTH_SHORT).show();

                        pessengerInfosList.get(pos).setGender(String.valueOf(rb.getText()));

                   /* if(rb.isChecked()){
                        //pessengerInfosList.get(pos).setChecked(true);
                        //pessengerInfosList.get(getAdapterPosition()-1).setCheckedId(checkedId);
                        //pessengerInfo.s

                        // pessengerInfo.setChecked(rb.isChecked());
                        pessengerInfosList.get(pos).setChecked(true);
                        //pessengerInfosList.get(pos).setNametitle(String.valueOf(rb.getText()));
                    }
                    else {
                        //pessengerInfosList.get(pos).setChecked(false);
                    }*/
                        if(rb.getText().toString().equals("Mr")){
                            pessengerInfosList.get(pos).setAdultChecked(true);
                        /*rdbMr.setChecked(true);
                        rdbMrs.setChecked(false);
                        rdbMs.setChecked(false);*/
                            //pessengerInfosList.get(pos).setNametitle(String.valueOf(rb.getText()));
                        }
                        else if(rb.getText().toString().equals("Mrs")){
                        /*rdbMr.setChecked(false);
                        rdbMrs.setChecked(true);
                        rdbMs.setChecked(false);*/
                            pessengerInfosList.get(pos).setAdultChecked(true);
                        }

                        else if(rb.getText().toString().equals("Ms")){
                        /*rdbMr.setChecked(false);
                        rdbMrs.setChecked(false);
                        rdbMs.setChecked(true);*/
                            pessengerInfosList.get(pos).setAdultChecked(true);
                        }



                    }
                });

                /*RadioGroup Adult Name Title*/
                rdgChildTitle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        Integer pos = getAdapterPosition();
                       // Toast.makeText(context, pos + " clicked!", Toast.LENGTH_SHORT).show();
                        RadioButton rb = group.findViewById(checkedId);
                       // Toast.makeText(context, pos +""+rb.getText().toString(), Toast.LENGTH_SHORT).show();

                        pessengerInfosList.get(pos).setNametitle(String.valueOf(rb.getText()));
                   /* if(rb.isChecked()){
                        pessengerInfosList.get(pos).setChecked(true);
                        //pessengerInfosList.get(getAdapterPosition()-1).setCheckedId(checkedId);
                        //pessengerInfo.s

                    }
                    else {
                        //pessengerInfosList.get(pos).setChecked(false);
                    }*/


                        if(rb.getText().toString().equals("Mstr")){
                            pessengerInfosList.get(pos).setChildChecked(true);
                        /*rdbMstr.setChecked(true);
                        rdbMiss.setChecked(false);*/
                            //pessengerInfosList.get(pos).setGender(String.valueOf(rb.getText().toString()));
                        }
                        else if(rb.getText().toString().equals("Miss")){
                            pessengerInfosList.get(pos).setChildChecked(true);
                       /* rdbMstr.setChecked(false);
                        rdbMiss.setChecked(true);*/
                            //pessengerInfosList.get(pos).setGender(String.valueOf(rb.getText().toString()));
                        }



                        //group.clearCheck();

                        //notifyDataSetChanged();
                    }
                });

                /*RadioGroup Adult Name Title*/
                rdgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        //Integer pos = (Integer) group.getTag();
                        Integer pos = getAdapterPosition();
                        //Toast.makeText(context, pos + " clicked!", Toast.LENGTH_SHORT).show();
                        RadioButton rb = group.findViewById(checkedId);
                        pessengerInfosList.get(pos).setGender(String.valueOf(rb.getText()));

                    /*if(rb.isChecked()){
                        pessengerInfosList.get(pos).setChecked(true);
                        //pessengerInfosList.get(getAdapterPosition()-1).setCheckedId(checkedId);
                        //pessengerInfo.s
                        //pessengerInfosList.get(pos).setGender(String.valueOf(rb.getText()));
                    }
                    else {
                        //pessengerInfosList.get(pos).setChecked(false);
                        //pessengerInfosList.get(getAdapterPosition()-1).setCheckedId(checkedId);
                        //pessengerInfo.s
                       // pessengerInfosList.get(pos).setGender(String.valueOf(rb.getText()));
                    }*/

                        if(rb.getText().toString().equals("Male")){
                            pessengerInfosList.get(pos).setGenderChecked(true);
                       /* rdbMale.setChecked(true);
                        rdbFemale.setChecked(false);*/
                            //pessengerInfosList.get(pos).setGender(String.valueOf(rb.getText().toString()));
                        }
                        else if(rb.getText().toString().equals("Female")){
                            pessengerInfosList.get(pos).setGenderChecked(true);
                       /* rdbMale.setChecked(false);
                        rdbFemale.setChecked(true);*/
                            //pessengerInfosList.get(pos).setGender(String.valueOf(rb.getText().toString()));
                        }

                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }



    public static class FooterHolder extends RecyclerView.ViewHolder{
        public static EditText editMobile;
        public static EditText editEmail;
        public static CheckBox checkBox;
        public static EditText editGstNo;
        public static EditText editCompanyName;
        public static EditText editCompanyAddress;
        public static EditText editContactNo;
        public static EditText editGstEmail;
        public static TextView textErrorFooter;
        public static TextView textErrorGstNo;
        public static TextView textErrorGstMail;
        public static TextView textErrorCompName;
        public static TextView textErrorCompAdd;
        public static TextView textErrorContact;

        LinearLayout layoutFooter;
        LinearLayout layoutFooterCcontent;
        Button btnContinue;

        public FooterHolder(@NonNull View itemFooterView) {
            super(itemFooterView);
            try {
                editMobile = itemFooterView.findViewById(R.id.room_passenger_info_footer_edtxt_mobileno);
                editEmail = itemFooterView.findViewById(R.id.room_passenger_info_footer_edtxt_emailid);
                editGstNo = itemFooterView.findViewById(R.id.room_passenger_info_footer_edtxt_gstno);
                editGstEmail = itemFooterView.findViewById(R.id.room_passenger_info_footer_edtxt_gst_email);
                editCompanyAddress= itemFooterView.findViewById(R.id.room_passenger_info_footer_edtxt_company_address);
                editCompanyName= itemFooterView.findViewById(R.id.room_passenger_info_footer_edtxt_companyno);
                editContactNo= itemFooterView.findViewById(R.id.room_passenger_info_footer_edtxt_contact_no);
                checkBox= itemFooterView.findViewById(R.id.room_passenger_info_footer_checkbox);
                textErrorFooter= itemFooterView.findViewById(R.id.room_passenger_info_footer_txt_error);
                textErrorGstNo= itemFooterView.findViewById(R.id.room_passenger_info_footer_txt_errorgstno);
                textErrorGstMail= itemFooterView.findViewById(R.id.room_passenger_info_footer_txt_errorgstmail);
                textErrorContact= itemFooterView.findViewById(R.id.room_passenger_info_footer_txt_errorcontact);
                textErrorCompAdd= itemFooterView.findViewById(R.id.room_passenger_info_footer_txt_error_compaddres);
                textErrorCompName= itemFooterView.findViewById(R.id.room_passenger_info_footer_txt_errorcompno);
                //btnContinue= itemFooterView.findViewById(R.id.room_passenger_info_footer_btn_continue);

                layoutFooter= itemFooterView.findViewById(R.id.room_passenger_item_layout_footer);
                layoutFooterCcontent= itemFooterView.findViewById(R.id.room_passenger_info_footer_layout_content);
                //layoutHeader=(LinearLayout)itemView.findViewById(R.id.flight_passenger_item_layout_header);

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
                editEmail.setFilters(new InputFilter[]{filter1});
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


                //EditText GstNo
                editGstNo.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                        // pessengerInfo.setGstno(editGstNo.getText().toString());


                        if(!editable.toString().equals("")){
                            textErrorGstNo.setText("");
                            pessengerInfosList.get(getAdapterPosition()).setGstno(editGstNo.getText().toString());
                        }
                        else {
                            textErrorGstNo.setText("Enter GST No.");
                            pessengerInfosList.get(getAdapterPosition()).setGstno(editGstNo.getText().toString());
                        }
                    }
                });

                //EditText Gst Email
                editGstEmail.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                        //pessengerInfo.setGstemail(editGstEmail.getText().toString());
                        // pessengerInfosList.get(getAdapterPosition()).setGstemail(editGstEmail.getText().toString());
                        if(!editable.toString().equals("")){
                            textErrorGstMail.setText("");
                            pessengerInfosList.get(getAdapterPosition()).setGstemail(editGstEmail.getText().toString());
                        }
                        else {
                            textErrorGstMail.setText("Enter GST Email Id");
                            pessengerInfosList.get(getAdapterPosition()).setGstemail(editGstEmail.getText().toString());
                        }
                    }
                });

                //EditText Company Address
                editCompanyAddress.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        //pessengerInfo.setCompanyAddress(editCompanyAddress.getText().toString());

                        if(!editable.toString().equals("")){
                            textErrorCompAdd.setText("");
                            pessengerInfosList.get(getAdapterPosition()).setCompanyAddress(editCompanyAddress.getText().toString());
                        }
                        else {
                            textErrorCompAdd.setText("Enter Company Address");
                            pessengerInfosList.get(getAdapterPosition()).setCompanyAddress(editCompanyAddress.getText().toString());
                        }

                    }
                });

                //EditText Company No
                editCompanyName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        //pessengerInfo.setCompanyName(editCompanyName.getText().toString());


                        if(!editable.toString().equals("")){
                            textErrorCompName.setText("");
                            pessengerInfosList.get(getAdapterPosition()).setCompanyName(editCompanyName.getText().toString());
                        }
                        else {
                            textErrorCompName.setText("Enter Company Name");
                            pessengerInfosList.get(getAdapterPosition()).setCompanyName(editCompanyName.getText().toString());
                        }

                    }
                });

                //EditText contact Number
                editContactNo.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        //pessengerInfo.setContactno(editContactNo.getText().toString());

                        if(!editable.toString().equals("")){
                            textErrorContact.setText("");
                            pessengerInfosList.get(getAdapterPosition()).setContactno(editContactNo.getText().toString());
                        }
                        else {
                            pessengerInfosList.get(getAdapterPosition()).setContactno(editContactNo.getText().toString());
                            textErrorContact.setText("Enter Contact No.");
                        }

                    }
                });

                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CheckBox cb = (CheckBox) v;
                        // PessengerInfo contact = (PessengerInfo) cb.getTag();

                        Integer pos=getAdapterPosition();
                        //contact.setChecked(cb.isChecked());
                        // pessengerInfosList.get(getAdapterPosition()+1).setChecked(cb.isChecked());

                        // Toast.makeText(v.getContext(),"click on checkbox:"+pos,Toast.LENGTH_SHORT).show();

                        //Toast.makeText(v.getContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();


                        if (cb.isChecked()) {
                            // pessengerInfo.setChecked(true);
                            pessengerInfosList.get(getAdapterPosition()).setSelect(true);

                            layoutFooterCcontent.setVisibility(View.VISIBLE);
                        } else {
                            //pessengerInfo.setChecked(false);
                            pessengerInfosList.get(getAdapterPosition()).setSelect(false);
                            layoutFooterCcontent.setVisibility(View.GONE);
                        }
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }

    public HotelRoomPassengerInfoAdapter(Context ctx, ArrayList<HotelPassengerModel> infoArrayList,
                                         ArrayList<ErrorMsgModel> errorList, HotelPassengerInfoActivity act){

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
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.utility_hotel_room_passenger_info_item, viewGroup, false);
                return new ItemHolder(view);
            case PessengerInfo.FOOTER_TYPE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.utility_room_passenger_info_footer_item, viewGroup, false);
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
            HotelPassengerModel pessengerInfo= pessengerInfosList.get(position);
            pos=position;
            if(pessengerInfo !=null){

                switch (pessengerInfo.getType()){

               /* case PessengerInfo.HEADER_TYPE:
                    HeaderHolder headerHolder= (HeaderHolder)viewHolder;
                    //headerHolder.layoutHeader.setVisibility(View.VISIBLE);
                    break;*/

                    case PessengerInfo.ITEM_TYPE:
                        ItemHolder itemHolder= (ItemHolder)viewHolder;

                        //itemHolder.rdgAdultTitle.setTag(pessengerInfosList.get(position));
                        //itemHolder.rdgChildTitle.setTag(pessengerInfosList.get(position));
                        //itemHolder.rdgGender.setTag(pessengerInfosList.get(position));


                        if(pessengerInfosList.get(position).getPessengerType().equals("A")){

                            itemHolder.txtTitle.setText("Adults " +"-");
                            itemHolder.rdgAdultTitle.setVisibility(View.VISIBLE);
                            itemHolder.rdgChildTitle.setVisibility(View.GONE);
                            itemHolder.editDob.setEnabled(true);
                            itemHolder.editDob.setFocusable(true);

                            int adultcount=pessengerInfosList.get(position).getCountAdults();
                            itemHolder.txtCount.setText(String.valueOf(adultcount));

                            if(adultcount==1){
                                itemHolder.layoutHeader.setVisibility(View.VISIBLE);
                            }
                            else {
                                itemHolder.layoutHeader.setVisibility(View.GONE);
                            }
                        }
                        else if (pessengerInfosList.get(position).getPessengerType().equals("C")){

                            itemHolder.txtTitle.setText("Childs "+"-");
                            int childcount=pessengerInfosList.get(position).getCountChilds();
                            itemHolder.txtCount.setText(String.valueOf(childcount));

                            itemHolder.rdgAdultTitle.setVisibility(View.GONE);
                            itemHolder.rdgChildTitle.setVisibility(View.VISIBLE);
                            itemHolder.editDob.setEnabled(false);
                            itemHolder.editDob.setFocusable(false);
                            itemHolder.editDob.setTextColor(context.getResources().getColor(R.color.black));
                        }
                        else if (pessengerInfosList.get(position).getPessengerType().equals("I")){

                            itemHolder.txtTitle.setText("Infants "+"-");
                            int infantscount=pessengerInfosList.get(position).getCountInfants();
                            itemHolder.txtCount.setText(String.valueOf(infantscount));

                            itemHolder.rdgAdultTitle.setVisibility(View.GONE);
                            itemHolder.rdgChildTitle.setVisibility(View.VISIBLE);
                        }

                        itemHolder.rdgAdultTitle.setTag(position);
                        itemHolder.rdgChildTitle.setTag(position);
                        itemHolder.rdgGender.setTag(position);


                        if(itemHolder.rdgAdultTitle.getVisibility()==View.VISIBLE){

                            if(pessengerInfosList.get(position).getGender().equals("Mr")) {
                                itemHolder.rdbMr.setChecked(true);

                            }
                            else if(pessengerInfosList.get(position).getGender().equals("Mrs")) {
                                itemHolder.rdbMrs.setChecked(true);

                            }
                            else if(pessengerInfosList.get(position).getGender().equals("Ms")) {
                                itemHolder.rdbMs.setChecked(true);

                            }

                            if(itemHolder.rdbMs.isChecked()){
                                //editModelArrayList.get(getAdapterPosition()).isChecked();
                                pessengerInfosList.get(position).setAdultChecked(true);
                                //pessengerInfosList.get(position-1).setCheckedId(myViewHolder.rdbMs.getId());
                                pessengerInfosList.get(position).setNametitle(String.valueOf(itemHolder.rdbMs.getText()));
                                //pessengerInfosList.get(position-1).setNametitle();
                            }

                            else if(itemHolder.rdbMr.isChecked()){
                                //editModelArrayList.get(getAdapterPosition()).isChecked();
                                pessengerInfosList.get(position).setAdultChecked(true);
                                //pessengerInfosList.get(position-1).setCheckedId(myViewHolder.rdbMr.getId());
                                pessengerInfosList.get(position).setNametitle(String.valueOf(itemHolder.rdbMr.getText()));
                                // myViewHolder.rdbMr.setText( pessengerInfosList.get(position-1).getNametitle());
                            }

                            else if(itemHolder.rdbMrs.isChecked()){
                                //editModelArrayList.get(getAdapterPosition()).isChecked();
                                pessengerInfosList.get(position).setAdultChecked(true);
                                //pessengerInfosList.get(position-1).setCheckedId(myViewHolder.rdbMrs.getId());
                                pessengerInfosList.get(position).setNametitle(String.valueOf(itemHolder.rdbMrs.getText()));
                                //myViewHolder.rdbMrs.setText( pessengerInfosList.get(position-1).getNametitle());
                            }

                        }
                        else if(itemHolder.rdgChildTitle.getVisibility()==View.VISIBLE){

                            if(pessengerInfosList.get(position).getGender().equals("Miss")) {
                                itemHolder.rdbMiss.setChecked(true);

                            }
                            else if(pessengerInfosList.get(position).getGender().equals("Mstr")) {
                                itemHolder.rdbMstr.setChecked(true);

                            }

                            if(itemHolder.rdbMiss.isChecked()){
                                //editModelArrayList.get(getAdapterPosition()).isChecked();
                                pessengerInfosList.get(position).setChildChecked(true);
                                // pessengerInfosList.get(position-1).setCheckedId(myViewHolder.rdbMiss.getId());
                                pessengerInfosList.get(position).setNametitle(String.valueOf(itemHolder.rdbMiss.getText()));
                                //myViewHolder.rdbMiss.setText( pessengerInfosList.get(position-1).getNametitle());
                            }

                            else if(itemHolder.rdbMstr.isChecked()){
                                //editModelArrayList.get(getAdapterPosition()).isChecked();
                                pessengerInfosList.get(position).setChildChecked(true);
                                //pessengerInfosList.get(position-1).setCheckedId(myViewHolder.rdbMstr.getId());
                                pessengerInfosList.get(position).setNametitle(String.valueOf(itemHolder.rdbMstr.getText()));
                                //myViewHolder.rdbMstr.setText( pessengerInfosList.get(position-1).getNametitle());

                            }

                        }

                        //Radio Group Male and Female
                        if(pessengerInfosList.get(position).getGender().equals("Male")) {
                            itemHolder.rdbMale.setChecked(true);

                        }
                        else if(pessengerInfosList.get(position).getGender().equals("Female")) {
                            itemHolder.rdbFemale.setChecked(true);

                        }

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

                        itemHolder.editName.setText(pessengerInfosList.get(position).getName());
                        itemHolder.editDob.setText(pessengerInfosList.get(position).getDob());
                        itemHolder.editSurName.setText(pessengerInfosList.get(position).getSurName());


                        itemHolder.txtError.setText(errorMsgList.get(position).getErrorMsg());

                        break;

                    case PessengerInfo.FOOTER_TYPE:

                        FooterHolder footerHolder=(FooterHolder)viewHolder;

                        FooterHolder.checkBox.setChecked(pessengerInfosList.get(position).isSelect());

                        FooterHolder.checkBox.setTag(pessengerInfosList.get(position));


                        //String mobileNo=new LoginPreferences_brand(context).getLoggedinUser().getMobileNo();
                        LoginResponse loginResponse=new LoginResponse();

                        loginResponse=new LoginPreferences_Utility(context).getLoggedinUser();

                        FooterHolder.editMobile.setText(pessengerInfosList.get(position).getMobile());
                        FooterHolder.editEmail.setText(pessengerInfosList.get(position).getEmail());

                        FooterHolder.editGstNo.setText(pessengerInfosList.get(position).getGstno());
                        FooterHolder.editGstEmail.setText(pessengerInfosList.get(position).getGstemail());
                        FooterHolder.editCompanyName.setText(pessengerInfosList.get(position).getCompanyName());
                        FooterHolder.editCompanyAddress.setText(pessengerInfosList.get(position).getCompanyAddress());
                        FooterHolder.editContactNo.setText(pessengerInfosList.get(position).getContactno());
                        FooterHolder.textErrorFooter.setText(errorMsgList.get(position).getErrorMsg());

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
}
