package com.xiaorui.service;

import com.xiaorui.beans.Album;
import java.util.List;

/**
 * AlbumService 接口
 * 定义与专辑相关的业务逻辑方法。
 */
public interface AlbumService {

    /**
     * 添加专辑。
     * @param album 专辑对象
     */
    public void addAlbum(Album album);

    /**
     * 修改专辑信息。
     * @param album 专辑对象
     * @return 是否修改成功
     */
    public boolean modifyAlbum(Album album);

    /**
     * 根据关键词查询专辑。
     * @param keyword 查询关键词
     * @return 符合条件的专辑列表
     */
    public List<Album> queryAlbumsByKeyword(String keyword);

    /**
     * 删除专辑。
     * @param id 专辑 ID
     * @return 是否删除成功
     */
    public boolean removeAlbum(Integer id);

    /**
     * 根据用户账号获取用户已购专辑。
     * @param Account 用户账号
     * @return 用户已购专辑列表
     */
    public List<Album> getAlbumsByUserAccount(String Account);

    /**
     * 根据专辑 ID 查询专辑。
     * @param Id 专辑 ID
     * @return 专辑对象
     */
    public Album searchAlbumById(Integer Id);

    /**
     * 根据多个条件搜索专辑。
     * @param query 查询关键词
     * @param searchByAlbumName 是否按专辑名称搜索
     * @param searchByAlbumTranslation 是否按专辑翻译名称搜索
     * @param searchBySinger 是否按歌手搜索
     * @param searchByLanguage 是否按语言搜索
     * @return 符合条件的专辑列表
     */
    public List<Album> searchAlbums(String query, Boolean searchByAlbumName, Boolean searchByAlbumTranslation, Boolean searchBySinger, Boolean searchByLanguage);

    /**
     * 查询所有专辑。
     * @return 专辑列表
     */
    public List<Album> queryAlbums();
}
