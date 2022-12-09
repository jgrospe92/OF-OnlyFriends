package com.example.models;

import android.text.TextUtils;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    // return false if empty
    public static boolean checkInput(EditText editText, String msg){
        if (!TextUtils.isEmpty(editText.getText().toString())){
            return  true;
        }
        editText.setError(msg);
        return false;
    }

    public static boolean checkValidEmail(EditText editText, String msg){
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(editText.getText().toString());
        if(matcher.find() && editText.getText().toString() != ""){
            return true;
        }
        editText.setError(msg);
        return  false;
    }

    public static boolean checkIfNumber(EditText editText, String msg){
        String regex = "^[1-9]\\d*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(editText.getText().toString());
        if(matcher.find()){
            return true;
        }
        editText.setError(msg);
        return  false;
    }
}
