package com.goorm.clonestagram.like.service;

import com.goorm.clonestagram.post.domain.Posts;
import com.goorm.clonestagram.like.domain.Like;
import com.goorm.clonestagram.like.repository.LikeRepository;
import com.goorm.clonestagram.post.service.PostService;
import com.goorm.clonestagram.user.domain.Users;
import com.goorm.clonestagram.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;

    // 좋아요 토글
    @Transactional
    public void toggleLike(Long userId, Long postId) {
        Users user = userService.findByIdAndDeletedIsFalse(userId);
        Posts post = postService.findByIdAndDeletedIsFalse(postId);

        // userId와 postId를 사용해 좋아요 여부 확인
        Optional<Like> existingLike = likeRepository.findByUser_IdAndPost_Id(userId, postId);

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get()); // 좋아요 취소
        } else {
            Like like = new Like();
            like.setUser(user);
            like.setPost(post);
            likeRepository.save(like); // 좋아요 추가
        }
    }

    @Transactional(readOnly = true)
    public Long getLikeCount(Long postId) {
        return likeRepository.countByPost_Id(postId);
    }


    public boolean isPostLikedByLoginUser(Long postId, Long userId) {
        return likeRepository.existsByUserIdAndPost_Id(userId, postId);
    }


}
