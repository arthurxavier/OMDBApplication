package br.com.zup.omdbapplication.assynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import br.com.zup.omdbapplication.production.Imdb;
import br.com.zup.omdbapplication.webservice.Connection;

import java.util.ArrayList;

/**
 * Created by Hugo on 05/12/2016.
 */
public class AssyncTaskArray extends AsyncTask<String, Void, ArrayList<Imdb>> {
    private Context context;
    private ProgressDialog mProgress;

    public AssyncTaskArray(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute(){
        mProgress = ProgressDialog.show(context,"","Carregando",true);
    }

    @Override
    protected ArrayList<Imdb> doInBackground(String... params){
        return Connection.getInformacaoArrayImdb(params[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<Imdb> imdb){
        mProgress.dismiss();
    }
}
