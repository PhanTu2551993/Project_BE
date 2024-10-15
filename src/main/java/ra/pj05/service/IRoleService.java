package ra.pj05.service;




import ra.pj05.constants.RoleName;
import ra.pj05.model.entity.Roles;

import java.util.List;

public interface IRoleService {
    List<Roles>
    getAllRoles();
    Roles findByRoleName(RoleName roleName);
}
