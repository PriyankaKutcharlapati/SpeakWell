package campus.englishguide;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class VerbBaseForm extends AsyncTask<String,Integer,String> {

    final String app_id = "68fe5241";
    final String app_key = "a4ffed84103cb6323d519c9b2b1158e1";
    String myurl;
    Context context;
    public static String base;
    VerbBaseForm(Context context)    {
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


            System.out.println("Haiiii");
            JSONObject js = new JSONObject(s);
            JSONArray results = js.getJSONArray("results");

            JSONObject lEntries = results.getJSONObject(0);
            JSONArray laArray = lEntries.getJSONArray("lexicalEntries");

            JSONObject entries = laArray.getJSONObject(0);
            JSONArray e = entries.getJSONArray("inflectionOf");

            //JSONArray p = lEntries.getJSONArray("inflectionOf");
            JSONObject obj = e.getJSONObject(0);
            base = obj.getString("text");

            System.out.println(base);

        }catch(JSONException e)    {
            e.printStackTrace();
        }
    }

}
