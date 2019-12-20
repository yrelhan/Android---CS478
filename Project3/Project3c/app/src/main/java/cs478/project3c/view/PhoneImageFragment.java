package cs478.project3c.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cs478.project3c.R;

public class PhoneImageFragment extends Fragment {

    private ImageView phoneImage;
    private int imgResource = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone_image, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Restore previous selection
        phoneImage = getView().findViewById(R.id.phoneImage);
        if (imgResource != -1)
            phoneImage.setImageResource(imgResource);
    }

    public void setPhoneImage(int imgResource) {
        // Set the new image
        this.imgResource = imgResource;
        if (phoneImage != null)
            phoneImage.setImageResource(imgResource);
    }

}
