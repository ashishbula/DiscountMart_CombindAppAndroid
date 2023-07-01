package in.discountmart.business.data;

import android.app.ProgressDialog;
import android.content.Context;

public class ProcessLoaderIndication {

	ProgressDialog dialog;
	String message="Loading...";
	Context context;
	public ProcessLoaderIndication(Context context)
	{
		this.context=context;
		dialog=new ProgressDialog(context);
		
	}
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	
	public void showDialog()
	{
		this.dialog.setMessage(message);
		this.dialog.show();
		this.dialog.setCancelable(false);
	}
	public void hideDialog()
	{
		if (dialog.isShowing())
		{
			dialog.dismiss();
		}
	}
	
	}
