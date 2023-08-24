package com.team.gallexiv.api.Comments;

import com.team.gallexiv.model.CommentService;
import com.team.gallexiv.model.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentsController {

    final CommentService commentS;

    public CommentsController(CommentService commentS ) {
        this.commentS = commentS;
    }
}
