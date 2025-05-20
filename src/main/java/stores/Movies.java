package stores;

import java.time.LocalDate;

import interfaces.IMovies;
import structures.*;

public class Movies implements IMovies {
    Stores stores;
    MyHashMap<Movie> moviesMap = new MyHashMap<Movie>();
    int size;
    MyHashMap<Collection> collectionMap = new MyHashMap<Collection>();
    private int[] cachedAllIDs = null;

    /**
     * The constructor for the Movies data store. This is where you should
     * initialise your data structures.
     * 
     * @param stores An object storing all the different key stores,
     *               including itself
     */
    public Movies(Stores stores) {
        this.stores = stores;
        this.size = 0;
        // TODO Add initialisation of data structure here
    }

    public static int[] intRemoveSpace(int[] a, int num) {
        int b[] = new int[num];
        for (int i = 0; i < num; i++) {
            if (a[i] != 0)
                b[i] = a[i];
        }
        return b;
    }

    /**
     * Adds data about a film to the data structure
     * 
     * @param id               The unique ID for the film
     * @param title            The English title of the film
     * @param originalTitle    The original language title of the film
     * @param overview         An overview of the film
     * @param tagline          The tagline for the film (empty string if there is no
     *                         tagline)
     * @param status           Current status of the film
     * @param genres           An array of Genre objects related to the film
     * @param release          The release date for the film
     * @param budget           The budget of the film in US Dollars
     * @param revenue          The revenue of the film in US Dollars
     * @param languages        An array of ISO 639 language codes for the film
     * @param originalLanguage An ISO 639 language code for the original language of
     *                         the film
     * @param runtime          The runtime of the film in minutes
     * @param homepage         The URL to the homepage of the film
     * @param adult            Whether the film is an adult film
     * @param video            Whether the film is a "direct-to-video" film
     * @param poster           The unique part of the URL of the poster (empty if
     *                         the URL is not known)
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(int id, String title, String originalTitle, String overview, String tagline, String status,
            Genre[] genres, LocalDate release, long budget, long revenue, String[] languages, String originalLanguage,
            double runtime, String homepage, boolean adult, boolean video, String poster) {
        boolean a = moviesMap.add(id, new Movie(id, title, originalTitle, overview, tagline, status, genres, release,
                budget, revenue, languages, originalLanguage, runtime, homepage, adult, video, poster));
        size++;
        cachedAllIDs = null;
        return a;
    }

    /**
     * Removes a film from the data structure, and any data
     * added through this class related to the film
     * 
     * @param id The film ID
     * @return TRUE if the film has been removed successfully, FALSE otherwise
     */
    @Override
    public boolean remove(int id) {
        size--;
        cachedAllIDs = null;
        return moviesMap.remove(id);
    }

    /**
     * Gets all the IDs for all films
     * 
     * @return An array of all film IDs stored
     */
    @Override
    public int[] getAllIDs() {
        if (cachedAllIDs != null) {
            return cachedAllIDs;
        }

        cachedAllIDs = moviesMap.getAllKeys();
        return cachedAllIDs;
    }

    /**
     * Finds the film IDs of all films released within a given range. If a film is
     * released either on the start or end dates, then that film should not be
     * included
     * 
     * @param start The start point of the range of dates
     * @param end   The end point of the range of dates
     * @return An array of film IDs that were released between start and end
     */
    @Override
    public int[] getAllIDsReleasedInRange(LocalDate start, LocalDate end) {
        int[] allIDs = moviesMap.getAllKeys();
        AList<Integer> resultList = new AList<>();

        for (int id : allIDs) {
            Movie movie = moviesMap.get(id);
            LocalDate releaseDate = movie.getRelease();
            if (releaseDate != null && releaseDate.isAfter(start) && releaseDate.isBefore(end)) {
                resultList.add(id);
            }
        }

        // 转换为数组
        int[] result = new int[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            result[i] = resultList.get(i);
        }

        return result;
    }

    /**
     * Gets the title of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The title of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getTitle(int id) {
        Movie m = moviesMap.get(id);
        if (m != null)
            return m.getTitle();
        else
            return null;
    }

    /**
     * Gets the original title of a particular film, given the ID number of that
     * film
     * 
     * @param id The movie ID
     * @return The original title of the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public String getOriginalTitle(int id) {
        Movie m = moviesMap.get(id);
        if (m != null)
            return m.getOriginalTitle();
        else
            return null;
    }

    /**
     * Gets the overview of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The overview of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getOverview(int id) {
        Movie m = moviesMap.get(id);
        if (m != null)
            return m.getOverview();
        else
            return null;
    }

    /**
     * Gets the tagline of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The tagline of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getTagline(int id) {
        Movie m = moviesMap.get(id);
        if (m != null)
            return m.getTagline();
        else
            return null;
    }

    /**
     * Gets the status of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The status of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getStatus(int id) {
        Movie m = moviesMap.get(id);
        if (m != null)
            return m.getStatus();
        else
            return null;
    }

    /**
     * Gets the genres of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The genres of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public Genre[] getGenres(int id) {
        Movie m = moviesMap.get(id);
        if (m != null)
            return m.getGenres();
        else
            return null;
    }

    /**
     * Gets the release date of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The release date of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public LocalDate getRelease(int id) {
        Movie m = moviesMap.get(id);
        if (m != null)
            return m.getRelease();
        else
            return null;
    }

    /**
     * Gets the budget of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The budget of the requested film. If the film cannot be found, then
     *         return -1
     */
    @Override
    public long getBudget(int id) {
        Movie m = moviesMap.get(id);
        if (m != null)
            return m.getBudget();
        else
            return -1;
    }

    /**
     * Gets the revenue of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The revenue of the requested film. If the film cannot be found, then
     *         return -1
     */
    @Override
    public long getRevenue(int id) {
        Movie m = moviesMap.get(id);
        if (m != null)
            return m.getRevenue();
        else
            return -1;
    }

    /**
     * Gets the languages of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The languages of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public String[] getLanguages(int id) {
        Movie m = moviesMap.get(id);
        if (m != null)
            return m.getLanguages();
        else
            return null;
    }

    /**
     * Gets the original language of a particular film, given the ID number of that
     * film
     * 
     * @param id The movie ID
     * @return The original language of the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public String getOriginalLanguage(int id) {
        Movie m = moviesMap.get(id);
        if (m != null)
            return m.getOriginalLanguage();
        else
            return null;
    }

    /**
     * Gets the runtime of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The runtime of the requested film. If the film cannot be found, then
     *         return -1
     */
    @Override
    public double getRuntime(int id) {
        Movie m = moviesMap.get(id);
        if (m != null)
            return m.getRuntime();
        else
            return -1;
    }

    /**
     * Gets the homepage of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The homepage of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getHomepage(int id) {
        Movie m = moviesMap.get(id);
        if (m != null)
            return m.getHomepage();
        else
            return null;
    }

    /**
     * Gets weather a particular film is classed as "adult", given the ID number of
     * that film
     * 
     * @param id The movie ID
     * @return The "adult" status of the requested film. If the film cannot be
     *         found, then return false
     */
    @Override
    public boolean getAdult(int id) {
        Movie m = moviesMap.get(id);
        if (m != null)
            return m.getAdult();
        else
            return false;
    }

    /**
     * Gets weather a particular film is classed as "direct-to-video", given the ID
     * number of that film
     * 
     * @param id The movie ID
     * @return The "direct-to-video" status of the requested film. If the film
     *         cannot be found, then return false
     */
    @Override
    public boolean getVideo(int id) {
        Movie m = moviesMap.get(id);
        if (m != null)
            return m.getVideo();
        else
            return false;
    }

    /**
     * Gets the poster URL of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The poster URL of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public String getPoster(int id) {
        Movie m = moviesMap.get(id);
        if (m != null)
            return m.getPoster();
        else
            return null;
    }

    /**
     * Sets the average IMDb score and the number of reviews used to generate this
     * score, for a particular film
     * 
     * @param id          The movie ID
     * @param voteAverage The average score on IMDb for the film
     * @param voteCount   The number of reviews on IMDb that were used to generate
     *                    the average score for the film
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean setVote(int id, double voteAverage, int voteCount) {
        Movie m = moviesMap.get(id);
        if (m != null) {
            m.setVote(voteAverage, voteCount);
            return true;
        } else
            return false;
    }

    /**
     * Gets the average score for IMDb reviews of a particular film, given the ID
     * number of that film
     * 
     * @param id The movie ID
     * @return The average score for IMDb reviews of the requested film. If the film
     *         cannot be found, then return -1
     */
    @Override
    public double getVoteAverage(int id) {
        Movie m = moviesMap.get(id);
        if (m != null)
            return m.getVoteAverage();
        else
            return -1;
    }

    /**
     * Gets the amount of IMDb reviews used to generate the average score of a
     * particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The amount of IMDb reviews used to generate the average score of the
     *         requested film. If the film cannot be found, then return -1
     */
    @Override
    public int getVoteCount(int id) {
        Movie m = moviesMap.get(id);
        if (m != null)
            return m.getVoteCount();
        else
            return -1;
    }

    /**
     * Adds a given film to a collection. The collection is required to have an ID
     * number, a name, and a URL to a poster for the collection
     * 
     * @param filmID                 The movie ID
     * @param collectionID           The collection ID
     * @param collectionName         The name of the collection
     * @param collectionPosterPath   The URL where the poster can
     *                               be found
     * @param collectionBackdropPath The URL where the backdrop can
     *                               be found
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addToCollection(int filmID, int collectionID, String collectionName, String collectionPosterPath,
            String collectionBackdropPath) {
        if (collectionMap.isContains(collectionID) == false) {
            Collection c = new Collection(collectionID, collectionName, collectionPosterPath, collectionBackdropPath);
            collectionMap.add(collectionID, c);
            c.addMovie(filmID);
        } else {
            Collection c = collectionMap.get(collectionID);
            c.addMovie(filmID);
        }
        Movie m = moviesMap.get(filmID);
        if (m != null) {
            m.addToCollection(collectionID);
            return true;
        } else
            return false;// ??
    }

    /**
     * Get all films that belong to a given collection
     * 
     * @param collectionID The collection ID to be searched for
     * @return An array of film IDs that correspond to the given collection ID. If
     *         there are no films in the collection ID, or if the collection ID is
     *         not valid, return an empty array.
     */
    @Override
    public int[] getFilmsInCollection(int collectionID) {
        Collection c = collectionMap.get(collectionID);
        if (c != null)
            return c.getMoviesID();
        else
            return new int[0];
    }

    /**
     * Gets the name of a given collection
     * 
     * @param collectionID The collection ID
     * @return The name of the collection. If the collection cannot be found, then
     *         return null
     */
    @Override
    public String getCollectionName(int collectionID) {
        Collection c = collectionMap.get(collectionID);
        if (c != null)
            return c.getCollectionName();
        else
            return null;
    }

    /**
     * Gets the poster URL for a given collection
     * 
     * @param collectionID The collection ID
     * @return The poster URL of the collection. If the collection cannot be found,
     *         then return null
     */
    @Override
    public String getCollectionPoster(int collectionID) {
        Collection c = collectionMap.get(collectionID);
        if (c != null)
            return c.getCollectionPoster();
        else
            return null;
    }

    /**
     * Gets the backdrop URL for a given collection
     * 
     * @param collectionID The collection ID
     * @return The backdrop URL of the collection. If the collection cannot be
     *         found, then return null
     */
    @Override
    public String getCollectionBackdrop(int collectionID) {
        Collection c = collectionMap.get(collectionID);
        if (c != null)
            return c.getCollectionBackdrop();
        else
            return null;
    }

    /**
     * Gets the collection ID of a given film
     * 
     * @param filmID The movie ID
     * @return The collection ID for the requested film. If the film cannot be
     *         found, then return -1
     */
    @Override
    public int getCollectionID(int filmID) {
        Movie m = moviesMap.get(filmID);
        if (m != null)
            return m.getCollection();
        else
            return -1;
    }

    /**
     * Sets the IMDb ID for a given film
     * 
     * @param filmID The movie ID
     * @param imdbID The IMDb ID
     * @return TRUE if the data able to be set, FALSE otherwise
     */
    @Override
    public boolean setIMDB(int filmID, String imdbID) {
        Movie m = moviesMap.get(filmID);
        if (m != null) {
            m.setImdb(imdbID);
            return true;
        } else
            return false;
    }

    /**
     * Gets the IMDb ID for a given film
     * 
     * @param filmID The movie ID
     * @return The IMDb ID for the requested film. If the film cannot be found,
     *         return null
     */
    @Override
    public String getIMDB(int filmID) {
        Movie m = moviesMap.get(filmID);
        if (m != null)
            return m.getImdb();
        else
            return null;
    }

    /**
     * Sets the popularity of a given film. If the popularity for a film already
     * exists, replace it with the new value
     * 
     * @param id         The movie ID
     * @param popularity The popularity of the film
     * @return TRUE if the data able to be set, FALSE otherwise
     */
    @Override
    public boolean setPopularity(int id, double popularity) {
        Movie m = moviesMap.get(id);
        if (m != null) {
            m.setPopularity(popularity);
            return true;
        } else
            return false;
    }

    /**
     * Gets the popularity of a given film
     * 
     * @param id The movie ID
     * @return The popularity value of the requested film. If the film cannot be
     *         found, then return -1.0. If the popularity has not been set, return
     *         0.0
     */
    @Override
    public double getPopularity(int id) {
        Movie m = moviesMap.get(id);
        if (m != null)
            return m.getPopularity();
        else
            return -1.0;
    }

    /**
     * Adds a production company to a given film
     * 
     * @param id      The movie ID
     * @param company A Company object that represents the details on a production
     *                company
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addProductionCompany(int id, Company company) {
        Movie m = moviesMap.get(id);
        if (m != null) {
            m.addCompany(company);
            return true;
        } else
            return false;
    }

    /**
     * Adds a production country to a given film
     * 
     * @param id      The movie ID
     * @param country A ISO 3166 string containing the 2-character country code
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addProductionCountry(int id, String country) {
        Movie m = moviesMap.get(id);
        if (m != null) {
            m.addCountry(country);
            return true;
        } else
            return false;
    }

    /**
     * Gets all the production companies for a given film
     * 
     * @param id The movie ID
     * @return An array of Company objects that represent all the production
     *         companies that worked on the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public Company[] getProductionCompanies(int id) {
        Movie m = moviesMap.get(id);
        if (m != null)
            return m.getCompany();
        else
            return null;
    }

    /**
     * Gets all the production companies for a given film
     * 
     * @param id The movie ID
     * @return An array of Strings that represent all the production countries (in
     *         ISO 3166 format) that worked on the requested film. If the film
     *         cannot be found, then return null
     */
    @Override
    public String[] getProductionCountries(int id) {
        Movie m = moviesMap.get(id);
        if (m != null)
            return m.getCountry();
        else
            return null;
    }

    /**
     * States the number of movies stored in the data structure
     * 
     * @return The number of movies stored in the data structure
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Produces a list of movie IDs that have the search term in their title,
     * original title or their overview
     * 
     * @param searchTerm The term that needs to be checked
     * @return An array of movie IDs that have the search term in their title,
     *         original title or their overview. If no movies have this search term,
     *         then an empty array should be returned
     */
    @Override
    public int[] findFilms(String searchTerm) {
        int[] allIDs = moviesMap.getAllKeys();
        AList<Integer> resultList = new AList<>();

        for (int id : allIDs) {
            Movie movie = moviesMap.get(id);
            if (movie.getTitle().contains(searchTerm) ||
                    movie.getOriginalTitle().contains(searchTerm) ||
                    movie.getOverview().contains(searchTerm)) {
                resultList.add(id);
            }
        }

        // 转换为数组
        int[] result = new int[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            result[i] = resultList.get(i);
        }

        return result;
    }

}
