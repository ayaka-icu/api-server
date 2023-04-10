package icu.ayaka.img.controller;

import icu.ayaka.img.constants.ApiConstants;
import icu.ayaka.img.service.IImgService;
import icu.ayaka.img.utils.ImgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;


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
    private ApiConstants apiConstants;

    @Autowired
    private IImgService imgService;

    /**
     * 获取随机图片 Url
     * @return 随机图片 URL
     */
    @GetMapping("/url")
    public String random(@RequestParam(value = "bili",required = false,defaultValue = "1") String bili){

        if ("1".equals(bili)){
            return "redirect:" + imgService.getRandom().getUrl();
        }
        if ("2".equals(bili)){
            return "redirect:" + imgService.getRandom2().getUrl();
        }
        return "redirect:" + "/static/404.jpg";
    }

    /**
     * 获取随机图片 Url
     * @return 随机图片 URL
     */
    @GetMapping("/url.io")
    public void random2(@RequestParam(value = "bili",required = false,defaultValue = "1") String bili,HttpServletResponse response){

        if ("1".equals(bili)){
            String s = imgService.getRandom().getUrl();
            ImgUtils.respUrlImg(s,response);
        }
        if ("2".equals(bili)){
            String s = imgService.getRandom2().getUrl();
            ImgUtils.respUrlImg(s,response);
        }
    }

    /**
     * 获取随机图片 JSON
     * @return 随机图片 JSON
     */
    @GetMapping("/url/json")
    @ResponseBody
    public String randomJson(@RequestParam(value = "bili",required = false,defaultValue = "1") String bili){
        if ("1".equals(bili)){
            return imgService.getRandom().getJson();
        }
        if ("2".equals(bili)){
            return imgService.getRandom2().getJson();
        }
        return "输出出错,请重试。";
    }

    @GetMapping(value = "/getImg/{src}")
    public void getImg(@PathVariable("src")String src, HttpServletResponse resp) throws IOException {
        ImgUtils.respUrlImg(src,resp);
    }

}
