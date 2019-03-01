package campus.englishguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class GameComputer extends AppCompatActivity {
    Spinner time;
    public static EditText t;
    public static int secs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_computer);

        time = (Spinner) findViewById(R.id.spin);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(GameComputer.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.seconds));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time.setAdapter(arrayAdapter);

        String selectedSecs = time.getSelectedItem().toString();
        System.out.println(selectedSecs);
        String[] splitted = selectedSecs.split("\\s+");
        secs = Integer.parseInt(splitted[0]);

    }
    public void playComputer(View view) {
        Intent intent = new Intent(GameComputer.this,ComputerQuestion.class);
        startActivity(intent);
    }
}
