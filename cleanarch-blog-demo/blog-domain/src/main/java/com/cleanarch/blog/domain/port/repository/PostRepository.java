package com.cleanarch.blog.domain.port.repository;

import com.cleanarch.blog.domain.bo.PostBo;
import java.util.List;

public interface PostRepository {

  PostBo addPost(PostBo post);

  PostBo findPostById(String id);

  void deletePostById(String id);

  List<PostBo> findAllPosts();

  String getPostSummary(String postId);
}
