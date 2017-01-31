package br.com.zup.omdbapplication.assynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import br.com.zup.omdbapplication.production.Imdb;

import br.com.zup.omdbapplication.webservice.Connection;

/**
 * Created by Hugo on 12/12/2016.
 */
public class AssyncTaskObj extends AsyncTask<String, Void, Imdb> {
    private Context context;
    private ProgressDialog mProgress;

    public AssyncTaskObj(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute(){
        mProgress = ProgressDialog.show(context,"","Carregando",true);
    }

    @Override
    protected Imdb doInBackground(String... params){
        return Connection.getInformacaoImdb(params[0]);
    }

    @Override
    protected void onPostExecute(Imdb imdb){
        mProgress.dismiss();
    }
}
