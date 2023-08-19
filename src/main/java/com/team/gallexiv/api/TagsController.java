package com.team.gallexiv.api;

import com.team.gallexiv.model.Tag;
import com.team.gallexiv.model.TagService;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin()
@RequestMapping(path="/tags")
public class TagsController {

    final TagService tagService;
    TagsController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public Collection<Tag> getTags() {
        return tagService.getAllTags();
    }

    //分頁式查詢 還不能用
    @PostMapping("/all")
    public String find(@RequestBody String json) throws JSONException {
        JSONObject responseJson = new JSONObject();

//        long count = tagService.count(json);
//        responseJson.put("count", count);

//        List<Tag> tagList = tagService.find(json);
//        JSONArray array = new JSONArray();
//        if(tagList!=null && !tagList.isEmpty()) {
//            for(Tag tag : tagList) {
//                JSONObject item = new JSONObject()
//                        .put("id", tag.getTagId())
//                        .put("name", tag.getTagName());
//                array = array.put(item);
//            }
//        }
//        responseJson.put("list", array);
        return responseJson.toString();
    }

    @DeleteMapping(path = "/{id}")
    public Boolean deleteTag(@PathVariable int id) {
        return tagService.deleteTag(id);
    }

    //TODO 沒有處理好接到的東西
    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public Tag updateTag(@PathVariable int id, @RequestBody Tag tag) {
        System.out.println("接收到"+tag+" 名稱為"+tag.getTagName());
        return tagService.updateTag(id, tag);
    }
}