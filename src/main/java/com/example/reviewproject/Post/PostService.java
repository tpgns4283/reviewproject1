package com.example.reviewproject.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

import com.example.reviewproject.user.User;
import com.example.reviewproject.user.UserDTO;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostResponseDTO createPost(PostRequestDTO dto, User user) {
        Post post = new Post(dto);
        post.setUser(user);

        var saved = postRepository.save(post);

        return new PostResponseDTO(saved);
    }

    public PostResponseDTO getPostDto(Long postId) {
        Post post = getPost(postId);
        return new PostResponseDTO(post);
    }

    public Map<UserDTO, List<PostResponseDTO>> getUserPostMap() {
        Map<UserDTO, List<PostResponseDTO>> userPostMap = new HashMap<>();

        List<Post> PostList = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate")); // 작성일 기준 내림차순

        PostList.forEach(Post -> {
            var userDto = new UserDTO(Post.getUser());
            var PostDto = new PostResponseDTO(Post);
            if (userPostMap.containsKey(userDto)) {
                userPostMap.get(userDto).add(PostDto);
            } else {
                userPostMap.put(userDto, new ArrayList<>(List.of(PostDto)));
            }
        });

        return userPostMap;
    }

    @Transactional
    public PostResponseDTO updatePost(Long postId, PostRequestDTO postRequestDTO, User user) {
        Post post = getUserPost(postId, user);

        post.setTitle(postRequestDTO.getTitle());
        post.setContent(postRequestDTO.getContent());

        return new PostResponseDTO(post);
    }

    @Transactional
    public PostResponseDTO completePost(Long postId, User user) {
        Post post = getUserPost(postId, user);

        post.complete();

        return new PostResponseDTO(post);
    }

    public Post getPost(Long postId) {

        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할일 ID 입니다."));
    }

    public Post getUserPost(Long postId, User user) {
        Post post = getPost(postId);

        if(!user.getId().equals(post.getUser().getId())) {
            throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
        }
        return post;
    }
}