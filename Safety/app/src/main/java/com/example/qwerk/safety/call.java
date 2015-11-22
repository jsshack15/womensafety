package com.example.qwerk.safety;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class call extends ActionBarActivity {
    private TextView latituteField;
    private TextView longitudeField;

    private String provider;
    int[] locationarr=new int[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panic);


        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.GPS_PROVIDER;
        Location location = locationManager.getLastKnownLocation(locationProvider);

        if(location!=null){

            locationarr[0]=(int) (location.getLatitude());
            locationarr[1]= (int) (location.getLongitude());

        }
        else {
            System.out.println("default");
            locationarr[0]=37;
            locationarr[1]=37;

        }
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            System.out.print("het jjdlajsld");
            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    System.out.print("hiiii");
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();
                    System.out.print("help");

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.print("get it done you asshole");
        }
    }





    public  void whenClick(View view){

        String uri="ravi";

        Toast.makeText(getBaseContext(),""+ locationarr[0], Toast.LENGTH_LONG).show();
        DownloadWebPageTask task = new DownloadWebPageTask();

        task.execute(new String[] { "http://192.168.137.1/map/add_user_data.php?q=9876543210&u="+locationarr[0]+"&r="+locationarr[1] });
        Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_call, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
