package com.luwei.services.manager;

import com.luwei.common.enums.type.ManagerType;
import com.luwei.common.enums.type.RoleType;
import com.luwei.common.utils.BcryptUtil;
import com.luwei.models.manager.Manager;
import com.luwei.models.manager.ManagerDao;
import com.luwei.models.user.UserDao;
import com.luwei.services.manager.cms.ManagerAddDTO;
import com.luwei.services.manager.cms.ManagerEditDTO;
import com.luwei.services.manager.cms.ManagerPageVO;
import com.luwei.services.manager.cms.ManagerQueryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Leone
 * @since 2018-07-31
 **/
@Slf4j
@Service
@Transactional
public class ManagerService {

    @Resource
    private UserDao userDao;

    @Resource
    private ManagerDao managerDao;

    /**
     * 分页
     *
     * @param pageable
     * @return
     */
    public Page<ManagerPageVO> page(ManagerQueryDTO query, Pageable pageable) {
        Specification<Manager> specification = (Root<Manager> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));

            if (null != query.getUsername()) {
                list.add(criteriaBuilder.like(root.get("username").as(String.class), "%" + query.getUsername() + "%"));
            }

            if (null != query.getAccount()) {
                list.add(criteriaBuilder.equal(root.get("account").as(String.class), "%" + query.getAccount() + "%"));
            }

            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("managerId")));
            return criteriaQuery.getRestriction();
        };
        return managerDao.findAll(specification, pageable).map(this::toManagerPageVO);
    }

    /**
     * 转换
     *
     * @param e
     * @return
     */
    private ManagerPageVO toManagerPageVO(Manager e) {
        ManagerPageVO vo = new ManagerPageVO();
        BeanUtils.copyProperties(e, vo);
        return vo;
    }

    /**
     * 所有管理员列表
     *
     * @param query
     * @return List<ManagerPageVO>
     */
    public List<ManagerPageVO> list(ManagerQueryDTO query) {
        Specification<Manager> specification = (Root<Manager> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));
            if (null != query.getUsername()) {
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + query.getUsername() + "%"));
            }
            if (null != query.getAccount()) {
                list.add(criteriaBuilder.equal(root.get("account").as(String.class), "%" + query.getAccount() + "%"));
            }
            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("managerId")));
            return criteriaQuery.getRestriction();
        };
        return managerDao.findAll(specification).stream().map(this::toManagerPageVO).collect(Collectors.toList());
    }

    /**
     * 添加管理员
     *
     * @param dto
     */
    public void save(ManagerAddDTO dto) {
        Manager manager = new Manager();
        BeanUtils.copyProperties(dto, manager);
        manager.setPassword(BcryptUtil.encode(dto.getPassword()));
        manager.setDisabled(false);
        manager.setRole(dto.getRoleType());
        manager.setDeleted(false);
        manager.setManagerType(dto.getRoleType().equals(RoleType.STORE) ? ManagerType.STORE : ManagerType.PLATFORM);
        if (RoleType.ADMIN.equals(dto.getRoleType()) || RoleType.ROOT.equals(dto.getRoleType())) {
            manager.setStoreId(0);
        }
        manager.setPhone("");
        managerDao.save(manager);
    }

    /**
     * 修改管理员
     *
     * @param dto
     * @return ManagerPageVO
     */
    public ManagerPageVO update(ManagerEditDTO dto) {
        Manager manager = managerDao.findById(dto.getManagerId()).orElse(null);
        Assert.notNull(manager, "管理员不存在");
        BeanUtils.copyProperties(dto, manager);
        manager.setRole(dto.getRoleType());
        manager.setPassword(BcryptUtil.encode(dto.getPassword()));
        Manager entity = managerDao.save(manager);
        ManagerPageVO vo = new ManagerPageVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    /**
     * 禁用启用管理员
     *
     * @param managerId
     */
    public ManagerPageVO update(Integer managerId) {
        Manager manager = managerDao.findById(managerId).orElse(null);
        Assert.notNull(manager, "管理员不存在");
        if (manager.getDisabled()) {
            manager.setDisabled(false);
        } else {
            manager.setDisabled(true);
        }
        managerDao.save(manager);
        ManagerPageVO vo = new ManagerPageVO();
        BeanUtils.copyProperties(manager, vo);
        return vo;
    }

    /**
     * 查找指定管理员
     *
     * @param managerId
     * @return
     */
    public Manager findOne(Integer managerId) {
        Manager manager = managerDao.findById(managerId).get();
        Assert.notNull(manager, "管理员不存在");
        return manager;
    }

    /**
     * 删除管理员
     *
     * @param ids
     */
    public void delete(Set<Integer> ids) {
        /*Integer result = managerDao.updateDeleted(ids, true);
        log.info("result:{}", result);*/
        managerDao.deleteManagersByManagerIdIn(new ArrayList<>(ids));
    }

    /**
     * 修改密码
     *
     * @param dto
     * @return
     */
    public ManagerPageVO resetPassword(ManagerEditDTO dto) {
        Manager manager = findOne(dto.getManagerId());
        manager.setPassword(dto.getPassword());
        Manager entity = managerDao.save(manager);
        ManagerPageVO vo = new ManagerPageVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }


}
