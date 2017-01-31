package br.com.zup.omdbapplication.context;

import android.content.Context;

public class Contexto extends android.app.Application {
    private static Contexto contexto = null;
    /* (non-Javadoc)
     * @see android.app.Application#onCreate()
     */
    @Override
    public void onCreate()
    {
        super.onCreate();
        contexto = this;
    }
    public static Context context()
    {
        return contexto.getApplicationContext();
    }
}
