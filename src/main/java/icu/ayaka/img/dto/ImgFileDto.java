package icu.ayaka.img.dto;

import icu.ayaka.img.entity.Img;
import icu.ayaka.img.entity.ImgFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImgFileDto {

    private String path;
    private String size;
    private String type;
    private Integer width;
    private Integer height;
    private String info ;

    //返回完整
    public static ImgFileDto imgDto(ImgFile imgFile) {
        ImgFileDto imgDto = new ImgFileDto();
        imgDto.path = imgFile.getPath();
        imgDto.size = imgFile.getSize() + "MB";
        imgDto.type = imgFile.getType();
        imgDto.width = imgFile.getWidth();
        imgDto.height = imgFile.getHeight();
        imgDto.info = "内部人员使用";
        return imgDto;
    }
}
