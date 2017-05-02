package tedxperiments.math.entrenamente;

import android.content.pm.PackageInfo;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JsonUtil {
	
	public static JSONObject toJSon(DataScore DataObj,String theAUID, String theName, String theEmail,String theBirth,String theGender, String theStudies, String theHand ,String theLanguage, String theNumberOfLanguages,
	String musicListener, String musicInstrumentist,String musicTheory) {
		
		try {
			// Here we convert Java Object to JSON 
			
			//Toda la data subject.
			//private int level;
			//private int operand_1;
			//private int operand_2;
			//private long CorrResult;
			//private long EnteredAnswer;
			//ArrayList<Integer> vectorAnswer = new ArrayList<Integer>();
			//private int correct;
			//private int erase;
			//ArrayList<Long> resptime = new ArrayList<Long>();
			//private long totaltime;
			//private int confidence;
			//private int effort;
			//	private int initalconf;//Esto no esta en el Json
			//		private int initialeff;//Esto no esta en el Json
			//		ArrayList<Integer> vectorConf = new ArrayList<Integer>();//Esto no esta en el Json
			//		ArrayList<Long> confTime = new ArrayList<Long>();//Esto no esta en el Json
			//		ArrayList<Integer> vectorEffort = new ArrayList<Integer>();//Esto no esta en el Json ESTO NO FUNCIONA porque arrastra.
			//		ArrayList<Long> effortTime = new ArrayList<Long>();//Esto no esta en el Json
			//		private Calendar expdate1; //Es un objeto que tiene (int year, int month, int day, int hour, int minute, int second)
			//		private Calendar expdate2;
			
			//	private int tref;//Esto no se si habria que mandarlo
			//	private long OneScore;
			//Score? NUMERO DE TRIAL
			
			//Toda la data subject.
			
			JSONArray jsonBirthVector = new JSONArray();
			
			if (theBirth==null || theBirth==""){}
			else{
			String [] dateStrings = theBirth.split(" ");
			jsonBirthVector.put(Integer.parseInt(dateStrings[2]));
			jsonBirthVector.put(Integer.parseInt(dateStrings[1]));
			jsonBirthVector.put(Integer.parseInt(dateStrings[0]));}
			
			
			
			JSONObject jsonObj = new JSONObject();
			JSONObject jsonPersonalData = new JSONObject();
			if (theAUID==null)jsonPersonalData.put("AUID", "ID not detected");
			else jsonPersonalData.put("AUID", theAUID);

			if (DataObj.getArcorder() == 1) {
				if (theName == null) jsonPersonalData.put("Name", "");
				else jsonPersonalData.put("Name", theName);
				if (theEmail == null) jsonPersonalData.put("Email", "");
				else jsonPersonalData.put("Email", theEmail);
				if (theBirth == null || theBirth == "") {
					jsonPersonalData.put("Birthdate", "");
				} else {
					jsonPersonalData.put("Birthdate", jsonBirthVector);
				}
				if (theGender == null) jsonPersonalData.put("Gender", "");
				else jsonPersonalData.put("Gender", theGender);
				if (theStudies == null) jsonPersonalData.put("Studies", "");
				else jsonPersonalData.put("Studies", theStudies);
				if (theHand == null) jsonPersonalData.put("Hand", "");
				else jsonPersonalData.put("Hand", theHand);
				if (theLanguage == null) jsonPersonalData.put("Native_Language", "");
				else jsonPersonalData.put("Native_Language", theLanguage);
				if (theNumberOfLanguages == null) jsonPersonalData.put("Number_of_Languages", "");
				else jsonPersonalData.put("Number_of_Languages", theNumberOfLanguages);
				if (musicListener == null) jsonPersonalData.put("Music_Listener", "");
				else jsonPersonalData.put("Music_Listener", musicListener);
				if (musicInstrumentist == null) jsonPersonalData.put("Music_Instrumentist", "");
				else jsonPersonalData.put("Music_Instrumentist", musicInstrumentist);
				if (musicTheory == null) jsonPersonalData.put("Music_Theory", "");
				else jsonPersonalData.put("Music_Theory", musicTheory);

				//System Data
				String systemLang = Resources.getSystem().getConfiguration().locale.getLanguage();
				if (systemLang==null)jsonPersonalData.put("System_Language","");
				else jsonPersonalData.put("System_Language",systemLang);
			}
			jsonObj.put("PersonalData", jsonPersonalData);

			String appLang = Locale.getDefault().getLanguage();
			if (appLang==null)jsonObj.put("App_Language","");
			else jsonObj.put("App_Language",appLang);

			jsonObj.put("Game_Type", DataObj.getGameType());
			jsonObj.put("Level", DataObj.getLevel());  
			jsonObj.put("Operation_Type", DataObj.getOpType());
			jsonObj.put("Operand_1", DataObj.getOp1());
			jsonObj.put("Operand_2", DataObj.getOp2());
			jsonObj.put("Operator", DataObj.getOperator());
			jsonObj.put("Correct_Result", DataObj.getResult());
			jsonObj.put("Entered_Answer", DataObj.getAnswer());
			
			JSONArray jsonRVector = new JSONArray(); // we need another object to store the answer
			ArrayList<Integer> respvectorpass = new ArrayList<Integer>();
			respvectorpass=DataObj.getVectorAns();
			for (int contRV = 0; contRV<respvectorpass.size();contRV++){
			jsonRVector.put(respvectorpass.get(contRV));}
			jsonObj.put("Response_Vector", jsonRVector);
						
			jsonObj.put("Correct", DataObj.getCorrect());
			jsonObj.put("Erase", DataObj.getErase());
			jsonObj.put("Hints_Available", DataObj.getHintAva());
			jsonObj.put("Hint_Shown", DataObj.getHintDisp());
			jsonObj.put("Hide_Question", DataObj.getHidden());
			jsonObj.put("Session_Trial", DataObj.getArcorder());
			jsonObj.put("Session_Correct", DataObj.getAcccorrect());
			jsonObj.put("Trial_Number", DataObj.getTrialNumber());

			JSONArray jsonRTime = new JSONArray(); // we need another object to store the times
			ArrayList<Long> resptimepass = new ArrayList<Long>();
			resptimepass=DataObj.getTimes();
			for (int contRT = 0; contRT<resptimepass.size();contRT++){
			jsonRTime.put(resptimepass.get(contRT));}
			jsonObj.put("Response_Times", jsonRTime);
			
			jsonObj.put("Total_Time", DataObj.getTotTime());
			
			jsonObj.put("Confidence_Answer", DataObj.getConfidence());  
			jsonObj.put("Effort_Answer", DataObj.getEffort());
			jsonObj.put("Initial_Confidence", DataObj.getInitConfidence());  
			jsonObj.put("Initial_Effort", DataObj.getInitEffort());
			
			
			JSONArray jsonDate1 = new JSONArray();
					
			Calendar C1= DataObj.gettheDate1();
			jsonDate1.put(C1.get(Calendar.YEAR));
			jsonDate1.put(C1.get(Calendar.MONTH)+1);
			jsonDate1.put(C1.get(Calendar.DATE));
			jsonDate1.put(C1.get(Calendar.HOUR_OF_DAY));
			jsonDate1.put(C1.get(Calendar.MINUTE));
			jsonDate1.put(C1.get(Calendar.SECOND));
			
			jsonObj.put("Start_Date", jsonDate1);
			
			JSONArray jsonDate2 = new JSONArray();
						
			Calendar C2= DataObj.gettheDate2();
			jsonDate2.put(C2.get(Calendar.YEAR));
			jsonDate2.put(C2.get(Calendar.MONTH)+1);
			jsonDate2.put(C2.get(Calendar.DATE));
			jsonDate2.put(C2.get(Calendar.HOUR_OF_DAY));
			jsonDate2.put(C2.get(Calendar.MINUTE));
			jsonDate2.put(C2.get(Calendar.SECOND));
			
			jsonObj.put("End_Date", jsonDate2);
			
			jsonObj.put("Max_Time", (DataObj.getReftime()*2));
			jsonObj.put("Time_Exceeded", DataObj.getTimeEx());
			jsonObj.put("Corr_in_a_Row", DataObj.getCorrinarow());
			
			jsonObj.put("Score", DataObj.getMyScore());

			try {
				PackageInfo pinfo = MainActivity.THEcontext.getPackageManager().getPackageInfo(MainActivity.THEcontext.getPackageName(), 0);
				int versionNumber = pinfo.versionCode;
				if (versionNumber==0)jsonObj.put("App_Version","");
				else {
					String version = "Android_"+versionNumber;
					jsonObj.put("App_Version",version);
				}
			}
			catch (Exception e){
				jsonObj.put("App_Version","");
			}

			return jsonObj;
			
		}
		catch(JSONException ex) {
			ex.printStackTrace();
		}
		
		return null;
		
	}

}