package icu.ayaka.img.task;

import icu.ayaka.img.cache.ImgCache;
import icu.ayaka.img.cache.ImgFileCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@Slf4j
public class CacheTask {

    @Autowired
    ImgCache imgCache;
    @Autowired
    ImgFileCache imgFileCache;

    /**
     * 每 凌晨 3点 对缓存进行刷新
     * 应该使用任务进行定时刷新缓存，写入很少，大多数情况下，缓存是与数据库同步的，但是要做兜底
     */
    @Scheduled(cron = "0 0 3 * * ?") //TODO: 加入到配置文件，自定义cron表达式
    public void cacheTask() {

        log.info("================= 凌晨3点 开始执行图片缓存刷新任务！ =================");

        Long a = imgCache.addAll();
        if (a != null && a > 0) {
            log.info("[URL图片]缓存刷新成功！ 共写入: " + a + " 条数据");
        } else {
            log.debug("[URL图片]缓存刷新错误！");
        }

        Long b = imgFileCache.addAll();
        if (b != null && b > 0) {
            log.info("[本地图片]缓存刷新成功！ 共写入: " + b + " 条数据");
        } else {
            log.debug("[本地图片]缓存刷新错误！");
        }

        if (a != null && a > 0 && b != null && b > 0){
            log.info("================= 凌晨3点 缓存刷新任务结束 重建成功！ =================");
        } else {
            log.debug("================ 凌晨3点 缓存刷新任务结束 重建失败！ =================");
        }

    }

}
