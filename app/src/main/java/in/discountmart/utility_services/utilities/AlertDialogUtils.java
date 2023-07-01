
package in.discountmart.utility_services.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import in.discountmart.R;
import in.discountmart.utility_services.listener.AlertDialogButtonListener;

public class AlertDialogUtils {

    public static void showDialogWithOneButton(Context context, String title, String message, final AlertDialogButtonListener buttonListner) {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(Html.fromHtml("<textfont color='#000000'>" + message + "</textfont>"))
                .setCancelable(false)
                .setIcon(context.getResources().getDrawable(R.mipmap.done));
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                buttonListner.onButtontapped("Ok");
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    /**
     * param[0] == title
     * param[1] == message
     * param[2] == positive button text
     * param[3] = negative button text
     *
     * @param context
     * @param param
     */
    public static void showDialogWithTwoButton(Context context, final AlertDialogButtonListener buttonListener, final String... param) {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(param[0])
                .setMessage(Html.fromHtml("<textfont color='#000000'>" + param[1] + "</textfont>"))
                .setIcon(context.getResources().getDrawable(R.mipmap.done))
                .setCancelable(false);
        builder.setPositiveButton(param[2], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
                buttonListener.onButtontapped(param[2]);
            }
        });
        builder.setNegativeButton(param[3], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
                buttonListener.onButtontapped(param[3]);
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    public static void showDialog(Context context, String title, String message) {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(Html.fromHtml("<textfont color='#000000'>" + message + "</textfont>"))
                .setIcon(context.getResources().getDrawable(R.mipmap.done));
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    public static void showAlertdialogContext (Context context,final AlertDialogButtonListener buttonListener, String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(
                context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss();
                        buttonListener.onButtontapped("NO");
                    }
                });
        builder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss();
                        buttonListener.onButtontapped("YES");
                    }
                });
        builder.show();
    }

    public static void showMaterialDialogWithTwoButton(Context context, final AlertDialogButtonListener buttonListener, final String... param) {
        androidx.appcompat.app.AlertDialog dialog;
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context,R.style.AlertDialogTheme);

        builder.setTitle(param[0])
                //.setMessage(Html.fromHtml("<textfont color='#000000'>" + param[1] + "</textfont>"))
                .setMessage( param[1])
                .setIcon(context.getResources().getDrawable(R.mipmap.done));

        builder.setPositiveButton(param[2], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
                buttonListener.onButtontapped(param[2]);
            }
        });
        builder.setNegativeButton(param[3], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
                buttonListener.onButtontapped(param[3]);
            }
        });
        dialog = builder.create();
        dialog.show();
    }
}
