package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private EditText streetAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        streetAddress = (EditText) findViewById(R.id.streetAdd);


        streetAddress.setOnEditorActionListener(new EditText.OnEditorActionListener(){

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if(i==KeyEvent.KEYCODE_ENTER || i== EditorInfo.IME_ACTION_DONE){

                    if(!streetAddress.getText().toString().isEmpty()){

                        Intent validAddress = new Intent();
                        validAddress.putExtra("address",streetAddress.getText().toString());
                        setResult(Activity.RESULT_OK,validAddress);
                        finish();
                        return true;
                    }else {

                        Intent notValidAddress = new Intent();
                        notValidAddress.putExtra("address", streetAddress.getText().toString());
                        setResult(Activity.RESULT_CANCELED, notValidAddress);
                        finish();
                        return true;

                    }
                }
                return false;
            }
        });

    }
}
