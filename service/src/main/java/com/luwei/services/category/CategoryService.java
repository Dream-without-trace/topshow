package com.luwei.services.category;

import com.luwei.models.goods.category.GoodsCategory;
import com.luwei.models.goods.category.GoodsCategoryDao;
import com.luwei.services.category.cms.CategoryAddDTO;
import com.luwei.services.category.cms.CategoryEditDTO;
import com.luwei.services.category.cms.CategoryPageVO;
import com.luwei.services.category.cms.CategorySelectVO;
import com.luwei.services.category.web.CategoryListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Leone
 * @since 2018-08-08
 **/
@Slf4j
@Service
@Transactional
public class CategoryService {

    @Resource
    private GoodsCategoryDao categoryDao;

    @Resource
    private CategoryService categoryService;

    /**
     * 分页
     *
     * @param pageable
     * @param query
     * @return
     */
    public Page<CategoryPageVO> page(Pageable pageable, Object query) {
        Specification<GoodsCategory> specification = (Root<GoodsCategory> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted"), 0));
            Predicate[] predicates = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(predicates));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("categoryId")));
            return criteriaQuery.getRestriction();
        };
        return categoryDao.findAll(specification, pageable).map(this::toCategoryPageVO);
    }

    /**
     * 转换
     *
     * @param category
     * @return
     */
    private CategoryPageVO toCategoryPageVO(GoodsCategory category) {
        CategoryPageVO vo = new CategoryPageVO();
        BeanUtils.copyProperties(category, vo);
        vo.setCreateTime(category.getCreateTime());
        return vo;
    }

    /**
     * 所有分类
     *
     * @return
     */
    public List<GoodsCategory> list() {
        return categoryDao.findAll();
    }


    /**
     * 所有分类
     *
     * @return
     */
    public List<CategoryListVO> webList() {
        return categoryDao.findAll().stream().filter(a -> !a.getDeleted()).map(e -> {
            CategoryListVO vo = new CategoryListVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 添加分类
     *
     * @param dto
     */
    public void save(CategoryAddDTO dto) {
        GoodsCategory category = new GoodsCategory();
        BeanUtils.copyProperties(dto, category);
        category.setDescription("分类详情");
        categoryDao.save(category);
    }

    /**
     * 修改
     *
     * @param dto
     */
    public void update(CategoryEditDTO dto) {
        GoodsCategory category = findOne(dto.getCategoryId());
        BeanUtils.copyProperties(dto, category);
        categoryDao.save(category);
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(Set<Integer> ids) {
        categoryDao.delCategoryByIds(new ArrayList<>(ids));
    }

    /**
     * 查找一个
     *
     * @param categoryId
     * @return
     */
    public GoodsCategory findOne(Integer categoryId) {
        GoodsCategory category = categoryDao.findById(categoryId).orElse(null);
        Assert.notNull(category, "商品分类不存在");
        return category;
    }


    /**
     * 查询商品分类列表
     *
     * @return
     */
    public List<CategorySelectVO> categoryList() {

        List<GoodsCategory> list = categoryService.list();
        return list.stream().filter(a -> !a.getDeleted()).map(e -> {
            CategorySelectVO vo = new CategorySelectVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

    }

    /**
     * 获取所有分类id
     *
     * @return
     */
    public List<Integer> findAllCategoryId() {
        return categoryDao.findAllId();
    }
}
