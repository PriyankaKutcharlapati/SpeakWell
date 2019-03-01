package campus.englishguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ExercisePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_page);
        //row1.setCompoundDrawablePadding(15);
    }

    public void grammarFunction(View view)  {
        Intent intent = new Intent(ExercisePage.this,Grammar.class);
        startActivity(intent);
    }

    public void pronounceFunction(View view) {
        Intent intent = new Intent(ExercisePage.this,Pronounce.class);
        startActivity(intent);
    }

    public void chatFunction(View view) {
        Intent intent = new Intent(ExercisePage.this,Chat.class);
        startActivity(intent);
    }
}
