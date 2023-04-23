package icu.ayaka.img.controller;


import icu.ayaka.img.cache.ImgFileCache;
import icu.ayaka.img.service.IImgFileService;
import icu.ayaka.img.utils.ImgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ayaka-icu
 * @since 2023-04-10
 */
@Controller
@RequestMapping("/img")
public class ImgFileController {

    @Autowired
    IImgFileService imgFileService;

    @Autowired
    ImgFileCache imgFileCache;

    @GetMapping("/file/size/num")
    @ResponseBody
    public String size(){
        return imgFileCache.size();
    }

    @GetMapping("/file/size")
    public String sizeImg(){
        int num = Integer.parseInt(imgFileCache.size());
        return "forward:" + "/img/count?num="+ num;
    }

    @GetMapping("/file.io")
    public void get(@RequestParam(value = "bili",required = false,defaultValue = "1") String bili,HttpServletResponse response){
        String randomID = imgFileCache.getRandomID(bili);
        if (!ObjectUtils.isEmpty(randomID)){
            String path = imgFileService.getById(randomID).getPath();
            //从路径中获取图片流
            ImgUtils.respLocalImg(path,response);
        }
    }
    //http://localhost:8060/img/file.io

    @GetMapping("/file/json")
    @ResponseBody
    public String get(@RequestParam(value = "bili",required = false,defaultValue = "1") String bili){
        String randomID = imgFileCache.getRandomID(bili);
        if (!ObjectUtils.isEmpty(randomID)){
            return imgFileService.getById(randomID).getJson();
        }
        return "输出出错,请重试。";
    }
    //http://localhost:8060/img/file/json

}

