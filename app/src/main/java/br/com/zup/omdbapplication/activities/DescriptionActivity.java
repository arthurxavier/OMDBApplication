package br.com.zup.omdbapplication.activities;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import br.com.zup.omdbapplication.R;
import br.com.zup.omdbapplication.database.ControllerDB;
import br.com.zup.omdbapplication.database.CreateDB;
import br.com.zup.omdbapplication.database.DataBase;
import br.com.zup.omdbapplication.production.Imdb;
import br.com.zup.omdbapplication.webservice.VolleyRequests;

/**
 * Created by arthur on 31/01/17.
 */

public class DescriptionActivity extends AppCompatActivity {

    Imdb imdb;
    ProgressDialog load;

    private ControllerDB banco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_info);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        banco = new ControllerDB(getBaseContext());

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("imdb");
        String contexto = bundle.getString("contexto");


        final Button salvar = (Button) findViewById(R.id.btnSalvar);

        if (contexto.equals("class com.example.hugo.projeto_imdb.activity.SavedActivity")) {
            salvar.setText("Remover");
            String campos[] = {
                    CreateDB.tabela.TITLE,
                    CreateDB.tabela.YEAR,
                    CreateDB.tabela.RATED,
                    CreateDB.tabela.RELEASED,
                    CreateDB.tabela.RUNTIME,
                    CreateDB.tabela.GENRE,
                    CreateDB.tabela.DIRECTOR,
                    CreateDB.tabela.ACTORS,
                    CreateDB.tabela.PLOT,
                    CreateDB.tabela.LANGUAGE,
                    CreateDB.tabela.POSTER,
                    CreateDB.tabela.IMDBRATING,
                    CreateDB.tabela.IMDBID,
            };
            final String where = CreateDB.tabela.IMDBID + "=" + "'" + id + "'";
            Cursor cursor = banco.CarregaDados(CreateDB.TABELA, campos, where);
            imdb = setImdbCursor(cursor);
            setView(imdb);
            salvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    banco.DeletaDados(CreateDB.TABELA, where);
                    Intent intent = new Intent(DescriptionActivity.this, GaleryActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            showProgressDialog();
            salvar.setText("Salvar");
            //imdb = callTask("http://www.omdbapi.com/?i=" + id);
            callVolley("http://www.omdbapi.com/?i=" + id);
            salvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String caminho = new String();
                    //se a producao nao tem poster, nao salva a imagem do imdb no bd
                    if (!imdb.getPoster().equals("N/A")) {
                        caminho = salvarImagem(imdb.getImagem());
                        imdb.setImagemPath(caminho);
                    }
                    //ContentValues values = new ContentValues();
                    //IMDbDatabase.putValues(values,imdb);
                    Map<String, String> mapa = new HashMap<String, String>();
                    preencherMapa(mapa, imdb);
                    ContentValues values = new ContentValues();



                    values = DataBase.putValues(mapa, values);
                    Long resultado = banco.inserirDados(CreateDB.TABELA, values);
                    if (resultado == -1) {
                        try {
                            Toast.makeText(DescriptionActivity.this, "Erro ao adicionar", Toast.LENGTH_SHORT).show();
                            if (!caminho.equals(null)) {
                                File file = new File(caminho);
                                file.delete();
                            }
                        } catch (Exception e) {
                            e.getMessage();
                        }

                    } else {
                        Toast.makeText(DescriptionActivity.this, "Adicionado com sucesso", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            });
        }

        /*
        Button openIMDb = (Button) findViewById(R.id.buttonOpenIMDb);
        openIMDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DescriptionActivity.this, IMDbWebViewActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("imdb", imdb.getImdbID());
                startActivity(intent);
            }
        });
        */
    }

    private void callVolley(String endereco) {
        VolleyRequests volleyRequests = new VolleyRequests(DescriptionActivity.this);
        volleyRequests.volleyJsonRequest(endereco, new VolleyRequests.VolleyResult() {
            @Override
            public void onSucess(Object result) {
                dismissProgressDialog();
                setImdb((JSONObject) result);
                setView(imdb);
            }

            @Override
            public void onError(VolleyError error) {
                dismissProgressDialog();
            }
        });
    }

    public void callVolleyImage() {
        VolleyRequests volleyRequests = new VolleyRequests(DescriptionActivity.this);
        volleyRequests.volleyImageRequest(imdb.getPoster(), new VolleyRequests.VolleyResult() {
            @Override
            public void onSucess(Object result) {
                imdb.setImagem((Bitmap) result);
            }

            @Override
            public void onError(VolleyError error) {
                imdb.setImagem(BitmapFactory.decodeResource(getResources(), R.drawable.imdb));
            }
        });

    }

    public void setImdb(JSONObject value) {
        String json = value.toString();
        Gson gson = new Gson();
        imdb = gson.fromJson(json, Imdb.class);
        callVolleyImage();
    }

    public void showProgressDialog() {
        load = ProgressDialog.show(DescriptionActivity.this, "", "Carregando", true);
    }

    public void dismissProgressDialog() {
        load.dismiss();
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


    private Imdb setImdbCursor(Cursor cursor) {
        Imdb imdb = new Imdb();
        imdb.setTitle(cursor.getString(0));
        imdb.setYear(cursor.getString(1));
        imdb.setRated(cursor.getString(2));
        imdb.setReleased(cursor.getString(3));
        imdb.setRuntime(cursor.getString(4));
        imdb.setGenre(cursor.getString(5));
        imdb.setDirector(cursor.getString(6));
        imdb.setWriter(cursor.getString(7));
        imdb.setActors(cursor.getString(8));
        imdb.setPlot(cursor.getString(9));
        imdb.setLanguage(cursor.getString(10));
        imdb.setCountry(cursor.getString(11));
        imdb.setAwards(cursor.getString(12));
        imdb.setImagemPath(cursor.getString(13));
        imdb.setMetascore(cursor.getString(14));
        imdb.setImdbRating(cursor.getString(15));
        imdb.setImdbVotes(cursor.getString(16));
        imdb.setImdbID(cursor.getString(17));
        imdb.setType(cursor.getString(18));
        return imdb;
    }

    private void setView(Imdb imdb) {
        TextView tv_Title = (TextView) findViewById(R.id.tv_Title);
        TextView tv_Year = (TextView) findViewById(R.id.tv_Year);
        TextView tv_Rated = (TextView) findViewById(R.id.tv_Rated);
        TextView tv_Rating = (TextView) findViewById(R.id.tv_Rating);
        TextView tv_Plot = (TextView) findViewById(R.id.tv_Plot);
        TextView tv_Directos = (TextView) findViewById(R.id.tv_Directors);


        tv_Title.setText(imdb.getTitle());
        tv_Year.setText(imdb.getYear());
        tv_Rated.setText(imdb.getRated());
        tv_Rating.setText(imdb.getImdbRating());
        tv_Plot.setText(imdb.getPlot());
        tv_Directos.setText(imdb.getDirector());

    }

    private static String salvarImagem(Bitmap imagem) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("TAG", "Erro ao criar o arquivo");
            return null;
        }

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            imagem.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.close();
            return pictureFile.getPath();
        } catch (FileNotFoundException e) {
            Log.d("TAG", "Arquivo nao encontrado: " + e.getMessage());
            return null;
        } catch (IOException e) {
            Log.d("TAG", "Erro ao acessar arquivo: " + e.getMessage());
            return null;
        }
    }

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/imdb");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmssSSS").format(new Date());
        String imageName = "poster" + timeStamp + ".jpg";
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + imageName);
        return mediaFile;
    }

    private void preencherMapa(Map<String, String> mapa, Imdb imdb) {
        mapa.put(DataBase.TITLE, imdb.getTitle());
        mapa.put(DataBase.YEAR, imdb.getYear());
        mapa.put(DataBase.RATED, imdb.getRated());
        mapa.put(DataBase.RELEASED, imdb.getReleased());
        mapa.put(DataBase.RUNTIME, imdb.getRuntime());
        mapa.put(DataBase.GENRE, imdb.getGenre());
        mapa.put(DataBase.DIRECTOR, imdb.getDirector());
        mapa.put(DataBase.ACTORS, imdb.getActors());
        mapa.put(DataBase.PLOT, imdb.getPlot());
        mapa.put(DataBase.LANGUAGE, imdb.getLanguage());
        mapa.put(DataBase.POSTER, imdb.getImagemPath());
        mapa.put(DataBase.IMDBRATING, imdb.getImdbRating());
        mapa.put(DataBase.IMDBID, imdb.getImdbID());
    }
}
