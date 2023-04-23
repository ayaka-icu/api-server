package icu.ayaka.img.utils;

import icu.ayaka.img.cache.ImgCache;
import icu.ayaka.img.cache.ImgFileCache;
import icu.ayaka.img.entity.Img;
import icu.ayaka.img.entity.ImgFile;
import icu.ayaka.img.service.IImgFileService;
import icu.ayaka.img.service.IImgService;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 添加图片
 */
@Component
public class AddImgUtils {

    private final IImgService imgService;
    private final IImgFileService imgFileService;
    private final ImgCache imgCache;
    private final ImgFileCache imgFileCache;
    //依赖注入
    public AddImgUtils(IImgService imgService, IImgFileService imgFileService, ImgCache imgCache, ImgFileCache imgFileCache) {
        this.imgService = imgService;
        this.imgFileService = imgFileService;
        this.imgCache = imgCache;
        this.imgFileCache = imgFileCache;
    }

    /**
     * 封装
     *
     * @param pathParam 路径
     * @param isLocal   是否为本地
     */
    public void runAddByFile(String pathParam, boolean isLocal) {
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
                    if (isLocal) { //本地
                        ImgFile imgFile = ImgUtils.newImgByFile(new File(path));
                        try {
                            //保存到数据库
                            boolean save = imgFileService.save(imgFile);
                            //写入缓存
                            imgFileCache.addNewCache(imgFile.getId().toString(), imgFile.getScale().toString());
                            System.out.print("图片URL: " + path);
                            System.out.print("是否添加成功: " + save + "\n");
                        } catch (Exception e) {
                            System.out.print("图片URL: " + path);
                            System.out.print("是否添加成功: " + false + "\n");
                        }
                    } else { //URL
                        Img img = ImgUtils.newImgByUrl(path);
                        try { //可能会添加失败,这里try为了对下面的继续进行添加
                            //保存到数据库
                            boolean save = imgService.save(img);
                            //写入缓存
                            imgCache.addNewCache(img.getId().toString(), img.getScale().toString());
                            System.out.print("图片URL: " + path);
                            System.out.print("是否添加成功: " + save + "\n");
                        } catch (Exception e) {
                            System.out.print("图片URL: " + path);
                            System.out.print("是否添加成功: " + false + "\n");
                        }
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

    /**
     * <h1>扫描目录下的图片</h1>
     *
     * @param src 文件夹路径
     */
    public void runAddBySrc(String src) {
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
                    try { //可能会添加失败,这里try为了对下面的继续进行添加
                        //添加到数据库
                        boolean save = imgFileService.save(imgFile);
                        //写入缓存
                        imgFileCache.addNewCache(imgFile.getId().toString(), imgFile.getScale().toString());
                        System.out.print("图片URL: " + path);
                        System.out.print("是否添加成功: " + save + "\n");
                    } catch (Exception e) {
                        System.out.print("图片URL: " + path);
                        System.out.print("是否添加成功: " + false + "\n");
                    }
                }
            }
        }
    }
}
