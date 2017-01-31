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

public class CustomAdapter extends RecyclerView.Adapter {
    private ArrayList<Imdb> lista;
    private Context contexto;
    private Activity activity;


    public CustomAdapter(ArrayList<Imdb> lista, Context contexto, Activity activity){
        this.lista = lista;
        this.contexto = contexto;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contexto).inflate(R.layout.item_recyclerview, parent, false);
        CustomRecyclerViewHolder holder = new CustomRecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewholder, int position) {
        CustomRecyclerViewHolder holder = (CustomRecyclerViewHolder) viewholder;
        final Imdb imdb = lista.get(position);
        //se o caminho for null, a producao nao esta salva
        //se esta salva, basta pega-la no bd
        if(imdb.getImagemPath()==null){
            holder.imgPoster.setImageBitmap(imdb.getImagem());
        } else {
            String path = imdb.getImagemPath();
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            holder.imgPoster.setImageBitmap(bitmap);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Contexto.context(), DescriptionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("imdb",imdb.getImdbID());
                intent.putExtra("contexto",contexto.getClass().toString());
                Contexto.context().startActivity(intent);
                if(!contexto.getClass().toString().equals("class com.zup.omdbapplication.activity.MainActivity")) {
                    activity.finish();
                }
            }
        });


    }


    @Override
    public int getItemCount(){
        return lista.size();
    }

    public class CustomRecyclerViewHolder extends RecyclerView.ViewHolder {

        final CardView cardView;
        final ImageView imgPoster;

        public CustomRecyclerViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
            imgPoster = (ImageView) view.findViewById(R.id.cardImage);
        }
    }
}
