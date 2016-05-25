package com.spnv.dechipherer;


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Translate extends ActionBarActivity implements AdapterView.OnItemSelectedListener {
    GoogleTranslateMainActivity translator;
    EditText translateedittext;
    EditText translatabletext;
    TextToSpeech t1;
String to="fr";
    Spinner sp1;
    String OcrText="";
    String names[][]={{"Afrikaans","Albanian","Arabic","Armenian","Azerbaijani","Basque","Belarusian","Bengali","Bosnian","Bulgarian","Catalan","Cebuano","Chichewa","Croatian","Czech","Danish","Dutch","English","Esperanto","Estonian","Filipino","Finnish","French","Galician","Georgian","German","Greek","Gujarati","Haitian","Hausa","Hebrew","Hindi","Hmong","Hungarian","Icelandic","Igbo","Indonesian","Irish","Italian","Japanese","Javanese","Kannada","Kazakh","Khmer","Korean","Lao","Latin","Latvian","Lithuanian","Macedonian","Malagasy","Malay","Malayalam","Maltese","Maori","Marathi","Mongolian","Myanmar(Burmese)","Nepali","Norwegian","Persian","Polish","Portuguese","Punjabi","Romanian","Russian","Serbian","Sesotho","Sinhala","Slovak","Slovenian","Somali","Spanish","Sudanese","Swahili","Swedish","Tajik","Tamil","Telugu","Thai","Turkish","Ukrainian","Urdu","Uzbek","Vietnamese","Welsh","Yiddish","Yoruba","Zulu"},
            {"af","sq","ar","hy","az","eu","be","bn","bs","bg","ca","ceb","ny","hr","cs","da","nl","en","eo","et","tl","fi","fr","gl","ka","de","el","gu","ht","ha","iw","hi","hmn","hu","is","ig","id","ga","it","ja","jw","kn","kk","km","ko","lo","la","lv","lt","mk","mg","ms","ml","mt","mi","mr","mn","my","ne","no","fa","pl","pt","ma","ro","ru","sr","st","si","sk","sl","so","es","su","sw","sv","tg","ta","te","th","tr","uk","ur","uz","vi","cy","yi","yo","zu"}};

    protected static final int RESULT_SPEECH = 1;
    private TextView txtText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        translateedittext = (EditText) findViewById(R.id.translateedittext);
        translatabletext = (EditText) findViewById(R.id.translatabletext);
        Button translatebutton = (Button) findViewById(R.id.translatebutton);
        Button imgocr = (Button) findViewById(R.id.btss);
        translatebutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new EnglishToTagalog().execute();

            }
        });





        Button btksp = (Button) findViewById(R.id.bttks);
        btksp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Translate.this,caputre1.class);
                startActivity(i);
            }
        });
        imgocr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Translate.this,CaptureActivity.class);
                startActivity(i);
            }
        });
         sp1=(Spinner) findViewById(R.id.spn);

        assert sp1 != null;
        sp1.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, names[0]);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter);

        Button spk= (Button)findViewById(R.id.btnsk);
        spk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

                try {
                    startActivityForResult(intent, RESULT_SPEECH);

                    translateedittext.setText("");
                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Opps! Your device doesn't support Speech to Text",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
        Button spek=(Button) findViewById(R.id.btnSpeak);
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });
        spek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = translatabletext.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        String item = parent.getItemAtPosition(pos).toString();
        System.out.println("new lang="+item);
        for(int i=0;i<88;i++)
        {
            if(names[0][i].equals(item))
            {
                to=names[1][i];

            }
        }
        Button spek=(Button) findViewById(R.id.btnSpeak);
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });
        spek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = translatabletext.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        Intent intent = getIntent();
        String textt = intent.getStringExtra("OcrText");
if(textt!=null)
{
    EditText trans = (EditText) this.findViewById(R.id.translateedittext);

    trans.setText(textt);
}


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        to="fr";
    }

    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    private class EnglishToTagalog extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        protected void onError(Exception ex) {

        }
        @Override
        protected Void doInBackground(Void... params) {

            try {
                translator = new GoogleTranslateMainActivity("AIzaSyDv9qLwp4opHOVm4jQtWbnCCrP0HmXwfVc");

                Thread.sleep(1000);


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;

        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPreExecute() {
            //start the progress dialog
            progress = ProgressDialog.show(Translate.this, null, "Translating...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(Void result) {
            progress.dismiss();

            super.onPostExecute(result);
            translated();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    translateedittext.setText(text.get(0));
                }
                break;
            }

        }
        Button spek=(Button) findViewById(R.id.btnSpeak);
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });
        spek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = translatabletext.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    public void translated(){

        String text, translatetotagalog = translateedittext.getText().toString();//get the value of text


            text = translator.translte(translatetotagalog, to);
           EditText translatabletext = (EditText) this.findViewById(R.id.translatabletext);
            translatabletext.setText(text);

    }

}

