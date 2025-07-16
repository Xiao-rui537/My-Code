package com.xiaorui.mapper;

import com.xiaorui.beans.Album;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * AlbumMapper 接口
 * 定义与 Album 相关的数据库操作方法。
 */
@Mapper
public interface AlbumMapper {

    /**
     * 添加一张专辑。
     * @param album 专辑对象
     */
    public void addAlbum(Album album);

    /**
     * 删除一张专辑。
     * @param id 专辑 ID
     */
    public void removeAlbum(Integer id);

    /**
     * 修改一张专辑。
     * @param album 专辑对象
     */
    public void modifyAlbum(Album album);

    /**
     * 查询全部专辑。
     * @return 专辑列表
     */
    public List<Album> queryAlbums();

    /**
     * 查询专辑总数。
     * @return 专辑总数
     */
    public Integer queryCountOfAlbums();

    /**
     * 根据关键词查询专辑。
     * @param keyword 查询关键词
     * @return 符合条件的专辑列表
     */
    public List<Album> queryAlbumsByKeyword(String keyword);

    /**
     * 根据歌手查询专辑。
     * @param singer 歌手名称
     * @return 符合条件的专辑对象
     */
    public Album queryAlbumBySinger(String singer);

    /**
     * 根据专辑 ID 查询专辑。
     * @param id 专辑 ID
     * @return 专辑对象
     */
    public Album queryAlbumById(Integer id);

    /**
     * 根据专辑 ID 列表批量查询专辑。
     * @param albumIds 专辑 ID 列表
     * @return 专辑列表
     */
    public List<Album> findAlbumsByIds(List<Integer> albumIds);

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
}
