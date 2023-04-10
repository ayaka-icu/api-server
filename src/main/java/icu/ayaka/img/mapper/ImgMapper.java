package icu.ayaka.img.mapper;

import icu.ayaka.img.entity.Img;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Ayaka-icu
 * @since 2023-04-10
 */
@Mapper
public interface ImgMapper extends BaseMapper<Img> {

    @Select("SELECT * FROM tb_img WHERE scale=1 ORDER BY RAND() LIMIT 1;")
    Img getRandom();

    @Select("SELECT * FROM tb_img WHERE scale=2 ORDER BY RAND() LIMIT 1;")
    Img getRandom2();

}
