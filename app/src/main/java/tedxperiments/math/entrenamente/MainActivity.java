package tedxperiments.math.entrenamente;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;

import java.util.Locale;


public class MainActivity extends Activity implements OnClickListener {

	
	//ui items
	private Button playBtn, helpBtn, highBtn, arcBtn;
	//level names
	String[] levelOperand = {"Addition", "Multiplication","Square"};
	String[] levelAdd = {"1 digit", "2 digits", "3 digits", "4 digits"};
	String[] levelMul = {"1 x 1", "2 x 1", "3 x 1","2 x 2", "4 x 1","3 x 2","3 x 3"};
	String[] levelSquare = {"1 digit", "2 digits", "3 digits", "4 digits", "5 digits"};
	public static Context THEcontext;
	
	
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//retrieve references
		playBtn = (Button)findViewById(R.id.play_btn);
		helpBtn = (Button)findViewById(R.id.help_btn);
		highBtn = (Button)findViewById(R.id.high_btn);
		arcBtn = (Button)findViewById(R.id.Arcade_btn);

		//listen for clicks
		playBtn.setOnClickListener(this);
		helpBtn.setOnClickListener(this);
		highBtn.setOnClickListener(this);
		arcBtn.setOnClickListener(this);
		highBtn.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("Set Language");
				// Add the buttons
				builder.setPositiveButton("English", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						String languageToLoad = "en"; // your language
						Locale locale = new Locale(languageToLoad);
						Locale.setDefault(locale);
						Configuration config = new Configuration();
						config.locale = locale;
						getBaseContext().getResources().updateConfiguration(config,
								getBaseContext().getResources().getDisplayMetrics());
						dialog.dismiss();

						Intent refresh = new Intent(MainActivity.this, MainActivity.class);
						startActivity(refresh);
						finish();
					}
				});
				builder.setNegativeButton("French", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// User cancelled the dialog

						String languageToLoad = "fr"; // your language
						Locale locale = new Locale(languageToLoad);
						Locale.setDefault(locale);
						Configuration config = new Configuration();
						config.locale = locale;
						getBaseContext().getResources().updateConfiguration(config,
								getBaseContext().getResources().getDisplayMetrics());
						dialog.dismiss();

						Intent refresh = new Intent(MainActivity.this, MainActivity.class);
						startActivity(refresh);
						finish();

					}
				});

				builder.create().show();
				return true;
			}
		});
		THEcontext=this;

		setNativeLanguage();

		ActionBar AB = getActionBar();
		AB.setTitle(Html.fromHtml("<font color='#60b0c1'>Entrenamente </font>"));
		AB.hide();
	}

	@Override
	public void onClick(View view) {
		if(view.getId()==R.id.play_btn){
			
			//play button
			Intent levelIntent = new Intent(this, LevelSelect.class);
			this.startActivity(levelIntent);
			
			
			//Menu de sumas
			//final AlertDialog.Builder builderSum = new AlertDialog.Builder(this);
			//builderSum.setTitle("Choose a level Addition")
			//.setSingleChoiceItems(levelAdd, 0, new DialogInterface.OnClickListener() {
			//	public void onClick(DialogInterface dialogSum, int which2) {
			//		dialogSum.dismiss();
			//		//start gameplay
			//		startPlay(0, which2); //pasa operand y level
			//	} 
			//});
			
			//Menu de multiplicacion
			//final AlertDialog.Builder builderMul = new AlertDialog.Builder(this);
			//builderMul.setTitle("Choose a level Multiplication")
			//.setSingleChoiceItems(levelMul, 0, new DialogInterface.OnClickListener() {
			//	public void onClick(DialogInterface dialogMul, int which3) {
			//		dialogMul.dismiss();
			//		//start gameplay
			//		startPlay(1, which3);
			//	} 
			//});
			
			//Menu de cuadrados
			//final AlertDialog.Builder builderSq = new AlertDialog.Builder(this);
			//builderSq.setTitle("Choose a level Square")
			//.setSingleChoiceItems(levelSquare, 0, new DialogInterface.OnClickListener() {
			//	public void onClick(DialogInterface dialogSq, int which4) {
			//		dialogSq.dismiss();
			//		//start gameplay
			//		startPlay(2, which4);
			//	} 
			//});
			
			
				
			//Menu para elegir operacion
			//AlertDialog.Builder builder = new AlertDialog.Builder(this);
			//builder.setTitle("Choose a level")
			//.setSingleChoiceItems(levelOperand, 0, new DialogInterface.OnClickListener() {
			//	public void onClick(DialogInterface dialog, int which) {
			//		dialog.dismiss();
					//start gameplay
					//startPlay(which);
			//		if (which ==0){
			//				AlertDialog adSum = builderSum.create();
			//				adSum.show();}
			//			else if (which==1){
			//				AlertDialog adMul = builderMul.create();
			//				adMul.show();}
			//				else if (which==2){
			//					AlertDialog adSq = builderSq.create();
			//					adSq.show();}
			//	} 
			//});
					
			
						
						
			//AlertDialog ad = builder.create();
			//ad.show();
			
		}
		else if(view.getId()==R.id.help_btn){
			//how to play button
			Intent helpIntent = new Intent(this, HowToPlay.class);
			this.startActivity(helpIntent);
		}
		else if(view.getId()==R.id.high_btn){
			//high scores button
			//Intent highIntent = new Intent(this, HighList.class);
			//Intent highIntent = new Intent(this, PersonalQuestions.class);
			Intent highIntent = new Intent(this, StatsSelect.class);
			this.startActivity(highIntent);
		}
		else if(view.getId()==R.id.Arcade_btn){
			int askpersonal = PassLevel.getAskPersonal("Ask", MainActivity.THEcontext);
			if (askpersonal<1){
				Intent personalIntent = new Intent(this, PersonalQuestions.class);
				this.startActivity(personalIntent);
				}
			else {
			Intent arcIntent = new Intent(this, HighList.class);
			//Intent arcIntent = new Intent(this, PlayArcade.class);
			this.startActivity(arcIntent);}
		}
	}
	
	
	
	 
	public void startPlay(int chosenOperator, int chosenLevel){
		//start gameplay
		Intent playIntent = new Intent(this, PlayGame.class);
		playIntent.putExtra("operator",chosenOperator);
		playIntent.putExtra("level", chosenLevel);
		this.startActivity(playIntent);
	}

	public void setNativeLanguage() {
		String myLanguage = PassLevel.getPersonal("myLanguageId", MainActivity.THEcontext);
		if (myLanguage == null) return;
		int myLanguageId = Integer.parseInt(myLanguage);
		String languageToLoad = new String();
		String actualLanguage = Locale.getDefault().getLanguage();
		switch (myLanguageId){
			case 0:
				languageToLoad = "en";
				break;
			case 1:
				languageToLoad = "es";
				break;
			case 2:
				languageToLoad = "fr";
				break;
			case 3:
				languageToLoad = "pt";
				break;
			default:
				return;
		}

		if (!languageToLoad.equals("") && !languageToLoad.equals(actualLanguage)) {
			Locale locale = new Locale(languageToLoad);
			Locale.setDefault(locale);
			Configuration config = new Configuration();
			config.locale = locale;
			getBaseContext().getResources().updateConfiguration(config,
					getBaseContext().getResources().getDisplayMetrics());
			Intent refresh = new Intent(MainActivity.this, MainActivity.class);
			startActivity(refresh);
			finish();
		}
	}

	//@Override
	//public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
	//	return true;
	//}
}


