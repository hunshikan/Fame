package com.zbw.fame.service.impl;

import com.zbw.fame.dto.MetaDto;
import com.zbw.fame.exception.TipException;
import com.zbw.fame.mapper.ArticlesMapper;
import com.zbw.fame.mapper.MetasMapper;
import com.zbw.fame.model.Articles;
import com.zbw.fame.model.Metas;
import com.zbw.fame.service.MetasService;
import com.zbw.fame.util.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 属性 Service 实现类
 *
 * @auther zbw
 * @create 2017/8/28 23:33
 */
@Service("metasService")
public class MetasServiceImpl implements MetasService {

    @Autowired
    private MetasMapper metasMapper;

    @Autowired
    private ArticlesMapper articlesMapper;

    @Override
    public List<MetaDto> getMetaDtos(String type) {
        type = verifyType(type);
        return metasMapper.selectMetasDistinct(type);
    }

    @Override
    public boolean deleteMeta(String name, String type) {
        Metas meta = new Metas();
        type = verifyType(type);
        meta.setName(name);
        meta.setType(type);
        List<Metas> metasList = metasMapper.select(meta);
        if (metasList.size() == 0) {
            throw new TipException("没有该名称的属性");
        }
        for (Metas m : metasList) {
            Articles articles = articlesMapper.selectByPrimaryKey(m.getArticleId());
            if (null != articles) {
                if (type.equals(Types.CATEGORY)) {
                    articles.setCategory("");
                }
                if (type.equals(Types.TAG)) {
                    articles.setTags(this.resetMeta(name, articles.getTags()));
                }
                articlesMapper.updateByPrimaryKey(articles);
            }
        }

        return metasMapper.delete(meta) > 0;
    }

    @Override
    public boolean saveMeta(String name, String type) {
        type = verifyType(type);
        Metas metas = new Metas();
        metas.setType(type);
        metas.setName(name);
        if (metasMapper.select(metas).size() > 0) {
            throw new TipException("该属性已经存在");
        }
        return metasMapper.insert(metas) > 0;
    }

    @Override
    public boolean saveOrRemoveMetas(String names, String type, Integer articleId) {
        type = verifyType(type);
        if (null == articleId) {
            throw new TipException("关联文章id不能为空");
        }
        //names为空直接删除该文章下的相关属性
        if (StringUtils.isEmpty(names)) {
            Metas condition = new Metas();
            condition.setType(type);
            condition.setArticleId(articleId);
            metasMapper.delete(condition);
            return true;
        }
        removeMetas(names, type, articleId);
        saveMetas(names, type, articleId);
        return true;
    }

    /**
     * 添加names新加的属性到数据库
     *
     * @param names
     * @param type
     * @param articleId
     */
    private void saveMetas(String names, String type, Integer articleId) {
        String[] nameArr = names.split(",");
        for (String n : nameArr) {
            Metas meta = new Metas();
            meta.setName(n);
            meta.setType(type);
            meta.setArticleId(articleId);
            if (null == metasMapper.selectOne(meta)) {
                metasMapper.insert(meta);
            }
        }
    }

    /**
     * 从数据库中删除names属性中没有的
     *
     * @param names
     * @param type
     * @param articleId
     */
    private void removeMetas(String names, String type, Integer articleId) {
        Metas condition = new Metas();
        condition.setArticleId(articleId);
        condition.setType(type);
        List<Metas> metas = metasMapper.select(condition);
        String[] nameArr = names.split(",");
        Set<String> nameSet = new HashSet<>(Arrays.asList(nameArr));
        for (Metas m : metas) {
            if (!nameSet.contains(m.getName())) {
                metasMapper.delete(m);
            }
        }
    }


    /**
     * 验证Type是否为定义的
     *
     * @return
     */
    private String verifyType(String type) {
        switch (type) {
            case Types.CATEGORY:
                return type;
            case Types.TAG:
                return type;
            default:
                throw new TipException("传输的属性类型不合法");
        }
    }

    /**
     * 从属性字符串中去除一个属性
     *
     * @param name
     * @param metas
     * @return
     */
    private String resetMeta(String name, String metas) {
        String[] metaArr = metas.split(",");
        StringBuffer sb = new StringBuffer();
        for (String m : metaArr) {
            if (!name.equals(m)) {
                sb.append(",").append(m);
            }
        }
        if (sb.length() > 0) {
            return sb.substring(1);
        }
        return "";
    }

}
