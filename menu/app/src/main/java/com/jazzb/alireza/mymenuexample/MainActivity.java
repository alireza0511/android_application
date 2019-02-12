package com.jazzb.alireza.mymenuexample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt = findViewById(R.id.button1);

        registerForContextMenu(bt);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.template_menu, menu);
        menu.setHeaderTitle("Context Menu");
//        menu.add(0, v.getId(), 0, "Upload");
//        menu.add(0, v.getId(), 0, "Search");
//        menu.add(0, v.getId(), 0, "Share");
//        menu.add(0, v.getId(), 0, "Bookmark");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.more_info:
                Toast.makeText(context, "more info", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.template_menu:
                Toast.makeText(context, "template", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.opt_template1:
                Toast.makeText(context, "template1", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.opt_template2:
                Toast.makeText(context, "template2", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.opt_template3:
                Toast.makeText(context, "template3", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.opt_template4:
                Toast.makeText(context, "template4", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


}
