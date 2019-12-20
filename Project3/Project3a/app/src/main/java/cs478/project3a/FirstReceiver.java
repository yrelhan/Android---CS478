package cs478.project3a;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class FirstReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Start PhoneWebsiteActivity
        Intent phoneWebsiteIntent = new Intent(context, PhoneWebsiteActivity.class);
        phoneWebsiteIntent.putExtra("phoneUrl", intent.getStringExtra("phoneUrl"));
        context.startActivity(phoneWebsiteIntent);
    }

}
