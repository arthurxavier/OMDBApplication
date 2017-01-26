package br.com.zup.omdbapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by arthur on 26/01/17.
 */

public class CreateDB extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "bancoIMDB.db";
    public static final String TABELA = "filmes";
    public static DataBase tabela = new DataBase();
    private static final int VERSAO = 3;

    public CreateDB(Context context){

        super(context,NOME_BANCO,null,VERSAO);
    }


    private static CreateDB instance;
    public static synchronized CreateDB getInstance(Context context){
        if(instance == null)
            instance = new CreateDB(context);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        String sql = "CREATE TABLE " + TABELA + "(" + tabela.campos() + ")";
        database.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        database.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(database);
    }


}