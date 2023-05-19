package icu.ayaka.img.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static icu.ayaka.constants.ImgConstants.*;
import static icu.ayaka.constants.RedisConstants.*;

@Component
public class AllCache {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 更新所有库 缓存
     * @return 更新条数
     */
    public long addAllCache(){
        //清除缓存
        redisTemplate.delete(CACHE_API_IMG_ALL_SCALE1);
        redisTemplate.delete(CACHE_API_IMG_ALL_SCALE2);
        redisTemplate.delete(CACHE_API_IMG_ALL_SCALE3);
        redisTemplate.delete(CACHE_API_IMG_ALL_SIZE);
        //执行脚本
        RedisScript<Void> redisScript = new DefaultRedisScript<>(SCRIPT_CACHE_ALL_UID, Void.class);
        redisTemplate.execute(redisScript, Arrays.asList(CACHE_API_IMG_URL_SCALE1, CACHE_API_IMG_FILE_SCALE1, CACHE_API_IMG_ALL_SCALE1));
        redisTemplate.execute(redisScript, Arrays.asList(CACHE_API_IMG_URL_SCALE2, CACHE_API_IMG_FILE_SCALE2, CACHE_API_IMG_ALL_SCALE2));
        redisTemplate.execute(redisScript, Arrays.asList(CACHE_API_IMG_URL_SCALE3, CACHE_API_IMG_FILE_SCALE3, CACHE_API_IMG_ALL_SCALE3));
        String url = redisTemplate.opsForValue().get(CACHE_API_IMG_URL_SIZE);
        String file = redisTemplate.opsForValue().get(CACHE_API_IMG_FILE_SIZE);
        if (url != null && file != null){
            long size = Long.parseLong(url) + Long.parseLong(file);
            redisTemplate.opsForValue().set(CACHE_API_IMG_ALL_SIZE, Long.toString(size));
            return size;
        }else if (url != null) {
            redisTemplate.opsForValue().set(CACHE_API_IMG_ALL_SIZE, url);
            return Long.parseLong(url);
        }else if (file != null) {
            redisTemplate.opsForValue().set(CACHE_API_IMG_ALL_SIZE, file);
            return Long.parseLong(file);
        }
        return 0L;
    }

    /**
     * 随机获取 tb_img 图片ID
     *
     * @param scale 图片类型 1 / 2 / 3
     * @return 图片ID
     */
    public ZSetOperations.TypedTuple<String> getRandomID(String scale){

        if (SCALE_ONE.equals(scale)){
            return redisTemplate.opsForZSet().randomMemberWithScore(CACHE_API_IMG_ALL_SCALE1);
        } else if (SCALE_TWO.equals(scale)) {
            return redisTemplate.opsForZSet().randomMemberWithScore(CACHE_API_IMG_ALL_SCALE2);
        } else if (SCALE_THREE.equals(scale)) {
            return redisTemplate.opsForZSet().randomMemberWithScore(CACHE_API_IMG_ALL_SCALE3);
        }

        return null;
    }

}
