package br.com.zup.omdbapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.zup.omdbapplication.R;
import br.com.zup.omdbapplication.activities.DescriptionActivity;
import br.com.zup.omdbapplication.context.Contexto;
import br.com.zup.omdbapplication.production.Imdb;

/**
 * Created by arthur on 31/01/17.
 */

public class CustomAdapterDescription extends RecyclerView.Adapter{
    private ArrayList<Imdb> lista;
    private Context context;

    public CustomAdapterDescription(ArrayList<Imdb> lista, Context context, Activity pai){
        this.lista = lista;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview, parent, false);
        CustomRecyclerViewHolder holder = new CustomRecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position){
        CustomRecyclerViewHolder holder = (CustomRecyclerViewHolder) viewHolder;
        final Imdb imdb = lista.get(position);

        //se o caminho for null, a producao nao esta salva
        //se esta salva, basta pega-la no bd
        if(imdb.getImagemPath()==null){
            holder.imageDesc.setImageBitmap(imdb.getImagem());
        } else {
            String path = imdb.getImagemPath();
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            holder.imageDesc.setImageBitmap(bitmap);

        }


    }

    @Override
    public int getItemCount(){
        return lista.size();
    }

    public class CustomRecyclerViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageDesc;

        public CustomRecyclerViewHolder(View view) {
            super(view);
            imageDesc = (ImageView) view.findViewById(R.id.iv_cardImageDescription);
        }
    }
}
