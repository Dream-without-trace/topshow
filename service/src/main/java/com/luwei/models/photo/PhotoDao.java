package com.luwei.models.photo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;

/**
 * @author Leone
 * @since 2018-08-02
 **/
public interface PhotoDao extends JpaRepository<Photo, Integer>, JpaSpecificationExecutor<Photo> {

    Page<Photo> findPhotosByUserIdOrderByCreateTimeAsc(Integer userId, Pageable pageable);

    Page<Photo> findPhotosByUserIdOrderByCreateTimeDesc(Integer userId, Pageable pageable);

    Photo findFirstByUserIdAndCreateTimeBetween(Integer userId, Date start, Date end);

}
