package cn.rongcapital.mkt.mongodb;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import cn.rongcapital.mkt.po.mongodb.TagTree;

public interface TagTreeRepository extends MongoRepository<TagTree, Long> {

	/**
	 * 方法以findBy开头，后面跟TagTree中的属性名(首字母大写)
	 * 
	 * @param mid
	 * @return
	 */
	public List<TagTree> findByLevelAndStatus(int level, int Status);

	/**
	 * 方法以findBy开头，后面跟TagTree中的属性名(首字母大写)
	 * 
	 * @param mid
	 * @return
	 */
	public List<TagTree> findByTagId(String tagId);

	Page<TagTree> findAll(Pageable pageable);

	/**
	 * 方法以findBy开头，后面跟TagTree中的属性名(首字母大写)
	 * 
	 * @param mid
	 * @return
	 */
	public List<TagTree> findByTagName(String tagName);
	
	/**
	 * 方法以findBy开头，后面跟TagTree中的属性名(首字母大写)
	 * 
	 * @param mid
	 * @return
	 */
	public List<TagTree> findByLevelAndStatusAndTagNameLike(int level, int Status,String tagName);

}