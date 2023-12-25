package com.example.reviewproject.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {
	private Long postId;
	private String text;
}
