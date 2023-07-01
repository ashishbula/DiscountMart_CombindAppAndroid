package in.discountmart.business.fragment;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import in.discountmart.R;
import in.discountmart.business.model_business.responsemodel.RewardResponse;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class NextRewardFragment extends Fragment {

    TableLayout tableLayNextRecord;
    ScrollView scrollView;
    ImageView imgNoResult;

   // ArrayList<RewardResponse.NextReward> nextRewardArrayList;
    ArrayList<Map<String ,String>>nextRewardList;
    RewardResponse rewardResponse;
    //MatchingRecognitionResponse.PendingRecognition pendingRecognitions[];

    Context context;
    public NextRewardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_next_reward, container, false);
        try {

            tableLayNextRecord = (TableLayout) v.findViewById(R.id.next_reward_frag_tablelayout_record);
            scrollView = (ScrollView) v.findViewById(R.id.next_reward_frag_scroll_result);
            imgNoResult = (ImageView) v.findViewById(R.id.next_reward_frag_img_noresult);
            context = getActivity();

            Bundle bundle = getArguments();
            if (bundle != null) {
                //bundle=getArguments();
                nextRewardList = (ArrayList<Map<String ,String>>) bundle.getSerializable("NextRecog");


                if(nextRewardList != null && nextRewardList.size() > 0 ){
                    scrollView.setVisibility(View.VISIBLE);
                    imgNoResult.setVisibility(View.GONE);
                    addNextRewardTableData(nextRewardList);
                }
                else {
                    scrollView.setVisibility(View.GONE);
                    imgNoResult.setVisibility(View.VISIBLE);
                }
                // pendingRecognitions=matchRecog.getPendingrewards();
            } else {

                scrollView.setVisibility(View.GONE);
                imgNoResult.setVisibility(View.VISIBLE);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
            return v;

    }


    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(context);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());
        //tv.setOnClickListener(context);
        return tv;
    }


    @NonNull
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.VERTICAL);
    }

    /**
     * This function add and show the dynamic data of
     * the Pending Reward Report in the table Content
     *
     **/

    public void addNextRewardTableData(ArrayList<Map<String, String>> walletList ) {
        tableLayNextRecord.setVisibility(View.VISIBLE);
        //tableLayoutRecord.removeAllViews();
        ArrayList<Map<String, String>> mylist1 = new ArrayList<Map<String, String>>();
        mylist1=walletList;

        // Map<String,String> arrayMap = mylist1.get(0);
        //arrayMap=mylist1.get(0);
        //Log.d("ArrayMap: -",arrayMap.keySet().toString());

        for (int i = 0; i < mylist1.size(); i++)
        {
            Map<String, String> map =mylist1.get(i);
            //Object obj= map.keySet().toArray()[i];
            // get a reference for the TableLayout
            //TableLayout table = (TableLayout) findViewById(R.id.TableLayout01);

            // create a new TableRow
            TableRow tableRow = new TableRow(context);

            /*TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);

            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;

            tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
                    bottomMargin);

            tableRow.setLayoutParams(tableRowParams);
            tableRow.setBackgroundColor(getResources().getColor(R.color.gray));*/


            for(Object obj : map.keySet()){


                Object objKey=map.get(obj);
                Log.d("Key - ", String.valueOf(obj));
                TableRow tr = new TableRow(context);
                tableRow.setLayoutParams(getLayoutParams());
                if (i==0){
                    tableRow.addView(getTextView(i, String.valueOf(obj), Color.WHITE, Typeface.BOLD, Color.BLACK));
                }
                else
                    tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.NORMAL, getResources().getColor(R.color.LightGray)));
                //tableRow.addView(getTextView(i, String.valueOf(objKey), Color.BLACK, Typeface.BOLD, Color.BLUE));

            }
            tableLayNextRecord.addView(tableRow);

        }

    }

    /*public void initTable(final ArrayList<RewardResponse.NextReward> achiveRewards) {
        //remove all rows if exist already

        tableLayNextRecord.setVisibility(View.VISIBLE);
        tableLayNextRecord.removeAllViews();

        TableRow tbrow0 = new TableRow(context);

        TextView tvh_1 = new TextView(context);

        tvh_1.setText(" ");

        tvh_1.setGravity(Gravity.CENTER);

        tvh_1.setPadding(10, 10, 10, 10);

        tbrow0.addView(tvh_1);

        *//*Heading Text S No*//*
        TextView tvh_2 = new TextView(context);

        tvh_2.setText("S.No");

        tvh_2.setTextColor(getResources().getColor(R.color.black));

        tvh_2.setBackgroundResource(R.drawable.white_bg_box_black_border);

        tvh_2.setGravity(Gravity.CENTER);

        tvh_2.setPadding(10, 10, 10, 10);

        tvh_2.setTypeface(null, Typeface.BOLD);

        tbrow0.addView(tvh_2);

        *//*Heading Text Left Reward Point*//*
        TextView tvh_3 = new TextView(context);


        tvh_3.setText("Required Left Reward Point");

        tvh_3.setTextColor(getResources().getColor(R.color.black));

        tvh_3.setBackgroundResource(R.drawable.white_bg_box_black_border);

        tvh_3.setGravity(Gravity.CENTER);

        tvh_3.setPadding(10, 10, 10, 10);

        tvh_3.setTypeface(null, Typeface.BOLD);

        tbrow0.addView(tvh_3);

        *//*Heading Text Right Reward Point*//*
        TextView tvh_4 = new TextView(context);

        tvh_4.setText("Required Right Reward Point");

        tvh_4.setTextColor(getResources().getColor(R.color.black));

        tvh_4.setBackgroundResource(R.drawable.white_bg_box_black_border);

        tvh_4.setGravity(Gravity.CENTER);

        tvh_4.setPadding(10, 10, 10, 10);

        tvh_4.setTypeface(null, Typeface.BOLD);

        tbrow0.addView(tvh_4);

        *//*Heading Text Reward*//*
        TextView tvh_5 = new TextView(context);

        tvh_5.setText("Reward");

        tvh_5.setTextColor(getResources().getColor(R.color.black));

        tvh_5.setBackgroundResource(R.drawable.white_bg_box_black_border);

        tvh_5.setGravity(Gravity.CENTER);

        tvh_5.setPadding(10, 10, 10, 10);

        tvh_5.setTypeface(null, Typeface.BOLD);

        tbrow0.addView(tvh_5);

        *//*Heading Text Rank*//*
        TextView tvh_6 = new TextView(context);

        tvh_6.setText("Rank");

        tvh_6.setTextColor(getResources().getColor(R.color.black));

        tvh_6.setBackgroundResource(R.drawable.white_bg_box_black_border);

        tvh_6.setGravity(Gravity.CENTER);

        tvh_6.setPadding(10, 10, 10, 10);

        tvh_6.setTypeface(null, Typeface.BOLD);

        tbrow0.addView(tvh_6);

        tableLayNextRecord.addView(tbrow0);

        *//*Add Data*//*
        for (int i = 0; i < achiveRewards.size(); i++) {

            TableRow tbrow1 = new TableRow(context);

            final int index = i;

            TextView tvd_1 = new TextView(context);
            tvd_1.setText(" ");
            tvd_1.setGravity(Gravity.CENTER);
            tvd_1.setPadding(10, 10, 10, 10);
            tbrow1.addView(tvd_1);

            *//*Serial Numaber*//*
            try{
                TextView tvd_2 = new TextView(context);
                tvd_2.setGravity(Gravity.CENTER);
                tvd_2.setBackgroundResource(R.drawable.white_bg_box_black_border);
                tvd_2.setPadding(10, 10, 10, 10);
                //tvd_2.setText(index);

                tvd_2.setText(String.valueOf(index+1));
                tbrow1.addView(tvd_2);
            }catch (Exception e){
                e.printStackTrace();
            }

            *//*Left Reward pont*//*
            TextView tvd_3 = new TextView(context);

            tvd_3.setEllipsize(TextUtils.TruncateAt.END);

            // tv12.setMaxLines(1);

            tvd_3.setText(achiveRewards.get(i).getRequiredleftrewardpoint());

            tvd_3.setTextColor(Color.BLACK);

            tvd_3.setBackgroundResource(R.drawable.white_bg_box_black_border);

            tvd_3.setGravity(Gravity.LEFT);

            tvd_3.setPadding(10, 10, 10, 10);

            tbrow1.addView(tvd_3);

            *//*Right Reward pont*//*

            TextView tvd_4 = new TextView(context);

            tvd_4.setEllipsize(TextUtils.TruncateAt.END);

            tvd_4.setTextColor(Color.BLACK);

            tvd_4.setBackgroundResource(R.drawable.white_bg_box_black_border);

            tvd_4.setGravity(Gravity.CENTER);

            tvd_4.setPadding(10, 10, 10, 10);

            tvd_4.setText(achiveRewards.get(i).getRequiredrightrewardpoint());

            tbrow1.addView(tvd_4);

            *//*Reward*//*

            TextView tvd_5 = new TextView(context);

            tvd_5.setEllipsize(TextUtils.TruncateAt.END);

            tvd_5.setTextColor(Color.BLACK);

            tvd_5.setBackgroundResource(R.drawable.white_bg_box_black_border);

            tvd_5.setGravity(Gravity.CENTER);

            tvd_5.setPadding(10, 10, 10, 10);

            tvd_5.setText(achiveRewards.get(i).getReward());

            tbrow1.addView(tvd_5);

            *//*Rank*//*

            TextView tvd_6 = new TextView(context);

            tvd_6.setEllipsize(TextUtils.TruncateAt.END);

            tvd_6.setTextColor(Color.BLACK);

            tvd_6.setBackgroundResource(R.drawable.white_bg_box_black_border);

            tvd_6.setGravity(Gravity.CENTER);

            tvd_6.setPadding(10, 10, 10, 10);

            tvd_6.setText(achiveRewards.get(i).getRank());

            tbrow1.addView(tvd_6);

            tableLayNextRecord.addView(tbrow1);


        }


    }*/

}
