package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;

import com.example.models.Helper;
import com.example.models.Profile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Wallet extends AppCompatActivity {

    TextView currentBalanceTextView;
    EditText amountEditTextWallet;
    Button addAmountButtonWallet;
    Profile profile;

    public  Wallet(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        SharedPreferences userData = getSharedPreferences("user", MODE_PRIVATE);
        profile = new Profile(getApplicationContext());
        profile = profile.get(userData.getString("userID",""));

        currentBalanceTextView = findViewById(R.id.currentBalanceTextView);
        amountEditTextWallet = findViewById(R.id.amountEditTextWallet);

        currentBalanceTextView.setText("Your current balance is : $" + profile.getWallet());
        addAmountButtonWallet = findViewById(R.id.addAmountButtonWallet);
        addAmountButtonWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkIfNumber(amountEditTextWallet)){
                    amountEditTextWallet.setError("Please enter a positive number");
                    return;
                }
                Profile profileHelper = new Profile(getApplicationContext());

                int newAmount = Integer.parseInt(amountEditTextWallet.getText().toString());
                int oldAmount = Integer.parseInt(profile.getWallet());
                String newBal = String.valueOf(oldAmount + newAmount);
                profile.setWallet(newBal);
                profileHelper.update(profile);
                currentBalanceTextView.setText("Your current balance is : $" + profile.getWallet());
             
            }
        });
    }

    private static boolean checkIfNumber(EditText editText){
        String regex = "^[1-9]\\d*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(editText.getText().toString());
        if(matcher.find()){
            return true;
        }
        return  false;
    }

    public void cancel(View view) {
        finish();
    }
}