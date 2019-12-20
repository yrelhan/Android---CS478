package cs478.project3c.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cs478.project3c.R;
import cs478.project3c.model.Phone;
import cs478.project3c.model.PhoneDatabase;

public class PhoneListFragment extends Fragment {

    public interface OnPhoneListItemSelectedListener {
        void onPhoneListItemSelected(int selected);
    }

    private int selection = -1;
    private ListView phoneList;
    private OnPhoneListItemSelectedListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) throws ClassCastException {
        super.onAttach(context);
        this.listener = (OnPhoneListItemSelectedListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set list adapter and item click listener
        phoneList = getView().findViewById(R.id.phoneList);
        phoneList.setAdapter(new ArrayAdapter<Phone>(getContext(),
                R.layout.phone_list_item, PhoneDatabase.ALL_PHONES));
        phoneList.setOnItemClickListener((parent, view, position, id) -> {
            selection = position;
            this.listener.onPhoneListItemSelected(selection);
        });

        // Restore previous selection
        if (selection > 0) {
            phoneList.setItemChecked(selection, true);
            this.listener.onPhoneListItemSelected(selection);
        }
    }

    public Phone getSelectedItem() {
        return (Phone) phoneList.getItemAtPosition(phoneList.getCheckedItemPosition());
    }

}
