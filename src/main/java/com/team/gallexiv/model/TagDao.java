package com.team.gallexiv.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface TagDao extends JpaRepository<Tag, Integer> {

Optional<Tag> findByTagName(String name);

}
