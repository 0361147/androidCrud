package dev.premananda.crud;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SessionHelper {
    final String SESSIONNAME = "LOGIN";
    final String ISLOGIN = "ISLOGIN";
    final String USERID = "USERID";
    final Integer SESSIONMODE = 0;

    SharedPreferences session;
    SharedPreferences.Editor editor;
    Context appContext;

    public SessionHelper(Context context) {
        appContext = context;
        session = context.getSharedPreferences(SESSIONNAME, SESSIONMODE);
        editor = session.edit();
    }

    public void setLogin(Integer userId) {
        editor.putBoolean(ISLOGIN, true);
        editor.putInt(USERID, userId);
        editor.commit();
    }

    public void setLogOut() {
        editor.clear();
        editor.commit();

        Intent intent = new Intent(appContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appContext.startActivity(intent);
    }

    public Integer getUserId() {
        return session.getInt(USERID, -1);
    }
}
