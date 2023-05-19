package icu.ayaka.img.controller;

import icu.ayaka.img.cache.ImgCache;
import icu.ayaka.img.service.IImgService;
import icu.ayaka.img.utils.ImgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ayaka-icu
 * @since 2023-04-10
 */
@Controller()
@RequestMapping("/img")
public class ImgController {

    @Autowired
    private IImgService imgService;

    @Autowired
    private ImgCache imgCache;

    @GetMapping("/url/size/num")
    @ResponseBody
    public String size(){
        return imgCache.size();
    }

    @GetMapping("/url/size")
    public String sizeImg(){
        int num = Integer.parseInt(imgCache.size());
        return "forward:" + "/img/count?num="+ num;
    }

    /**
     * 获取随机图片 Url
     * @return 随机图片 URL
     */
    @GetMapping("/url")
    public String random(@RequestParam(value = "bili",required = false,defaultValue = "1") String bili){
        String randomID = imgCache.getRandomID(bili);
        if (!ObjectUtils.isEmpty(randomID)){
            return "redirect:" +  imgService.getById(randomID).getUrl();
        }
        return "redirect:" + "/static/404.jpg";
    }
    //Test: http://localhost:8060/img/url?bili=3


    /**
     * 获取随机图片 Url
     *
     */
    @GetMapping("/url.io")
    public void random2(@RequestParam(value = "bili",required = false,defaultValue = "1") String bili,HttpServletResponse response){
        String randomID = imgCache.getRandomID(bili);
        if (!ObjectUtils.isEmpty(randomID)){
            String s = imgService.getById(randomID).getUrl();
            ImgUtils.respUrlImg(s,response);
        }
    }
    //Test: http://localhost:8060/img/url.io
    //Test: http://localhost:8060/img/url.io?bili=3

    /**
     * 获取随机图片 JSON
     * @return 随机图片 JSON
     */
    @GetMapping("/url/json")
    @ResponseBody
    public String randomJson(@RequestParam(value = "bili",required = false,defaultValue = "1") String bili){
        String randomID = imgCache.getRandomID(bili);
        if (!ObjectUtils.isEmpty(randomID)){
            return imgService.getById(randomID).getJson();
        }
        return "输出出错,请重试。";
    }
    //Test: http://localhost:8060/img/url/json


}
