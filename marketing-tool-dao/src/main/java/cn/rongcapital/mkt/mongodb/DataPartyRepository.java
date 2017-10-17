package cn.rongcapital.mkt.mongodb;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import cn.rongcapital.mkt.po.mongodb.DataParty;

public interface  DataPartyRepository extends MongoRepository<DataParty,Long> {

	/**
	 * 方法以findBy开头，后面跟DataParty中的属性名(首字母大写)
	 * @param mid
	 * @return
	 */
	public List<DataParty> findByMid(String mid);
	
	Page<DataParty> findAll(Pageable pageable);

}
