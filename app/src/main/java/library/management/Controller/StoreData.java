package library.management.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import library.management.Class.User;

public class StoreData {

    private static final String KEY_PREFERENCES_NAME = "libraryAppPreferences";

    private Context context;

    public StoreData(Context context)
    {
        this.context = context;
    }

    public void setLocalHost(String Host){
        SharedPreferences pref = context.getSharedPreferences(KEY_PREFERENCES_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("localHost", Host);
        editor.commit();
    }

    public String getLocalHost(){
        SharedPreferences pref = context.getSharedPreferences(KEY_PREFERENCES_NAME, context.MODE_PRIVATE);
        return pref.getString("localHost", "");
    }

    public void setCurrentUser(User user,String X)
    {
        SharedPreferences pref = context.getSharedPreferences(KEY_PREFERENCES_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", "True");
        editor.putString("Admin", X);
        editor.putString("Type", user.getUserType());
        editor.putString("Id", user.getUserId());
        editor.putString("Name", user.getName());
        editor.putString("Email", user.getEmail());
        editor.commit();
    }

    public boolean isAdmin(){
        SharedPreferences pref = context.getSharedPreferences(KEY_PREFERENCES_NAME, context.MODE_PRIVATE);
        if (pref.getString("Admin", "").equals("True"))
            return true;
        else
            return false;
    }

    public User getCurrentUser()
    {
        SharedPreferences pref = context.getSharedPreferences(KEY_PREFERENCES_NAME, context.MODE_PRIVATE);

        if (!pref.contains("token"))
            return null;

        User user = new User();
        user.setUserType(pref.getString("Type", ""));
        user.setUserId(pref.getString("Id", ""));
        user.setName(pref.getString("Name", ""));
        user.setEmail(pref.getString("Email", ""));

        return user;
    }

    public void logoutUser(){
        SharedPreferences pref = context.getSharedPreferences(KEY_PREFERENCES_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear().commit();
    }
}
