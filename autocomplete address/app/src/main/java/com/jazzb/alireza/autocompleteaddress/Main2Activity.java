package com.jazzb.alireza.autocompleteaddress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    EditText edt, edt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        edt = findViewById(R.id.editText);
        edt2 = findViewById(R.id.editText2);

        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0)
                    Toast.makeText(Main2Activity.this, "Not enter", Toast.LENGTH_SHORT).show();
                else if (s.length() == 4)
                    Toast.makeText(Main2Activity.this, "go to check pref code", Toast.LENGTH_SHORT).show();


            }
        };



        edt2.addTextChangedListener(textWatcher );

        edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String email = edt.getText().toString().trim();

                    if (email.matches(emailPattern) ){
                        Toast.makeText(Main2Activity.this, "email valid", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Main2Activity.this, "not email valid", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });


    }
}
