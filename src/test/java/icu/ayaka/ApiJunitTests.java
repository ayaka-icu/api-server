package icu.ayaka;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.function.LongPredicate;

public class ApiJunitTests {

    @Test
    public void me01Test() throws Exception {
        File jpegFile = new File("D:\\desktop\\img\\zip.png");
        Metadata metadata = JpegMetadataReader.readMetadata(jpegFile);
        for(Directory directory : metadata.getDirectories()){
            for(Tag tag : directory.getTags()){
                System.out.print("name : " + tag.getTagName() + "  -->");
                System.out.println("desc : " + tag.getDescription());
            }
        }
    }

    @Test
    public void me02Test() throws Exception{
        File picture = new File("D:\\desktop\\img\\zip.png");
        BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));
        System.out.println(String.format("%.2f",picture.length()/1024.0/1024.0));// 源图大小
        System.out.println(sourceImg.getWidth()); // 源图宽度
        System.out.println(sourceImg.getHeight()); // 源图高度
    }

    @Test
    public void me03Test(){
        String s1 = null;
        String s2 = "123";
        Long aLong = Long.valueOf(s1);
        Long aLong1 = Long.valueOf(s2);
        System.out.println(aLong);
        System.out.println(aLong1);
        Long a = aLong1 + aLong;
        System.out.println(a);
    }

    @Test
    public void me04Test(){
        String s1 = null;
        String s2 = "123";
        System.out.println(Long.parseLong(s1));
        System.out.println(Long.parseLong(s2));
        System.out.println(Long.parseLong(s1) + Long.parseLong(s2));
    }

    @Test
    public void me05Test(){
        System.out.println("ganga".matches("^[1-9]\\d*$"));
        System.out.println("123".matches("^[1-9]\\d*$"));
        System.out.println("0123".matches("^[1-9]\\d*$"));
        System.out.println("-123".matches("^[1-9]\\d*$"));
        System.out.println("321.123".matches("^[1-9]\\d*$"));
        System.out.println("-123.23".matches("^[1-9]\\d*$"));
    }

}
