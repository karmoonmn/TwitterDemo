package com.example.demo3.mapper;

import com.example.demo3.DTO.CommentDto;
import com.example.demo3.DTO.PostDto;
import com.example.demo3.DTO.UserDto;
import com.example.demo3.model.Comment;
import com.example.demo3.model.Post;
import com.example.demo3.model.User;
import com.example.demo3.util.PostUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostDtoMapper {

    public static PostDto toPostDto(Post post, User reqUser) {
        UserDto user = UserDtoMapper.toUserDto(reqUser);

        boolean isLiked = PostUtil.isLikedByReqUser(reqUser, post);
        boolean isRepost = PostUtil.isRepostByReqUser(reqUser, post);

        List<String> repostUserId = new ArrayList<>();

        for (User user1 : post.getRepost()) {
            repostUserId.add(user1.getId());
        }

        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setContent(post.getContent());
        postDto.setImage(post.getImage());
        postDto.setTotalLikes(post.getLikes().size());
        postDto.setTotalReplies(post.getReplies().size());
        postDto.setTotalReposts(post.getRepost().size());
        postDto.setUser(user);
        postDto.setLiked(isLiked);
        postDto.setRepost(isRepost);
        postDto.setRepostUsersId(repostUserId);
        postDto.setReplyPosts(toPostDtos(post.getReplies(), reqUser));
        postDto.setVideo(post.getVideo());
        postDto.setTags(post.getTags());
        postDto.setCommentCount(post.getCommentCount());

        return postDto;
    }

    public static List<PostDto> toPostDtos(List<Post> posts, User reqUser) {
        List<PostDto> postDtos = new ArrayList<>();

        for (Post post : posts) {
            PostDto postDto = toReplyPostDto(post, reqUser);
            postDtos.add(postDto);
        }
        return postDtos;
    }

    private static PostDto toReplyPostDto(Post post, User reqUser) {
        UserDto user = UserDtoMapper.toUserDto(post.getUser());

        boolean isLiked = PostUtil.isLikedByReqUser(reqUser, post);
        boolean isRepost = PostUtil.isRepostByReqUser(reqUser, post);

        List<String> repostUserId = new ArrayList<>();

        for (User user1 : post.getRepost()) {
            repostUserId.add(user1.getId());
        }

        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setContent(post.getContent());
        postDto.setImage(post.getImage());
        postDto.setTotalLikes(post.getLikes().size());
        postDto.setTotalReplies(post.getReplies().size());
        postDto.setTotalReposts(post.getRepost().size());
        postDto.setUser(user);
        postDto.setLiked(isLiked);
        postDto.setRepost(isRepost);
        postDto.setRepostUsersId(repostUserId);
        postDto.setVideo(post.getVideo());
        postDto.setTags(post.getTags());

        return postDto;
    }

}
