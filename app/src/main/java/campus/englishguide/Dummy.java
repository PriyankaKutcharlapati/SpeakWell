package campus.englishguide;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;



public class Dummy {

    public static String s;
    final static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "en-pos-maxent.bin" ;
    Context mContext;

    public Dummy(Context context)  {
        this.mContext = context;
        try {
            InputStream inputStream = null;

            inputStream = new FileInputStream(path);

            POSModel model = null;

            model = new POSModel(inputStream);




            POSTaggerME tagger = new POSTaggerME(model);

            String sentence = "I am buying it";


            //Tokenizing the sentence using WhitespaceTokenizer class
            WhitespaceTokenizer whitespaceTokenizer= WhitespaceTokenizer.INSTANCE;
            String[] tokens = whitespaceTokenizer.tokenize(sentence);


            //Generating tags
            String[] tags = tagger.tag(tokens);


            //Instantiating the POSSample class
            POSSample sample = new POSSample(tokens, tags);

            s = sample.toString();
            System.out.println(s);

        } catch(Exception e)    {
            e.printStackTrace();
        }
    }

    public String partsSpeech() throws Exception    {


        InputStream inputStream = null;

        inputStream = mContext.getAssets().open("enposmaxent.bin");

        POSModel model = null;

        model = new POSModel(inputStream);




        POSTaggerME tagger = new POSTaggerME(model);

        String sentence = "I am buying it";


        //Tokenizing the sentence using WhitespaceTokenizer class
        WhitespaceTokenizer whitespaceTokenizer= WhitespaceTokenizer.INSTANCE;
        String[] tokens = whitespaceTokenizer.tokenize(sentence);


        //Generating tags
        String[] tags = tagger.tag(tokens);


        //Instantiating the POSSample class
        POSSample sample = new POSSample(tokens, tags);

        s = sample.toString();
        System.out.println(sample.toString());
        return s;

    }

    public static void main(String args[]) throws Exception{

       //String x = d.partsSpeech();
       // System.out.println(x);
}




}
