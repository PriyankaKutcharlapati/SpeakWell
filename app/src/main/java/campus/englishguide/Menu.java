package campus.englishguide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by sai vaishnavi on 04-01-2019.
 */
public class Menu extends AppCompatActivity {

    private String username = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menupage);
        Toolbar toolBar = (Toolbar)findViewById(R.id.bar);
        setSupportActionBar(toolBar);

        SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
        username = prefs.getString("username", "UNKNOWN");

        TextView user = (TextView)findViewById(R.id.display);
        //if (user != null && !user.isEmpty()) {
        user.setText("Hi " + username);
        //}


    }
    public void pronounceFunction(View view) {
        Intent intent = new Intent(Menu.this,Pronounce.class);
        startActivity(intent);
    }

    public void grammarFunction(View view)  {
        Intent intent = new Intent(Menu.this,Grammar.class);
        startActivity(intent);
    }

    public void exerciseFunction(View view) {
        Intent intent = new Intent(Menu.this,ExercisePage.class);
        startActivity(intent);
    }

    public void chatFunction(View view) {
        Intent intent = new Intent(Menu.this,Chat.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(Menu.this,Settings.class);
                startActivity(intent);
                break;
            case R.id.collections:
                Intent intent1 = new Intent(Menu.this,Collections.class);
                startActivity(intent1);
                break;
            default:
                //unknown error
        }
        return super.onOptionsItemSelected(item);
    }
}
