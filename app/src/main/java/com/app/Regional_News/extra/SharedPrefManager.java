package com.app.Regional_News.extra;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.BaseBundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;


public class SharedPrefManager {

    public static final String SP_MAHASISWA_APP = "spMahasiswaApp";

    public static final String SP_NAMA = "spNama";
    public static final String SP_EMAIL = "spEmail";
    public static final String P_ldata = "plogindata";
    public static final String F_ldata = "flogindata";

    public static final String SP_P_LOGIN = "pLogin";
    public static final String SP_F_LOGIN = "fLogin";
    public static final String SP_LANGUAGE = "spLanguage";


    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_MAHASISWA_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    // New method specifically for saving the language preference
    public void saveLanguagePreference(String keySP, boolean value) {
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }



    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSPNama(){
        return sp.getString(SP_NAMA, "");
    }
    public String getPdata(){
        return sp.getString(P_ldata, "");
    }

    public  String getFdata()
    {
        return  sp.getString(F_ldata,"");
    }

    public String getSPEmail(){
        return sp.getString(SP_EMAIL, "");
    }

    public Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_P_LOGIN, false);
    }
    public Boolean getUserLogin(){
        return sp.getBoolean(SP_F_LOGIN, false);
    }

    // New method to save updated user data
    // Added this method to save the updated user data as a JSON string
    public void saveFdata(String fdata) {
        spEditor.putString(F_ldata, fdata);
        spEditor.commit();
    }



    public void saveUserData(String name, String email, String pwd, String u_pic, String u_id) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("u_name", name);
            jsonObject.put("u_email", email);
            jsonObject.put("u_pwd", pwd);
            jsonObject.put("u_pic", u_pic);
            jsonObject.put("u_id", u_id);

            String jsonString = jsonObject.toString();
            saveSPString(F_ldata, jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public Boolean getLanguagePreference() {
        return sp.getBoolean(SP_LANGUAGE, false); // Default is English (false)
    }




}
