package com.example.reviewproject.comment;

import java.util.concurrent.RejectedExecutionException;

import com.example.reviewproject.CommonResponseDTO;
import com.example.reviewproject.user.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;

@RequestMapping("/api/comments")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDTO> postComment(@RequestBody CommentRequestDTO commentRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDTO responseDTO = commentService.createComment(commentRequestDTO, userDetails.getUser());

        return ResponseEntity.status(201).body(responseDTO);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommonResponseDTO> putComment(@PathVariable Long commentId, @RequestBody CommentRequestDTO commentRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            CommentResponseDTO responseDTO = commentService.updateComment(commentId, commentRequestDTO, userDetails.getUser());
            return ResponseEntity.ok().body(responseDTO);
        } catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommonResponseDTO> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            commentService.deleteComment(commentId, userDetails.getUser());
            return ResponseEntity.ok().body(new CommonResponseDTO("정상적으로 삭제 되었습니다.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
