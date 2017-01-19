package tedxperiments.math.entrenamente;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {
	 public static int TYPE_WIFI = 1;
	 public static int TYPE_MOBILE = 2;
	 public static int TYPE_NOT_CONNECTED = 0;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		int status = getConnectivityStatus(context);
		switch(status){
		case 0://Toast.makeText(context, "No Connection", Toast.LENGTH_LONG).show();
			break;
		case 1:
			if(!PassLevel.isActivityVisible()){
				//Toast.makeText(context, "Wifi", Toast.LENGTH_LONG).show();
				new AsyncSend().execute(BuildConfig.SERVER_URL);}
			
			break;
		case 2://Toast.makeText(context, "Mobile", Toast.LENGTH_LONG).show();
			break;}
        

	}

	 public static int getConnectivityStatus(Context context) {
	        ConnectivityManager cm = (ConnectivityManager) context
	                .getSystemService(Context.CONNECTIVITY_SERVICE);
	 
	        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
	        if (null != activeNetwork) {
	            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
	                return TYPE_WIFI;
	             
	            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
	                return TYPE_MOBILE;
	        } 
	        return TYPE_NOT_CONNECTED;
	    }
	
	
}
