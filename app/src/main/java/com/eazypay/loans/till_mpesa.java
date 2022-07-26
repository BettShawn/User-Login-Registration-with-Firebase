package com.eazypay.loans;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.BuildConfig;

public class till_mpesa extends AppCompatActivity {

    ProgressDialog dialog;

    int i = 0;
    int x = 0;
    private EditText passEditText;
    ImageButton imageButton;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tillmpesa);
//        TapdaqConfig config = Tapdaq.getInstance().config();
//
//        config.setUserSubjectToGdprStatus(STATUS.TRUE); //GDPR declare if user is in EU
//        config.setConsentStatus(STATUS.TRUE); //GDPR consent must be obtained from the user
//        config.setAgeRestrictedUserStatus(STATUS.FALSE); //Is user subject to COPPA or GDPR age restrictions
//
//        Tapdaq.getInstance().initialize(till_mpesa.this, getResources().getString(R.string.applicationid), getResources().getString(R.string.appkey), config, new TapdaqInitListener());
//        Tapdaq.getInstance().setUserSubjectToGDPR(till_mpesa.this, STATUS.TRUE);
//        Tapdaq.getInstance().setConsentGiven(till_mpesa.this, STATUS.FALSE);
//        Tapdaq.getInstance().setIsAgeRestrictedUser(till_mpesa.this, STATUS.UNKNOWN);
       // this.mpesatxt = (EditText) findViewById(R.id.mpesainp);
        this.passEditText = (EditText) findViewById(R.id.password1);
        //Random r = new Random();
        //int minNumber = 105;
        //int maxNumber = 135;
       //int randomNumber = r.nextInt((maxNumber-minNumber)+1)+minNumber;
       // TextView tv = (TextView) findViewById(R.id.text_view);
       // tv.setText(String.valueOf(randomNumber));

//        if (Tapdaq.getInstance().isVideoReady(till_mpesa.this,"default")) {
       // }

       // tv.setText("Dear Customer,\nOur company is committed to serving our customers based on trust and loyalty.\nFor that reason, Your information will be verified and once approved, you will get your loan immediately through the details you have shared.");
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void confirmmpesa(View arg0) {
        submitting();
    }

    private boolean isValidMpesa(String mpesa) {
        if (mpesa == null || mpesa.length() < 4) {
            return false;
        }
        return true;
    }

    public void submitting() {
        this.dialog = ProgressDialog.show(this, BuildConfig.FLAVOR, "Confirming Payment..", true);
        this.dialog.show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                String character = passEditText.getText ().toString ();
                String text = "BOB  STUDIO  IT SOLUTIONS";
                if (character.contains(text)) {
                    till_mpesa.this.dialog.dismiss();
                    Toast.makeText (till_mpesa.this, "Payment confirmed!", Toast.LENGTH_SHORT).show ();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }else {
                    mpesaerror();
                    till_mpesa.this.dialog.dismiss();
                }

            }
        }, 500);
    }

    public void mpesaerror() {
        SharedPreferences.Editor editor = getSharedPreferences("apply", MODE_PRIVATE).edit();
        editor.putString("apply", "confirm");
        editor.apply();
        this.passEditText.setError("INVALID CODE!!Failed try again after one hour");
      //  tv.setError("INVALID CODE!!Failed try again after one hour");

    }
//    @Override
//    protected void onDestroy() {
//        if (adbanner != null) {
//            adbanner.destroy(MainActivity.this);
//        }
//        super.onDestroy();
//    }



}
