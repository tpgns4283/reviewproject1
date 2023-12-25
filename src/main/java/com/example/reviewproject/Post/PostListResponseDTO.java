package com.example.reviewproject.Post;

import java.util.List;

import com.example.reviewproject.user.UserDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class PostListResponseDTO {
    private UserDTO user;
    private List<PostResponseDTO> todoList;

    public PostListResponseDTO(UserDTO user, List<PostResponseDTO> todoList) {
        this.user = user;
        this.todoList = todoList;
    }
}