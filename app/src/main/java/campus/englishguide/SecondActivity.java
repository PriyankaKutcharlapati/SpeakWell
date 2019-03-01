package campus.englishguide;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


public class SecondActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    Intent intent = new Intent(SecondActivity.this,FirstActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.voice:

                    return true;
                case R.id.game:
                    Intent intent1 = new Intent(SecondActivity.this,ThirdActivity.class);
                    startActivity(intent1);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }
    public void pronounceFunction(View view)    {
        Intent intent = new Intent(SecondActivity.this, Pronounce.class);
        startActivity(intent);
    }

    public void grammarFunction(View view)    {
        Intent intent = new Intent(SecondActivity.this, Grammar.class);
        startActivity(intent);
    }
}
