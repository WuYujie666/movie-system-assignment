package structures;

import structures.*;
import stores.*;

public class CastPerson extends Person {
    int credit=0;
    AList<Integer> castFilms;
    AList<Integer> castStarInFilms;
    // Constructor
    public CastPerson(int id, String name, String profilePath) {
        super(id, name,profilePath);
        castFilms=new AList<Integer>();
        castStarInFilms=new AList<Integer>();
    }
    //Getters
    public int getCredit() {
        return credit;
    }
    public int[] getCastFilms() {
        return castFilms.toIntArray();
    }
    public int[] getCastStarInFilms() {
        return castStarInFilms.toIntArray();
    }
    
    //addMovie
    public void addMovie(int filmId, int order){
        castFilms.add(filmId);
        if(order<3){//???
            castStarInFilms.add(filmId);
        }
        credit++;
    }



}

