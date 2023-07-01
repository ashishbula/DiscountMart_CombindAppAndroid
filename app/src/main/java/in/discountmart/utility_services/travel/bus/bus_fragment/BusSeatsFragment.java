package in.discountmart.utility_services.travel.bus.bus_fragment;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import in.discountmart.R;
import in.discountmart.utility_services.travel.bus.bus_activity.BusSeatsActivity;
import in.discountmart.utility_services.travel.bus.bus_model.BusSeatModel;
import in.discountmart.utility_services.travel.bus.bus_model.bus_response.BusSeatsResponse;
import in.discountmart.utility_services.travel.bus.bus_sharedprference.BusSharedValues;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusSeatsFragment extends Fragment {

    TextView txtBtnLower;
    TextView txtBtnUpper;
    TableLayout tableLayoutLowerSeat;
    TableLayout tableLayoutUpperSeat;
    LinearLayout layoutLower;
    LinearLayout layoutUpper;
    BusSeatsResponse.BusSeats busSeats[];
    View view;
    ArrayList<BusSeatsResponse.BusSeats> busSeatsArrayList;
    ArrayList<BusSeatsResponse.BusSeats> busSeatUpperArrayList;
    ArrayList<BusSeatsResponse.BusSeats> busSeatLowerArrayList;

    ArrayList<Integer> dataList_row = new ArrayList<>();
    ArrayList<Integer> dataList_column = new ArrayList<>();
    int colMin = 0;
    int colMax = 0;
    int rowMin = 0;
    int rowMax = 0;

    Context context;

    int maxSeat=0;
    String Seat="";

    public BusSeatsFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView=inflater.inflate(R.layout.utility_fragment_bus_seats, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//        if (savedInstanceState != null) {
//            // Restore last state for checked position.
//            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
//        }
        try {
            context=getActivity();
            view=getActivity().findViewById(android.R.id.content);
            txtBtnLower=(TextView)mainView.findViewById(R.id.bus_seat_frag_txt_lower);
            txtBtnUpper=(TextView)mainView.findViewById(R.id.bus_seat_frag_txt_upper);
            tableLayoutLowerSeat=(TableLayout)mainView.findViewById(R.id.bus_seat_frag_table_lower_seat_map);
            tableLayoutUpperSeat=(TableLayout)mainView.findViewById(R.id.bus_seat_frag_table_upper_seat_map);
            layoutLower=(LinearLayout)mainView.findViewById(R.id.bus_seat_frag_layout_lower_seat_map);
            layoutUpper=(LinearLayout)mainView.findViewById(R.id.bus_seat_frag_layout_upper_seat_map);

            Bundle bundle=getArguments();
            if(bundle != null){

                BusSeatsResponse busSeatsResponse=(BusSeatsResponse)bundle.getSerializable("BusSeat");
                Seat= bundle.getString("MaxSeat");
                assert busSeatsResponse != null;
                busSeats=busSeatsResponse.getSeats();


                if(! Seat.equals("")){
                    maxSeat= Integer.parseInt(Seat);
                }
                else {
                    maxSeat=0;
                }


                busSeatsArrayList=new ArrayList<BusSeatsResponse.BusSeats>(Arrays.asList(busSeats));
               // busSeatLowerArrayList=new ArrayList<BusSeatsResponse.BusSeats>();
               // busSeatUpperArrayList=new ArrayList<BusSeatsResponse.BusSeats>();

                if(BusSharedValues.getInstance().busSeatsLowerList.size() > 0 ){
                    busSeatLowerArrayList=BusSharedValues.getInstance().busSeatsLowerList;
                }
                if(BusSharedValues.getInstance().busSeatsUpperList.size() > 0){
                    busSeatUpperArrayList=BusSharedValues.getInstance().busSeatsUpperList;
                }

            }
            /*if(busSeatsArrayList.size() > 0){
                for (int i=0; i < busSeatsArrayList.size(); i++){
                    if (busSeatsArrayList.get(i).getzIndex().equals("0")){

                        // busSeatsResponse1.setAvailable(busSeatsArrayList.get(i).getAvailable());
                        busSeatLowerArrayList.add(busSeatsArrayList.get(i));
                    }

                    else{
                        busSeatUpperArrayList.add(busSeatsArrayList.get(i));
                    }

                }
            }



            if(busSeatUpperArrayList.size() > 0 && busSeatLowerArrayList.size() > 0){
                BusSharedValues.getInstance().busSeatsUpperList=busSeatUpperArrayList;
                BusSharedValues.getInstance().busSeatsLowerList=busSeatLowerArrayList;
            }
            else {
                BusSharedValues.getInstance().busSeatsUpperList=busSeatUpperArrayList;
                BusSharedValues.getInstance().busSeatsLowerList=busSeatLowerArrayList;
            }*/



            //*********For Lower Seat*************//
            //***********Row calculation (Total No.of Row, Max Row , Min Row)
            for (int i = 0; i < busSeatLowerArrayList.size(); i++) {
                dataList_row.add(Integer.parseInt(busSeatLowerArrayList.get(i).getRow()));
            }
            Collections.sort(dataList_row);
            rowMin = dataList_row.get(0);
            rowMax = dataList_row.get(dataList_row.size() - 1);

            //************Column calculation (Total No.of Column, Max Column , Min Column)
            for (int i = 0; i < busSeatLowerArrayList.size(); i++) {
                dataList_column.add(Integer.parseInt(busSeatLowerArrayList.get(i).getColumn()));
            }
            Collections.sort(dataList_column);
            colMin = dataList_column.get(0);
            colMax = dataList_column.get(dataList_column.size() - 1);

            //***********Creating Lower Bus Seat Map using Row N Column
            layoutLower.setVisibility(View.VISIBLE);
            layoutUpper.setVisibility(View.GONE);
            //tableLayoutUpperSeat.setVisibility(View.GONE);
            //tableLayoutLowerSeat.setVisibility(View.VISIBLE);
            initLowerTable(busSeatLowerArrayList);

            /*Create Upper seat */

            //*********For upper Seat*************//
            //***********Row calculation (Total No.of Row, Max Row , Min Row)
                    for (int i = 0; i < busSeatUpperArrayList.size(); i++) {
                        dataList_row.add(Integer.parseInt(busSeatUpperArrayList.get(i).getRow()));
                    }
                    Collections.sort(dataList_row);
                    rowMin = dataList_row.get(0);
                    rowMax = dataList_row.get(dataList_row.size() - 1);

                    //************Column calculation (Total No.of Column, Max Column , Min Column)
                    for (int i = 0; i < busSeatUpperArrayList.size(); i++) {
                        dataList_column.add(Integer.parseInt(busSeatUpperArrayList.get(i).getColumn()));
                    }
                    Collections.sort(dataList_column);
                    colMin = dataList_column.get(0);
                    colMax = dataList_column.get(dataList_column.size() - 1);


            initUpperTable(busSeatUpperArrayList);

            /*Text Lower Seat on click show lower seat*/
            txtBtnLower.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        txtBtnUpper.setBackground(getResources().getDrawable(R.drawable.transparent_bg));
                        txtBtnLower.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                        txtBtnLower.setTextColor(getResources().getColor(R.color.white));
                        txtBtnUpper.setTextColor(getResources().getColor(R.color.gray));

                    }

                    layoutLower.setVisibility(View.VISIBLE);
                    layoutUpper.setVisibility(View.GONE);

                }
            });


            /*Text Upper Seat on click show upper seat*/
            txtBtnUpper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        txtBtnLower.setBackground(getResources().getDrawable(R.drawable.transparent_bg));
                        txtBtnUpper.setBackground(getResources().getDrawable(R.drawable.colorprimary_bg_box));
                        txtBtnLower.setTextColor(getResources().getColor(R.color.gray));
                        txtBtnUpper.setTextColor(getResources().getColor(R.color.white));

                    }

                    layoutLower.setVisibility(View.GONE);
                    layoutUpper.setVisibility(View.VISIBLE);


                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
        return mainView;

    }

    @Override

    public void onSaveInstanceState(Bundle outState)  {
        super.onSaveInstanceState(outState);
        //outState.putInt("curChoice", mCurCheckPosition);
    }

    public void initLowerTable(final ArrayList<BusSeatsResponse.BusSeats> list) {

        //*********** Column For Loop

            if(tableLayoutLowerSeat != null)
                tableLayoutLowerSeat.removeAllViews();


            for (int col = colMin; col <= colMax; col++) {
            final int indexCol = col;
            TableRow tbrow = new TableRow(context);
            TableRow.LayoutParams layoutParams=new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5,5,5,5);
            tbrow.setLayoutParams(layoutParams);
            tbrow.setGravity(Gravity.TOP|Gravity.CENTER);

            int blankSeat=0;
            //*********** Row For Loop
            for (int row = rowMax; row >= rowMin; row--) {
                final int indexRow = row;

                final RelativeLayout relativeLayout=new RelativeLayout(context);
                relativeLayout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                relativeLayout.setGravity(Gravity.CENTER);
                relativeLayout.setPadding(5, 5, 5, 5);

                //relativeLayout.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);

                /*Image for seat */
                final ImageView imageSeat = new ImageView(context);
                imageSeat.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                imageSeat.setScaleType(ImageView.ScaleType.FIT_XY);
                //imageSeat.setPadding(5, 5, 5, 5);

                /*TExt for seat name */
                final TextView txtSeatName=new TextView(context);
                txtSeatName.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                //txtSeatName.setTextSize(getResources().getDimension(R.dimen.txt_size_v_small));
                txtSeatName.setPadding(10,10,10,10);

                /*For image seat set center*/
                LayoutParams lpImage = (LayoutParams) imageSeat.getLayoutParams();
                lpImage.addRule(RelativeLayout.CENTER_IN_PARENT);
                imageSeat.setLayoutParams(lpImage);


                /*For text set center*/
                LayoutParams lpText = (LayoutParams) txtSeatName.getLayoutParams();
                lpText.addRule(RelativeLayout.CENTER_IN_PARENT);
                txtSeatName.setLayoutParams(lpText);

                boolean flagSeatExist = false;
                boolean flagSeatIsAvailable = false;


                //*********** For Loop checking list every element if containing particular row n col
                for (int i = 0; i < list.size(); i++) {
                    final int index = i;

                    //*********** If row, col mathes list row, col
                    if (indexCol == Integer.parseInt(list.get(i).getColumn()) &&
                            indexRow == Integer.parseInt(list.get(i).getRow())) {

                       final  boolean flagSeatIsSleeper ;

                        //*********** If Seat iS  Sitting or Sleeper
                        // Length is 1 than it is seat
                        if (list.get(i).getLength().equals("1") && list.get(i).getWidth().equals("1"))  {

                            //*********** If Seat Available or not
                            if (list.get(i).getAvailable().equalsIgnoreCase("true")) {
                                imageSeat.setImageResource(R.mipmap.utility_seat_available);

                                txtSeatName.setText(list.get(i).getName());

                                flagSeatIsAvailable = true;
                            }
                            else if(list.get(i).getAvailable().equalsIgnoreCase("false")) {
                                imageSeat.setImageResource(R.mipmap.utility_seat_booked);
                                txtSeatName.setText(list.get(i).getName());

                                flagSeatIsAvailable = false;
                            }
                            //******check seat for ladies is available or not******//
                           else if (list.get(i).getLadiesSeat().equalsIgnoreCase("true") &&
                                    list.get(i).getAvailable().equalsIgnoreCase("true")) {
                                imageSeat.setImageResource(R.mipmap.utility_seat_ladies);
                                txtSeatName.setText(list.get(i).getName());

                                flagSeatIsAvailable = true;
                            }
                            else if (list.get(i).getLadiesSeat().equalsIgnoreCase("false") &&
                                    list.get(i).getAvailable().equalsIgnoreCase("false")) {

                                imageSeat.setImageResource(R.mipmap.utility_seat_ladies_boked);
                                txtSeatName.setText(list.get(i).getName());

                                flagSeatIsAvailable = false;
                            }
                            //*********** Seat iS  Sitting
                            flagSeatIsSleeper = false;


                        }
                        // Length is 2 than it is utility_sleeper
                        else  {
                            //*********** If utility_sleeper Available or not
                            if (list.get(i).getAvailable().equalsIgnoreCase("true")) {
                                imageSeat.setImageResource(R.mipmap.utility_sleeper_available);
                                txtSeatName.setText(list.get(i).getName());

                                flagSeatIsAvailable = true;

                            }
                            else if(list.get(i).getAvailable().equalsIgnoreCase("false")){

                                imageSeat.setImageResource(R.mipmap.utility_sleeper_booked);
                                txtSeatName.setText(list.get(i).getName());

                                flagSeatIsAvailable = false;
                            }

                            //******check sleepr for ladies is available or not******//
                           else if (list.get(i).getLadiesSeat().equalsIgnoreCase("true") &&
                                    list.get(i).getAvailable().equalsIgnoreCase("true"))
                           {
                               imageSeat.setImageResource(R.mipmap.utility_sleeper_ladies);
                               txtSeatName.setText(list.get(i).getName());

                               flagSeatIsAvailable = true;
                            }
                            else if (list.get(i).getLadiesSeat().equalsIgnoreCase("false") &&
                                    list.get(i).getAvailable().equalsIgnoreCase("false"))
                            {
                                imageSeat.setImageResource(R.mipmap.utility_sleeper_booked_ladies);
                                txtSeatName.setText(list.get(i).getName());

                                flagSeatIsAvailable = false;
                            }
                            //*********** Seat iS  Sleeper
                            flagSeatIsSleeper = true;

                        }


                        relativeLayout.addView(imageSeat);
                        relativeLayout.addView(txtSeatName);
                        tbrow.addView(relativeLayout);

                        flagSeatExist = true;

                        //*********** Click event perform only when Seat is Available
                        if (flagSeatIsAvailable) {
                            imageSeat.setOnClickListener(new View.OnClickListener() {
                                Boolean flagSeatSelectedAlready = false;

                                @Override
                                public void onClick(View view) {

                                    if(maxSeat == 0){
                                        if (flagSeatIsSleeper && flagSeatSelectedAlready) {
                                            //imageView0.setImageResource(R.mipmap.available_sleeper);
                                            imageSeat.setImageResource(R.mipmap.utility_sleeper_available);
                                            flagSeatSelectedAlready = false;
                                            BusSeatModel model=new BusSeatModel();
                                            model.setSeatType(list.get(index).getLength());
                                            model.setSeat(list.get(index).getName());
                                            model.setAmount(list.get(index).getFare());
                                            model.setLadiesSeat(list.get(index).getLadiesSeat());
                                            CalculateSeatAndFare1(model,"","minus");

                                           //CalculateSeatAndFare(list.get(index).getName().toString(), list.get(index).getFare(),list.get(index).getLength(), "minus");
                                        } else if (flagSeatIsSleeper && !flagSeatSelectedAlready) {
                                            //imageView0.setImageResource(R.mipmap.select_sleeper);
                                            imageSeat.setImageResource(R.mipmap.utility_sleeper_select);
                                            flagSeatSelectedAlready = true;

                                            BusSeatModel model=new BusSeatModel();
                                            model.setSeatType(list.get(index).getLength());
                                            model.setSeat(list.get(index).getName());
                                            model.setAmount(list.get(index).getFare());
                                            model.setLadiesSeat(list.get(index).getLadiesSeat());
                                            CalculateSeatAndFare1(model,"","add");
                                            // Toast.makeText(context, "Seat Name= " + list.get(index).getName() + "\n" + "Fare= " + list.get(index).getFare(), Toast.LENGTH_LONG).show();

                                            //CalculateSeatAndFare(list.get(index).getName().toString(), list.get(index).getFare(), list.get(index).getLength(),"add");

                                        } else if (!flagSeatIsSleeper && flagSeatSelectedAlready) {
                                            //imageView0.setImageResource(R.mipmap.available_seat);
                                            imageSeat.setImageResource(R.mipmap.utility_seat_available);
                                            flagSeatSelectedAlready = false;
                                            BusSeatModel model=new BusSeatModel();
                                            model.setSeatType(list.get(index).getLength());
                                            model.setSeat(list.get(index).getName());
                                            model.setAmount(list.get(index).getFare());
                                            model.setLadiesSeat(list.get(index).getLadiesSeat());
                                            CalculateSeatAndFare1(model,"","minus");
                                            //CalculateSeatAndFare(list.get(index).getName().toString(), list.get(index).getFare(),list.get(index).getLength(), "minus");
                                        } else if (!flagSeatIsSleeper && !flagSeatSelectedAlready) {
                                            //imageView0.setImageResource(R.mipmap.select_seat);
                                            imageSeat.setImageResource(R.mipmap.utility_seat_select);
                                            flagSeatSelectedAlready = true;

                                            BusSeatModel model=new BusSeatModel();
                                            model.setSeatType(list.get(index).getLength());
                                            model.setSeat(list.get(index).getName());
                                            model.setAmount(list.get(index).getFare());
                                            model.setLadiesSeat(list.get(index).getLadiesSeat());
                                            CalculateSeatAndFare1(model,"","add");
                                            // Toast.makeText(context, "Seat Name= " + list.get(index).getName() + "\n" + "Fare= " + list.get(index).getFare(), Toast.LENGTH_LONG).show();
                                            //CalculateSeatAndFare(list.get(index).getName().toString(), list.get(index).getFare(), list.get(index).getLength(),"add");

                                        }
                                    }
                                    else{

                                        if(BusSeatsActivity.arrayListSeatName.size() == maxSeat) {
                                                if (flagSeatIsSleeper && flagSeatSelectedAlready) {
                                                    //imageView0.setImageResource(R.mipmap.available_sleeper);
                                                    imageSeat.setImageResource(R.mipmap.utility_sleeper_available);
                                                    flagSeatSelectedAlready = false;
                                                    BusSeatModel model=new BusSeatModel();
                                                    model.setSeatType(list.get(index).getLength());
                                                    model.setSeat(list.get(index).getName());
                                                    model.setAmount(list.get(index).getFare());
                                                    model.setLadiesSeat(list.get(index).getLadiesSeat());
                                                    CalculateSeatAndFare1(model,"","minus");
                                                    //CalculateSeatAndFare(list.get(index).getName().toString(), list.get(index).getFare(),list.get(index).getLength(), "minus");
                                                } else if (flagSeatIsSleeper && !flagSeatSelectedAlready) {

                                                    Toast.makeText(getActivity(),"Can not select " + maxSeat +" seat",Toast.LENGTH_SHORT).show();

                                                } else if (!flagSeatIsSleeper && flagSeatSelectedAlready) {
                                                    //imageView0.setImageResource(R.mipmap.available_seat);
                                                    imageSeat.setImageResource(R.mipmap.utility_seat_available);
                                                    flagSeatSelectedAlready = false;
                                                    BusSeatModel model=new BusSeatModel();
                                                    model.setSeatType(list.get(index).getLength());
                                                    model.setSeat(list.get(index).getName());
                                                    model.setAmount(list.get(index).getFare());
                                                    model.setLadiesSeat(list.get(index).getLadiesSeat());
                                                    CalculateSeatAndFare1(model,"","minus");

                                                   // CalculateSeatAndFare(list.get(index).getName().toString(), list.get(index).getFare(),list.get(index).getLength(), "minus");
                                                } else if (!flagSeatIsSleeper && !flagSeatSelectedAlready) {

                                                    Toast.makeText(getActivity(),"Can not select " + maxSeat +" seat",Toast.LENGTH_SHORT).show();

                                                }
                                        }

                                        else if(BusSeatsActivity.arrayListSeatName.size() < maxSeat){


                                            if (flagSeatIsSleeper && flagSeatSelectedAlready) {
                                                //imageView0.setImageResource(R.mipmap.available_sleeper);
                                                imageSeat.setImageResource(R.mipmap.utility_sleeper_available);
                                                flagSeatSelectedAlready = false;
                                                BusSeatModel model=new BusSeatModel();
                                                model.setSeatType(list.get(index).getLength());
                                                model.setSeat(list.get(index).getName());
                                                model.setAmount(list.get(index).getFare());
                                                model.setLadiesSeat(list.get(index).getLadiesSeat());
                                                CalculateSeatAndFare1(model,"","minus");

                                                //CalculateSeatAndFare(list.get(index).getName().toString(), list.get(index).getFare(),list.get(index).getLength(), "minus");
                                            } else if (flagSeatIsSleeper && !flagSeatSelectedAlready) {
                                                //imageView0.setImageResource(R.mipmap.select_sleeper);
                                                imageSeat.setImageResource(R.mipmap.utility_sleeper_select);
                                                flagSeatSelectedAlready = true;

                                                BusSeatModel model=new BusSeatModel();
                                                model.setSeatType(list.get(index).getLength());
                                                model.setSeat(list.get(index).getName());
                                                model.setAmount(list.get(index).getFare());
                                                model.setLadiesSeat(list.get(index).getLadiesSeat());
                                                CalculateSeatAndFare1(model,"","add");
                                                // Toast.makeText(context, "Seat Name= " + list.get(index).getName() + "\n" + "Fare= " + list.get(index).getFare(), Toast.LENGTH_LONG).show();
                                               //CalculateSeatAndFare(list.get(index).getName().toString(), list.get(index).getFare(),list.get(index).getLength(), "add");

                                            } else if (!flagSeatIsSleeper && flagSeatSelectedAlready) {
                                                //imageView0.setImageResource(R.mipmap.available_seat);
                                                imageSeat.setImageResource(R.mipmap.utility_seat_available);
                                                flagSeatSelectedAlready = false;

                                                BusSeatModel model=new BusSeatModel();
                                                model.setSeatType(list.get(index).getLength());
                                                model.setSeat(list.get(index).getName());
                                                model.setAmount(list.get(index).getFare());
                                                model.setLadiesSeat(list.get(index).getLadiesSeat());
                                                CalculateSeatAndFare1(model,"","minus");

                                                //CalculateSeatAndFare(list.get(index).getName().toString(), list.get(index).getFare(),list.get(index).getLength(), "minus");
                                            } else if (!flagSeatIsSleeper && !flagSeatSelectedAlready) {
                                                //imageView0.setImageResource(R.mipmap.select_seat);
                                                imageSeat.setImageResource(R.mipmap.utility_seat_select);
                                                flagSeatSelectedAlready = true;

                                                BusSeatModel model=new BusSeatModel();
                                                model.setSeatType(list.get(index).getLength());
                                                model.setSeat(list.get(index).getName());
                                                model.setAmount(list.get(index).getFare());
                                                model.setLadiesSeat(list.get(index).getLadiesSeat());

                                                CalculateSeatAndFare1(model,"","add");
                                                // Toast.makeText(context, "Seat Name= " + list.get(index).getName() + "\n" + "Fare= " + list.get(index).getFare(), Toast.LENGTH_LONG).show();
                                               // CalculateSeatAndFare(list.get(index).getName().toString(), list.get(index).getFare(),list.get(index).getLength(), "add");

                                            }
                                        }
                                        else {


                                            Toast.makeText(getActivity(),"Can not select " + maxSeat +" seat",Toast.LENGTH_SHORT).show();
                                        }
                                    }



                                }
                            });
                        }
                    }

                    /*else {
                        relativeLayout.addView(imageSeat);
                        relativeLayout.addView(txtSeatName);
                        //tbrow.addView(relativeLayout);
                    }*/
                }
                if (!flagSeatExist) {
                    blankSeat++;
                    imageSeat.setImageResource(R.mipmap.utility_seat_available);
                    //imageView0.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    imageSeat.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

                    imageSeat.setVisibility(View.INVISIBLE);
                    relativeLayout.addView(imageSeat);
                    relativeLayout.addView(txtSeatName);
                    tbrow.addView(relativeLayout);
                }
            }
            if(blankSeat<4)
                tableLayoutLowerSeat.addView(tbrow);
        }
    }


    public void initUpperTable(final ArrayList<BusSeatsResponse.BusSeats> list) {

        //*********** Column For Loop

            if(tableLayoutUpperSeat != null)
              tableLayoutUpperSeat.removeAllViews();



        for (int col = colMin; col <= colMax+1; col++) {
            final int indexCol = col;
            TableRow tbrow = new TableRow(context);
            TableRow.LayoutParams layoutParams=new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5,5,5,5);
            tbrow.setLayoutParams(layoutParams);
            tbrow.setGravity(Gravity.TOP|Gravity.CENTER);

            int blankSeat=0;
            //*********** Row For Loop
            for (int row = rowMax; row >= rowMin; row--) {
                final int indexRow = row;

                /*Set Relative layout*/
                final RelativeLayout relativeLayout=new RelativeLayout(context);
                relativeLayout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                relativeLayout.setGravity(Gravity.CENTER);
                relativeLayout.setPadding(5,5,5,5);


                /*Set Image*/
                final ImageView imageView0 = new ImageView(context);
                imageView0.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                imageView0.setScaleType(ImageView.ScaleType.FIT_XY);
                //imageView0.setPadding(5, 5, 5, 5);

                /*Set Text*/
                final TextView txtSeatName=new TextView(context);
                txtSeatName.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                //txtSeatName.setTextSize(getResources().getDimension(R.dimen.txt_size_v_small));
                txtSeatName.setPadding(10,10,10,10);

                /*Set params for image*/
                LayoutParams lpImage = (LayoutParams) imageView0.getLayoutParams();
                lpImage.addRule(RelativeLayout.CENTER_IN_PARENT);
                imageView0.setLayoutParams(lpImage);

                /*Set params for text*/
                LayoutParams lpText = (LayoutParams) txtSeatName.getLayoutParams();
                lpText.addRule(RelativeLayout.CENTER_IN_PARENT);
                txtSeatName.setLayoutParams(lpText);

                boolean flagSeatExist = false;
                boolean flagSeatIsAvailable = false;


                //*********** For Loop checking list every element if containing particular row n col
                for (int i = 0; i < list.size(); i++) {
                    final int index = i;

                    //*********** If row, col mathes list row, col
                    if (indexCol == Integer.parseInt(list.get(i).getColumn()) &&
                            indexRow == Integer.parseInt(list.get(i).getRow())) {

                       final   boolean flagSeatIsSleeper;

                        //*********** If Seat iS  Sitting or Sleeper
                        // Length is 1 than it is seat
                        if (list.get(i).getLength().equals("1") && list.get(i).getWidth().equals("1"))  {

                            //*********** If Seat Available or not
                            if (list.get(i).getAvailable().equalsIgnoreCase("true")) {
                                imageView0.setImageResource(R.mipmap.utility_seat_available);
                                txtSeatName.setText(list.get(i).getName());

                                flagSeatIsAvailable = true;
                            }
                            else if(list.get(i).getAvailable().equalsIgnoreCase("false")) {
                                imageView0.setImageResource(R.mipmap.utility_seat_booked);
                                txtSeatName.setText(list.get(i).getName());

                                flagSeatIsAvailable = false;
                            }
                            //******check seat for ladies is available or not******//
                            else if (list.get(i).getLadiesSeat().equalsIgnoreCase("true") &&
                                    list.get(i).getAvailable().equalsIgnoreCase("true")) {
                                imageView0.setImageResource(R.mipmap.utility_seat_ladies);
                                txtSeatName.setText(list.get(i).getName());

                            }
                            else if (list.get(i).getLadiesSeat().equalsIgnoreCase("false") &&
                                    list.get(i).getAvailable().equalsIgnoreCase("false")) {
                                imageView0.setImageResource(R.mipmap.utility_seat_ladies_boked);
                                txtSeatName.setText(list.get(i).getName());

                            }
                            //*********** Seat iS  Sitting
                            flagSeatIsSleeper = false;


                        }
                        // Length is 2 than it is utility_sleeper
                        else {
                            //*********** If utility_sleeper Available or not
                            if (list.get(i).getAvailable().equalsIgnoreCase("true")) {
                                imageView0.setImageResource(R.mipmap.utility_sleeper_available);
                                txtSeatName.setText(list.get(i).getName());

                                flagSeatIsAvailable = true;

                            }
                            else if(list.get(i).getAvailable().equalsIgnoreCase("false")){
                                imageView0.setImageResource(R.mipmap.utility_sleeper_booked);
                                txtSeatName.setText(list.get(i).getName());

                                flagSeatIsAvailable = false;
                            }

                            //******check sleepr for ladies is available or not******//
                            else if (list.get(i).getLadiesSeat().equalsIgnoreCase("true") &&
                                    list.get(i).getAvailable().equalsIgnoreCase("true"))
                            {
                                imageView0.setImageResource(R.mipmap.utility_sleeper_ladies);
                                txtSeatName.setText(list.get(i).getName());

                            }
                            else if (list.get(i).getLadiesSeat().equalsIgnoreCase("false") &&
                                    list.get(i).getAvailable().equalsIgnoreCase("false"))
                            {
                                imageView0.setImageResource(R.mipmap.utility_sleeper_booked_ladies);
                                txtSeatName.setText(list.get(i).getName());

                            }
                            //*********** Seat iS  Sleeper
                            flagSeatIsSleeper = true;

                        }

                        relativeLayout.addView(imageView0);
                        relativeLayout.addView(txtSeatName);
                        tbrow.addView(relativeLayout);
                        //tbrow.addView(imageView0);
                        flagSeatExist = true;

                        //*********** Click event perform only when Seat is Available
                        if (flagSeatIsAvailable) {
                            imageView0.setOnClickListener(new View.OnClickListener() {
                                Boolean flagSeatSelectedAlready = false;

                                @Override
                                public void onClick(View view) {

                                    if(maxSeat == 0){
                                        if (flagSeatIsSleeper && flagSeatSelectedAlready) {
                                            imageView0.setImageResource(R.mipmap.utility_sleeper_available);
                                            flagSeatSelectedAlready = false;
                                            BusSeatModel model=new BusSeatModel();
                                            model.setSeatType(list.get(index).getLength());
                                            model.setSeat(list.get(index).getName());
                                            model.setAmount(list.get(index).getFare());
                                            model.setLadiesSeat(list.get(index).getLadiesSeat());
                                            CalculateSeatAndFare1(model,"","minus");
                                            //CalculateSeatAndFare(list.get(index).getName().toString(), list.get(index).getFare(),list.get(index).getLength(), "minus");
                                        } else if (flagSeatIsSleeper && !flagSeatSelectedAlready) {
                                            imageView0.setImageResource(R.mipmap.utility_sleeper_select);
                                            flagSeatSelectedAlready = true;
                                            BusSeatModel model=new BusSeatModel();
                                            model.setSeatType(list.get(index).getLength());
                                            model.setSeat(list.get(index).getName());
                                            model.setAmount(list.get(index).getFare());
                                            model.setLadiesSeat(list.get(index).getLadiesSeat());
                                            CalculateSeatAndFare1(model,"","add");
                                            // Toast.makeText(context, "Seat Name= " + list.get(index).getName() + "\n" + "Fare= " + list.get(index).getFare(), Toast.LENGTH_LONG).show();
                                           //CalculateSeatAndFare(list.get(index).getName().toString(), list.get(index).getFare(),list.get(index).getLength(), "add");

                                        } else if (!flagSeatIsSleeper && flagSeatSelectedAlready) {
                                            imageView0.setImageResource(R.mipmap.utility_seat_available);
                                            flagSeatSelectedAlready = false;
                                            BusSeatModel model=new BusSeatModel();
                                            model.setSeatType(list.get(index).getLength());
                                            model.setSeat(list.get(index).getName());
                                            model.setAmount(list.get(index).getFare());
                                            model.setLadiesSeat(list.get(index).getLadiesSeat());
                                            CalculateSeatAndFare1(model,"","minus");
                                            //CalculateSeatAndFare(list.get(index).getName().toString(), list.get(index).getFare(),list.get(index).getLength(), "minus");
                                        } else if (!flagSeatIsSleeper && !flagSeatSelectedAlready) {
                                            imageView0.setImageResource(R.mipmap.utility_seat_select);
                                            flagSeatSelectedAlready = true;

                                            BusSeatModel model=new BusSeatModel();
                                            model.setSeatType(list.get(index).getLength());
                                            model.setSeat(list.get(index).getName());
                                            model.setAmount(list.get(index).getFare());
                                            model.setLadiesSeat(list.get(index).getLadiesSeat());
                                            CalculateSeatAndFare1(model,"","add");
                                            // Toast.makeText(context, "Seat Name= " + list.get(index).getName() + "\n" + "Fare= " + list.get(index).getFare(), Toast.LENGTH_LONG).show();
                                           // CalculateSeatAndFare(list.get(index).getName().toString(), list.get(index).getFare(),list.get(index).getLength(), "add");

                                        }
                                    }
                                    else {
                                        if(BusSeatsActivity.arrayListSeatName.size() == maxSeat) {
                                            if (flagSeatIsSleeper && flagSeatSelectedAlready) {
                                                //imageView0.setImageResource(R.mipmap.available_sleeper);
                                                imageView0.setImageResource(R.mipmap.utility_sleeper_available);
                                                flagSeatSelectedAlready = false;
                                                BusSeatModel model=new BusSeatModel();
                                                model.setSeatType(list.get(index).getLength());
                                                model.setSeat(list.get(index).getName());
                                                model.setAmount(list.get(index).getFare());
                                                model.setLadiesSeat(list.get(index).getLadiesSeat());
                                                CalculateSeatAndFare1(model,"","minus");

                                               // CalculateSeatAndFare(list.get(index).getName().toString(), list.get(index).getFare(),list.get(index).getLength(), "minus");
                                            } else if (flagSeatIsSleeper && !flagSeatSelectedAlready) {

                                                Toast.makeText(getActivity(),"Can not select " + maxSeat +" seat",Toast.LENGTH_SHORT).show();

                                            } else if (!flagSeatIsSleeper && flagSeatSelectedAlready) {
                                                //imageView0.setImageResource(R.mipmap.available_seat);
                                                imageView0.setImageResource(R.mipmap.utility_seat_available);
                                                flagSeatSelectedAlready = false;
                                                BusSeatModel model=new BusSeatModel();
                                                model.setSeatType(list.get(index).getLength());
                                                model.setSeat(list.get(index).getName());
                                                model.setAmount(list.get(index).getFare());
                                                model.setLadiesSeat(list.get(index).getLadiesSeat());
                                                CalculateSeatAndFare1(model,"","minus");

                                                //CalculateSeatAndFare(list.get(index).getName().toString(), list.get(index).getFare(),list.get(index).getLength(), "minus");
                                            } else if (!flagSeatIsSleeper && !flagSeatSelectedAlready) {

                                                Toast.makeText(getActivity(),"Can not select " + maxSeat +" seat",Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                        else if(BusSeatsActivity.arrayListSeatName.size() < maxSeat){


                                            if (flagSeatIsSleeper && flagSeatSelectedAlready) {
                                                //imageView0.setImageResource(R.mipmap.available_sleeper);
                                                imageView0.setImageResource(R.mipmap.utility_sleeper_available);
                                                flagSeatSelectedAlready = false;
                                                BusSeatModel model=new BusSeatModel();
                                                model.setSeatType(list.get(index).getLength());
                                                model.setSeat(list.get(index).getName());
                                                model.setAmount(list.get(index).getFare());
                                                model.setLadiesSeat(list.get(index).getLadiesSeat());
                                                CalculateSeatAndFare1(model,"","minus");

                                                //CalculateSeatAndFare(list.get(index).getName().toString(), list.get(index).getFare(), list.get(index).getLength(),"minus");
                                            } else if (flagSeatIsSleeper && !flagSeatSelectedAlready) {
                                                //imageView0.setImageResource(R.mipmap.select_sleeper);
                                                imageView0.setImageResource(R.mipmap.utility_sleeper_select);
                                                flagSeatSelectedAlready = true;
                                                BusSeatModel model=new BusSeatModel();
                                                model.setSeatType(list.get(index).getLength());
                                                model.setSeat(list.get(index).getName());
                                                model.setAmount(list.get(index).getFare());
                                                model.setLadiesSeat(list.get(index).getLadiesSeat());
                                                CalculateSeatAndFare1(model,"","add");

                                                // Toast.makeText(context, "Seat Name= " + list.get(index).getName() + "\n" + "Fare= " + list.get(index).getFare(), Toast.LENGTH_LONG).show();
                                               //CalculateSeatAndFare(list.get(index).getName().toString(), list.get(index).getFare(),list.get(index).getLength(), "add");

                                            } else if (!flagSeatIsSleeper && flagSeatSelectedAlready) {
                                                //imageView0.setImageResource(R.mipmap.available_seat);
                                                imageView0.setImageResource(R.mipmap.utility_seat_available);
                                                flagSeatSelectedAlready = false;
                                                BusSeatModel model=new BusSeatModel();
                                                model.setSeatType(list.get(index).getLength());
                                                model.setSeat(list.get(index).getName());
                                                model.setAmount(list.get(index).getFare());
                                                model.setLadiesSeat(list.get(index).getLadiesSeat());
                                                CalculateSeatAndFare1(model,"","minus");

                                                //CalculateSeatAndFare(list.get(index).getName().toString(), list.get(index).getFare(),list.get(index).getLength(), "minus");
                                            } else if (!flagSeatIsSleeper && !flagSeatSelectedAlready) {
                                                //imageView0.setImageResource(R.mipmap.select_seat);
                                                imageView0.setImageResource(R.mipmap.utility_seat_select);
                                                flagSeatSelectedAlready = true;

                                                BusSeatModel model=new BusSeatModel();
                                                model.setSeatType(list.get(index).getLength());
                                                model.setSeat(list.get(index).getName());
                                                model.setAmount(list.get(index).getFare());
                                                model.setLadiesSeat(list.get(index).getLadiesSeat());
                                                CalculateSeatAndFare1(model,"","add");
                                                // Toast.makeText(context, "Seat Name= " + list.get(index).getName() + "\n" + "Fare= " + list.get(index).getFare(), Toast.LENGTH_LONG).show();
                                                //CalculateSeatAndFare(list.get(index).getName().toString(), list.get(index).getFare(),list.get(index).getLength(), "add");

                                            }
                                        }
                                        else {


                                            Toast.makeText(getActivity(),"Can not select more from" + maxSeat +" seat",Toast.LENGTH_SHORT).show();
                                        }
                                    }



                                }
                            });
                        }
                    }
                    /*else {
                        relativeLayout.addView(imageView0);
                        relativeLayout.addView(txtSeatName);
                        //tbrow.addView(relativeLayout);
                    }*/
                }
                if (!flagSeatExist) {
                    blankSeat++;
                    imageView0.setImageResource(R.mipmap.utility_seat_available);
                    //imageView0.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    imageView0.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

                    imageView0.setVisibility(View.INVISIBLE);
                    relativeLayout.addView(imageView0);
                    relativeLayout.addView(txtSeatName);
                    tbrow.addView(relativeLayout);
                }
            }
            if(blankSeat<4)
                tableLayoutUpperSeat.addView(tbrow);
        }
    }

    public void CalculateSeatAndFare(String seatName, String seatFare, String seatType,String addOrMinus) {

        if (addOrMinus.equalsIgnoreCase("add")) {

            BusSeatsActivity.arrayListSeatName.add(seatName);
            BusSeatsActivity.arrayListSeatFare.add(seatFare);
            BusSeatsActivity.arrayListSeatType.add(seatType);
            BusSeatsActivity.doubleFare = BusSeatsActivity.doubleFare + (Double.parseDouble(seatFare));

        } else {
            BusSeatsActivity.arrayListSeatFare.remove(BusSeatsActivity.arrayListSeatName.indexOf(seatFare));
            BusSeatsActivity.arrayListSeatName.remove(seatName);
            BusSeatsActivity.arrayListSeatType.remove(seatType);
            BusSeatsActivity.doubleFare = BusSeatsActivity.doubleFare - (Double.parseDouble(seatFare));
        }

        if (BusSeatsActivity.arrayListSeatName.size()<=0) {
            BusSeatsActivity.stringSeatName = "";
            // BusSeatMapActivity.stringSeatFare = "";

        }
        else if (BusSeatsActivity.arrayListSeatName.size()==1) {
            BusSeatsActivity.stringSeatName = BusSeatsActivity.arrayListSeatName.get(0);
            //BusSeatMapActivity.stringSeatFare = BusSeatMapActivity.arrayListSeatFare.get(0);

        }else {
            BusSeatsActivity.stringSeatName = android.text.TextUtils.join(", ", BusSeatsActivity.arrayListSeatName);
            //BusSeatMapActivity.stringSeatFare = android.text.TextUtils.join(", ", BusSeatMapActivity.arrayListSeatFare);
        }




        BusSeatsActivity.txtTotalSeat.setText("Seats "+BusSeatsActivity.stringSeatName);
        BusSeatsActivity.txtFareAmount.setText(getResources().getString(R.string.rs_symbol)+" "+Double.toString(BusSeatsActivity.doubleFare));

        BusSharedValues.getInstance().SelectedSeatList =BusSeatsActivity.arrayListSeatName;
        BusSharedValues.getInstance().SelectedSeatFare=BusSeatsActivity.arrayListSeatFare;
        BusSharedValues.getInstance().SelectedSeatType=BusSeatsActivity.arrayListSeatType;
        BusSharedValues.getInstance().TotalFare = BusSeatsActivity.doubleFare;
    }

    public void CalculateSeatAndFare1(BusSeatModel seatName, String seatFare, String addOrMinus) {

        try {
            if (addOrMinus.equalsIgnoreCase("add")) {



                BusSeatsActivity.busSeatArrayList.add(seatName);
                //BusSeatsActivity.busSeatArrayList.add(seatFare);
                BusSeatsActivity.doubleFare = BusSeatsActivity.doubleFare + (Double.parseDouble(seatName.getAmount()));

            } else {

                //BusSeatsActivity.busSeatArrayList.remove(BusSeatsActivity.busSeatArrayList.indexOf(seatName));
                for(int i =0; i < BusSeatsActivity.busSeatArrayList.size(); i++){
                    if(BusSeatsActivity.busSeatArrayList.get(i).getSeat().equals(seatName.getSeat())){
                        int index=i;
                        BusSeatsActivity.busSeatArrayList.remove(index);
                    }
                }
                // BusSeatsActivity.busSeatArrayList.remove(seatName);
                // BusSeatsActivity.arrayListSeatName.remove(seatName);
                BusSeatsActivity.doubleFare = BusSeatsActivity.doubleFare - (Double.parseDouble(seatName.getAmount()));
            }

            if (BusSeatsActivity.busSeatArrayList.size()<=0) {
                BusSeatsActivity.stringSeatName = "";
                // BusSeatMapActivity.stringSeatFare = "";

            }
            else if (BusSeatsActivity.busSeatArrayList.size()==1) {
                BusSeatsActivity.stringSeatName = "";
                BusSeatsActivity.stringSeatName = BusSeatsActivity.busSeatArrayList.get(0).getSeat();
                //BusSeatMapActivity.stringSeatFare = BusSeatMapActivity.arrayListSeatFare.get(0);

            }else {

                StringBuilder sb=new StringBuilder();
                BusSeatsActivity.stringSeatName = "";
                for (int i=0; i < BusSeatsActivity.busSeatArrayList.size(); i++) {
                    if(BusSeatsActivity.stringSeatName.equals("")){
                        BusSeatsActivity.stringSeatName= BusSeatsActivity.busSeatArrayList.get(i).getSeat();
                    }
                    else {
                        BusSeatsActivity.stringSeatName= BusSeatsActivity.stringSeatName+ ","+BusSeatsActivity.busSeatArrayList.get(i).getSeat();
                    }
                }

                // BusSeatsActivity.stringSeatName = sb.toString();
                // BusSeatsActivity.stringSeatName = TextUtils.join(", ", Collections.singleton(BusSeatsActivity.busSeatArrayList.get(BusSeatsActivity.busSeatArrayList.size()).getSeat()));
                //BusSeatMapActivity.stringSeatFare = android.text.TextUtils.join(", ", BusSeatMapActivity.arrayListSeatFare);

           /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                BusSeatsActivity.stringSeatName= BusSeatsActivity.busSeatArrayList.stream().map(a -> String.valueOf(a.getSeat())).collect(Collectors.joining(","));
            }*/
            }




            BusSeatsActivity.txtTotalSeat.setText("Seats "+BusSeatsActivity.stringSeatName);
            BusSeatsActivity.txtFareAmount.setText(getResources().getString(R.string.rs_symbol)+" "+Double.toString(BusSeatsActivity.doubleFare));

            BusSharedValues.getInstance().busSeatModelArrayList=BusSeatsActivity.busSeatArrayList;
            BusSharedValues.getInstance().SelectedSeatList =BusSeatsActivity.arrayListSeatName;
            BusSharedValues.getInstance().SelectedSeatFare=BusSeatsActivity.arrayListSeatFare;
            BusSharedValues.getInstance().TotalFare = BusSeatsActivity.doubleFare;
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void onResume(){
        super.onResume();


    }



}
