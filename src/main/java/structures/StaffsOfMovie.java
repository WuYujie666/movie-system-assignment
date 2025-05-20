package structures;

import stores.CastCredit;
import stores.CrewCredit;

public class StaffsOfMovie {
    CastCredit[] cast;
    CrewCredit[] crew;
    int id;
    
    //constructor
    public StaffsOfMovie(CastCredit[] cast, CrewCredit[] crew, int id) {
        this.cast = cast;
        this.crew = crew;
        this.id = id;
    }
    
    //getters
    public CastCredit[] getCast() {
            return cast;
    }
    
    public CrewCredit[] getCrew() {
            return crew;
    }
    
    public int getId() {
            return id;
    }
}
