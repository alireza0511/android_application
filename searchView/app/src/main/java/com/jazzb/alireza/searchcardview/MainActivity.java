package com.jazzb.alireza.searchcardview;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SearchView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        sv= (SearchView) findViewById(R.id.mSearch);
        RecyclerView rv= (RecyclerView) findViewById(R.id.myRecycler);

        //SET ITS PROPETRIES
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());

        //ADAPTER
        final MyAdapter adapter=new MyAdapter(this,getPlayers());
        rv.setAdapter(adapter);

        //SEARCH
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                adapter.getFilter().filter(query);
                return false;
            }
        });
    }

    //ADD PLAYERS TO ARRAYLIST
    private ArrayList<Player> getPlayers()
    {
        ArrayList<Player> players=new ArrayList<>();
        Player p=new Player();
        p.setName("Ander Herera");
        p.setPos("Midfielder");
        players.add(p);

        p=new Player();
        p.setName("David De Geaa");
        p.setPos("Goalkeeper");
        players.add(p);

        p=new Player();
        p.setName("Michael Carrick");
        p.setPos("Midfielder");
        players.add(p);

        p=new Player();
        p.setName("Juan Mata");
        p.setPos("Playmaker");
        players.add(p);

        p=new Player();
        p.setName("Diego Costa");
        p.setPos("Striker");
        players.add(p);

        p=new Player();
        p.setName("Oscar");
        p.setPos("Playmaker");
        players.add(p);

        return players;
    }

}
