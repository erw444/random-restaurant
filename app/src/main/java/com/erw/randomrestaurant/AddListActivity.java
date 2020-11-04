package com.erw.randomrestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddListActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.erw.randomrestaurant.REPLY";

    private EditText mEditListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);
        mEditListView = findViewById(R.id.list_name);

        final Button button = findViewById(R.id.button_new_list_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditListView.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String word = mEditListView.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY, word);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}
