package com.example.senni.taksipalvelu;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button buttonStart;
    Button buttonOrder;
    Button buttonCancel;
    Button buttonReturn;
    Button buttonInfo;

    Spinner spinnerPassengers;
    Spinner spinnerLuggage;

    String passengers;
    String luggage;

    TextView numberText;
    TextView timeText;
    TextView priceText;

    boolean clickChecker = false;
    boolean doubleBackToExitPressedOnce = false;

    //TODO: Replace placeholder values
    String numberFinal = "2056EHG1";
    String timeFinal = "12:45";
    int priceOriginal = 9;
    int priceFinal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Launch main screen
        setContentView(R.layout.activity_main);

        buttonStart = findViewById(R.id.button_main);
        buttonStart.setOnClickListener(mainOnClick);
    }

    //From main screen to the order screen
    View.OnClickListener mainOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            priceFinal = 0;
            priceOriginal = 9;

            setContentView(R.layout.order_screen);

            buttonOrder = findViewById(R.id.button_order);
            buttonOrder.setOnClickListener(orderOnClick);

            spinnerPassengers = findViewById(R.id.spinner_passenger);
            spinnerLuggage = findViewById(R.id.spinner_luggage);
        }
    };

    //Placing the order and moving to the information screen
    View.OnClickListener orderOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            clickChecker = true;

            passengers = spinnerPassengers.getSelectedItem().toString();
            luggage = spinnerLuggage.getSelectedItem().toString();

            //price management
            switch (passengers){
                case "1–4":
                    priceOriginal = 9; break;
                case "5–8":
                    priceOriginal = priceOriginal + 3; break;
                case "9–10":
                    priceOriginal = priceOriginal + 5; break;
                default:
                    priceOriginal = 25; break;
            }

            switch (luggage){
                case "Small":
                    priceFinal = priceOriginal; break;
                case "Medium":
                    priceFinal = priceOriginal + 2; break;
                case "Large":
                    priceFinal = priceOriginal + 3; break;
                default:
                    priceFinal++; break;
            }

            setContentView(R.layout.information_screen);

            buttonCancel = findViewById(R.id.button_cancel);
            buttonCancel.setOnClickListener(cancelOnClick);

            buttonReturn = findViewById(R.id.button_return);
            buttonReturn.setOnClickListener(returnOnClick);

            numberText = findViewById(R.id.textView_number);
            numberText.setText(numberFinal);

            timeText = findViewById(R.id.textView_time);
            timeText.setText(timeFinal);

            priceText = findViewById(R.id.textView_price);
            priceText.setText(String.format(Locale.ENGLISH,"%d", priceFinal));

        }
    };

    //Cancel order
    View.OnClickListener cancelOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            priceFinal = 0;
            priceOriginal = 9;

            clickChecker = false;

            setContentView(R.layout.activity_main);

            buttonStart = findViewById(R.id.button_main);
            buttonStart.setOnClickListener(mainOnClick);
        }
    };

    //Return to the main screen
    View.OnClickListener returnOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setContentView(R.layout.activity_main);

            buttonStart = findViewById(R.id.button_main);
            buttonStart.setOnClickListener(mainOnClick);

            buttonInfo = findViewById(R.id.button_info);
            buttonInfo.setVisibility(View.VISIBLE);
            buttonInfo.setOnClickListener(infoOnClick);
        }
    };

    //Return to the information screen
    View.OnClickListener infoOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setContentView(R.layout.information_screen);

            buttonCancel = findViewById(R.id.button_cancel);
            buttonCancel.setOnClickListener(cancelOnClick);

            buttonReturn = findViewById(R.id.button_return);
            buttonReturn.setOnClickListener(returnOnClick);

            numberText = findViewById(R.id.textView_number);
            numberText.setText(numberFinal);

            timeText = findViewById(R.id.textView_time);
            timeText.setText(timeFinal);

            priceText = findViewById(R.id.textView_price);
            priceText.setText(String.format(Locale.ENGLISH,"%d", priceFinal));
        }
    };

    @Override
    public void onBackPressed(){
        //Launch main screen
        setContentView(R.layout.activity_main);

        buttonStart = findViewById(R.id.button_main);
        buttonStart.setOnClickListener(mainOnClick);

        //Check if order is placed and enable info button
        if (clickChecker){
            buttonInfo = findViewById(R.id.button_info);
            buttonInfo.setVisibility(View.VISIBLE);
            buttonInfo.setOnClickListener(infoOnClick);
        }

        //Exit app with pressing back twice
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.exit_message, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

}
