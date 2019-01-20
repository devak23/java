package com.ak.learnings;

// Client program
public class CacheClient {
    public static void main(String[] args) {
        RankCache cache = CacheFactory.getCache(CacheType.RANK_CACHE);
        cache.setSourceFile("StudentData.txt");
        StudentResult studentResult = cache.readFromCache("1");
        System.out.println(studentResult);
    }
}
