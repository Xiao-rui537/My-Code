package com.xiaorui.service.impl;

import com.xiaorui.beans.Album;
import com.xiaorui.beans.Category;
import com.xiaorui.mapper.CategoryMapper;
import com.xiaorui.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * CategoryServiceImpl 类
 * 实现了 CategoryService 接口，处理分类相关的业务逻辑。
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryMapper categoryMapper;

    /**
     * 构造方法，注入 CategoryMapper。
     * @param categoryMapper 分类映射器
     */
    @Autowired
    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    /**
     * 查询所有分类。
     * @return 分类列表
     */
    @Override
    public List<Category> queryCategory() {
        try {
            List<Category> categories = categoryMapper.queryCategory();
            logger.info("All categories queried successfully");
            return categories;
        } catch (Exception e) {
            logger.error("Error occurred while querying all categories", e);
            return Collections.emptyList();
        }
    }

    /**
     * 添加分类。
     * @param name 分类名称
     * @return 是否添加成功
     */
    @Override
    public boolean addCategory(String name) {
        try {
            if (categoryMapper.identifyCategory(name) > 0) {
                logger.warn("Category already exists: {}", name);
                return false;
            }
            categoryMapper.addCategoryType(name);
            logger.info("Category added successfully: {}", name);
            return true;
        } catch (Exception e) {
            logger.error("Error occurred while adding category: {}", name, e);
            return false;
        }
    }

    /**
     * 删除分类。
     * @param id 分类 ID
     * @return 是否删除成功
     */
    @Override
    public boolean removeCategory(Integer id) {
        try {
            Category categoryToDelete = categoryMapper.getCategoryById(id);
            if (categoryToDelete == null) {
                logger.warn("Category not found");
                return false;
            }
            categoryMapper.deleteCategory(categoryToDelete.getCat_name());
            logger.info("Category removed successfully with id: {}", id);
            return true;
        } catch (Exception e) {
            logger.error("Error occurred while removing category with id: {}", id, e);
            return false;
        }
    }

    /**
     * 根据分类 ID 查询专辑。
     * @param id 分类 ID
     * @return 符合条件的专辑列表
     */
    @Override
    public List<Album> queryAlbumsByCategory(Integer id) {
        try {
            List<Album> albums = categoryMapper.queryAlbumsByCategory(id);
            logger.info("Albums queried successfully for category id: {}", id);
            return albums;
        } catch (Exception e) {
            logger.error("Error occurred while querying albums by category id: {}", id, e);
            return Collections.emptyList();
        }
    }

    /**
     * 根据分类 ID 查询分类信息。
     * @param id 分类 ID
     * @return 分类对象
     */
    @Override
    public Category getCategoryById(Integer id) {
        try {
            Category category = categoryMapper.getCategoryById(id);
            if (category != null) {
                logger.info("Category queried successfully by id: {}", id);
            } else {
                logger.warn("Category not found with id: {}", id);
            }
            return category;
        } catch (Exception e) {
            logger.error("Error occurred while querying category by id: {}", id, e);
            return null;
        }
    }
}
