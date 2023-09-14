package com.team.gallexiv.data.api.Tags;

import com.team.gallexiv.common.lang.VueData;
import com.team.gallexiv.data.model.TagService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagsController {
    final TagService tagS;

    public TagsController(TagService tagS){
        this.tagS = tagS;
    }

    @CrossOrigin
    @GetMapping(path = "/tags", produces = "application/json;charset=UTF-8")
    @Operation(description = "取得全部tag")
    public VueData findAllTag() {
        return tagS.getAllTag();
    }

    @GetMapping
    public VueData findTagById(@PathVariable Integer tagId){
        return tagS.getTagById(tagId);
    }
}
