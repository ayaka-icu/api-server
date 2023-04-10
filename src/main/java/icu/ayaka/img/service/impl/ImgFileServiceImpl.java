package icu.ayaka.img.service.impl;

import icu.ayaka.img.entity.ImgFile;
import icu.ayaka.img.mapper.ImgFileMapper;
import icu.ayaka.img.service.IImgFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ayaka-icu
 * @since 2023-04-10
 */
@Service
public class ImgFileServiceImpl extends ServiceImpl<ImgFileMapper, ImgFile> implements IImgFileService {

    @Override
    public ImgFile getRandom() {
        return getBaseMapper().getRandom();
    }

    @Override
    public ImgFile getRandom2() {
        return getBaseMapper().getRandom2();
    }
}
