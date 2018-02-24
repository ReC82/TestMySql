package com.bit.blmt.testmysql;

import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONStringer;

import static android.R.attr.button;

public class MySqlMain extends AppCompatActivity {

    public Button GetData;
    String ServerURL = "http://www.quizart.be/lidwoord/setdata.php" ;
    String mywoord = null;
    String mylidwoord = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sql_main);


        System.out.println("Before Insert");


        EditText woord = (EditText)findViewById(R.id.tb_woord);
        final EditText lidwoord = (EditText)findViewById(R.id.tb_lidwoord);
        Button btn_send = (Button)findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetData();

                InsertData(mywoord, mylidwoord);

            }
        });
        InsertData("Kasteel", "De");
        System.out.println("After Insert");
            }

    public void GetData(){

        mywoord = ((TextView) findViewById(R.id.tb_woord)).getText().toString();

        mylidwoord = ((TextView) findViewById(R.id.tb_lidwoord)).getText().toString();;

    }
    public void InsertData(final String woord, final String lidwoord){
        GetData();
        System.out.println("Enter InsertData Function");
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                System.out.println("Enter Do in Background");

                try {
                        System.out.println("Try...");
                        String username = (String)params[0];
                        String password = (String)params[1];

                        String data  = URLEncoder.encode("woord", "UTF-8") + "=" +
                                URLEncoder.encode(woord, "UTF-8");
                        data += "&" + URLEncoder.encode("lidwoord", "UTF-8") + "=" +
                                URLEncoder.encode(lidwoord, "UTF-8");

                        URL url = new URL(ServerURL);
                        URLConnection conn = url.openConnection();

                        conn.setDoOutput(true);
                        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                        wr.write( data );
                        wr.flush();

                        BufferedReader reader = new BufferedReader(new
                                InputStreamReader(conn.getInputStream()));

                        StringBuilder sb = new StringBuilder();
                        String line = null;

                        // Read Server Response
                    System.out.println("Read Server Response");
                        while((line = reader.readLine()) != null) {
                            sb.append(line);
                            break;
                        }

                        return sb.toString();

                }
                catch(Exception e){
                    System.out.println("Exception :" + e.toString());
                    return new String("Exception: " + e.getMessage());
                }
                finally {
                    System.out.println("Request Sent !");
                    System.out.println("Data Inserted Successfully");
                }
            }

            public void SelectData(final String woord, final String lidwoord){

                System.out.println("Enter InsertData Function");
                class SelectDataReqAsyncTask extends AsyncTask<String, Void, String> {
                    @Override
                    protected String doInBackground(String... params) {
                        System.out.println("Enter Do in Background");

                        try {
                            System.out.println("Try...");

                            //Params to send
                            /*
                            String username = (String)params[0];
                            String password = (String)params[1];

                            String data  = URLEncoder.encode("woord", "UTF-8") + "=" +
                                    URLEncoder.encode(woord, "UTF-8");
                            data += "&" + URLEncoder.encode("lidwoord", "UTF-8") + "=" +
                                    URLEncoder.encode(lidwoord, "UTF-8");*/
                            String urlrequest = "http://www.quizart.be/lidwoord/setdata.php";
                            URL url = new URL(ServerURL);
                            URLConnection conn = url.openConnection();

                            BufferedReader reader = new BufferedReader(new
                                    InputStreamReader(conn.getInputStream()));
                            return "true";
                        }
                        catch(Exception e){
                            System.out.println("Exception :" + e.toString());
                            return new String("Exception: " + e.getMessage());
                        }
                        finally {
                            System.out.println("Request Sent !");
                            System.out.println("Data Inserted Successfully");
                        }
                    }


            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                Toast.makeText(MySqlMain.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();
                System.out.println("Data Sent !");
            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(woord, lidwoord);
    }

    private String getQuery(List<Pair<String, String>> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Pair<String, String> pair : params)
        {
            if (first)
                first = false;
            else
                result.append(pair);

            result.append(pair);
        }

        return result.toString();
    }
public String get(String url) throws IOException {
    InputStream is = null;
    try {
        final HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        is = conn.getInputStream();
        // Read the InputStream and save it in a string
        return readIt(is);
    }
    finally {
        // Makes sure that the InputStream is closed after the app is
        // finished using it.
        if (is != null) {
            is.close();
        }
    }
}

private String readIt(InputStream is) throws IOException {
    BufferedReader r = new BufferedReader(new InputStreamReader(is));
    StringBuilder response = new StringBuilder();
    String line;
    while ((line = r.readLine()) != null) {
        response.append(line).append('\n');
    }
    return response.toString();
}

private class AsyncRunner extends AsyncTask<File, Integer, Void>
{

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        //Toast.makeText(getApplicationContext(), "Début du traitement asynchrone", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Integer... values){
        //super.onProgressUpdate(values);
        // Mise à jour de la ProgressBar

    }

    @Override
    protected Void doInBackground(File... files) {
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        //Toast.makeText(getApplicationContext(), "Le traitement asynchrone est terminé", Toast.LENGTH_LONG).show();
    }
}
}