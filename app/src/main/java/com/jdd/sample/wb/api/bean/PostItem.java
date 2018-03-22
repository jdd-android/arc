package com.jdd.sample.wb.api.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author lc. 2018-03-19 17:05
 * @since 1.0.0
 */

public class PostItem {

    @JSONField(name = "id")
    private long id;
    @JSONField(name = "reposts_count")
    private long rePostCount;
    @JSONField(name = "comments_count")
    private long commentsCount;
    @JSONField(name = "source_type")
    private long sourceType;

    @JSONField(name = "favorited")
    private boolean favorited;
    @JSONField(name = "truncated")
    private boolean truncated;

    @JSONField(name = "text")
    private String text;
    @JSONField(name = "source")
    private String source;
    @JSONField(name = "mid")
    private String mid;

    /** format = "EEE MMM dd HH:mm:ss Z yyyy" */
    @JSONField(name = "created_at")
    private String createAt;
    @JSONField(name = "user")
    private UserInfo userInfo;
    /** 转发微博的原微博数据 */
    @JSONField(name = "retweeted_status")
    private PostItem rePostOrigin;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRePostCount() {
        return rePostCount;
    }

    public void setRePostCount(long rePostCount) {
        this.rePostCount = rePostCount;
    }

    public long getCommentsCount() {
        return commentsCount;
    }

    public long getSourceType() {
        return sourceType;
    }

    public void setSourceType(long sourceType) {
        this.sourceType = sourceType;
    }

    public void setCommentsCount(long commentsCount) {
        this.commentsCount = commentsCount;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public PostItem getRePostOrigin() {
        return rePostOrigin;
    }

    public void setRePostOrigin(PostItem rePostOrigin) {
        this.rePostOrigin = rePostOrigin;
    }
}
