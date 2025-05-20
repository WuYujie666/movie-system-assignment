package structures;

import java.time.LocalDateTime;

public class Rating{
    int userID;
    int movieID;
    float rating;
    LocalDateTime timestamp;

    //constructor
    public Rating(int userID, int movieID, float rating,  LocalDateTime timestamp){
        this.userID = userID;
        this.movieID = movieID;
        this.rating = rating;
        this.timestamp = timestamp;
    }
    public int getUserID(){
        return userID;
    }
    public int getMovieID(){
        return movieID;
    }
    public float getRating(){
        return rating;
    }
    public LocalDateTime getTimestamp(){
        return timestamp;
    }
    public void changeRating(float newRating,LocalDateTime newTimestamp){
        rating = newRating;
        timestamp = newTimestamp;
    }
}