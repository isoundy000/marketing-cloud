package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.ImgTextAssetDao;
import cn.rongcapital.mkt.service.DeleteImgTextAssetService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * Created by Yunfeng on 2016-5-27.
 */
@Service
public class DeleteImgTextServiceImpl implements DeleteImgTextAssetService {

    @Autowired
    private ImgTextAssetDao imgTextAssetDao;

    @Override
    public Object deleteImgTextService(int imgtextId) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("id",imgtextId);
        int rowEffected = imgTextAssetDao.logicDeleteAssetById(paramMap);

        BaseOutput baseOutput = new BaseOutput();
        baseOutput.setCode(0);
        if(rowEffected != 0){
            baseOutput.setMsg("success");
        }else{
            baseOutput.setMsg("不存在当前输入值的图文资源");
        }
        baseOutput.setTotal(rowEffected);
//        baseOutput.setCount(rowEffected);
        return Response.ok().entity(baseOutput).build();
    }
}
