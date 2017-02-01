package br.com.zup.omdbapplication.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import br.com.zup.omdbapplication.R;
import br.com.zup.omdbapplication.adapter.CustomAdapter;
import br.com.zup.omdbapplication.database.ControllerDB;
import br.com.zup.omdbapplication.database.CreateDB;
import br.com.zup.omdbapplication.production.Imdb;

/**
 * Created by arthur on 31/01/17.
 */

public class GaleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galery);

        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar_saved);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ControllerDB banco = new ControllerDB(getBaseContext());
        String campos[] = {CreateDB.tabela.IMDBID, CreateDB.tabela.TITLE, CreateDB.tabela.YEAR, CreateDB.tabela.POSTER};
        Cursor cursor = banco.consultarProducoes(CreateDB.TABELA,campos);

        ArrayList<Imdb> lista = new ArrayList<Imdb>();

        if(cursor.getCount()==0){
            Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_galery),"Sem produções salvas",Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else {

            for(int i=0; i<cursor.getCount();i++){
                String id = cursor.getString(0);
                String titulo = cursor.getString(1);
                String ano = cursor.getString(2);
                String image = cursor.getString(3);
                lista.add(new Imdb(titulo,id,ano,image));
                cursor.moveToNext();
            }
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listaReciclavelSalvos);
        recyclerView.setAdapter(new CustomAdapter(lista,this,this));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
