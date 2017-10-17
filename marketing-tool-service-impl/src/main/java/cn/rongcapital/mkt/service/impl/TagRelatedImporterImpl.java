package cn.rongcapital.mkt.service.impl;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.dao.TagDao;
import cn.rongcapital.mkt.dao.TagGroupMapDao;
import cn.rongcapital.mkt.dao.TagRecommendDao;
import cn.rongcapital.mkt.dao.TaggroupDao;
import cn.rongcapital.mkt.po.Tag;
import cn.rongcapital.mkt.po.TagGroupMap;
import cn.rongcapital.mkt.po.TagRecommend;
import cn.rongcapital.mkt.po.Taggroup;
import cn.rongcapital.mkt.service.TagRelatedImporter;

@Service
public class TagRelatedImporterImpl implements TagRelatedImporter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TagRecommendDao tagRecommendDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private TagGroupMapDao tagGroupMapDao;

    @Autowired
    private TaggroupDao taggroupDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void importData() throws IOException {
        File file = new File("/Users/nianjun/Desktop/导入数据.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        while (line != null) {
            // 以#开头的行略过, 文件为空略过, 
            if (StringUtils.isEmpty(line) || !line.contains(",") || line.trim().startsWith("#")) {
                line = bufferedReader.readLine();
                continue;
            }
            String[] tags = line.split(",");

            String parent = tags[0].trim();
            String self = parent + "-" + tags[1].trim();
            List<String> childList = Arrays.asList(tags[2].trim().split("/"));
            int taggroupId = 0;
            for (int i = 0; i < childList.size(); i++) {
                logger.info(parent + "," + self + "," + childList.get(i));
                Taggroup parentTag = new Taggroup();
                TagGroupMap tagGroupMap = new TagGroupMap();
                tagGroupMap.setGroupId(taggroupId);
                tagGroupMap.setCreateTime(new Date());
                tagGroupMap.setUpdateTime(new Date());
                parentTag.setName(parent);
                List<Taggroup> parentTagList = taggroupDao.selectList(parentTag);
                if (CollectionUtils.isEmpty(parentTagList)) {
                    parentTag.setCreateTime(new Date());
                    parentTag.setLevel(0);
                    parentTag.setParentGroupId(-1L);
                    parentTag.setStatus((byte) 0);
                    taggroupDao.insert(parentTag);
                } else {
                    parentTag = parentTagList.get(0);
                }

                Taggroup selfTag = new Taggroup();
                selfTag.setName(self);

                List<Taggroup> selftTagList = taggroupDao.selectList(selfTag);
                if (CollectionUtils.isEmpty(selftTagList)) {
                    selfTag.setCreateTime(new Date());
                    selfTag.setLevel(1);
                    selfTag.setParentGroupId(Long.parseLong(parentTag.getId() + ""));
                    selfTag.setStatus((byte) 0);
                    taggroupDao.insert(selfTag);
                    taggroupId = selfTag.getId();
                    tagGroupMap.setGroupId(taggroupId);
                    TagRecommend tagRecommend = new TagRecommend();
                    tagRecommend.setTagGroupId(taggroupId);
                    tagRecommend.setTagGroupName(tags[1].trim());
                    tagRecommend.setStatus((byte) 0);
                    tagRecommend.setCreateTime(new Date());
                    tagRecommend.setUpdateTime(new Date());
                    tagRecommendDao.insert(tagRecommend);
                } else {
                    selfTag = selftTagList.get(0);
                }

                Taggroup childTag = new Taggroup();
                childTag.setName(childList.get(i));
                childTag.setCreateTime(new Date());
                childTag.setLevel(2);
                childTag.setParentGroupId(Long.parseLong(selfTag.getId() + ""));
                childTag.setStatus((byte) 0);
                taggroupDao.insert(childTag);
                
                Tag tag = new Tag();
                tag.setCreateTime(new Date());
                tag.setUpdateTime(new Date());
                tag.setName(childTag.getName());
                tag.setStatus((byte) 0);
                tagDao.insert(tag);
                
                tagGroupMap.setTagId(tag.getId());
                tagGroupMapDao.insert(tagGroupMap);
                logger.info(tagGroupMap.getGroupId() + " : " + tag.getName() + " : " + tag.getId());
            }

            line = bufferedReader.readLine();
        }
    }

}
