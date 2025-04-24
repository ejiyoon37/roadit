package com.roadit.roaditbackend.repository;
import com.roadit.roaditbackend.entity.Wishlist;
import com.roadit.roaditbackend.entity.Users;
import com.roadit.roaditbackend.entity.ExplorePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> findByUserAndPost(Users user, ExplorePost post);
    List<Wishlist> findAllByUser(Users user);
    void deleteByUserAndPost(Users user, ExplorePost post);
    boolean existsByUserAndPost(Users user, ExplorePost post);

}


