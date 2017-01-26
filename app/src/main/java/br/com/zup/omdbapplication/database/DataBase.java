package br.com.zup.omdbapplication.database;

/**
 * Created by arthur on 26/01/17.
 */

public class DataBase {

    public static final String TITLE = "title";
    public static final String YEAR = "year";
    public static final String RATED = "rated";
    public static final String RELEASED = "released";
    public static final String RUNTIME = "runtime";
    public static final String GENRE = "genre";
    public static final String DIRECTOR = "director";
    public static final String ACTORS = "actors";
    public static final String PLOT = "plot";
    public static final String LANGUAGE = "language";
    public static final String IMAGE = "image";
    public static final String IMDBID = "IMDBID";
    public static final String IMDBRATING = "imdbRating";
    public static final String POSTER = "poster";


    public String campos() {

        return TITLE + " text,"
                + YEAR + " text,"
                + RATED + " text,"
                + RELEASED + " text,"
                + RUNTIME + " text,"
                + GENRE + " text,"
                + DIRECTOR + " text,"
                + ACTORS + " text,"
                + PLOT + " text,"
                + LANGUAGE + " text,"
                + IMDBID + " text PRIMARY KEY,"
                + IMDBRATING + " text,"
                + IMAGE + " BLOB,"
                + POSTER + " text";
    }


}
