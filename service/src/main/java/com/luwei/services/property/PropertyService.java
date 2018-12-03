package com.luwei.services.property;

import com.luwei.models.property.Property;
import com.luwei.models.property.PropertyDao;
import com.luwei.services.property.cms.PropertyAddDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Leone
 * @since 2018-08-08
 **/
@Slf4j
@Service
@Transactional
public class PropertyService {

    @Resource
    private PropertyDao propertyDao;

    public void save(PropertyAddDTO dto) {
        Property property = new Property();
        BeanUtils.copyProperties(dto, property);

    }

    public void save(Property property) {
        propertyDao.save(property);
    }

    public Property findOne(Integer specificationId) {
        Property property = propertyDao.findById(specificationId).orElse(null);
        Assert.notNull(property, "属性不能为空");
        return property;
    }

    public List<Property> findAll(Integer specificationId) {
        return propertyDao.findAll();
    }
}
