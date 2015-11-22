package com.example.qwerk.safety;

import android.app.ActionBar;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class addcontact extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontact);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_addcontact, menu);
        return true;
    }

    public void onClickAddName(View view) {
        // Add a new student record
        ContentValues values = new ContentValues();
        String name=((EditText)findViewById(R.id.p1n)).getText().toString();
        String num=((EditText)findViewById(R.id.p1p)).getText().toString();
        System.out.print(name);

        values.put(database.NAME,name);

        values.put(database.NUMBER,num );

        Uri uri = getContentResolver().insert(database.CONTENT_URI, values);

        Toast.makeText(getBaseContext(), "Contact added successfully", Toast.LENGTH_LONG).show();
    }

    public void onClickRetrieveStudents(View view) {

        // Retrieve student records
        String URL = "content://com.example.qwerk.safety.databs/dbase";

        Uri students = Uri.parse(URL);
        Cursor c = managedQuery(students, null, null, null, "name");

        if (c.moveToFirst()) {
            do {
                Toast.makeText(this,
                        c.getString(c.getColumnIndex(database._ID)) +
                                ", " + c.getString(c.getColumnIndex(database.NAME)) +
                                ", " + c.getString(c.getColumnIndex(database.NUMBER)),
                        Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }
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
