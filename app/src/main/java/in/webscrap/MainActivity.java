package in.webscrap;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    String url = "https://www.timeanddate.com/worldclock/india/new-delhi";
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        */
    }

    public void check_connection(View view) {
        if (!ConnectivityReceiver.isConnected()) {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();

        }
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            new Title().execute();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            Toast.makeText(this, "Connected to Internet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Offline", Toast.LENGTH_SHORT).show();
        }
    }

    // Title AsyncTask
    private class Title extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Please Wait");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                title = document.title();

                //document = Jsoup.parse(document.toString());

                Elements elements = document.select("span[class=h1]");

                title = elements.text();
/*
                Elements img = document.select("a[class=brand brand-image] img[src]");
                Elements elements = document.select("span[class=h1]");
                // Get the html document title
                //title = elements.attr("");
                if (elements.isEmpty()){
                    //Toast.makeText(MainActivity.this, "Empty", Toast.LENGTH_SHORT).show();
                    title = "Empty";
                }
                //title = document.title();
                */
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView
            TextView txttitle = (TextView) findViewById(R.id.titletxt);
            txttitle.setText(title);
            mProgressDialog.dismiss();
        }
    }

}
