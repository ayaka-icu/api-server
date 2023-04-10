package icu.ayaka.img.controller;

import icu.ayaka.img.utils.ImgUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Random;

@Controller
@RequestMapping("/img")
public class ImgToolController {

    @GetMapping("/tool/urls")
    public String v1(@RequestParam(value = "urls",required = true) String urls){
        String[] split = urls.split("@@");
        return "redirect:" + split[new Random().nextInt(split.length)];
    }
    //Test: http://localhost:8060/img/tool/urls?urls=http://img.ayaka.icu/i/2023/03/25/641e74f1770ad.jpg@@http://img.ayaka.icu/i/2023/03/25/641e80711778b.jpg

    @GetMapping("/tool/urls.io")
    @ResponseBody
    public void v2(@RequestParam(value = "urls",required = true) String urls, HttpServletResponse response){
        String[] split = urls.split("@@");
        String url = split[new Random().nextInt(split.length)];
        ImgUtils.respUrlImg(url,response);
    }
    //http://localhost:8060/img/tool/urls.io?urls=http://img.ayaka.icu/i/2023/03/25/641e74f1770ad.jpg@@http://img.ayaka.icu/i/2023/03/25/641e80711778b.jpg
   /* @RequestMapping(value = "/urls",method = RequestMethod.GET)
    public String getUrl(@RequestParam("url") String urls){
        String[] split = urls.split("@@");
        return "redirect:" + split[new Random().nextInt(split.length)];
    }*/

}
