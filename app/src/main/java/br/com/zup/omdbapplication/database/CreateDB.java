package br.com.zup.omdbapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDB extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "bancoimdb.db";
    public static final String TABELA = "producoes";
    public static DataBase tabela = new DataBase();
    private static final int VERSAO = 1;

    //construtor
    public CreateDB(Context context){
        super(context,NOME_BANCO,null,VERSAO);
    }
    //singleton
    private static CreateDB instance;
    public static synchronized CreateDB getInstance(Context context){
        if(instance == null)
            instance = new CreateDB(context);
        return instance;
    }

    //chamado quando a aplicacao chama o bd pela primeira vez.
    // deve possuir todas as diretrizes de criacao e populacao inicial do banco
    @Override
    public void onCreate(SQLiteDatabase database){
        //codigo de criacao que segue o padrao JDBC
        String sql = "CREATE TABLE " + TABELA + "(" + tabela.campos() + ")";
        //comando de criacao
        database.execSQL(sql);
    }

    //responsavel por atualizar o banco de dados se acontecer mudanca estrutural
    // sempre eh chamado quando uma atualizacao eh necessaria,
    // para nao haver inconsistencia de dados entre o banco existente e o novo
    //contem tambem a versao antiga e a nova
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        database.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(database);
    }
}
