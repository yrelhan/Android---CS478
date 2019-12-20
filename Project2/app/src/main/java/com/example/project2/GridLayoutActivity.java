package com.example.project2;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridLayoutActivity extends Activity {

    public static String EXTRA_RES_ID = "EXTRA_RES_ID";
    private ImageAdapter imageAdapter;
    private ListView listView;

    ArrayList<String> modelNames = new ArrayList<String>(Arrays.asList("Samsung Galaxy S10","Samsung Galaxy S10 Plus","Samsung Galaxy S9+","iPhone 11","iPhone 11 Pro Max","iPhone XS","iPhone XS Max","OnePlus 7 Pro","Google Pixel 3A"));
    ArrayList<Integer> modelImages = new ArrayList<Integer>(Arrays.asList(R.drawable.samsungs10,R.drawable.samsungs10plus,R.drawable.galaxys9plus,R.drawable.iphone11,R.drawable.iphone11promax,R.drawable.iphonexs,R.drawable.iphonexsmax,R.drawable.oneplus7pro,R.drawable.googlepixel3a));
    ArrayList<String> modelWebsite = new ArrayList<String>(Arrays.asList("https://www.samsung.com/us/mobile/phones/","https://www.samsung.com/us/mobile/phones/","https://www.samsung.com/us/mobile/phones/","https://www.apple.com/iphone/","https://www.apple.com/iphone/","https://www.apple.com/iphone/","https://www.apple.com/iphone/","https://www.oneplus.com/7pro#/","https://store.google.com/product/pixel_3a"));
    ArrayList<String> modelPrice = new ArrayList<>(Arrays.asList("1000$","1200$","900$","1000$","1100$","950$","1000$","600$","500$"));
    ArrayList<String> modelDescription = new ArrayList<>(Arrays.asList("Samsung's 2019 Flagship!","Samsung's 2019 Flagship, and more!","Samsung's 2018 Flagship","Apple's 2019 Flagship","Apple's 2019 Flagship, and more!","Apple's 2018 Flaghsip","Apple's 2018 Flagship, and more!","OnePlus 2019 Flagship","Google's 2019 Flagship"));
    ArrayList<String> modelRam = new ArrayList<>(Arrays.asList("8GB","8GB","8GB","4GB","4GB","4GB","4GB","8GB","4GB"));
    ArrayList<String> modelYearReleased = new ArrayList<>(Arrays.asList("2019","2019","2018","2019","2019","2018","2018","2019","2018"));
    ArrayList<String> modelStorage = new ArrayList<>(Arrays.asList("256GB","256GB","256GB","256GB","256GB","256GB","256GB","256GB","128GB"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        listView = (ListView) findViewById(R.id.phonelist);
        imageAdapter = new ImageAdapter(this,modelNames,modelImages,modelDescription);
        listView.setAdapter(imageAdapter);

        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(GridLayoutActivity.this,ViewImageActivity.class);
                intent.putExtra("modelName",modelNames.get(position));
                intent.putExtra("modelImage",modelImages.get(position));
                intent.putExtra("modelWebsite",modelWebsite.get(position));
                GridLayoutActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        menu.setHeaderTitle("Your choices are:");
        menu.add(0,v.getId(),0,"See the big picture");
        menu.add(0,v.getId(),0,"Go to the webpage");
        menu.add(0,v.getId(),0,"See model details");

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        if(item.getTitle().equals("See the big picture")){
            showBigPicture(info.position);
        }else if(item.getTitle().equals("Go to the webpage")){
            goToWebPage(info.position);
        }else if(item.getTitle().equals("See model details")){
            //showDeatils(info.position,modelImages,modelPrice,modelPrice,modelYearReleased,modelRam,modelStorage);
            showDetails(info.position);
        }
        return true;
    }

//    private void showDeatils(int position, ArrayList<Integer> modelImages, ArrayList<String> modelPrice, ArrayList<String> price, ArrayList<String> modelYearReleased, ArrayList<String> modelRam, ArrayList<String> modelStorage) {
//        imageAdapter = new ImageAdapter(this,modelNames,modelImages,modelDescription);
//        listView.setAdapter(imageAdapter);
//    }

    public void showDetails(int position){
        String price = modelPrice.get(position);
        String ram = modelRam.get(position);
        String year = modelYearReleased.get(position);
        String storage = modelStorage.get(position);
        Integer image = modelImages.get(position);
        Intent intent = new Intent(GridLayoutActivity.this,ViewDetailsActivity.class);
        intent.putExtra("year",year);
        intent.putExtra("storage",storage);
        intent.putExtra("ram",ram);
        intent.putExtra("image",image);
        intent.putExtra("price",price);
        startActivity(intent);
    }


    public void showBigPicture(int position){
        Intent intent = new Intent(GridLayoutActivity.this,ViewImageActivity.class);
        intent.putExtra("modelImage",modelImages.get(position));
        intent.putExtra("modelWebsite",modelWebsite.get(position));
        startActivity(intent);
    }

    public void goToWebPage(int position){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(modelWebsite.get(position)));
        startActivity(intent);
    }
}
