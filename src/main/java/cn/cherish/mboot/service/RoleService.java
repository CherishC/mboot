package cn.cherish.mboot.service;

import cn.cherish.mboot.dal.dto.RoleDTO;
import cn.cherish.mboot.dal.entity.Role;
import cn.cherish.mboot.dal.vo.BasicSearchVO;
import cn.cherish.mboot.dal.vo.role.RoleSaveVO;
import cn.cherish.mboot.dal.vo.role.RoleUpdateVO;
import cn.cherish.mboot.repository.IBaseDAO;
import cn.cherish.mboot.repository.RoleDAO;
import cn.cherish.mboot.util.ObjectConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "roles")
public class RoleService extends ABaseService<Role, Long> {

    @Autowired
    private RoleDAO roleDAO;

    @Override
    protected IBaseDAO<Role, Long> getEntityDAO() {
        return roleDAO;
    }

    public Page<RoleDTO> findAll(BasicSearchVO basicSearchVO) {

        int pageNumber = basicSearchVO.getStartIndex() / basicSearchVO.getPageSize() + 1;
        Page<Role> rolePage = this.findAll(pageNumber, basicSearchVO.getPageSize());

        return rolePage.map(source -> {
            RoleDTO roleDTO = new RoleDTO();
            ObjectConvertUtil.objectCopy(roleDTO, source);
            return roleDTO;
        });
    }

    @Transactional
    public void updateByVO(RoleUpdateVO roleUpdateVO) {
        Role role = this.findById(roleUpdateVO.getId());
        ObjectConvertUtil.objectCopy(role, roleUpdateVO);
        this.update(role);
    }

    public boolean exist(String name) {
        return roleDAO.findByName(name) != null;
    }

    @Transactional
    public void saveByVO(RoleSaveVO roleSaveVO) {
        Role role = new Role();
        ObjectConvertUtil.objectCopy(role, roleSaveVO);
        this.save(role);
    }

    public List<Role> listByUserId(Long userId){
        return roleDAO.findByUserId(userId);
    }

    /*不建议如此强硬，该手动去除关联再删除
    @Transactional(readOnly = false)
    public void delete(Long roleId){
        //先删除t_user_role表的外键关联
        customizedDAO.deleteUserRoleRelation(roleId);
        //再删除role
        roleDAO.delete(roleId);
    }*/

}
