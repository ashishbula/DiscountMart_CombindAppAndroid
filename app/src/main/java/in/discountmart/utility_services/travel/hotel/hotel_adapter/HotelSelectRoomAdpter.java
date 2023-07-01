package in.discountmart.utility_services.travel.hotel.hotel_adapter;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.discountmart.R;
import in.discountmart.utility_services.travel.hotel.hotel_activity.HotelSelectRoomActivity;
import in.discountmart.utility_services.travel.hotel.hotel_model.Child_AgeModel;
import in.discountmart.utility_services.travel.hotel.hotel_model.HotelRoomModel;

public class HotelSelectRoomAdpter extends RecyclerView.Adapter<HotelSelectRoomAdpter.MyViewHolder> {
    int adult=0;
    int childinteger=0;
    int room=0;
    int flag=0;
    public static int childage1;
    public static int childage2;

    String selectRoom="";
    int roomNumber=0;
    private static Context context;
    public static ArrayList<HotelRoomModel> roomList;
    public static ArrayList<Child_AgeModel> child_ageList;
    //SelectChildAgeAdapter ageAdapter;
    static LinearLayout linearLayout;
    ArrayList<LinearLayout> layoutsList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtRemove;
        TextView txtRoom;
        TextView txtAdultIncrement;
        TextView txtChildIncrement;
        TextView txtChildDecrement;
        TextView txtAdultDecrement;
        TextView txtChild;
        TextView txtAdult;
         TextView txtchildAge1;
       TextView txtchildAge2;
      // public static TextView txtchildAge;
       public static LinearLayout layoutChildAge;
       LinearLayout layoutChildAge1;
        LinearLayout layoutChildAge2;
       SeekBar seekBar1;
       SeekBar seekBar2;



        public MyViewHolder(View view) {
            super(view);
            txtRoom = view.findViewById(R.id.hotel_select_room_content_txt_room);
            txtRemove = view.findViewById(R.id.hotel_select_room_content_txt_remove);
            txtAdultIncrement = view.findViewById(R.id.hotel_select_room_content_txt_adult_increse);
            txtChildIncrement = view.findViewById(R.id.hotel_select_room_content_txt_child_increse);
            txtChildDecrement = view.findViewById(R.id.hotel_select_room_content_txt_child_decrese);
            txtAdultDecrement = view.findViewById(R.id.hotel_select_room_content_txt_decrese);
            txtAdult = view.findViewById(R.id.hotel_select_room_content_txt_number_adult);
            txtChild = view.findViewById(R.id.hotel_select_room_content_txt_number_child);
            //layoutChildAge=view.findViewById(R.id.hotel_select_room_content_layout_child_age);
            layoutChildAge1=view.findViewById(R.id.hotel_select_room_content_layout_child_age1);
            layoutChildAge2=view.findViewById(R.id.hotel_select_room_content_layout_child_age2);
           txtchildAge1 = view.findViewById(R.id.hotel_select_room_content_txt_child_age1);
           txtchildAge2 = view.findViewById(R.id.hotel_select_room_content_txt_child_age2);
           seekBar1=view.findViewById(R.id.hotel_select_room_content_seekbar_child_age1);
           seekBar2=view.findViewById(R.id.hotel_select_room_content_seekbar_child_age2);
           // txtchildAge = view.findViewById(R.id.hotel_select_room_content_txt_child_age);

            /*Layout txtAdult Text change listener*/
            txtAdult.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    roomList.get(getAdapterPosition()).setAdult(s.toString());
                    //adult=roomList.get(position).getAdultcount();
                }
            });



            /*Layout txtChild Text change listener*/
            txtChild.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    roomList.get(getAdapterPosition()).setChild(s.toString());

                }
            });

            /*SeekBar 1 for child age 1 get age on change listener*/


            seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int cage1=0;

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(progress < 2)
                        seekBar1.setProgress(2);
                    cage1 = progress;

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                   // roomList.get(getAdapterPosition()).setChildage(cage1);
                    roomList.get(getAdapterPosition()).setChildage1(cage1);
                    txtchildAge1.setText(String.valueOf(roomList.get(getAdapterPosition()).getChildage1()));

                    //if(roomList.get(getAdapterPosition()).getChildcount()==1){
                        if(cage1 != 0){
                            roomList.get(getAdapterPosition()).getChildAgeModels().get(0).setChild("1");
                            roomList.get(getAdapterPosition()).getChildAgeModels().get(0).setAge(String.valueOf(cage1));
                           roomList.get(getAdapterPosition()).getChildAgeModels().get(0).setRoom(roomList.get(getAdapterPosition()).getRoom());
                        }

                    //}
                }
            });

            /*SeekBar 2 for child age 1 get age on change listener*/

            seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int cage2=0;

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    if(progress < 2)
                        seekBar2.setProgress(2);
                    cage2 = progress;

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    //Toast.makeText(context, "Seek bar progress is :" + cage2, Toast.LENGTH_SHORT).show();
                    roomList.get(getAdapterPosition()).setChildage2(cage2);
                    txtchildAge2.setText(String.valueOf(roomList.get(getAdapterPosition()).getChildage2()));

                    if(cage2 != 0){
                        roomList.get(getAdapterPosition()).getChildAgeModels().get(1).setChild("2");
                       roomList.get(getAdapterPosition()).getChildAgeModels().get(1).setAge(String.valueOf(cage2));
                        roomList.get(getAdapterPosition()).getChildAgeModels().get(1).setRoom(roomList.get(getAdapterPosition()).getRoom());

                    }

                }
            });

        }
    }


    public HotelSelectRoomAdpter(Context context, ArrayList<HotelRoomModel> List) {
        this.context = context;
        this.roomList = List;
    }
    public void setData(Context context, ArrayList<HotelRoomModel> List) {
        this.context = context;
        this.roomList = List;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.utility_hotel_select_room_content, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final HotelRoomModel item = roomList.get(position);
        holder.txtRoom.setText(" Room " +roomList.get(position).getRoom());
        holder.txtAdult.setText(roomList.get(position).getAdult());
        holder.txtChild.setText(roomList.get(position).getChild());
        //holder.txtchildAge1.setText(String.valueOf(roomList.get(position).getChildage1()));
        //holder.txtchildAge2.setText(String.valueOf(roomList.get(position).getChildage2()));

    /*Update*/
        adult=roomList.get(position).getAdultcount();
        childinteger=roomList.get(position).getChildcount();
        room= Integer.parseInt(roomList.get(position).getRoom());
        int child=roomList.get(position).getChildcount();

        if(adult > 0){
            if(adult == 1){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.txtAdultIncrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));
                    holder.txtAdultDecrement.setBackground(context.getResources().getDrawable(R.drawable.light_gray_bg));
                }
                holder. txtAdult.setText(String.valueOf(adult));
                holder.txtAdultIncrement.setFocusable(true);
                holder.txtAdultIncrement.setClickable(true);
                holder.txtAdultDecrement.setFocusable(false);
                holder.txtAdultDecrement.setClickable(false);
            }
            else if(adult > 1 && adult < 4){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.txtAdultIncrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));
                    holder.txtAdultDecrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));

                }
                holder. txtAdult.setText(String.valueOf(adult));
                holder.txtAdultIncrement.setFocusable(true);
                holder.txtAdultIncrement.setClickable(true);
                holder.txtAdultDecrement.setFocusable(true);
                holder.txtAdultDecrement.setClickable(true);
            }
            else if(adult == 4){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.txtAdultIncrement.setBackground(context.getResources().getDrawable(R.drawable.light_gray_bg));
                    holder.txtAdultDecrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));

                }
                holder. txtAdult.setText(String.valueOf(adult));
                holder.txtAdultIncrement.setFocusable(false);
                holder.txtAdultIncrement.setClickable(false);
                holder.txtAdultDecrement.setFocusable(true);
                holder.txtAdultDecrement.setClickable(true);
            }
        }
        /*update*/
        if(roomList.get(position).getChildAgeModels().size() > 0){

            if(childinteger==1){
                //childinteger=roomList.get(position).getChildcount();
                roomList.get(position).setAddLayout(true);
                holder.layoutChildAge1.setVisibility(View.VISIBLE);
                holder.layoutChildAge2.setVisibility(View.GONE);
                holder.txtchildAge1.setText(roomList.get(position).getChildAgeModels().get(0).getAge());

                holder.seekBar1.setProgress(Integer.parseInt(roomList.get(position).getChildAgeModels().get(0).getAge()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.txtChildIncrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));
                    holder. txtChildDecrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));

                }
                holder.txtChildIncrement.setFocusable(true);
                holder.txtChildIncrement.setClickable(true);
                holder.txtChildDecrement.setFocusable(true);
                holder.txtChildDecrement.setClickable(true);
            }
            else if(childinteger == 2){
                holder.layoutChildAge1.setVisibility(View.VISIBLE);
                holder.layoutChildAge2.setVisibility(View.VISIBLE);
                roomList.get(position).setAddLayout(true);

                holder.txtchildAge1.setText(roomList.get(position).getChildAgeModels().get(0).getAge());
                holder.seekBar1.setProgress(Integer.parseInt(roomList.get(position).getChildAgeModels().get(0).getAge()));

                holder.txtchildAge2.setText(roomList.get(position).getChildAgeModels().get(1).getAge());
                holder.seekBar2.setProgress(Integer.parseInt(roomList.get(position).getChildAgeModels().get(1).getAge()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.txtChildIncrement.setBackground(context.getResources().getDrawable(R.drawable.light_gray_bg));
                    holder.txtChildDecrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));

                }
                holder.txtChildIncrement.setFocusable(false);
                holder. txtChildIncrement.setClickable(false);
                holder. txtChildDecrement.setFocusable(true);
                holder. txtChildDecrement.setClickable(true);
            }



        }
        else {
            roomList.get(position).setAddLayout(false);
            if(roomList.get(position).isAddLayout()){
                holder.layoutChildAge1.setVisibility(View.VISIBLE);
                holder.layoutChildAge2.setVisibility(View.VISIBLE);
            }
            else {
                holder.layoutChildAge1.setVisibility(View.GONE);
                holder.layoutChildAge2.setVisibility(View.GONE);
            }
            holder.txtChild.setText(roomList.get(position).getChild());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                //holder.txtAdultDecrement.setBackground(context.getResources().getDrawable(R.drawable.light_gray_bg));
                holder.txtChildDecrement.setBackground(context.getResources().getDrawable(R.drawable.light_gray_bg));
                holder.txtChildIncrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));

            }
            holder.txtChildIncrement.setFocusable(true);
            holder.txtChildIncrement.setClickable(true);
            holder.txtChildDecrement.setFocusable(false);
            holder.txtChildDecrement.setClickable(false);
        }
/*end*/
        /*Button Adult Increment Click Listener*/
        holder.txtAdultIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adult=roomList.get(position).getAdultcount();
                if(adult == 1  || adult < 4){
                    adult = adult + 1;

                    holder. txtAdult.setText(String.valueOf(adult));
                    roomList.get(position).setAdult(String.valueOf(adult));
                    roomList.get(position).setAdultcount(adult);
                    holder.txtAdultIncrement.setFocusable(true);
                    holder.txtAdultIncrement.setClickable(true);
                    holder.txtAdultDecrement.setFocusable(true);
                    holder.txtAdultDecrement.setClickable(true);


                }
                else if(adult ==4) {
                    adult = 4;
                    holder.txtAdult.setText(String.valueOf(adult));
                    roomList.get(position).setAdult(String.valueOf(adult));
                    roomList.get(position).setAdultcount(adult);
                    holder.txtAdultIncrement.setFocusable(false);
                    holder.txtAdultIncrement.setClickable(false);
                    holder.txtAdultDecrement.setFocusable(true);
                    holder.txtAdultDecrement.setClickable(true);

                }
                if(adult == 1){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.txtAdultIncrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));
                        holder.txtAdultDecrement.setBackground(context.getResources().getDrawable(R.drawable.light_gray_bg));

                    }
                }
                else if(adult > 1 && adult < 4){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.txtAdultIncrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));
                        holder.txtAdultDecrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));

                    }
                }
                else if(adult == 4){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.txtAdultIncrement.setBackground(context.getResources().getDrawable(R.drawable.light_gray_bg));
                        holder.txtAdultDecrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));

                    }
                }

            }
        });

        /*Button Adult Decrement Click Listener*/
        holder.txtAdultDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //increaseInteger(v);
                //auldinteger=0;
                adult=roomList.get(position).getAdultcount();
                if(adult == 1 ){
                    adult = 1;
                    holder.txtAdult.setText(String.valueOf(adult));
                    roomList.get(position).setAdult(String.valueOf(adult));
                    roomList.get(position).setAdultcount(adult);
                    holder.txtAdultDecrement.setFocusable(false);
                    holder.txtAdultDecrement.setClickable(false);
                   holder. txtAdultIncrement.setFocusable(true);
                    holder.txtAdultIncrement.setClickable(true);
                }
                else if(adult > 1){
                    adult = adult-1;
                    holder.txtAdult.setText(String.valueOf(adult));
                    roomList.get(position).setAdult(String.valueOf(adult));
                    roomList.get(position).setAdultcount(adult);
                    holder.txtAdultDecrement.setFocusable(true);
                    holder. txtAdultDecrement.setClickable(true);
                    holder.txtAdultIncrement.setFocusable(true);
                    holder.txtAdultIncrement.setClickable(true);
                }

                if(adult == 1){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.txtAdultIncrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));
                        holder.txtAdultDecrement.setBackground(context.getResources().getDrawable(R.drawable.light_gray_bg));

                    }
                    roomList.get(position).setAdultcount(adult);
                }
                else if(adult > 1 && adult < 4){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder. txtAdultIncrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));
                        holder.txtAdultDecrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));

                    }
                    roomList.get(position).setAdultcount(adult);
                }
                else if(adult == 4){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.txtAdultIncrement.setBackground(context.getResources().getDrawable(R.drawable.light_gray_bg));
                        holder.txtAdultDecrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));

                    }
                    roomList.get(position).setAdultcount(adult);
                }

            }
        });

        /*Button Child Increment Click Listener*/
        holder.txtChildIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //increaseInteger(v);
                //minteger=0;
               // HotelSelectRoomChildAgeActivity.txtAge1=holder.txtchildAge;
                childinteger=roomList.get(position).getChildcount();

                if(childinteger == 1  || childinteger < 2){

                    childinteger=childinteger+1;

                    holder. txtChild.setText(String.valueOf(childinteger));
                    roomList.get(position).setChildcount(childinteger);
                    roomList.get(position).setChild(String.valueOf(childinteger));

                    holder.txtChildIncrement.setFocusable(true);
                    holder.txtChildIncrement.setClickable(true);
                    holder.txtChildDecrement.setFocusable(true);
                    holder.txtChildDecrement.setClickable(true);

                }
                else if(childinteger ==2) {

                    childinteger=2;
                    roomList.get(position).setChildcount(childinteger);
                    holder. txtChild.setText(String.valueOf(childinteger));
                    roomList.get(position).setChild(String.valueOf(childinteger));

                    holder.txtChildIncrement.setFocusable(false);
                    holder. txtChildIncrement.setClickable(false);
                    holder. txtChildDecrement.setFocusable(true);
                    holder. txtChildDecrement.setClickable(true);

                }

                if(childinteger == 0){

                    roomList.get(position).setChildcount(0);
                    roomList.get(position).setAddLayout(false);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.txtChildIncrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));
                        holder. txtChildDecrement.setBackground(context.getResources().getDrawable(R.drawable.light_gray_bg));

                    }
                    roomList.get(position).setAge(false);
                    roomList.get(position).setFlag(0);

                }
                else if(childinteger > 0 && childinteger < 2 ){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.txtChildIncrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));
                        holder.txtChildDecrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));

                    }
                    roomList.get(position).setChildcount(childinteger);
                    roomList.get(position).setAge(true);
                    roomList.get(position).setFlag(1);
                    roomList.get(position).setAddLayout(true);
                }
                else if(childinteger==2){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.txtChildIncrement.setBackground(context.getResources().getDrawable(R.drawable.light_gray_bg));
                        holder.txtChildDecrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));

                    }
                    holder.txtChildIncrement.setFocusable(false);
                    holder. txtChildIncrement.setClickable(false);
                    holder. txtChildDecrement.setFocusable(true);
                    holder. txtChildDecrement.setClickable(true);
                    roomList.get(position).setAge(true);
                    roomList.get(position).setFlag(1);
                    roomList.get(position).setAddLayout(true);
                }

                if(roomList.get(position).isAge() && roomList.get(position).getFlag()==1){

                    if(roomList.get(position).getChildcount()==0 && !roomList.get(position).isAddLayout() ){

                        holder.layoutChildAge1.setVisibility(View.GONE);
                        holder.layoutChildAge2.setVisibility(View.GONE);

                    }
                    else if(roomList.get(position).getChildcount() == 1 && roomList.get(position).isAddLayout() ){
                        holder.layoutChildAge1.setVisibility(View.VISIBLE);
                       // notifyItemInserted(position);
                        holder.txtchildAge1.setText(String.valueOf(roomList.get(position).getChildage1()));
                    }
                    else if(roomList.get(position).getChildcount() == 2 && roomList.get(position).isAddLayout()){
                        holder.layoutChildAge1.setVisibility(View.VISIBLE);
                        holder.layoutChildAge2.setVisibility(View.VISIBLE);

                        holder.txtchildAge1.setText(String.valueOf(roomList.get(position).getChildage1()));
                        holder.txtchildAge2.setText(String.valueOf(roomList.get(position).getChildage2()));
                        //notifyItemInserted(position);
                    }
                }

                /*update*/
                if(childinteger > 0){

                    if(roomList.get(position).getChildAgeModels().size() == 0 ||roomList.get(position).getChildAgeModels()== null ){
                        Child_AgeModel child_ageModel1=new Child_AgeModel();
                        child_ageModel1.setAge("0");
                        child_ageModel1.setChild("0");
                        child_ageModel1.setRoom(roomList.get(position).getRoom());
                        ArrayList<Child_AgeModel>childAgeList1=new ArrayList<Child_AgeModel>();
                        childAgeList1.add(child_ageModel1);
                        roomList.get(position).setChildAgeModels(childAgeList1);
                    }

                    else{
                        Child_AgeModel child_ageModel2=new Child_AgeModel();
                        child_ageModel2.setAge("0");
                        child_ageModel2.setChild("0");
                        child_ageModel2.setRoom(roomList.get(position).getRoom());
                        //ArrayList<Child_AgeModel>childAgeList=new ArrayList<Child_AgeModel>();
                        //childAgeList.add(child_ageModel2);
                        roomList.get(position).getChildAgeModels().add(child_ageModel2);

                    }

                }

            }
        });

        /*Button Child Decrement Click Listener*/
        holder.txtChildDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //increaseInteger(v);
                // minteger=0;
                childinteger=roomList.get(position).getChildcount();

                if(childinteger==0){

                    childinteger=0;
                    holder. txtChild.setText(String.valueOf(childinteger));
                    roomList.get(position).setChild(String.valueOf(childinteger));
                    roomList.get(position).setChildcount(childinteger);
                    holder. txtChildDecrement.setFocusable(false);
                    holder. txtChildDecrement.setClickable(false);
                    holder. txtChildIncrement.setFocusable(true);
                    holder.txtChildIncrement.setClickable(true);
                }
                else if(childinteger > 0){

                   childinteger=childinteger-1;
                    holder. txtChild.setText(String.valueOf(childinteger));
                    roomList.get(position).setChild(String.valueOf(childinteger));
                    roomList.get(position).setChildcount(childinteger);
                    holder.txtChildDecrement.setFocusable(true);
                    holder. txtChildDecrement.setClickable(true);
                    holder. txtChildIncrement.setFocusable(true);
                    holder.txtChildIncrement.setClickable(true);

                }

                 if(childinteger == 2) {
                    childinteger = 2;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.txtChildIncrement.setBackground(context.getResources().getDrawable(R.drawable.light_gray_bg));
                        holder.txtChildDecrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));

                    }
                    roomList.get(position).setChildcount(childinteger);
                    roomList.get(position).setAge(true);
                    roomList.get(position).setFlag(1);
                    roomList.get(position).setAddLayout(true);
                }

                else if(childinteger > 0 && childinteger < 2)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.txtChildIncrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));
                        holder.txtChildDecrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));

                    }
                    roomList.get(position).setChildcount(childinteger);
                    roomList.get(position).setAge(true);
                    roomList.get(position).setFlag(1);
                    roomList.get(position).setAddLayout(true);
                }
                else if(childinteger==0){
                     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                         holder. txtChildIncrement.setBackground(context.getResources().getDrawable(R.drawable.colorprimary_bg_box));
                         holder. txtChildDecrement.setBackground(context.getResources().getDrawable(R.drawable.light_gray_bg));

                     }
                     holder. txtChildDecrement.setFocusable(false);
                     holder. txtChildDecrement.setClickable(false);
                     holder. txtChildIncrement.setFocusable(true);
                     holder.txtChildIncrement.setClickable(true);
                     roomList.get(position).setAge(false);
                     roomList.get(position).setFlag(0);
                     roomList.get(position).setAddLayout(false);
                 }



                if(roomList.get(position).isAge() && roomList.get(position).getFlag()==1){

                    if(roomList.get(position).getChildcount()==0 && !roomList.get(position).isAddLayout() ){


                        holder.layoutChildAge1.setVisibility(View.GONE);
                        holder.layoutChildAge2.setVisibility(View.GONE);

                    }
                    else if(roomList.get(position).getChildcount() == 1 && roomList.get(position).isAddLayout() ){
                        holder.layoutChildAge1.setVisibility(View.VISIBLE);
                        holder.layoutChildAge2.setVisibility(View.GONE);

                        roomList.get(position).setChildage2(0);
                        holder.txtchildAge2.setText(String.valueOf(roomList.get(position).getChildage2()));
                        holder.seekBar2.setProgress(0);

                    }
                    else if(roomList.get(position).getChildcount() == 2 && roomList.get(position).isAddLayout()){
                        holder.layoutChildAge1.setVisibility(View.VISIBLE);
                        holder.layoutChildAge2.setVisibility(View.VISIBLE);
                        //notifyItemInserted(position);
                    }
                }
                else {
                    holder.layoutChildAge1.setVisibility(View.GONE);
                    holder.layoutChildAge2.setVisibility(View.GONE);
                    if(roomList.get(position).getChildAgeModels().size() !=0)
                        roomList.get(position).getChildAgeModels().remove(0);
                    roomList.get(position).setChildage2(0);
                    roomList.get(position).setChildage1(0);
                    holder.txtchildAge2.setText(String.valueOf(roomList.get(position).getChildage2()));
                    holder.txtchildAge1.setText(String.valueOf(roomList.get(position).getChildage1()));

                    holder.seekBar2.setProgress(0);
                    holder.seekBar1.setProgress(0);

                }
                /*update*/
                if(roomList.get(position).getChildAgeModels().size() != 0){
                    roomList.get(position).getChildAgeModels().remove(childinteger);
                    //notifyItemChanged(position);
                    //notifyItemRangeChanged(position,roomList.get(position).getChildAgeModels().size());
                    // notifyItemRemoved(position);
                    //notifyItemChanged(position);
                    //notifyItemRangeChanged(position,roomList.get(position).getChildAgeModels().size());
                }



            }
        });


        if(getItemCount()==1){
            holder.txtRemove.setVisibility(View.GONE);
        }
        else if(getItemCount() > 1 ){
            holder.txtRemove.setVisibility(View.VISIBLE);
        }
        holder.txtRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position,roomList);
                HotelSelectRoomActivity.roomNumber--;
                for (int i=0;i <roomList.size();i++){
                    int index=i+1;
                    roomList.get(i).setRoom(String.valueOf(index));
                    holder.txtRoom.setText(" Room " +roomList.get(i).getRoom());

                }

            }
        });


    }

    @Override
    public int getItemCount() {
        //return roomList.size();
        return roomList == null ? 0 : roomList.size();

    }

    public void removeItem(int position,ArrayList<HotelRoomModel> list) {

        ArrayList<HotelRoomModel> tempList=list;
        tempList.remove(position);
        roomList=tempList;
        //MyViewHolder.layoutChildAge.removeViewAt(position);
        //roomList.remove(position);
        //roomList.remove(position).getChildAgeModels();
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()

       // notifyItemRemoved(roomList.size());
        notifyItemRemoved(position);
       // notifyItemChanged(position);
        notifyItemRangeChanged(position,roomList.size());
        //notifyItemRemoved(roomList.size());

    }

    public void restoreItem(HotelRoomModel item, int position) {
        roomList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }


    public static LinearLayout createChildAgeListView(final int child, final  ArrayList<Child_AgeModel> List,int position){
        /*Set Dynamically add Linear Layout,TextView,CheckBox*/

        MyViewHolder.layoutChildAge.removeAllViews();

        LinearLayout linearLayout=new LinearLayout(context);
       // linearLayout.removeAllViews();
        if(child > 0){
            int pos;
            for (int i=0;i<child;i++){

                int index=i+1;
//layoutAirlines.removeAllViews();
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    layoutParams.setMarginStart(10);
                }

                linearLayout.setPadding(5,5,5,5);
                linearLayout.setLayoutParams(layoutParams);

                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                //linearLayout.setWeightSum(1);

                TextView textView=new TextView(context);
                textView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, .8f));
                textView.setText("Child "+index +"Age " +List.get(i).getAge());
                textView.setTextColor(context.getResources().getColor(R.color.black));

                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.txt_size_normal));


                pos=i;
                linearLayout.addView(textView);

                MyViewHolder.layoutChildAge.addView(linearLayout);
               /* if(roomList.get(position).isAddLayout()){

                        // MyViewHolder.layoutChildAge.removeAllViews();

                        for(int j=0;i< roomList.size();i++){
                            MyViewHolder.layoutChildAge.removeAllViews();
                            if(roomList.get(j).isAddLayout()){
                                int index1=j+1;
                                MyViewHolder.layoutChildAge.addView(linearLayout);
                            }
                        }




                }*/







            }


        }
        return MyViewHolder.layoutChildAge;

    }
}
