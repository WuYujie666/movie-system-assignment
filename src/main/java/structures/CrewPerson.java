package structures;

import structures.*;
import stores.*;

public class CrewPerson extends Person {
    int credit=0;
    AList<Integer> crewFilms=new AList<Integer>();

    // Constructor
    public CrewPerson(int id, String name, String profilePath) {
        super(id, name, profilePath);
    }
    //Getters
    public int getCredit() {
        return credit;
    }
    
    public int[] getCrewFilms() {
        return crewFilms.toIntArray();
    }

    public void addMovie(int filmId){
        crewFilms.add(filmId);
        credit++;
    }


}

