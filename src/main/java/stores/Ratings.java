package stores;

import java.time.LocalDateTime;
import interfaces.IRatings;
import structures.*;
import stores.Movies;

public class Ratings implements IRatings {
    Stores stores;
    AList<Rating> ratingList = new AList<Rating>();
    int size;

    // AList <Integer> moviesList = new AList<Integer>();
    // AList <Integer> usersList = new AList<Integer>();
    /**
     * The constructor for the Ratings data store. This is where you should
     * initialise your data structures.
     * 
     * @param stores An object storing all the different key stores,
     *               including itself
     */
    public Ratings(Stores stores) {
        this.stores = stores;
        this.size = 0;

    }

    /**
     * Adds a rating to the data structure. The rating is made unique by its user ID
     * and its movie ID
     * 
     * @param userID    The user ID
     * @param movieID   The movie ID
     * @param rating    The rating gave to the film by this user (between 0 and 5
     *                  inclusive)
     * @param timestamp The time at which the rating was made
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(int userID, int movieID, float rating, LocalDateTime timestamp) {
        //
        Rating t = new Rating(userID, movieID, rating, timestamp);
        if (findRating(userID, movieID) == null) {
            ratingList.add(t);
            size++;
            return true;
        } else
            return false;
    }

    /**
     * Removes a given rating, using the user ID and the movie ID as the unique
     * identifier
     * 
     * @param userID  The user ID
     * @param movieID The movie ID
     * @return TRUE if the data was removed successfully, FALSE otherwise
     */

    private Rating findRating(int userID, int movieID) {
        for (int i = 0; i < ratingList.size(); i++) {
            if (ratingList.get(i).getUserID() == userID && ratingList.get(i).getMovieID() == movieID) {
                return ratingList.get(i);
            }
        }
        return null;
    }

    @Override
    public boolean remove(int userid, int movieID) {
        //
        Rating t = findRating(userid, movieID);
        if (t != null) {
            ratingList.remove(t);
            size--;
            return true;
        } else
            return false;
    }

    /**
     * Sets a rating for a given user ID and movie ID. Therefore, should the given
     * user have already rated the given movie, the new data should overwrite the
     * existing rating. However, if the given user has not already rated the given
     * movie, then this rating should be added to the data structure
     * 
     * @param userID    The user ID
     * @param movieID   The movie ID
     * @param rating    The new rating to be given to the film by this user (between
     *                  0 and 5 inclusive)
     * @param timestamp The time at which the rating was made
     * @return TRUE if the data able to be added/updated, FALSE otherwise
     */
    @Override
    public boolean set(int userID, int movieID, float rating, LocalDateTime timestamp) {

        Rating t = findRating(userID, movieID);
        if (t == null) {
            ratingList.add(new Rating(userID, movieID, rating, timestamp));
        } else {
            t.changeRating(rating, timestamp);
        }
        return true;
    }

    /**
     * Get all the ratings for a given film
     * 
     * @param movieID The movie ID
     * @return An array of ratings. If there are no ratings or the film cannot be
     *         found, then return an empty array
     */
    @Override
    public float[] getMovieRatings(int movieID) {
        AList<Float> ratings = new AList<>();

        for (int i = 0; i < ratingList.size(); i++) {
            if (ratingList.get(i).getMovieID() == movieID) {
                ratings.add(ratingList.get(i).getRating());
            }
        }
        // 转换为数组
        float[] result = new float[ratings.size()];
        for (int i = 0; i < ratings.size(); i++) {
            result[i] = ratings.get(i);
        }
        return result;
    }

    /**
     * Get all the ratings for a given user
     * 
     * @param userID The user ID
     * @return An array of ratings. If there are no ratings or the user cannot be
     *         found, then return an empty array
     */
    @Override
    public float[] getUserRatings(int userID) {
        AList<Float> ratings = new AList<>();

        for (int i = 0; i < ratingList.size(); i++) {
            if (ratingList.get(i).getUserID() == userID) {
                ratings.add(ratingList.get(i).getRating());
            }
        }

        // 转换为数组
        float[] result = new float[ratings.size()];
        for (int i = 0; i < ratings.size(); i++) {
            result[i] = ratings.get(i);
        }

        return result;
    }

    /**
     * Get the average rating for a given film
     * 
     * @param movieID The movie ID
     * @return Produces the average rating for a given film.
     *         If the film cannot be found in ratings, but does exist in the movies
     *         store, return 0.0f.
     *         If the film cannot be found in ratings or movies stores, return
     *         -1.0f.
     */
    @Override
    public float getMovieAverageRating(int movieID) {
        float sum = 0;
        int count = 0;

        for (int i = 0; i < ratingList.size(); i++) {
            if (ratingList.get(i).getMovieID() == movieID) {
                sum += ratingList.get(i).getRating();
                count++;
            }
        }

        if (count > 0) {
            return sum / count;
        } else if (movieExistsInStore(movieID)) {
            return 0.0f;
        } else {
            return -1.0f;
        }
    }

    /**
     * Get the average rating for a given user
     * 
     * @param userID The user ID
     * @return Produces the average rating for a given user. If the user cannot be
     *         found, or there are no rating, return -1
     */
    @Override
    public float getUserAverageRating(int userID) {
        float sum = 0;
        int count = 0;

        // 直接遍历评分列表，计算指定用户的评分总和和数量
        for (int i = 0; i < ratingList.size(); i++) {
            Rating rating = ratingList.get(i);
            if (rating.getUserID() == userID) {
                sum += rating.getRating();
                count++;
            }
        }

        // 如果有评分，计算平均值；否则返回-1
        if (count > 0) {
            return sum / count;
        } else {
            return -1;
        }
    }

    /**
     * Gets the top N movies with the most ratings, in order from most to least
     * 
     * @param num The number of movies that should be returned
     * @return A sorted array of movie IDs with the most ratings. The array should
     *         be
     *         no larger than num. If there are less than num movies in the store,
     *         then the array should be the same length as the number of movies
     */
    @Override
    public int[] getMostRatedMovies(int num) {
        // 使用哈希表统计每部电影的评分数量
        MyHashMap<Integer> movieRatingCountMap = new MyHashMap<>();

        // 第一次遍历：统计每部电影的评分数量
        for (int i = 0; i < ratingList.size(); i++) {
            int movieId = ratingList.get(i).getMovieID();
            Integer count = movieRatingCountMap.get(movieId);

            if (count == null) {
                movieRatingCountMap.add(movieId, 1);
            } else {
                // 移除后重新添加
                movieRatingCountMap.remove(movieId);
                movieRatingCountMap.add(movieId, count + 1);
            }
        }

        // 获取所有电影ID
        int[] movieIds = movieRatingCountMap.getAllKeys();

        // 创建电影评分对象数组
        Moviesrating[] movieRatings = new Moviesrating[movieIds.length];
        for (int i = 0; i < movieIds.length; i++) {
            int movieId = movieIds[i];
            int ratingCount = movieRatingCountMap.get(movieId);
            movieRatings[i] = new Moviesrating(movieId, ratingCount, 0); // 第三个参数avgRating在这里不重要
        }

        // 使用快速排序按评分数量降序排序
        quickSort(movieRatings, 0, movieRatings.length - 1, (m1, m2) -> m1.ratings > m2.ratings);

        // 提取结果
        int resultSize = Math.min(num, movieRatings.length);
        int[] result = new int[resultSize];
        for (int i = 0; i < resultSize; i++) {
            result[i] = movieRatings[i].moviesID;
        }

        return result;
    }

    /**
     * Gets the top N users with the most ratings, in order from most to least
     * 
     * @param num The number of users that should be returned
     * @return A sorted array of user IDs with the most ratings. The array should be
     *         no larger than num. If there are less than num users in the store,
     *         then the array should be the same length as the number of users
     */

    @Override
    public int[] getMostRatedUsers(int num) {
        // 使用哈希表统计每个用户的评分数量
        MyHashMap<Integer> userRatingCountMap = new MyHashMap<>();

        // 第一次遍历：统计每个用户的评分数量
        for (int i = 0; i < ratingList.size(); i++) {
            int userId = ratingList.get(i).getUserID();
            Integer count = userRatingCountMap.get(userId);

            if (count == null) {
                userRatingCountMap.add(userId, 1);
            } else {
                // 由于MyHashMap不支持更新，我们需要先移除再添加
                userRatingCountMap.remove(userId);
                userRatingCountMap.add(userId, count + 1);
            }
        }

        // 获取所有用户ID
        int[] userIds = userRatingCountMap.getAllKeys();

        // 创建用户评分对象数组
        Usersrating[] userRatings = new Usersrating[userIds.length];
        for (int i = 0; i < userIds.length; i++) {
            int userId = userIds[i];
            int ratingCount = userRatingCountMap.get(userId);
            userRatings[i] = new Usersrating(userId, ratingCount);
        }

        // 使用快速排序按评分数量降序排序
        quickSort(userRatings, 0, userRatings.length - 1, (u1, u2) -> u1.ratings > u2.ratings);

        // 提取结果
        int resultSize = Math.min(num, userRatings.length);
        int[] result = new int[resultSize];
        for (int i = 0; i < resultSize; i++) {
            result[i] = userRatings[i].userID;
        }

        return result;
    }

    /**
     * Gets the number of ratings in the data structure
     * 
     * @return The number of ratings in the data structure
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Get the number of ratings that a movie has
     * 
     * @param movieid The movie id to be found
     * @return The number of ratings the specified movie has.
     *         If the movie exists in the movies store, but there
     *         are no ratings for it, then return 0. If the movie
     *         does not exist in the ratings or movies store, then
     *         return -1
     */
    @Override
    public int getNumRatings(int movieid) {
        // 直接计算指定电影的评分数量，不需要生成所有电影的列表
        int count = 0;
        for (int i = 0; i < ratingList.size(); i++) {
            if (ratingList.get(i).getMovieID() == movieid) {
                count++;
            }
        }
        if (count > 0) {
            return count;
        } else {
            // 由于不能直接访问stores.movies，我们需要找到其他方式检查电影是否存在
            // 我们可以使用stores对象的其他方法或属性

            // 例如，如果Ratings类有一个方法可以访问电影信息
            if (movieExistsInStore(movieid)) {
                return 0; // 电影存在但没有评分
            } else {
                return -1; // 电影不存在
            }
        }
    }

    private boolean movieExistsInStore(int movieId) {
        try {
            // 获取所有电影ID
            int[] allMovieIds = stores.getMovies().getAllIDs();

            // 检查movieId是否在allMovieIds数组中
            for (int id : allMovieIds) {
                if (id == movieId) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            // 出现异常，假设电影不存在
            return false;
        }
    }

    /**
     * Get the highest average rated film IDs, in order of there average rating
     * (hightst first).
     * 
     * @param numResults The maximum number of results to be returned
     * @return An array of the film IDs with the highest average ratings, highest
     *         first. If there are less than num movies in the store,
     *         then the array should be the same length as the number of movies
     */
    @Override
    public int[] getTopAverageRatedMovies(int numResults) {
        // 收集电影评分信息
        MovieRatingCollection ratingCollection = collectMovieRatings();

        // 按平均评分降序排序
        sortMoviesByRatingDesc(ratingCollection.movies, ratingCollection.count);

        // 提取结果
        int resultSize = Math.min(numResults, ratingCollection.count);
        int[] result = new int[resultSize];
        for (int i = 0; i < resultSize; i++) {
            result[i] = ratingCollection.movies[i].moviesID;
        }

        return result;
    }

    // 辅助类，存储电影评分集合
    private static class MovieRatingCollection {
        Moviesrating[] movies;
        int count;

        public MovieRatingCollection(Moviesrating[] movies, int count) {
            this.movies = movies;
            this.count = count;
        }
    }

    /**
     * 收集所有电影的评分信息
     * 
     * @return 电影评分集合
     */
    private MovieRatingCollection collectMovieRatings() {
        // 使用哈希表存储电影ID到索引的映射
        MyHashMap<Integer> movieToIndexMap = new MyHashMap<>();

        // 创建数组存储电影评分信息
        Moviesrating[] movieRatings = new Moviesrating[5000]; // 假设不超过5000部电影
        int movieCount = 0;

        for (int i = 0; i < ratingList.size(); i++) {
            int movieId = ratingList.get(i).getMovieID();
            float rating = ratingList.get(i).getRating();

            // 检查电影是否已在数组中
            Integer indexObj = movieToIndexMap.get(movieId);

            if (indexObj == null) {
                // 添加新电影
                movieRatings[movieCount] = new Moviesrating(movieId, 1, rating);
                movieToIndexMap.add(movieId, movieCount);
                movieCount++;
            } else {
                // 更新现有电影评分
                updateMovieAverageRating(movieRatings[indexObj], rating);
            }
        }

        return new MovieRatingCollection(movieRatings, movieCount);
    }

    /**
     * 更新电影的平均评分
     * 
     * @param movie     电影评分对象
     * @param newRating 新的评分
     */
    private void updateMovieAverageRating(Moviesrating movie, float newRating) {
        movie.ratings++;
        // 使用加权平均公式更新平均评分
        float weight = 1.0f / movie.ratings;
        movie.avgRating = (weight * newRating) + ((1.0f - weight) * movie.avgRating);
    }

    /**
     * 按平均评分降序排序电影数组
     * 
     * @param movies 电影数组
     * @param count  有效电影数量
     */
    private void sortMoviesByRatingDesc(Moviesrating[] movies, int count) {
        quickSort(movies, 0, count - 1, (a, b) -> a.avgRating > b.avgRating);
    }

    // 更通用的比较器接口
    private interface Comparator<T> {
        boolean compare(T a, T b);
    }

    // 通用快速排序方法
    private <T> void quickSort(T[] arr, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pi = partition(arr, low, high, comparator);
            quickSort(arr, low, pi - 1, comparator);
            quickSort(arr, pi + 1, high, comparator);
        }
    }

    private <T> int partition(T[] arr, int low, int high, Comparator<T> comparator) {
        T pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparator.compare(arr[j], pivot)) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    private <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}