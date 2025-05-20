package structures;

import java.time.LocalDate;

import stores.Company;
import stores.Genre;
import structures.AList;

public class Movie {

    int id;
    String title;
    String originalTitle;
    String overview;
    String tagline;

    String status;
    Genre[] genres;
    LocalDate release;
    long budget;
    long revenue;
    String[] languages;
    String originalLanguage;
    double runtime;
    String homepage;
    boolean adult;
    boolean video;
    String poster;

    double voteAverage;
    int voteCount;
    int collection;
    String imdbID;

    double popularity;
    AList<Company> companies;
    AList<String> countries;
//    Company[] companies;
//    String[] countries;

    public Movie(int id, String title, String originalTitle, String overview, String tagline, String status, Genre[] genres, LocalDate release, long budget, long revenue, String[] languages, String originalLanguage, double runtime, String homepage, boolean adult, boolean video, String poster
    ){
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.tagline = tagline;
        this.status = status;
        this.genres = genres;
        this.release = release;
        this.budget = budget;
        this.revenue = revenue;
        this.languages = languages;
        this.originalLanguage = originalLanguage;
        this.runtime = runtime;
        this.homepage = homepage;
        this.adult = adult;
        this.video = video;
        this.poster = poster;
        this.companies = new AList<Company>();
        this.countries = new AList<String>();
    }

    public int getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getOriginalTitle() {
        return originalTitle;
    }
    
    public String getOverview() {
        return overview;
    }
    
    public String getTagline() {
        return tagline;
    }

    public String getStatus() {
        return status;
    }
    
    public Genre[] getGenres() {
        return genres;
    }
    
    public LocalDate getRelease() {
        return release;
    }

    public long getBudget() {
        return budget;
    }
    
    public long getRevenue() {
        return revenue;
    }
    
    public String[] getLanguages() {
        return languages;
    }
    
    public String getOriginalLanguage() {
        return originalLanguage;
    }
    
    public double getRuntime() {
        return runtime;
    }
    
    public String getHomepage() {
        return homepage;
    }
    
    public boolean getAdult() {
        return adult;
    }

    public boolean getVideo() {
        return video;
    }
    
    public String getPoster() {
        return poster;
    }

    public double getVoteAverage() {
        return voteAverage;
    }
    
    public void setVote(double voteAverage, int voteCount) {
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }
    
    public int getVoteCount() {
        return voteCount;
    }
    
    public void addToCollection(int collectionID) {
        collection=collectionID;
    }
    
    public int getCollection() {
        return collection;
    }

    public String getImdb() {
        return imdbID;
    }
    
    public void setImdb(String imdbID) {
        this.imdbID = imdbID;
    }
    
    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public Company[] getCompany() {
        Company[] companiesList=new Company[companies.size()];
        for(int i=0;i<companies.size();i++){
            companiesList[i]=companies.get(i);
        }
        return companiesList;
    }
    
    public void addCompany(Company company) {
        companies.add(company);
    }
    public void addCountry(String country) {
        countries.add(country);
    }
    
    public String[] getCountry() {
        String[] countriesList=new String[countries.size()];
        for(int i=0;i<countries.size();i++){
            countriesList[i]=countries.get(i);
        }
        return countriesList;
    }
   }