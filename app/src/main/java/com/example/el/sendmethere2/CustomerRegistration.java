package com.example.el.sendmethere2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//import android.widget.EditText;

public class CustomerRegistration extends AppCompatActivity {

   // EditText textname = (EditText) findViewById(R.id.textname);
    //EditText textemail = (EditText) findViewById(R.id.textemail);
    //EditText textphone = (EditText) findViewById(R.id.textphone);
   Button start;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_register);

        start = (Button) findViewById(R.id.start);

        //Listening to start button event
        start.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), CustomerHome.class);

                startActivity(nextScreen);

            }
        });

    }
}
