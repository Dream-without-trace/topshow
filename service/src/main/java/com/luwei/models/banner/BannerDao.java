package com.luwei.models.banner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Leone
 * @since 2018-08-02
 **/
public interface BannerDao extends JpaRepository<Banner, Integer>, JpaSpecificationExecutor<Banner> {



}
