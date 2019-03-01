package campus.englishguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sai vaishnavi on 05-01-2019.
 */
public class Login extends AppCompatActivity {
    TextView textView;
    RelativeLayout layout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        textView = (TextView)findViewById(R.id.newuser);
        layout = (RelativeLayout) findViewById(R.id.layoutlogin);
        layout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this,Signin.class);
                startActivity(intent);
            }
        });
    }
    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    public void loginNavigate(View view) {
       // TextView tV = (TextView)findViewById(R.id.textView);
        //TextView tV2 = (TextView) findViewById(R.id.textView2);
        EditText tV = (EditText)findViewById(R.id.textView);
        EditText tV2 = (EditText)findViewById(R.id.textView2);
        String tv = tV.getText().toString();
        String tv2 = tV2.getText().toString();
        SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
        prefs.edit().putString("username", tv).commit();
        if(tv.contentEquals(tv2)) {
            if(tv.contentEquals("") || tv2.contentEquals("")) {
                Toast.makeText(Login.this, "Enter the details", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(Login.this, Menu.class);
                startActivity(intent);
            }
        }
        else {
            Toast.makeText(Login.this, "Enter valid password", Toast.LENGTH_SHORT).show();
        }
    }
}
