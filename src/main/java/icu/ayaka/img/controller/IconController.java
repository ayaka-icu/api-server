package icu.ayaka.img.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IconController {

    @GetMapping("/icons")
    public String getIcons(@RequestParam(value = "i",required = false) String i,
                           @RequestParam(value = "theme",required = false,defaultValue = "1") String theme,
                           @RequestParam(value = "size",required = false,defaultValue = "15") String size){

        theme = "2".equals(theme) ? "light" : "dark";
        //Test:http://localhost:8060/icons?i=idea,nginx,docker,redis,spring,mysql,github,linux,lua,md
        //Test:http://localhost:8060/icons?i=idea,nginx,docker,redis,spring,mysql,github,linux,lua,md&theme=2
        //Test:http://localhost:8060/icons?i=idea,nginx,docker,redis,spring,mysql,github,linux,lua,md&theme=2&size=3
        return "redirect:" + "https://skillicons.dev/icons?i=" + i + "&theme=" + theme + "&perline=" + size; // redirect
    }//&theme=light dark light

    /*@GetMapping("/icons/help")
    @ResponseBody
    public String IconsHelp (HttpServletRequest request){
        String basePath = request.getScheme()+"://" +
                request.getServerName() + ":" + request.getServerPort() +
                request.getContextPath() + "/";
        return "API: "+basePath+"icons?i=idea,nginx,docker,redis,spring,mysql,github,linux,lua,md"+
                "<br>" +
                "--请求参数:";
    }*/
    //Test:http://localhost:8060/icons/help
    //Github:https://github.com/tandpfun/skill-icons#readme

}
