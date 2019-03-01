package campus.englishguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);
        Thread x = new Thread(){
            public void run() {
                try{
                    sleep(1500);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                finally {
                    Intent i = new Intent(SplashPage.this,LoginFinal.class);
                    startActivity(i);
                }
            }
        };
        x.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
