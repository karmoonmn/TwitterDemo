package com.example.demo3.mapper;

import com.example.demo3.DTO.LikeDto;
import com.example.demo3.DTO.PostDto;
import com.example.demo3.DTO.UserDto;
import com.example.demo3.model.Like;
import com.example.demo3.model.User;

import java.util.ArrayList;
import java.util.List;

public class LikeDtoMapper {

    public static LikeDto toLikeDto(Like like, User reqUser) {
        UserDto user = UserDtoMapper.toUserDto(like.getUser());
        UserDto reqUserDto = UserDtoMapper.toUserDto(reqUser);
        PostDto post = PostDtoMapper.toPostDto(like.getPost(), reqUser);

        LikeDto likeDto = new LikeDto();
        likeDto.setId(like.getId());
        likeDto.setPost(post);

        likeDto.setUser(user);

        return likeDto;
    }

    public static List<LikeDto> toLikeDtos(List<Like> likes, User reqUser) {
        List<LikeDto> likeDtos = new ArrayList<>();

        for (Like like : likes) {
            UserDto user = UserDtoMapper.toUserDto(like.getUser());
            PostDto post = PostDtoMapper.toPostDto(like.getPost(), reqUser);

            LikeDto likeDto = new LikeDto();
            likeDto.setId(like.getId());
            likeDto.setPost(post);

            likeDto.setUser(user);
            likeDtos.add(likeDto);
        }
        return likeDtos;
    }
}
