package tedxperiments.math.entrenamente;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatsSelect extends Activity implements OnClickListener {
	
	private LinearLayout sum1,sum2,mul1,mul2,mul3,mul4,sq2,sq3,sq4;
	private TextView sum11, sum12, sum13,
					 sum21, sum22, sum23,
					 mul11, mul12, mul13,
					 mul21, mul22, mul23,
					 mul31, mul32, mul33,
					 mul41, mul42, mul43,
					 sq21,  sq22,  sq23,
					 sq31,  sq32,  sq33,
					 sq41,  sq42,  sq43;
	
	
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
	
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_statsselect);
	   	     
	    sum1 = (LinearLayout)findViewById(R.id.sum1);
	    sum1.setOnClickListener(this);
	    sum2 = (LinearLayout)findViewById(R.id.sum2);
	    sum2.setOnClickListener(this);
	    mul1 = (LinearLayout)findViewById(R.id.mul1);
	    mul1.setOnClickListener(this);
	    mul2 = (LinearLayout)findViewById(R.id.mul2);
	    mul2.setOnClickListener(this);
	    mul3 = (LinearLayout)findViewById(R.id.mul3);
	    mul3.setOnClickListener(this);
	    mul4 = (LinearLayout)findViewById(R.id.mul4);
	    mul4.setOnClickListener(this);
	    sq2 = (LinearLayout)findViewById(R.id.sq2);
	    sq2.setOnClickListener(this);
	    sq3 = (LinearLayout)findViewById(R.id.sq3);
	    sq3.setOnClickListener(this);
	    sq4 = (LinearLayout)findViewById(R.id.sq4);
	    sq4.setOnClickListener(this);
	    
	    sum11= (TextView)findViewById(R.id.textView11);
	    sum12= (TextView)findViewById(R.id.textView12);
	    sum13= (TextView)findViewById(R.id.textView13);
	    sum21= (TextView)findViewById(R.id.textView21);
	    sum22= (TextView)findViewById(R.id.textView22);
	    sum23= (TextView)findViewById(R.id.textView23);
	    mul11= (TextView)findViewById(R.id.textView31);
	    mul12= (TextView)findViewById(R.id.textView32);
	    mul13= (TextView)findViewById(R.id.textView33);
		mul21= (TextView)findViewById(R.id.textView41);
		mul22= (TextView)findViewById(R.id.textView42);
		mul23= (TextView)findViewById(R.id.textView43);
		mul31= (TextView)findViewById(R.id.textView51);
		mul32= (TextView)findViewById(R.id.textView52);
		mul33= (TextView)findViewById(R.id.textView53);
		mul41= (TextView)findViewById(R.id.textView61);
		mul42= (TextView)findViewById(R.id.textView62);
		mul43= (TextView)findViewById(R.id.textView63);
		sq21= (TextView)findViewById(R.id.textView71);
		sq22= (TextView)findViewById(R.id.textView72);
		sq23= (TextView)findViewById(R.id.textView73);
		sq31= (TextView)findViewById(R.id.textView81);
		sq32= (TextView)findViewById(R.id.textView82);
		sq33= (TextView)findViewById(R.id.textView83);
		sq41= (TextView)findViewById(R.id.textView91);
		sq42= (TextView)findViewById(R.id.textView92);
		sq43= (TextView)findViewById(R.id.textView93);


		if(MainActivity.THEcontext == null){
			Intent mainActivity = new Intent(this,MainActivity.class);
			this.startActivity(mainActivity);
			finish();
		}
		else {

			ArrayList<Long> sum1c = PassLevel.getTimesStats("1d+1d", MainActivity.THEcontext);
			ArrayList<Long> sum2c = PassLevel.getTimesStats("2d+2d", MainActivity.THEcontext);
			ArrayList<Long> mul1c = PassLevel.getTimesStats("1dx1d", MainActivity.THEcontext);
			ArrayList<Long> mul2c = PassLevel.getTimesStats("2dx1d", MainActivity.THEcontext);
			ArrayList<Long> mul3c = PassLevel.getTimesStats("3dx1d", MainActivity.THEcontext);
			ArrayList<Long> mul4c = PassLevel.getTimesStats("4dx1d", MainActivity.THEcontext);
			ArrayList<Long> sq2c = PassLevel.getTimesStats("(2d)^2", MainActivity.THEcontext);
			ArrayList<Long> sq3c = PassLevel.getTimesStats("(3d)^2", MainActivity.THEcontext);
			ArrayList<Long> sq4c = PassLevel.getTimesStats("(4d)^2", MainActivity.THEcontext);

			ArrayList<Long> sum1i = PassLevel.getTimesStats("1d+1dinc", MainActivity.THEcontext);
			ArrayList<Long> sum2i = PassLevel.getTimesStats("2d+2dinc", MainActivity.THEcontext);
			ArrayList<Long> mul1i = PassLevel.getTimesStats("1dx1dinc", MainActivity.THEcontext);
			ArrayList<Long> mul2i = PassLevel.getTimesStats("2dx1dinc", MainActivity.THEcontext);
			ArrayList<Long> mul3i = PassLevel.getTimesStats("3dx1dinc", MainActivity.THEcontext);
			ArrayList<Long> mul4i = PassLevel.getTimesStats("4dx1dinc", MainActivity.THEcontext);
			ArrayList<Long> sq2i = PassLevel.getTimesStats("(2d)^2inc", MainActivity.THEcontext);
			ArrayList<Long> sq3i = PassLevel.getTimesStats("(3d)^2inc", MainActivity.THEcontext);
			ArrayList<Long> sq4i = PassLevel.getTimesStats("(4d)^2inc", MainActivity.THEcontext);


			if (sum1c.size() > 1) {
				long ttsum1c = 0;
				for (int i = 0; i < sum1c.size(); i++) {
					ttsum1c = ttsum1c + sum1c.get(i);
				}
				long tavgsum1c = (long) ttsum1c / sum1c.size();

				int porsum1;
				porsum1 = (int) (sum1c.size() * 100 / (sum1c.size() + sum1i.size()));
				String avgtime = millisToShortDHMS(tavgsum1c);
				sum11.setText(Html.fromHtml("1+1"));
				sum12.setText(Html.fromHtml(getString(R.string.correct_stats) + "<b>" + porsum1 + " %"));
				sum13.setText(Html.fromHtml("&#60T&#62 <b>" + avgtime));
				//sum1.setText(Html.fromHtml("<b>" + "1d + 1d" + "<br />" + "<small><small><small><small>"+getString(R.string.correct_stats)+"</small>"+porsum1+" %<br /><small>"+"&#60T&#62 </small>"+avgtime+"<small></small></small></small>" + "</b>"));
			} else {
				sum11.setText(Html.fromHtml("1+1"));
				sum11.setTextColor(Color.parseColor("#FFFFFF"));
				sum12.setText(Html.fromHtml(getString(R.string.no_data_stats)));
				sum12.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
				sum12.setTextColor(Color.parseColor("#FFFFFF"));
				sum13.setText(Html.fromHtml(""));
				sum1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_gray2));
				//sum1.setText(Html.fromHtml("<b>" + "1d + 1d" + "<br />" + "<small><small><small><small>"+getString(R.string.no_data_stats)+"</small></small></small>" + "</b>"));
				sum1.setEnabled(false);
			}

			if (sum2c.size() > 1) {
				long ttsum2c = 0;
				for (int i = 0; i < sum2c.size(); i++) {
					ttsum2c = ttsum2c + sum2c.get(i);
				}
				long tavgsum2c = (long) ttsum2c / sum2c.size();

				int porsum2;
				porsum2 = (int) (sum2c.size() * 100 / (sum2c.size() + sum2i.size()));
				String avgtime = millisToShortDHMS(tavgsum2c);
				sum21.setText(Html.fromHtml("2+2"));
				sum22.setText(Html.fromHtml(getString(R.string.correct_stats) + "<b>" + porsum2 + " %"));
				sum23.setText(Html.fromHtml("&#60T&#62 <b>" + avgtime));
				//sum2.setText(Html.fromHtml("<b>" + "2d + 2d" + "<br />" + "<small><small><small><small>"+getString(R.string.correct_stats)+"</small>"+porsum2+" %<br /><small>"+"&#60T&#62 </small>"+avgtime+"<small></small></small></small>" + "</b>"));
			} else {
				sum21.setText(Html.fromHtml("2+2"));
				sum21.setTextColor(Color.parseColor("#FFFFFF"));
				sum22.setText(Html.fromHtml(getString(R.string.no_data_stats)));
				sum22.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
				sum22.setTextColor(Color.parseColor("#FFFFFF"));
				sum23.setTextColor(Color.parseColor("#FFFFFF"));
				sum23.setText(Html.fromHtml(""));
				sum2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_gray2));
				//sum2.setText(Html.fromHtml("<b>" + "2d + 2d" + "<br />" + "<small><small><small><small>"+getString(R.string.no_data_stats)+"</small></small></small>" + "</b>"));
				sum2.setEnabled(false);
			}

			if (mul1c.size() > 1) {
				long ttmul1c = 0;
				for (int i = 0; i < mul1c.size(); i++) {
					ttmul1c = ttmul1c + mul1c.get(i);
				}
				long tavgmul1c = (long) ttmul1c / mul1c.size();

				int pormul1;
				pormul1 = (int) (mul1c.size() * 100 / (mul1c.size() + mul1i.size()));
				String avgtime = millisToShortDHMS(tavgmul1c);
				mul11.setText(Html.fromHtml("1x1"));
				mul12.setText(Html.fromHtml(getString(R.string.correct_stats) + "<b>" + pormul1 + " %"));
				mul13.setText(Html.fromHtml("&#60T&#62 <b>" + avgtime));
				//mul1.setText(Html.fromHtml("<b>" + "1d x 1d" + "<br />" + "<small><small><small><small>"+getString(R.string.correct_stats)+"</small>"+pormul1+" %<br /><small>"+"&#60T&#62 </small>"+avgtime+"<small></small></small></small>" + "</b>"));
			} else {
				mul11.setText(Html.fromHtml("1x1"));
				mul11.setTextColor(Color.parseColor("#FFFFFF"));
				mul12.setText(Html.fromHtml(getString(R.string.no_data_stats)));
				mul12.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
				mul12.setTextColor(Color.parseColor("#FFFFFF"));
				mul13.setText(Html.fromHtml(""));
				mul1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_gray2));
				//mul1.setText(Html.fromHtml("<b>" + "1d x 1d" + "<br />" + "<small><small><small><small>"+getString(R.string.no_data_stats)+"</small></small></small>" + "</b>"));
				mul1.setEnabled(false);
			}

			if (mul2c.size() > 1) {
				long ttmul2c = 0;
				for (int i = 0; i < mul2c.size(); i++) {
					ttmul2c = ttmul2c + mul2c.get(i);
				}
				long tavgmul2c = (long) ttmul2c / mul2c.size();

				int pormul2;
				pormul2 = (int) (mul2c.size() * 100 / (mul2c.size() + mul2i.size()));
				String avgtime = millisToShortDHMS(tavgmul2c);
				mul21.setText(Html.fromHtml("2x1"));
				mul22.setText(Html.fromHtml(getString(R.string.correct_stats) + "<b>" + pormul2 + " %"));
				mul23.setText(Html.fromHtml("&#60T&#62 <b>" + avgtime));
				// mul2.setText(Html.fromHtml("<b>" + "2d x 1d" + "<br />" + "<small><small><small><small>"+getString(R.string.correct_stats)+"</small>"+pormul2+" %<br /><small>"+"&#60T&#62 </small>"+avgtime+"<small></small></small></small>" + "</b>"));
			} else {
				mul21.setText(Html.fromHtml("2x1"));
				mul21.setTextColor(Color.parseColor("#FFFFFF"));
				mul22.setText(Html.fromHtml(getString(R.string.no_data_stats)));
				mul22.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
				mul22.setTextColor(Color.parseColor("#FFFFFF"));
				mul23.setText(Html.fromHtml(""));
				mul2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_gray2));
				//mul2.setText(Html.fromHtml("<b>" + "2d x 1d" + "<br />" + "<small><small><small><small>"+getString(R.string.no_data_stats)+"</small></small></small>" + "</b>"));
				mul2.setEnabled(false);
			}

			if (mul3c.size() > 1) {
				long ttmul3c = 0;
				for (int i = 0; i < mul3c.size(); i++) {
					ttmul3c = ttmul3c + mul3c.get(i);
				}
				long tavgmul3c = (long) ttmul3c / mul3c.size();

				int pormul3;
				pormul3 = (int) (mul3c.size() * 100 / (mul3c.size() + mul3i.size()));
				String avgtime = millisToShortDHMS(tavgmul3c);
				mul31.setText(Html.fromHtml("3x1"));
				mul32.setText(Html.fromHtml(getString(R.string.correct_stats) + "<b>" + pormul3 + " %"));
				mul33.setText(Html.fromHtml("&#60T&#62 <b>" + avgtime));
				// mul3.setText(Html.fromHtml("<b>" + "3d x 1d" + "<br />" + "<small><small><small><small>"+getString(R.string.correct_stats)+"</small>"+pormul3+" %<br /><small>"+"&#60T&#62 </small>"+avgtime+"<small></small></small></small>" + "</b>"));
			} else {
				mul31.setText(Html.fromHtml("3x1"));
				mul31.setTextColor(Color.parseColor("#FFFFFF"));
				mul32.setText(Html.fromHtml(getString(R.string.no_data_stats)));
				mul32.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
				mul32.setTextColor(Color.parseColor("#FFFFFF"));
				mul33.setText(Html.fromHtml(""));
				mul3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_gray2));
				//mul3.setText(Html.fromHtml("<b>" + "3d x 1d" + "<br />" + "<small><small><small><small>"+getString(R.string.no_data_stats)+"</small></small></small>" + "</b>"));
				mul3.setEnabled(false);
			}

			if (mul4c.size() > 1) {
				long ttmul4c = 0;
				for (int i = 0; i < mul4c.size(); i++) {
					ttmul4c = ttmul4c + mul4c.get(i);
				}
				long tavgmul4c = (long) ttmul4c / mul4c.size();

				int pormul4;
				pormul4 = (int) (mul4c.size() * 100 / (mul4c.size() + mul4i.size()));
				String avgtime = millisToShortDHMS(tavgmul4c);
				mul41.setText(Html.fromHtml("4x1"));
				mul42.setText(Html.fromHtml(getString(R.string.correct_stats) + "<b>" + pormul4 + " %"));
				mul43.setText(Html.fromHtml("&#60T&#62 <b>" + avgtime));
				//mul4.setText(Html.fromHtml("<b>" + "4d x 1d" + "<br />" + "<small><small><small><small>"+getString(R.string.correct_stats)+"</small>"+pormul4+" %<br /><small>"+"&#60T&#62 </small>"+avgtime+"<small></small></small></small>" + "</b>"));
			} else {
				mul41.setText(Html.fromHtml("4x1"));
				mul41.setTextColor(Color.parseColor("#FFFFFF"));
				mul42.setText(Html.fromHtml(getString(R.string.no_data_stats)));
				mul42.setTextColor(Color.parseColor("#FFFFFF"));
				mul42.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
				mul43.setText(Html.fromHtml(""));
				mul4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_gray2));
				//mul4.setText(Html.fromHtml("<b>" + "4d x 1d" + "<br />" + "<small><small><small><small>"+getString(R.string.no_data_stats)+"</small></small></small>" + "</b>"));
				mul4.setEnabled(false);
			}

			if (sq2c.size() > 1) {
				long ttsq2c = 0;
				for (int i = 0; i < sq2c.size(); i++) {
					ttsq2c = ttsq2c + sq2c.get(i);
				}
				long tavgsq2c = (long) ttsq2c / sq2c.size();

				int porsq2;
				porsq2 = (int) (sq2c.size() * 100 / (sq2c.size() + sq2i.size()));
				String avgtime = millisToShortDHMS(tavgsq2c);
				sq21.setText(Html.fromHtml("2" + "<small><small><sup>" + "2" + "</small></small></sup>"));
				sq22.setText(Html.fromHtml(getString(R.string.correct_stats) + "<b>" + porsq2 + " %"));
				sq23.setText(Html.fromHtml("&#60T&#62 <b>" + avgtime));
				//sq2.setText(Html.fromHtml("<b>" + "(2d)"+"<small><small><sup>"+"2"+"</small></small></sup>" + "<br />" + "<small><small><small><small>"+getString(R.string.correct_stats)+"</small>"+porsq2+" %<br /><small>"+"&#60T&#62 </small>"+avgtime+"<small></small></small></small>" + "</b>"));
			} else {
				sq21.setText(Html.fromHtml("2" + "<small><small><sup>" + "2" + "</small></small></sup>"));
				sq21.setTextColor(Color.parseColor("#FFFFFF"));
				sq22.setText(Html.fromHtml(getString(R.string.no_data_stats)));
				sq22.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
				sq22.setTextColor(Color.parseColor("#FFFFFF"));
				sq23.setText(Html.fromHtml(""));
				sq2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_gray2));
				//sq2.setText(Html.fromHtml("<b>" + "(2d)"+"<small><small><sup>"+"2"+"</small></small></sup>" + "<br />" + "<small><small><small><small>"+getString(R.string.no_data_stats)+"</small></small></small>" + "</b>"));
				sq2.setEnabled(false);
			}

			if (sq3c.size() > 1) {
				long ttsq3c = 0;
				for (int i = 0; i < sq3c.size(); i++) {
					ttsq3c = ttsq3c + sq3c.get(i);
				}
				long tavgsq3c = (long) ttsq3c / sq3c.size();

				int porsq3;
				porsq3 = (int) (sq3c.size() * 100 / (sq3c.size() + sq3i.size()));
				String avgtime = millisToShortDHMS(tavgsq3c);
				sq31.setText(Html.fromHtml("3" + "<small><small><sup>" + "2" + "</small></small></sup>"));
				sq32.setText(Html.fromHtml(getString(R.string.correct_stats) + "<b>" + porsq3 + " %"));
				sq33.setText(Html.fromHtml("&#60T&#62 <b>" + avgtime));
				//sq3.setText(Html.fromHtml("<b>" + "(3d)"+"<small><small><sup>"+"2"+"</small></small></sup>" + "<br />" + "<small><small><small><small>"+getString(R.string.correct_stats)+"</small>"+porsq3+" %<br /><small>"+"&#60T&#62 </small>"+avgtime+"<small></small></small></small>" + "</b>"));
			} else {
				sq31.setText(Html.fromHtml("3" + "<small><small><sup>" + "2" + "</small></small></sup>"));
				sq31.setTextColor(Color.parseColor("#FFFFFF"));
				sq32.setText(Html.fromHtml(getString(R.string.no_data_stats)));
				sq32.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
				sq32.setTextColor(Color.parseColor("#FFFFFF"));
				sq33.setText(Html.fromHtml(""));
				sq3.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_gray2));
				//sq3.setText(Html.fromHtml("<b>" + "(3d)"+"<small><small><sup>"+"2"+"</small></small></sup>" + "<br />" + "<small><small><small><small>"+getString(R.string.no_data_stats)+"</small></small></small>" + "</b>"));
				sq3.setEnabled(false);
			}

			if (sq4c.size() > 1) {
				long ttsq4c = 0;
				for (int i = 0; i < sq4c.size(); i++) {
					ttsq4c = ttsq4c + sq4c.get(i);
				}
				long tavgsq4c = (long) ttsq4c / sq4c.size();

				int porsq4;
				porsq4 = (int) (sq4c.size() * 100 / (sq4c.size() + sq4i.size()));
				String avgtime = millisToShortDHMS(tavgsq4c);
				sq41.setText(Html.fromHtml("4" + "<small><small><sup>" + "2" + "</small></small></sup>"));
				sq42.setText(Html.fromHtml(getString(R.string.correct_stats) + "<b>" + porsq4 + " %"));
				sq43.setText(Html.fromHtml("&#60T&#62 <b>" + avgtime));
				//sq4.setText(Html.fromHtml("<b>" + "(4d)"+"<small><small><sup>"+"2"+"</small></small></sup>" + "<br />" + "<small><small><small><small>"+getString(R.string.correct_stats)+"</small>"+porsq4+" %<br /><small>"+"&#60T&#62 </small>"+avgtime+"<small></small></small></small>" + "</b>"));
			} else {
				sq41.setText(Html.fromHtml("4" + "<small><small><sup>" + "2" + "</small></small></sup>"));
				sq41.setTextColor(Color.parseColor("#FFFFFF"));
				sq42.setText(Html.fromHtml(getString(R.string.no_data_stats)));
				sq42.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
				sq42.setTextColor(Color.parseColor("#FFFFFF"));
				sq43.setText(Html.fromHtml(""));
				sq4.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_gray2));
				//sq4.setText(Html.fromHtml("<b>" + "(4d)"+"<small><small><sup>"+"2"+"</small></small></sup>" + "<br />" + "<small><small><small><small>"+getString(R.string.no_data_stats)+"</small></small></small>" + "</b>"));
				sq4.setEnabled(false);
			}

			//sum1.setText(Html.fromHtml("<b>1d + 1d</b>"));
			//sum2.setText(Html.fromHtml("<b>2d + 2d</b>"));
			//mul1.setText(Html.fromHtml("<b>1d x 1d</b>"));
			//mul2.setText(Html.fromHtml("<b>2d x 1d</b>"));
			//mul3.setText(Html.fromHtml("<b>3d x 1d</b>"));
			//mul4.setText(Html.fromHtml("<b>4d x 1d</b>"));
			//sq2.setText(Html.fromHtml("<b>(2d)"+"<small><small><sup>"+"2"+"</small></small></sup></b>"));
			//sq3.setText(Html.fromHtml("<b>(3d)"+"<small><small><sup>"+"2"+"</small></small></sup></b>"));
			//sq4.setText(Html.fromHtml("<b>(4d)"+"<small><small><sup>"+"2"+"</small></small></sup></b>"));

		}
	    
	    ActionBar AB = getActionBar();
	  		AB.setDisplayHomeAsUpEnabled(true);
	  		AB.setTitle(Html.fromHtml("<font color='#ED1566'>"+getString(R.string.stats_navbar)+"</font>"));
	  		//AB.setSubtitle("Seleccione un tipo de cuenta");	    
	  		AB.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F0F1F2")));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.sum1){
			Intent timeIntent = new Intent(this, TimesGraph.class);
			timeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			timeIntent.putExtra("type","1d+1d");
			this.startActivity(timeIntent);
			finish();
		}
		else if(v.getId()==R.id.sum2){
			Intent timeIntent = new Intent(this, TimesGraph.class);
			timeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			timeIntent.putExtra("type","2d+2d");
			this.startActivity(timeIntent);
			finish();
		}
		else if(v.getId()==R.id.mul1){
			Intent timeIntent = new Intent(this, TimesGraph.class);
			timeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			timeIntent.putExtra("type","1dx1d");
			this.startActivity(timeIntent);
			finish();
		}
		else if(v.getId()==R.id.mul2){
			Intent timeIntent = new Intent(this, TimesGraph.class);
			timeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			timeIntent.putExtra("type","2dx1d");
			this.startActivity(timeIntent);
			finish();
		}
		else if(v.getId()==R.id.mul3){
			Intent timeIntent = new Intent(this, TimesGraph.class);
			timeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			timeIntent.putExtra("type","3dx1d");
			this.startActivity(timeIntent);
			finish();
		}
		else if(v.getId()==R.id.mul4){
			Intent timeIntent = new Intent(this, TimesGraph.class);
			timeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			timeIntent.putExtra("type","4dx1d");
			this.startActivity(timeIntent);
			finish();
		}
		else if(v.getId()==R.id.sq2){
			Intent timeIntent = new Intent(this, TimesGraph.class);
			timeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			timeIntent.putExtra("type","(2d)^2");
			this.startActivity(timeIntent);
			finish();
		}
		else if(v.getId()==R.id.sq3){
			Intent timeIntent = new Intent(this, TimesGraph.class);
			timeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			timeIntent.putExtra("type","(3d)^2");
			this.startActivity(timeIntent);
			finish();
		}
		else if(v.getId()==R.id.sq4){
			Intent timeIntent = new Intent(this, TimesGraph.class);
			timeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			timeIntent.putExtra("type","(4d)^2");
			this.startActivity(timeIntent);
			finish();
		}
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
		    	if (hours==0)
		    		{if (minutes==0) res = String.format("%02d.%03ds", seconds,miliseconds);
		    		else res = String.format("%02dm %02d.%03ds", minutes, seconds,miliseconds); 
		    		}
		    	else res = String.format("%02dh %02dm %02d.%03ds", hours, minutes, seconds,miliseconds);
		    }
		    else {
		      res = String.format("%dd%02d:%02d:%02d.%03d", days, hours, minutes, seconds,miliseconds);
		    }
		    return res;
		  }
	 
	}
		
	


