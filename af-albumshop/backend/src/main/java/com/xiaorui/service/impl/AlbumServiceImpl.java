package com.xiaorui.service.impl;

import com.xiaorui.beans.Album;
import com.xiaorui.beans.Order;
import com.xiaorui.beans.User;
import com.xiaorui.mapper.AlbumMapper;
import com.xiaorui.mapper.OrderMapper;
import com.xiaorui.mapper.UserMapper;
import com.xiaorui.service.AlbumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * AlbumServiceImpl 类
 * 实现了 AlbumService 接口，处理专辑相关的业务逻辑。
 */
@Service
public class AlbumServiceImpl implements AlbumService {

    private static final Logger logger = LoggerFactory.getLogger(AlbumServiceImpl.class);

    private final AlbumMapper albumMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 构造方法，注入 AlbumMapper。
     * @param albumMapper 专辑映射器
     */
    @Autowired
    public AlbumServiceImpl(AlbumMapper albumMapper) {
        this.albumMapper = albumMapper;
    }

    /**
     * 添加专辑。
     * @param album 专辑对象
     */
    @Override
    public void addAlbum(Album album) {
        try {
            albumMapper.addAlbum(album);
            logger.info("Album added successfully: {}", album);
        } catch (Exception e) {
            logger.error("Error occurred while adding album: {}", album, e);
            throw new RuntimeException("Failed to add album", e);
        }
    }

    /**
     * 修改专辑信息。
     * @param album 专辑对象
     * @return 是否修改成功
     */
    @Override
    public boolean modifyAlbum(Album album) {
        try {
            albumMapper.modifyAlbum(album);
            logger.info("Album modified successfully: {}", album);
            return true;
        } catch (Exception e) {
            logger.error("Error occurred while modifying album: {}", album, e);
            throw new RuntimeException("Failed to modify album", e);
        }
    }

    /**
     * 根据关键词查询专辑。
     * @param keyword 查询关键词
     * @return 符合条件的专辑列表
     */
    @Override
    public List<Album> queryAlbumsByKeyword(String keyword) {
        try {
            List<Album> albums = albumMapper.queryAlbumsByKeyword(keyword);
            logger.info("Albums queried successfully with keyword: {}", keyword);
            return albums;
        } catch (Exception e) {
            logger.error("Error occurred while querying albums by keyword: {}", keyword, e);
            return Collections.emptyList();
        }
    }

    /**
     * 删除专辑。
     * @param id 专辑 ID
     * @return 是否删除成功
     */
    @Override
    public boolean removeAlbum(Integer id) {
        try {
            Album albumToDelete = albumMapper.queryAlbumById(id);
            if (albumToDelete == null) {
                logger.warn("Album not found");
                return false;
            }
            albumMapper.removeAlbum(id);
            logger.info("Album removed successfully with id: {}", id);
            return true;
        } catch (Exception e) {
            logger.error("Error occurred while removing album with id: {}", id, e);
            throw new RuntimeException("Failed to remove album", e);
        }
    }

    /**
     * 查询所有专辑。
     * @return 专辑列表
     */
    @Override
    public List<Album> queryAlbums() {
        try {
            List<Album> albums = albumMapper.queryAlbums();
            logger.info("All albums queried successfully");
            return albums;
        } catch (Exception e) {
            logger.error("Error occurred while querying all albums", e);
            return Collections.emptyList();
        }
    }

    /**
     * 根据用户账号查询用户已购专辑。
     * @param account 用户账号
     * @return 用户已购专辑列表
     */
    @Override
    public List<Album> getAlbumsByUserAccount(String account) {
        try {
            User user = userMapper.findUserByAccount(account);
            if (user == null) {
                logger.warn("User not found with account: {}", account);
                return Collections.emptyList();
            }

            List<Order> userOrders = orderMapper.queryOrderByAccount(user.getUser_account());
            if (userOrders == null || userOrders.isEmpty()) {
                logger.info("No orders found for user with account: {}", account);
                return Collections.emptyList();
            }

            List<Integer> albumIds = new ArrayList<>();
            for (Order order : userOrders) {
                albumIds.add(order.getOrder_album_id());
            }

            List<Album> albums = albumMapper.findAlbumsByIds(albumIds);
            logger.info("Albums retrieved successfully for user with account: {}", account);
            return albums;
        } catch (Exception e) {
            logger.error("Error occurred while retrieving albums for user account: {}", account, e);
            return Collections.emptyList();
        }
    }

    /**
     * 根据专辑 ID 查询专辑。
     * @param id 专辑 ID
     * @return 专辑对象
     */
    @Override
    public Album searchAlbumById(Integer id) {
        try {
            Album album = albumMapper.queryAlbumById(id);
            if (album != null) {
                logger.info("Album queried successfully by id: {}", id);
            } else {
                logger.warn("Album not found with id: {}", id);
            }
            return album;
        } catch (Exception e) {
            logger.error("Error occurred while querying album by id: {}", id, e);
            return null;
        }
    }

    /**
     * 根据多个条件搜索专辑。
     * @param query 查询关键词
     * @param searchByAlbumName 是否按专辑名称搜索
     * @param searchByAlbumTranslation 是否按专辑翻译名称搜索
     * @param searchBySinger 是否按歌手搜索
     * @param searchByLanguage 是否按语言搜索
     * @return 符合条件的专辑列表
     */
    @Override
    public List<Album> searchAlbums(String query, Boolean searchByAlbumName, Boolean searchByAlbumTranslation, Boolean searchBySinger, Boolean searchByLanguage) {
        try {
            searchByAlbumName = searchByAlbumName != null ? searchByAlbumName : false;
            searchByAlbumTranslation = searchByAlbumTranslation != null ? searchByAlbumTranslation : false;
            searchBySinger = searchBySinger != null ? searchBySinger : false;
            searchByLanguage = searchByLanguage != null ? searchByLanguage : false;

            List<Album> albums = albumMapper.searchAlbums(query, searchByAlbumName, searchByAlbumTranslation, searchBySinger, searchByLanguage);
            logger.info("Albums searched successfully with query: {}", query);
            return albums;
        } catch (Exception e) {
            logger.error("Error occurred while searching albums with query: {}", query, e);
            return Collections.emptyList();
        }
    }
}