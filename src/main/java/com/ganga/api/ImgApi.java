package com.ganga.api;

import com.ganga.utils.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class ImgApi {

    /**
     * <h1>API:</h1>
     *  获取指定路径下的所有文件。<br>
     *      这个路径可以是jar包路径下的相对路径 <br>
     *      也可以是绝对路径 <br>
     * @param isRelative 是否jar包路径下的相对路径
     * @param src 路径
     * @param resp HttpServletResponse
     * @throws IOException
     */
    public static void imgApiSrc(boolean isRelative,String src, HttpServletResponse resp) throws IOException {

        File[] srcFiles = null;
        if (isRelative){
            srcFiles = FileUtils.getRunFiles(src);
        }else {
            srcFiles = new File(src).listFiles();
        }

        if (srcFiles != null && srcFiles.length > 0){
            //随机 目录 下的一张图片
            File ranFile = srcFiles[new Random().nextInt(srcFiles.length)];
            //生产流对象
            final InputStream in = getImgInputStream(ranFile);
            //写入Response
            resp.setContentType(MediaType.IMAGE_PNG_VALUE);
            IOUtils.copy(in, resp.getOutputStream());
        }
    }

    private static InputStream getImgInputStream(File file) throws IOException {
        return new FileInputStream(file);
    }

}
