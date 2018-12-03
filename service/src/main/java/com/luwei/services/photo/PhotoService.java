package com.luwei.services.photo;

import com.google.common.base.Joiner;
import com.luwei.models.photo.Photo;
import com.luwei.models.photo.PhotoDao;
import com.luwei.models.user.User;
import com.luwei.services.photo.web.PhotoUploadDTO;
import com.luwei.services.photo.web.PhotoWebPageVO;
import com.luwei.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-27
 **/
@Slf4j
@Service
@Transactional
public class PhotoService {

    @Resource
    private PhotoDao photoDao;

    @Resource
    private UserService userService;

    /**
     * xcx端page
     *
     * @param pageable
     * @param userId
     * @return
     */
    public Page<PhotoWebPageVO> page(Pageable pageable, Integer userId) {
        return photoDao.findPhotosByUserIdOrderByCreateTimeDesc(userId, pageable).map(this::toPhotoWebPageVO);
    }

    /**
     * 转换
     *
     * @param photo
     * @return
     */
    private PhotoWebPageVO toPhotoWebPageVO(Photo photo) {
        PhotoWebPageVO vo = new PhotoWebPageVO();
        vo.setUserId(photo.getUserId());
        vo.setCreateTime(photo.getCreateTime());
        vo.setPhotos(Arrays.asList(photo.getPicture().split(",")));
        return vo;
    }


    public void update(Object object) {
    }

    public Object one(Integer id) {
        return null;
    }

    public void delete(Set<Integer> ids) {
    }

    /**
     * 查找一个
     *
     * @param photoId
     * @return
     */
    public Photo findOne(Integer photoId) {
        return photoDao.findById(photoId).orElse(null);
    }

    /**
     * 查找当天上传一个
     *
     * @param userId
     * @return
     */
    public Photo findOneByNowDay(Integer userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        Date start = calendar.getTime();
        System.out.println(start);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        Date end = calendar.getTime();
        System.out.println(end);
        return photoDao.findFirstByUserIdAndCreateTimeBetween(userId, start, end);
    }

    /**
     * 上传图片
     *
     * @param dto
     */
    public void upload(PhotoUploadDTO dto) {
        User user = userService.findOne(dto.getUserId());
        Photo photo = findOneByNowDay(dto.getUserId());
        System.out.println(photo);
        if (photo == null) {
            photo = new Photo();
            String photoList = Joiner.on(",").join(dto.getPhotos());
            photo.setPicture(photoList);
            photo.setUserId(dto.getUserId());
            photo.setRemark("用户上传图片");
            photoDao.save(photo);
        } else {
            String photoArray = Joiner.on(",").join(dto.getPhotos());
            photo.setPicture(photo.getPicture() + "," + photoArray);
            photoDao.save(photo);
        }
    }


}
