package com.example.reviewproject.Post;

import java.time.LocalDateTime;


import com.example.reviewproject.CommonResponseDTO;
import com.example.reviewproject.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class PostResponseDTO extends CommonResponseDTO {
    private Long id;
    private String title;
    private String content;
    private Boolean isCompleted;
    private UserDTO user;
    private LocalDateTime createDate;

    public PostResponseDTO(String msg, Integer statusCode) {
        super(msg, statusCode);
    }

    public PostResponseDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.isCompleted = post.getIsCompleted();
        this.user = new UserDTO(post.getUser());
        this.createDate = post.getCreateDate();
    }
}
