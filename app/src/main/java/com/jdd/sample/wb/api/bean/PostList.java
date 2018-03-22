package com.jdd.sample.wb.api.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author lc. 2018-03-19 17:16
 * @since 1.0.0
 */

public class PostList {

    @JSONField(name = "previous_cursor")
    private long previousCursor;
    @JSONField(name = "next_cursor")
    private long nextCursor;
    @JSONField(name = "total_number")
    private long totalNumber;

    @JSONField(name = "statuses")
    private List<PostItem> list;

    public long getPreviousCursor() {
        return previousCursor;
    }

    public void setPreviousCursor(long previousCursor) {
        this.previousCursor = previousCursor;
    }

    public long getNextCursor() {
        return nextCursor;
    }

    public void setNextCursor(long nextCursor) {
        this.nextCursor = nextCursor;
    }

    public long getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(long totalNumber) {
        this.totalNumber = totalNumber;
    }

    public List<PostItem> getList() {
        return list;
    }

    public void setList(List<PostItem> list) {
        this.list = list;
    }
}
