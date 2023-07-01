package in.discountmart.utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public static byte[] getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    /*public void blankValueFromSharePreference(Context context) {
        SharedPrefrence.setPassword(context, "");
        SharedPrefrence.setUserID(context, "");
        SharedPrefrence.setUsername(context, "");
        SharedPrefrence.setProfileIamge(context,"");
        SharedPrefrence.setIsActive(context,"");
        SharedPrefrence.setUserMobileNumber(context,"");

        String toast= "Invalid login detail. Please login again";
        Toast.makeText(context,toast,Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        context.getApplicationContext().finish();
    }*/

}