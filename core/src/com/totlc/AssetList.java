package com.totlc;

public enum AssetList {
    SPIDER_IDLE("sprites/spider/spider_idel.atlse"),
    SPIDER_WALK("sprites/spider/spider.atlas");

    private String pathname;
    AssetList(String s) {
        this.pathname = s;
    }

    @Override
    public String toString() {
        return this.pathname;
    }
}
