package icu.ayaka.img.controller.manage;

import icu.ayaka.constants.ApiConstants;
import icu.ayaka.img.utils.AddImgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/img/add")
public class AddController {

    //依赖注入:
    @Autowired
    private ApiConstants apiConstants;

    @Autowired
    private AddImgUtils addImgUtils;

    /**
     * <h1>添加本地图片 方式一                       </h1><br>
     *
     * <h2>扫描 [指定文件路径] 下所有图片添加到 [本地图片]             </h2>
     * <h2>会对指定文件路径下的文件进行图片判断               </h2>
     *
     * @param pathParam 文件路径
     * @param auth 认证
     * @return 结果
     */
    @PostMapping("/src/local")
    public String addLocalBySrc(@RequestParam(value = "path",required = false) String pathParam,
                                @RequestBody String auth){
        if (!apiConstants.auth.equals(auth)){
            return "你没有权限!";
        }
        addImgUtils.runAddBySrc(pathParam);
        return "添加成功";
    }


    /**
     * <h1>添加本地图片 方式二                      </h1><br>
     *
     * <h2>根据 [指定文件] 添加到 [本地图片]
     * <h2>这个文件里的每行数据对应 服务器图片路径
     * <h2> 见 \test\resources\img\test-local.txt 文件
     *
     * @param pathParam 文件路径
     * @param auth 认证
     * @return 结果
     */
    @PostMapping("/file/local")
    public String addLocalByFile(@RequestParam(value = "path",required = false) String pathParam,
                     @RequestBody String auth){
        if (!apiConstants.auth.equals(auth)){
            return "你没有权限!";
        }
        if (ObjectUtils.isEmpty(pathParam)){
            pathParam = apiConstants.absolutePath;
        }
        addImgUtils.runAddByFile(pathParam,true);
        return "添加成功";
    }


    /**
     * <h1>添加外部 URL 图片                             </h1><br>
     *
     * <h2>扫描指定文件夹下图片 添加到 [本地图片]            </h2><br>
     *
     * <h2>这个文件里的每行数据对应 图片url路径             </h2><br>
     * <h2>见 \test\resources\img\test-url.txt 文件    </h2><br>
     * <h2>和 \test\resources\img\test-url2.txt 文件   </h2><br>
     *
     * @param pathParam 文件路径
     * @param auth 认证
     * @return 结果
     */
    @PostMapping("/file/url")
    public String addUrlByFile(@RequestParam(value = "path",required = false) String pathParam,
                     @RequestBody String auth){
        if (!apiConstants.auth.equals(auth)){
            return "你没有权限!";
        }
        if (ObjectUtils.isEmpty(pathParam)){
            pathParam = apiConstants.absolutePath;
        }
        addImgUtils.runAddByFile(pathParam,false);
        return "添加成功";
    }

}
