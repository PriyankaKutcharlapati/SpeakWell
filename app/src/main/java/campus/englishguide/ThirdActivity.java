package campus.englishguide;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class ThirdActivity extends AppCompatActivity {
    //ImageView imageview;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    Intent intent = new Intent(ThirdActivity.this,FirstActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.voice:
                    Intent intent1 = new Intent(ThirdActivity.this,SecondActivity.class);
                    startActivity(intent1);
                    return true;
                case R.id.game:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        //imageview = (ImageView) findViewById(R.id.imageView6);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    public void vsComputer(View view) {
        Intent intent = new Intent(ThirdActivity.this,GameComputer.class);
        startActivity(intent);
    }
}
