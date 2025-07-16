package com.xiaorui.mapper;

import com.xiaorui.beans.Album;
import com.xiaorui.beans.Category;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * CategoryMapper 接口
 * 定义与 Category 相关的数据库操作方法。
 */
@Mapper
public interface CategoryMapper {

    /**
     * 查询所有分类。
     * @return 分类列表
     */
    public List<Category> queryCategory();

    /**
     * 根据分类 ID 查询专辑。
     * @param id 分类 ID
     * @return 符合条件的专辑列表
     */
    public List<Album> queryAlbumsByCategory(Integer id);

    /**
     * 添加新的分类类型。
     * @param name 分类名称
     */
    public void addCategoryType(String name);

    /**
     * 删除分类。
     * @param name 分类名称
     */
    public void deleteCategory(String name);

    /**
     * 确定分类是否存在。
     * @param name 分类名称
     * @return 分类 ID，如果不存在则返回 -1
     */
    public Integer identifyCategory(String name);

    /**
     * 根据分类 ID 查询分类。
     * @param id 分类 ID
     * @return 分类对象
     */
    public Category getCategoryById(Integer id);
}
