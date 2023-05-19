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
     * 获取 url 库中链接个数
     * @return 链接个数
     */
    public String size(){
        return redisTemplate.opsForValue().get(CACHE_API_IMG_URL_SIZE);
    }

    /**
     * 刷新 / 创建 条数
     * @return Url 条数
     */
    public long refreshSize(){
        Long s1 = redisTemplate.opsForSet().size(CACHE_API_IMG_URL_SCALE1);
        Long s2 = redisTemplate.opsForSet().size(CACHE_API_IMG_URL_SCALE2);
        Long s3 = redisTemplate.opsForSet().size(CACHE_API_IMG_URL_SCALE3);
        long sum = (s1 == null ? 0 : s1) + (s2 == null ? 0 : s2) + (s3 == null ? 0 : s3);
        redisTemplate.opsForValue().set(CACHE_API_IMG_URL_SIZE, Long.toString(sum));
        return sum;
    }

    /**
     * 删除缓存 并 重建缓存
     * 获取数据库 tb_img 中 所有记录 进行建立缓存
     * 注意: 这个过程已经创建了条数
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
            Long id = img.getId();
            Integer scale = img.getScale();
            switch (scale.toString()) {
                case SCALE_ONE:
                    total += redisTemplate.opsForSet().add(CACHE_API_IMG_URL_SCALE1, id.toString());
                    break;
                case SCALE_TWO:
                    total += redisTemplate.opsForSet().add(CACHE_API_IMG_URL_SCALE2, id.toString());
                    break;
                case SCALE_THREE:
                    total += redisTemplate.opsForSet().add(CACHE_API_IMG_URL_SCALE3, id.toString());
                    break;
            }
        }
        //添加 url 个数
        redisTemplate.opsForValue().set(CACHE_API_IMG_URL_SIZE,total.toString());

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
     * 添加一条缓存
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
        redisTemplate.opsForValue().increment(CACHE_API_IMG_URL_SIZE);
    }

}
