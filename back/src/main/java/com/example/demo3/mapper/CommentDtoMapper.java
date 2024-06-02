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

public class CommentDtoMapper {

    public static CommentDto toCommentDto(Comment comment, User reqUser) {
        UserDto user = UserDtoMapper.toUserDto(comment.getUser());
        PostDto post = PostDtoMapper.toPostDto(comment.getPost(), reqUser);
        CommentDto dto = new CommentDto();

        dto.setId(comment.getId());
        dto.setCommentText(comment.getCommentText());
        dto.setUser(user.getId());
        dto.setParentCommentId(comment.getParentCommentId());
        dto.setPost(post.getId());
        dto.setVisible(comment.isVisible());
        return dto;
    }



    public static List<CommentDto> toCommentDtos(List<Comment> comments, User loggedInUser) {
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto dto = toCommentDto(comment, loggedInUser);
            commentDtos.add(dto);
        }
        return commentDtos;
    }
}
