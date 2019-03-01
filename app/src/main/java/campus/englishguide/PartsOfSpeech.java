package campus.englishguide;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;

public class PartsOfSpeech extends AsyncTask<String,String,String> {

    public static String s;
    String sentence;

    @Override
    protected String doInBackground(String... strings) {
        //Loading Parts of speech-maxent model
        InputStream inputStream = null;
        try {
            inputStream = new
                    FileInputStream("C:/Users/sai vaishnavi/Downloads/enposmaxent.bin");

            POSModel model = new POSModel(inputStream);


            //Instantiating POSTaggerME class
            POSTaggerME tagger = new POSTaggerME(model);

            sentence = ComputerQuestion.user_ans;


            //Tokenizing the sentence using WhitespaceTokenizer class
            WhitespaceTokenizer whitespaceTokenizer= WhitespaceTokenizer.INSTANCE;
            String[] tokens = whitespaceTokenizer.tokenize(sentence);


            //Generating tags
            String[] tags = tagger.tag(tokens);


            //Instantiating the POSSample class
            POSSample sample = new POSSample(tokens, tags);

            s = sample.toString();
            System.out.println(sample.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }
}
