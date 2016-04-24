package com.example.winged_elite.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent mainIntent = new Intent(Menu.this, testPost.class);
        Menu.this.startActivity(mainIntent);
        Menu.this.finish();
    }

}
