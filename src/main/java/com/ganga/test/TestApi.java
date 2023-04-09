package com.ganga.test;

import com.ganga.utils.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/test")
public class TestApi {

    @GetMapping("/getRunFiles")
    public File[] getRunFiles(){
        return FileUtils.getRunFiles("/img");
    }


}
