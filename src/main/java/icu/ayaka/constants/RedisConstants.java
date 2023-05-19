package icu.ayaka.constants;

public class RedisConstants {

    //外部URL 横屏
    public static final String CACHE_API_IMG_URL_SCALE1 = "api:img:url:scale1";
    //外部URL 竖屏
    public static final String CACHE_API_IMG_URL_SCALE2 = "api:img:url:scale2";
    //外部URL 似正方形
    public static final String CACHE_API_IMG_URL_SCALE3 = "api:img:url:scale3";
    //图片数
    public static final String CACHE_API_IMG_URL_SIZE = "api:img:url:size";

    //内部File 横屏
    public static final String CACHE_API_IMG_FILE_SCALE1 = "api:img:file:scale1";
    //内部File 竖屏
    public static final String CACHE_API_IMG_FILE_SCALE2 = "api:img:file:scale2";
    //内部File 似正方形条数
    public static final String CACHE_API_IMG_FILE_SCALE3 = "api:img:file:scale3";
    //图片数
    public static final String CACHE_API_IMG_FILE_SIZE = "api:img:file:size";

    //所有图片 横屏
    public static final String CACHE_API_IMG_ALL_SCALE1 = "api:img:all:scale1";
    //所有图片 竖屏
    public static final String CACHE_API_IMG_ALL_SCALE2 = "api:img:all:scale2";
    //所有图片 似正方形条数
    public static final String CACHE_API_IMG_ALL_SCALE3 = "api:img:all:scale3";
    //所有图片数量
    public static final String CACHE_API_IMG_ALL_SIZE = "api:img:all:size";


    //所有uid 脚本
    public static final String SCRIPT_CACHE_ALL_UID = "-- 清除缓存\n" +
            "redis.call('DEL', KEYS[3])\n" +
            "-- 获取 各个缓存id\n" +
            "local m1 = redis.call('SMEMBERS', KEYS[1])\n" +
            "local m2 = redis.call('SMEMBERS', KEYS[2])\n" +
            "-- 将 url库 的元素添加到zset中，分数为1\n" +
            "for _, member in ipairs(m1) do\n" +
            "    redis.call('ZADD', KEYS[3], 1, member)\n" +
            "end\n" +
            "-- 将 file库 的元素添加到zset中，分数为2\n" +
            "for _, member in ipairs(m2) do\n" +
            "    redis.call('ZADD', KEYS[3], 2, member)\n" +
            "end";

    //图片全局唯一 id
    public static final String API_IMG_ID_WORKER = "api:img:id:";

    //GitHub
    public static final String CACHE_API_IMG_GITHUB = "api:img:github:";

}
