package dev.premananda.crud;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    DBHelper dbHelper;
    TextView name;
    TextView password;
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // set back button di action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        name = findViewById(R.id.txtName);
        password = findViewById(R.id.txtPassword);
        username = findViewById(R.id.txtUsername);
        dbHelper = new DBHelper(this);
    }

    // function yang akan berjalan ketika back button di click
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onRegister(View view) {
        try {
            dbHelper.save(new User(
                    username.getText().toString().trim(),
                    password.getText().toString().trim(),
                    name.getText().toString()
            ));
            finish();
        } catch (Error e) {
            Toast.makeText(this, "Failed to register user", Toast.LENGTH_LONG).show();
            Log.d("register error", String.valueOf(e));
        } finally {
            dbHelper.close();
        }
    }
}
