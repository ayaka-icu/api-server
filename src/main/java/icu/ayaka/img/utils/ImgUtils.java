package icu.ayaka.img.utils;

import com.alibaba.fastjson2.JSON;
import icu.ayaka.img.dto.ImgFileDto;
import icu.ayaka.img.entity.Img;
import icu.ayaka.img.dto.ImgDto;
import icu.ayaka.img.entity.ImgFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
public class ImgUtils {


    /**
     * 向数据库中添加图片信息 <br>
     * <h1>本地图片<h1/>
     *
     * @param file 文件
     * @return ImgFile
     */
    public static ImgFile newImgByFile(File file) {

        BufferedImage sourceImg;

        try(FileInputStream fileInputStream = new FileInputStream(file)) {

            sourceImg = ImageIO.read(fileInputStream);
            ImgFile imgFile = new ImgFile();
            //路径
            imgFile.setPath(file.getPath());
            //大小
            imgFile.setSize(Double.valueOf(String.format("%.2f", file.length() / 1024.0 / 1024.0)));
            //名字
            String name = file.getName();
            imgFile.setName(file.getName());
            //类型
            if (name.lastIndexOf(".") != -1) {
                imgFile.setType(name.substring(name.lastIndexOf(".") + 1));
            }
            int width = sourceImg.getWidth(), height = sourceImg.getHeight();
            // 源图宽度
            imgFile.setWidth(width);
            // 源图高度
            imgFile.setHeight(height);
            // 计算横屏还是竖屏/其他
            if (1.00 * width / height >= 1.22) { // 4:3
                imgFile.setScale(1);
            } else if (1.00 * height / width >= 1.22) {
                imgFile.setScale(2);
            } else {
                imgFile.setScale(3);
            }
            // JSON
            String json = JSON.toJSONString(ImgFileDto.imgDto(imgFile));
            imgFile.setJson(json);
            // Time
            imgFile.setTime(LocalDateTime.now());
            return imgFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 向数据库中添加图片信息 <br>
     * <h1>URL连接图片<h1/>
     *
     * @param url 图片URL
     * @return Img
     */
    public static Img newImgByUrl(String url) {

        String str1 = "http";
        boolean b = url.contains(str1);
        if (b == false) {
            //判断是否是网络图片的url
            url = "http://*****:8080/" + url;
        }
        InputStream inputStream = null;
        InputStream inputStream2 = null;
        try {
            URL urlImg = new URL(url);
            //创建链接对象
            URLConnection urlConnection = urlImg.openConnection();
            //设置超时
            urlConnection.setConnectTimeout(1000);
            urlConnection.setReadTimeout(5000);
            urlConnection.connect();
            //获取流
            inputStream = urlConnection.getInputStream();
            byte[] data = inputStream.readAllBytes();
            //复制一份流
            inputStream2 = new ByteArrayInputStream(data);

            //读取图片
            BufferedImage sourceImg = ImageIO.read(inputStream2);
            if (sourceImg != null) {

                Img img = new Img();
                //路径
                img.setUrl(url);
                //大小
                img.setSize(Double.valueOf(String.format("%.2f", data.length / 1024.0 / 1024.0)));
                //名字
                if (url.lastIndexOf("/") != -1) {
                    img.setName(url.substring(url.lastIndexOf("/") + 1));
                }
                //类型
                if (url.lastIndexOf(".") != -1) {
                    img.setType(url.substring(url.lastIndexOf(".") + 1));
                }
                int width = sourceImg.getWidth(), height = sourceImg.getHeight();
                // 源图宽度
                img.setWidth(width);
                // 源图高度
                img.setHeight(height);
                // 计算横屏还是竖屏/其他
                if (1.00 * width / height >= 1.22) { // 4:3
                    img.setScale(1);
                } else if (1.00 * height / width >= 1.22) {
                    img.setScale(2);
                } else {
                    img.setScale(3);
                }
                // JSON
                String json = JSON.toJSONString(ImgDto.imgDto(img));
                img.setJson(json);
                // Time
                img.setTime(LocalDateTime.now());
                return img;

                //获取图片格式
                //String format = url.substring(url.lastIndexOf(".") + 1);
                //打印图片
                //ImageIO.write(bufferedImage,format,response.getOutputStream());// 将文件流放入response中
            }
        } catch (Exception e) {
            log.debug("图片异常: {} , 参数URL异常: {}",e,url);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream2 != null) {
                try {
                    inputStream2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    /**
     * <h1>响应体 --> 本地图片</h1>
     *
     * @param path 文件路径
     * @param resp 响应体
     */
    public static void respLocalImg(String path, HttpServletResponse resp) {

        try (InputStream in = new FileInputStream(path);){
            //生产流对象
            //写入Response
            resp.setContentType(MediaType.IMAGE_PNG_VALUE);
            IOUtils.copy(in, resp.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * <h1>响应体 --> URL</h1>
     *
     * @param url 文件路径
     * @param resp 响应体
     */
    public static void respUrlImg(String url, HttpServletResponse resp) {

        boolean b = url.contains("http");
        if (!b) {
            //判断是否是网络图片的url
            url = "http://*****:8080/" + url;
        }
        InputStream in = null;
        try {
            URL urlImg = new URL(url);
            //创建链接对象
            URLConnection urlConnection = urlImg.openConnection();
            //设置超时
            urlConnection.setConnectTimeout(1000);
            urlConnection.setReadTimeout(5000);
            urlConnection.connect();
            //获取流
            in = urlConnection.getInputStream();
            resp.setContentType(MediaType.IMAGE_PNG_VALUE);
            IOUtils.copy(in,resp.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
