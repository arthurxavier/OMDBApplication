package br.com.zup.omdbapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by arthur on 26/01/17.
 */

public class ControllerDB{
    private SQLiteDatabase database;
    private CreateDB criaBanco;

    //Mantem somente uma instancia do banco de dados
    public ControllerDB(Context context){
        criaBanco = CreateDB.getInstance(context);
    }

    //Os parametros sao a tabela que sera acessada e os valores que serao inseridos
    public synchronized long inserirProducao(String tabela, ContentValues values){
        //Abre o bd para edicao
        database = criaBanco.getWritableDatabase();
        //Insere os valores na tabela definida
        long resultado = database.insert(tabela,null,values);

        return resultado;
    }

    //Os parametros sao a tabela que sera acessada e os campos de informacao que serao retornados no cursor
    public synchronized Cursor consultarProducoes(String tabela, String[] campos){
        //cursor eh uma classe que salvara as informacoes retornadas por uma query em um BD
        Cursor cursor;
        //abre o banco somente para leitura
        database = criaBanco.getReadableDatabase();
        //faz a pesquisa
        cursor = database.query(tabela,campos,null,null,null,null,null,null);
        if(cursor!=null){
            //move o cursor para a primeira linha
            cursor.moveToFirst();
        }

        return cursor;
    }

    //Os parametros sao a tabela que sera acessada e o dado de identificacao
    public synchronized void deletarProducao(String tabela, String where){
        //passa o id para identificar o item a ser removido
        //String where = CreateDatabase.tabela.IMDBID + "=" + "'"+id+"'";
        database = criaBanco.getReadableDatabase();
        database.delete(tabela,where,null);
    }

    //Os parametros sao a tabela que sera acessada e os campos de informacao que serao retornados no cursor
    public synchronized Cursor buscaProducao(String tabela, String[] campos, String where){
        //cursor eh uma classe que salvara as informacoes retornadas por uma query em um BD
        Cursor cursor;
        //abre o banco somente para leitura
        database = criaBanco.getReadableDatabase();
        //faz a pesquisa
        cursor = database.query(tabela,campos,where,null,null,null,null,null);
        if(cursor!=null){
            //move o cursor para a primeira linha
            cursor.moveToFirst();
        }

        return cursor;
    }
}
