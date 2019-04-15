package dev.premananda.crud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    DBHelper dbHelper;
    SessionHelper sessionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsername = findViewById(R.id.txtUsername);
        btnLogin = findViewById(R.id.btnLogin);
        txtPassword = findViewById(R.id.txtPassword);
        dbHelper = new DBHelper(this);
        sessionHelper = new SessionHelper(getApplicationContext());

        Integer userId = sessionHelper.getUserId();
        if (userId != -1) {
            startActivity(new Intent(getApplicationContext(), Protected.class));
            finish();
        }
    }


    public void onLogin(View view) {
        try {
            User user = dbHelper.findByUsername(txtUsername.getText().toString().trim());
            if (user == null) {
                Toast.makeText(this, "Your username is not registerd", Toast.LENGTH_LONG).show();
                return;
            }

            if (txtPassword.getText().toString().trim().equals(user.getPassword())) {
                sessionHelper.setLogin(user.getId());
                startActivity(new Intent(this, Protected.class));
                finish();
            } else  {
                Toast.makeText(this, "invalid password", Toast.LENGTH_LONG).show();
            }
        } catch (Error e) {
            Toast.makeText(this, "Fail to login", Toast.LENGTH_LONG).show();
            Log.d("Login errors", e.toString());
        } finally {
            dbHelper.close();
        }
    }

    public void onRegister(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}
