package com.xiaorui.beans;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Album 实体类
 * 对应数据库中的 Album 表，包含专辑相关的字段和访问器。
 */
@TableName("Album") // 指定数据库表名
public class Album {

    @TableId // 指定为主键
    private Integer album_id; // 专辑ID，唯一标识

    private String album_name; // 专辑名称
    private String album_name_tran; // 专辑翻译名称
    private String album_title; // 专辑标题
    private Integer album_category_id; // 专辑分类ID
    @DateTimeFormat(pattern = "yyyy-MM-dd") // 指定前端接收日期格式
    private Date album_release_day; // 专辑发行日期
    private Integer album_price; // 专辑价格
    private Integer album_sale; // 专辑打折价格
    private String album_singer; // 专辑歌手
    private String album_cover; // 专辑封面图片URL
    private String album_introduction; // 专辑简介
    private Integer album_song; // 专辑包含的歌曲数量
    private String album_song_name; // 专辑中歌曲的名称集合，用斜杠分隔
    private String album_language; // 专辑语言

    // 访问器（Getter 和 Setter 方法）

    public Integer getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(Integer album_id) {
        this.album_id = album_id;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getAlbum_name_tran() {
        return album_name_tran;
    }

    public void setAlbum_name_tran(String album_name_tran) {
        this.album_name_tran = album_name_tran;
    }

    public String getAlbum_title() {
        return album_title;
    }

    public void setAlbum_title(String album_title) {
        this.album_title = album_title;
    }

    public Integer getAlbum_category_id() {
        return album_category_id;
    }

    public void setAlbum_category_id(Integer album_category_id) {
        this.album_category_id = album_category_id;
    }

    @JsonFormat(pattern = "yyyy-MM-dd") // 指定返回JSON的日期格式
    public Date getAlbum_release_day() {
        return album_release_day;
    }

    public void setAlbum_release_day(Date album_release_day) {
        this.album_release_day = album_release_day;
    }

    public Integer getAlbum_price() {
        return album_price;
    }

    public void setAlbum_price(Integer album_price) {
        this.album_price = album_price;
    }

    public Integer getAlbum_sale() {
        return album_sale;
    }

    public void setAlbum_sale(Integer album_sale) {
        this.album_sale = album_sale;
    }

    public String getAlbum_singer() {
        return album_singer;
    }

    public void setAlbum_singer(String album_singer) {
        this.album_singer = album_singer;
    }

    public String getAlbum_cover() {
        return album_cover;
    }

    public void setAlbum_cover(String album_cover) {
        this.album_cover = album_cover;
    }

    public String getAlbum_introduction() {
        return album_introduction;
    }

    public void setAlbum_introduction(String album_introduction) {
        this.album_introduction = album_introduction;
    }

    public Integer getAlbum_song() {
        return album_song;
    }

    public void setAlbum_song(Integer album_song) {
        this.album_song = album_song;
    }

    public String getAlbum_song_name() {
        return album_song_name;
    }

    public void setAlbum_song_name(String album_song_name) {
        this.album_song_name = album_song_name;
    }

    public String getAlbum_language() {
        return album_language;
    }

    public void setAlbum_language(String album_language) {
        this.album_language = album_language;
    }

    /**
     * 无参构造方法
     * 用于实例化对象时不传入任何参数
     */
    public Album() {}

    /**
     * 带参构造方法
     * 用于实例化对象时传入所有参数
     *
     * @param album_id 专辑ID
     * @param album_name 专辑名称
     * @param album_name_tran 专辑翻译名称
     * @param album_title 专辑标题
     * @param album_category_id 专辑分类ID
     * @param album_release_day 专辑发行日期
     * @param album_price 专辑价格
     * @param album_sale 专辑销售量
     * @param album_singer 专辑歌手
     * @param album_cover 专辑封面
     * @param album_introduction 专辑简介
     * @param album_song 专辑包含的歌曲数量
     * @param album_song_name 专辑歌曲名称集合
     * @param album_language 专辑语言
     */
    public Album(Integer album_id, String album_name, String album_name_tran, String album_title, Integer album_category_id, Date album_release_day, Integer album_price, Integer album_sale, String album_singer, String album_cover, String album_introduction, Integer album_song, String album_song_name, String album_language) {
        this.album_id = album_id;
        this.album_name = album_name;
        this.album_name_tran = album_name_tran;
        this.album_title = album_title;
        this.album_category_id = album_category_id;
        this.album_release_day = album_release_day;
        this.album_price = album_price;
        this.album_sale = album_sale;
        this.album_singer = album_singer;
        this.album_cover = album_cover;
        this.album_introduction = album_introduction;
        this.album_song = album_song;
        this.album_song_name = album_song_name;
        this.album_language = album_language;
    }

    /**
     * 重写 toString 方法
     * 用于返回对象的字符串表示
     */
    @Override
    public String toString() {
        return "album{" +
                "album_id=" + album_id +
                ", album_name='" + album_name + '\'' +
                ", album_name_tran='" + album_name_tran + '\'' +
                ", album_title='" + album_title + '\'' +
                ", album_category_id=" + album_category_id +
                ", album_release_day='" + album_release_day + '\'' +
                ", album_price=" + album_price +
                ", album_sale=" + album_sale +
                ", album_singer='" + album_singer + '\'' +
                ", album_cover='" + album_cover + '\'' +
                ", album_introduction='" + album_introduction + '\'' +
                ", album_song=" + album_song +
                ", album_song_name='" + album_song_name + '\'' +
                ", album_language='" + album_language + '\'' +
                '}';
    }
}
