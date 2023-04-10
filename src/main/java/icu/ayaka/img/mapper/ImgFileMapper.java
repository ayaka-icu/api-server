package icu.ayaka.img.mapper;

import icu.ayaka.img.entity.ImgFile;
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
public interface ImgFileMapper extends BaseMapper<ImgFile> {

    @Select("SELECT * FROM tb_img_file WHERE scale=1 ORDER BY RAND() LIMIT 1")
    ImgFile getRandom();

    @Select("SELECT * FROM tb_img_file WHERE scale=2 ORDER BY RAND() LIMIT 1")
    ImgFile getRandom2();

}
