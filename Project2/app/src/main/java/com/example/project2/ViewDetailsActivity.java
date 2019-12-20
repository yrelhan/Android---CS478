package com.example.project2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ViewDetailsActivity extends Activity {

    ImageView imageView;
    TextView modelPrice;
    TextView modelRam;
    TextView modelStorage;
    TextView modelYear;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phonedetails);
        Intent intent = this.getIntent();
        final Bundle bundle = getIntent().getExtras();
        imageView = (ImageView) findViewById(R.id.imageView2);
        modelPrice = (TextView) findViewById(R.id.price);
        modelStorage = (TextView) findViewById(R.id.storage);
        modelRam = (TextView) findViewById(R.id.ram);
        modelYear = (TextView) findViewById(R.id.year);

        if(bundle!=null){
            imageView.setImageResource(bundle.getInt("image"));
            modelPrice.setText(bundle.getString("price"));
            modelRam.setText(bundle.getString("ram"));
            modelStorage.setText(bundle.getString("storage"));
            modelYear.setText(bundle.getString("year"));

        }

    }
}
