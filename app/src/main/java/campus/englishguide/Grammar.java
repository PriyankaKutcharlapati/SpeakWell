package campus.englishguide;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;

public class Grammar extends AppCompatActivity {
    TextView op;
    String opt;
    public static ImageView ans;
    public static TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar);
        op = (TextView) findViewById(R.id.output);
        text = (TextView) findViewById(R.id.textView5);
        ans = (ImageView) findViewById(R.id.button5);
        ans.setVisibility(View.GONE);
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
                    ans.setVisibility(View.VISIBLE);
                    opt = op.getText().toString();
                }
                break;
        }
    }

    public void requestApiButtonClick1(View v)    {
        GrammarRequest.def = "";
        //Toast.makeText(this,"you clicked", Toast.LENGTH_SHORT).show();
        GrammarRequest grammarRequest = new GrammarRequest(this);
        //grammarRequest.execute();
       grammarRequest.execute(grammarEntries());
    }

    private String grammarEntries() {

        final String language = "en-US";
        final String text = opt;
        String[] text_arr = text.split("\\s+");
        int x = text_arr.length;
        System.out.println(text);
        StringBuffer s = new StringBuffer();
        char ch = ' ';
        for (int i = 0; i < text_arr[0].length(); i++) {

            // If previous character is space and current
            // character is not space then it shows that
            // current letter is the starting of the word
            if (ch == ' ' && text_arr[0].charAt(i) != ' ')
                s.append(Character.toUpperCase(text_arr[0].charAt(i)));
            else
                s.append(text_arr[0].charAt(i));
            ch = text_arr[0].charAt(i);
        }
        String finalurl = s.toString();
        for(int i = 1;i < x;i++)    {
            finalurl += "%20" + text_arr[i];
        }
        System.out.println(finalurl);
        Log.d("one","Hai");

       //Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
        //final String word_id = word.toLowerCase(); //word id is case sensitive and lowercase is required

        //intent1 = getIntent();
        //String str = intent1.getStringExtra("result");
        //t5.setText(MyDictionaryRequest.def);
        //return "http://api.grammarbot.io/v2/check?api_key=9JMF2Y56&text=" + text + "&language=" + language;
        //System.out.println("http://api.grammarbot.io/v2/check?api_key=9JMF2Y56&text=" + finalurl + "&language=en-US");
        return "http://api.grammarbot.io/v2/check?api_key=9JMF2Y56&text=" + finalurl + "&language=en-US";
        //return "http://api.grammarbot.io/v2/check?api_key=9JMF2Y56&text=I%20can%27t%20remember%20how%20to%20go%20their.&language=en-US";

    }

}
