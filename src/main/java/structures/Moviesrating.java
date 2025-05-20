package structures;

public class Moviesrating {
    public int moviesID;
    public int ratings;
    public float avgRating;

    public Moviesrating(int movieID, int ratings, float avgRating) {
        this.moviesID = movieID;
        this.ratings = ratings;
        this.avgRating = avgRating;
    }

    // 修复了浮点数除法问题
    public static Moviesrating[] getList(AList<Rating> ratingList) {
        Moviesrating a[] = new Moviesrating[ratingList.size()];
        int count = 0; // 跟踪实际填充的数组元素数量

        for (int i = 0; i < ratingList.size(); i++) {
            int mt = ratingList.get(i).getMovieID();
            float rt = ratingList.get(i).getRating();

            // 查找电影是否已在数组中
            int j;
            boolean found = false;
            for (j = 0; j < count; j++) {
                if (mt == a[j].moviesID) {
                    a[j].ratings++;
                    // 修复浮点数除法
                    a[j].avgRating = (1 / (float) a[j].ratings * rt) +
                            (((float) a[j].ratings - 1) / (float) a[j].ratings * a[j].avgRating);
                    found = true;
                    break;
                }
            }

            if (!found) {
                a[count] = new Moviesrating(mt, 1, rt);
                count++;
            }
        }

        // 创建一个大小正确的数组
        Moviesrating[] result = new Moviesrating[count];
        for (int i = 0; i < count; i++) {
            result[i] = a[i];
        }

        return result;
    }
}
