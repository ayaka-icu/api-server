package icu.ayaka.img;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestJunit {

    @Test
    void test() throws Exception {
        File jpegFile = new File("D:\\desktop\\img\\zip.png");
        Metadata metadata = JpegMetadataReader.readMetadata(jpegFile);
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.print("name : " + tag.getTagName() + "  -->");
                System.out.println("desc : " + tag.getDescription());
            }
        }
    }

    @Test
    void test2() throws Exception {
        File picture = new File("D:\\desktop\\img\\zip.png");
        BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));
        System.out.println(String.format("%.2f", picture.length() / 1024.0 / 1024.0));// 源图大小
        System.out.println(sourceImg.getWidth()); // 源图宽度
        System.out.println(sourceImg.getHeight()); // 源图高度
    }

    @Test
    void test3() {
        String max = ("8");
        String num = "12345";
        String[] arr = byArray(max, num);

    }

    public String[] byArray(String max, String num) {

        if (!num.matches("^[1-9]\\d*$") || !max.matches("^[1-9]\\d*$")) {
            System.out.println("参数错误");
        }

        int m = Integer.parseInt(max);
        int n = num.length();

        char[] charArray = num.toCharArray();

        if (m >= n) {
            int zone = m - n;
            String[] arr = new String[m];
            for (int i = 0; i < arr.length; i++) {
                if (i < zone) {
                    arr[i] = "0.gif";
                } else {
                    arr[i] = charArray[i - zone] + ".gif";
                }
            }
            return arr;
        } else {
            String[] arr = new String[n];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = charArray[i] + ".gif";
            }
            return arr;
        }
    }

    @Test
    public void me05Test() throws Exception{
        String username = "ayaka-icu";
        String accessToken = "your_access_token";
        String apiUrl = "https://api.github.com/users/" + username;
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "token " + accessToken);
        conn.setRequestProperty("Accept", "application/vnd.github.v3+json");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        conn.disconnect();
        System.out.println(response.toString());
    }

}
