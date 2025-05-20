# Report
## Choice of the data structure
1. 目前我们学习的数据结构有ArrayList、LinkedList、HashMap、BinarySearchTree等等。我选择主要使用ArrayList储存rating，使用一种简易的哈希表来储存Movie and Credit。
2. I choose ArrayList to store ratings的首要原因是ArrayList时最易于实现的一种数据结构。其次，由于不需要关注元素之间的顺序，不存在删除和添加中间元素造成O(n)复杂度的操作，不存在使用AraayList时时间消耗长的问题。虽然如此，但软件在UI界面中仍然在加载17000条rating时卡死，这说明我选择的ArrayList在处理大量数据时存在问题。
3. 使用哈希表来储存Movie和Credit的原因是它们具有唯一的id，非常适合作为key值，不会出现冲突。这让我在自己实现哈希表时方便很多。不使用哈希表储存rating是因为它并没有这样的id存在（userId和movieId都是可重复的）。另外，由于movie类和credit类中涉及了较多的get和set操作，而哈希表可以以O(1)的时间复杂度快速地get和set。最后，我们知道电影的id不会超过6位数字，因此也不需要对哈希表进行扩容。
   ## How to build the required operations
    1. Ratings类
       1. 首先，由于一条评论由 userID，movieId,rating,timestamp 组成，它们关系密切，所以我写了一个Rating类将它们捆绑在一起。然后，我创建了一个Rating类的ArrayList用于存放数据。
       2. 我写了一个private Rating findRating(int userID, int movieID)，通过遍历ArrayList以查找Rating对象。add，remove，set由ArrayList提供。对于getMovieRatings、getUserRatings等方法，我遍历ArrayList来获得需要的数组。对于getMostRatedMovies、getTopAverageRatedMovies、getNumRatings方法，我建立Moviesrating将每个电影的movieId、rating数量与平均rating捆绑在一起,在每次调用相关方法时获得一个Moviesrating类的arrayList，然后再对它进行操作。
    2. Movies类
       1. 创建Movie类用于储存一个电影的信息。创建一个Movie类的HashMap来储存所有电影。
       2. add,remove操作依赖于哈希表。
       3. getAllIDs、getAllIDsReleasedInRange等操作需要遍历哈希表获得所有id。
       4. 使用电影的id的get操作可以直接将id作为key来查找信息，非常快速。例如getTitle。
       5. setVote、setIMDB、setPopularity就是给Mobie类对象添加新属性
       6. 对于Collection，我创建了一个Collection类用于存储每个Collection的信息，包括一个数组，里面存放了属于这个collection的电影的id。使用哈希表存放所有Collection(使用collectionID作为key)。同时，Movie类里也由一个属性存储CollectionID。这样Movie和Collection之间可以互相指向。（更好的做法是存储对象而非id，但会让代码变得更复杂）。
       7. 在Movie类中使用ArrayList储存companies和countries
       8. findFilms
    3. 3. Credit Class(similar to Movies)
   1. Create a StaffsOfMovie class to store the cast and crew credit and id of a certain movie. Create a HashMap of StaffsOfMovie objects to store all movies' credits. Add, remove,get and size operations depend on the hash table. 
   2. 我特别维护了两个用于存放唯一的cast和crew的哈希表————CastPerson类的castMap和CrewPerson类的crewMap，以Cast和Crew的id作为key。每次add后，它们都会更新。这会让查找变得快捷，但每次添加会变慢，而且移除后更新列表很困难，同时也难以debug。
   3. CastPerson和CrewPerson类也是我自己写的，Castperson是Person的子类，除了person类的属性，还包含了credit、和两个储存cast films 和 star in films 的ArrayList。CrewPerson也是相似的。
   4. 后续的很多操作都基于castMap和crewMap。例如getUniqueCast是遍历castMap后输出，getCastFilms与可以用key得到CastPerson对象，之后返回它CastFilms属性。值得一提的是，由于CastPerson是Person的子类，假如方法要求返回Person对象，我可以返回一个CastPerson对象，它很好获得。这可以应用到getCast等方法中
   5. 
        