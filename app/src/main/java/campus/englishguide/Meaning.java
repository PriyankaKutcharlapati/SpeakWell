package campus.englishguide;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Meaning extends AsyncTask<String,Integer,String> {

    final String app_id = "68fe5241";
    final String app_key = "a4ffed84103cb6323d519c9b2b1158e1";
    String myurl;
    Context context;
    public static String def;
    public static String def1;

    Meaning(Context context)    {
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

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
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
        System.out.println("In method");
        super.onPostExecute(s);
        String path;

        try {
            System.out.println("Try Block");
            JSONObject js = new JSONObject(s);
            JSONArray results = js.getJSONArray("results");

            JSONObject lEntries = results.getJSONObject(0);
            JSONArray laArray = lEntries.getJSONArray("lexicalEntries");

            JSONObject entries = laArray.getJSONObject(0);
            JSONArray e = entries.getJSONArray("entries");

            JSONObject jsonObject = e.getJSONObject(0);
            JSONArray sensesArray = jsonObject.getJSONArray("senses");

            JSONObject d  = sensesArray.getJSONObject(0);
            JSONArray de = d.getJSONArray("definitions");
            def1 = de.getString(0);
            FirstActivity.mean.setText(def1);
            //def = pronounceObj.getString("audioFile");

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



