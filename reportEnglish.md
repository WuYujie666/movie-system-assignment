# Report of CourseWork
吴昱颉 2023141520196 section1
## Choice of the Data Structure
1. The data structures we have learned so far include ArrayList, LinkedList, HashMap, and BinarySearchTree, etc. The primary reason for using ArrayList to store ratings is that it is the easiest to implement among all data structures. Additionally, since we don't care the order of elements, there are no operations with O(n) complexity when deleting or adding elements in the middle. This solves the problem of long time consumption when using ArrayList. However, the software still freezes when loading more than 17,000 ratings in the UI, indicating that there is a problem with the choice of ArrayList for handling large amounts of data. 

3. I choose hash table to store Movie and Credit, because they have unique ids, which makes them suitable as key values and avoids conflicts. This makes it easier for me to implement my own hash table `MyHashMap`. Unlike ArrayList, hash table operations is fast, get and set can be performed in O(1) time complexity. Additionally, since the movie id doesn't go beyond 6 digits, there is no need to expand the hash table.

## How to Build Required Operations
1. Rating Class
    1. First, since a rating consists of userID, movieId, rating, and timestamp, which are closely related, I wrote a `Rating` class to bundle them together. Then, I created an ArrayList of `Rating` objects to store the data.
    2. I wrote a `findRating` method to find the Rating object by traversing the ArrayList. Add, remove, and set operations are provided by ArrayList. For `getMovieRatings`, `getUserRatings`, etc., I traverse the ArrayList to get the required arrays. For `getMostRatedMovies`, `getTopAverageRatedMovies`, and `getNumRatings` methods, I create a `Moviesrating` class to bundle each movie's movieId, rating count, and average rating, and then obtain an ArrayList of `Moviesrating` objects and operate on it.

2. Movie Class
    1. Create a `Movie` class to store the information of a movie. Create a HashMap of `Movie` objects to store all movies.
    2. Add and remove operations depend on the hash table.
    3. `GetAllIDs`, `GetAllIDsReleasedInRange`, etc., require traversing the hash table to get all ids.
    4. Using the movie id's get operation can directly use the id as a key to find the information, which is very fast, such as `GetTitle`.
    5. `SetVote`, `SetIMDB`, `SetPopularity` are new attributes added to the Movie class.
    6. For collections, I created a `Collection` class to store each Collection's information, including an array containing the ids of movies belonging to this collection. Use a hash table to store all Collections (using `collectionID` as the key). The `Movie` class also has an attribute to store the `CollectionID`. This way, the Movie and Collection can point to each other (a better approach would be to store objects rather than ids, but it would make the code more complex).
    7. Use ArrayList to store `companies` and `countries` in the Movie class. Because the number of companies and countries is small.
    8. I maintain a variable called `size` to store the size of existing movies.
    9. To findFilms by search term, we have to traverse the hash table.
3. Credit Class(similar to Movies)
   1. Create a `StaffsOfMovie` class to store the cast and crew credit and id of a certain movie. Create a HashMap of `StaffsOfMovie` ob jects to store all movies' credits. Add, remove,get and size operations depend on the hash table. 
   2. I particularly maintained two hash tables for storing unique cast and crew. The `castMap` of `CastPerson`class and crewMap of `CrewPerson`, using Cast's and Crew's `id` as keys. After each add, the hash table will be updated. This will make the search faster, but each addition will be slower, and it will be difficult to update the list in remove method.It's also difficult to debug.
   3. `CastPerson` and `CrewPerson` classes are also written by myself, which are subclasses of `Person`. In addition to the attributes of the person class, CastPerson also includes information of cast, like `credit`, two ArrayLists `castFilms` and `castStarInFilms`. `CrewPerson` is similar.
   4. Many operations are based on castMap and crewMap. For example, `getUniqueCast` traverses the castMap and outputs it. `getCastFilms` can use the key to get the `CastPerson` object and then return its `CastFilms` attribute. By the way, since `CastPerson` is a subclass of `Person`, if the method requires returning a `Person` object, I can return a `CastPerson` object, which is easy to obtain. This can be applied to methods such as `getCast`.

