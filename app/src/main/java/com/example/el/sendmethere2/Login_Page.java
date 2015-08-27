package com.example.el.sendmethere2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login_Page extends AppCompatActivity{

    Button cstomerbutton, drverbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login__page);

        cstomerbutton = (Button) findViewById(R.id.cstomerbutton);
        drverbutton = (Button) findViewById(R.id.drverbutton);

        //Listening to Customer Registration button event
        cstomerbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen1 = new Intent(getApplicationContext(), CustomerRegistration.class);

                startActivity(nextScreen1);

            }
        });

        //Listening to Driver Login button event
        drverbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen2 = new Intent(getApplicationContext(), DriverLogin.class);

                startActivity(nextScreen2);

            }
        });

    }



}
