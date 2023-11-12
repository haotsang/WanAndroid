package com.haotsang.wanandroid.model.core;

public class StatusData<T> {

    /**
     * 数据加载状态
     */
    private DataStatus mDataStatus;

    /**
     * 实际数据
     */
    private T data;

    /**
     * 错误信息
     */
    private ErrorData error;


    protected StatusData() {
    }

    public StatusData(DataStatus dataStatus, T data, ErrorData error) {
        mDataStatus = dataStatus;
        this.data = data;
        this.error = error;
    }

    public static <T> StatusData<T> content(T data) {
        if (data != null) {
            return new StatusData<>(DataStatus.Content, data, null);
        } else {
            return new StatusData<>(DataStatus.Empty, null, null);
        }
    }

    public static <T> StatusData<T> error(T data, ErrorData error) {
        return new StatusData<>(DataStatus.Error, data, error);
    }

    public static <T> StatusData<T> error(ErrorData errorCode) {
        return error(null, errorCode);
    }

    public static <T> StatusData<T> empty(T data) {
        return new StatusData<>(DataStatus.Empty, data, null);
    }

    public static <T> StatusData<T> empty() {
        return empty(null);
    }


    public static <T> StatusData<T> loading(T data) {
        return new StatusData<>(DataStatus.Loading, data, null);
    }

    public static <T> StatusData<T> loading() {
        return loading(null);
    }


    public T getData() {
        return data;
    }

    public ErrorData getError() {
        return error;
    }

    public static boolean hasContent(StatusData statusData) {
        if (statusData != null) {
            return statusData.getDataStatus().hasContent();
        }
        return false;
    }
    public DataStatus getDataStatus() {
        if (mDataStatus == null) {
            return DataStatus.Empty;
        } else if (mDataStatus.hasContent()) {
            if (data == null) {
                return DataStatus.Empty;
            }
        }
        return mDataStatus;
    }
}
