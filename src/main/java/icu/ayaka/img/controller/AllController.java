package icu.ayaka.img.controller;

import icu.ayaka.img.cache.AllCache;
import icu.ayaka.img.cache.ImgCache;
import icu.ayaka.img.cache.ImgFileCache;
import icu.ayaka.img.service.IImgFileService;
import icu.ayaka.img.service.IImgService;
import icu.ayaka.img.utils.ImgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/img")
public class AllController {

    @Autowired
    private AllCache allCache;
    @Autowired
    private ImgCache imgCache;
    @Autowired
    private ImgFileCache imgFileCache;
    @Autowired
    private IImgService imgService;
    @Autowired
    private IImgFileService imgFileService;

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

    @GetMapping("/all")
    public String randomAll(@RequestParam(value = "bili",required = false,defaultValue = "1") String bili, HttpServletResponse response){
        ZSetOperations.TypedTuple<String> randomID = allCache.getRandomID(bili);
        Double score = randomID.getScore();
        if (score == null){
            return null;
        }
        if (score == 1.0){
            return "redirect:" +  imgService.getById(randomID.getValue()).getUrl();
        }
        if (score == 2.0) {
            ImgUtils.respLocalImg(imgFileService.getById(randomID.getValue()).getPath(),response);
            return null;
        }
        return null;
    }

}
