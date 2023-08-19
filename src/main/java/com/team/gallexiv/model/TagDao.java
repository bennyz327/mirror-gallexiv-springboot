package com.team.gallexiv.model;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagDao extends JpaRepository<Tag, Integer>{
    Tag findByTagName(String tagName);

//    public abstract List<Tag> find(JSONObject obj);
//    public long count(JSONObject obj);
}
