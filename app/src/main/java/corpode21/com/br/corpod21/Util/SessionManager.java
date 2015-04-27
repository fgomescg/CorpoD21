package corpode21.com.br.corpod21.Util;


import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import corpode21.com.br.corpod21.ui.LoginActivity;
import corpode21.com.br.corpod21.entidades.Usuario;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "CorpoD21";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PESO_INI = "peso_ini";
    public static final String KEY_ALTURA = "altura";
    public static final String KEY_PESO_META = "peso_meta";
    public static final String KEY_BICEPS = "biceps";
    public static final String KEY_CINTURA = "cintura";
    public static final String KEY_QUADRIL = "quadril";
    public static final String KEY_PERNA = "perna";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(Usuario usuario){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_NAME, usuario.getNome());
        editor.putString(KEY_EMAIL, usuario.getEmail());

        editor.putString(KEY_PESO_INI, String.valueOf(usuario.getPesoIni()));
        editor.putString(KEY_ALTURA, String.valueOf(usuario.getAltura()));
        editor.putString(KEY_PESO_META, String.valueOf(usuario.getPesoMeta()));
        editor.putString(KEY_BICEPS, String.valueOf(usuario.getBiceps()));
        editor.putString(KEY_CINTURA, String.valueOf(usuario.getCintura()));
        editor.putString(KEY_QUADRIL, String.valueOf(usuario.getQuadril()));
        editor.putString(KEY_PERNA, String.valueOf(usuario.getPerna()));

        // commit changes
        editor.commit();
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_PESO_INI, pref.getString(KEY_PESO_INI, null));
        user.put(KEY_ALTURA, pref.getString(KEY_ALTURA, null));
        user.put(KEY_PESO_META, pref.getString(KEY_PESO_META, null));
        user.put(KEY_BICEPS, pref.getString(KEY_BICEPS, null));
        user.put(KEY_CINTURA, pref.getString(KEY_CINTURA, null));
        user.put(KEY_QUADRIL, pref.getString(KEY_QUADRIL, null));
        user.put(KEY_PERNA, pref.getString(KEY_PERNA, null));

        // return user
        return user;
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }


    /**This function clears all session data and redirect the user to LoginActivity
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /* * Quick check for login
       Get Login State **/
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }


}