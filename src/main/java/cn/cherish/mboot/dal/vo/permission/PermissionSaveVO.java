package cn.cherish.mboot.dal.vo.permission;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

/**
 * Created by Cherish on 2017/2/6.
 */
@Data
public class PermissionSaveVO implements java.io.Serializable {

    private static final long serialVersionUID = 1868612547516878668L;

    @Pattern(regexp="^[A-Za-z]{1,16}$", message="权限名必须是1~16位字母")
    private String permit;

    @Length(min = 0, max = 1024, message = "{user.description}")
    private String description;

}