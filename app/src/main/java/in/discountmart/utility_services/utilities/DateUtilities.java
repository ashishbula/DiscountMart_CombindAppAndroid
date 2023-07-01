package in.discountmart.utility_services.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateUtilities {

    public static boolean isNetworkEnabled(Context pContext) {
        NetworkInfo activeNetwork = getActiveNetwork(pContext);
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public static NetworkInfo getActiveNetwork(Context pContext) {
        ConnectivityManager conMngr = (ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return conMngr == null ? null : conMngr.getActiveNetworkInfo();
    }
    public static String convertDate(String date) {

        String[] d = date.split("");
        String year = d[1] + d[2] + d[3] + d[4];
        String month = d[5] + d[6];
        String day = d[7] + d[8];
        String value1 = day + "/" + month + "/" + year;
        return value1;
    }

    /*Method month number to month string(text)*/
    public static String convertMonthtoText(int month) {
        String Month = "";
        if (month == 1)
            Month = "Jan";
        else if (month == 2)
            Month = "Feb";
        else if (month == 3)
            Month = "Mar";
        else if (month == 4)
            Month = "Apr";
        else if (month == 5)
            Month = "May";
        else if (month == 6)
            Month = "Jun";
        else if (month == 7)
            Month = "July";
        else if (month == 8)
            Month = "Aug";
        else if (month == 9)
            Month = "Sep";
        else if (month == 10)
            Month = "Oct";
        else if (month == 11)
            Month = "Nov";
        else if (month == 12)
            Month = "Dec";
        return Month;
    }

    /*Method for Date spilt and convert */
    public static String SpiltandConvertDate(String date){
       String targetdatevalue;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.ENGLISH);
        Date sourceDate = null;
        try {
            sourceDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);
        targetdatevalue= targetFormat.format(sourceDate);

        String[] items1 = targetdatevalue.split("-");
        String d1=items1[0];
        String m1=items1[1];
        String y1=items1[2];

        int d = Integer.parseInt(d1);
        int m = Integer.parseInt(m1);
        int y = Integer.parseInt(y1);

        String dat= String.valueOf(d);
        String mon=convertMonthtoText(m);
        String yer=String.valueOf(y);
        String Date=dat + "-"+ mon+ "-" +yer;

        return Date;
    }

    /*Method for Date spilt*/
    public static String SpiltDate(String date){
        String targetdatevalue;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.ENGLISH);
        Date sourceDate = null;
        try {
            sourceDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
        targetdatevalue= targetFormat.format(sourceDate);

        return targetdatevalue;
    }

    public static String timeconverter(String time) {
        String[] timeArray= time.split(":");
        int hour= Integer.parseInt(timeArray[0]);
        String stringAmPm=" AM";
        if(hour == 00)
        {
            hour = 12;
            stringAmPm=" AM";
        }
        else if(hour == 12)
        {
            hour = 12;
            stringAmPm=" PM";
        }
        else if(hour < 12)
        {
            stringAmPm=" AM";
        }
        else
        {
            hour = hour-12;
            stringAmPm=" PM";
        }

         String hh = ConvertNumberIntoTwoDigit(Integer.toString(hour));
         String mm = ConvertNumberIntoTwoDigit(timeArray[1].toString());

        String finalTime = hh + " : "+ mm + stringAmPm;

        return finalTime;
    }

 public static String ConvertNumberIntoTwoDigit(String num)
    {
        String Number = num;

        if(Number.equals("1"))
            Number="01";
        else if(Number.equals("2"))
            Number="02";
        else if(Number.equals("3"))
            Number="03";
        else if(Number.equals("4"))
            Number="04";
        else if(Number.equals("5"))
            Number="05";
        else if(Number.equals("6"))
            Number="06";
        else if(Number.equals("7"))
            Number="07";
        else if(Number.equals("8"))
            Number="08";
        else if(Number.equals("9"))
            Number="09";
        else
            Number=Number;
        return Number;
    }

    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

    public static EditText edttxtDobUtility;


    // date picker open for Date and selected date set to edittext
    public static class SelectDateFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar1 = Calendar.getInstance();
            int yy = calendar1.get(Calendar.YEAR);
            int mm = calendar1.get(Calendar.MONTH);
            int dd = calendar1.get(Calendar.DAY_OF_MONTH);

            //enable date before one year
            DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, yy, mm, dd);


            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                int thisMonth = mm + 1;
                String stringTodayDate = dd + "/" + thisMonth + "/" + yy;
                Date todayDate = sdf.parse(stringTodayDate);
                dpd.getDatePicker().setMinDate(todayDate.getTime());

            } catch (ParseException e) {
                //handle exception
            }

            return dpd;
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            String day = DateUtilities.ConvertNumberIntoTwoDigit(Integer.toString(dd));
            String month = DateUtilities.convertMonthtoText(mm + 1);
            edttxtDobUtility.setText(day+"-"+(month)+"-"+yy);
        }
    }

    // date picker open for Date and selected date set to edittext
    public static class SelectDateFragment2 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar1 = Calendar.getInstance();
            int yy = calendar1.get(Calendar.YEAR);
            int mm = calendar1.get(Calendar.MONTH);
            int dd = calendar1.get(Calendar.DAY_OF_MONTH);

            //enable date before one year
            DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, yy, mm, dd);


            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                int thisMonth = mm + 1;
                String stringTodayDate = dd + "/" + thisMonth + "/" + yy;
                Date todayDate = sdf.parse(stringTodayDate);
                dpd.getDatePicker().setMaxDate(todayDate.getTime());

            } catch (ParseException e) {
                //handle exception
            }

            return dpd;
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            String day = DateUtilities.ConvertNumberIntoTwoDigit(Integer.toString(dd));
            String month = convertMonthtoText(mm + 1);
            edttxtDobUtility1.setText(day+"-"+(month)+"-"+yy);
        }
    }
    public static EditText edttxtDobUtility1;

    public static class SelectDateFragment1 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar1 = Calendar.getInstance();
            int yy = calendar1.get(Calendar.YEAR);
            int mm = calendar1.get(Calendar.MONTH);
            int dd = calendar1.get(Calendar.DAY_OF_MONTH);

            //enable date before one year
            DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, yy, mm, dd);


            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            int thisMonth = mm + 1;
            int year=yy-1;
            String strTodayDate = dd + "-" + thisMonth + "-" + year;
            //Date todayDate = sdf.parse(strTodayDate);
            Date todayDate = null;
            try {
                todayDate = sdf.parse(strTodayDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //dpd.getDatePicker().setMaxDate(todayDate.getTime());
            dpd.getDatePicker().setMaxDate(todayDate.getTime() - (todayDate.getTime() % (24 * 60 * 60 * 1000)));

            return dpd;
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            String day = DateUtilities.ConvertNumberIntoTwoDigit(Integer.toString(dd));
            String month = DateUtilities.convertMonthtoText(mm + 1);
            edttxtDobUtility1.setText(day+"-"+(month)+"-"+yy);
        }
    }


    public static EditText edttxtTimeUtility;
    public static Spinner spnrTimeAmPmUtility;

    // date picker open for Date and selected date set to edittext
    public static class SelectTimeFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar1 = Calendar.getInstance();
            int hr = calendar1.get(Calendar.HOUR);
            int min = calendar1.get(Calendar.MINUTE);

            TimePickerDialog dpd = new TimePickerDialog(getActivity(),this, hr, min,false);

            return dpd;
        }

        public void onTimeSet(TimePicker picker, int hr, int min) {
            String hour = DateUtilities.ConvertNumberIntoTwoDigit(Integer.toString(hr));
            String minute = DateUtilities.ConvertNumberIntoTwoDigit(Integer.toString(min + 1));
            String time= DateUtilities.timeconverter(hr+""+":"+min);
            edttxtTimeUtility.setText(time);

            if(hr<12)
                spnrTimeAmPmUtility.setSelection(0);
            else
                spnrTimeAmPmUtility.setSelection(1);

        }
    }
    private static final String PACKAGE = "com.google.zxing.client.android";

    public static AlertDialog showDownloadDialog(final Activity activity,
                                                 CharSequence stringTitle,
                                                 CharSequence stringMessage,
                                                 CharSequence stringButtonYes,
                                                 CharSequence stringButtonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(activity);
        downloadDialog.setTitle(stringTitle);
        downloadDialog.setMessage(stringMessage);
        downloadDialog.setPositiveButton(stringButtonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + PACKAGE);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    activity.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {
                    // Hmm, market is not installed
                    Log.w("tag", "Android Market is not installed; cannot install Barcode Scanner");
                }
            }
        });
        downloadDialog.setNegativeButton(stringButtonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        return downloadDialog.show();
    }

    public static String getDifferenceTime(String startDate, String endDate){
        String s="";

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss",Locale.US);

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(startDate);
            d2 = format.parse(endDate);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");
            if(diffDays == 0 && diffHours > 0 && diffMinutes > 0){
                s= diffHours + "h "+diffMinutes + "m";

            }
            else if(diffDays > 0 && diffHours > 0 && diffMinutes > 0){
                s= diffDays + "d "+diffHours + "h "+diffMinutes + "m";
            }
            else if(diffDays > 0 && diffHours > 0 && diffMinutes == 0){
                s= diffDays + "d "+diffHours + " h";
            }
            else if(diffDays > 0 && diffHours ==0 && diffMinutes == 0){
                s= diffDays + "d "+diffMinutes + "m";
            }
            else if(diffDays == 0 && diffHours > 0 && diffMinutes == 0){
                s= diffHours + "h ";
            }

        }catch (Exception e){
            e.printStackTrace();
        }



            return s;
    }


    public static String jsonToDateString(String strDate){
        String retDate="";
        SimpleDateFormat input = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = null;

        try {

            String jsondate=strDate;
            jsondate=jsondate.replace("/Date(", "").replace(")/", "");

            Calendar calendar = Calendar.getInstance();
            //calendar.setTimeInMillis(Long.parseLong(jsondate));

            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH + 1);
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);
           /*int time = Integer.parseInt(strDate.substring(6));
            Date d= new Date(time);*/
            Long timeInMillis = Long.valueOf(jsondate);
            calendar.setTimeInMillis(timeInMillis);
            retDate=calendar.getTime().toLocaleString();

            date = input.parse(retDate);
            retDate = output.format(date);

            //retDate=mDay+"-"+mMonth+"-"+mYear;

            // retDate= new SimpleDateFormat("mm-ddd-yyyy",Locale.ENGLISH).format(retDate);
        }catch (Exception e){
            e.printStackTrace();
        }

        return retDate;
    }


}
