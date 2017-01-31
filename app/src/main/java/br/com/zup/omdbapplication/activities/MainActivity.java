package br.com.zup.omdbapplication.activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import br.com.zup.omdbapplication.R;
import br.com.zup.omdbapplication.adapter.CustomAdapter;
import br.com.zup.omdbapplication.assynctask.AssyncTaskArray;
import br.com.zup.omdbapplication.production.Imdb;
import br.com.zup.omdbapplication.webservice.VolleyRequests;
import br.com.zup.omdbapplication.webservice.VolleyResult;


public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    ArrayList<Imdb> list;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Infla a toolbar definida em toolbar_main.xml
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //Requere acesso direto ao search service
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //Associa searchView à barra de pesquisa no layout
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            //Usar false torna o icone de busca sempre visivel, o padrao eh true
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Esconde o teclado
                searchView.clearFocus();
                //Pega o titulo pesquisado. Se for mais de um nome ele troca espaços por + (ex.: the flash -> the+flash)
                String titulo = searchView.getQuery().toString().replace(' ', '+');
                //Esse endereco retorna uma lista com os filmes que contenham a pesquisa em seu titulo
                String endereco = "http://www.omdbapi.com/?s=" + titulo;
                //Cria uma thread para fazer a pesquisa
                showProgressDialog();
                callVolley(endereco);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            //O usuario clicou no disquete para salvar a busca feita no BD
        /*    case R.id.action_save:
                Intent intent = new Intent(MainActivity.this, SavedActivity.class);
                startActivity(intent);
                return true;
        */
            //O usuario clicou na lupa para abrir a caixa de pesquisa
            case R.id.action_search:
                return true;

            //Se chegar no default, o usuario utilizou uma opcao nao implementada
            default:
                Snackbar snackbar = Snackbar.make(findViewById(R.id.mainLayout), "Acao invalida", Snackbar.LENGTH_LONG);
                snackbar.show();
                return super.onOptionsItemSelected(item);
        }
    }


    private ArrayList<Imdb> callTask(String endereco) {
        //Cria uma assync task, que executa no plano de fundo do aplicativo
        AssyncTaskArray task = new AssyncTaskArray(MainActivity.this);
        //execute faz com que a task execute seus metodos( doInBackground necessario + 2 opcionais)
        task.execute(endereco);

        try {
            //retorna uma lista de objetos da classe Imdb
            return task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }


    //helpers
    private void callVolley(String endereco) {
        VolleyRequests volleyRequests = new VolleyRequests(MainActivity.this);
        volleyRequests.volleyJsonRequest(endereco, new VolleyResult() {
            @Override
            public void onSucess(Object result) {
                dismissProgressDialog();
                setList((JSONObject) result);
                if (list != null) {
                    fillRecyclerView(list);
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.mainLayout), "Filme nao encontrado", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }

            @Override
            public void onError() {
                dismissProgressDialog();
            }

        });
    }

    public void callVolleyImage() {
        VolleyRequests volleyRequests = new VolleyRequests(MainActivity.this);
        for (final Imdb imdb : list) {
            volleyRequests.volleyImageRequest(imdb.getPoster(), new VolleyResult() {
                @Override
                public void onSucess(Object result) {
                    imdb.setImagem((Bitmap) result);
                }

                @Override
                public void onError() {
                    imdb.setImagem(BitmapFactory.decodeResource(getResources(), R.drawable.imdb));
                }

            });
        }
    }

    public void showProgressDialog() {
        mProgress = ProgressDialog.show(MainActivity.this, "", "Carregando", true);
    }

    public void dismissProgressDialog() {
        mProgress.dismiss();
    }

    public void fillRecyclerView(ArrayList<Imdb> list) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listaReciclavel);
        recyclerView.setAdapter(new CustomAdapter(list, MainActivity.this, MainActivity.this));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setList(JSONObject value) {
        try {
            JSONArray jsonArray = value.getJSONArray("Search");
            Gson gson = new Gson();
            Type collectionType = new TypeToken<ArrayList<Imdb>>() {
            }.getType();
            list = gson.fromJson(jsonArray.toString(), collectionType);

            callVolleyImage();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
