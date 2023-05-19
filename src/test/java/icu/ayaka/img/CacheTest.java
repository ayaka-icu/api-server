package icu.ayaka.img;

import icu.ayaka.ApiApplication;
import icu.ayaka.img.cache.AllCache;
import icu.ayaka.img.cache.ImgCache;
import icu.ayaka.img.cache.ImgFileCache;
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

import java.io.File;

import static icu.ayaka.constants.ImgConstants.SCALE_ONE;
import static icu.ayaka.constants.ImgConstants.SCALE_TWO;

@SpringBootTest(classes = ApiApplication.class)
@RunWith(SpringRunner.class)
public class CacheTest {

    @Autowired
    ImgCache imgCache;

    @Autowired
    ImgFileCache imgFileCache;

    @Autowired
    AllCache allCache;

    @Autowired
    IImgService imgService;
    @Autowired
    IImgFileService imgFileService;
    @Autowired
    ImgUtils imgUtils;

    @Test
    public void addAllUrlTest(){
        Long aLong = imgCache.addAll();
        System.out.println("总条数:" + aLong);
    }

    @Test
    public void addAllFileTest(){
        Long aLong = imgFileCache.addAll();
        System.out.println("总条数:" + aLong);
    }

    @Test
    public void getUrlRandomTest(){
        for (int i = 0; i < 5; i++) {
            String cacheID = imgCache.getRandomID(SCALE_TWO);
            System.out.println("cacheID: " + cacheID);
            Img img = imgService.getById(cacheID);
            System.out.println(img + "\n\n\n");
        }
    }

    @Test
    public void getFileRandomTest(){
        for (int i = 0; i < 5; i++) {
            String cacheID = imgFileCache.getRandomID(SCALE_ONE);
            System.out.println("cacheID: " + cacheID);
            ImgFile byId = imgFileService.getById(cacheID);
            System.out.println(byId + "\n\n\n");
        }
    }
    @Test
    public void getTest(){
        ImgFile imgFile = imgUtils.newImgByFile(new File("D:\\desktop\\XXX\\XXX.jpg"));
        imgFileService.save(imgFile);
        System.out.println(imgFile.getId());
    }

    /**
     * 所有 图片库 缓存
     */
    @Test
    public void allTest(){
        allCache.addAllCache();
    }

}
