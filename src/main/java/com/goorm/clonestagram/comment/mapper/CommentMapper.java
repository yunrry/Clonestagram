package com.goorm.clonestagram.comment.mapper;

import com.goorm.clonestagram.comment.domain.CommentEntity;
import com.goorm.clonestagram.comment.dto.CommentRequest;
import com.goorm.clonestagram.post.domain.Posts;
import com.goorm.clonestagram.user.domain.Users;

public class CommentMapper {

    public static CommentEntity toEntity(CommentRequest request, Users users, Posts posts) {
        return CommentEntity.builder()
                .users(users)
                .posts(posts)
                .content(request.getContent())
                .build();
    }
}
