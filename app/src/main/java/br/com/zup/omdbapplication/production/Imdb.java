package br.com.zup.omdbapplication.production;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import br.com.zup.omdbapplication.R;
import br.com.zup.omdbapplication.context.Contexto;

/**
 * Created by arthur on 31/01/17.
 */

public class Imdb {
    private String Title;
    private String Year;
    private String Rated;
    private String Released;
    private String Runtime;
    private String Genre;
    private String Director;
    private String Actors;
    private String Plot;
    private String Language;
    private String Poster;
    private Bitmap imagem;
    private String imagemPath;
    private String imdbRating;
    private String imdbID;
    private String Type;

    public Imdb(){};

    public Imdb(String title, String id,String year,String imagem){
        this.Title = title;
        this.imdbID = id;
        this.Year = year;
        this.imagemPath = imagem;

        //se o filme nao tem poster, o caminho gerado eh null, logo preenchemos com a imagem base
        if (imagem==null){
            this.setImagem(BitmapFactory.decodeResource(Contexto.context().getResources(), R.drawable.imdb));
        }
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getRated() {
        return Rated;
    }

    public void setRated(String rated) {
        Rated = rated;
    }

    public String getReleased() {
        return Released;
    }

    public void setReleased(String released) {
        Released = released;
    }

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String runtime) {
        Runtime = runtime;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String actors) {
        Actors = actors;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public Bitmap getImagem() {
        return imagem;
    }

    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;
    }

    public String getImagemPath() {
        return imagemPath;
    }

    public void setImagemPath(String imagemPath) {
        this.imagemPath = imagemPath;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }



    @Override
    public String toString(){
        return Title + ": " + Year +  imagem;
    }

}















