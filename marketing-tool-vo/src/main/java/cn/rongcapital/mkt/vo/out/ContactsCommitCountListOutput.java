package cn.rongcapital.mkt.vo.out;

import cn.rongcapital.mkt.vo.BaseOutput;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yunfeng on 2016-8-16.
 */
public class ContactsCommitCountListOutput extends BaseOutput{

    private List<ContactsCommitCountOutput> ContactsCommitCountOutputList = new ArrayList<ContactsCommitCountOutput>();

    public ContactsCommitCountListOutput(int code, String msg, int total) {
        super(code, msg, total, null);
    }

    @JsonProperty("data_list")
    public List<ContactsCommitCountOutput> getContactsCommitCountOutputList() {
        return ContactsCommitCountOutputList;
    }

    public void setContactsCommitCountOutputList(List<ContactsCommitCountOutput> contactsCommitCountOutputList) {
        ContactsCommitCountOutputList = contactsCommitCountOutputList;
    }
}
