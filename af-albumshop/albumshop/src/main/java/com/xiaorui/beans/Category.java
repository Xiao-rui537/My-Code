package com.xiaorui.beans;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * Category 实体类
 * 对应数据库中的 category 表，表示分类信息。
 */
@TableName("category") // 指定对应的数据库表名
public class Category {

    @TableId // 指定主键字段
    private Integer cat_id; // 分类ID，唯一标识

    private String cat_name; // 分类名称
    private String cat_image; // 分类图片路径或URL

    /**
     * 无参构造方法
     * 用于实例化对象时不传入任何参数
     */
    public Category() {}

    // 访问器（Getter 和 Setter 方法）

    /**
     * 获取分类ID
     * @return 分类ID
     */
    public Integer getCat_id() {
        return cat_id;
    }

    /**
     * 设置分类ID
     * @param cat_id 分类ID
     */
    public void setCat_id(Integer cat_id) {
        this.cat_id = cat_id;
    }

    /**
     * 获取分类名称
     * @return 分类名称
     */
    public String getCat_name() {
        return cat_name;
    }

    /**
     * 设置分类名称
     * @param cat_name 分类名称
     */
    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    /**
     * 获取分类图片
     * @return 分类图片路径或URL
     */
    public String getCat_image() {
        return cat_image;
    }

    /**
     * 设置分类图片
     * @param cat_image 分类图片路径或URL
     */
    public void setCat_image(String cat_image) {
        this.cat_image = cat_image;
    }

    /**
     * 带参构造方法
     * 用于实例化对象时传入所有参数
     *
     * @param cat_image 分类图片路径或URL
     * @param cat_name 分类名称
     * @param cat_id 分类ID
     */
    public Category(String cat_image, String cat_name, Integer cat_id) {
        this.cat_image = cat_image;
        this.cat_name = cat_name;
        this.cat_id = cat_id;
    }

    /**
     * 重写 toString 方法
     * 用于返回对象的字符串表示
     *
     * @return 分类对象的字符串表示
     */
    @Override
    public String toString() {
        return "category{" +
                "cat_id=" + cat_id +
                ", cat_name='" + cat_name + '\'' +
                ", cat_img_src='" + cat_image + '\'' +
                '}';
    }
}
