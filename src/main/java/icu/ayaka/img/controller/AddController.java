package icu.ayaka.img.controller;

import icu.ayaka.img.constants.ApiConstants;
import icu.ayaka.img.dto.Auth;
import icu.ayaka.img.entity.Img;
import icu.ayaka.img.entity.ImgFile;
import icu.ayaka.img.service.IImgFileService;
import icu.ayaka.img.service.IImgService;
import icu.ayaka.img.utils.ImgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@RestController
@RequestMapping("/img/add")
public class AddController {

    @Autowired
    private IImgService imgService;

    @Autowired
    private IImgFileService imgFileService;

    @Autowired
    private ApiConstants apiConstants;

    @PostMapping("/file/local")
    public String me(@RequestParam(value = "path",required = false) String pathParam,
                     @RequestBody String auth){
        if (!apiConstants.auth.equals(auth)){
            return "你没有权限!";
        }
        if (ObjectUtils.isEmpty(pathParam)){
            pathParam = apiConstants.absolutePath;
        }
        this.addByFile(pathParam,true);
        return "添加成功";
    }

    @PostMapping("/src/local")
    public String me2(@RequestParam(value = "path",required = false) String pathParam,
                     @RequestBody String auth){
        if (!apiConstants.auth.equals(auth)){
            return "你没有权限!";
        }
        this.getBySrc(pathParam);
        return "添加成功";
    }

    @PostMapping("/file/url")
    public String me3(@RequestParam(value = "path",required = false) String pathParam,
                     @RequestBody String auth){
        if (!apiConstants.auth.equals(auth)){
            return "你没有权限!";
        }
        if (ObjectUtils.isEmpty(pathParam)){
            pathParam = apiConstants.absolutePath;
        }
        this.addByFile(pathParam,false);
        return "添加成功";
    }

    /**
     * 封装
     * @param pathParam 路径
     * @param isLocal 是否为本地
     */
    private void addByFile(String pathParam,boolean isLocal){
        //创建字符缓冲流
        BufferedReader br = null;

        try {
            //赋值路径对象
            br = new BufferedReader(new FileReader(pathParam));
            //单行路径
            String path;
            while ((path = br.readLine()) != null) {
                if (!"".equals(path)) {
                    //根据路径获取图片对象
                    //写入数据库
                    if (isLocal){
                        ImgFile imgFile = ImgUtils.newImgByFile(new File(path));
                        boolean save = imgFileService.save(imgFile);
                        System.out.print("图片URL: " + path);
                        System.out.print("是否添加成功: " + save + "\n");
                    }else {
                        Img img = ImgUtils.newImgByUrl(path);
                        boolean save = imgService.save(img);
                        System.out.print("图片URL: " + path);
                        System.out.print("是否添加成功: " + save + "\n");
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();//释放资源
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void getBySrc(String src){
        File files = new File(src);
        File[] array = files.listFiles();
        for (File file : array) {
            String path = file.getPath();
            System.out.println(path);
            //判断是否为不为目录
            if (file.isFile()) {
                //判断格式是否正确
                String sup = null;
                if (path.lastIndexOf(".") != -1) {
                    sup = path.substring(path.lastIndexOf(".") + 1);
                }// jpg tiff jpeg png gif bmp svg ico swf webp
                if ("jpg".equals(sup) || "tiff".equals(sup) || "jpeg".equals(sup) || "png".equals(sup) ||
                        "gif".equals(sup) || "bmp".equals(sup) || "svg".equals(sup) || "ico".equals(sup) ||
                        "swf".equals(sup) || "webp".equals(sup)) {
                    //执行添加
                    ImgFile imgFile = ImgUtils.newImgByFile(file);
                    imgFileService.save(imgFile);
                }
            }
        }
    }

}
