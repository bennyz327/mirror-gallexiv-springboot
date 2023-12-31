package com.team.gallexiv.model;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class TagService {

    final TagDao tagDao;

    TagService(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public Collection<Tag> getAllTags() {
        return tagDao.findAll();
    }

    public Page<Tag> getAllpage(Integer p) {
        System.out.println("P是: "+p);
        if (p == null) {
            p = 0;
        }
        Pageable pconf = PageRequest.of(p, 6, Sort.by("tagId"));
        return tagDao.findAll(pconf);
    }

//    public List<Tag> find(String json) {
//        try {
//            JSONObject obj = new JSONObject(json);
//            return tagDao.find(obj);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public long count(String json) {
//        try {
//            JSONObject obj = new JSONObject(json);
//            return tagDao.count(obj);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }

    public boolean deleteTag(int id) {
        if (tagDao.existsById(id)) {
            tagDao.deleteById(id);
            return true;
        }
        return false;
    }

    public Tag updateTag(int tagId, Tag tag) {
        //檢查是否有此tag
        if (tagDao.existsById(tagId)) {
            //檢查是否有重複的tag
            if (tagDao.findByTagName(tag.getTagName()) != null) {
                return null;
            }
            //更新tag
            Tag currentTag = tagDao.findById(tagId).get();
            //直接更新物件
            currentTag.setTagName(tag.getTagName());
            return tagDao.save(currentTag);
        }
        return null;
    }

    public Tag addTag(Tag newTag) {
        if (tagDao.findByTagName((newTag.getTagName())) != null) {
            System.out.println("標籤重複");
            return null;
        }
        return tagDao.save(newTag);
    }

}
