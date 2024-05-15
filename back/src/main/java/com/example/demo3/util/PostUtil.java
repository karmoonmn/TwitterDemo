package com.example.demo3.util;

import com.example.demo3.model.Like;
import com.example.demo3.model.Post;
import com.example.demo3.model.User;

public class PostUtil {

    public final static boolean isLikedByReqUser(User reqUser, Post post) {
        if (post.getLikes().size() != 0) {

            for (Like like : post.getLikes()) {
                if (like.getUser().getId().equals(reqUser.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public final static boolean isRepostByReqUser(User reqUser, Post post) {
        for (User user : post.getRepost()) {
            if (user.getId().equals(reqUser.getId())) {
                return true;
            }
        }
        return false;
    }
}
