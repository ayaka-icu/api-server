package icu.ayaka.img.controller;

import icu.ayaka.img.cache.ImgCache;
import icu.ayaka.img.cache.ImgFileCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/img")
public class AllController {

    @Autowired
    private ImgCache imgCache;
    @Autowired
    private ImgFileCache imgFileCache;

    @GetMapping("/size/num")
    @ResponseBody
    public Long sizeNum(){
        String t1 = imgCache.size();
        String t2 = imgFileCache.size();
        if (t1 != null && t2 != null){
            return Long.parseLong(t1) + Long.parseLong(t2);
        } else if (t1 != null) {
            return Long.parseLong(t1);
        } else if (t2 != null) {
            return Long.parseLong(t2);
        }
        return null;
    }

    @GetMapping("/size")
    public String size(){
        String t1 = imgCache.size();
        String t2 = imgFileCache.size();
        if (t1 != null && t2 != null){
            long num = Long.parseLong(t1) + Long.parseLong(t2);
            return "forward:" + "/img/count?num="+ num;
        } else if (t1 != null) {
            return "forward:" + "/img/count?num="+ t1;
        } else if (t2 != null) {
            return "forward:" + "/img/count?num="+ t2;
        }
        return null;
    }

}
