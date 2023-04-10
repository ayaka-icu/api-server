package icu.ayaka.img.dto;

import icu.ayaka.img.entity.Img;
import icu.ayaka.img.entity.ImgFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImgDto {

    private String url;
    private String size;
    private String type;
    private Integer width;
    private Integer height;

    //返回完整
    public static ImgDto imgDto(Img img) {
        ImgDto imgDto = new ImgDto();
        imgDto.url = img.getUrl();
        imgDto.size = img.getSize() + "MB";
        imgDto.type = img.getType();
        imgDto.width = img.getWidth();
        imgDto.height = img.getHeight();
        return imgDto;
    }

}
