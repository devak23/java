package com.ak.learnings;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Business Rules:
 * 1. While adding we have to check whether cache exists already or not,if not you have to create only one object of
 * cache and add first element into cache.
 * 2. If cache size is 20 or more than 20, then remove least used element from cache and add new element into cache.
 * 3. While reading if cache is empty, read first 10 student results from text file into cache.
 */

public class RankCacheImpl implements RankCache {
    private static final Object monitor = new Object();
    private static final int MAX_PERMISSIBLE_SIZE = 5;
    private static final RankCache INSTANCE = new RankCacheImpl();
    private String sourceFile;
    private Set<StudentResultEntry> data = new TreeSet<>();

    private RankCacheImpl() {
        // singleton
    }

    public static RankCache getInstance() {
        return INSTANCE;
    }

    @Override
    public void addToCache(StudentResult result) {
        if (result == null) return;

        if (data.size() >= MAX_PERMISSIBLE_SIZE) {
            // remove the least recently used
            StudentResultEntry leastAccessed = Collections.min(data);
            data.removeIf(sre -> sre.getInvocationTime() == leastAccessed.getInvocationTime());
        }
        data.add(new StudentResultEntry(result, System.nanoTime()));
    }

    @Override
    public void removeFromCache(String studentRank) {
        int rank = Integer.parseInt(studentRank);
        data.removeIf(sre -> sre.getStudentResult().getRank() == rank);
    }

    @Override
    public StudentResult readFromCache(String studentRank) {
        StudentResult result;
        int rank = Integer.parseInt(studentRank);
        if (data.isEmpty()) {
            try {
                initialize();
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        Optional<StudentResultEntry> entry = data.stream()
                .filter(sre -> sre.getStudentResult().getRank() == rank)
                .findFirst();

        if (entry.isPresent()) {
            StudentResultEntry sre = entry.get();
            sre.setInvocationTime(System.nanoTime());
            return sre.getStudentResult();
        }
        return null;
    }

    @Override
    public RankCache setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
        return this;
    }

    private void initialize() throws IOException, URISyntaxException {
        if (sourceFile == null) {
            throw new RuntimeException("Cannot initialize the cache with a null file");
        }

        URL resource = this.getClass().getClassLoader().getResource("StudentData.txt");
        Path path = Paths.get(resource.toURI());

        List<String> contents = Files.readAllLines(path);
        for (int i = 0; i < contents.size(); i++) {
            if (i == 0) {
                continue; // ignore the header row
            }
            if (i > MAX_PERMISSIBLE_SIZE) {
                break;
            }
            String elements[] = contents.get(i).split(" ");
            StudentResult studentResult = StudentResult.initialize(elements);
            if (studentResult != null) {
                data.add(new StudentResultEntry(studentResult, System.nanoTime()));
            }
        }
        if (data.isEmpty()) {
            throw new RuntimeException("Couldn't initialize the data with the given file");
        }
    }

    @Override
    public int size() {
        return data.size();
    }

    private static final class StudentResultEntry implements Comparable {
        private StudentResult studentResult;
        private long invocationTime;

        public StudentResultEntry(StudentResult studentResult, long invocationTime) {
            this.studentResult = studentResult;
            this.invocationTime = invocationTime;
        }

        public StudentResult getStudentResult() {
            return studentResult;
        }

        public StudentResultEntry setStudentResult(StudentResult studentResult) {
            this.studentResult = studentResult;
            return this;
        }

        public long getInvocationTime() {
            return invocationTime;
        }

        public StudentResultEntry setInvocationTime(long invocationTime) {
            this.invocationTime = invocationTime;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StudentResultEntry that = (StudentResultEntry) o;
            return invocationTime == that.invocationTime;
        }

        @Override
        public int hashCode() {
            return Objects.hash(invocationTime);
        }

        @Override
        public String toString() {
            return "StudentResultEntry{" +
                    "name=" + studentResult.getName() +
                    ", invocationTime=" + invocationTime +
                    '}';
        }

        @Override
        public int compareTo(Object o) {
            StudentResultEntry that = (StudentResultEntry) o;
            return Long.compare(this.getInvocationTime(), that.getInvocationTime());
        }
    }
}
