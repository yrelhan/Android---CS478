package cs478.project3a;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class PhoneWebsiteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_website);

        // Retrieve the URL to load
        String url = getIntent().getStringExtra("phoneUrl");
        if (url == null) {
            finish();
        }

        // Set up the WebView
        WebView phoneWebView = findViewById(R.id.phoneWebView);
        phoneWebView.loadUrl(url);
        phoneWebView.getSettings().setLoadsImagesAutomatically(true);
        phoneWebView.getSettings().setJavaScriptEnabled(true);

        // Set up the progress bar
        ProgressBar pageLoadProgress = findViewById(R.id.pageLoadProgress);
        phoneWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                pageLoadProgress.setProgress(newProgress);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
