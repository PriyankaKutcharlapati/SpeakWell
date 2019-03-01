package campus.englishguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;


public class FirstActivity extends AppCompatActivity {

    private TextView mTextMessage;
    DatabaseReference databaseReference;
    String id = "1";
    String username;
    TextView t12;
    String val;
    public static TextView mean;
    Random ran;
    TextView instant;
    ImageView arrow;
    ArrayList<String> arrayList = new ArrayList<String>();




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:

                    return true;
                case R.id.voice:
                    Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.game:
                    Intent intent1 = new Intent(FirstActivity.this,ThirdActivity.class);
                    startActivity(intent1);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        instant = (TextView) findViewById(R.id.textView13);
        mean = (TextView) findViewById(R.id.textView14);
        arrow = (ImageView) findViewById(R.id.imageView);
        arrow.setVisibility(View.GONE);
        t12 = (TextView) findViewById(R.id.t12);

        SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
        username = prefs.getString("username", "UNKNOWN");
        t12.setText("Hello " + username);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("words");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.child("word").getValue(String.class);
                addFun(value);
                if(arrayList.size() == 10)  {
                    showFun();
                }

                System.out.println(value);
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

    public void requestApiButtonClick() {
        Meaning meaning = new Meaning(this);
        meaning.execute(entries());
    }

    private String entries() {

        final String language = "en";
        final String word = val;
        System.out.println(word);
        //Toast.makeText(this,word,Toast.LENGTH_SHORT).show();
        final String word_id = word.toLowerCase(); //word id is case sensitive and lowercase is required

        //intent1 = getIntent();
        //String str = intent1.getStringExtra("result");
        //t5.setText(MyDictionaryRequest.def);
        System.out.println(Meaning.def1);
        //mean.setText(MyDictionaryRequest.def1);

        return "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id;


    }

    private void showFun() {
        System.out.println(arrayList);
        ran = new Random();
        int index = ran.nextInt(arrayList.size());
        val = arrayList.get(index);
        instant.setText(val);
  //      arrow.setVisibility(View.VISIBLE);
        requestApiButtonClick();
        //ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.textView7,arrayList);
        //ListView listView = (ListView)findViewById(R.id.list);
        //listView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
    }

    private void addFun(String value) {
        arrayList.add(value);

    }



}


