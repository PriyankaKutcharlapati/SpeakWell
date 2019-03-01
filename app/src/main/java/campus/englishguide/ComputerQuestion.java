package campus.englishguide;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;

//import static campus.englishguide.GameComputer.time;

public class ComputerQuestion extends AppCompatActivity {

    DatabaseReference databaseReference1,databaseReference2;
    ArrayList<String> arrayList1 = new ArrayList<String>();
    ArrayList<String> arrayList2 = new ArrayList<String>();
    TextView t8,t9;
    String question;
    Random ran;
    public static String parts;
    Context mContext;
    public static EditText ans;
    public static TextView result;
    public static  String val1;
    public static String val2,user_ans;
    static int count = 0;
    public String text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_question);
        t8 = (TextView) findViewById(R.id.textView8);
        t9 = (TextView) findViewById(R.id.t9);
        result = (TextView) findViewById(R.id.result);

        int t1 = GameComputer.secs;

        ans = (EditText) findViewById(R.id.answer);
        user_ans = ans.getText().toString();
        System.out.println("user ans"+ user_ans);

       // parts = partsOfSpeech();
       //System.out.println("xyzz" + parts);

        CountDownTimer countDownTimer = new CountDownTimer(t1 * 1000,1000) {
            @Override
            public void onTick(long l) {
                int x = (int) l / 1000;
                t9.setText(String.valueOf(x));
            }

            @Override
            public void onFinish() {
                t9.setText("Done");
            }
        }.start();

        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("verbs");
        databaseReference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //Log.e(dataSnapshot.getKey(),dataSnapshot.getChildrenCount() + "");
                String value = dataSnapshot.child("verb").getValue(String.class);
                addFun1(value);
                if(arrayList1.size() == 20)  {
                    showFun1();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("topics");
        databaseReference2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.child("topic").getValue(String.class);
                addFun2(value);
                if(arrayList2.size() == 4)  {
                    showFun2();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void requestApiButtonClick(View view) {

       // SolutionRequest.def = "";
        System.out.println("Vyshu1");
        SolutionRequest solutionRequest = new SolutionRequest(this);
        solutionRequest.execute(grammarEntries());
    }

    private String grammarEntries() {
        System.out.println("Vyshu12");
        final String language = "en-US";
        text = ans.getText().toString();
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
        parts = partsOfSpeech();
        return "http://api.grammarbot.io/v2/check?api_key=9JMF2Y56&text=" + finalurl + "&language=en-US";


    }


    private void showFun1() {
        ran = new Random();
        System.out.println(arrayList1);
        int index = ran.nextInt(arrayList1.size());
        val1 = arrayList1.get(index);
        System.out.println(val1);
        question = val1;
        question += "+";
    }

    private void addFun1(String value) {
        arrayList1.add(value);
    }

    private void showFun2() {
        ran = new Random();
        System.out.println(arrayList2);
        int index = ran.nextInt(arrayList2.size());
        val2 = arrayList2.get(index);
        question += val2;
        t8.setText(question);
    }

    private void addFun2(String value) {
        arrayList2.add(value);
    }


    public void nextQuestion(View view) {
        if(count < 20) {
            Intent intent = new Intent(getApplicationContext(),ComputerQuestion.class);
            count++;
            startActivity(intent);
        }
        else    {
            Intent intent = new Intent(getApplicationContext(),ComputerResult.class);
            startActivity(intent);
        }

    }

    public String partsOfSpeech() {
        System.out.println("In parts of speech");

        try {

            //InputStream inputStream = getResources().openRawResource(getResources().getIdentifier("enposmaxent","raw", getPackageName()));
           //InputStream inputStream = new FileInputStream("C:/Users/sai vaishnavi/Downloads/enposmaxent.bin");

            System.setProperty("org.xml.sax.driver", "org.xmlpull.v1.sax2.Driver");
            AssetFileDescriptor fileDescriptor =
                    this.getAssets().openFd("enposmaxent.bin");
            FileInputStream inputStream = fileDescriptor.createInputStream();




            POSModel model = new POSModel(inputStream);


            POSTaggerME tagger = new POSTaggerME(model);

            String sentence = text;

            System.out.println("In method" + sentence);


            //Tokenizing the sentence using WhitespaceTokenizer class
            WhitespaceTokenizer whitespaceTokenizer = WhitespaceTokenizer.INSTANCE;
            String[] tokens = whitespaceTokenizer.tokenize(sentence);


            //Generating tags
            String[] tags = tagger.tag(tokens);


            //Instantiating the POSSample class
            POSSample sample = new POSSample(tokens, tags);

            System.out.println(sample.toString());

            return sample.toString();

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return "mistake";
    }
}
