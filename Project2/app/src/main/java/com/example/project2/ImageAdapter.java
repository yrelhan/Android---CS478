package com.example.project2;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;


class ImageAdapter extends BaseAdapter {

    private Context context;
    ArrayList<String> phoneNames;
    ArrayList<Integer> phoneImages;
    ArrayList<String> phoneDescriptions;

    public ImageAdapter(@NonNull Context context, @NonNull ArrayList<String> phoneNames, ArrayList<Integer> phoneImages,ArrayList<String> phoneDescriptions) {
        this.context=context;
        this.phoneImages=phoneImages;
        this.phoneNames=phoneNames;
        this.phoneDescriptions=phoneDescriptions;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.phonelayout,parent,false);
            ImageView phonePicture = (ImageView) convertView.findViewById(R.id.phoneImage);
            TextView phoneName = (TextView) convertView.findViewById(R.id.phoneName);
            TextView phoneDesc = (TextView) convertView.findViewById(R.id.phonedesc);
            phoneName.setText(phoneNames.get(position));
            phonePicture.setImageResource(phoneImages.get(position));
            phoneDesc.setText(phoneDescriptions.get(position));
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return phoneNames.size();
    }

    @Override
    public Object getItem(int i) {
        return phoneNames.get(i);
    }

    @Override
    public long getItemId(int i) {
        return phoneImages.get(i);
    }

}
