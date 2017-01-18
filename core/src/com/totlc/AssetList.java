package com.totlc;

public enum AssetList {
    SPIDER_WALK("spider/spider.atlas");

    private String pathname;
    AssetList(String s) {
        this.pathname = s;
    }

    @Override
    public String toString() {
        return this.pathname;
    }
}
