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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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


public class PlayGame extends Activity implements OnClickListener {

	//gameplay elements
	private int level =0, sublevel = 0, operator = 0, operand1 = 0, operand2 = 0;
	private long answer = 0;
		
	//operator constants
	private final int ADD_OPERATOR = 0, MULTIPLY_OPERATOR = 1, SQUARE_OPERATOR = 2;
	//operator text
	private String[] operators = {"+", "x", "x"};
	private String gametype ="Training";
	
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
	
	//private int[][] ntocompletelevel={
	//		{10, 5, 5, 5},//va 10 al principio
	//		{10, 5, 5, 5, 5, 5, 5},//va 10 al principio
	//		{100, 5, 5, 3,3},
	//		};
	
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
	private int hintsavailable=-1;
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
	public long thisTotTime=0;
	
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
		scoreTxt.setTextColor(Color.parseColor("#131d24"));
		

		//hide tick cross initially
		//response.setVisibility(View.INVISIBLE);
		scoreTxt.setVisibility(View.INVISIBLE);

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
		
		clearBtn.setText("\u2190");
		
		if(savedInstanceState!=null){
			//saved instance state data
			sublevel=savedInstanceState.getInt("level");
			int exScore = savedInstanceState.getInt("score");
			//scoreTxt.setText("Score: "+exScore);
			scoreTxt.setText("Score: "+0);
				
			
		}
		else{
			//get passed level number
			Bundle extras = getIntent().getExtras();
			if(extras !=null)
			{	int passedLevel = extras.getInt("level", -1);
				if(passedLevel>=0) level = passedLevel;
				
				int passedsubLevel = extras.getInt("sublevel", -1);
				if(passedsubLevel>=0) sublevel = passedsubLevel;
				
				int passedOperator = extras.getInt("operator", -1);
				if(passedLevel>=0) operator = passedOperator;
											
				
			}
		}

		//Pasarselo a la dataScore
		//Buscar un formato mas facil para leer despues
		DScore.setLevel(-1);
		DScore.setGameType(gametype);
		
		//npasslevel = ntocompletelevel[operator][sublevel];
		tavg = ttocompletelevel[operator][sublevel];
		DScore.setReftime(tavg*1000);
		//String AUID = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
		
		
		//Action Bar
		ActionBar AB = getActionBar();
		AB.setDisplayHomeAsUpEnabled(true);
		AB.setTitle(Html.fromHtml("<font color='#60b0c1'>Entrenamente </font>"));
		AB.hide();
		//AB.setSubtitle("sub-title");
		switch(operator){
		 	case ADD_OPERATOR: switch (sublevel){
		 		case 0: levelTxt.setText("1+1");AB.setSubtitle(getString(R.string.actual_level)+level+": 1 dig + 1dig");break;
		 		case 1: levelTxt.setText("2+2");break;
		 		case 2: AB.setSubtitle(getString(R.string.actual_level)+level+": 3 dig + 3dig");break;
		 		case 3: AB.setSubtitle(getString(R.string.actual_level)+level+": 4 dig + 4dig");break;
		 	}break;
		 	case MULTIPLY_OPERATOR: switch (sublevel){
		 		case 0: levelTxt.setText("1x1");break;
		 		case 1: levelTxt.setText("2x1");break;
		 		case 2: levelTxt.setText("3x1");break;
		 		case 3: levelTxt.setText(getString(R.string.actual_level)+level);AB.setSubtitle(getString(R.string.actual_level)+level+": 2 dig x 2dig");break;
		 		case 4: levelTxt.setText("4x1");break;
		 		case 5: levelTxt.setText(getString(R.string.actual_level)+level);AB.setSubtitle(getString(R.string.actual_level)+level+": 3 dig x 2dig");break;
		 		case 6: levelTxt.setText(getString(R.string.actual_level)+level);AB.setSubtitle(getString(R.string.actual_level)+level+": 3 dig x 3dig");break;
		 	
		 	}break;
		 	case SQUARE_OPERATOR: switch (sublevel){
		 		case 0: levelTxt.setText(getString(R.string.actual_level)+level);AB.setSubtitle(Html.fromHtml(getString(R.string.actual_level)+level+": (1dig)<sup><small>2</small></sup>"));break;
		 		case 1: levelTxt.setText(Html.fromHtml("2<sup><small>2</small></sup>"));break;
		 		case 2: levelTxt.setText(Html.fromHtml("3<sup><small>2</small></sup>"));break;
		 		case 3: levelTxt.setText(Html.fromHtml("4<sup><small>2</small></sup>"));break;
		 		case 4: levelTxt.setText(getString(R.string.actual_level)+level);AB.setSubtitle(Html.fromHtml(getString(R.string.actual_level)+level+": (5dig)<sup><small>2</small></sup>"));break;
		 	 }break;
		}
		
		 
		
		 
		 
		 chrono.setOnChronometerTickListener(
			        new Chronometer.OnChronometerTickListener(){

			    @Override
			    public void onChronometerTick(Chronometer chronometer) {
			     // TODO Auto-generated method stub
			    long myElapsedMillis = SystemClock.elapsedRealtime() -chrono.getBase();
			    if (myElapsedMillis>59*60*1000)chrono.stop();
			    int remtime = (int) (tavg*1000*2-myElapsedMillis);
			    //if (remtime>=0)timelimit.setProgress(remtime);
			    //else timelimit.setProgress(0);
			    if (level==8 ||level==12 ||level==15 ||level==19 ||level==24)
			    {hidden=1;
			    if (remtime>(int)(tavg*2000-1000)) 										 question.setTextColor(Color.parseColor("#000000"));
			    else if (remtime<(int)(tavg*2000-1000) && remtime>(int)(tavg*2000-2000)) question.setTextColor(Color.parseColor("#4d4d4d"));
			    else if (remtime<(int)(tavg*2000-2000) && remtime>(int)(tavg*2000-3000)) question.setTextColor(Color.parseColor("#818181"));
			    else if (remtime<(int)(tavg*2000-3000) && remtime>(int)(tavg*2000-4000)) question.setTextColor(Color.parseColor("#9a9a9a"));
			    else if (remtime<(int)(tavg*2000-4000) && remtime>(int)(tavg*2000-5000)) question.setTextColor(Color.parseColor("#cecece"));
			    else if (remtime<(int)(tavg*2000-5000) && remtime>(int)(tavg*2000-6000)) question.setTextColor(Color.parseColor("#d8d8d8"));
			    else if (remtime<(int)(tavg*2000-6000)) 								 question.setTextColor(Color.parseColor("#FFFFFF"));
			    }
			    else hidden=0;
			     }}
			      );
			  
		 
		 		 
		
		//initialize random
		random = new Random();
		//play
		chooseQuestion();
		eqsettext();
		
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
			
			ContIntr+=1;
			//Se pregunta instrospeccion cada 5
			//El cronometro sigure corriendo PROBLEMA
				//if (ContIntr%5==0){
					if (random.nextInt(5)==1){
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
		//reset answer text
		answerTxt.setText("= ?");
		answerTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
		enterBtn.setEnabled(false);
		
		//choose operator
		//operator = random.nextInt(operators.length);
		//choose operands
		operand1 = getOperand();
		//DScore.setOp1(operand1);
		
		//Elegir Operands
				switch(operator){
				case ADD_OPERATOR:
					operand2 = getOperand();
					break;
				//Para tener numeros de distintos ordenes resuelvo dividiendo por 10 o 100 segun corresponda.
					//seguramente apareza el 1 como operando, lo cual no es bueno.
				case MULTIPLY_OPERATOR:
					switch(sublevel){
						case 0:
						case 3:
						case 6:
							operand2 = getOperand();
							break;	
						case 1:
							do { operand2 = Math.round(getOperand()/10); }
							while(operand2 == 1);
							break;
						case 5:
							do { operand2 = Math.round(getOperand()/10);}
							while(operand2 == 10);
							break;
						case 2:
							do {operand2 = Math.round(getOperand()/100);}
							while(operand2 == 1);
							break;
						case 4:
							do{operand2 = Math.round(getOperand()/1000);}
							while(operand2 == 1);
							break;
					}
					break;
					
				case SQUARE_OPERATOR:
					operand2 = operand1;
					break;
				default:
					break;
				}
				//DScore.setOp2(operand2);

		// checks for operators
		//if(operator==SUBTRACT_OPERATOR){
			//no negative answers
		//	while(operand2>operand1){
		//		operand1 = getOperand();
		//		operand2 = getOperand();
		//	}
		//}
		//else if(operator==DIVIDE_OPERATOR){
		//	//whole numbers only
		//	while((((double)operand1/(double)operand2)%1 > 0) 
		//			|| (operand1==operand2)){
		//		operand1 = getOperand();
		//		operand2 = getOperand();
		//	}
		//}

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
		if(operator==2)
		{question.setText(Html.fromHtml(operand1+"<sup><small>2</small></sup>"));}
		else{question.setText(operand1+" "+operators[operator]+" "+operand2);}
		question.setTextColor(Color.parseColor("#000000"));
		
						
		//Start Chrono
		//No se bien si va aca, me parece que no
		DScore.setErase(0);
		Calendar Dt1 = Calendar.getInstance();
		DScore.setDate1(Dt1);
		chrono.setBase(SystemClock.elapsedRealtime());
		chrono.start();
		starttime=System.currentTimeMillis();
		timelimit.setMax(tavg*2*1000);
		timelimit.setProgress(tavg*2*1000);
				
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
			int exScore = getScore();
									
			//check answer
			if(enteredAnswer==answer){
				//correct
				DScore.setCorrect(1);
				
				if ((int)(DScore.getTotTime()/1000)<2*tavg)
				{CorrinaRow++;
				thisNcorrect++;
				thisTotTime=thisTotTime+DScore.getTotTime();
				DScore.setCorrinarow(CorrinaRow);
				DScore.setTimeEx(0);
				//totTimeLevel=totTimeLevel+(int)(DScore.getTotTime()/1000);
				}
				
				else {
				//totTimeLevel=0;
				CorrinaRow++;
				thisNcorrect++;
				thisTotTime=thisTotTime+DScore.getTotTime();
				DScore.setCorrinarow(CorrinaRow);
				DScore.setTimeEx(1);
				}
				
					if (toastanswer != null){toastanswer.cancel();}
					if (timeranswer != null){timeranswer.cancel();}
					//toastanswer=Ftoastanswer(1);
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
				//scoreTxt.setText("Score: "+(MyScore));
				scoreTxt.setText("Score: "+0);
				//response.setImageResource(R.drawable.tick);
				//response.setVisibility(View.VISIBLE);
								
			}
			else{
				//set high score
				//setHighScore();
				//incorrect
				DScore.setCorrect(0);
					if (toastanswer != null)toastanswer.cancel();
					if (timeranswer != null)timeranswer.cancel();
						//toastanswer=Ftoastanswer(0);
						//	toastanswer.show();
					correction(0);
				
								
				//Get Score MIO
							//scoreTxt.setText("Score: "+(MyScore));
							scoreTxt.setText("Score: "+0);
				//response.setImageResource(R.drawable.cross);
				//response.setVisibility(View.VISIBLE);
				CorrinaRow=0;
				//totTimeLevel=0;
				DScore.setCorrinarow(CorrinaRow);
				thisNincorrect++;
				if ((int)(DScore.getTotTime()/1000)<2*tavg)
				DScore.setTimeEx(0);
				else DScore.setTimeEx(1);
				
			}
			
			//switch(npasslevel){
			//case 10:
			//	switch(CorrinaRow){
			//	case 0:response.setImageResource(R.drawable.correct0_10);break;
			//	case 1:response.setImageResource(R.drawable.correct1_10);break;
			//	case 2:response.setImageResource(R.drawable.correct2_10);break;
			//	case 3:response.setImageResource(R.drawable.correct3_10);break;
			//	case 4:response.setImageResource(R.drawable.correct4_10);break;
			//	case 5:response.setImageResource(R.drawable.correct5_10);break;
			//	case 6:response.setImageResource(R.drawable.correct6_10);break;
			//	case 7:response.setImageResource(R.drawable.correct7_10);break;
			//	case 8:response.setImageResource(R.drawable.correct8_10);break;
			//	case 9:response.setImageResource(R.drawable.correct9_10);break;
			//	case 10:response.setImageResource(R.drawable.correct10_10);break;}
			//	break;
			//case 5:
			///	switch(CorrinaRow){
			//	case 0:response.setImageResource(R.drawable.correct0_5);break;
			//	case 1:response.setImageResource(R.drawable.correct1_5);break;
			//	case 2:response.setImageResource(R.drawable.correct2_5);break;
			//	case 3:response.setImageResource(R.drawable.correct3_5);break;
			//	case 4:response.setImageResource(R.drawable.correct4_5);break;
			//	case 5:response.setImageResource(R.drawable.correct5_5);break;}
			//	break;
			//case 3:
			//	switch(CorrinaRow){
			//	case 0:response.setImageResource(R.drawable.correct0_3);break;
			//	case 1:response.setImageResource(R.drawable.correct1_3);break;
			//	case 2:response.setImageResource(R.drawable.correct2_3);break;
			//	case 3:response.setImageResource(R.drawable.correct3_3);break;}
			//	break;
			//}
			
			//response.setVisibility(View.VISIBLE);
									
				Calendar Dt2 = Calendar.getInstance();
				DScore.setDate2(Dt2);
				DScore.setOp1(operand1);
				DScore.setOp2(operand2);
				DScore.setResult(answer);
				if (operator==0) DScore.setOperator("+");
				else DScore.setOperator("x");
				
				String thisoperation="";
				String op1=Integer.toString(operand1);
				String op2=Integer.toString(operand2);
				int n1 = op1.length();
				int n2 = op2.length();
				if (operator==2) thisoperation =("("+n1+"d)^2");
				else if (operator==1)thisoperation =(""+n1+"dx"+n2+"d");
				else if (operator==0)thisoperation =(""+n1+"d+"+n2+"d");
				
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

	public AlertDialog FWon(){
		//Se construye
		final AlertDialog.Builder builderNL = new AlertDialog.Builder(this);
		builderNL.setTitle(getString(R.string.level_completed));
		builderNL.setCancelable(false);
				
		LinearLayout LinNL=new LinearLayout(this);
		LinNL.setOrientation(1);
		
		TextView textNL1=new TextView(this);
		textNL1.setText(getString(R.string.training_completed));
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
		this.startActivity(returnIntent);
		finish();}
		
		
	
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
	    //3x1
		startPlay(8,1, 2);
	}
	else if(level==8){
	    //2^2
		int askpersonal = PassLevel.getAskPersonal("Ask", MainActivity.THEcontext);
		if (askpersonal<7){
			Intent personalIntent = new Intent(this, PersonalQuestions.class);
			this.startActivity(personalIntent);
			finish();}
		
		else{startPlay(9,2, 1);}
	}
	else if(level==9){
	    //2^2
		startPlay(10,2, 1);
	}
	else if(level==10){
	    //2^2
		startPlay(11,2, 1);
	}
	else if(level==11){
	    //2^2
		startPlay(12,2, 1);
	}
	else if(level==12){
	    //4x1
		startPlay(13,1, 4);
	}
	else if(level==13){
	    //4x1
		startPlay(14,1, 4);
	}
	else if(level==14){
	    //4x1
		startPlay(15,1, 4);
	}
	else if(level==15){
	    //3^2
		startPlay(16,2, 2);
	}
	else if(level==16){
	    //3^2
		startPlay(17,2, 2);
	}
	else if(level==17){
	    //3^2
		startPlay(18,2, 2);
	}
	else if(level==18){
	    //3^2
		startPlay(19,2, 2);
	}
	else if(level==19){
	    //4^2
		int askpersonal = PassLevel.getAskPersonal("Ask", MainActivity.THEcontext);
		if (askpersonal<15){
			Intent personalIntent = new Intent(this, PersonalQuestions.class);
			this.startActivity(personalIntent);
			finish();}
		
		else{startPlay(20,2, 3);}
	}
	else if(level==20){
	    //4^2
		startPlay(21,2, 3);
	}
	else if(level==21){
	    //4^2
		startPlay(22,2, 3);
	}
	else if(level==22){
	    //4^2
		startPlay(23,2, 3);
	}
	else if(level==23){
	    //4^2
		startPlay(24,2, 3);
	}
	else if(level==24){
	    //GANO! Decirle algo especial
		AlertDialog won= FWon();
		won.show();
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

public void correction(int correct){
	if (correct==1){
		equationTxt.setBackgroundResource(R.drawable.button_green);
		//if ((int)(DScore.getTotTime()/1000)>2*tavg){equationTxt.setText("Pod�s hacerlo m�s r�pido");}
		if (level==9) equationTxt.setText(Html.fromHtml("<font color='#0b9E8C'><sup><small>2</small></sup><br /></font>"+"<big><font color='#FF9000'>\u2605</font> "+getString(R.string.correct)+"</big>"+"<br /><font color='#0b9E8C'><sup><small>2</small></sup></font>"));
		else if (level==16) equationTxt.setText(Html.fromHtml("<font color='#0b9E8C'><sup><small>2</small></sup><br /></font>"+"<big><font color='#FF9000'>\u2605</font> "+getString(R.string.correct)+"</big>"+"<br /><font color='#0b9E8C'><sup><small>2</small></sup><br /><sup><small>2</small></sup></font>"));
		else if(level==20) equationTxt.setText(Html.fromHtml("<font color='#0b9E8C'><sup><small>2</small></sup><br /><sup><small>2</small></sup><br /></font>"+"<big><font color='#FF9000'>\u2605</font> "+getString(R.string.correct)+"</big>"+"<br /><font color='#0b9E8C'><sup><small>2</small></sup><br /><sup><small>2</small></sup></font>"));
		else 
		equationTxt.setText(Html.fromHtml("<big><font color='#FF9000'>\u2605</font> "+getString(R.string.correct)+"</big>"));
		//equationTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star_yellow, 0,R.drawable.star_yellow, 0);
	}
		
	else if (correct==0){
		equationTxt.setBackgroundResource(R.drawable.button_pink);
		if (level==9) equationTxt.setText(Html.fromHtml("<font color='#ED1566'><sup><small>2</small></sup><br /></font>"+"<big><font color='#E4E5E6'>\u2605</font> "+getString(R.string.wrong_correct_answer)+answer+"</big> "+getString(R.string.wrong_entered_answer)+"<big>"+DScore.getAnswer()+"</big><br /><font color='#ED1566'><sup><small>2</small></sup></font>"));
			else if (level==16) equationTxt.setText(Html.fromHtml("<font color='#ED1566'><sup><small>2</small></sup><br /></font>"+"<big><font color='#E4E5E6'>\u2605</font> "+getString(R.string.wrong_correct_answer)+answer+"</big> "+getString(R.string.wrong_entered_answer)+"<big>"+DScore.getAnswer()+"</big><br /><font color='#ED1566'><sup><small>2</small></sup><br /><sup><small>2</small></sup></font>"));
				else if(level==20) equationTxt.setText(Html.fromHtml("<font color='#ED1566'><sup><small>2</small></sup><br /><sup><small>2</small></sup><br /></font>"+"<big><font color='#E4E5E6'>\u2605</font> "+getString(R.string.wrong_correct_answer)+answer+"</big> "+getString(R.string.wrong_entered_answer)+"<big>"+DScore.getAnswer()+"</big><br /><font color='#ED1566'><sup><small>2</small></sup><br /><sup><small>2</small></sup></font>"));
					else 
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
			   //equationTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0,0, 0);
			   eqsettext();
			   }

		};
	return reset;
			
}


private void eqsettext(){
	equationTxt.setLines(1);
	 switch(level){
		case 1: equationTxt.setText(""); hintdisplayed=0;break;
		case 2: equationTxt.setText("");hintdisplayed=0;break;
		case 3: equationTxt.setText("");hintdisplayed=0;break;
		case 4: hintdisplayed=1;break; // Este es dinamico
		case 5: equationTxt.setText("");hintdisplayed=0;break;
		case 6: hintdisplayed=1;break; // Este es dinamico
		case 7: equationTxt.setText("");hintdisplayed=0;break;
		case 8: equationTxt.setText(getString(R.string.practice_fadeout_message));hintdisplayed=0;break;
		case 9: hintdisplayed=1;break; // Este es dinamico
		case 10: equationTxt.setText(Html.fromHtml("x<sup><small>2</small></sup> = (x-a)(x+a) + a<sup><small>2</small></sup>"));hintdisplayed=1;break;
		case 11: equationTxt.setText("");hintdisplayed=0;break;
		case 12: equationTxt.setText(getString(R.string.practice_fadeout_message));hintdisplayed=0;break;
		case 13: hintdisplayed=1;break; // Este es dinamico
		case 14: equationTxt.setText("");hintdisplayed=0;break;
		case 15: equationTxt.setText(getString(R.string.practice_fadeout_message));hintdisplayed=0;break;
		case 16: hintdisplayed=1;break; // Este es dinamico
		case 17: hintdisplayed=1;break; // Este es dinamico
		case 18: equationTxt.setText("");hintdisplayed=0;break;
		case 19: equationTxt.setText(getString(R.string.practice_fadeout_message));hintdisplayed=0;break;
		case 20: hintdisplayed=1;break; // Este es dinamico
		case 21: hintdisplayed=1;break; // Este es dinamico
		case 22: hintdisplayed=1;break; // Este es dinamico
		case 23: equationTxt.setText("");hintdisplayed=0;break;
		case 24: equationTxt.setText(getString(R.string.practice_fadeout_message));hintdisplayed=0;break;
		
}
	 switch(level){
		case 4: equationTxt.setText(10*(int)(operand1/10)+"x"+operand2+" + "+operand1%10+"x"+operand2);break; // Este es dinamico
		case 6: equationTxt.setText(100*(int)(operand1/100)+"x"+operand2+" + "+10*(int)((operand1%100)/10)+"x"+operand2+" + "+operand1%10+"x"+operand2);break;
		case 9: int numberA=Math.min(operand1%10,10-operand1%10);
				equationTxt.setText(Html.fromHtml("x<sup><small>2</small></sup> = (x-a)(x+a) + a<sup><small>2</small></sup><br />"
				+"("+operand1+"-"+numberA+")"+" x "+"("+operand1+"+"+numberA+")"+" + "+numberA+"<sup><small>2</small></sup><br />"
				+(operand1-numberA)+" x "+(operand1+numberA)+" + "+numberA+"<sup><small>2</small></sup>"));
				equationTxt.setLines(3);break;
		case 13: equationTxt.setText(1000*(int)(operand1/1000)+"x"+operand2+" + "+100*(int)((operand1%1000)/100)+"x"+operand2+" + "+10*(int)((operand1%100)/10)+"x"+operand2+" + "+operand1%10+"x"+operand2);break;
		case 16: int numberB=Math.min(operand1%100,100-operand1%100);
				int numberC=Math.min(numberB%10,10-numberB%10);
				equationTxt.setText(Html.fromHtml("x<sup><small>2</small></sup> = (x-a)(x+a) + a<sup><small>2</small></sup><br />"
				+"("+operand1+"-"+numberB+")"+" x "+"("+operand1+"+"+numberB+") + "+numberB+"<sup><small>2</small></sup><br />"
				+"("+(operand1-numberB)+"x"+(operand1+numberB)+") + ("+numberB+"-"+numberC+")"+" x "+"("+numberB+"+"+numberC+") + "+numberC+"<sup><small>2</small></sup><br />"
				+"("+(operand1-numberB)+"x"+(operand1+numberB)+") + ("+(numberB-numberC)+"x"+(numberB+numberC)+") + "+numberC+"<sup><small>2</small></sup>"));
				equationTxt.setLines(4);break;
		case 17: int numberD=Math.min(operand1%100,100-operand1%100);
				equationTxt.setText(Html.fromHtml("x<sup><small>2</small></sup> = (x-a)(x+a) + a<sup><small>2</small></sup><br />"
				+"("+operand1+"-"+numberD+")"+" x "+"("+operand1+"+"+numberD+") + "+numberD+"<sup><small>2</small></sup><br />"
				+"("+(operand1-numberD)+"x"+(operand1+numberD)+") + "+numberD+"<sup><small>2</small></sup>"));break;
		case 20: int numberE=Math.min(operand1%1000,1000-operand1%1000);
				int numberF=Math.min(numberE%100,100-numberE%100);
				int numberG=Math.min(numberF%10,10-numberF%10);
				equationTxt.setText(Html.fromHtml("x<sup><small>2</small></sup> = (x-a)(x+a) + a<sup><small>2</small></sup><br />"
				+"("+operand1+"-"+numberE+")"+" x "+"("+operand1+"+"+numberE+") + "+numberE+"<sup><small>2</small></sup><br />"
				+"("+(operand1-numberE)+"x"+(operand1+numberE)+") + ("+numberE+"-"+numberF+")"+" x "+"("+numberE+"+"+numberF+") + "+numberF+"<sup><small>2</small></sup><br />"
				+"("+(operand1-numberE)+"x"+(operand1+numberE)+") + ("+(numberE-numberF)+"x"+(numberE+numberF)+") + ("+numberF+"-"+numberG+")"+"x"+"("+numberF+"+"+numberG+") + "+numberG+"<sup><small>2</small></sup><br />"
				+"("+(operand1-numberE)+"x"+(operand1+numberE)+") + ("+(numberE-numberF)+"x"+(numberE+numberF)+") + ("+(numberF-numberG)+"x"+(numberF+numberG)+") + "+numberG+"<sup><small>2</small></sup>"));
				equationTxt.setLines(5);break;
		case 21:int numberJ=Math.min(operand1%1000,1000-operand1%1000);
				int numberK=Math.min(numberJ%100,100-numberJ%100);
				equationTxt.setText(Html.fromHtml("x<sup><small>2</small></sup> = (x-a)(x+a) + a<sup><small>2</small></sup><br />"
				+"("+(operand1-numberJ)+"x"+(operand1+numberJ)+") + ("+numberJ+"-"+numberK+")"+" x "+"("+numberJ+"+"+numberK+") + "+numberK+"<sup><small>2</small></sup><br />"
				+"("+(operand1-numberJ)+"x"+(operand1+numberJ)+") + ("+numberJ+"-"+numberK+")"+" x "+"("+numberJ+"+"+numberK+") + "+numberK+"<sup><small>2</small></sup><br />"
				+"("+(operand1-numberJ)+"x"+(operand1+numberJ)+") + ("+(numberJ-numberK)+"x"+(numberJ+numberK)+") + "+numberK+"<sup><small>2</small></sup>"));break;
		case 22: int numberL=Math.min(operand1%1000,1000-operand1%1000);
				equationTxt.setText(Html.fromHtml("x<sup><small>2</small></sup> = (x-a)(x+a) + a<sup><small>2</small></sup><br />"
				+"("+operand1+"-"+numberL+")"+" x "+"("+operand1+"+"+numberL+") + "+numberL+"<sup><small>2</small></sup><br />"
				+"("+(operand1-numberL)+"x"+(operand1+numberL)+") + "+numberL+"<sup><small>2</small></sup>"));break;
		}
	 
	 equationTxt.append(Html.fromHtml("<big><font color='#93C9C3'>A</font></big>"));
	
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
	            Intent intent = new Intent(this, LevelSelect.class);
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
        	Intent returnIntent = new Intent(this, LevelSelect.class);
			this.startActivity(returnIntent);
			finish();
			return true;
        }
        return super.onKeyDown(keyCode, event);
    }

	//set high score if activity destroyed
	protected void onDestroy(){
		setHighScore();
		
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
		
		for (int ilist=0; ilist < DScoreList.size(); ilist++)
			{JSONObject jsonData = new JSONObject();
			jsonData=JsonUtil.toJSon(DScoreList.get(ilist),savedAUID,savedName,savedEmail,savedBirth,savedGender,savedStudies,savedHand);
			helper.insertValues(jsonData.toString());
			}
        	
		//Cursor cursor = helper.fetchReminder(1);
		//String sendthis = cursor.getString(cursor.getColumnIndex("Data_to_Send")); // id is column name in db
		helper.close();
		DScoreList.clear();
		
		int[] NNN = new int[24];
		NNN=PassLevel.getNcorrect("Ncorrect", MainActivity.THEcontext);
		NNN[level-1]=NNN[level-1]+thisNcorrect;
		PassLevel.setNcorrect("Ncorrect",NNN, MainActivity.THEcontext);
		
		int[] NNI = new int[24];
		NNI=PassLevel.getNcorrect("Nincorrect", MainActivity.THEcontext);
		NNI[level-1]=NNI[level-1]+thisNincorrect;
		PassLevel.setNcorrect("Nincorrect",NNI, MainActivity.THEcontext);
		
		long[] TTC = new long[24];
		TTC=PassLevel.getTheTimes("Timescorrect", MainActivity.THEcontext);
		TTC[level-1]=TTC[level-1]+thisTotTime;
		PassLevel.setTheTimes("Timescorrect",TTC, MainActivity.THEcontext);
		
		
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
