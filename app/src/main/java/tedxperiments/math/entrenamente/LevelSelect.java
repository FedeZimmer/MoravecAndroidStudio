package tedxperiments.math.entrenamente;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import tedxperiments.math.entrenamente.R;

public class LevelSelect extends Activity implements OnClickListener{
	
	private Button level1b,level2b,level3b,level4b,level5b,level6b,level7b,level8b,level9b,level10b,level11b,level12b,level13b,level14b,level15b,level16b,level17b,level18b,level19b,level20b,level21b;
	//private int levelcompleted=19;//Le asigno un valor solo para probar
	//Deberia levantarlo de un file
	private int askpersonal;
	
	
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
	    setContentView(R.layout.activity_levels);
	    
	    level1b = (Button)findViewById(R.id.lvl1);
	    level2b = (Button)findViewById(R.id.lvl2);
	    level3b = (Button)findViewById(R.id.lvl3);
	    level4b = (Button)findViewById(R.id.lvl4);
	    level5b = (Button)findViewById(R.id.lvl5);
	    level6b = (Button)findViewById(R.id.lvl6);
	    level7b = (Button)findViewById(R.id.lvl7);
	    level8b = (Button)findViewById(R.id.lvl8);
	    level9b = (Button)findViewById(R.id.lvl9);
	    level10b = (Button)findViewById(R.id.lvl10);
	    level11b = (Button)findViewById(R.id.lvl11);
	    level12b = (Button)findViewById(R.id.lvl12);
	    level13b = (Button)findViewById(R.id.lvl13);
	    level14b = (Button)findViewById(R.id.lvl14);
	    level15b = (Button)findViewById(R.id.lvl15);
	    level16b = (Button)findViewById(R.id.lvl16);
	    level17b = (Button)findViewById(R.id.lvl17);
	    level18b = (Button)findViewById(R.id.lvl18);
	    level19b = (Button)findViewById(R.id.lvl19);
	    level20b = (Button)findViewById(R.id.lvl20);
	    level21b = (Button)findViewById(R.id.lvl21);
	   	 
	    
	    level1b.setOnClickListener(this);
	    level2b.setOnClickListener(this);
	    level3b.setOnClickListener(this);
	    level4b.setOnClickListener(this);
	    level5b.setOnClickListener(this);
	    level6b.setOnClickListener(this);
	    level7b.setOnClickListener(this);
	    level8b.setOnClickListener(this);
	    level9b.setOnClickListener(this);
	    level10b.setOnClickListener(this);
	    level11b.setOnClickListener(this);
	    level12b.setOnClickListener(this);
	    level13b.setOnClickListener(this);
	    level14b.setOnClickListener(this);
	    level15b.setOnClickListener(this);
	    level16b.setOnClickListener(this);
	    level17b.setOnClickListener(this);
	    level18b.setOnClickListener(this);
	    level19b.setOnClickListener(this);
	    level20b.setOnClickListener(this);
	    level21b.setOnClickListener(this);
	  
	    
	    level1b.setText(Html.fromHtml("<big>" + "1+1" + "</big><br />" + "<small><small><small><font color='#E4E5E6'>"+"Único"+"</small></small></small></font>" + ""));
	    level2b.setText(Html.fromHtml("<big>" + "1x1" + "</big><br />" + "<small><small><small><font color='#E4E5E6'>"+"Único"+"</small></small></small></font>" + ""));
	    level3b.setText(Html.fromHtml("<big>" + "2+2" + "</big><br />" + "<small><small><small><font color='#E4E5E6'>"+"Único"+"</small></small></small></font>" + ""));
	    level4b.setText(Html.fromHtml("<big>" + "2x1" + "</big><br />" + "<small><small><small><font color='#0b9E8C'>"+"Inicial"+"</small></small></small></font>" + ""));
	    level5b.setText(Html.fromHtml("<big>" + "2x1" + "</big><br />" + "<small><small><small><font color='#FF9000'>"+"Medio"+"</small></small></small></font>" + ""));
	    level6b.setText(Html.fromHtml("<big>" + "2x1" + "</big><br />" + "<small><small><small><font color='#ED1566'>"+"Avanzado"+"</small></small></small></font>" + ""));
	    level7b.setText(Html.fromHtml("<big>" + "3x1" + "</big><br />" + "<small><small><small><font color='#0b9E8C'>"+"Inicial"+"</small></small></small></font>" + ""));
	    level8b.setText(Html.fromHtml("<big>" + "3x1" + "</big><br />" + "<small><small><small><font color='#FF9000'>"+"Medio"+"</small></small></small></font>" + ""));
	    level9b.setText(Html.fromHtml("<big>" + "3x1" + "</big><br />" + "<small><small><small><font color='#ED1566'>"+"Avanzado"+"</small></small></small></small></font>" + ""));
	    level10b.setText(Html.fromHtml("<big>" + "2"+"<small><small><sup>"+"2"+"</small></small></sup>" + "</big><br />" + "<small><small><small><font color='#0b9E8C'>" +"Inicial"+"</small></small></small></font>" + ""));
	    level11b.setText(Html.fromHtml("<big>" + "2"+"<small><small><sup>"+"2"+"</small></small></sup>" + "</big><br />" + "<small><small><small><font color='#FF9000'>"+"Medio"+"</small></small></small></font>" + ""));
	    level12b.setText(Html.fromHtml("<big>" + "2"+"<small><small><sup>"+"2"+"</small></small></sup>" + "</big><br />" + "<small><small><small><font color='#ED1566'>"+"Avanzado"+"</small></small></small></font>" + ""));
	    level13b.setText(Html.fromHtml("<big>" + "4x1" + "</big><br />" + "<small><small><small><font color='#0b9E8C'>"+"Inicial"+"</small></small></small></font>" + ""));
	    level14b.setText(Html.fromHtml("<big>" + "4x1" + "</big><br />" + "<small><small><small><font color='#FF9000'>"+"Medio"+"</small></small></small></font>" + ""));
	    level15b.setText(Html.fromHtml("<big>" + "4x1" + "</big><br />" + "<small><small><small><font color='#ED1566'>"+"Avanzado"+"</small></small></small></small></font>" + ""));
	    level16b.setText(Html.fromHtml("<big>" + "3"+"<small><small><sup>"+"2"+"</small></small></sup>" + "</big><br />" + "<small><small><small><font color='#0b9E8C'>" +"Inicial"+"</small></small></small></font>" + ""));
	    level17b.setText(Html.fromHtml("<big>" + "3"+"<small><small><sup>"+"2"+"</small></small></sup>" + "</big><br />" + "<small><small><small><font color='#FF9000'>" +"Medio"+"</small></small></small></font>" + "")); 
	    level18b.setText(Html.fromHtml("<big>" + "3"+"<small><small><sup>"+"2"+"</small></small></sup>" + "</big><br />" + "<small><small><small><font color='#ED1566'>" +"Avanzado"+"</small></small></small></font>" + ""));
	    level19b.setText(Html.fromHtml("<big>" + "4"+"<small><small><sup>"+"2"+"</small></small></sup>" + "</big><br />" + "<small><small><small><font color='#0b9E8C'>" +"Inicial"+"</small></small></small></font>" + ""));
	    level20b.setText(Html.fromHtml("<big>" + "4"+"<small><small><sup>"+"2"+"</small></small></sup>" + "</big><br />" + "<small><small><small><font color='#FF9000'>" +"Medio"+"</small></small></small></font>" + ""));
	    level21b.setText(Html.fromHtml("<big>" + "4"+"<small><small><sup>"+"2"+"</small></small></sup>" + "</big><br />" + "<small><small><small><font color='#ED1566'>" +"Avanzado"+"</small></small></small></font>" + ""));
	    
	    
	    ActionBar AB = getActionBar();
		AB.setDisplayHomeAsUpEnabled(true);
		AB.setTitle(Html.fromHtml("<font color='#ED1566'>Práctica</font>"));
		//AB.setSubtitle("Seleccione un nivel");
		AB.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F0F1F2")));
	    
	   
		int levelcompleted = PassLevel.getCurrentLevel("Levels_Completed", this);
		//int levelcompleted = 14;
		//askpersonal = PassLevel.getAskPersonal("Ask", MainActivity.THEcontext);
		
	    
	    //switch (levelcompleted)
	    //{
	    //case 0: level2b.setEnabled(false);
	    //		level2b.getBackground().setAlpha(90);//Numero entre 0 y 255
	    //case 1: level3b.setEnabled(false);
	    //		level3b.getBackground().setAlpha(90);//Numero entre 0 y 255
	    //case 2: level4b.setEnabled(false);
	    //		level4b.getBackground().setAlpha(90);//Numero entre 0 y 255
	    //case 3: level5b.setEnabled(false);
		// 		level5b.getBackground().setAlpha(90);//Numero entre 0 y 255
	    //case 4: level6b.setEnabled(false);
		//		level6b.getBackground().setAlpha(90);//Numero entre 0 y 255
		//case 5: level7b.setEnabled(false);
		//		level7b.getBackground().setAlpha(90);//Numero entre 0 y 255
		//case 6: level8b.setEnabled(false);
		//		level8b.getBackground().setAlpha(90);//Numero entre 0 y 255
		//case 7: level9b.setEnabled(false);
		//		level9b.getBackground().setAlpha(90);//Numero entre 0 y 255
		//case 8: level10b.setEnabled(false);
		//		level10b.getBackground().setAlpha(90);//Numero entre 0 y 255
		//case 9: level11b.setEnabled(false);
		//		level11b.getBackground().setAlpha(90);//Numero entre 0 y 255
		//case 10: level12b.setEnabled(false);
		//		level12b.getBackground().setAlpha(90);//Numero entre 0 y 255
		//case 11: level13b.setEnabled(false);
		//		level13b.getBackground().setAlpha(90);//Numero entre 0 y 255
		//case 12: level14b.setEnabled(false);
		//		level14b.getBackground().setAlpha(90);//Numero entre 0 y 255
		//case 13: level15b.setEnabled(false);
		//		level15b.getBackground().setAlpha(90);//Numero entre 0 y 255
		//case 14: level16b.setEnabled(false);
		//		level16b.getBackground().setAlpha(90);//Numero entre 0 y 255
		//case 15: level17b.setEnabled(false);
		//		level17b.getBackground().setAlpha(90);
		//case 16: level18b.setEnabled(false);
		//		level18b.getBackground().setAlpha(90);
		//case 17: level19b.setEnabled(false);
		//		level19b.getBackground().setAlpha(90);
		// case 18: level20b.setEnabled(false);
		//		level20b.getBackground().setAlpha(90);
		//case 19: level21b.setEnabled(false);
		//		level21b.getBackground().setAlpha(90);
		//case 20: level22b.setEnabled(false);
		//		level22b.getBackground().setAlpha(90);
		//case 21: level23b.setEnabled(false);
		//		level23b.getBackground().setAlpha(90);
		//case 22: level24b.setEnabled(false);
		//		 level24b.getBackground().setAlpha(90);
	    				    
		//break;  
	    //}
	       	    
	    
	}
	
	@Override
	public void onClick(View view) {
		if(view.getId()==R.id.lvl1){
		    //1+1
			startPlay(1,0, 0);
		}
		else if(view.getId()==R.id.lvl2){
		    //1x1
			startPlay(2,1, 0);
		}
		else if(view.getId()==R.id.lvl3){
		    //2+2
			startPlay(3,0, 1);
		}
		else if(view.getId()==R.id.lvl4){
		    //2x1
			startPlay(4,1, 1);
		}
		else if(view.getId()==R.id.lvl5){
		    //2x1
			startPlay(5,1, 1);
		}
		else if(view.getId()==R.id.lvl6){
		    //NUEVO
			startPlay(8,1, 1);
		}
		else if(view.getId()==R.id.lvl7){
		    //3x1
			startPlay(6,1, 2);
		}
		else if(view.getId()==R.id.lvl8){
		    //3x1
			startPlay(7,1, 2);
		}
		else if(view.getId()==R.id.lvl9){
		    //3x1
			startPlay(8,1, 2);
		}
		else if(view.getId()==R.id.lvl10){
		    //2^2
			startPlay(9,2, 1);
		}
		
		else if(view.getId()==R.id.lvl11){
		    //2^2
			startPlay(11,2, 1);
		}
		else if(view.getId()==R.id.lvl12){
		    //2^2
			startPlay(12,2, 1);
		}
		else if(view.getId()==R.id.lvl13){
		    //4x1
			startPlay(13,1, 4);
		}
		else if(view.getId()==R.id.lvl14){
		    //4x1
			startPlay(14,1, 4);
		}
		else if(view.getId()==R.id.lvl15){
		    //4x1
			startPlay(15,1, 4);
		}
		else if(view.getId()==R.id.lvl16){
		    //3^2
			startPlay(16,2, 2);
		}
		
		else if(view.getId()==R.id.lvl17){
		    //3^2
			startPlay(18,2, 2);
		}
		else if(view.getId()==R.id.lvl18){
		    //3^2
			startPlay(19,2, 2);
		}
		else if(view.getId()==R.id.lvl19){
		    //4^2
			startPlay(20,2, 3);
		}
		
		else if(view.getId()==R.id.lvl20){
		    //4^2
			startPlay(23,2, 3);
		}
		else if(view.getId()==R.id.lvl21){
		    //4^2
			startPlay(24,2, 3);
		}
		
		
	}
	public void startPlay(int chosenLevel, int chosenOperator, int chosenSublevel){
		//start gameplay
		Intent playIntent = new Intent(this, PlayGame.class);
		playIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		playIntent.putExtra("level",chosenLevel);
		playIntent.putExtra("operator",chosenOperator);
		playIntent.putExtra("sublevel", chosenSublevel);
		this.startActivity(playIntent);
		finish();
	}
	

}
