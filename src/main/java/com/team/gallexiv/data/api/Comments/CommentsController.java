package com.team.gallexiv.data.api.Comments;

import com.team.gallexiv.data.model.CommentService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentsController {

    final CommentService commentS;

    public CommentsController(CommentService commentS ) {
        this.commentS = commentS;
    }


}
