package com.xiaorui.controller;

import com.xiaorui.beans.Album;
import com.xiaorui.beans.Category;
import com.xiaorui.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CategoryController 控制器类
 * 处理与分类相关的 HTTP 请求。
 */
@RestController
@CrossOrigin // 解决跨域问题
@RequestMapping("/category")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryService categoryService;

    /**
     * 构造方法，使用自动注入方式注入 CategoryService。
     * @param categoryService 分类服务类
     */
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 查询所有分类。
     * @return 分类列表
     */
    @GetMapping("/list")
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            List<Category> categories = categoryService.queryCategory();
            logger.info("Fetched all categories successfully");
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            logger.error("Error fetching categories", e);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * 根据分类 ID 获取对应的专辑。
     * @param id 分类 ID
     * @return 该分类下的专辑列表
     */
    @GetMapping("/category/{id}")
    public ResponseEntity<List<Album>> getAlbumsByCategory(@PathVariable("id") Integer id) {
        try {
            List<Album> albums = categoryService.queryAlbumsByCategory(id);
            if (albums.isEmpty()) {
                logger.warn("No albums found for category ID: {}", id);
                return ResponseEntity.status(404).build();
            }
            logger.info("Fetched albums for category ID: {}", id);
            return ResponseEntity.ok(albums);
        } catch (Exception e) {
            logger.error("Error fetching albums for category ID: {}", id, e);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * 根据分类 ID 获取分类名称。
     * @param id 分类 ID
     * @return 分类名称
     */
    @GetMapping("/{id}")
    public ResponseEntity<String> getCategoryNameById(@PathVariable Integer id) {
        try {
            Category category = categoryService.getCategoryById(id);
            if (category != null) {
                logger.info("Category found for ID: {}", id);
                return ResponseEntity.ok(category.getCat_name());
            } else {
                logger.warn("Category not found for ID: {}", id);
                return ResponseEntity.status(404).body("Category not found");
            }
        } catch (Exception e) {
            logger.error("Error fetching category by ID: {}", id, e);
            return ResponseEntity.status(500).body("Error fetching category");
        }
    }
}
