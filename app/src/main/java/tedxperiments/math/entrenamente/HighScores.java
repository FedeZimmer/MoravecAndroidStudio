package tedxperiments.math.entrenamente;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
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

public class HighScores extends Activity {
	private TextView thistitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_high);
		setContentView(R.layout.activity_highchart);
		thistitle = (TextView)findViewById(R.id.thetitle);
		thistitle.setText("Porcentaje de aciertos por nivel");
		
		
		
		ActionBar AB = getActionBar();
		AB.setDisplayHomeAsUpEnabled(true);
		AB.setTitle(Html.fromHtml("<font color='#60b0c1'>Entrenamente </font>"));
		AB.setSubtitle("Estadísticas (Continúa --->)");
		
				
		// init example series data
		
		int[] NNC = new int[25];
		NNC=PassLevel.getNcorrect("Ncorrect", MainActivity.THEcontext);
		int[] NNI = new int[25];
		NNI=PassLevel.getNcorrect("Nincorrect", MainActivity.THEcontext);
		
		GraphViewData[] data = new GraphViewData[25];
		for (int i=0; i<25; i++) {
			int roundpercentage;
			if (NNC[i]+NNI[i]>0)
				roundpercentage= (int)(NNC[i]*100)/(NNC[i]+NNI[i]);
			else roundpercentage=0; 
			data[i] = new GraphViewData(i, roundpercentage);
			};
		GraphViewSeries exampleSeries = new GraphViewSeries(data);
		exampleSeries.getStyle().color=(Color.parseColor("#e15656"));
		//GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
		//	new GraphViewData(1, 10)
		//  , new GraphViewData(2, 60)
		//    , new GraphViewData(3, 14)
		//    , new GraphViewData(4, 28)
		//});
		 
		GraphView graphView = new BarGraphView(
		    this // context
		    , "Porcentaje de aciertos por nivel" // heading
		);
		graphView.addSeries(exampleSeries); // data
		graphView.setTitle("");
		//graphView.setHorizontalLabels(new String[] {"1", "2", "3", "4","5", "6", "7", "8","9", "10", "11", "12","13", "14", "15", "16", "17", "18", "19"});
		graphView.setHorizontalLabels(new String[] {"1", "", "3", "","5", "", "7", "","9", "", "11", "","13", "", "15", "", "17", "", "19", "", "21", "", "23", "", "25"});
		graphView.setVerticalLabels(new String[] {"100", "75", "50", "25","0"});
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.GRAY);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.GRAY);
		graphView.setManualYMinBound(0);
		graphView.setManualYMaxBound(100);
		graphView.setBackgroundColor(Color.parseColor("#131d24"));
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.layoutchart);
		
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
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; return!
	            Intent intent = new Intent(this, MainActivity.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
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
		Intent HIntent = new Intent(this, HighScores2.class);
		//Intent highIntent = new Intent(this, PersonalQuestions.class);
		this.startActivity(HIntent);
		finish();
		
	}
	private void StartotraR() {
		// TODO Auto-generated method stub
		Intent HIntent = new Intent(this, HighList.class);
		//Intent highIntent = new Intent(this, PersonalQuestions.class);
		this.startActivity(HIntent);
		finish();
		
	}
	
	
}

