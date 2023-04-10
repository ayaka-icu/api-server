package icu.ayaka.img.help;


import icu.ayaka.img.constants.ApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/img/help")
public class ImgHelp {

    @Autowired
    private ApiConstants apiConstants;

    //TODO: 接口完善后再写
   /* @GetMapping("/url/img")
    public String img(){
        return "img-api: <br>" +
                "  - /img/random : 返回随机图片，图片有后台指定目录。<br>" +
                "  - /img/urls : 返回给定url其中一个进行返回。 <br>" +
                "  - TODO: 更多 <br>" +
                "<br><br>" +
                "  # 查看单个api以 /img/urls/help 进行查询用法";

    }*/

/*    @GetMapping("/random/help")
    public String random(){
        return "访问 /random 有后台提供的图片进行数据返回。";
    }

    @GetMapping("/urls/help")
    public String urls(){
        return "多个以@@进行分割<br>" +
                "如: <a href=\"http://localhost:8060/img/urls?url=http://img.ayaka.icu/i/2023/03/25/641e74f1770ad.jpg@@http://img.ayaka.icu/i/2023/03/25/641e80711778b.jpg\">" +
                "http://localhost:8060/img/urls?url=http://img.ayaka.icu/i/2023/03/25/641e74f1770ad.jpg@@http://img.ayaka.icu/i/2023/03/25/641e80711778b.jpg" +
                "</a>";
    }*/



}
