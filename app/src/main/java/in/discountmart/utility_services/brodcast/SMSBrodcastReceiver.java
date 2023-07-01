package in.discountmart.utility_services.brodcast;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSBrodcastReceiver extends BroadcastReceiver {

    public static final String SMS_ORIGIN = "MD-Vision";


    // special character to prefix the otp. Make sure this character appears only once in the sms
    public static final String OTP_DELIMITER = "Is";
    @Override
    public void onReceive(Context context, Intent intent) {
        String verificationCode="";
        final Bundle bundle = intent.getExtras();

        try{
            if (bundle != null)
            {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj .length; i++)
                {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[])                                                                                                    pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber ;
                    String message = currentMessage .getDisplayMessageBody();

                    // if the SMS is not from our gateway, ignore the message
                    if (!senderNum.toLowerCase().contains(SMS_ORIGIN.toLowerCase())) {
                        return;
                    }

// verification code from sms
                    verificationCode = getVerificationCode(message);

                    Log.e(TAG, "OTP received: " + verificationCode);

                    Intent hhtpIntent = new Intent(context,SMSBrodcastReceiver.class);
                    hhtpIntent.putExtra("otp", verificationCode);
                    context.startService(hhtpIntent);
                    //LocalBroadcastManager.getInstance(context).sendBroadcast(hhtpIntent);

                }
            }
        }catch (Exception e){
            Log.e("SmsException",e.toString());
        }



    }

    /**
     * Getting the OTP from sms message body
     * ':' is the separator of OTP from the message
     *
     * @param message
     * @return
     */
    private String getVerificationCode(String message) {
        String code = "";
        try{


            //String code = null;
            int index = message.indexOf(OTP_DELIMITER);

            if (index != 0) {
                int start = index + 3;
                int length = 5;
                code = message.substring(start, start + length);
                return code;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return code;
    }
}
