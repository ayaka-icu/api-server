package com.ganga.controller;

import com.ganga.constants.ApiConstants;
import com.ganga.api.ImgApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

@Controller()
@RequestMapping("/img")
public class ImgController {

    @Autowired
    private ApiConstants apiConstants;

    @GetMapping(value = "/random")
    public void get2(HttpServletResponse resp) throws IOException {
        if (apiConstants.isRelative){
            ImgApi.imgApiSrc(true,apiConstants.relativePath,resp);
        }
        ImgApi.imgApiSrc(false,apiConstants.absolutePath,resp);
    }

    @RequestMapping(value = "/urls",method = RequestMethod.GET)
    public String getUrl(@RequestParam("url") String urls){
        String[] split = urls.split("@@");
        return "redirect:" + split[new Random().nextInt(split.length)];
    }
    //Test: http://localhost:8060/img/urls?url=http://blog.ayaka.icu!!ayaka.love!!996.icu
    //Test: http://localhost:8060/img/urls?url=http://img.ayaka.icu/i/2023/03/25/641e74f1770ad.jpg@@http://img.ayaka.icu/i/2023/03/25/641e80711778b.jpg


}
