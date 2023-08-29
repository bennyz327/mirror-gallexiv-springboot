package com.team.gallexiv.model;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    final TagDao tagD;

    public TagService(TagDao tagD){
        this.tagD=tagD;
    }

    // 取得單筆tag
    public Tag getTagById(int tagId){
        Optional<Tag> tag = tagD.findById(tagId);
        return tag.orElse(null);
    }

    //取得全部tag
    public List<Tag> getAllTag(){
        return  tagD.findAll();
    }


    //刪除tag
//    public void deleteTagById(int tagId){
//        Optional<Tag> tagOptional = tagD.findById(tagId);
//        if(tagOptional.isEmpty()){
//            return;
//        }
//        tagD.deleteById(tagId);
//    }

    //新增tag
    public Tag insertTag(Tag tag){
        Optional<Tag> optionalTag = tagD.findByTagName(tag.getTagName());
        if (optionalTag.isEmpty()) {
           return tagD.save(tag);
        }
        return null;
    }

    //更新tag
    public void updateTagById(int tagId, String tagName){
        Optional<Tag> optional = tagD.findById(tagId);

        if(optional.isEmpty()){
            return;
        }
        Tag result = optional.get();
        result.setTagName(tagName);

    }

}
