package icu.ayaka.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebConfig {

    @RequestMapping("/")
    @ResponseBody
    public String index(){
        return "<br><h1>API详细使用页面还没写</h1><br>" +
                "<h1>API详细使用请先访问此处: <a href=\"https://github.com/ayaka-icu/api-server\">https://github.com/ayaka-icu/api-server</a></h1>";
    }

}
