package icu.ayaka.img;

import icu.ayaka.ApiApplication;
import icu.ayaka.constants.ApiConstants;
import icu.ayaka.img.controller.manage.AddController;
import icu.ayaka.img.entity.Img;
import icu.ayaka.img.entity.ImgFile;
import icu.ayaka.img.service.IImgFileService;
import icu.ayaka.img.service.IImgService;
import icu.ayaka.img.utils.ImgUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

@SpringBootTest(classes = ApiApplication.class)
@RunWith(SpringRunner.class)
public class AddImgTest {

    @Autowired
    private IImgService imgService;
    @Autowired
    private IImgFileService imgFileService;
    @Autowired
    private AddController addController;
    @Autowired
    private ApiConstants apiConstants;


    @Test
    public void addLocalBySrcTest(){
        //要是api-server服务器上的目录
        //非服务段无法测试
        String src = "";
        String s = addController.addLocalBySrc(src, apiConstants.auth);
        System.out.println(s);
    }

    @Test
    public void addLocalByFileTest(){
        //非服务段无法测试
        String file = "D:\\AppCode\\GitHub\\api-server\\src\\test\\resources\\img\\test-local.txt";
        String s = addController.addLocalByFile(file, apiConstants.auth);
        System.out.println(s);
    }


    @Test
    public void addUrlTest(){
        //支持本地测试上次
        //String file = "D:\\AppCode\\GitHub\\api-server\\src\\test\\resources\\img\\test-url1.txt";
        String file = "D:\\AppCode\\GitHub\\api-server\\src\\test\\resources\\img\\test-url2.txt";
        String s = addController.addUrlByFile(file, apiConstants.auth);
        System.out.println(s);
    }























    //===============================================================================================================




    //private final String PATH_URL = "D:\\AppCode\\GitHub\\api-server\\api-img\\src\\main\\resources\\img\\test-url2.txt";
    private final String PATH_URL = "D:\\AppCode\\GitHub\\api-server\\src\\test\\resources\\img\\test-url1.txt";

    @Test
    public void addTextUrl() {
        //创建字符缓冲流
        BufferedReader br = null;

        try {
            //赋值路径对象
            br = new BufferedReader(new FileReader(PATH_URL));
            //单行路径
            String path;
            while ((path = br.readLine()) != null) {
                if (!"".equals(path)) {
                    //根据路径获取图片对象
                    Img img = ImgUtils.newImgByUrl(path);
                    //写入数据库
                    boolean save = imgService.save(img);
                    System.out.print("图片URL: " + path);
                    System.out.print("是否添加成功: " + save + "\n");
                    //单行路径生成写入
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

    private final String PATH_FILE = "D:\\desktop\\Test";

    @Test
    public void addTextFile() {
        File files = new File(PATH_FILE);
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
                if ("jpg".equals(sup) || "tiff".equals(sup) || "jpeg".equals(sup) || "png".equals(sup)  ||
                        "gif".equals(sup) || "bmp".equals(sup) || "svg".equals(sup) || "ico".equals(sup) ||
                        "swf".equals(sup) || "webp".equals(sup))
                {
                    //执行添加
                    ImgFile imgFile = ImgUtils.newImgByFile(file);
                    imgFileService.save(imgFile);
                }
            }
        }
    }


}
