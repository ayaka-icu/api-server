package icu.ayaka.img.controller;

import icu.ayaka.ApiApplication;
import icu.ayaka.img.utils.CountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

import static icu.ayaka.constants.RedisConstants.CACHE_API_IMG_GITHUB;

@Controller
@RequestMapping("/img")
public class CountController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 通过指定数字 返回计数图片
     * @param max 位数格式,不够前面补 0
     * @param num 要计数的数字 最大 11 位数
     * @return 计数图片
     */
    @GetMapping("/count")
    @ResponseBody
    public ResponseEntity<byte[]> count(@RequestParam(value = "max", required = false, defaultValue = "6") String max,
                                        @RequestParam(value = "num", required = true, defaultValue = "0") String num){

        char[] array = CountUtils.byArray(max, num, 11);

        if (array == null) {
            return null;
        }

        return this.imgCreate(array);
    }

   /* *//**
     * 初始化创建 github 用户访问量
     * @param user  github用户名
     * @param init  初始化访问数量
     * @return  String
     *//*
    @GetMapping("/github/create/{user}")
    @ResponseBody
    public String githubCreate(@PathVariable("user") String user,
                                               @RequestParam(value = "init", required = false, defaultValue = "0") String init){
        //非法参数判断 ^[1-9]\\d*$
        if (!init.matches("^[1-9]\\d*$")) {
            return "添加失败,初始参数 init 错误, 请输入正确数值！";
        }
        //github用户判断
        boolean isUser = this.isUser(user);
        //用户不存在
        if (!isUser){
            return "用户不存在！";
        }
        //用户存在 添加缓存
        Boolean is = redisTemplate.opsForValue().setIfAbsent(CACHE_API_IMG_GITHUB + user, init);
        //是否已经创建
        if (!Boolean.TRUE.equals(is)){
            return "用户已初始化过访问量，如需更改，请发联系我：2282514478@qq.com";
        }
        //创建成功
        return "添加成功，初始访问量:" + init;
    }

    *//**
     * github访问计数 +1 并返回一张计数图片
     * @param user  github名称
     * @param max   位数格式 默认为6
     * @return  计数图片
     *//*
    @GetMapping("/github/{user}")
    @ResponseBody
    public ResponseEntity<byte[]> github(@PathVariable("user") String user,
                                         @RequestParam(value = "max", required = false, defaultValue = "6") String max){
        //github用户判断
        boolean isUser = this.isUser(user);
        //用户不存在
        if (!isUser){
            return null;
        }
        //自增并返回访问数量
        Long num = redisTemplate.opsForValue().increment(CACHE_API_IMG_GITHUB + user);
        //初步算法char数组
        char[] array = CountUtils.byArray(max, String.valueOf(num), 8);
        //算法失败
        if (array == null) {
            return null;
        }
        //char -> 图片 -> response封装
        return this.imgCreate(array);
    }*/



    // =============================================================


    /**
     * 通过字符数组 生成数字图片
     * @param array 字符数组
     * @return ResponseEntity<byte[]>
     */
    public ResponseEntity<byte[]> imgCreate(char[] array){
        ArrayList<String> imagePath = new ArrayList<>();

        for (char c : array) {
            imagePath.add(c + ".gif");
        }

        // 读取多张PNG图片
        List<BufferedImage> images = new ArrayList<>();

        for (String path : imagePath) {
            try(InputStream is = ApiApplication.class.getClassLoader().getResourceAsStream("static/count/"+path)){
                BufferedImage image = ImageIO.read(is);
                images.add(image);
            }catch (IOException e){
                e.printStackTrace();
            }
        }


        // 计算合并后的PNG图片大小
        // 因为添加的图片大小是相同的 这样写
        int w = 45 * images.size();
        int h = 100;

        /* 因为这里是 图片大小不同的使用用
        int w = 0;
        int h = 0;
        for (BufferedImage image : images) {
            w += image.getWidth();
            h = Math.max(h, image.getHeight()); //大的高度作数
        }
        */

        // 创建新的PNG图片
        BufferedImage mergedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        // 将多张PNG图片合并成一张PNG图片
        Graphics2D g2d = mergedImage.createGraphics();
        int x = 0;
        for (BufferedImage image : images) {
            g2d.drawImage(image, x, 0, null);
            x += image.getWidth();
        }
        g2d.dispose();

        // 将合并后的PNG图片转换成字节数组
        try ( ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            ImageIO.write(mergedImage, "png", baos);
            byte[] imageData = baos.toByteArray();
            // 返回合并后的PNG图片
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 判断是否是 github 正确用户
     * @param user  GitHub user name
     * @return  否是 github 正确用户
     */
    private boolean isUser(String user){
        try {
            String username = "example";
            URL url = new URL("https://api.github.com/users/" + user);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                //用户存在
                return true;
            } else if (responseCode == 404) {
                //用户不存在
                return false;
            } else {
                //其他情况
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}

