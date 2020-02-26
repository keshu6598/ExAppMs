package com.example.exappms;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu; 
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Dialog myDialog;
    private SimpleCursorAdapter adapter;
    private DBManager dbManager;
    private ListView listView;

    final String[] from = new String[] {DatabaseHelper.SUBJECT, DatabaseHelper.DESC};

    final int[] to = new int[] { R.id.room_number, R.id.rasp_details};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        listView = findViewById(R.id.all_pi_details);
        listView.setEmptyView(findViewById(R.id.empty));


        adapter = new SimpleCursorAdapter(this, R.layout.list_view_layout, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        // On click listener for item on the list view.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
            }
        });

        // On hold listener for the item in the list view.
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String room_number_to_change = ((TextView) view.findViewById(R.id.room_number)).getText().toString();
                String rasp_detail_to_change = ((TextView) view.findViewById(R.id.rasp_details)).getText().toString();


                Intent modify_intent = new Intent(getApplicationContext(), ModifyRespberrypi.class);
                modify_intent.putExtra("room_number_to_change", room_number_to_change);
                modify_intent.putExtra("rasp_detail_to_change", rasp_detail_to_change );

                startActivity(modify_intent);
                return true;
            }
        });

        myDialog = new Dialog(this);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPopup(view);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void ShowPopup(View v) {

        myDialog.setContentView(R.layout.pop_up_window);

        Button cancel = myDialog.findViewById(R.id.cancel);
        Button save = myDialog.findViewById(R.id.add);
        final EditText et_roomno = myDialog.findViewById(R.id.et_roomno);
        final EditText et_rspid = myDialog.findViewById(R.id.et_rspid);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbManager.insert(et_roomno.getText().toString(), et_rspid.getText().toString());
                returnHome();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    // Used to start the home Activity.
    public void returnHome() {
        Intent home_intend = new Intent(MainActivity.this, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intend);
    }
}
