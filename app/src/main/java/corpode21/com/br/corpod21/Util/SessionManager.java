package corpode21.com.br.corpod21.Util;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import corpode21.com.br.corpod21.R;
import corpode21.com.br.corpod21.ui.LoginActivity;
import corpode21.com.br.corpod21.entidades.Usuario;

import com.google.gson.Gson;

import corpode21.com.br.corpod21.entidades.SemanaEvolucao;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences pref_email;

    public static final String SEMANAS = "Semana_Evolucao";

    private static final String PREF_EMAIL_USER = "c21_user_email";

    private static final String PREF_USER = "c21_user_";
    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    public String EMAIL;

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_FOTO = "foto";
    public static final String KEY_FIRST_TIME = "first_time";
    public static final String KEY_FIRST_TIME_VIDEO = "first_time_video";
    public static final String KEY_HCAFE = "hCafe";
    public static final String KEY_NCAFE = "nCafe";
    public static final String KEY_HLANCHEMANHA = "hLancheManha";
    public static final String KEY_NLANCHEMANHA = "nLancheManha";
    public static final String KEY_HALMOCO = "hAlmoco";
    public static final String KEY_NALMOCO = "nAlmoco";
    public static final String KEY_HLANCHETARDE = "hLancheTarde";
    public static final String KEY_NLANCHETARDE = "nLancheTarde";
    public static final String KEY_HJANTAR = "hJantar";
    public static final String KEY_NJANTAR = "nJantar";
    public static final String KEY_HCEIA = "hCeia";
    public static final String KEY_NCEIA = "nCeia";


    // Constructor
    public SessionManager(Context context) {
        this._context = context;

        pref_email = _context.getSharedPreferences(PREF_EMAIL_USER, PRIVATE_MODE);

        EMAIL = pref_email.getString("email",null);

        if(EMAIL != null)
            pref = _context.getSharedPreferences(PREF_USER + EMAIL, PRIVATE_MODE);
        else
            pref = _context.getSharedPreferences(PREF_USER, PRIVATE_MODE);

            editor = pref.edit();
    }

    public void createLoginSession(Usuario usuario){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_NAME, usuario.getNome());
        editor.putString(KEY_EMAIL, usuario.getEmail());
        editor.putString(KEY_FOTO, usuario.getImage());
        editor.putString(KEY_FIRST_TIME, "1");
        editor.putString(KEY_FIRST_TIME_VIDEO, "1");
        editor.putString(KEY_HCAFE, _context.getString(R.string.hora_Cafe));
        editor.putString(KEY_HLANCHEMANHA, _context.getString(R.string.hora_LancheManha));
        editor.putString(KEY_HALMOCO, _context.getString(R.string.hora_Almoco));
        editor.putString(KEY_HLANCHETARDE, _context.getString(R.string.hora_LancheTarde));
        editor.putString(KEY_HJANTAR, _context.getString(R.string.hora_Jantar));
        editor.putString(KEY_HCEIA, _context.getString(R.string.hora_Ceia));

        editor.putString(KEY_NCAFE, String.valueOf(false));
        editor.putString(KEY_NLANCHEMANHA, String.valueOf(false));
        editor.putString(KEY_NALMOCO, String.valueOf(false));
        editor.putString(KEY_NLANCHETARDE, String.valueOf(false));
        editor.putString(KEY_NJANTAR, String.valueOf(false));
        editor.putString(KEY_NCEIA, String.valueOf(false));
        // commit changes
        editor.commit();
    }


    public void setUserDetails(String KEY, String VALUE)
    {
        editor.putString(KEY, VALUE);
        editor.commit();
    }

    public String getVALUE(String KEY) { return pref.getString(KEY,null);  }
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_FOTO, pref.getString(KEY_FOTO, null));
        user.put(KEY_FIRST_TIME, pref.getString(KEY_FIRST_TIME, null));
        user.put(KEY_FIRST_TIME_VIDEO, pref.getString(KEY_FIRST_TIME_VIDEO, null));

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
        editor.putBoolean(IS_LOGIN, false);
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


    public void saveSemanas(List<SemanaEvolucao> Semanas)
    {
        Gson gson = new Gson();
        String jsonSemanas = gson.toJson(Semanas);
        editor.putString(SEMANAS, jsonSemanas);

        editor.commit();
    }

    public void removeSemana(SemanaEvolucao semana) {
        ArrayList<SemanaEvolucao> semanas = getSemanas(_context);
        if (semanas != null) {
            semanas.remove(semana);
            saveSemanas(semanas);
        }
    }

    public void addSemana(Context context, SemanaEvolucao Semana) {
        List<SemanaEvolucao> semanas = getSemanas(context);
        if (semanas == null)
            semanas = new ArrayList<>();
        semanas.add(Semana);
        saveSemanas(semanas);
    }

    public ArrayList<SemanaEvolucao> getSemanas(Context context) {
        SharedPreferences settings;
        List<SemanaEvolucao> semanas;

        if(EMAIL == null)
            EMAIL = pref_email.getString("email",null);

        settings = context.getSharedPreferences(PREF_USER + EMAIL,
                Context.MODE_PRIVATE);

        if (settings.contains(SEMANAS)) {
            String jsonFavorites = settings.getString(SEMANAS, null);
            Gson gson = new Gson();
            SemanaEvolucao[] semanaItens = gson.fromJson(jsonFavorites,
                    SemanaEvolucao[].class);

            semanas = Arrays.asList(semanaItens);
            semanas = new ArrayList<>(semanas);
        } else
            return null;

        return (ArrayList<SemanaEvolucao>) semanas;
    }

}