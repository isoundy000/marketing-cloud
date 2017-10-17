package cn.rongcapital.mkt.vo.in;

import javax.ws.rs.FormParam;

import cn.rongcapital.mkt.vo.BaseInput;

public class UploadFormIn extends BaseInput {

    private String name;
    private byte[] data;

    @FormParam("name")
    public void setPath(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @FormParam("file")
    public void setContentData(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

}
