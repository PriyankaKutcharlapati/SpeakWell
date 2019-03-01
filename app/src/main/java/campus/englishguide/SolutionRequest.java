package campus.englishguide;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;

public class SolutionRequest extends AsyncTask<String,String,String> {

    final String app_key = "9JMF2Y56";
    String myurl;
    String topic,word,partsOf;
    Context context;
    StringBuilder stringBuilder;
    BufferedReader reader;
    public static String def;


    SolutionRequest(Context context)    {
        this.context = context;
        System.out.println("In Async1");
    }
    //ComputerQuestion cq = new ComputerQuestion();

    @Override
    protected String doInBackground(String... params) {
        myurl = params[0];
        System.out.println("In Background1");
        try {
            System.out.println("In try1");
            URL url = new URL(myurl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept", "application/json");
            // urlConnection.setRequestProperty("app_id",app_id);
            urlConnection.setRequestProperty("app_key", app_key);
            System.out.println(" After url");
            // read the output from the server
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            stringBuilder = new StringBuilder();
            System.out.println(" After Buffer");
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            //System.out.println(stringBuilder);
            JSONObject jsonObj = new JSONObject(stringBuilder.toString());
            JSONArray arr = jsonObj.getJSONArray("matches");
            if (arr.length() > 0) {
                JSONObject obj = arr.getJSONObject(0);
                //def = pronounceObj.getString("phoneticSpelling");
                def = obj.getString("message");
                System.out.println(def);
                //def = "Wrong";

            } else {
                int r = checkResult();
                if(r == 1)  {
                    def = "correct";
                }
                else    {
                    def = "Wrong";
                }

            }
            return def;

        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
            return e.toString();

        }
    }

    private int checkResult() throws Exception{
        topic = ComputerQuestion.val2;
        System.out.println("topic"+topic);
        word = ComputerQuestion.val1;
        System.out.println("word"+word);
        partsOf = ComputerQuestion.parts;
        //String[] splitString = partsOf.split("_");
        System.out.println("parts"+partsOf);
        requestApiButtonClick();
        String verb = VerbBaseForm.base;
        System.out.println("Base form" + verb);
        String answer = ComputerQuestion.ans.getText().toString();
        System.out.println("answer"+answer);
        switch (topic)  {
            case "simple present":
                System.out.println("simple present");
                if((answer.contains(word)) &&(partsOf.contains("VBP") || partsOf.contains("VBZ")))  {
                    return 1;
                }
                return 0;
            case "present continuous":
                System.out.println("present continuous");
                if((answer.contains(word)) && ((answer.contains("am")) || (answer.contains("is")) || (answer.contains("are")) ) &&(partsOf.contains("VBG")))  {
                    return 1;
                }
                return 0;
            case "present perfect":
                System.out.println("present perfect");
                if((answer.contains(word)) && ((answer.contains("have")) || (answer.contains("has")) ) &&(partsOf.contains("VBN")))  {
                    return 1;
                }
                return 0;
            case "present perfect continuous":
                if((answer.contains(word)) && ((answer.contains("have")) || (answer.contains("has"))) && (answer.contains("been")) &&(partsOf.contains("VBN")))  {
                    return 1;
                }
                return 0;
            default:
                System.out.println("no match");

        }
        return 0;
    }


    @Override
    protected void onPostExecute(String s) {
        System.out.println("oyyyy");
        super.onPostExecute(s);
        System.out.println(def);
        ComputerQuestion.result.setText(def);
    }

    public void requestApiButtonClick()    {
        VerbBaseForm vbf = new VerbBaseForm(context);
        vbf.execute(baseEntries());
    }

    private String baseEntries() {

        final String language = "en";
        final String word = "swimming";
        System.out.println(word);
        //Toast.makeText(this,word,Toast.LENGTH_SHORT).show();
        final String word_id = word.toLowerCase(); //word id is case sensitive and lowercase is required

        //intent1 = getIntent();
        //String str = intent1.getStringExtra("result");
        //t5.setText(MyDictionaryRequest.def);
        //t6.setText(MyDictionaryRequest.def1);
        return "https://od-api.oxforddictionaries.com:443/api/v1/inflections/" + language + "/" + word_id;


    }
}
