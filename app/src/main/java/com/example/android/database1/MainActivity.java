package com.example.android.database1;
import com.example.android.database1.data.DbHelper;
import com.example.android.database1.data.Contract;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Button viewAmount;
    private EditText entryId_field, title_field, subtitle_field;
    private TextView dbAmount;
    private int entryId;
    private String title, subtitle;
    private DbHelper helper = new DbHelper(this);
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entryId_field = (EditText) findViewById(R.id.entry_id);
        title_field = (EditText) findViewById(R.id.title);
        subtitle_field = (EditText) findViewById(R.id.subtitle);

        viewAmount = (Button) findViewById(R.id.view_amount);
        viewAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addData(v);
                displayDatabaseInfo();
            }
        });
    }

    public void addData(View view){
        entryId = Integer.parseInt(entryId_field.getText().toString().trim());
        title = title_field.getText().toString().trim();
        subtitle = subtitle_field.getText().toString().trim();
        database = helper.getWritableDatabase();
        helper.insertData(entryId, title, subtitle, database);
        Toast.makeText(this,"data inserted", Toast.LENGTH_SHORT).show();
    }

    private void displayDatabaseInfo(){

        //to access our database, initiate subclass of SQLiteOpenHelper

        //Create or open a database to read from it
        SQLiteDatabase db = helper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        Cursor cursor = db.rawQuery("SELECT * FROM " + Contract.FeedEntry.TABLE_NAME, null);

        try {
            dbAmount = (TextView) findViewById(R.id.db_amount);
            dbAmount.setText("Number of rows in feedEntry database table: " + cursor.getCount());
        }finally {

            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }

    }
}
