package com.ak.learnings;

public interface RankCache {
    void addToCache(StudentResult result);

    void removeFromCache(String studentRank);

    StudentResult readFromCache(String studentRank);

    RankCache setSourceFile(String sourceFile);

    int size();
}