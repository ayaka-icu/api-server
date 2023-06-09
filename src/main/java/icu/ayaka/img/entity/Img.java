package icu.ayaka.img.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Ayaka-icu
 * @since 2023-04-10
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("tb_img")
@ToString
public class Img implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 图片路径
     */
    private String url;

    /**
     * 图片大小: 单位MB 最大  double(5,2)
     */
    private Double size;

    /**
     * 图片名称
     */
    private String name;

    /**
     * 图片类型 jpg/png/...
     */
    private String type;

    /**
     * 图片比例 1:横屏 2:竖屏 3:类似正方形
     */
    private Integer scale;

    /**
     * 图片宽度
     */
    private Integer width;

    /**
     * 图片长度
     */
    private Integer height;

    /**
     * 图片JSON格式，key：url+size+width+height+type
     */
    private String json;

    /**
     * 图片更新时间
     */
    private LocalDateTime time;


}
