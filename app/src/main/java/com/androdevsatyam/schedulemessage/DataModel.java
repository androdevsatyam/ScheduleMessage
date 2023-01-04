package com.androdevsatyam.schedulemessage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.UserHandle;

/**
 * Created by SATYAM on 04/01/23.
 * Associated with IOVRVF
 * Contact me on: satyamiovrvf@gmail.com
 */

public class DataModel {

    SharedPreferences preferences;

    DataModel(Context context){
        preferences=context.getSharedPreferences("Schedule",Context.MODE_PRIVATE);
    }

    public void setData(String number,String msg){
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("num",number);
        editor.putString("msg",msg);
        editor.apply();
    }

    public String getData(String type){
        return preferences.getString(type,"");
    }
}
