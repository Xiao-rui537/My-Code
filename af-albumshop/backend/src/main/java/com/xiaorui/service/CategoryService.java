package com.xiaorui.service;

import com.xiaorui.beans.Album;
import com.xiaorui.beans.Category;
import java.util.List;

/**
 * CategoryService 接口
 * 定义与分类相关的业务逻辑方法。
 */
public interface CategoryService {

    /**
     * 查询所有分类。
     * @return 分类列表
     */
    public List<Category> queryCategory();

    /**
     * 添加分类。
     * @param name 分类名称
     * @return 是否添加成功
     */
    public boolean addCategory(String name);

    /**
     * 删除分类。
     * @param id 分类 ID
     * @return 是否删除成功
     */
    public boolean removeCategory(Integer id);

    /**
     * 根据分类 ID 查询专辑。
     * @param id 分类 ID
     * @return 符合条件的专辑列表
     */
    public List<Album> queryAlbumsByCategory(Integer id);

    /**
     * 根据分类 ID 查询分类信息。
     * @param id 分类 ID
     * @return 分类对象
     */
    public Category getCategoryById(Integer id);
}