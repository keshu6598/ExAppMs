package com.example.exappms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ModifyRespberrypi extends Activity implements View.OnClickListener {
    private EditText titleText;
    private Button updateBtn, deleteBtn;
    private EditText descText;
    private String room_name;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Modify Record");
        setContentView(R.layout.modify_respberry_pi);

        dbManager = new DBManager(this);
        dbManager.open();

        titleText = findViewById(R.id.modified_room_name);
        descText  = findViewById(R.id.modified_rasp_id);

        updateBtn = findViewById(R.id.btn_modify);
        deleteBtn = findViewById(R.id.btn_delete);

        Intent intent = getIntent();
        room_name = intent.getStringExtra("room_number_to_change");
        String desc = intent.getStringExtra("rasp_detail_to_change");

        titleText.setText(room_name);
        descText.setText(desc);

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_modify:
                String title = titleText.getText().toString();
                String desc = descText.getText().toString();

                dbManager.update(room_name,title, desc);
                this.returnHome();
                break;

            case R.id.btn_delete:
                dbManager.delete(room_name);
                this.returnHome();
                break;
        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}
