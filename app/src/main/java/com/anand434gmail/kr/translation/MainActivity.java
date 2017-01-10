package com.anand434gmail.kr.translation;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onTranslateClick(View view) {
        EditText translateEditText = (EditText) findViewById(R.id.translationEditText);

        if(!isEmpty(translateEditText)){
            Toast.makeText(this ,"Getting Translation" ,Toast.LENGTH_LONG).show();

            new SaveTheFeed().execute();
        }else {
            Toast.makeText(this , "Enter Text To Translate" , Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length()==0;
    }

    private class SaveTheFeed extends AsyncTask<Void , Void , Void>{

        EditText translateEditText =(EditText) findViewById(R.id.translationEditText);

        String wordsToTranslate = translateEditText.getText().toString();

        //Holds Translations that will be displayed on String
        String result = "";

        @Override
        protected Void doInBackground(Void... voids) {

            HttpHandler sh = new HttpHandler();
            wordsToTranslate = wordsToTranslate.replace(" " , "+");
            String url = "http://newjustin.com/translateit.php?action=translations&english_words=";
            String jsonStr = sh.makeServiceCall(url +wordsToTranslate);

            Log.e(TAG, "Response from url: " + jsonStr);

            try{
                JSONObject jObject = new JSONObject(jsonStr);
                JSONArray jArray = jObject.getJSONArray("translations");
                outputTranslation(jArray);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            //put translation textVew

            TextView translationTextView = (TextView) findViewById(R.id.translatedTextView);
            translationTextView.setText(result);
        }

        private void outputTranslation(JSONArray jArray) {
            String[] languages = {"arabic" , "chinese" , "danish","dutch","french"
                                    ,"german","italian" ,"portuguese","russian","spanish"};


           try {
               for (int i = 0; i < jArray.length(); i++) {
                   JSONObject translationObject = jArray.getJSONObject(i);

                   result = result + languages[i] + " : " + translationObject.getString(languages[i])
                           + "\n";

               }
           }catch (JSONException e){
               e.printStackTrace();
           }


        }


    }
}
