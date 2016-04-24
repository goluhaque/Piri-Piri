package com.example.winged_elite.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        b1 = (Button) findViewById(R.id.button);
        b1.setOnClickListener(this);
        Toast.makeText(loginActivity.this, "init_login", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        Intent mainIntent = new Intent(loginActivity.this,chatActivity.class);
        loginActivity.this.startActivity(mainIntent);
        loginActivity.this.finish();
    }
}
