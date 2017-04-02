package tedxperiments.math.entrenamente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Path.FillType;
import android.graphics.PorterDuff.Mode;
import tedxperiments.math.entrenamente.R;


public class PlayArcade extends Activity implements OnClickListener {

	//gameplay elements
	private int level=0,sublevel = 0, operator = 0, operand1 = 0, operand2 = 0;
	private long answer = 0;
	private String thisoperation = "";
	
	private int hints=3;
	private boolean hintused;
	//operator constants
	private final int ADD_OPERATOR = 0, MULTIPLY_OPERATOR = 1, SQUARE_OPERATOR = 2;
	//operator text
	private String[] operators = {"+", "x", "x"};
	private String gametype ="Arcade";
	
	//min and max for each level and operator
	private int[][] levelMin = {
			{1, 10, 100, 1000},
			{2, 11, 101, 11, 1001, 101, 101},
			{2, 11, 101, 1001,10001},
			};
	private int[][] levelMax = {
			{9, 99, 999, 9999},
			{9, 99, 999, 99, 9999, 999, 999},
			{9, 99, 999, 9999, 99999},
			};
	
	private int[][] ScoreLevel = {
			{1, 2, 3, 4},
			{5, 7, 8, 10, 11, 12, 13},
			{6, 9, 14, 15,16},
			};
	
	private int[][] ntocompletelevel={
			{10, 5, 5, 5},//va 10 al principio
			{10, 5, 5, 5, 5, 5, 5},//va 10 al principio
			{100, 5, 5, 3,3},
			};
	
	private int[][] ttocompletelevel={
			{5, 8, 100, 100},
			{7, 10, 12, 15, 15, 100, 100},
			{100, 12, 25, 60,100},
			};
	
	
	private int npasslevel;
	private int tavg;
	private static Toast toastanswer;
	private static CountDownTimer timeranswer;
	private int hintdisplayed;
	private int hintsavailable;
	private int hidden=0;
		
	
	//random number generator
	private Random random;
	//ui elements
	private TextView question, answerTxt, scoreTxt, equationTxt,levelTxt;
	private ImageView response;
	private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, 
	enterBtn, clearBtn;
	
	
	//Cronometro
	private Chronometer chrono;
	private long starttime;
	private ProgressBar timelimit;
			
	//HACER CON ARRAYLIST
	ArrayList<Long> answertime = new ArrayList<Long>();
	//private long[][] answertime[][];
	int Ti=0;
	//int Tj=0;
	
	//Puntajes
	
	DataScore DScore = new DataScore();
	ArrayList<Integer> enteredVector = new ArrayList<Integer>();
	ArrayList<Integer> enteredConfidence = new ArrayList<Integer>();
	ArrayList<Integer> enteredEffort = new ArrayList<Integer>();
	
	ArrayList<DataScore> DScoreList= new ArrayList<DataScore>();;
	
	public int CorrinaRow=0;
	//public int totTimeLevel=0;
		//Contador Instrospeccion
	int ContIntr=0;
	IntrospectionOK CheckandContinue= new IntrospectionOK();
	public int thisNcorrect=0;
	public int thisNincorrect=0;
	public boolean hide;
	public int leveltime=0;
	
	//shared preferences
	private SharedPreferences gamePrefs;
	public static final String GAME_PREFS = "ArithmeticFile";
		
	//Handler
	Handler_sqlite helper = new Handler_sqlite(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_playgame);
						
		//initiate shared prefs
		gamePrefs = getSharedPreferences(GAME_PREFS, 0);
		
		
		//text and image views
		question = (TextView)findViewById(R.id.question);
		answerTxt = (TextView)findViewById(R.id.answer);
		equationTxt = (TextView)findViewById(R.id.equation);
		
		response = (ImageView)findViewById(R.id.response);
		chrono = (Chronometer) findViewById(R.id.chronometer1);
		levelTxt = (TextView)findViewById(R.id.thislevel);
		scoreTxt = (TextView)findViewById(R.id.score);
		timelimit = (ProgressBar)findViewById(R.id.progressbar);
		timelimit.getProgressDrawable().setColorFilter(Color.parseColor("#0b9E8C"), Mode.SRC_IN);
		scoreTxt.setText("0/20");
		

		//hide tick cross initially
		//response.setVisibility(View.INVISIBLE);
		

		//number, enter and clear buttons
		btn1 = (Button)findViewById(R.id.btn1);
		btn2 = (Button)findViewById(R.id.btn2);
		btn3 = (Button)findViewById(R.id.btn3);
		btn4 = (Button)findViewById(R.id.btn4);
		btn5 = (Button)findViewById(R.id.btn5);
		btn6 = (Button)findViewById(R.id.btn6);
		btn7 = (Button)findViewById(R.id.btn7);
		btn8 = (Button)findViewById(R.id.btn8);
		btn9 = (Button)findViewById(R.id.btn9);
		btn0 = (Button)findViewById(R.id.btn0);
		enterBtn = (Button)findViewById(R.id.enter);
		clearBtn = (Button)findViewById(R.id.clear);

		//listen for clicks
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		btn8.setOnClickListener(this);
		btn9.setOnClickListener(this);
		btn0.setOnClickListener(this);
		enterBtn.setOnClickListener(this);
		clearBtn.setOnClickListener(this);
		
		equationTxt.setOnClickListener(this);
		
		clearBtn.setText("\u2190");
		
		//if(savedInstanceState!=null){
		//	//saved instance state data
		//	sublevel=savedInstanceState.getInt("level");
		//	//int exScore = savedInstanceState.getInt("score");
		//	}
		//else{
		//	//get passed level number
		//	Bundle extras = getIntent().getExtras();
		//	if(extras !=null)
		//	{	int passedLevel = extras.getInt("level", -1);
		//		if(passedLevel>=0) level = passedLevel;
		//		
		//		int passedsubLevel = extras.getInt("sublevel", -1);
		//		if(passedsubLevel>=0) sublevel = passedsubLevel;
		//		
		//		int passedOperator = extras.getInt("operator", -1);
		//		if(passedLevel>=0) operator = passedOperator;
		//	}
		//}
		Bundle extras = getIntent().getExtras();
		
		if(extras !=null)
		{	int passedLevel = extras.getInt("level", -1);
			if(passedLevel>0) level = passedLevel;
			}
		else level =PassLevel.getCurrentLevel("Levels_Completed_Arcade", this);
		//level =PassLevel.getCurrentLevel("Levels_Completed_Arcade", this);
		if (level==0) level=1;
		
		//Pasarselo a la dataScore
		//Buscar un formato mas facil para leer despues
		DScore.setLevel(level);
		DScore.setGameType(gametype);
		npasslevel = ntocompletelevel[operator][sublevel];
		levelTxt.setText(getString(R.string.actual_level)+level);
		
		//tavg = ttocompletelevel[operator][sublevel];
		//DScore.setReftime(tavg*1000);
		//String AUID = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
		
		
		//Action Bar
		ActionBar AB = getActionBar();
		AB.setDisplayHomeAsUpEnabled(true);
		 AB.setTitle(Html.fromHtml("<font color='#60b0c1'>Entrenamente </font>"));
		AB.setSubtitle(getString(R.string.actual_level)+level);
		AB.hide();
		
		hintdisplayed=0;
		//if (!thisoperation.equals("1d+1d") && !thisoperation.equals("1dx1d")) equationTxt.setText(Html.fromHtml("<big>"+hints+"</big>/3 pistas disponibles"));
		 //  else equationTxt.setText(Html.fromHtml(""));
		   
		 //switch(operator){
		 //	case ADD_OPERATOR: switch (sublevel){
		// 		case 0: AB.setSubtitle("Nivel: "+level+": 1 dig + 1dig");break;
		 //		case 1: AB.setSubtitle("Nivel: "+level+": 2 dig + 2dig");break;
		 //		case 2: AB.setSubtitle("Nivel: "+level+": 3 dig + 3dig");break;
		 //		case 3: AB.setSubtitle("Nivel: "+level+": 4 dig + 4dig");break;
		// 	}break;
		 //	case MULTIPLY_OPERATOR: switch (sublevel){
		 //		case 0: AB.setSubtitle("Nivel: "+level+": 1 dig x 1dig");break;
		 //		case 1: AB.setSubtitle("Nivel: "+level+": 2 dig x 1dig");break;
		 //		case 2: AB.setSubtitle("Nivel: "+level+": 3 dig x 1dig");break;
		 //		case 3: AB.setSubtitle("Nivel: "+level+": 2 dig x 2dig");break;
		 //		case 4: AB.setSubtitle("Nivel: "+level+": 4 dig x 1dig");break;
		 //		case 5: AB.setSubtitle("Nivel: "+level+": 3 dig x 2dig");break;
		 //		case 6: AB.setSubtitle("Nivel: "+level+": 3 dig x 3dig");break;
		 //	
		 //	}break;
		 //	case SQUARE_OPERATOR: switch (sublevel){
		 //		case 0: AB.setSubtitle(Html.fromHtml("Nivel: "+level+": (1dig)<sup><small>2</small></sup>"));break;
		 //		case 1: AB.setSubtitle(Html.fromHtml("Nivel: "+level+": (2dig)<sup><small>2</small></sup>"));break;
		 //		case 2: AB.setSubtitle(Html.fromHtml("Nivel: "+level+": (3dig)<sup><small>2</small></sup>"));break;
		 //		case 3: AB.setSubtitle(Html.fromHtml("Nivel: "+level+": (4dig)<sup><small>2</small></sup>"));break;
		 //		case 4: AB.setSubtitle(Html.fromHtml("Nivel: "+level+": (5dig)<sup><small>2</small></sup>"));break;
		 //	 }break;
		//}
		 
		// switch(level){
		// 		case 1: equationTxt.setText("Intente responder correctamente en menos de 10 segundos");break;
		// 		case 2: equationTxt.setText("Intente responder correctamente en menos de 14 segundos");break;
		// 		case 3: equationTxt.setText("Intente responder correctamente en menos de 16 segundos");break;
		// 		case 4: break; // Este es dinamico
		// 		case 5: equationTxt.setText("Intente responder correctamente en menos de 20 segundos");break;
		// 		case 6: break; // Este es dinamico
		// 		case 7: equationTxt.setText("Intente responder correctamente en menos de 24 segundos");break;
		// 		case 8: break; // Este es dinamico
		// 		case 9: equationTxt.setText(Html.fromHtml("x<sup><small>2</small></sup> = (x-a)(x+a) + a<sup><small>2</small></sup>"));break;
		// 		case 10: equationTxt.setText("Intente responder correctamente en menos de 24 segundos");break;
		// 		case 11: break; // Este es dinamico
		// 		case 12: equationTxt.setText("Intente responder correctamente en menos de 30 segundos");break;
		// 		case 13: break; // Este es dinamico
		// 		case 14: break; // Este es dinamico
		// 		case 15: equationTxt.setText("Intente responder correctamente en menos de 50 segundos");break;
		// 		case 16: break; // Este es dinamico
		// 		case 17: break; // Este es dinamico
		// 		case 18: break; // Este es dinamico
		// 		case 19: equationTxt.setText("Intente responder correctamente en menos de 120 segundos");break;
		// 		
		// 	
		//}
		 
		 chrono.setOnChronometerTickListener(
			        new Chronometer.OnChronometerTickListener(){

			    @Override
			    public void onChronometerTick(Chronometer chronometer) {
			     // TODO Auto-generated method stub
			    long myElapsedMillis = SystemClock.elapsedRealtime() -chrono.getBase();
			    if (myElapsedMillis>59*60*1000)chrono.stop();
			    int remtime = (int) (tavg*1000*2-myElapsedMillis);
			    if (remtime>=0)timelimit.setProgress(remtime);
			    else {timelimit.setProgress(0);
			    	 response.setImageResource(R.drawable.reloj_fin);
			    }
			    if (hide)
			    {hidden=1;
			    if (remtime>(int)(tavg*2000-1000)) 										 question.setTextColor(Color.parseColor("#000000"));
			    else if (remtime<(int)(tavg*2000-1000) && remtime>(int)(tavg*2000-2000)) question.setTextColor(Color.parseColor("#4d4d4d"));
			    else if (remtime<(int)(tavg*2000-2000) && remtime>(int)(tavg*2000-3000)) question.setTextColor(Color.parseColor("#818181"));
			    else if (remtime<(int)(tavg*2000-3000) && remtime>(int)(tavg*2000-4000)) question.setTextColor(Color.parseColor("#9a9a9a"));
			    else if (remtime<(int)(tavg*2000-4000) && remtime>(int)(tavg*2000-5000)) question.setTextColor(Color.parseColor("#cecece"));
			    else if (remtime<(int)(tavg*2000-5000) && remtime>(int)(tavg*2000-6000)) question.setTextColor(Color.parseColor("#d8d8d8"));
			    else if (remtime<(int)(tavg*2000-6000)) 								 question.setTextColor(Color.parseColor("#FFFFFF"));
			    else {hidden=0;}
			    
			    	//if (remtime>(int)(tavg*2000-1000))
			    	//	{if(operator==2)	{question.setText(Html.fromHtml(operand1+"<sup><small>2</small></sup>"));}
					//				else{question.setText(operand1+" "+operators[operator]+" "+operand2);}
			    	//	}
			    	//else if (remtime>(int)(tavg*2000-2000) && remtime>(int)(tavg*2000-3000))
			    	//	question.setText("");
			    	//else if (remtime>(int)(tavg*2000-3000) && remtime>(int)(tavg*2000-7000))
			    	//	{if(operator==2)	{question.setText(Html.fromHtml(operand1+"<sup><small>2</small></sup>"));}
			    	//	else{question.setText(operand1+" "+operators[operator]+" "+operand2);}
			    	//	}
			    	//else if (remtime<(int)(tavg*2000-7000))
			    	//	question.setText(""); 
			    }}
			        }
			      );
			  
		 
		 		 
		
		//initialize random
		random = new Random();
		//play
		if (level<151){
		chooseQuestion();}
		else {
			AlertDialog won= FWon();
			won.show();}
		if (!thisoperation.equals("1d+1d") && !thisoperation.equals("1dx1d")) equationTxt.setText(Html.fromHtml("<big>"+hints+"</big>/3 "+getString(R.string.available_hints)));
		   else equationTxt.setText(Html.fromHtml("<big><font color='#93C9C3'>A</font></big>"));
		   
		
	}
	

	@Override
	public void onClick(View view) {
		if(view.getId()==R.id.enter){
			//enter button
			
			//Stop Chrono
			answertime.add(Ti,System.currentTimeMillis()-starttime);
			chrono.stop();
			//le paso el ultimo valor
			DScore.setTimes(answertime);
			enteredVector.add(20);
			answertime.clear();
			Ti=0;
			//Tj+=1;
			leveltime=(int)(leveltime+(int)DScore.getTotTime());
			ContIntr+=1;
			boolean AskIntr= false;
			if (level>15 && level<20) {if (random.nextInt(20)==1) AskIntr=true;}
			else if (level>=20 && level<25){if (random.nextInt(10)==1) AskIntr=true;}
			else if (level>=25 && level<30){if (random.nextInt(7)==1) AskIntr=true;}
			else if (level>=30){if (random.nextInt(5)==1) AskIntr=true;}
			
	
				//if (ContIntr%5==0){
					if (AskIntr){
					//AlertDialog AskIntr=Fintrospection();
					CheckandContinue.AlertD=Fintrospection();
					
					CheckandContinue.setE(0);
					CheckandContinue.setC(0); 
					//AskIntr.show();
					if (toastanswer != null)
						toastanswer.cancel();
					if (timeranswer != null)
						timeranswer.cancel();
				
					CheckandContinue.AlertD.show();
					
					
					CheckandContinue.AlertD.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
			        
			       
				}
					
			
				else {//Le cargo unos valores sin sentido, poruqe el Objeto tiene esos campos
					DScore.setConfidence(-1);
					DScore.setEffort(-1);
					DScore.setInitConfidence(-1);
					DScore.setInitEffort(-1);
					enteredConfidence.add(-1);
					enteredEffort.add(-1);
					CheckAnswer();
					}
				//get answer
				
						
		
		}
		
		else if(view.getId()==R.id.clear){
			//clear button
			//Aca no se que hacer con el Time
			//answerTxt.setText("= ?");
			DScore.setErase(1);
			enteredVector.add(-1);
			
			String previousanswerContent = answerTxt.getText().toString();
			if(previousanswerContent.endsWith("?")){}
			else{if (previousanswerContent.length()>2)answerTxt.setText(answerTxt.getText().subSequence(0, answerTxt.getText().length()-1));}
			
			if (previousanswerContent.length()<10){answerTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);}
			else if (previousanswerContent.length()<13){answerTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);}
			else if (previousanswerContent.length()<15){answerTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);}
			}
		
		
		else if(view.getId()==R.id.equation){
			if (!thisoperation.equals("1d+1d") && !thisoperation.equals("1dx1d"))
					{if (hints>0 && hintused==false)
					{
						hints=hints-1;
						hintused=true; hintdisplayed=1;
						if (timeranswer != null){timeranswer.cancel();}
						equationTxt.setBackgroundResource(R.drawable.button_green2);
						//equationTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,0, 0);
		
						switch(operator){
						case 0:
							if (operand1<10)equationTxt.setText(operand1+" + "+operand2);
							else equationTxt.setText(operand1+" + "+10*(int)(operand2/10)+" + "+operand2%10);
							break;
						case 1:
							if (operand1<10) equationTxt.setText(operand1+"x"+operand2);
							else if (operand1<100) equationTxt.setText(10*(int)(operand1/10)+"x"+operand2+" + "+operand1%10+"x"+operand2);
							else if (operand1<1000) equationTxt.setText(100*(int)(operand1/100)+"x"+operand2+" + "+10*(int)((operand1%100)/10)+"x"+operand2+" + "+operand1%10+"x"+operand2);
							else if (operand1<10000) equationTxt.setText(1000*(int)(operand1/1000)+"x"+operand2+" + "+100*(int)((operand1%1000)/100)+"x"+operand2+" + "+10*(int)((operand1%100)/10)+"x"+operand2+" + "+operand1%10+"x"+operand2);
							break;
						case 2:
							if (operand1<100){
								int numberA=Math.min(operand1%10,10-operand1%10);
								equationTxt.setText(Html.fromHtml("x<sup><small>2</small></sup> = (x-a)(x+a) + a<sup><small>2</small></sup><br />"
										+"("+operand1+"-"+numberA+")"+" x "+"("+operand1+"+"+numberA+")"+" + "+numberA+"<sup><small>2</small></sup><br />"
										+(operand1-numberA)+" x "+(operand1+numberA)+" + "+numberA+"<sup><small>2</small></sup>"));
							}
							else if (operand1<1000){
								int numberB=Math.min(operand1%100,100-operand1%100);
								int numberC=Math.min(numberB%10,10-numberB%10);
								equationTxt.setText(Html.fromHtml("x<sup><small>2</small></sup> = (x-a)(x+a) + a<sup><small>2</small></sup><br />"
										+"("+operand1+"-"+numberB+")"+" x "+"("+operand1+"+"+numberB+") + "+numberB+"<sup><small>2</small></sup><br />"
										+"("+(operand1-numberB)+"x"+(operand1+numberB)+") + ("+numberB+"-"+numberC+")"+" x "+"("+numberB+"+"+numberC+") + "+numberC+"<sup><small>2</small></sup><br />"
										+"("+(operand1-numberB)+"x"+(operand1+numberB)+") + ("+(numberB-numberC)+"x"+(numberB+numberC)+") + "+numberC+"<sup><small>2</small></sup>"));break;
							}
							else if (operand1<10000){
								int numberE=Math.min(operand1%1000,1000-operand1%1000);
								int numberF=Math.min(numberE%100,100-numberE%100);
								int numberG=Math.min(numberF%10,10-numberF%10);
								equationTxt.setText(Html.fromHtml("x<sup><small>2</small></sup> = (x-a)(x+a) + a<sup><small>2</small></sup><br />"
										+"("+operand1+"-"+numberE+")"+" x "+"("+operand1+"+"+numberE+") + "+numberE+"<sup><small>2</small></sup><br />"
										+"("+(operand1-numberE)+"x"+(operand1+numberE)+") + ("+numberE+"-"+numberF+")"+" x "+"("+numberE+"+"+numberF+") + "+numberF+"<sup><small>2</small></sup><br />"
										+"("+(operand1-numberE)+"x"+(operand1+numberE)+") + ("+(numberE-numberF)+"x"+(numberE+numberF)+") + ("+numberF+"-"+numberG+")"+"x"+"("+numberF+"+"+numberG+") + "+numberG+"<sup><small>2</small></sup><br />"
										+"("+(operand1-numberE)+"x"+(operand1+numberE)+") + ("+(numberE-numberF)+"x"+(numberE+numberF)+") + ("+(numberF-numberG)+"x"+(numberF+numberG)+") + "+numberG+"<sup><small>2</small></sup>"));
							}
							break;
						}
					}
					}
			}
		
		//switch(level){
 		//	case 4: equationTxt.setText(10*(int)(operand1/10)+"x"+operand2+" + "+operand1%10+"x"+operand2);break; // Este es dinamico
 		//	case 6: equationTxt.setText(100*(int)(operand1/100)+"x"+operand2+" + "+10*(int)((operand1%100)/10)+"x"+operand2+" + "+operand1%10+"x"+operand2);break;
 		//	case 8: int numberA=Math.min(operand1%10,10-operand1%10);
 		//			equationTxt.setText(Html.fromHtml("x<sup><small>2</small></sup> = (x-a)(x+a) + a<sup><small>2</small></sup><br />"
 		//			+"("+operand1+"-"+numberA+")"+" x "+"("+operand1+"+"+numberA+")"+" + "+numberA+"<sup><small>2</small></sup><br />"
 		//			+(operand1-numberA)+" x "+(operand1+numberA)+" + "+numberA+"<sup><small>2</small></sup>"));break;
 		//	case 11: equationTxt.setText(1000*(int)(operand1/1000)+"x"+operand2+" + "+100*(int)((operand1%1000)/100)+"x"+operand2+" + "+10*(int)((operand1%100)/10)+"x"+operand2+" + "+operand1%10+"x"+operand2);break;
 		//	case 13: int numberB=Math.min(operand1%100,100-operand1%100);
 		//			int numberC=Math.min(numberB%10,10-numberB%10);
 		//			equationTxt.setText(Html.fromHtml("x<sup><small>2</small></sup> = (x-a)(x+a) + a<sup><small>2</small></sup><br />"
 		//			+"("+operand1+"-"+numberB+")"+" x "+"("+operand1+"+"+numberB+") + "+numberB+"<sup><small>2</small></sup><br />"
 		//			+"("+(operand1-numberB)+"x"+(operand1+numberB)+") + ("+numberB+"-"+numberC+")"+" x "+"("+numberB+"+"+numberC+") + "+numberC+"<sup><small>2</small></sup><br />"
		//			+"("+(operand1-numberB)+"x"+(operand1+numberB)+") + ("+(numberB-numberC)+"x"+(numberB+numberC)+") + "+numberC+"<sup><small>2</small></sup>"));break;
 		//	case 14: int numberD=Math.min(operand1%100,100-operand1%100);
 		//			equationTxt.setText(Html.fromHtml("x<sup><small>2</small></sup> = (x-a)(x+a) + a<sup><small>2</small></sup><br />"
 		//			+"("+operand1+"-"+numberD+")"+" x "+"("+operand1+"+"+numberD+") + "+numberD+"<sup><small>2</small></sup><br />"
 		//			+"("+(operand1-numberD)+"x"+(operand1+numberD)+") + "+numberD+"<sup><small>2</small></sup>"));break;
 		//	case 16: int numberE=Math.min(operand1%1000,1000-operand1%1000);
		//			int numberF=Math.min(numberE%100,100-numberE%100);
		//			int numberG=Math.min(numberF%10,10-numberF%10);
		//			equationTxt.setText(Html.fromHtml("x<sup><small>2</small></sup> = (x-a)(x+a) + a<sup><small>2</small></sup><br />"
		//			+"("+operand1+"-"+numberE+")"+" x "+"("+operand1+"+"+numberE+") + "+numberE+"<sup><small>2</small></sup><br />"
		//			+"("+(operand1-numberE)+"x"+(operand1+numberE)+") + ("+numberE+"-"+numberF+")"+" x "+"("+numberE+"+"+numberF+") + "+numberF+"<sup><small>2</small></sup><br />"
		//			+"("+(operand1-numberE)+"x"+(operand1+numberE)+") + ("+(numberE-numberF)+"x"+(numberE+numberF)+") + ("+numberF+"-"+numberG+")"+"x"+"("+numberF+"+"+numberG+") + "+numberG+"<sup><small>2</small></sup><br />"
		//			+"("+(operand1-numberE)+"x"+(operand1+numberE)+") + ("+(numberE-numberF)+"x"+(numberE+numberF)+") + ("+(numberF-numberG)+"x"+(numberF+numberG)+") + "+numberG+"<sup><small>2</small></sup>"));break;
 		//	case 17:int numberJ=Math.min(operand1%1000,1000-operand1%1000);
 		//			int numberK=Math.min(numberJ%100,100-numberJ%100);
 		//			equationTxt.setText(Html.fromHtml("x<sup><small>2</small></sup> = (x-a)(x+a) + a<sup><small>2</small></sup><br />"
 		//			+"("+(operand1-numberJ)+"x"+(operand1+numberJ)+") + ("+numberJ+"-"+numberK+")"+" x "+"("+numberJ+"+"+numberK+") + "+numberK+"<sup><small>2</small></sup><br />"
 		//			+"("+(operand1-numberJ)+"x"+(operand1+numberJ)+") + ("+numberJ+"-"+numberK+")"+" x "+"("+numberJ+"+"+numberK+") + "+numberK+"<sup><small>2</small></sup><br />"
 		//			+"("+(operand1-numberJ)+"x"+(operand1+numberJ)+") + ("+(numberJ-numberK)+"x"+(numberJ+numberK)+") + "+numberK+"<sup><small>2</small></sup>"));break;
 		//	case 18: int numberL=Math.min(operand1%1000,1000-operand1%1000);
 		//			equationTxt.setText(Html.fromHtml("x<sup><small>2</small></sup> = (x-a)(x+a) + a<sup><small>2</small></sup><br />"
		//			+"("+operand1+"-"+numberL+")"+" x "+"("+operand1+"+"+numberL+") + "+numberL+"<sup><small>2</small></sup><br />"
		//			+"("+(operand1-numberL)+"x"+(operand1+numberL)+") + "+numberL+"<sup><small>2</small></sup>"));break;
 		//	}
		
			
			
		
		else {
			//number button
			
			//Cronometro cada numero
			answertime.add(Ti,System.currentTimeMillis()-starttime);
			Ti+=1;
					
			enterBtn.setEnabled(true); 
			//response.setVisibility(View.INVISIBLE);
			//get number from tag
			int enteredNum = Integer.parseInt(view.getTag().toString());
			enteredVector.add(enteredNum);
			//either first or subsequent digit
			String previousanswerContent = answerTxt.getText().toString();
			//long previousAnswer = FenteredAnswer(previousanswerContent);
			if (previousanswerContent.length()<11) {
				if(answerTxt.getText().toString().endsWith("?"))
					answerTxt.setText("= "+enteredNum);
				else
					answerTxt.append(""+enteredNum);
			}
			if (previousanswerContent.length()<8){
				answerTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);}
			else{
				answerTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);}
//			else if (previousanswerContent.length()<11){answerTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);}
//			else if (previousanswerContent.length()<13){answerTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);}
		}
	}
	

	private long FenteredAnswer(String VanswerContent) {

			long VarenteredAnswer;
			try{
				VarenteredAnswer = Long.parseLong(VanswerContent.substring(2));
		}
			catch (NumberFormatException e){
				VarenteredAnswer=0;}
		return VarenteredAnswer;
		
	}
	
	//method retrieves score
	private int getScore(){
		String scoreStr = scoreTxt.getText().toString();
		return Integer.parseInt(scoreStr.substring(scoreStr.lastIndexOf(" ")+1));
	}
	
	
	//method generates questions
	private void chooseQuestion(){
		if (thisNcorrect+thisNincorrect<20){
		//reset answer text
		answerTxt.setText("= ?");
		answerTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
		response.setImageResource(R.drawable.reloj);
		enterBtn.setEnabled(false);
		
		//choose operator
		//operator = random.nextInt(operators.length);
		//choose operands
		//operand1 = getOperand();
		//DScore.setOp1(operand1);
		Operation op;
		ArcLevel arcl= new ArcLevel();
		op = arcl.getOperation(level);
		operator=op.getOperator();
		operand1=op.getOp1();
		operand2=op.getOp2();
		tavg=op.getTimeavg();
		hide=op.getHide();
		DScore.setReftime(tavg*1000);
		hintsavailable=hints;
		
		String op1=Integer.toString(operand1);
		String op2=Integer.toString(operand2);
		int n1 = op1.length();
		int n2 = op2.length();
		if (operator==2) thisoperation =("("+n1+"d)^2");
		else if (operator==1)thisoperation =(""+n1+"dx"+n2+"d");
		else if (operator==0)thisoperation =(""+n1+"d+"+n2+"d");
		
		
				//calculate answer
		switch(operator){
		case ADD_OPERATOR:
			answer = operand1+operand2;
			break;
		case MULTIPLY_OPERATOR:
			answer = operand1*operand2;
			break;
		case SQUARE_OPERATOR:
			answer = operand1*operand2;
			break;
		//case DIVIDE_OPERATOR:
		//	answer = operand1/operand2;
		//	break;
		default:
			break;
		
		}
		//DScore.setResult(answer);	

		//show question
		if(operator==2)	{question.setText(Html.fromHtml(operand1+"<sup><small>2</small></sup>"));}
		else{question.setText(operand1+" "+operators[operator]+" "+operand2);}
		question.setTextColor(Color.parseColor("#000000"));
		//equationTxt.setText(Html.fromHtml("<big>"+hints+"</big>/3 pistas disponibles"));
		hintused=false;
		hintdisplayed=0;
		
		//switch(level){
 		//	case 4: equationTxt.setText(10*(int)(operand1/10)+"x"+operand2+" + "+operand1%10+"x"+operand2);break; // Este es dinamico
 		//	case 6: equationTxt.setText(100*(int)(operand1/100)+"x"+operand2+" + "+10*(int)((operand1%100)/10)+"x"+operand2+" + "+operand1%10+"x"+operand2);break;
 		//	case 8: int numberA=Math.min(operand1%10,10-operand1%10);
 		//			equationTxt.setText(Html.fromHtml("x<sup><small>2</small></sup> = (x-a)(x+a) + a<sup><small>2</small></sup><br />"
 		//			+"("+operand1+"-"+numberA+")"+" x "+"("+operand1+"+"+numberA+")"+" + "+numberA+"<sup><small>2</small></sup><br />"
 		//			+(operand1-numberA)+" x "+(operand1+numberA)+" + "+numberA+"<sup><small>2</small></sup>"));break;
 		//	case 11: equationTxt.setText(1000*(int)(operand1/1000)+"x"+operand2+" + "+100*(int)((operand1%1000)/100)+"x"+operand2+" + "+10*(int)((operand1%100)/10)+"x"+operand2+" + "+operand1%10+"x"+operand2);break;
 		//	case 13: int numberB=Math.min(operand1%100,100-operand1%100);
 		//			int numberC=Math.min(numberB%10,10-numberB%10);
 		//			equationTxt.setText(Html.fromHtml("x<sup><small>2</small></sup> = (x-a)(x+a) + a<sup><small>2</small></sup><br />"
 		//			+"("+operand1+"-"+numberB+")"+" x "+"("+operand1+"+"+numberB+") + "+numberB+"<sup><small>2</small></sup><br />"
 		//			+"("+(operand1-numberB)+"x"+(operand1+numberB)+") + ("+numberB+"-"+numberC+")"+" x "+"("+numberB+"+"+numberC+") + "+numberC+"<sup><small>2</small></sup><br />"
		//			+"("+(operand1-numberB)+"x"+(operand1+numberB)+") + ("+(numberB-numberC)+"x"+(numberB+numberC)+") + "+numberC+"<sup><small>2</small></sup>"));break;
 		//	case 14: int numberD=Math.min(operand1%100,100-operand1%100);
 		//			equationTxt.setText(Html.fromHtml("x<sup><small>2</small></sup> = (x-a)(x+a) + a<sup><small>2</small></sup><br />"
 		//			+"("+operand1+"-"+numberD+")"+" x "+"("+operand1+"+"+numberD+") + "+numberD+"<sup><small>2</small></sup><br />"
 		//			+"("+(operand1-numberD)+"x"+(operand1+numberD)+") + "+numberD+"<sup><small>2</small></sup>"));break;
 		//	case 16: int numberE=Math.min(operand1%1000,1000-operand1%1000);
		//			int numberF=Math.min(numberE%100,100-numberE%100);
		//			int numberG=Math.min(numberF%10,10-numberF%10);
		//			equationTxt.setText(Html.fromHtml("x<sup><small>2</small></sup> = (x-a)(x+a) + a<sup><small>2</small></sup><br />"
		//			+"("+operand1+"-"+numberE+")"+" x "+"("+operand1+"+"+numberE+") + "+numberE+"<sup><small>2</small></sup><br />"
		//			+"("+(operand1-numberE)+"x"+(operand1+numberE)+") + ("+numberE+"-"+numberF+")"+" x "+"("+numberE+"+"+numberF+") + "+numberF+"<sup><small>2</small></sup><br />"
		//			+"("+(operand1-numberE)+"x"+(operand1+numberE)+") + ("+(numberE-numberF)+"x"+(numberE+numberF)+") + ("+numberF+"-"+numberG+")"+"x"+"("+numberF+"+"+numberG+") + "+numberG+"<sup><small>2</small></sup><br />"
		//			+"("+(operand1-numberE)+"x"+(operand1+numberE)+") + ("+(numberE-numberF)+"x"+(numberE+numberF)+") + ("+(numberF-numberG)+"x"+(numberF+numberG)+") + "+numberG+"<sup><small>2</small></sup>"));break;
 		//	case 17:int numberJ=Math.min(operand1%1000,1000-operand1%1000);
 		//			int numberK=Math.min(numberJ%100,100-numberJ%100);
 		//			equationTxt.setText(Html.fromHtml("x<sup><small>2</small></sup> = (x-a)(x+a) + a<sup><small>2</small></sup><br />"
 		//			+"("+(operand1-numberJ)+"x"+(operand1+numberJ)+") + ("+numberJ+"-"+numberK+")"+" x "+"("+numberJ+"+"+numberK+") + "+numberK+"<sup><small>2</small></sup><br />"
 		//			+"("+(operand1-numberJ)+"x"+(operand1+numberJ)+") + ("+numberJ+"-"+numberK+")"+" x "+"("+numberJ+"+"+numberK+") + "+numberK+"<sup><small>2</small></sup><br />"
 		//			+"("+(operand1-numberJ)+"x"+(operand1+numberJ)+") + ("+(numberJ-numberK)+"x"+(numberJ+numberK)+") + "+numberK+"<sup><small>2</small></sup>"));break;
 		//	case 18: int numberL=Math.min(operand1%1000,1000-operand1%1000);
 		//			equationTxt.setText(Html.fromHtml("x<sup><small>2</small></sup> = (x-a)(x+a) + a<sup><small>2</small></sup><br />"
		//			+"("+operand1+"-"+numberL+")"+" x "+"("+operand1+"+"+numberL+") + "+numberL+"<sup><small>2</small></sup><br />"
		//			+"("+(operand1-numberL)+"x"+(operand1+numberL)+") + "+numberL+"<sup><small>2</small></sup>"));break;
 		//	}
		
		//Start Chrono
		//No se bien si va aca, me parece que no
		DScore.setErase(0);
		Calendar Dt1 = Calendar.getInstance();
		DScore.setDate1(Dt1);
		chrono.setBase(SystemClock.elapsedRealtime());
		chrono.start();
		starttime=System.currentTimeMillis();
		timelimit.setMax(tavg*2*1000);
		timelimit.setProgress(tavg*2*1000);}
		
		else{
			//Toast.makeText(getBaseContext(), "Llegaste a 20 preguntas. Hiciste "+thisNcorrect+" Bien",Toast.LENGTH_SHORT).show();
			saveprogress(thisNcorrect);
			FNextLevel3();
			//AlertDialog AskNextLevel2=FNextLevel2();
			//AskNextLevel2.show();
			
			}
				
	}

	
	//method generates operands
	
	private int getOperand(){
		return random.nextInt(levelMax[operator][sublevel] - levelMin[operator][sublevel] + 1) 
				+ levelMin[operator][sublevel];
	}
	
	//La parte de introspeccion
	public AlertDialog Fintrospection(){
		
		AlertDialog IntrDialog=null;
		
		//Se construye
		final AlertDialog.Builder builderIntr = new AlertDialog.Builder(this);
		builderIntr.setTitle(getString(R.string.introspection_title));
		builderIntr.setCancelable(false);
				
		LinearLayout LinIntr=new LinearLayout(this);
		LinIntr.setOrientation(1);
		
		TextView textConf=new TextView(this);
		textConf.setText(getString(R.string.confidence_question));
		textConf.setGravity(1);
		textConf.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		
		SeekBar seek1=new SeekBar(this);
		seek1.setMax(100);
		int randA = random.nextInt(100);
		DScore.setInitConfidence(randA);
		enteredConfidence.add(randA);
		seek1.setProgress(randA);
		
			FrameLayout HorIntr1=new FrameLayout(this);
			HorIntr1.setPadding(0, 0, 0, 25);
			//HorIntr.setGravity(1);
			TextView textsubConf11=new TextView(this);
			//textsubConf11.setText("  Tir� a pegar");
			textsubConf11.setText(getString(R.string.confidence_min));
			textsubConf11.setGravity(Gravity.LEFT);
			TextView textsubConf12=new TextView(this);
			//textsubConf12.setText("Muy seguro  ");
			textsubConf12.setText(getString(R.string.confidence_max));
			textsubConf12.setGravity(Gravity.RIGHT);
			
			HorIntr1.addView(textsubConf11);
			HorIntr1.addView(textsubConf12);
		
		TextView textEsf=new TextView(this);
		textEsf.setText(getString(R.string.effort_question));
		textEsf.setGravity(1);
		textEsf.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
		
		SeekBar seek2=new SeekBar(this);
		seek2.setMax(100);
		int randB=random.nextInt(100);
		DScore.setInitEffort(randB);
		enteredEffort.add(randB);
		seek2.setProgress(randB);
		
			FrameLayout HorIntr2=new FrameLayout(this);
			HorIntr2.setPadding(0, 0, 0, 25);
			//HorIntr.setGravity(1);
			TextView textsubConf21=new TextView(this);
			//textsubConf21.setText("  Fue f�cil");
			textsubConf21.setText(getString(R.string.effort_min));
			textsubConf21.setGravity(Gravity.LEFT);
			TextView textsubConf22=new TextView(this);
			//textsubConf22.setText("Me cost�  ");
			textsubConf22.setText(getString(R.string.effort_max));
			textsubConf22.setGravity(Gravity.RIGHT);
		
		HorIntr2.addView(textsubConf21);
		HorIntr2.addView(textsubConf22);
		
		LinIntr.addView(textConf);
		LinIntr.addView(seek1);
		LinIntr.addView(HorIntr1);
		LinIntr.addView(textEsf);
		LinIntr.addView(seek2);
		LinIntr.addView(HorIntr2);
		
		builderIntr.setView(LinIntr);
		
		
	
		
		//Elimino el Thumb, pero no queda claro que HAY QUE ELEGIR ALGO
		//Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);
		//seek1.setThumb(transparentDrawable);
		//seek2.setThumb(transparentDrawable);
	
		
				
			
		seek1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			 
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					
					// TODO Auto-generated method stub
						
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
						
				}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					// TODO Auto-generated method stub
						
					int pro1 = progress;	//we can use the progress value of pro as anywhere
					enteredConfidence.add(pro1);
					DScore.setConfidence(pro1);
					CheckandContinue.setC(1);
					if((CheckandContinue.getE() == 1) && (CheckandContinue.getC() == 1)){
						CheckandContinue.AlertD.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
						CheckandContinue.AlertD.getButton(AlertDialog.BUTTON_POSITIVE).setText(getString(R.string.done_introspection));}
					
					
					//((AlertDialog) builderIntr) getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
					
					//Toast.makeText(getBaseContext(), String.valueOf(progress),
					//	Toast.LENGTH_SHORT).show();
				}
			});
		    
	
	 seek2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
		  
		 
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
					
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
					
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
					
				int pro2 = progress;	//we can use the progress value of pro as anywhere
				enteredEffort.add(pro2);
				DScore.setEffort(pro2);
				CheckandContinue.setE(1);
				if((CheckandContinue.getE() == 1) && (CheckandContinue.getC() == 1)){
				CheckandContinue.AlertD.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
				CheckandContinue.AlertD.getButton(AlertDialog.BUTTON_POSITIVE).setText(getString(R.string.done_introspection));}
		
				
				//Toast.makeText(getBaseContext(), String.valueOf(progress),
					//Toast.LENGTH_SHORT).show();
				
			}
		});
	    
		builderIntr.setPositiveButton(getString(R.string.answer_both),new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog,int id){
				CheckAnswer();} 
			

			
			});
		 
		
		
	 	IntrDialog = builderIntr.create();
		
		return IntrDialog;
		//IntrDialog.show();
		
		
		}
	
	private void CheckAnswer(){	//get answer
		String answerContent = answerTxt.getText().toString();
		//check we have an answer
		//get number from String to Long
			long enteredAnswer = FenteredAnswer(answerContent);
			DScore.setAnswer(enteredAnswer);	
			//get score
			//int exScore = getScore();
									
			//check answer
			if(enteredAnswer==answer){
				//correct
				DScore.setCorrect(1);
										
				if ((int)(DScore.getTotTime()/1000)<2*tavg)
				{
				CorrinaRow++;
				DScore.setCorrinarow(CorrinaRow);
				thisNcorrect++;
				DScore.setTimeEx(0);
				//totTimeLevel=totTimeLevel+(int)(DScore.getTotTime()/1000);
				}
				
				else {
				//totTimeLevel=0;
				DScore.setCorrinarow(CorrinaRow);
				//thisNcorrect++;	
				DScore.setTimeEx(1);
				
				}
				
					if (toastanswer != null){toastanswer.cancel();}
					if (timeranswer != null){timeranswer.cancel();}
					//toastanswer=Ftoastanswer2(1);
					//toastanswer.show();
					correction(1);
					//Handler handler = new Handler();
					//handler.postDelayed(new Runnable() {
					//	@Override
					//	public void run() {
					//		toastcorrect.cancel(); 
					//	}
					//	}, 1000);

				//Get Score MIO
				long MyScore = DScore.getMyScore();
				//VA ESTO!
				scoreTxt.setText(thisNcorrect+thisNincorrect+"/20");
				
				//response.setImageResource(R.drawable.onetick);
				//response.setVisibility(View.VISIBLE);
								
			}
			else{
				//set high score
				//setHighScore();
				//incorrect
				DScore.setCorrect(0);
					if (toastanswer != null)toastanswer.cancel();
					if (timeranswer != null)timeranswer.cancel();
						//toastanswer=Ftoastanswer2(0);
							//toastanswer.show();
							correction(0);
				
								
				//Get Score MIO
				long MyScore = DScore.getMyScore();
				
				//response.setImageResource(R.drawable.cross);
				//response.setVisibility(View.VISIBLE);
				CorrinaRow=0;
				//totTimeLevel=0;
				DScore.setCorrinarow(CorrinaRow);
				thisNincorrect++;
				scoreTxt.setText(thisNcorrect+thisNincorrect+"/20");
				if ((int)(DScore.getTotTime()/1000)<2*tavg)		
				DScore.setTimeEx(0);
				else DScore.setTimeEx(1);
							
			}
			
			//switch(thisNcorrect){
			//case 0:
			//case 1:
			//case 2:
			//case 3:
			//case 4:
			//case 5:
			//case 6:
			//case 7:
			//case 8:
			//case 9:
			//case 10:break;
			//case 12:response. setVisibility(View.VISIBLE);response.setImageResource(R.drawable.correct1_3);break;
			//case 13:response.setImageResource(R.drawable.correct1_3);break;
			//case 14:response.setImageResource(R.drawable.correct1_3);break;
			//case 15:response.setImageResource(R.drawable.correct2_3);break;
			//case 16:response.setImageResource(R.drawable.correct2_3);break;
			//case 17:response.setImageResource(R.drawable.correct2_3);break;
			//case 18:response.setImageResource(R.drawable.correct3_3);break;
			//case 19:response.setImageResource(R.drawable.correct3_3);break;
			//case 20:response.setImageResource(R.drawable.correct3_3);break;
			//}
			
			//response.setVisibility(View.VISIBLE);
									
				Calendar Dt2 = Calendar.getInstance();
				DScore.setDate2(Dt2);
				DScore.setOp1(operand1);
				DScore.setOp2(operand2);
				DScore.setResult(answer);
				if (operator==0) DScore.setOperator("+");
				else DScore.setOperator("x");
				
				//String thisoperation="";
				//String op1=Integer.toString(operand1);
				//String op2=Integer.toString(operand2);
				//int n1 = op1.length();
				//int n2 = op2.length();
				//if (operator==2) thisoperation =("("+n1+"d)^2");
				//else if (operator==1)thisoperation =(""+n1+"dx"+n2+"d");
				//else if (operator==0)thisoperation =(""+n1+"d+"+n2+"d");
				
				DScore.setOpType(thisoperation);
				
				DScore.setVectorAns(enteredVector);
				DScore.setVectorConf(enteredConfidence);
				DScore.setVectorEffort(enteredEffort);
				
				DScore.setHintDisp(hintdisplayed);
				DScore.setHintAva(hintsavailable);
				DScore.setHidden(hidden);
				
				DScore.setArcorder(thisNcorrect+thisNincorrect);
				DScore.setAcccorrect(thisNcorrect);
				
				if (DScore.getCorrect()==1)
					{String thisoptype = DScore.getOpType();
					ArrayList<Long> answertimes = PassLevel.getTimesStats(thisoptype, MainActivity.THEcontext);
					answertimes.add(DScore.getTotTime());
					PassLevel.setTimesStats(thisoptype, answertimes, MainActivity.THEcontext);}
				else if (DScore.getCorrect()==0)
					{String thisoptype = DScore.getOpType();
					thisoptype=thisoptype.concat("inc");
					ArrayList<Long> answertimes = PassLevel.getTimesStats(thisoptype, MainActivity.THEcontext);
					answertimes.add(DScore.getTotTime());
					PassLevel.setTimesStats(thisoptype, answertimes, MainActivity.THEcontext);}
				
				
				DataScore toList = new DataScore();
				toList=copyds(DScore);	
				DScoreList.add(toList);
				
				enteredVector.clear();
				enteredConfidence.clear();
				enteredEffort.clear();
				
				chooseQuestion();
		}
	
	private void saveprogress(int thisNcorrect2) {
		// TODO Auto-generated method stub
		int[] ArcStats = new int[150];
		int[] ArcTimes = new int[150];
		ArcStats=PassLevel.getArcadeStats("Arcade_Stats", MainActivity.THEcontext);
		ArcTimes=PassLevel.getArcadeStats("Arcade_Times", MainActivity.THEcontext);
		
		if (thisNcorrect2<15){}
			else if (thisNcorrect2<17)
				{if(ArcStats[level-1]<1){
					ArcStats[level-1]=1;
					PassLevel.setArcadeStats("Arcade_Stats",ArcStats, MainActivity.THEcontext);
					ArcTimes[level-1]=leveltime;
					PassLevel.setArcadeStats("Arcade_Times",ArcTimes, MainActivity.THEcontext);
					}
				else if (ArcStats[level-1]==1){
						if (leveltime<ArcTimes[level-1])
						{ArcTimes[level-1]=leveltime;
						PassLevel.setArcadeStats("Arcade_Times",ArcTimes, MainActivity.THEcontext);
						}
				}
				}
				else if (thisNcorrect2<20)
				{if(ArcStats[level-1]<2){
					ArcStats[level-1]=2;
					PassLevel.setArcadeStats("Arcade_Stats",ArcStats, MainActivity.THEcontext);
					ArcTimes[level-1]=leveltime;
					PassLevel.setArcadeStats("Arcade_Times",ArcTimes, MainActivity.THEcontext);
					}
				else if(ArcStats[level-1]==2){
					if (leveltime<ArcTimes[level-1])
					{ArcTimes[level-1]=leveltime;
					PassLevel.setArcadeStats("Arcade_Times",ArcTimes, MainActivity.THEcontext);
					}
				}
				}
					else if (thisNcorrect2<21)
					{if(ArcStats[level-1]<3){
						ArcStats[level-1]=3;
						PassLevel.setArcadeStats("Arcade_Stats",ArcStats, MainActivity.THEcontext);
						ArcTimes[level-1]=leveltime;
						PassLevel.setArcadeStats("Arcade_Times",ArcTimes, MainActivity.THEcontext);}
					else if(ArcStats[level-1]==3){
						if (leveltime<ArcTimes[level-1])
						{ArcTimes[level-1]=leveltime;
						PassLevel.setArcadeStats("Arcade_Times",ArcTimes, MainActivity.THEcontext);
						}
					}
					}
		
				
	}
	
	
	private DataScore copyds(DataScore dScore2) {
		DataScore newone = new DataScore();
		newone.setLevel(dScore2.getLevel());
		newone.setOp1(dScore2.getOp1());
		newone.setOp2(dScore2.getOp2());
		newone.setOperator(dScore2.getOperator());
		newone.setResult(dScore2.getResult());
		newone.setAnswer(dScore2.getAnswer());
		newone.setVectorAns(dScore2.getVectorAns());
		newone.setCorrect(dScore2.getCorrect());
		newone.setErase(dScore2.getErase());
		newone.setTimes(dScore2.getTimes());
		newone.setConfidence(dScore2.getConfidence());
		newone.setEffort(dScore2.getEffort());
		newone.setInitConfidence(dScore2.getInitConfidence());
		newone.setInitEffort(dScore2.getInitEffort());
		newone.setVectorConf(dScore2.getVectorConf());
		newone.setTimesConf(dScore2.getTimesConf());
		newone.setVectorEffort(dScore2.getVectorEffort());
		newone.setTimesEff(dScore2.getTimesEff());
		newone.setDate1(dScore2.gettheDate1());
		newone.setDate2(dScore2.gettheDate2());
		newone.setReftime(dScore2.getReftime());
		newone.setCorrinarow(dScore2.getCorrinarow());
		newone.setTimeEx(dScore2.getTimeEx());
		newone.setGameType(dScore2.getGameType());
		newone.setOpType(dScore2.getOpType());
		newone.setHintAva(dScore2.getHintAva());
		newone.setHintDisp(dScore2.getHintDisp());
		newone.setHidden(dScore2.getHidden());
		newone.setArcorder(dScore2.getArcorder());
		newone.setAcccorrect(dScore2.getAcccorrect());
		newone.getMyScore();
		newone.getTotTime();
		
		
		return newone;
	}

	public void FNextLevel3(){
		
		Dialog d=new Dialog(PlayArcade.this);
		d.getWindow();
		d.requestWindowFeature(Window.FEATURE_NO_TITLE);
		d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
	    d.setContentView(R.layout.toast_endlevel);
	    d.setCancelable(false);
        TextView congrats = (TextView)d.findViewById(R.id.congrats);
        TextView levelfinished = (TextView)d.findViewById(R.id.levelfinished);
        
        ImageView starslevel = (ImageView)d.findViewById(R.id.imageView1);
		TextView correctTotal = (TextView)d.findViewById(R.id.totalcorrect);
		TextView correctTxt = (TextView)d.findViewById(R.id.correcttext);
		
		ImageView same = (ImageView)d.findViewById(R.id.buttonsame);
		ImageView next = (ImageView)d.findViewById(R.id.buttonnext);
		TextView backToMainMenu = (TextView) d.findViewById(R.id.backtomainmenu);
		
		if (thisNcorrect<15) congrats.setText(getString(R.string.retry));
		else congrats.setText(getString(R.string.n_level_completed_congratulations)+level);
		levelfinished.setText(getString(R.string.actual_level)+level);
		if (thisNcorrect<15){starslevel.setImageResource(R.drawable.star_0);}
		else if (thisNcorrect<17)starslevel.setImageResource(R.drawable.star_1);
		else if (thisNcorrect<20)starslevel.setImageResource(R.drawable.star_2);
		else if (thisNcorrect<21)starslevel.setImageResource(R.drawable.star_3);
		
		if (thisNcorrect<15) correctTotal.setText(Html.fromHtml("<font color=#ED1566>"+thisNcorrect+"</font>/20"));
		else correctTotal.setText(Html.fromHtml("<font color=#0b9E8C>"+thisNcorrect+"</font>/20"));
		
		correctTxt.setText(thisNcorrect+getString(R.string.n_correct));
		
		if (thisNcorrect<15) next.setVisibility(View.GONE);
		else next.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        // your code here
		    	FStartNL2();
		     }
		 });
		
		same.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        // your code here
		    	FStartThisL2();
		     }
		 });

		backToMainMenu.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// your code here
				returnhome();
			}
		});
		
		
        d.show();   
		
			
		}
	
	
	public void FStartNL2(){
		PassLevel.setNewLevel("Levels_Completed_Arcade", level+1, this);
		Intent arcIntent = new Intent(this, PlayArcade.class);
		arcIntent.putExtra("level",level+1);
		this.startActivity(arcIntent);
		finish();
		
	};
	public void FStartThisL2(){
		Intent arcIntent = new Intent(this, PlayArcade.class);
		arcIntent.putExtra("level",level);
		this.startActivity(arcIntent);
		finish();
	};
	
	public void FStartNL(){
		if(level==0){
	    //1+1
		startPlay(1,0, 0);
	}
	else if(level==1){
	    //1x1
		startPlay(2,1, 0);
	}
	else if(level==2){
	    //2+2
		startPlay(3,0, 1);
	}
	else if(level==3){
	    //2x1
		startPlay(4,1, 1);
	}
	else if(level==4){
	    //2x1
		startPlay(5,1, 1);
	}
	else if(level==5){
	    //3x1
		startPlay(6,1, 2);
	}
	else if(level==6){
	    //3x1
		startPlay(7,1, 2);
	}
	else if(level==7){
	    //2^2
		int askpersonal = PassLevel.getAskPersonal("Ask", MainActivity.THEcontext);
		if (askpersonal<7){
			Intent personalIntent = new Intent(this, PersonalQuestions.class);
			this.startActivity(personalIntent);
			finish();}
		
		else{startPlay(8,2, 1);}
	}
	else if(level==8){
	    //2^2
		startPlay(9,2, 1);
	}
	else if(level==9){
	    //2^2
		startPlay(10,2, 1);
	}
	else if(level==10){
	    //4x1
		startPlay(11,1, 4);
	}
	else if(level==11){
	    //4x1
		startPlay(12,1, 4);
	}
	else if(level==12){
	    //3^2
		startPlay(13,2, 2);
	}
	else if(level==13){
	    //3^2
		startPlay(14,2, 2);
	}
	else if(level==14){
	    //3^2
		startPlay(15,2, 2);
	}
	else if(level==15){
	    //4^2
		int askpersonal = PassLevel.getAskPersonal("Ask", MainActivity.THEcontext);
		if (askpersonal<15){
			Intent personalIntent = new Intent(this, PersonalQuestions.class);
			this.startActivity(personalIntent);
			finish();}
		
		else{startPlay(16,2, 3);}
	}
	else if(level==16){
	    //4^2
		startPlay(17,2, 3);
	}
	else if(level==17){
	    //4^2
		startPlay(18,2, 3);
	}
	else if(level==18){
	    //4^2
		startPlay(19,2, 3);
	}
	else if(level==19){
	    //GANO! Decirle algo especial
		
	}
		
}
public void startPlay(int chosenLevel, int chosenOperator, int chosenSublevel){
	
	//if level==2
	//if level==6
	//if level==10
	//if level==12 PREGUNTAR PERSONAL QUESTIONS
	//else
	//start gameplay
	Intent playIntent = new Intent(this, PlayGame.class);
	playIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	playIntent.putExtra("level",chosenLevel);
	playIntent.putExtra("operator",chosenOperator);
	playIntent.putExtra("sublevel", chosenSublevel);
	this.startActivity(playIntent);
}
	
	
	  public static String POST(String url, DataScore DataObj){
		  	InputStream inputStream = null;
	        String result = "";
	        
	        //Reatrive personal
	        String savedAUID=PassLevel.getPersonal("myAnUID",MainActivity.THEcontext);
	        String savedName=PassLevel.getPersonal("myName",MainActivity.THEcontext);
	        String savedEmail=PassLevel.getPersonal("myEmail",MainActivity.THEcontext);
	        String savedBirth=PassLevel.getPersonal("myBirth",MainActivity.THEcontext);
	        String savedGender=PassLevel.getPersonal("myGender",MainActivity.THEcontext);
	        String savedStudies=PassLevel.getPersonal("myStudies",MainActivity.THEcontext);
	        String savedHand=PassLevel.getPersonal("myHand",MainActivity.THEcontext);
	        				
	        
	        try {
	        	 
	            // 1. create HttpClient
	            HttpClient httpclient = new DefaultHttpClient();
	 
	            // 2. make POST request to the given URL
	            HttpPost httpPost = new HttpPost(url);
	            String ToSend = "";
	 
	            // 3. build jsonObject
	            JSONObject jsonData = new JSONObject();
	            jsonData=JsonUtil.toJSon(DataObj, savedAUID,savedName,savedEmail,savedBirth,savedGender,savedStudies,savedHand);
	            
	  
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
	  

private class HttpAsyncTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {
    	
        return POST(urls[0],DScore);
    	
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
    	
        Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        //DScoreList.remove(0);
        
   }
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


public AlertDialog FWon(){
	//Se construye
	final AlertDialog.Builder builderNL = new AlertDialog.Builder(this);
	builderNL.setTitle(getString(R.string.game_completed));
	builderNL.setCancelable(false);
			
	LinearLayout LinNL=new LinearLayout(this);
	LinNL.setOrientation(1);
	
	TextView textNL1=new TextView(this);
	textNL1.setText(getString(R.string.game_completed_congratulations));
	textNL1.setGravity(1);
	textNL1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
	LinNL.addView(textNL1);
	TextView textNL2=new TextView(this);
	textNL2.setText(getString(R.string.game_completed_message));
	textNL2.setGravity(1);
	textNL2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
	LinNL.addView(textNL2);
	builderNL.setView(LinNL);
	
	
	
	
	builderNL.setPositiveButton(getString(R.string.back_main_menu),new DialogInterface.OnClickListener()
	{
		
		public void onClick(DialogInterface dialog,int id){returnhome();
			
		} 
				
		});
	

	
	AlertDialog NLDialog = builderNL.create();

	return NLDialog;
	//IntrDialog.show();
	
	
	}
public void returnhome(){
	Intent returnIntent = new Intent(this, MainActivity.class);
	returnIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	this.startActivity(returnIntent);
	finish();}

public void correction(int correct){
	if (correct==1){
		equationTxt.setBackgroundResource(R.drawable.button_green);
		if ((int)(DScore.getTotTime()/1000)>2*tavg){equationTxt.setText(getString(R.string.motivation_message));}
		else{equationTxt.setText(Html.fromHtml("<big><font color='#FF9000'>\u2605</font> "+getString(R.string.correct)+"</big>"));}
		//equationTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star_yellow, 0,R.drawable.star_yellow, 0);
	}
		
	else if (correct==0){
		equationTxt.setBackgroundResource(R.drawable.button_pink);
		equationTxt.setText(Html.fromHtml("<big><font color='#E4E5E6'>\u2605</font> "+getString(R.string.wrong_correct_answer)+answer+"</big> "+getString(R.string.wrong_entered_answer)+"<big>"+DScore.getAnswer()+"</big>"));
		//equationTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star_gray, 0,R.drawable.star_gray, 0);
	}
	timeranswer=reseteqtxt();
	timeranswer.start();
	

}

public CountDownTimer reseteqtxt(){
	CountDownTimer reset = new CountDownTimer(3000, 1000) {

		   public void onTick(long millisUntilFinished) {
		   }

		   public void onFinish() {
			   equationTxt.setBackgroundResource(R.drawable.button_green2);
			   if(!hintused)
				   {if (!thisoperation.equals("1d+1d") && !thisoperation.equals("1dx1d")) equationTxt.setText(Html.fromHtml("<big>"+hints+"</big>/3 "+getString(R.string.available_hints)));
				   else equationTxt.setText(Html.fromHtml("<big><font color='#93C9C3'>A</font></big>"));
				   }
			   //equationTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,0, 0);
			   }

		};
	return reset;
	
		
}


		
	//set high score
	private void setHighScore(){
		int exScore = getScore();
		
		if(exScore>0){
			//we have a valid score	
			SharedPreferences.Editor scoreEdit = gamePrefs.edit();
			DateFormat dateForm = new SimpleDateFormat("dd MMMM yyyy");
			String dateOutput = dateForm.format(new Date());
			//get existing scores
			String scores = gamePrefs.getString("highScores", "");
			//check for scores
			if(scores.length()>0){
				//we have existing scores
				List<Score> scoreStrings = new ArrayList<Score>();
				//split scores
				String[] exScores = scores.split("\\|");
				//add score object for each
				for(String eSc : exScores){
					String[] parts = eSc.split(" - ");
					scoreStrings.add(new Score(parts[0], Integer.parseInt(parts[1])));
				}
				//new score
				Score newScore = new Score(dateOutput, exScore);
				scoreStrings.add(newScore);
				//sort
				Collections.sort(scoreStrings);
				//get top ten
				StringBuilder scoreBuild = new StringBuilder("");
				for(int s=0; s<scoreStrings.size(); s++){
					if(s>=10) break;
					if(s>0) scoreBuild.append("|");
					scoreBuild.append(scoreStrings.get(s).getScoreText());
				}
				//write to prefs
				scoreEdit.putString("highScores", scoreBuild.toString());
				scoreEdit.commit();
				
				

			}
			else{
				//no existing scores
				scoreEdit.putString("highScores", ""+dateOutput+" - "+exScore);
				scoreEdit.commit();
				
				
			}
		}
	}
	
	
		
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; return!
	            Intent intent = new Intent(this, HighList.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) { //Back key pressed
           //Things to Do
        	Intent returnIntent = new Intent(this, HighList.class);
        	returnIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(returnIntent);
			finish();
			return true;
        }
        return super.onKeyDown(keyCode, event);
    }

	//set high score if activity destroyed
	protected void onDestroy(){
		//setHighScore();
		
		super.onDestroy();
			
	}
	
		
	@Override
    protected void onPause () {
        super.onPause();
        if (toastanswer != null)toastanswer.cancel();
        if (timeranswer != null)timeranswer.cancel();
        PassLevel.activityPaused();
        //Reatrive personal
        String savedAUID=PassLevel.getPersonal("myAnUID",MainActivity.THEcontext);
        String savedName=PassLevel.getPersonal("myName",MainActivity.THEcontext);
        String savedEmail=PassLevel.getPersonal("myEmail",MainActivity.THEcontext);
        String savedBirth=PassLevel.getPersonal("myBirth",MainActivity.THEcontext);
        String savedGender=PassLevel.getPersonal("myGender",MainActivity.THEcontext);
        String savedStudies=PassLevel.getPersonal("myStudies",MainActivity.THEcontext);
        String savedHand=PassLevel.getPersonal("myHand",MainActivity.THEcontext);
        
		Handler_sqlite helper = new Handler_sqlite(MainActivity.THEcontext);
		helper.open();
		
		//ESTO VA
		for (int ilist=0; ilist < DScoreList.size(); ilist++)
			{JSONObject jsonData = new JSONObject();
			jsonData=JsonUtil.toJSon(DScoreList.get(ilist),savedAUID,savedName,savedEmail,savedBirth,savedGender,savedStudies,savedHand);
			long valorDeRetorno = helper.insertValues(jsonData.toString());
			//if (valorDeRetorno==-1)
			}
        //Cursor cursor = helper.fetchReminder(1);
		//String sendthis = cursor.getString(cursor.getColumnIndex("Data_to_Send")); // id is column name in db
		
		helper.close();
		DScoreList.clear();
		
		//int[] NNN = new int[19];
		//NNN=PassLevel.getNcorrect("Ncorrect", MainActivity.THEcontext);
		//NNN[level-1]=NNN[level-1]+thisNcorrect;
		//PassLevel.setNcorrect("Ncorrect",NNN, MainActivity.THEcontext);
		
		//int[] NNI = new int[19];
		//NNI=PassLevel.getNcorrect("Nincorrect", MainActivity.THEcontext);
		//NNI[level-1]=NNI[level-1]+thisNincorrect;
		//PassLevel.setNcorrect("Nincorrect",NNI, MainActivity.THEcontext);
		
			//ESTO VA!!!
			new AsyncSend().execute(BuildConfig.SERVER_URL);
	finish();		
    }
	
	@Override
	protected void onResume() {
	  super.onResume();
	  PassLevel.activityResumed();
	}
	
	
	

	//save instance state
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		//save score and level
		int exScore = getScore();
		savedInstanceState.putInt("score", exScore);
		savedInstanceState.putInt("level", sublevel);
		//superclass method
		super.onSaveInstanceState(savedInstanceState);
	}
}

