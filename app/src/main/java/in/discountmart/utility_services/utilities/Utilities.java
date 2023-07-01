package in.discountmart.utility_services.utilities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;


public class Utilities {

    public static boolean isNetworkEnabled(Context pContext) {
        NetworkInfo activeNetwork = getActiveNetwork(pContext);
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public static NetworkInfo getActiveNetwork(Context pContext) {
        ConnectivityManager conMngr = (ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return conMngr == null ? null : conMngr.getActiveNetworkInfo();
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    public static String convertDate(String date) {

        String[] d = date.split("");
        String year = d[1] + d[2] + d[3] + d[4];
        String month = d[5] + d[6];
        String day = d[7] + d[8];
        String value1 = day + "/" + month + "/" + year;
        return value1;
    }

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

    public static String convertTextToNumMonth(String month) {
        String Month = "";
        if (month.contentEquals( "Jan"))
            Month = "01";
        else if (month.contentEquals("Feb"))
            Month = "02";
        else if (month.contentEquals("Mar"))
            Month = "03";
        else if (month.contentEquals("Apr"))
            Month = "04";
        else if (month.contentEquals("May"))
            Month = "05";
        else if (month.contentEquals("Jun"))
            Month = "06";
        else if (month.contentEquals("Jul"))
            Month = "07";
        else if (month.contentEquals("Aug"))
            Month = "08";
        else if (month.contentEquals("Sep"))
            Month = "09";
        else if (month.contentEquals("Oct"))
            Month = "10";
        else if (month.contentEquals("Nov"))
            Month = "11";
        else if (month.contentEquals("Dec"))
            Month = "12";
        return Month;
    }

    public static String timeconverter(String time)
    {
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

        String finalTime = hh + ":"+ mm + stringAmPm;

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

    public static String convertMonth(int month) {
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
            Month = "Jul";
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

    public static String convertMonthNumber(int month) {
        String Month = "";
        if (month == 1)
            Month = "01";
        else if (month == 2)
            Month = "02";
        else if (month == 3)
            Month = "03";
        else if (month == 4)
            Month = "04";
        else if (month == 5)
            Month = "05";
        else if (month == 6)
            Month = "06";
        else if (month == 7)
            Month = "07";
        else if (month == 8)
            Month = "08";
        else if (month == 9)
            Month = "09";
        else if (month == 10)
            Month = "10";
        else if (month == 11)
            Month = "11";
        else if (month == 12)
            Month = "12";
        return Month;
    }

    public static String convertdayNumber(int day) {
        String Day = "";
        if (day == 1)
            Day = "01";
        else if (day == 2)
            Day = "02";
        else if (day == 3)
            Day = "03";
        else if (day == 4)
            Day = "04";
        else if (day == 5)
            Day = "05";
        else if (day == 6)
            Day = "06";
        else if (day == 7)
            Day = "07";
        else if (day == 8)
            Day = "08";
        else if (day == 9)
            Day = "09";
        else
            Day = Integer.toString(day);

        return Day;
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

    public static Bitmap getclip(Bitmap bitmap) {
//		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//		Canvas canvas = new Canvas(output);
//
//		final Paint paint = new Paint();
//		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//		paint.setAntiAlias(true);
//		canvas.drawARGB(0, 0, 0, 0);
//		canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,bitmap.getWidth() / 2, paint);
//		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
//		canvas.drawBitmap(bitmap, rect, rect, paint);
//		return output;



        Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);

        Canvas c = new Canvas(circleBitmap);
        c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,bitmap.getWidth() / 2, paint);
        return circleBitmap;


    }

    public static void goToPage(Context paramContext, Class paramClass,
                                Bundle paramBundle) {
        Intent localIntent = new Intent(paramContext, paramClass);
        if (paramBundle != null)
            localIntent.putExtra("android.intent.extra.INTENT", paramBundle);
        paramContext.startActivity(localIntent);
    }

    /*public static String convertMonth(int month) {
        String Month="";
        if (month==1)
            Month = "Jan";
        else if (month==2)
            Month = "Fab";
        else if (month==3)
            Month = "Mar";
        else if (month==4)
            Month = "Apr";
        else if (month==5)
            Month = "May";
        else if (month==6)
            Month = "Jun";
        else if (month==7)
            Month = "July";
        else if (month==8)
            Month = "Aug";
        else if (month==9)
            Month = "Sep";
        else if (month==10)
            Month = "Oct";
        else if (month==11)
            Month = "Nov";
        else if (month==12)
            Month = "Dec";
        return Month;
    }*/
    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

}



