package campus.englishguide;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class GrammarRequest extends AsyncTask<String,String,String> {

    //final String app_id = "68fe5241";
    final String app_key = "9JMF2Y56";
    String myurl;
    Context context;
    StringBuilder stringBuilder;
    BufferedReader reader;
    public static String def;
    GrammarRequest(Context context)    {
        this.context = context;
        System.out.println("In Async");
    }


    @Override
    protected String doInBackground(String... params) {
        myurl = params[0];
        System.out.println("In Background");
        try {
            System.out.println("In try");
            URL url = new URL(myurl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
           // urlConnection.setRequestProperty("app_id",app_id);
            urlConnection.setRequestProperty("app_key",app_key);
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
            if(arr.length() > 0) {
                JSONObject obj = arr.getJSONObject(0);
                //def = pronounceObj.getString("phoneticSpelling");
                def = obj.getString("message");
                System.out.println(def);

            }
            else    {
                def = "Correct";
            }
            return def;

        }
        catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
            return e.toString();
        }

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println(def);
       // if(def != null) {
            Grammar.text.setText(def);
        /*} else {
            Grammar.text.setText("Correct");
        }*/





        //System.out.println(s);
    }

}
