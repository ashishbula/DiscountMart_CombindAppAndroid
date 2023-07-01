package in.discountmart.utility_services.utilities;

import android.util.Base64;

public class TokenBase64 {
    public static String base64EncodeToken(String token) {
        //byte[] encodedBytes = Base64.encode(token.getBytes());
        String encodedBytes =Base64.encodeToString(token.toString().getBytes(), Base64.NO_WRAP);
        return encodedBytes;
    }


    public static String base64Decode(String token) {
        //byte[] decodedBytes = Base64.decode(token.getBytes());
        String stringFromBase = new String(Base64.decode(token, Base64.DEFAULT));
        //return new String(decodedBytes, Charset.forName("UTF-8"));
        return stringFromBase;
    }

    public static String getToken()
    {
        String plainText = "APIAccess"+"API@123";
        //String plainText = "LifeAPIAccess"+"Life4API@123";
//String authorizationString = Base64.encodeToString(plainText.toString().getBytes(), Base64.DEFAULT ); // contain \n
        String authorizationString = Base64.encodeToString((plainText).getBytes(), Base64.NO_WRAP);
        return authorizationString;
    }
}
