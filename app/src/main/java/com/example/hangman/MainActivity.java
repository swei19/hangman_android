package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_MESSAGE = "com.example.Hangman.MESSAGE";


    RadioGroup catGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button competitiveButton = (Button) findViewById(R.id.competitiveMode);
        competitiveButton.setOnClickListener(this);

        Button casualButton = (Button) findViewById(R.id.casualMode);
        casualButton.setOnClickListener(this);

        catGroup = findViewById(R.id.catGroup);


    }

    /** Called when the user taps the new game button */
    public void newGame(View view) {

        TextView errorMessage = findViewById(R.id.errorMessage);

        Intent intent = new Intent(this, MainUI.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String playerName = editText.getText().toString();

        Button competitiveButton = findViewById(R.id.competitiveMode);

        int clickedRadioButtonID = catGroup.getCheckedRadioButtonId();
        RadioButton clickedRadioButton = findViewById(clickedRadioButtonID);
        intent.putExtra(EXTRA_MESSAGE, playerName);

        if (clickedRadioButtonID != -1) {
            intent.putExtra("CATEGORY_CHOSEN", clickedRadioButton.getText().toString().toLowerCase());
            startActivity(intent);
        } else if (!competitiveButton.isEnabled()){
            intent.putExtra("CATEGORY_CHOSEN", "competitive");
            startActivity(intent);
        } else {
            errorMessage.setVisibility(View.VISIBLE);
            errorMessage.setText("Please select a category");
        }




    }

    public void onClick(View v){

        switch(v.getId()){
            case R.id.competitiveMode: {

                Button competitiveButton = findViewById(R.id.competitiveMode);
                Button casualButton  = findViewById(R.id.competitiveMode);
                RadioGroup catGroup = findViewById(R.id.catGroup);

                casualButton.setEnabled(true);
                casualButton.getBackground().clearColorFilter();
                competitiveButton.setEnabled(false);

                catGroup.setVisibility(View.INVISIBLE);

                break;
            }

            case R.id.casualMode: {

                Button competitiveButton = findViewById(R.id.competitiveMode);
                Button casualButton  = findViewById(R.id.competitiveMode);
                RadioGroup catGroup = findViewById(R.id.catGroup);

                competitiveButton.setEnabled(true);
                competitiveButton.getBackground().clearColorFilter();

                catGroup.setVisibility(View.VISIBLE);
                break;
            }

        }





    };



}






