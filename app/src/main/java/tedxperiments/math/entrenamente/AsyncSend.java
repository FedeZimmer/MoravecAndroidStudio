package tedxperiments.math.entrenamente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class AsyncSend extends AsyncTask<String, Void, String> {
	
	   @Override
	    protected String doInBackground(String... urls) {
		   try{		   
			   Handler_sqlite helper = new Handler_sqlite(MainActivity.THEcontext);
			   helper.open();
			   Cursor cursor = helper.fetchAllReminders();
			   //helper.close();
			   
			   //String thepost=POST(urls[0],sendthis);
			   if (cursor.moveToFirst()) {
				   do {
					   String sendthis = cursor.getString(cursor.getColumnIndex("Data_to_Send")); // id is column name in db
					   int issent = cursor.getInt(cursor.getColumnIndex("SENT")); // id is column name in db
					   
					   if (issent==0)
					   {
						   if(isConnected()){   
							   String url=urls[0];
							   String jsonData = sendthis;
							   InputStream inputStream = null;
							   String result = "";
							   boolean sendok=false;
							   
							   try {        	 
								   // 1. create HttpClient
								   HttpClient httpclient = new DefaultHttpClient();
								   
								   // 2. make POST request to the given URL
								   HttpPost httpPost = new HttpPost(url);
								   String ToSend = "";
								   
								   // 3. build jsonObject
								   JSONObject jsonObject = new JSONObject();
								   jsonObject.put("test_subject", "HerokuTest");
								   jsonObject.put("experiment_log",jsonData.toString());
								   jsonObject.put("experiment_name","MoravecData_v02");
								   //jsonObject.put("experiment_name","Entrenamente_v4");
								   //jsonObject.put("experiment_name","Entrenamente");
								   
								   // 4. convert JSONObject to JSON to String
								   ToSend = jsonObject.toString();
	            
								   // 5. set json to StringEntity
								   StringEntity se = new StringEntity(ToSend);
								   
								   // 6. set httpPost Entity
								   httpPost.setEntity(se);
	            
								   // 7. Set some headers to inform server about the type of the content
								   se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
								   httpPost.setHeader("Accept", "application/json");
								   //httpPost.setHeader("ContentType", "application/json");
	 
								   // 8. Execute POST request to the given URL
								   HttpResponse httpResponse = httpclient.execute(httpPost);
								   int code = httpResponse.getStatusLine().getStatusCode();
								   if (code==HttpStatus.SC_CREATED) {sendok=true;}
	 
								   // 9. receive response as inputStream
								   inputStream = httpResponse.getEntity().getContent();
						   						   	 
								   // 10. convert inputstream to string
								   if(inputStream != null)
								   {result = convertInputStreamToString(inputStream);}
						   		   else
						   			   result = "Did not work";
								   
							   } catch (Exception e) {
								   Log.d("InputStream", e.getLocalizedMessage());
						   
							   }
							   //String thepost=result;
							   if (sendok){
								   //marco con uno porque se mando
								   helper.updateReminder(cursor.getLong(cursor.getColumnIndex("_id")));
								   
								   //Cursor cursor2 = helper.fetchReminder(1);
								   //int testthis = cursor2.getInt(cursor2.getColumnIndex("SENT")); // id is column name in db
								   //helper.deleteReminder(cursor.getLong(cursor.getColumnIndex("_id")));
								   //helper.close();
							   }
						   }}
					   else if(issent==1)
					   {helper.deleteReminder(cursor.getLong(cursor.getColumnIndex("_id")));
					   
					   }
					   
			   		} while (cursor.moveToNext());
			   }
			   
			   helper.close();
			   
		   }catch (Exception destr){};
		   return "Done";
	   	}
	   
	   public boolean isConnected(){
	        ConnectivityManager connMgr = (ConnectivityManager) MainActivity.THEcontext.getSystemService(Activity.CONNECTIVITY_SERVICE);
	            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	            if (networkInfo != null && networkInfo.isConnected()) 
	                return true;
	            else
	                return false;    
	    }
	 

		// onPostExecute displays the results of the AsyncTask.
	    @Override
	    protected void onPostExecute(String result) {
	    	
	        //Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
	        //DScoreList.remove(0);
	        
	        
	   }
	    
	    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
	        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
	        String line = "";
	        String result = "";
	        while((line = bufferedReader.readLine()) != null)
	            result += line;

	        inputStream.close();
	        return result;

	    }   
	    
	    public static String POST(String url, String jsonData){
		  	InputStream inputStream = null;
	        String result = "";
	        
	       
	        
	        try {        	 
	            // 1. create HttpClient
	            HttpClient httpclient = new DefaultHttpClient();
	 
	            // 2. make POST request to the given URL
	            HttpPost httpPost = new HttpPost(url);
	            String ToSend = "";
	 
	            // 3. build jsonObject
	            	            
	  
	            JSONObject jsonObject = new JSONObject();
	            jsonObject.put("test_subject", "Federico");
	            jsonObject.put("experiment_log",jsonData.toString());
	            jsonObject.put("experiment_name","Entrenamente");
	            
	            	            	            
	            // 4. convert JSONObject to JSON to String
	            ToSend = jsonObject.toString();
	            
	            
	            // 5. set json to StringEntity
	            StringEntity se = new StringEntity(ToSend);
	 
	            // 6. set httpPost Entity
	            httpPost.setEntity(se);
	            
	            
	 
	            // 7. Set some headers to inform server about the type of the content
	            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	            httpPost.setHeader("Accept", "application/json");
	           //httpPost.setHeader("ContentType", "application/json");
	            
	 
	            // 8. Execute POST request to the given URL
	            HttpResponse httpResponse = httpclient.execute(httpPost);
	 
	            // 9. receive response as inputStream
	            inputStream = httpResponse.getEntity().getContent();
	 
	            // 10. convert inputstream to string
	            if(inputStream != null)
	                result = convertInputStreamToString(inputStream);
	            else
	                result = "Did not work!";
	 
	        } catch (Exception e) {
	            Log.d("InputStream", e.getLocalizedMessage());
	            //GuardoDato
	        }
	        
	        // 11. return result
	        //Este es el que me dice que llego bien. Usarlo para borrar
	           return result;
	    }
	

}
