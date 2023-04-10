package icu.ayaka.img.service;

import icu.ayaka.img.entity.ImgFile;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ayaka-icu
 * @since 2023-04-10
 */
public interface IImgFileService extends IService<ImgFile> {

    ImgFile getRandom();

    ImgFile getRandom2();

}
