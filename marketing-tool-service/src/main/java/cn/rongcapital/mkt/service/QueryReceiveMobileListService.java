package cn.rongcapital.mkt.service;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by byf on 12/20/16.
 */
public interface QueryReceiveMobileListService {

    List<String> queryReceiveMobileList(@NotNull Long taskHeadId, @NotNull Long targetId);
}
