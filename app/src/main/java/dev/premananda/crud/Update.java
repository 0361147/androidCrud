package dev.premananda.crud;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Update extends AppCompatActivity {

    private DBHelper dbHelper;
    private TextView name;
    private TextView password;
    private TextView username;
    Intent intent;
    SessionHelper sessionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        name = findViewById(R.id.txtName);
        password = findViewById(R.id.txtPassword);
        username = findViewById(R.id.txtUsername);
        dbHelper = new DBHelper(this);
        intent = getIntent();
        sessionHelper = new SessionHelper(getApplicationContext());
        Integer userId = sessionHelper.getUserId();

        try {
            User user = dbHelper.findById(userId);
            if (user == null) {
                sessionHelper.setLogOut();
                finish();

            }
            name.setText(user.getName());
            password.setText(user.getPassword());
            username.setText(user.getUsername());

        } catch (Error e) {
            Toast.makeText(getApplicationContext(), "Fail to get user data", Toast.LENGTH_LONG).show();
            Log.d("find user data error", e.toString());
        } finally {
            dbHelper.close();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onUpdate(View view) {
        try {
            dbHelper.update(new User(
                    sessionHelper.getUserId(),
                    username.getText().toString().trim(),
                    password.getText().toString().trim(),
                    name.getText().toString()
            ));
            Intent intent = new Intent(this, Protected.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } catch (Error e) {
            Toast.makeText(this, "Fail to update user data", Toast.LENGTH_LONG).show();
            Log.d("fail to update user", String.valueOf(e));
        } finally {
            dbHelper.close();
        }
    }
}
