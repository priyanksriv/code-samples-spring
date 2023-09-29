package com.cleanarch.blog.domain.port.service;

import com.cleanarch.blog.domain.bo.PostBo;
import com.cleanarch.blog.domain.bo.PostSummaryBo;
import java.util.List;

public interface PostService {

  PostBo addPost(PostBo post);

  PostBo findPostById(String id);

  void deletePostById(String id);

  List<PostBo> findAllPosts();

  PostSummaryBo getSummary(String id);
}
