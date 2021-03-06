package com.calgo.pathfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    static final String DBG = "CALGO";

    View decorView;
    int  uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;

    private CanvasView cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Hide the status bar.
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void setCv() {
        if (cv != null)
            return;

        cv = (CanvasView) findViewById(R.id.myCanvas);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setCv();
        if (cv != null)
            cv.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Hide the status bar.
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(uiOptions);

        setCv();
        if (cv != null)
            cv.onResume();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        // Hide the status bar.
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
