package com.jdd.sample.wb.eventbus;

/**
 * @author lc. 2018-03-19 11:50
 * @since 1.0.0
 */

public class EventUserInfoRefresh {

    private boolean refreshSuccess;

    public EventUserInfoRefresh(boolean success) {
        refreshSuccess = success;
    }

    public boolean isRefreshSuccess() {
        return refreshSuccess;
    }
}
