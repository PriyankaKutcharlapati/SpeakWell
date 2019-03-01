package campus.englishguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LoginFinal extends AppCompatActivity {
    TextView textView;
    RelativeLayout layout;
    DatabaseReference databaseReference;
    ArrayList<String> arrayList = new ArrayList<String>();
    Random ran;
    private static final int PER_LOGIN = 1000;

    FirebaseAuth mAuth;
    EditText phonen,codec;
    String codeSent;
    //Dummy d;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_final);


        //System.out.println("12345" + d.s);

        phonen = (EditText) findViewById(R.id.textView);
        codec = (EditText) findViewById(R.id.textView2);
        mAuth =FirebaseAuth.getInstance();
        //check();
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

                Intent intent = new Intent(LoginFinal.this,RegisterFinal.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonGet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCode();
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifySignInCode();
            }
        });

    }

    private void verifySignInCode() {

        String code = codec.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                           Intent intent = new Intent(getApplicationContext(),FirstActivity.class);
                           startActivity(intent);

                        } else {
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException)  {
                                Toast.makeText(getApplicationContext(),"Incoreect",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


    private void sendVerificationCode() {

        String phone = phonen.getText().toString();
        System.out.println(phone);
        if(phone.isEmpty() || phone.length() < 10) {
            phonen.setError("Required");
            phonen.requestFocus();
            return;
        }


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            System.out.println("m x sx");
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
            System.out.println(s);
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }
    };

    public void check()    {
        SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
        if(prefs.getBoolean("logged",false)){
            Intent intent = new Intent(this,LoginFinal.class);
            startActivity(intent);
        }
        else    {
            Intent intent = new Intent(this,FirstActivity.class);
            startActivity(intent);
        }
    }

    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void skipLogin(View view)    {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.child("username").getValue(String.class);
                addFun1(value);
                if(arrayList.size() == 7)   {
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
        Intent intent = new Intent(getApplicationContext(),FirstActivity.class);
        startActivity(intent);

    }
    private void showFun1() {
        ran = new Random();
        System.out.println(arrayList);
        int index = ran.nextInt(arrayList.size());
        String val = arrayList.get(index);
        SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
        prefs.edit().putString("username", val).commit();
        prefs.edit().putBoolean("hasLoggedIn",true).apply();

        //t8.setText(val);

    }

    private void addFun1(String value) {
        arrayList.add(value);

    }

    public void loginNavigate(View view) {
        // TextView tV = (TextView)findViewById(R.id.textView);
        //TextView tV2 = (TextView) findViewById(R.id.textView2);
        EditText tV = (EditText)findViewById(R.id.textView);
        //EditText tV2 = (EditText)findViewById(R.id.textView2);
        String tv = tV.getText().toString().trim();
        //String tv2 = tV2.getText().toString();
        SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
        prefs.edit().putString("username", tv).commit();
        if(tv.isEmpty() || tv.length() < 10)    {
            Toast.makeText(LoginFinal.this, " Valid Number required", Toast.LENGTH_SHORT).show();
            return;
        }
        String phoneNumber = "+91" + tv;


        Intent intent = new Intent(LoginFinal.this,VerifyPhone.class);
        intent.putExtra("phonenumber",phoneNumber);
        startActivity(intent);

        /*if(tv.contentEquals(tv2)) {
            if(tv.contentEquals("") || tv2.contentEquals("")) {
                Toast.makeText(LoginFinal.this, "Enter the details", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(LoginFinal.this, FirstActivity.class);
                startActivity(intent);
            }
        }
        else {
            Toast.makeText(LoginFinal.this, "Enter valid password", Toast.LENGTH_SHORT).show();
        }*/
    }
}

