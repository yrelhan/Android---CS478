package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button enterAddress;
    private String streetAddress;
    private Button mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterAddress=(Button)findViewById(R.id.address_button);

        enterAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(MainActivity.this,Main2Activity.class);
                startActivityForResult(newIntent,99);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){

        if(requestCode==99){
            mapButton=(Button)findViewById(R.id.map_button);
            Log.i("OnactivityResult", "onActivityResult: success");
            if(resultCode==Activity.RESULT_OK){
                streetAddress=data.getStringExtra("address");
                mapButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String encodedStreetAddress= Uri.encode(streetAddress);
                        //String map = "http://maps.google.co.in/maps?q=" + encodedStreetAddress;
                        Uri map = Uri.parse("geo:0,0?q=" + encodedStreetAddress);
                        Intent i = new Intent(Intent.ACTION_VIEW, map);
                        //Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                        startActivity(i);

                    }
                });
            }else if(resultCode==Activity.RESULT_CANCELED && data==null){
                mapButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this,"No Street Address entered",Toast.LENGTH_LONG).show();
                    }
                });
            }

        }

    }
}
