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

    }

    @Test
    public void me04Test(){

    }

    @Test
    public void me05Test(){

    }

}