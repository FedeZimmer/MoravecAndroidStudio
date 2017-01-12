package tedxperiments.math.entrenamente;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.graphics.Paint;

public class TimesGraph extends Activity {
	private TextView thistitle;
	private String typeselected;
	private long maxY;
	private static int caseY;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_high);
		setContentView(R.layout.activity_highchart);
		thistitle = (TextView)findViewById(R.id.thetitle);
		thistitle.setText("Tiempos de respuesta");
		
		
		
		ActionBar AB = getActionBar();
		AB.setDisplayHomeAsUpEnabled(true);
		AB.setTitle(Html.fromHtml("<font color='#ED1566'>"+getString(R.string.graph_navbar)+"</font>"));
		//AB.setSubtitle("Tiempo de respuesta");
		AB.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F0F1F2")));
		
				
		// init example series data
		//int[] NNC = new int[25];
		//NNC=PassLevel.getNcorrect("Ncorrect", MainActivity.THEcontext);
		//int[] NNI = new int[25];
		//NNI=PassLevel.getNcorrect("Nincorrect", MainActivity.THEcontext);
		//ArrayList<Long> answerprueba = new ArrayList<Long>();
		//answerprueba.add((long) 987465535);
		//answerprueba.add((long) 987463453);
		//answerprueba.add((long) 987463253);
		//answerprueba.add((long) 987412353);
		
		//PassLevel.setTimesStats("prueba", answerprueba, MainActivity.THEcontext);
		
		Bundle extras = getIntent().getExtras();
		typeselected="1d+1d";
		if(extras !=null)
		{typeselected = extras.getString("type"); 
		}
		ArrayList<Long> answertimes = PassLevel.getTimesStats(typeselected, MainActivity.THEcontext);
		String usetitle="";
		if (typeselected.equals("1d+1d"))usetitle="1+1";
		if (typeselected.equals("2d+2d"))usetitle="2+2";
		if (typeselected.equals("1dx1d"))usetitle="1x1";
		if (typeselected.equals("2dx1d"))usetitle="2x1";
		if (typeselected.equals("3dx1d"))usetitle="3x1";
		if (typeselected.equals("4dx1d"))usetitle="4x1";
		if (typeselected.equals("(2d)^2"))usetitle="2"+"<small><small><sup>"+"2"+"</small></small></sup>";
		if (typeselected.equals("(3d)^2"))usetitle="3"+"<small><small><sup>"+"2"+"</small></small></sup>";
		if (typeselected.equals("(4d)^2"))usetitle="4"+"<small><small><sup>"+"2"+"</small></small></sup>";
		
		
		thistitle.setText(Html.fromHtml("<big>"+usetitle+"</big>"));
		
		ArrayList<Long> tempanswertime = (ArrayList<Long>) answertimes.clone();
		//Promediador movil triangular N=3
		if (answertimes.size()>4){
		//ArrayList<Long> tempanswertime = (ArrayList<Long>) answertimes.clone();
		answertimes.clear();
		for (int i=1; i<tempanswertime.size()-1; i++) {
			answertimes.add(i-1,(tempanswertime.get(i)+tempanswertime.get(i-1)/2+tempanswertime.get(i+1)/2)/2);
		}
		tempanswertime.clear();
		}
		if (answertimes.size()>1){
		
		GraphViewData[] data = new GraphViewData[answertimes.size()];
		for (int i=0; i<answertimes.size(); i++) {
			data[i] = new GraphViewData(i, answertimes.get(i));
			};
		GraphViewSeries exampleSeries = new GraphViewSeries(data);
		exampleSeries.getStyle().color=(Color.parseColor("#0b9E8C"));
		//GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
		//	new GraphViewData(1, 10)
		//  , new GraphViewData(2, 60)
		//    , new GraphViewData(3, 14)
		//    , new GraphViewData(4, 28)
		//});
		 
		LineGraphView graphView = new LineGraphView(
		    this // context
		    , typeselected // heading
		);
		
		graphView.addSeries(exampleSeries); // data
		graphView.setTitle("");
		graphView.setDrawDataPoints(true);
		graphView.setDataPointsRadius(4f);
		
		
		String[] YaxisStr;
		if(Collections.max(answertimes)<100000)
		{
		YaxisStr = getYaxis(Collections.max(answertimes));
		}
		else{
		
			YaxisStr = new String [11];
			Long[] Yaxis = new Long [11];
			long maxvalue = Collections.max(answertimes);
			for (int i=0; i<11; i++)
				{Yaxis[i]= i*maxvalue/10;} 
			caseY=3;
			for (int i=0; i<11; i++)
				{YaxisStr[10-i]= millisToShortDHMS(Yaxis[i]);}
		}
		
		//graphView.setHorizontalLabels(new String[] {"1", "2", "3", "4","5", "6", "7", "8","9", "10", "11", "12","13", "14", "15", "16", "17", "18", "19"});
		graphView.setVerticalLabels(YaxisStr);
		String strsize =String.valueOf(answertimes.size());
		graphView.setHorizontalLabels(new String[] {"1", strsize});
		
		
		//graphView.setVerticalLabels(new String[] {"100", "75", "50", "25","0"});
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.parseColor("#3E3E3E"));
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.parseColor("#3E3E3E"));
		graphView.getGraphViewStyle().setVerticalLabelsAlign(Paint.Align.CENTER);
		graphView.setManualYMinBound(0);
		
		if(Collections.max(answertimes)<100000){graphView.setManualYMaxBound(maxY);}
				
		graphView.setBackgroundColor(Color.parseColor("#F0F1F2"));
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.layoutchart);
		TextView newtext = (TextView)findViewById(R.id.subtitle);;
		
		layout.addView(graphView);
		setContentView(layout);
		
		
		
		graphView.setOnTouchListener(new OnSwipeTouchListener(getBaseContext()) {
		    @Override
		    public void onSwipeLeft() {
		        // Whatever
		    	StartotraL();
		    	
		    }
		    @Override
		    public void onSwipeRight() {
		        // Whatever
		    	StartotraR();
		    	 }

			
		});
		
		}
		
		
		else  {thistitle.setText("Juegue más niveles para ver sus estadísticas");}
		//get text view
		//TextView scoreView = (TextView)findViewById(R.id.high_scores_list);
		//get shared prefs
		//SharedPreferences scorePrefs = getSharedPreferences(PlayGame.GAME_PREFS, 0);
		//get scores
		//String[] savedScores = scorePrefs.getString("highScores", "").split("\\|");
		//build string
		//StringBuilder scoreBuild = new StringBuilder("");
		//for(String score : savedScores){
		//			scoreBuild.append(score+"\n");
		//}
		//display scores
//		scoreView.setText(scoreBuild.toString());
	}
	
	private String[] getYaxis(Long max) {
		// TODO Auto-generated method stub
		ArrayList<Long> Yaxislong = new ArrayList();
		long k;
		if(max<5000){k=500; caseY=1;}
		else if(max<10000){k=1000;caseY=2;}
		else if(max<20000){k=2000;caseY=2;}
		else if(max<50000){k=5000;caseY=2;}
		else{k=10000;caseY=3;}
		
		for (long i=0; i<max+k; i=i+k)
		{Yaxislong.add(i);}
		
		String[] YaxisStr = new String [Yaxislong.size()];
		for (int i=0; i< Yaxislong.size(); i++)
				{YaxisStr[Yaxislong.size()-1-i]= millisToShortDHMS(Yaxislong.get(i));}
		
		maxY=Yaxislong.get(Yaxislong.size()-1);
		
		return YaxisStr;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; return!
	            Intent intent = new Intent(this, StatsSelect.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public class GraphViewData implements GraphViewDataInterface {
	    private double x,y;

	    public GraphViewData(double x, double y) {
	        this.x = x;
	        this.y = y;
	    }

	    @Override
	    public double getX() {
	        return this.x;
	    }

	    @Override
	    public double getY() {
	        return this.y;
	    }
	}
	
	public class OnSwipeTouchListener implements OnTouchListener {

	    private final GestureDetector gestureDetector;

	    public OnSwipeTouchListener(Context context) {
	        gestureDetector = new GestureDetector(context, new GestureListener());
	    }

	    public void onSwipeLeft() {
	    }

	    public void onSwipeRight() {
	    }

	    public boolean onTouch(View v, MotionEvent event) {
	        return gestureDetector.onTouchEvent(event);
	    }

	    private final class GestureListener extends SimpleOnGestureListener {

	        private static final int SWIPE_DISTANCE_THRESHOLD = 100;
	        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

	        @Override
	        public boolean onDown(MotionEvent e) {
	            return true;
	        }

	        @Override
	        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	            float distanceX = e2.getX() - e1.getX();
	            float distanceY = e2.getY() - e1.getY();
	            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
	                if (distanceX > 0)
	                    onSwipeRight();
	                else
	                    onSwipeLeft();
	                return true;
	            }
	            return false;
	        }
	    }
	}
	
	private void StartotraL() {
		// TODO Auto-generated method stub
		
		
		if (typeselected.equals("1d+1d")){
			ArrayList<Long> sum2c = PassLevel.getTimesStats("2d+2d", MainActivity.THEcontext);
			if (sum2c.size()>1)
			{Intent time2Intent = new Intent(this, TimesGraph.class);
			time2Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			time2Intent.putExtra("type","2d+2d");
			this.startActivity(time2Intent);
			finish();
			}}
		else if (typeselected.equals("2d+2d")){
			ArrayList<Long> mul1c = PassLevel.getTimesStats("1dx1d", MainActivity.THEcontext);
			if (mul1c.size()>1)
			{Intent time2Intent = new Intent(this, TimesGraph.class);
			time2Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			time2Intent.putExtra("type","1dx1d");
			this.startActivity(time2Intent);
			finish();
			}}
		else if (typeselected.equals("1dx1d")){
			ArrayList<Long> mul2c = PassLevel.getTimesStats("2dx1d", MainActivity.THEcontext);
			if (mul2c.size()>1)
			{Intent time2Intent = new Intent(this, TimesGraph.class);
			time2Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			time2Intent.putExtra("type","2dx1d");
			this.startActivity(time2Intent);
			finish();
			}}
		else if (typeselected.equals("2dx1d")){
			ArrayList<Long> mul3c = PassLevel.getTimesStats("3dx1d", MainActivity.THEcontext);
			if (mul3c.size()>1)
			{Intent time2Intent = new Intent(this, TimesGraph.class);
			time2Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			time2Intent.putExtra("type","3dx1d");
			this.startActivity(time2Intent);
			finish();
			}}
		else if (typeselected.equals("3dx1d")){
			ArrayList<Long> mul4c = PassLevel.getTimesStats("4dx1d", MainActivity.THEcontext);
			if (mul4c.size()>1)
			{Intent time2Intent = new Intent(this, TimesGraph.class);
			time2Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			time2Intent.putExtra("type","4dx1d");
			this.startActivity(time2Intent);
			finish();
			}}
		else if (typeselected.equals("4dx1d")){
			ArrayList<Long> sq2c = PassLevel.getTimesStats("(2d)^2", MainActivity.THEcontext);
			if (sq2c.size()>1)
			{Intent time2Intent = new Intent(this, TimesGraph.class);
			time2Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			time2Intent.putExtra("type","(2d)^2");
			this.startActivity(time2Intent);
			finish();
			}}
		else if (typeselected.equals("(2d)^2")){
			ArrayList<Long> sq3c = PassLevel.getTimesStats("(3d)^2", MainActivity.THEcontext);
			if (sq3c.size()>1)
			{Intent time2Intent = new Intent(this, TimesGraph.class);
			time2Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			time2Intent.putExtra("type","(3d)^2");
			this.startActivity(time2Intent);
			finish();
			}}
		else if (typeselected.equals("(3d)^2")){
			ArrayList<Long> sq4c = PassLevel.getTimesStats("(4d)^2", MainActivity.THEcontext);
			if (sq4c.size()>1)
			{Intent time2Intent = new Intent(this, TimesGraph.class);
			time2Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			time2Intent.putExtra("type","(4d)^2");
			this.startActivity(time2Intent);
			finish();
			}}
		else if (typeselected.equals("(4d)^2")){
			ArrayList<Long> sum1c = PassLevel.getTimesStats("1d+1d", MainActivity.THEcontext);
			if (sum1c.size()>1)
			{Intent time2Intent = new Intent(this, TimesGraph.class);
			time2Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			time2Intent.putExtra("type","1d+1d");
			this.startActivity(time2Intent);
			finish();
			}}	
			
	}
	
	private void StartotraR() {
		// TODO Auto-generated method stub
		if (typeselected.equals("1d+1d")){
			ArrayList<Long> sq4c = PassLevel.getTimesStats("(4d)^2", MainActivity.THEcontext);
			if (sq4c.size()>1)
			{Intent time2Intent = new Intent(this, TimesGraph.class);
			time2Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			time2Intent.putExtra("type","(4d)^2");
			this.startActivity(time2Intent);
			finish();
			}}
		
		else if (typeselected.equals("2d+2d")){
			ArrayList<Long> sum1c = PassLevel.getTimesStats("1d+1d", MainActivity.THEcontext);
			if (sum1c.size()>1)
			{Intent time2Intent = new Intent(this, TimesGraph.class);
			time2Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			time2Intent.putExtra("type","1d+1d");
			this.startActivity(time2Intent);
			finish();
			}}	
		
		else if (typeselected.equals("1dx1d")){
			ArrayList<Long> sum2c = PassLevel.getTimesStats("2d+2d", MainActivity.THEcontext);
			if (sum2c.size()>1)
			{Intent time2Intent = new Intent(this, TimesGraph.class);
			time2Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			time2Intent.putExtra("type","2d+2d");
			this.startActivity(time2Intent);
			finish();
			}}
			
		else if (typeselected.equals("2dx1d")){
			ArrayList<Long> mul1c = PassLevel.getTimesStats("1dx1d", MainActivity.THEcontext);
			if (mul1c.size()>1)
			{Intent time2Intent = new Intent(this, TimesGraph.class);
			time2Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			time2Intent.putExtra("type","1dx1d");
			this.startActivity(time2Intent);
			finish();
			}}
			
		else if (typeselected.equals("3dx1d")){
			ArrayList<Long> mul2c = PassLevel.getTimesStats("2dx1d", MainActivity.THEcontext);
			if (mul2c.size()>1)
			{Intent time2Intent = new Intent(this, TimesGraph.class);
			time2Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			time2Intent.putExtra("type","2dx1d");
			this.startActivity(time2Intent);
			finish();
			}}
			
		else if (typeselected.equals("4dx1d")){
			ArrayList<Long> mul3c = PassLevel.getTimesStats("3dx1d", MainActivity.THEcontext);
			if (mul3c.size()>1)
			{Intent time2Intent = new Intent(this, TimesGraph.class);
			time2Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			time2Intent.putExtra("type","3dx1d");
			this.startActivity(time2Intent);
			finish();
			}}
		
		else if (typeselected.equals("(2d)^2")){
			ArrayList<Long> mul4c = PassLevel.getTimesStats("4dx1d", MainActivity.THEcontext);
			if (mul4c.size()>1)
			{Intent time2Intent = new Intent(this, TimesGraph.class);
			time2Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			time2Intent.putExtra("type","4dx1d");
			this.startActivity(time2Intent);
			finish();
			}}
		else if (typeselected.equals("(3d)^2")){
			ArrayList<Long> sq2c = PassLevel.getTimesStats("(2d)^2", MainActivity.THEcontext);
			if (sq2c.size()>1)
			{Intent time2Intent = new Intent(this, TimesGraph.class);
			time2Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			time2Intent.putExtra("type","(2d)^2");
			this.startActivity(time2Intent);
			finish();
			}}
			
		else if (typeselected.equals("(4d)^2")){
			ArrayList<Long> sq3c = PassLevel.getTimesStats("(3d)^2", MainActivity.THEcontext);
			if (sq3c.size()>1)
			{Intent time2Intent = new Intent(this, TimesGraph.class);
			time2Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			time2Intent.putExtra("type","(3d)^2");
			this.startActivity(time2Intent);
			finish();
			}}
					
		
	}
	
	public static String millisToShortDHMS(long duration) {
	    String res = "";
	    long days  = TimeUnit.MILLISECONDS.toDays(duration);
	    long hours = TimeUnit.MILLISECONDS.toHours(duration)
	                   - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
	    long minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
	                     - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
	    long seconds = TimeUnit.MILLISECONDS.toSeconds(duration)
	                   - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));
	    long miliseconds = TimeUnit.MILLISECONDS.toMillis(duration)
                - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(duration));
	    switch(caseY){
	    case 1:res = String.format("%d.%01d",seconds,miliseconds/100); break;
	    case 2:res = String.format("%d",seconds); break;
	    case 3:res = String.format("%dm%02ds", minutes, seconds);break;
	    }
	    return res;
	  }
	 //public static String millisToShortDHMS(long duration) {
	//	    String res = "";
	//	    long days  = TimeUnit.MILLISECONDS.toDays(duration);
	//	    long hours = TimeUnit.MILLISECONDS.toHours(duration)
	//	                   - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
	//	    long minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
	//	                     - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
	//	    long seconds = TimeUnit.MILLISECONDS.toSeconds(duration)
	//	                   - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));
	//	    long miliseconds = TimeUnit.MILLISECONDS.toMillis(duration)
	 //               - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(duration));
	//	    if (days == 0) {
	//	    	if (hours==0)
	//	    		{if (minutes==0) res = String.format("%d.%03d",seconds,miliseconds);
	//	    		else res = String.format("%02dm%02d", minutes, seconds);
	//	    		}
	//	    	else res = String.format("%02dh%02dm%02d.%03ds", hours, minutes, seconds,miliseconds);
	//	    }
	//	    else {
	//	      res = String.format("%dd%02d:%02d:%02d.%03d", days, hours, minutes, seconds,miliseconds);
	//	    }
	//	    return res;
	//	  }
	 
	 @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if ((keyCode == KeyEvent.KEYCODE_BACK)) { //Back key pressed
	           //Things to Do
	        	Intent returnIntent = new Intent(this, StatsSelect.class);
				this.startActivity(returnIntent);
				finish();
				return true;
	        }
	        return super.onKeyDown(keyCode, event);
	    }
	
	
	
}


