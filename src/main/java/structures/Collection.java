package structures;

public class Collection {
    int collectionID;
    String collectionName;
    String collectionPoster;
    String collectionBackdrop;
    AList<Integer> movies;

    // Constructor
    public Collection(int collectionID, String collectionName, String collectionPoster, String collectionBackdrop) {
        this.collectionID = collectionID;
        this.collectionName = collectionName;
        this.collectionPoster = collectionPoster;
        this.collectionBackdrop = collectionBackdrop;
        this.movies = new AList<>();
    }

    // Getters
    public int getCollectionID() {
        return collectionID;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getCollectionPoster() {
        return collectionPoster;
    }

    public String getCollectionBackdrop() {
        return collectionBackdrop;
    }

    public void addMovie(int movieId) {
        movies.add(movieId);
    }

    public int[] getMoviesID() {
        int a[] = new int[movies.size()];
        for (int i = 0; i < movies.size(); i++) {
            a[i] = movies.get(i);
        }
        return a;
    }

}
