package com.xiaorui.controller;

import com.xiaorui.beans.Album;
import com.xiaorui.service.AlbumService;
import com.xiaorui.utils.SearchCriteriaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AlbumController 控制器类
 * 处理与专辑相关的 HTTP 请求，包括查询、增加、修改和删除专辑。
 */
@Controller
@CrossOrigin // 解决跨域问题
public class AlbumController {

    private static final Logger logger = LoggerFactory.getLogger(AlbumController.class);
    private final AlbumService albumService;

    /**
     * 构造方法，使用自动注入方式注入 AlbumService。
     * @param albumService 专辑服务类
     */
    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    /**
     * 根据搜索条件搜索专辑。
     * @param searchCriteriaUtil 搜索条件工具类对象
     * @return 符合条件的专辑列表
     */
    @PostMapping("/albums/searchAlbum")
    public ResponseEntity<List<Album>> searchAlbums(@RequestBody SearchCriteriaUtil searchCriteriaUtil) {
        try {
            List<Album> result = albumService.searchAlbums(
                    searchCriteriaUtil.getQuery(),
                    searchCriteriaUtil.getSearchByAlbumName(),
                    searchCriteriaUtil.getSearchByAlbumTranslation(),
                    searchCriteriaUtil.getSearchBySinger(),
                    searchCriteriaUtil.getSearchByLanguage()
            );
            logger.info("Albums searched successfully with criteria: {}", searchCriteriaUtil);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error searching albums", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 根据关键词搜索专辑。
     * @param keyword 搜索关键词
     * @return 符合条件的专辑列表
     */
    @GetMapping("/albums/search")
    public ResponseEntity<List<Album>> search(@RequestParam String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            logger.warn("Keyword is missing in the request");
            return ResponseEntity.badRequest().build();
        }
        try {
            List<Album> results = albumService.queryAlbumsByKeyword(keyword);
            logger.info("Albums searched successfully with keyword: {}", keyword);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            logger.error("Error searching albums with keyword: {}", keyword, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 添加新专辑。
     * @param album 专辑对象
     * @return 添加成功的专辑对象
     */
    @PostMapping("/add")
    public ResponseEntity<?> addAlbum(@RequestBody Album album) {
        logger.info("Received album data: {}", album);
        try {
            albumService.addAlbum(album);
            logger.info("Album added successfully: {}", album);
            return ResponseEntity.status(HttpStatus.CREATED).body(album);
        } catch (Exception e) {
            logger.error("Error adding album", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("服务器内部错误: " + e.getMessage());
        }
    }

    /**
     * 修改专辑信息。
     * @param id 专辑 ID
     * @param album 专辑对象
     * @return 修改结果
     */
    @PutMapping("/albums/change/{id}")
    public ResponseEntity<String> modifyAlbum(@PathVariable Integer id, @RequestBody Album album) {
        if (id == null) {
            logger.warn("Album ID is missing in the request");
            return ResponseEntity.badRequest().body("Invalid album ID");
        }
        album.setAlbum_id(id);
        try {
            boolean result = albumService.modifyAlbum(album);
            if (result) {
                logger.info("Album updated successfully: {}", album);
                return ResponseEntity.ok("专辑信息更新成功");
            } else {
                logger.warn("Failed to update album with ID: {}", id);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("修改失败");
            }
        } catch (Exception e) {
            logger.error("Error updating album with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("服务器内部错误: " + e.getMessage());
        }
    }

    /**
     * 删除专辑。
     * @param id 专辑 ID
     * @return 删除结果
     */
    @DeleteMapping("/albums/delete/{id}")
    public ResponseEntity<String> removeAlbum(@PathVariable Integer id) {
        if (id == null) {
            logger.warn("Album ID is missing in the request");
            return ResponseEntity.badRequest().body("Invalid album ID");
        }
        try {
            boolean result = albumService.removeAlbum(id);
            if (result) {
                logger.info("Album deleted successfully with ID: {}", id);
                return ResponseEntity.noContent().build();
            } else {
                logger.warn("Failed to delete album with ID: {}", id);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败");
            }
        } catch (Exception e) {
            logger.error("Error deleting album with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("服务器内部错误: " + e.getMessage());
        }
    }

    /**
     * 查询所有专辑。
     * @return 专辑列表
     */
    @GetMapping("/albums/list")
    public ResponseEntity<List<Album>> queryAlbums() {
        try {
            List<Album> albums = albumService.queryAlbums();
            logger.info("Fetched all albums successfully");
            return ResponseEntity.ok(albums);
        } catch (Exception e) {
            logger.error("Error fetching all albums", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 获取用户已购专辑。
     * @param userAccount 用户账号
     * @return 用户已购专辑列表
     */
    @GetMapping("/albums/buy{userAccount:.+}")
    public ResponseEntity<List<Album>> getUserAlbums(@PathVariable String userAccount) {
        if (userAccount == null || userAccount.trim().isEmpty()) {
            logger.warn("User account is missing in the request");
            return ResponseEntity.badRequest().build();
        }
        try {
            List<Album> albums = albumService.getAlbumsByUserAccount(userAccount);
            logger.info("Fetched albums for user account: {}", userAccount);
            return ResponseEntity.ok(albums);
        } catch (Exception e) {
            logger.error("Error fetching albums for user account: {}", userAccount, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 根据专辑 ID 重定向到专辑详情页面。
     * @param Id 专辑 ID
     * @return 重定向响应
     */
    @GetMapping("/albums/{Id}")
    public ResponseEntity<Void> redirectToAlbumDetails(@PathVariable Integer Id) {
        if (Id == null) {
            logger.warn("Album ID is missing in the request");
            return ResponseEntity.badRequest().build();
        }
        logger.info("Redirecting to album details for ID: {}", Id);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/details/" + Id)
                .build();
    }

    /**
     * 获取专辑详情。
     * @param Id 专辑 ID
     * @return 专辑详情
     */
    @GetMapping("/details/{Id}")
    public ResponseEntity<Album> getAlbumDetails(@PathVariable Integer Id) {
        if (Id == null) {
            logger.warn("Album ID is missing in the request");
            return ResponseEntity.badRequest().build();
        }
        try {
            Album album = albumService.searchAlbumById(Id);
            if (album == null) {
                logger.warn("Album not found for ID: {}", Id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            logger.info("Fetched album details for ID: {}", Id);
            return ResponseEntity.ok(album);
        } catch (Exception e) {
            logger.error("Error fetching album details for ID: {}", Id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 根据专辑 ID 获取专辑信息。
     * @param id 专辑 ID
     * @return 专辑信息
     */
    @GetMapping("/album/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Integer id) {
        Album album = albumService.searchAlbumById(id);
        if (album != null) {
            return ResponseEntity.ok(album);  // 如果找到了专辑，返回 200 和专辑数据
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // 如果未找到专辑，返回 404
        }
    }
}
