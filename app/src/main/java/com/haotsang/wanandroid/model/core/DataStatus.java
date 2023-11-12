package com.haotsang.wanandroid.model.core;

public enum DataStatus {
    /**
     * 加载中
     */
    Loading,
    /**
     * 获取到内容
     */
    Content,
    /**
     * 获取到空内容
     */
    Empty,
    /**
     * 报错
     */
    Error;

    public boolean hasContent() {
        return this == Content;
    }
}
