package tedxperiments.math.entrenamente;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import tedxperiments.math.entrenamente.R;

public class HowToPlay extends Activity implements OnClickListener {

	private Button sumbut,multbut,squarebut;
	private Button otrobut;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_how);
		sumbut = (Button)findViewById(R.id.add_btn);
	    multbut = (Button)findViewById(R.id.multiplication_btn);
	    squarebut = (Button)findViewById(R.id.square_btn);
	    otrobut= (Button)findViewById(R.id.attention);
	    
	    sumbut.setOnClickListener(this);
	    multbut.setOnClickListener(this);
	    squarebut.setOnClickListener(this);
	    
	    
	    sumbut.setText(Html.fromHtml("<big><big><big>" + "+" + "</big></big></big><br />" + "<small><small><small>"+"Suma"+"</small></small></small>" + ""));
	    multbut.setText(Html.fromHtml("<big><big><big>" + "*" + "</big></big></big><br />" + "<small><small><small>"+"Multiplicación"+"</small></small></small>" + ""));
	    squarebut.setText(Html.fromHtml("<big><big><big>" + "x"+"<small><small><small><sup><sup>"+"2"+"</small></small></small></sup></sup>" + "</big></big></big><br />" + "<small><small><small>"+"Al cuadrado"+"</small></small></small>" + ""));
	    
	    
	    ActionBar AB = getActionBar();
		AB.setDisplayHomeAsUpEnabled(true);
		AB.setTitle(Html.fromHtml("<font color='#ED1566'>Tutorial</font>"));
		//AB.setSubtitle("Tutorial");
		AB.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F0F1F2")));
	    
	}


	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(view.getId()==R.id.add_btn){
			Intent SumIntent = new Intent(this, HowtoSum.class);
			this.startActivity(SumIntent);
			}
			else if(view.getId()==R.id.multiplication_btn){
				Intent MulIntent = new Intent(this, HowtoMul.class);
				this.startActivity(MulIntent);
			}
			else if(view.getId()==R.id.square_btn){
				Intent SqIntent = new Intent(this, HowtoSquare.class);
				this.startActivity(SqIntent);
				//startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://youtu.be/Hp1FDnUK6Yg")));
			    
			}
		
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

}

