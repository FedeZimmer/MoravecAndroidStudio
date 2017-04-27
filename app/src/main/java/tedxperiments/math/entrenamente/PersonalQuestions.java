package tedxperiments.math.entrenamente;


import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.Normalizer;
import java.util.Locale;

import tedxperiments.math.entrenamente.R;
 
public class PersonalQuestions extends Activity implements OnClickListener {
	
	private Button DoneBut;
	//String name,email,birthyear,genderAnswer,studiesAnswer,handAnswer,languageAnswer,numberOfLaguagesAnswer;
	String birthyear,genderAnswer,studiesAnswer,handAnswer,languageAnswer,numberOfLaguagesAnswer,musicListenerAnswer,musicInstrumentistAnswer,musicTheoryAnswer;
	int languageAnswerId = 10;
	//EditText editTextBirth,editTexSex,editTextStudies,editTextTextHand,editTextName,editTextEmail,editTextNumberLanguages;
	EditText editTextBirth,editTexSex,editTextStudies,editTextTextHand,editTextNumberLanguages;
	RadioGroup rGender,rStudies,rHand,rLanguage,rListener,rInstrumentist,rTheory;
	DataSubject DSubj;
	String AUID;
	RadioButton rbD,rbZ,rbA;
	
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaldata);
        
        ActionBar AB = getActionBar();
        AB.hide();

        	 try {
        		 // plain text input Nacimiento
        		 editTextBirth = (EditText) this.findViewById(R.id.editTextNumberInput);
                 editTextBirth.setOnClickListener(this);
                 String previousBirth=PassLevel.getPersonal("myBirth", MainActivity.THEcontext);
                 if(previousBirth!=null){editTextBirth.setText(previousBirth);
                 birthyear=previousBirth;}

                 // This will get the radiogrou
				 rGender = (RadioGroup)findViewById(R.id.radioGender);
				 rStudies = (RadioGroup)findViewById(R.id.radioStudies);
				 rHand = (RadioGroup)findViewById(R.id.radioHand);
				 rLanguage = (RadioGroup)findViewById(R.id.radioNativeLanguage);
				 rListener = (RadioGroup)findViewById(R.id.radioListener);
				 rInstrumentist = (RadioGroup)findViewById(R.id.radioInstrumentist);
				 rTheory = (RadioGroup)findViewById(R.id.radioTheory);

             // plain text input Nombre
//             editTextName = (EditText) this.findViewById(R.id.editTextPlainTextInput);
//             String previousName=PassLevel.getPersonal("myName", MainActivity.THEcontext);
//             if(previousName!=null){editTextName.setText(previousName);}

             // number input EMAIL
//             editTextEmail = (EditText) this.findViewById(R.id.editTextEmailAddressInput);
//             String previousEmail=PassLevel.getPersonal("myEmail", MainActivity.THEcontext);
//             if(previousEmail!=null){editTextEmail.setText(previousEmail);}

				 //Number of laguanges
				 editTextNumberLanguages = (EditText)this.findViewById(R.id.numberOfLanguagesEditText);

             DoneBut=(Button)findViewById(R.id.ButDone);
             DoneBut.setOnClickListener(this);

         	String previousGenderAnswer=PassLevel.getPersonal("myGender", MainActivity.THEcontext);
         	if (previousGenderAnswer!=null)
         	{genderAnswer=previousGenderAnswer;
         	if(previousGenderAnswer.equals("Masculino")){rGender.check(R.id.genderMale);}
         	else if(previousGenderAnswer.equals("Femenino")){rGender.check(R.id.genderFemale);}
         	else if(previousGenderAnswer.equals("No Informa")){rGender.check(R.id.genderNoInf);}
         	}

         	String previousStudiesAnswer=PassLevel.getPersonal("myStudies", MainActivity.THEcontext);
         	if (previousStudiesAnswer!=null)
         	{studiesAnswer=previousStudiesAnswer;
         	if (previousStudiesAnswer.equals("Primaria terminada")){rStudies.check(R.id.Primary);}
         	else if(previousStudiesAnswer.equals("Secundaria en curso")){rStudies.check(R.id.SecundaryCourse);}
         	else if (previousStudiesAnswer.equals("Secundaria terminada")){rStudies.check(R.id.SecundaryEnd);}
         	else if(previousStudiesAnswer.equals("Terciario en curso")){rStudies.check(R.id.ThirdCourse);}
         	else if (previousStudiesAnswer.equals("Terciario finalizado")){rStudies.check(R.id.ThirdEnd);}
         	}


         	String previoushandAnswer=PassLevel.getPersonal("myHand", MainActivity.THEcontext);
         	if (previoushandAnswer!=null)
         		{handAnswer=previoushandAnswer;
         	if (previoushandAnswer.equals("Diestro")){rHand.check(R.id.RightHanded);}
         	else if(previoushandAnswer.equals("Zurdo")){rHand.check(R.id.LeftHanded);}
         	else if (previoushandAnswer.equals("Ambas")){rHand.check(R.id.BothHanded);}}

				String previousNativeLanguageAnswer=PassLevel.getPersonal("myLanguageId", MainActivity.THEcontext);
				 if (previousNativeLanguageAnswer!=null) {
					 languageAnswerId = Integer.parseInt(previousNativeLanguageAnswer);
					 switch (languageAnswerId) {
						 case 0:
							 rLanguage.check(R.id.EnglishLanguage);
							 break;
						 case 1:
							 rLanguage.check(R.id.SpanishLanguage);
							 break;
						 case 2:
							 rLanguage.check(R.id.FrenchLanguage);
							 break;
						 case 3:
							 rLanguage.check(R.id.PortugueseLanguage);
							 break;
						 default:
							 return;
					 }
				 }

         
         } catch (NullPointerException e) {
             e.printStackTrace();
         } catch (Exception e) {
             e.printStackTrace();
         }
        	 
        	 rGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
     		{
     		    public void onCheckedChanged(RadioGroup rGroup, int checkedId)
     		    {
     		        // This will get the radiobutton that has changed in its check state
     		        RadioButton checkedRadioButton = (RadioButton)rGroup.findViewById(checkedId);
     		        // This puts the value (true/false) into the variable
     		        boolean isChecked = checkedRadioButton.isChecked();
     		        // If the radiobutton that has changed in check state is now checked...
     		        if (isChecked)
     		        {
     		            // Changes the textview's text to "Checked: example radiobutton text"
     		           genderAnswer=checkedRadioButton.getText().toString();
     		        }
     		    }});
     		
     		rStudies.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
     		{
     		    public void onCheckedChanged(RadioGroup rGroup, int checkedId)
     		    {
     		        // This will get the radiobutton that has changed in its check state
     		        RadioButton checkedRadioButton = (RadioButton)rGroup.findViewById(checkedId);
     		        // This puts the value (true/false) into the variable
     		        boolean isChecked = checkedRadioButton.isChecked();
     		        // If the radiobutton that has changed in check state is now checked...
     		        if (isChecked)
     		        {
     		            // Changes the textview's text to "Checked: example radiobutton text"
     		           studiesAnswer=checkedRadioButton.getText().toString();
     		        }
     		    }});
     		
     		rHand.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
     		{
     		    public void onCheckedChanged(RadioGroup rGroup, int checkedId)
     		    {
     		        // This will get the radiobutton that has changed in its check state
     		        RadioButton checkedRadioButton = (RadioButton)rGroup.findViewById(checkedId);
     		        // This puts the value (true/false) into the variable
     		        boolean isChecked = checkedRadioButton.isChecked();
     		        // If the radiobutton that has changed in check state is now checked...
     		        if (isChecked)
     		        {
     		            // Changes the textview's text to "Checked: example radiobutton text"
     		           handAnswer=checkedRadioButton.getText().toString();
     		        }
     		    }});

		rLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		{
			public void onCheckedChanged(RadioGroup rGroup, int checkedId)
			{
				// This will get the radiobutton that has changed in its check state
				RadioButton checkedRadioButton = (RadioButton)rGroup.findViewById(checkedId);
				// This puts the value (true/false) into the variable
				boolean isChecked = checkedRadioButton.isChecked();
				// If the radiobutton that has changed in check state is now checked...
				if (isChecked) {
					// Changes the textview's text to "Checked: example radiobutton text"
					languageAnswer=checkedRadioButton.getText().toString();
					languageAnswerId = rLanguage.indexOfChild(checkedRadioButton);
					setNativeLanguageLive();
				}
			}});

		rListener.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		{
			public void onCheckedChanged(RadioGroup rGroup, int checkedId)
			{
				// This will get the radiobutton that has changed in its check state
				RadioButton checkedRadioButton = (RadioButton)rGroup.findViewById(checkedId);
				// This puts the value (true/false) into the variable
				boolean isChecked = checkedRadioButton.isChecked();
				// If the radiobutton that has changed in check state is now checked...
				if (isChecked)
				{
					// Changes the textview's text to "Checked: example radiobutton text"
					musicListenerAnswer=checkedRadioButton.getText().toString();
				}
			}});

		rInstrumentist.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		{
			public void onCheckedChanged(RadioGroup rGroup, int checkedId)
			{
				// This will get the radiobutton that has changed in its check state
				RadioButton checkedRadioButton = (RadioButton)rGroup.findViewById(checkedId);
				// This puts the value (true/false) into the variable
				boolean isChecked = checkedRadioButton.isChecked();
				// If the radiobutton that has changed in check state is now checked...
				if (isChecked)
				{
					// Changes the textview's text to "Checked: example radiobutton text"
					musicInstrumentistAnswer=checkedRadioButton.getText().toString();
				}
			}});

		rTheory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		{
			public void onCheckedChanged(RadioGroup rGroup, int checkedId)
			{
				// This will get the radiobutton that has changed in its check state
				RadioButton checkedRadioButton = (RadioButton)rGroup.findViewById(checkedId);
				// This puts the value (true/false) into the variable
				boolean isChecked = checkedRadioButton.isChecked();
				// If the radiobutton that has changed in check state is now checked...
				if (isChecked)
				{
					// Changes the textview's text to "Checked: example radiobutton text"
					musicTheoryAnswer=checkedRadioButton.getText().toString();
				}
			}});


     }
    
       
    
    @Override
	public void onClick(View view) {

		if(view.getId()==R.id.editTextNumberInput){showdatepick();}
				
		if(view.getId()==R.id.ButDone){
//			name = editTextName.getText().toString();
//			name=noacentos(name);
//			email = editTextEmail.getText().toString();
			numberOfLaguagesAnswer = editTextNumberLanguages.getText().toString();
			AUID = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
			//Toast.makeText(getBaseContext(), Html.fromHtml("Nombre: "+name+"<br>Email: "+email+"<br>Nacimiento: "+birthyear+"<br>Gender: "+genderAnswer+"<br>Studies: "+studiesAnswer+"<br>Hand: "+handAnswer+"<br>UUID: "+AUID), Toast.LENGTH_SHORT).show();
			Toast.makeText(getBaseContext(), Html.fromHtml(getString(R.string.thanks_form)), Toast.LENGTH_SHORT).show();
			
		//Tengo que pasar todo a saved, memoria del telefono.
			PassLevel.setNewPersonal("myAnUID", AUID, MainActivity.THEcontext);
//			PassLevel.setNewPersonal("myName", name, MainActivity.THEcontext);
//			PassLevel.setNewPersonal("myEmail", email, MainActivity.THEcontext);
			PassLevel.setNewPersonal("myBirth", birthyear, MainActivity.THEcontext);
			PassLevel.setNewPersonal("myGender", noacentos(genderAnswer), MainActivity.THEcontext);
			PassLevel.setNewPersonal("myStudies", noacentos(studiesAnswer), MainActivity.THEcontext);
			PassLevel.setNewPersonal("myHand", noacentos(handAnswer), MainActivity.THEcontext);
			//New
			PassLevel.setNewPersonal("myLanguage", noacentos(languageAnswer), MainActivity.THEcontext);
			PassLevel.setNewPersonal("myLanguageId", String.valueOf(languageAnswerId), MainActivity.THEcontext);
			PassLevel.setNewPersonal("myNumberOfLanguages", numberOfLaguagesAnswer, MainActivity.THEcontext);
			PassLevel.setNewPersonal("music_listener", noacentos(musicListenerAnswer), MainActivity.THEcontext);
			PassLevel.setNewPersonal("music_instrumentist", noacentos(musicInstrumentistAnswer), MainActivity.THEcontext);
			PassLevel.setNewPersonal("music_theory", noacentos(musicTheoryAnswer), MainActivity.THEcontext);

			int askpersonal = PassLevel.getAskPersonal("Ask", MainActivity.THEcontext);

			setNativeLanguage();


			if (askpersonal==0)
			{
			PassLevel.setAskPersonal("Ask", 2, MainActivity.THEcontext);
			Intent arcIntent = new Intent(this, PlayArcade.class);
			arcIntent.putExtra("level",1); 
			this.startActivity(arcIntent);
			finish();
			}
		if (askpersonal==2)
		{
			PassLevel.setAskPersonal("Ask", 52, MainActivity.THEcontext);
			Intent arcIntent = new Intent(this, PlayArcade.class);
			arcIntent.putExtra("level",51); 
			this.startActivity(arcIntent);
			finish();
			}
		if (askpersonal==52)
		{
			PassLevel.setAskPersonal("Ask", 102, MainActivity.THEcontext);
			Intent arcIntent = new Intent(this, PlayArcade.class);
			arcIntent.putExtra("level",101); 
			this.startActivity(arcIntent);
			finish();
			}
			
		
		}
				
		}
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) { //Back key pressed
           //Things to Do
//        	name = editTextName.getText().toString();
//        	email = editTextEmail.getText().toString();
//        	name=noacentos(name);
			numberOfLaguagesAnswer = editTextNumberLanguages.getText().toString();
			AUID = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
			//Toast.makeText(getBaseContext(), "Nombre: "+name+"\nEmail: "+email+"\nGender: "+genderAnswer+"\nStudies: "+studiesAnswer+"\nHand: "+handAnswer+"\nUUID: "+AUID+"\nBirth: "+birthyear, Toast.LENGTH_SHORT).show();
			//Toast.makeText(getBaseContext(), "Por favor, complete los datos y presione Listo", Toast.LENGTH_LONG).show();
			PassLevel.setNewPersonal("myAnUID", AUID, MainActivity.THEcontext);
//			PassLevel.setNewPersonal("myName", name, MainActivity.THEcontext);
//			PassLevel.setNewPersonal("myEmail", email, MainActivity.THEcontext);
			PassLevel.setNewPersonal("myBirth", birthyear, MainActivity.THEcontext);
			PassLevel.setNewPersonal("myGender", noacentos(genderAnswer), MainActivity.THEcontext);
			PassLevel.setNewPersonal("myStudies", noacentos(studiesAnswer), MainActivity.THEcontext);
			PassLevel.setNewPersonal("myHand", noacentos(handAnswer), MainActivity.THEcontext);
			//New
			PassLevel.setNewPersonal("myLanguage", noacentos(languageAnswer), MainActivity.THEcontext);
			PassLevel.setNewPersonal("myLanguageId", String.valueOf(languageAnswerId), MainActivity.THEcontext);
			PassLevel.setNewPersonal("myNumberOfLanguages", numberOfLaguagesAnswer, MainActivity.THEcontext);
			PassLevel.setNewPersonal("music_listener", noacentos(musicListenerAnswer), MainActivity.THEcontext);
			PassLevel.setNewPersonal("music_instrumentist", noacentos(musicInstrumentistAnswer), MainActivity.THEcontext);
			PassLevel.setNewPersonal("music_theory", noacentos(musicTheoryAnswer), MainActivity.THEcontext);

			setNativeLanguage();

			//Intent returnIntent = new Intent(this, MainActivity.class);
			Intent returnIntent = new Intent(this, MainActivity.class);
			returnIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(returnIntent);
			finish();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    
    private String noacentos(String s) {
		// TODO Auto-generated method stub
    	//String conAcentos = "áéíóú'èêÉç";
        //String sinAcentos = "aeiou eeEc";
    	//for (int i = 0; i < conAcentos.length(); i++)
        //    s = s.replace(conAcentos.charAt(i), sinAcentos.charAt(i));
		       // return s;
		if (TextUtils.isEmpty(s)) {
			return "";
		}
		else {
			String convertedString = Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
			return convertedString;
		}
    }



	private void showdatepick() {
    		DatePickerDialog dateDlg = new DatePickerDialog(this, 
    	         new DatePickerDialog.OnDateSetListener() {
    	          
    	         public void onDateSet(DatePicker view, int year,
    	                                             int monthOfYear, int dayOfMonth) 
    	         {
    	                    Time chosenDate = new Time();        
    	                    chosenDate.set(dayOfMonth, monthOfYear, year);
    	                    long dtDob = chosenDate.toMillis(true);
    	                    CharSequence strDate = DateFormat.format("dd MM yyyy", dtDob);
    	                    birthyear = (String) strDate;
    	                    editTextBirth.setText(birthyear);
    	                    //Toast.makeText(PersonalQuestions.this, "Date picked: " + strDate, Toast.LENGTH_SHORT).show();
    	        }}, 1980,0, 1);
    	      
    			DatePicker DD = dateDlg.getDatePicker();
    			Time minD = new Time();
    			Time maxD = new Time();
    			minD.set(1, 0, 1910);
    			maxD.set(31, 11, 2015);
    			
    			DD.setMinDate(minD.toMillis(true));
    			DD.setMaxDate(maxD.toMillis(true));

    	      dateDlg.setMessage("Fecha de Nacimiento");
    	      dateDlg.show();
	}

	boolean isEmailValid(CharSequence email) {
		   return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
		}
	}

	public void setNativeLanguageLive() {
		String myLanguage = String.valueOf(languageAnswerId);
		if (myLanguage == null) return;
		int myLanguageId = Integer.parseInt(myLanguage);
		String languageToLoad = new String();
		String actualLanguage = Locale.getDefault().getLanguage();
		PassLevel.setNewPersonal("myLanguageId", String.valueOf(languageAnswerId), MainActivity.THEcontext);
        PassLevel.setNewPersonal("lastSelectedLanguage",String.valueOf(languageAnswerId), MainActivity.THEcontext);
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
			Intent refresh = new Intent(PersonalQuestions.this, PersonalQuestions.class);
			startActivity(refresh);
			finish();
		}
	}

}