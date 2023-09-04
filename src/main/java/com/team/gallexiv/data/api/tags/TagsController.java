package com.team.gallexiv.data.api.tags;

import com.team.gallexiv.common.lang.VueData;
import com.team.gallexiv.data.model.Tag;
import com.team.gallexiv.data.model.TagService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagsController {

    final TagService tagS;

    public TagsController(TagService tagS){
        this.tagS=tagS;
    }

    @CrossOrigin
    @GetMapping(path = "/tags", produces = "application/json;charset=UTF-8")
    public VueData getAll(){
        return tagS.getAllTag();
    }
}
