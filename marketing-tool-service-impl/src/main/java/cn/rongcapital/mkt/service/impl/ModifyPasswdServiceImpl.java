package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.dao.UserDao;
import cn.rongcapital.mkt.po.User;
import cn.rongcapital.mkt.service.ModifyPasswdService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.ModifyInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by Yunfeng on 2016-5-26.
 */
@Service
public class ModifyPasswdServiceImpl implements ModifyPasswdService{


    @Autowired
    UserDao userDao;

    @Override
    public Object modifyPasswd(ModifyInput input, SecurityContext securityContext) {
        BaseOutput baseOutput = null;
        User user = constructUser(input);
        if(userDao.validateLoginInput(user) == 0){
            baseOutput = generateBasicBaseOutput(0,"旧的用户名密码输入错误",0);
            return Response.ok().entity(baseOutput).build();
        }

        user.setPasswd(input.getNewPasswd());
        userDao.updatePasswd(user);
        baseOutput = generateBasicBaseOutput(0,"密码修改成功",0);
        return Response.ok().entity(baseOutput).build();
    }

    private User constructUser(ModifyInput input) {
        User user = new User();
        user.setUserId(input.getUser_id());
        user.setPasswd(input.getOldPasswd());
        return user;
    }

    private BaseOutput generateBasicBaseOutput(int code, String msg, int total) {
        BaseOutput baseOutput = new BaseOutput();
        baseOutput.setCode(code);
        baseOutput.setMsg(msg);
        baseOutput.setTotal(total);
        return baseOutput;
    }
}
