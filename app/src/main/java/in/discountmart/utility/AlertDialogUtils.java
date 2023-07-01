package in.discountmart.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import in.discountmart.R;
import in.discountmart.listener.AlertDialogButtonListener;

public class AlertDialogUtils {

    /**
     * param[0] == title
     * param[1] == message
     * param[2] == positive button text
     * param[3] = negative button text
     *
     * @param context
     * @param param
     */

    public static void showDialogWithOneButton(Context context, final AlertDialogButtonListener buttonListener, final String... param) {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(param[0])
                .setMessage(Html.fromHtml("<font color='#FF7F27'>"+param[1]+"</font>"))
                .setCancelable(false)
                .setIcon(context.getResources().getDrawable(R.mipmap.done));


        builder.setPositiveButton(param[2], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                buttonListener.onButtontapped(param[2]);
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    public static void showDialogWithTwoButton(Context context, final AlertDialogButtonListener buttonListener, final String... param) {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(param[0])
                .setMessage(Html.fromHtml("<font color='#FF7F27'>"+param[1]+"</font>"))
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

    public static void showDialogWithNeutaral(Context context, String title, String message) {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(Html.fromHtml("<font color='#FF7F27'>"+message+"</font>"))
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

    public static void showMatrialDialogSingleButton(Context context, String title, String message) {
        androidx.appcompat.app.AlertDialog dialog;
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context,R.style.AlertDialogTheme);
        builder.setTitle(title)
                .setMessage(Html.fromHtml("<font color='#FF7F27'>"+message+"</font>"))
                .setCancelable(false)
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
}
