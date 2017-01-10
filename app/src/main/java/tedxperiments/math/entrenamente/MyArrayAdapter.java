package tedxperiments.math.entrenamente;

import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.graphics.Color;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class MyArrayAdapter extends ArrayAdapter<String> {
 Context context;
 String[] values;
 
 public MyArrayAdapter(Context context, String[] values) {
  super(context, R.layout.highlist_layout, values);
  this.context = context;
  this.values = values;
 }
 
 public View getView(int position, View convertView, ViewGroup parent) {
  LayoutInflater inflater = (LayoutInflater) context
    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  View rowView = inflater.inflate(R.layout.highlist_layout, parent, false);
 
 
  TextView textView = (TextView) rowView.findViewById(R.id.textView1);
  ImageView image = (ImageView) rowView.findViewById(R.id.imageView1);
  TextView textViewTime = (TextView) rowView.findViewById(R.id.textView2);
  
  textView.setText(values[position]);
  //if (position==0){textView.setTextColor(Color.parseColor("#60b0c1"));}
  
  int[] ArcStats = new int[150];
  ArcStats=PassLevel.getArcadeStats("Arcade_Stats", MainActivity.THEcontext);
  
  int[] ArcTimes = new int[150];
  ArcTimes=PassLevel.getArcadeStats("Arcade_Times", MainActivity.THEcontext);
  //if (position==0){textViewTime.setText("TIEMPO");textViewTime.setTextColor(Color.parseColor("#60b0c1"));}
  //else {
	  if (ArcTimes[position]>0){
	  String timetext=millisToShortDHMS(ArcTimes[position]);
	  textViewTime.setText(timetext);}
	  //else textViewTime.setText("- - m - - .- - - s");}
  		else textViewTime.setText("");
	  //}
  
  //if (position==0){image.setImageResource(R.drawable.correct0_3);image.setVisibility(View.INVISIBLE);}
  //else
	  if (ArcStats[position]== 0) {
   image.setImageResource(R.drawable.star_0);
   } else if (ArcStats[position]== 1){
   image.setImageResource(R.drawable.star_1);
  }else if (ArcStats[position]== 2){
	   image.setImageResource(R.drawable.star_2);
	  }
  else if (ArcStats[position]== 3){
	   image.setImageResource(R.drawable.star_3);
	  }
  
  return rowView;
  
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
	    if (days == 0) {
	    	if (hours==0)res = String.format("%02dm %02d.%03ds", minutes, seconds,miliseconds);
	    	else res = String.format("%02dh %02dm %02d.%03ds", hours, minutes, seconds,miliseconds);
	    }
	    else {
	      res = String.format("%dd%02d:%02d:%02d.%03d", days, hours, minutes, seconds,miliseconds);
	    }
	    return res;
	  }
 
}