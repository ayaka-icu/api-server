package icu.ayaka.login.controller;

import com.google.code.kaptcha.Producer;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class CodeController {

    private final Producer producer;
    @Autowired
    public CodeController(Producer producer) {
        this.producer = producer;
    }

    @GetMapping("/login/code.jpg")
    public void verifyCode(HttpServletResponse response, HttpSession session) throws IOException, IOException {
        response.setContentType("image/png");
        String code = producer.createText();
        session.setAttribute("code", code);
        BufferedImage bi = producer.createImage(code);
        ServletOutputStream os = response.getOutputStream();
        ImageIO.write(bi,"jpg",os);
    }

}
