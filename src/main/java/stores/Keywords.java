package stores;

import interfaces.IKeywords;
import interfaces.AbstractStores;
import structures.MyArrayList;

public class Keywords implements IKeywords {
    AbstractStores stores;

    MyArrayList<Integer> id;
    MyArrayList<Keyword[]> keywords;
    MyArrayList<Keyword> unique;

    // 缓存
    private int[] cachedMostKeywordFilms = null;
    private int cachedMostKeywordFilmsNum = 0;
    private Keyword[] cachedUniqueKeywords = null;

    Keywords(AbstractStores stores) {
        this.stores = stores;
        id = new MyArrayList<>();
        keywords = new MyArrayList<>();
        unique = new MyArrayList<>();
    }

    @Override
    public boolean add(int filmID, Keyword keyword) {
        // 清除缓存
        clearCache();

        boolean result = true;

        for (int i = 0; i < this.id.size(); i++) {
            if (this.id.get(i) == filmID) {
                // 改进数组扩容：预先分配足够的空间
                int oldLength = keywords.get(i).length;
                Keyword[] tmp = new Keyword[oldLength + 1];
                System.arraycopy(keywords.get(i), 0, tmp, 0, oldLength);
                tmp[oldLength] = keyword;
                keywords.set(i, tmp);
                return result;
            }
        }

        boolean flag = true;
        for (int i = 0; i < unique.size(); i++) {
            if (this.unique.get(i).getID() == keyword.getID()) {
                flag = false;
                break;
            }
        }

        if (flag) {
            unique.add(keyword);
        }

        result &= this.id.add(filmID);
        Keyword[] tmp = { keyword };
        result &= keywords.add(tmp);
        return result;
    }

    @Override
    public boolean add(int id, Keyword[] keywords) {
        // 清除缓存
        clearCache();

        boolean result = true;

        for (int i = 0; i < this.id.size(); i++) {
            if (this.id.get(i) == id) {
                // 改进数组扩容：使用System.arraycopy
                int oldLength = this.keywords.get(i).length;
                Keyword[] tmp = new Keyword[oldLength + keywords.length];
                System.arraycopy(this.keywords.get(i), 0, tmp, 0, oldLength);
                System.arraycopy(keywords, 0, tmp, oldLength, keywords.length);
                this.keywords.set(i, tmp);
                return result;
            }
        }

        for (int i = 0; i < keywords.length; i++) {
            boolean flag = true;
            for (int j = 0; j < unique.size(); j++) {
                if (this.unique.get(j).getID() == keywords[i].getID()) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                unique.add(keywords[i]);
            }
        }

        result &= this.id.add(id);
        result &= this.keywords.add(keywords);
        return result;
    }

    @Override
    public boolean remove(int id) {
        // 清除缓存
        clearCache();

        int index = this.id.indexOf(id);
        boolean result = this.id.remove(id);
        result &= this.keywords.remove(this.keywords.get(index));
        return result;
    }

    @Override
    public boolean removeKeywordFromFilm(int id, int keywordID) {
        // 清除缓存
        clearCache();

        for (int i = 0; i < this.id.size(); i++) {
            if (this.id.get(i) == id) {
                int indexToRemove = -1;
                for (int j = 0; j < this.keywords.get(i).length; j++) {
                    if (this.keywords.get(i)[j].getID() == keywordID) {
                        indexToRemove = j;
                        break;
                    }
                }
                if (indexToRemove >= 0) {
                    // 改进数组扩容：使用System.arraycopy
                    Keyword[] tmp = new Keyword[this.keywords.get(i).length - 1];
                    if (indexToRemove > 0) {
                        System.arraycopy(this.keywords.get(i), 0, tmp, 0, indexToRemove);
                    }
                    if (indexToRemove < this.keywords.get(i).length - 1) {
                        System.arraycopy(this.keywords.get(i), indexToRemove + 1, tmp, indexToRemove,
                                this.keywords.get(i).length - indexToRemove - 1);
                    }
                    this.keywords.set(i, tmp);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public int[] getFilmIDs() {
        int[] result = new int[id.size()];
        for (int i = 0; i < id.size(); i++) {
            result[i] = id.get(i);
        }
        return result;
    }

    @Override
    public int[] getKeywordIDs() {
        int[] uniqueKeyword = new int[unique.size()];

        for (int i = 0; i < unique.size(); i++) {
            uniqueKeyword[i] = unique.get(i).getID();
        }

        return uniqueKeyword;
    }

    @Override
    public int[] getFilmsWithKeyword(int keywordID) {
        MyArrayList<Integer> tmp = new MyArrayList<>();
        for (int i = 0; i < this.id.size(); i++) {
            for (int j = 0; j < this.keywords.get(i).length; j++) {
                if (keywordID == this.keywords.get(i)[j].getID()) {
                    tmp.add(this.id.get(i));
                    break;
                }
            }
        }

        int[] result = new int[tmp.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = tmp.get(i);
        }
        return result;
    }

    @Override
    public Keyword[] getKeywordsForFilm(int id) {
        int index = this.id.indexOf(id);

        if (index < 0 || index > size()) {
            return null;
        }
        return keywords.get(index);
    }

    @Override
    public Keyword[] getUnique() {
        // 使用缓存
        if (cachedUniqueKeywords != null) {
            return cachedUniqueKeywords;
        }

        Keyword[] uniqueKeyword = new Keyword[unique.size()];

        for (int i = 0; i < unique.size(); i++) {
            uniqueKeyword[i] = unique.get(i);
        }

        cachedUniqueKeywords = uniqueKeyword;
        return uniqueKeyword;
    }

    @Override
    public int size() {
        return keywords.size();
    }

    @Override
    public Keyword[] findKeywords(String keyword) {
        MyArrayList<Keyword> tmpResult = new MyArrayList<>();
        for (int i = 0; i < unique.size(); i++) {
            if (unique.get(i).getName().contains(keyword)) {
                tmpResult.add(unique.get(i));
            }
        }

        Keyword[] result = new Keyword[tmpResult.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = tmpResult.get(i);
        }

        return result;
    }

    @Override
    public int[] getMostKeywordFilms(int numResults) {
        // 使用缓存
        if (cachedMostKeywordFilms != null && numResults <= cachedMostKeywordFilmsNum) {
            if (numResults == cachedMostKeywordFilmsNum) {
                return cachedMostKeywordFilms;
            } else {
                int[] result = new int[numResults];
                System.arraycopy(cachedMostKeywordFilms, 0, result, 0, numResults);
                return result;
            }
        }

        // 创建电影ID和关键词数量的数组
        FilmKeywordCount[] filmCounts = new FilmKeywordCount[id.size()];
        for (int i = 0; i < id.size(); i++) {
            filmCounts[i] = new FilmKeywordCount(id.get(i), keywords.get(i).length);
        }

        // 使用快速排序按关键词数量降序排序
        quickSortByKeywordCount(filmCounts, 0, filmCounts.length - 1);

        // 提取结果
        int resultSize = Math.min(numResults, filmCounts.length);
        int[] result = new int[resultSize];
        for (int i = 0; i < resultSize; i++) {
            result[i] = filmCounts[i].filmID;
        }

        // 缓存结果
        cachedMostKeywordFilms = result;
        cachedMostKeywordFilmsNum = numResults;

        return result;
    }

    // 辅助类，存储电影ID和关键词数量
    private static class FilmKeywordCount {
        int filmID;
        int keywordCount;

        FilmKeywordCount(int filmID, int keywordCount) {
            this.filmID = filmID;
            this.keywordCount = keywordCount;
        }
    }

    // 按关键词数量降序排序的快速排序实现
    private void quickSortByKeywordCount(FilmKeywordCount[] arr, int low, int high) {
        if (low < high) {
            int pi = partitionByKeywordCount(arr, low, high);
            quickSortByKeywordCount(arr, low, pi - 1);
            quickSortByKeywordCount(arr, pi + 1, high);
        }
    }

    private int partitionByKeywordCount(FilmKeywordCount[] arr, int low, int high) {
        int pivot = arr[high].keywordCount;
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j].keywordCount > pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    private void swap(FilmKeywordCount[] arr, int i, int j) {
        FilmKeywordCount temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 清除缓存
    private void clearCache() {
        cachedMostKeywordFilms = null;
        cachedUniqueKeywords = null;
    }
}
