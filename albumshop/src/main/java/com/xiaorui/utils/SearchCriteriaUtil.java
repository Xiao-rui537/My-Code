package com.xiaorui.utils;

/**
 * SearchCriteriaUtil 类
 * 封装专辑搜索的筛选条件：
 *  - query: 搜索关键词
 *  - searchByAlbumName: 是否按专辑名称搜索
 *  - searchByAlbumTranslation: 是否按专辑翻译名称搜索
 *  - searchBySinger: 是否按歌手搜索
 *  - searchByLanguage: 是否按语言搜索
 */
public class SearchCriteriaUtil {

    private String query; // 搜索关键词
    private Boolean searchByAlbumName; // 是否按专辑名称搜索
    private Boolean searchByAlbumTranslation; // 是否按专辑翻译名称搜索
    private Boolean searchBySinger; // 是否按歌手搜索
    private Boolean searchByLanguage; // 是否按语言搜索

    /**
     * 获取搜索关键词。
     * @return 搜索关键词
     */
    public String getQuery() {
        return query;
    }

    /**
     * 设置搜索关键词。
     * @param query 搜索关键词
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * 获取是否按专辑名称搜索。
     * @return 是否按专辑名称搜索
     */
    public Boolean getSearchByAlbumName() {
        return searchByAlbumName;
    }

    /**
     * 设置是否按专辑名称搜索。
     * @param searchByAlbumName 是否按专辑名称搜索
     */
    public void setSearchByAlbumName(Boolean searchByAlbumName) {
        this.searchByAlbumName = searchByAlbumName;
    }

    /**
     * 获取是否按专辑翻译名称搜索。
     * @return 是否按专辑翻译名称搜索
     */
    public Boolean getSearchByAlbumTranslation() {
        return searchByAlbumTranslation;
    }

    /**
     * 设置是否按专辑翻译名称搜索。
     * @param searchByAlbumTranslation 是否按专辑翻译名称搜索
     */
    public void setSearchByAlbumTranslation(Boolean searchByAlbumTranslation) {
        this.searchByAlbumTranslation = searchByAlbumTranslation;
    }

    /**
     * 获取是否按歌手搜索。
     * @return 是否按歌手搜索
     */
    public Boolean getSearchBySinger() {
        return searchBySinger;
    }

    /**
     * 设置是否按歌手搜索。
     * @param searchBySinger 是否按歌手搜索
     */
    public void setSearchBySinger(Boolean searchBySinger) {
        this.searchBySinger = searchBySinger;
    }

    /**
     * 获取是否按语言搜索。
     * @return 是否按语言搜索
     */
    public Boolean getSearchByLanguage() {
        return searchByLanguage;
    }

    /**
     * 设置是否按语言搜索。
     * @param searchByLanguage 是否按语言搜索
     */
    public void setSearchByLanguage(Boolean searchByLanguage) {
        this.searchByLanguage = searchByLanguage;
    }

    /**
     * 带所有字段的构造函数。
     * @param query 搜索关键词
     * @param searchByAlbumName 是否按专辑名称搜索
     * @param searchByAlbumTranslation 是否按专辑翻译名称搜索
     * @param searchBySinger 是否按歌手搜索
     * @param searchByLanguage 是否按语言搜索
     */
    public SearchCriteriaUtil(String query, Boolean searchByAlbumName, Boolean searchByAlbumTranslation, Boolean searchBySinger, Boolean searchByLanguage) {
        this.query = query;
        this.searchByAlbumName = searchByAlbumName;
        this.searchByAlbumTranslation = searchByAlbumTranslation;
        this.searchBySinger = searchBySinger;
        this.searchByLanguage = searchByLanguage;
    }

    /**
     * 重写 toString 方法，返回对象的字符串表示。
     * @return 字符串表示的搜索条件
     */
    @Override
    public String toString() {
        return "SearchCriteria{" +
                "query='" + query + '\'' +
                ", searchByAlbumName=" + searchByAlbumName +
                ", searchByAlbumTranslation=" + searchByAlbumTranslation +
                ", searchBySinger=" + searchBySinger +
                ", searchByLanguage=" + searchByLanguage +
                '}';
    }
}
