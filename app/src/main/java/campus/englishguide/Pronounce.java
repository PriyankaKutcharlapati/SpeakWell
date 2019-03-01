package campus.englishguide;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by sai vaishnavi on 04-01-2019.
 */
public class Pronounce extends AppCompatActivity {
    TextView op;
    public static String opt;
    //TextView t5;
    String url;
    Intent intent1;
    ImageView forward;
    public static ImageView speaker;
    public static TextView t6;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pronounce);
        op = (TextView) findViewById(R.id.output);
        forward = (ImageView) findViewById(R.id.image);
        speaker = (ImageView) findViewById(R.id.button4);
        forward.setVisibility(View.GONE);
        speaker.setVisibility(View.GONE);
        //t5 = (TextView) findViewById(R.id.t5);
        t6 = (TextView) findViewById(R.id.t6);

    }
    public void getSpeechInput(View view)   {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent,10);
        } else  {
            Toast.makeText(this, "Your device don't support speech Input", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)    {
            case 10:
                if(resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    op.setText(result.get(0));
                    opt = op.getText().toString();
                    forward.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
    public void requestApiButtonClick(View v)    {
        //Toast.makeText(this,"you clicked", Toast.LENGTH_SHORT).show();
        MyDictionaryRequest myDictionaryRequest = new MyDictionaryRequest(this);
        //url = dictionaryEntries();
        myDictionaryRequest.execute(dictionaryEntries());
    }

    private String dictionaryEntries() {

        final String language = "en";
        final String word = opt;
        System.out.println(word);
        //Toast.makeText(this,word,Toast.LENGTH_SHORT).show();
        final String word_id = word.toLowerCase(); //word id is case sensitive and lowercase is required

        //intent1 = getIntent();
        //String str = intent1.getStringExtra("result");
        //t5.setText(MyDictionaryRequest.def);
        //t6.setText(MyDictionaryRequest.def1);
        speaker.setVisibility(View.VISIBLE);
        return "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id;


    }
    public void playAudio(View view) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(MyDictionaryRequest.def);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        mediaPlayer.start();
        Toast.makeText(this,"audio",Toast.LENGTH_SHORT).show();
    }
}
