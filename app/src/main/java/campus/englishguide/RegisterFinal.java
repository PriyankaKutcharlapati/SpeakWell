package campus.englishguide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterFinal extends AppCompatActivity {
    TextView tv1,tv2,tv3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_final);
        tv1 = (TextView)findViewById(R.id.textViewsignin);
        tv2 = (TextView)findViewById(R.id.textView1signin);
        tv3 = (TextView)findViewById(R.id.textView2signin);
    }
    public void signinNavigate(View view) {
        String tV1 = tv1.getText().toString();
        String tV2 = tv2.getText().toString();
        String tV3 = tv3.getText().toString();
        SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
        prefs.edit().putString("username", tV1).commit();

        if(tV1.contentEquals("") || tV2.contentEquals("") || tV3.contentEquals("")) {
            Toast.makeText(RegisterFinal.this, "Enter the details", Toast.LENGTH_SHORT).show();
        }

        else if(!tV1.contentEquals("") && !tV2.contentEquals("") && !tV3.contentEquals("")) {
            if(!tV2.contentEquals(tV3)) {
                Toast.makeText(RegisterFinal.this, "ReEnter Correct Password", Toast.LENGTH_SHORT).show();
            }
            if(tV1.contentEquals(tV2) && tV1.contentEquals(tV3)) {
                Intent intent = new Intent(RegisterFinal.this, FirstActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplication(), "Enter valid password", Toast.LENGTH_SHORT).show();
            }

        }




    }
}
