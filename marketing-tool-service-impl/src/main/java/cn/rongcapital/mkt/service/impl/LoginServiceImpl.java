package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.dao.RoleDao;
import cn.rongcapital.mkt.dao.RoleResourceMapDao;
import cn.rongcapital.mkt.dao.UserDao;
import cn.rongcapital.mkt.po.User;
import cn.rongcapital.mkt.service.LoginService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.LoginInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yunfeng on 2016-5-25.
 */

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    RoleResourceMapDao roleResourceMapDao;

    @Override
    public Object validateLoginPassword(LoginInput loginInput, SecurityContext securityContext) {
        BaseOutput baseOutput= null;
        User user = constructUser(loginInput);
        if (validatePasswd(user)){
            baseOutput = generateBasicBaseOutput(0,"用户名密码错误",0);
            return Response.ok().entity(baseOutput).build();
        }

        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("user_id",user.getUserId());

        Map<String,Object> map = userDao.getRoleIdAndUserName(paramMap);
        String roleName = roleDao.getRoleName((Long) map.get("role_id"));
        String resourceIds = getResourceIds(map);

        baseOutput = constructBaseOutput((String) map.get("user_name"), roleName, resourceIds);
        return Response.ok().entity(baseOutput).build();
    }

    private String getResourceIds(Map<String, Object> map) {
        List<Long> resourceIds = roleResourceMapDao.selectResourceIds((Long) map.get("role_id"));
        StringBuilder builder = new StringBuilder("");
        for(long resourceId : resourceIds){
            builder.append(resourceId);
            builder.append(",");
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }

    private User constructUser(LoginInput loginInput) {
        User user = new User();
        user.setUserId(loginInput.getUserId());
        user.setPasswd(loginInput.getPassword());
        return user;
    }

    private BaseOutput generateBasicBaseOutput(int code, String msg, int total) {
        BaseOutput baseOutput = new BaseOutput();
        baseOutput.setCode(code);
        baseOutput.setMsg(msg);
        baseOutput.setTotal(total);
        return baseOutput;
    }

    private BaseOutput constructBaseOutput(String userName, String roleName, String resourceIds) {
        BaseOutput baseOutput = generateBasicBaseOutput(0,"success",1);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("user_name",userName);
        map.put("role_name",roleName);
        map.put("resource_ids",resourceIds);
        baseOutput.getData().add(map);
        return baseOutput;
    }

    //在这种Service中日志的打印方式
    private boolean validatePasswd(User user) {
        //查询数据库中是否存在这个User对象,没有返回null,有继续第二步和第三步
        Integer validateValue = userDao.validateLoginInput(user);
        if(validateValue == 0){
            return true;
        }
        return false;
    }
}
