package com.team.gallexiv.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagDao extends JpaRepository<Tag, Integer> {
    Tag findByTagName(String tagName);

//    public abstract List<Tag> find(JSONObject obj);
//    public long count(JSONObject obj);
}
