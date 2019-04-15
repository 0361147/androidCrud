package dev.premananda.crud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Protected extends AppCompatActivity {

    TextView name;
    TextView id;
    TextView username;
    SessionHelper sessionHelper;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protected);

        name = findViewById(R.id.lblName);
        id = findViewById(R.id.lblUserid);
        username = findViewById(R.id.lblUsername);
        dbHelper = new DBHelper(this);
        sessionHelper = new SessionHelper(getApplicationContext());

        Integer userId = sessionHelper.getUserId();
        try {
            User user = dbHelper.findById(userId);
            if (user == null) {
                sessionHelper.setLogOut();
                finish();

            }
            name.setText(user.getName());
            id.setText(userId.toString());
            username.setText(user.getUsername());
        } catch (Error e) {
            Toast.makeText(getApplicationContext(), "Fail to get use data", Toast.LENGTH_LONG).show();
            Log.d("Find user data error", e.toString());
        } finally {
            dbHelper.close();
        }
    }

    public void onLogout(View view) {
        sessionHelper.setLogOut();
        finish();
    }

    public void onUpdate(View view) {
        startActivity(new Intent(this, Update.class));
    }

    public void onViewUsers(View view) {
        startActivity(new Intent(this, UserList.class));
    }

    public void onDeleteAccount(View view) {
        try {
            dbHelper.deleteUserById(sessionHelper.getUserId());
            sessionHelper.setLogOut();
        } catch (Error e) {
            Toast.makeText(this, "Fail to delete user", Toast.LENGTH_LONG).show();
            Log.d("Delete account error", e.toString());
        } finally {
            dbHelper.close();
        }
    }
}
