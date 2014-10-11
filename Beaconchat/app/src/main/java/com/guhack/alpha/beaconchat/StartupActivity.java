package com.guhack.alpha.beaconchat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class StartupActivity extends Activity {

    EditText usernameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        usernameField = (EditText) findViewById(R.id.username_field);
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/fragile_bombers.ttf");
        TextView tv = (TextView) findViewById(R.id.enter_username_prompt);
        TextView title = (TextView) findViewById(R.id.app_title);
        Button button = (Button) findViewById(R.id.submit_button);
        button.setTypeface(tf);
        tv.setTypeface(tf);
        title.setTypeface(tf);
        findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameField.getText().toString();
                if (!username.isEmpty()) {
                    UserManager.getInstance().setUsername(username);
                    startActivity(new Intent(StartupActivity.this, ChatroomListActivity.class));
                }
                else{
                    Toast.makeText(StartupActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.startup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
