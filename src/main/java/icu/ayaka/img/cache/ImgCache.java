package icu.ayaka.img.cache;

import icu.ayaka.img.entity.Img;
import icu.ayaka.img.service.IImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

import static icu.ayaka.constants.ImgConstants.*;
import static icu.ayaka.constants.RedisConstants.*;

@Configuration
public class ImgCache {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IImgService imgService;

    /**
     * 删除缓存 并 重建缓存
     * 获取数据库 tb_img 中 所有记录 进行建立缓存
     *
     * @return 添加条数
     */
    public Long addAll() {

        //清除缓存
        redisTemplate.delete(CACHE_API_IMG_URL_SCALE1);
        redisTemplate.delete(CACHE_API_IMG_URL_SCALE2);
        redisTemplate.delete(CACHE_API_IMG_URL_SCALE3);

        //新建缓存
        List<Img> list = imgService.list();
        Long total = 0L;
        for (Img img : list) {
            Integer id = img.getId();
            Integer scale = img.getScale();
            if (SCALE_ONE.equals(scale.toString())) {
                total += redisTemplate.opsForSet().add(CACHE_API_IMG_URL_SCALE1, id.toString());
            } else if (SCALE_TWO.equals(scale.toString())) {
                total += redisTemplate.opsForSet().add(CACHE_API_IMG_URL_SCALE2, id.toString());
            } else if (SCALE_THREE.equals(scale.toString())){
                total += redisTemplate.opsForSet().add(CACHE_API_IMG_URL_SCALE3, id.toString());
            }
        }

        //返回条数
        return total;
    }

    /**
     * 随机获取 tb_img 图片ID
     *
     * @param scale 图片类型 1 / 2 / 3
     * @return 图片ID
     */
    public String getRandomID(String scale){

        if (SCALE_ONE.equals(scale)){
            return redisTemplate.opsForSet().randomMember(CACHE_API_IMG_URL_SCALE1);
        } else if (SCALE_TWO.equals(scale)) {
            return redisTemplate.opsForSet().randomMember(CACHE_API_IMG_URL_SCALE2);
        } else if (SCALE_THREE.equals(scale)) {
            return redisTemplate.opsForSet().randomMember(CACHE_API_IMG_URL_SCALE3);
        }

        return null;
    }

    /**
     * 添加一天缓存
     * @param id id
     * @param scale scale
     */
    public void addNewCache(String id, String scale){
        if (SCALE_ONE.equals(scale)){
            redisTemplate.opsForSet().add(CACHE_API_IMG_URL_SCALE1,id);
        } else if (SCALE_TWO.equals(scale)) {
            redisTemplate.opsForSet().add(CACHE_API_IMG_URL_SCALE2,id);
        } else if (SCALE_THREE.equals(scale)) {
            redisTemplate.opsForSet().add(CACHE_API_IMG_URL_SCALE3,id);
        }
    }

}
