package icu.ayaka.img.controller;


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

    @GetMapping("/file.io")
    public void get(@RequestParam(value = "bili",required = false,defaultValue = "1") String bili,HttpServletResponse response){
        //从数据库中查询
        String path = null;
        if ("1".equals(bili)){
            path = imgFileService.getRandom().getPath();
        }
        if ("2".equals(bili)){
            path = imgFileService.getRandom2().getPath();
        }
        //从路径中获取图片流
        if (!ObjectUtils.isEmpty(path)){
            ImgUtils.respLocalImg(path,response);
        }
    }

    @GetMapping("/file/json")
    @ResponseBody
    public String get(@RequestParam(value = "bili",required = false,defaultValue = "1") String bili){
        //从数据库中查询
        if ("1".equals(bili)){
            return imgFileService.getRandom().getJson();
        }
        if ("2".equals(bili)){
            return imgFileService.getRandom2().getJson();
        }
        return imgFileService.getRandom().getJson();
    }


}

