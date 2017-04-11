package tedxperiments.math.entrenamente;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PassLevel {
	public static void setNewLevel(String key, Integer value, Context context) {
	    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putInt(key, value);
	    editor.commit();
	}

	public static Integer getCurrentLevel(String key, Context context) {
	    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
	     return preferences.getInt(key, 0);//Aca tiene que ir 0
	}
	
	public static void setNewPersonal(String key, String value, Context context) {
	    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(key, value);
	    editor.commit();
	}
	
	public static String getPersonal(String key, Context context) {
	    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
	     return preferences.getString(key, null);
	}
	
	public static void setAskPersonal(String key, Integer value, Context context) {
	    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putInt(key, value);
	    editor.commit();
	}
	
	public static Integer getAskPersonal(String key, Context context) {
	    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
	     return preferences.getInt(key, 0);
	}
	
	public static void setNcorrect(String key, int[] values, Context context) {
		StringBuilder valueStr = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
		    valueStr.append(values[i]).append(",");
		}
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(key, valueStr.toString());
	    editor.commit();
	}
	
	public static int[] getNcorrect(String key, Context context) {
		 SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		 String valueStr=preferences.getString(key, null);
		 int[] savedValues = new int[25];
		 if (valueStr!=null)
		 	{StringTokenizer st = new StringTokenizer(valueStr, ",");
		 	for (int i = 0; i < 25; i++) {
		 		savedValues[i] = Integer.parseInt(st.nextToken());
		 	}
		 	}
		 else {for (int i = 0; i < 25; i++) {
		 		savedValues[i] = 0;}}
		 
	    return savedValues;
	}
	
	public static void setTheTimes(String key, long[] values, Context context) {
		StringBuilder valueStr = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
		    valueStr.append(values[i]).append(",");
		}
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(key, valueStr.toString());
	    editor.commit();
	}
	
	public static long[] getTheTimes(String key, Context context) {
		 SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		 String valueStr=preferences.getString(key, null);
		 long[] savedValues = new long[25];
		 if (valueStr!=null)
		 	{StringTokenizer st = new StringTokenizer(valueStr, ",");
		 	for (int i = 0; i < 25; i++) {
		 		savedValues[i] = Long.parseLong(st.nextToken());
		 	}
		 	}
		 else {for (int i = 0; i < 25; i++) {
		 		savedValues[i] = 0;}}
		 
	    return savedValues;
	}	
	
	
	public static void setArcadeStats(String key, int[] values, Context context) {
		StringBuilder valueStr = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
		    valueStr.append(values[i]).append(",");
		}
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(key, valueStr.toString());
	    editor.commit();
	}
	
	public static int[] getArcadeStats(String key, Context context) {
		 SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		 String valueStr=preferences.getString(key, null);
		 int[] savedValues = new int[150];
		 if (valueStr!=null)
		 	{StringTokenizer st = new StringTokenizer(valueStr, ",");
		 	for (int i = 0; i < 150; i++) {
		 		savedValues[i] = Integer.parseInt(st.nextToken());
		 	}
		 	}
		 else {for (int i = 0; i < 150; i++) {
		 		savedValues[i] = 0;}}
		 //va 0
		 
	    return savedValues;
	}
	
	public static void setTimesStats(String key, ArrayList<Long> values, Context context) {
		StringBuilder valueStr = new StringBuilder();
		for (int i = 0; i < values.size(); i++) {
		    valueStr.append(values.get(i)).append(",");
		}
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(key, valueStr.toString());
	    editor.commit();
	}
	
	public static ArrayList<Long> getTimesStats(String key, Context context) {
		 SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		 String valueStr=preferences.getString(key, null);
		 ArrayList<Long> savedValues = new ArrayList<Long>();
		 if (valueStr!=null)
		 	{StringTokenizer st = new StringTokenizer(valueStr, ",");
		 	while (st.countTokens()>0){
		 	//for (int i = 0; i < XXXXX; i++) {
		 		savedValues.add(Long.parseLong(st.nextToken()));
		 	}
		 	}
		 //else {
			 //for (int i = 0; i < XXX; i++) {
		 		//savedValues.add((long) 0);}
		 //}
		 
	    return savedValues;
	}


	public static void setTrialNumber(long trialNumber, Context context) {

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putLong("trial_number", trialNumber);
		editor.commit();
	}

	public static long getTrialNumber(Context context) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		Long value=preferences.getLong("trial_number", 0);
		return value;
	}
	
	
	public static boolean isActivityVisible() {
	    return activityVisible;
	  }  

	  public static void activityResumed() {
	    activityVisible = true;
	  }

	  public static void activityPaused() {
	    activityVisible = false;
	  }

	  private static boolean activityVisible;
	

}
