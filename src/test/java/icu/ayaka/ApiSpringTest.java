package icu.ayaka;

import icu.ayaka.constants.RedisConstants;
import icu.ayaka.img.entity.Img;
import icu.ayaka.img.entity.ImgFile;
import icu.ayaka.img.service.IImgFileService;
import icu.ayaka.img.service.IImgService;
import icu.ayaka.img.utils.RedisIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.WriteBuffer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static icu.ayaka.img.utils.RedisIdWorker.BEGIN_TIMESTAMP;

@SpringBootTest(classes = ApiApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
class ApiSpringTest {

    @Autowired
    private IImgService imgService;
    @Autowired
    private IImgFileService imgFileService;
    @Autowired
    private RedisIdWorker redisIdWorker;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void apiTest01() throws IOException {
        List<Img> list = imgService.list();
        String path = "D:\\AppCode\\GitHub\\api-server\\src\\test\\resources\\img\\migrate\\url.txt";
        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        for (Img img : list) {
            String url = img.getUrl();
            bw.write(url);
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    @Test
    public void apiTest02() throws IOException {
        List<ImgFile> list = imgFileService.list();
        String path = "D:\\AppCode\\GitHub\\api-server\\src\\test\\resources\\img\\migrate\\file.txt";
        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        for (ImgFile img : list) {
            String pathFile = img.getPath();
            bw.write(pathFile);
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }


    public ExecutorService es= Executors.newFixedThreadPool(500);

    @Test
    public void apiTest03() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(300);

        //开始时间戳
        long startTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

        Runnable task = () -> {
            //一个任务生成100个id
            for (int i = 0; i < 100; i++) {
                long id = redisIdWorker.nextId(RedisConstants.API_IMG_ID_WORKER);
                System.out.println(id);
            }
            countDownLatch.countDown();
        };

        //300个任务
        for (int i = 0; i < 300; i++) {
            es.submit(task);
        }

        countDownLatch.await();
        long stopTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        System.out.println(stopTimestamp - startTimestamp);
    }

    public long apiTest04() {
        LocalDateTime now = LocalDateTime.now();
        long nowTimestamp = now.toEpochSecond(ZoneOffset.UTC);
        long timestamp = nowTimestamp - BEGIN_TIMESTAMP;
        System.out.println(nowTimestamp+"xxx");
        System.out.println(timestamp+"xxx");
        //获取自增
        //1.获取当前日期
        String format = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        //2.获取自增
        long count = redisTemplate.opsForValue().increment(RedisConstants.API_IMG_ID_WORKER + format);
        return count;
    }

    @Test
    public void apiTest05() {
        for (int i = 0; i <100; i++) {
            System.out.println(apiTest04());
        }
    }

    @Test
    public void logTest(){
        long a = Long.parseLong(null);
        log.info("a = " + a);
    }

}
