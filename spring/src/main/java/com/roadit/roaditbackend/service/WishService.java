package com.roadit.roaditbackend.service;

import com.roadit.roaditbackend.entity.ExplorePost;
import com.roadit.roaditbackend.entity.Users;
import com.roadit.roaditbackend.entity.Wishlist;
import com.roadit.roaditbackend.repository.ExplorePostRepository;
import com.roadit.roaditbackend.repository.UserRepository;
import com.roadit.roaditbackend.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class WishService {

    private final UserRepository userRepository;
    private final ExplorePostRepository explorePostRepository;
    private final WishlistRepository wishlistRepository;


    @Transactional
    public boolean toggleWish(Long userId, Long postId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));
        ExplorePost post = explorePostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        Optional<Wishlist> wish = wishlistRepository.findByUserAndPost(user, post);

        if (wish.isPresent()) {
            wishlistRepository.delete(wish.get());
            return false;
        } else {
            Wishlist newWish = new Wishlist();
            newWish.setUser(user);
            newWish.setPost(post);
            wishlistRepository.save(newWish);
            return true;
        }
    }


    public List<ExplorePost> getWishedPostsByUser(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        List<Wishlist> wishes = wishlistRepository.findAllByUser(user);

        return wishes.stream()
                .map(Wishlist::getPost)
                .collect(Collectors.toList());
    }

}
