package tedxperiments.math.entrenamente;

import tedxperiments.math.entrenamente.HighScores.OnSwipeTouchListener;
import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;
 
 
public class HighList extends ListActivity{
     
 String [] values;

  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar AB = getActionBar();
		AB.setDisplayHomeAsUpEnabled(true);
		AB.setTitle(Html.fromHtml("<font color='#ED1566'>Moravec</font>"));
		//AB.setSubtitle("Modo Arcade");
		AB.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F0F1F2")));
		
		 
		
		String[] prevalues= new String [150];
        //values = new String [150];
        int[] ArcStats = new int[150];
        ArcStats=PassLevel.getArcadeStats("Arcade_Stats", MainActivity.THEcontext);
        int k=0;
        for (int i=0; i<150; i++)
    	{if (ArcStats[i]>0)k++;;
    	}
        
        if(k<150) values = new String [k+1];
        else if(k==150)values = new String [k];
        
        //values[0]="NIVEL";
        for (int i=0; i<values.length; i++)
        	{
        	//if (ArcStats[i]>0)
        		values[i]=getString(R.string.level_selection)+(i+1);
        	}
        
      setListAdapter(new MyArrayAdapter(getApplicationContext(), values));
        ListView lv = getListView(); 
        lv.setBackgroundColor(Color.parseColor("#F0F1F2"));
        lv.setDivider(null);
        lv.setDividerHeight(0);
        
       //lv.setAdapter(new MyArrayAdapter(getApplicationContext(), values));  
          
       //lv.setTextFilterEnabled(true);
       //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
       //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        
       
        lv.post( new Runnable() {
            @Override
            public void run() {
            //call smooth scroll
            	  int listsize=getListView().getCount();
                  getListView().smoothScrollToPosition(listsize);
            }
          }); 
      
        
       
       lv.setOnTouchListener(new OnSwipeTouchListener(getBaseContext()) {
    	    @Override
   		    public void onSwipeLeft() {
   		        // Whatever
   		    	//StartotraL();
   		    	
   		    }
   		    @Override
   		    public void onSwipeRight() {
   		        // Whatever
   		    	//StartotraR();
   		    	 }

   			
   		});

			
		        
        
    }
    
    
 
 @Override
 protected void onListItemClick(ListView l, View v, int position, long id) {
  // TODO Auto-generated method stub
  //Toast.makeText(getApplicationContext(), values[position],  Toast.LENGTH_SHORT).show();
	 if(position>-1)
	 {Intent arcIntent = new Intent(this, PlayArcade.class);
	 arcIntent.putExtra("level",position+1); 
	 this.startActivity(arcIntent);
	 finish();
	 }
  super.onListItemClick(l, v, position, id);
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
		Intent HIntent = new Intent(this, HighScores.class);
		//Intent highIntent = new Intent(this, PersonalQuestions.class);
		this.startActivity(HIntent);
		finish();
		
	}
	private void StartotraR() {
		// TODO Auto-generated method stub
		Intent HIntent = new Intent(this, MainActivity.class);
		//Intent highIntent = new Intent(this, PersonalQuestions.class);
		this.startActivity(HIntent);
		finish();
		
	}

 

}