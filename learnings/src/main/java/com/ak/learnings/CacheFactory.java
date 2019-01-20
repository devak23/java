package com.ak.learnings;

public final class CacheFactory {
    private CacheFactory() {
        // singleton
    }

    public static RankCache getCache(CacheType cacheType) {
        switch (cacheType) {
            case RANK_CACHE:
                return RankCacheImpl.getInstance();
            case SIMPLE_CACHE:
                throw new RuntimeException("Not implemented yet");
            default:
                throw new IllegalArgumentException();
        }
    }
}
