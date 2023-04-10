package icu.ayaka.img.utils;

import java.io.File;

public class FileUtils {


    public static File[]  getRunFiles(String src) {

        String filePath = System.getProperty("java.class.path");
        String pathSplit = System.getProperty("path.separator");//得到当前操作系统的分隔符，windows下是";",linux下是":"

        if (filePath.contains(pathSplit)) {
            filePath = filePath.substring(0, filePath.indexOf(pathSplit));
        } else if (filePath.endsWith(".jar")) {
            //截取路径中的jar包名,可执行jar包运行的结果里包含".jar"
            filePath = filePath.substring(0, filePath.lastIndexOf(File.separator) + 1);
        }
        //获取当前jar包目录下 的 src目录 的所有文件
        File file = new File(filePath + src);
        return file.listFiles();
    }

}
