package icu.ayaka.img.controller.manage;

import icu.ayaka.constants.ApiConstants;
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
@RequestMapping("/img")
public class RefreshCache {

    @Autowired
    ImgCache imgCache;
    @Autowired
    ImgFileCache imgFileCache;
    @Autowired
    ApiConstants apiConstants;

    /**
     * 刷新 url库 缓存
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

}
