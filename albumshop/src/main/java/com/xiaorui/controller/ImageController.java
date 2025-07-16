package com.xiaorui.controller;

import com.xiaorui.beans.Album;
import com.xiaorui.service.AlbumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

/**
 * ImageController 控制器类
 * 处理与图片上传和访问相关的 HTTP 请求。
 */
@RestController
@CrossOrigin // 解决跨域问题
@RequestMapping("/images")
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Value("${image.upload-dir:src/main/resources/static/images/}") // 配置上传目录
    private String uploadDir;

    private final AlbumService albumService;

    /**
     * 构造方法，使用自动注入方式注入 AlbumService。
     * @param albumService 专辑服务类
     */
    public ImageController(AlbumService albumService) {
        this.albumService = albumService;
    }

    /**
     * 上传图片接口并更新指定专辑的封面。
     * @param albumId 专辑 ID，可选参数
     * @param file 上传的文件
     * @return 包含图片访问 URL 的响应
     */
    @PostMapping({"/{albumId}", "/"})
    public ResponseEntity<?> uploadImage(@PathVariable(value = "albumId", required = false) Integer albumId,
                                         @RequestParam("file") MultipartFile file) {
        logger.info("接收到的专辑 ID: {}", albumId);
        logger.info("接收到的文件名: {}", file.getOriginalFilename());
        try {
            // 保存文件
            String uniqueFileName = UUID.randomUUID() + getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
            Path destinationPath = Paths.get(System.getProperty("user.dir"), uploadDir, uniqueFileName).normalize();
            Files.createDirectories(destinationPath.getParent());
            file.transferTo(destinationPath.toFile());

            // 动态生成完整 URL
            String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/images/")
                    .path(uniqueFileName)
                    .toUriString();

            // 处理专辑封面更新逻辑
            if (albumId != null) {
                Album album = albumService.searchAlbumById(albumId);
                if (album == null) {
                    logger.warn("Album not found for albumId: {}", albumId);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("专辑不存在");
                }
                album.setAlbum_cover(fileUrl);
                boolean isUpdated = albumService.modifyAlbum(album);
                if (!isUpdated) {
                    logger.warn("Failed to update album_cover for albumId: {}", albumId);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("专辑信息更新失败");
                }
                logger.info("Album cover updated successfully for albumId: {}", albumId);
            }

            // 无论是否更新专辑，返回图片 URL
            return ResponseEntity.ok(new ImageResponse(fileUrl));
        } catch (IOException e) {
            logger.error("File save error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件保存失败: " + e.getMessage());
        }
    }

    /**
     * 根据文件名获取图片内容。
     * @param filename 文件名
     * @return 图片文件流
     */
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable("filename") String filename) {
        try {
            Path filePath = Paths.get(System.getProperty("user.dir"), uploadDir, filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                logger.warn("File not found: {}", filename);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            logger.error("Error retrieving file: {}", filename, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 获取文件扩展名。
     * @param fileName 文件名
     * @return 文件扩展名
     */
    private String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf(".");
        return lastIndexOfDot != -1 ? fileName.substring(lastIndexOfDot) : "";
    }

    /**
     * 图片响应类，包含图片 URL。
     */
    public static class ImageResponse {
        private String url;

        public ImageResponse(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
