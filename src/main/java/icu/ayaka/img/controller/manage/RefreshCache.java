package icu.ayaka.img.controller.manage;

import icu.ayaka.constants.ApiConstants;
import icu.ayaka.img.cache.AllCache;
import icu.ayaka.img.cache.ImgCache;
import icu.ayaka.img.cache.ImgFileCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 这里除了调用
 * 应该使用任务进行定时刷新缓存，写入很少，大多数情况下，缓存是与数据库同步的，但是要做兜底
 *
 */
@RestController
@RequestMapping("/img/manage")
public class RefreshCache {

    @Autowired
    private ImgCache imgCache;
    @Autowired
    private ImgFileCache imgFileCache;
    @Autowired
    private ApiConstants apiConstants;
    @Autowired
    private AllCache allCache;

    /**
     * 刷新 所有缓存
     *
     * @param auth 认证
     * @return log
     */
    @PostMapping("/cache/all")
    public String cacheAll(@RequestBody String auth){
        if (!apiConstants.auth.equals(auth)){
            return "你没有权限!";
        }
        long t = allCache.addAllCache();
        return t> 0L ? "缓存刷新成功！共写入缓存: " + t + " 条数据" : "刷新失败!";
    }

    /**
     * 刷新 url库 缓存
     * 注意: 这个过程已经创建了条数
     * @param auth 认证
     * @return log
     */
    @PostMapping("/cache/url")
    public String cacheUrl(@RequestBody String auth){
        if (!apiConstants.auth.equals(auth)){
            return "你没有权限!";
        }
        Long all = imgCache.addAll();
        return all > 0 ? "缓存刷新成功！共写入缓存: " + all + " 条数据" : "刷新失败!";
    }


    /**
     * 刷新 file 库 缓存
     * 注意: 这个过程已经创建了条数
     * @param auth 认证
     * @return log
     */
    @PostMapping("/cache/file")
    public String cacheFile(@RequestBody String auth){
        if (!apiConstants.auth.equals(auth)){
            return "你没有权限!";
        }
        Long all = imgFileCache.addAll();
        return all > 0 ? "缓存刷新成功！共写入缓存: " + all + " 条数据" : "刷新失败!";
    }


    /**
     * 单独刷新 所有 图片条数
     * @param auth 认证
     * @return size
     */
    @PostMapping("/cache/size")
    public String cacheSize(@RequestBody String auth){
        if (!apiConstants.auth.equals(auth)){
            return "你没有权限!";
        }
        long t1 = imgCache.refreshSize();
        long t2 = imgFileCache.refreshSize();
        long sum = t1 + t2;
        return sum > 0 ? "缓存刷新成功！所有图片条数: " + sum + " 条数据" : "刷新失败!";
    }

    /**
     * 单独刷新 url库 图片条数
     * @param auth 认证
     * @return size
     */
    @PostMapping("/cache/url/size")
    public String cacheUrlSize(@RequestBody String auth){
        if (!apiConstants.auth.equals(auth)){
            return "你没有权限!";
        }
        long size = imgCache.refreshSize();
        return size > 0 ? "缓存刷新成功！Url库图片条数: " + size + " 条数据" : "刷新失败!";
    }

    /**
     * 单独刷新 File库 图片条数
     * @param auth 认证
     * @return size
     */
    @PostMapping("/cache/file/size")
    public String cacheFileSize(@RequestBody String auth){
        if (!apiConstants.auth.equals(auth)){
            return "你没有权限!";
        }
        long size = imgFileCache.refreshSize();
        return size > 0 ? "缓存刷新成功！File库图片条数: " + size + " 条数据" : "刷新失败!";
    }

}
