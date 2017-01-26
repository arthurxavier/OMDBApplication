package br.com.zup.omdbapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by arthur on 26/01/17.
 */

public class ControllerDB {

    private CreateDB createDB;
    private SQLiteDatabase db;

    public  ControllerDB(Context context){

        createDB = CreateDB.getInstance(context);
    }

    public synchronized long inserirDados(String table, ContentValues values){

        db = createDB.getWritableDatabase();
        long resultado = db.insert(table,null,values);

        return resultado;
    }

    public synchronized Cursor CarregaDados(String tabela, String[] campos){
        Cursor cursor;
        db = createDB.getReadableDatabase();
        cursor = db.query(tabela,campos,null,null,null,null,null,null);

        if(cursor!= null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public synchronized void DeletaDados(String tabela, String where){

        db = createDB.getReadableDatabase();
        db.delete(tabela,where,null);
        //database.close();
    }


}
