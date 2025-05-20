package stores;

import structures.*;
import interfaces.ICredits;

public class Credits implements ICredits {
    Stores stores;
    MyHashMap<StaffsOfMovie> staffMap;
    MyHashMap<CastPerson> castMap;
    MyHashMap<CrewPerson> crewMap;

    // AList<CastCredit> castList;
    // AList<CrewCredit> crewList;
    /**
     * The constructor for the Credits data store. This is where you should
     * initialise your data structures.
     * 
     * @param stores An object storing all the different key stores,
     *               including itself
     */

    public Credits(Stores stores) {
        this.stores = stores;
        staffMap = new MyHashMap<StaffsOfMovie>();
        castMap = new MyHashMap<CastPerson>();
        crewMap = new MyHashMap<CrewPerson>();
    }

    /**
     * Adds data about the people who worked on a given film. The movie ID should be
     * unique
     * 
     * @param cast An array of all cast members that starred in the given film
     * @param crew An array of all crew members that worked on a given film
     * @param id   The (unique) movie ID
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(CastCredit[] cast, CrewCredit[] crew, int id) {
        // 更新列表
        CastCredit[] a = cast;
        for (int j = 0; j < a.length; j++) {// 访问电影中每个演员
            if (a[j] != null) {
                CastPerson castPerson = new CastPerson(a[j].getID(), a[j].getName(), a[j].getProfilePath());
                boolean isUnique = castMap.add(a[j].getID(), castPerson);// 如果重复则不会添加
                castMap.get(a[j].getID()).addMovie(id, a[j].getOrder());// 添加电影和主演电影（无论是否重合）
                // 请你统一编码风格 咋一会Id一会ID一会id呢，受不了了
            }
        }

        CrewCredit[] b = crew;
        for (int j = 0; j < b.length; j++) {// 访问电影中每个演员
            if (b[j] != null) {
                CrewPerson crewPerson = new CrewPerson(b[j].getID(), b[j].getName(), b[j].getProfilePath());
                crewMap.add(b[j].getID(), crewPerson);// 如果重复则不会添加
                crewMap.get(b[j].getID()).addMovie(id);// 添加电影和主演电影（无论是否重合）
            }
        }
        return staffMap.add(id, new StaffsOfMovie(cast, crew, id));
    }

    /**
     * Remove a given films data from the data structure
     * 
     * @param id The movie ID
     * @return TRUE if the data was removed, FALSE otherwise
     */
    @Override
    public boolean remove(int id) {
        return staffMap.remove(id);
    }

    /**
     * Gets all the cast members for a given film
     * 
     * @param filmID The movie ID
     * @return An array of CastCredit objects, one for each member of cast that is
     *         in the given film. The cast members should be in "order" order. If
     *         there is no cast members attached to a film, or the film canot be
     *         found, then return an empty array
     */
    @Override
    public CastCredit[] getFilmCast(int filmID) {
        StaffsOfMovie a = staffMap.get(filmID);
        if (a != null) {
            return a.getCast();
        } else
            return new CastCredit[0];
    }

    /**
     * Gets all the crew members for a given film
     * 
     * @param filmID The movie ID
     * @return An array of CrewCredit objects, one for each member of crew that is
     *         in the given film. The crew members should be in ID order. If there
     *         is no crew members attached to a film, or the film canot be found,
     *         then return an empty array
     */
    @Override
    public CrewCredit[] getFilmCrew(int filmID) {
        StaffsOfMovie a = staffMap.get(filmID);
        if (a != null) {
            return a.getCrew();
        } else
            return new CrewCredit[0];
    }

    /**
     * Gets the number of cast that worked on a given film
     * 
     * @param filmID The movie ID
     * @return The number of cast member that worked on a given film. If the film
     *         cannot be found, then return -1
     */
    @Override
    public int sizeOfCast(int filmID) {
        StaffsOfMovie a = staffMap.get(filmID);
        if (a != null) {
            return a.getCast().length;
        } else
            return -1;
    }

    /**
     * Gets the number of crew that worked on a given film
     * 
     * @param filmID The movie ID
     * @return The number of crew member that worked on a given film. If the film
     *         cannot be found, then return -1
     */
    @Override
    public int sizeofCrew(int filmID) {
        StaffsOfMovie a = staffMap.get(filmID);
        if (a != null) {
            return a.getCrew().length;
        } else
            return -1;
    }

    /**
     * Gets the number of films stored in this data structure
     * 
     * @return The number of films in the data structure
     */
    @Override
    public int size() {
        return staffMap.size();
    }

    /**
     * Gets a list of all unique cast members present in the data structure
     * 
     * @return An array of all unique cast members as Person objects. If there are
     *         no cast members, then return an empty array
     */

    @Override
    public Person[] getUniqueCast() {
        Person[] cast = new Person[castMap.size()];
        int[] castIds = castMap.getAllKeys();

        for (int i = 0; i < castIds.length; i++) {
            cast[i] = castMap.get(castIds[i]);
        }

        return cast;
    }

    /**
     * Gets a list of all unique crew members present in the data structure
     * 
     * @return An array of all unique crew members as Person objects. If there are
     *         no crew members, then return an empty array
     */
    public Person[] getUniqueCrew() {
        Person[] crew = new Person[crewMap.size()];
        int[] crewIds = crewMap.getAllKeys();

        for (int i = 0; i < crewIds.length; i++) {
            crew[i] = crewMap.get(crewIds[i]);
        }

        return crew;
    }

    /**
     * Get all the cast members that have the given string within their name
     * 
     * @param cast The string that needs to be found
     * @return An array of unique Person objects of all cast members that have the
     *         requested string in their name
     */
    @Override
    public Person[] findCast(String cast) {
        int[] castIds = castMap.getAllKeys();
        AList<Person> matchingCast = new AList<>();

        for (int id : castIds) {
            CastPerson person = castMap.get(id);
            if (person.getName().contains(cast)) {
                matchingCast.add(person);
            }
        }

        // 转换为数组
        Person[] result = new Person[matchingCast.size()];
        for (int i = 0; i < matchingCast.size(); i++) {
            result[i] = matchingCast.get(i);
        }

        return result;
    }

    /**
     * Get all the crew members that have the given string within their name
     * 
     * @param crew The string that needs to be found
     * @return An array of unique Person objects of all crew members that have the
     *         requested string in their name
     */

    @Override
    public Person[] findCrew(String crew) {
        int[] crewIds = crewMap.getAllKeys();
        AList<Person> matchingCrew = new AList<>();

        for (int id : crewIds) {
            CrewPerson person = crewMap.get(id);
            if (person.getName().contains(crew)) {
                matchingCrew.add(person);
            }
        }

        // 转换为数组
        Person[] result = new Person[matchingCrew.size()];
        for (int i = 0; i < matchingCrew.size(); i++) {
            result[i] = matchingCrew.get(i);
        }

        return result;
    }

    /**
     * Gets the Person object corresponding to the cast ID
     * 
     * @param castID The cast ID of the person to be found
     * @return The Person object corresponding to the cast ID provided.
     *         If a person cannot be found, then return null
     */
    @Override
    public Person getCast(int castID) {
        return castMap.get(castID);
    }

    /**
     * Gets the Person object corresponding to the crew ID
     * 
     * @param crewID The crew ID of the person to be found
     * @return The Person object corresponding to the crew ID provided.
     *         If a person cannot be found, then return null
     */
    @Override
    public Person getCrew(int crewID) {
        return crewMap.get(crewID);
    }

    /**
     * Get an array of film IDs where the cast member has starred in
     * 
     * @param castID The cast ID of the person
     * @return An array of all the films the member of cast has starred
     *         in. If there are no films attached to the cast member,
     *         then return an empty array
     */
    @Override
    public int[] getCastFilms(int castID) {
        CastPerson cast = castMap.get(castID);
        if (cast != null) {
            return cast.getCastFilms();
        } else
            return new int[0];
    }

    /**
     * Get an array of film IDs where the crew member has starred in
     * 
     * @param crewID The crew ID of the person
     * @return An array of all the films the member of crew has starred
     *         in. If there are no films attached to the crew member,
     *         then return an empty array
     */
    @Override
    public int[] getCrewFilms(int crewID) {
        CrewPerson crew = crewMap.get(crewID);
        if (crew != null) {
            return crew.getCrewFilms();
        } else
            return new int[0];
    }

    /**
     * Get the films that this cast member stars in (in the top 3 cast
     * members/top 3 billing). This is determined by the order field in
     * the CastCredit class
     * 
     * @param castID The cast ID of the cast member to be searched for
     * @return An array of film IDs where the the cast member stars in.
     *         If there are no films where the cast member has starred in,
     *         or the cast member does not exist, return an empty array
     */
    @Override
    public int[] getCastStarsInFilms(int castID) {
        CastPerson cast = castMap.get(castID);
        if (cast != null) {
            return cast.getCastStarInFilms();
        } else
            return new int[0];
    }

    /**
     * Get Person objects for cast members who have appeared in the most
     * films. If the cast member has multiple roles within the film, then
     * they would get a credit per role played. For example, if a cast
     * member performed as 2 roles in the same film, then this would count
     * as 2 credits. The list should be ordered by the highest number of credits.
     * 
     * @param numResults The maximum number of elements that should be returned
     * @return An array of Person objects corresponding to the cast members
     *         with the most credits, ordered by the highest number of credits.
     *         If there are less cast members that the number required, then the
     *         list should be the same number of cast members found.
     */
    @Override
    public Person[] getMostCastCredits(int numResults) {
        int[] castIds = castMap.getAllKeys();
        CastPerson[] castPersons = new CastPerson[castIds.length];

        // 获取所有CastPerson对象
        for (int i = 0; i < castIds.length; i++) {
            castPersons[i] = castMap.get(castIds[i]);
        }

        // 使用快速排序按信用数量降序排序
        quickSortByCredits(castPersons, 0, castPersons.length - 1);

        // 提取结果
        int resultSize = Math.min(numResults, castPersons.length);
        Person[] result = new Person[resultSize];
        for (int i = 0; i < resultSize; i++) {
            result[i] = castPersons[i];
        }

        return result;
    }

    // 按信用数量降序排序的快速排序实现
    private void quickSortByCredits(CastPerson[] arr, int low, int high) {
        if (low < high) {
            int pi = partitionByCredits(arr, low, high);
            quickSortByCredits(arr, low, pi - 1);
            quickSortByCredits(arr, pi + 1, high);
        }
    }

    private int partitionByCredits(CastPerson[] arr, int low, int high) {
        int pivot = arr[high].getCredit();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j].getCredit() > pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    private void swap(CastPerson[] arr, int i, int j) {
        CastPerson temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Get the number of credits for a given cast member. If the cast member has
     * multiple roles within the film, then they would get a credit per role
     * played. For example, if a cast member performed as 2 roles in the same film,
     * then this would count as 2 credits.
     * 
     * @param castID A cast ID representing the cast member to be found
     * @return The number of credits the given cast member has. If the cast member
     *         cannot be found, return -1
     */
    @Override
    public int getNumCastCredits(int castID) {
        CastPerson cast = castMap.get(castID);
        if (cast != null)
            return cast.getCredit();
        else
            return -1;
    }

}
