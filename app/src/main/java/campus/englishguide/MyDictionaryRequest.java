package campus.englishguide;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MyDictionaryRequest extends AsyncTask<String,Integer,String> {

    final String app_id = "68fe5241";
    final String app_key = "a4ffed84103cb6323d519c9b2b1158e1";
    String myurl;
    Context context;
    public static String def;
    public static String def1;
    MyDictionaryRequest(Context context)    {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        myurl = params[0];
        try {
            URL url = new URL(myurl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("app_id",app_id);
            urlConnection.setRequestProperty("app_key",app_key);
            //InputStream s = urlConnection.getErrorStream();


            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            System.out.println("12345" + reader);
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            return stringBuilder.toString();

        }
        catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        String path;
        //System.out.println(s);

        try {
            System.out.println("Try Block");
            String low = Pronounce.opt.toLowerCase();
            String str = "java.io.FileNotFoundException: https://od-api.oxforddictionaries.com:443/api/v1/entries/en/" + low;
            System.out.println(str);
            if(s.equals(str))   {
                System.out.println("mabfjabf");
                def1 = "No Specific pronunciation";
                Pronounce.t6.setText(def1);
                Pronounce.speaker.setVisibility(View.GONE);
            }
            else {
                System.out.println("Haiiii");
                JSONObject js = new JSONObject(s);
                JSONArray results = js.getJSONArray("results");

                JSONObject lEntries = results.getJSONObject(0);
                JSONArray laArray = lEntries.getJSONArray("lexicalEntries");

                JSONObject entries = laArray.getJSONObject(0);
                JSONArray e = entries.getJSONArray("entries");

                //JSONObject jsonObject = e.getJSONObject(0);
                JSONArray p = entries.getJSONArray("pronunciations");
                JSONObject pronounceObj = p.getJSONObject(0);
                def1 = pronounceObj.getString("phoneticSpelling");
                System.out.println(def1);
                Pronounce.t6.setText(def1);
                Pronounce.speaker.setVisibility(View.VISIBLE);

                def = pronounceObj.getString("audioFile");
            }


            //Toast.makeText(context,def,Toast.LENGTH_SHORT).show();


            //JSONObject jso = p.getJSONObject(0);
            //JSONArray n = jso.getJSONArray("dialects");

            //def = jso.getString("phoneticNotation");
            //Toast.makeText(context,def,Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(context,Pronounce.class);
            //intent.putExtra("result",def);
            //context.startActivity(intent);

        }catch(JSONException e)    {
           e.printStackTrace();
        }



        //System.out.println(s);
    }

}
