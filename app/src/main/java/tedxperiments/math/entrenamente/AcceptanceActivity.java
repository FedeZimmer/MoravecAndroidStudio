package tedxperiments.math.entrenamente;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * Created by fzimmerman on 4/9/17.
 */

public class AcceptanceActivity extends Activity implements View.OnClickListener {

    private TextView acceptanceTextView;
    private CheckBox acceptanceCheckBox;
    private Button acceptanceButton;
    private boolean checked=false;
    private String nextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptance);
        Intent previousIntent= getIntent();
        Bundle bundle = previousIntent.getExtras();

        if(bundle!=null) {
            nextActivity = (String) bundle.get("type");
        }

        ActionBar AB = getActionBar();
        AB.hide();

        acceptanceTextView = (TextView) this.findViewById(R.id.acceptanceTextView);
        acceptanceCheckBox =(CheckBox) this.findViewById(R.id.acceptanceCheckBox);
        acceptanceButton = (Button) this.findViewById(R.id.acceptanceButton);
        acceptanceButton.setOnClickListener(this);
        acceptanceButton.setText(R.string.no_acceptance_button);

        acceptanceCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    checked = true;
                    acceptanceButton.setText(R.string.yes_acceptance_button);
                }
                else {
                    checked = false;
                    acceptanceButton.setText(R.string.no_acceptance_button);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.acceptanceButton){
            if(checked){
                //AVANZO
                startPlay();
            }
            else{
                super.onBackPressed();
            }
        }


        }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    public void startPlay(){

        if(nextActivity.equals("arcade")){
            int askpersonal = PassLevel.getAskPersonal("Ask", MainActivity.THEcontext);
            if (askpersonal<1){
                Intent personalIntent = new Intent(this, PersonalQuestions.class);
                this.startActivity(personalIntent);
            }
            else {
                Intent arcIntent = new Intent(this, HighList.class);
                this.startActivity(arcIntent);
                finish();
            }
        }
        if(nextActivity.equals("practice")){
            Intent levelIntent = new Intent(this, LevelSelect.class);
            this.startActivity(levelIntent);
        }
        PassLevel.setAskPersonal("AcceptanceAsked", 2, MainActivity.THEcontext);
        finish();
    }
}
