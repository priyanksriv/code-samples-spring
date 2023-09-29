package com.cleanarch.blog.service;

import com.cleanarch.blog.domain.bo.PostBo;
import com.cleanarch.blog.domain.bo.PostSummaryBo;
import com.cleanarch.blog.domain.port.repository.PostRepository;
import com.cleanarch.blog.domain.port.service.PostService;
import com.cleanarch.blog.domain.port.service.Source;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class DefaultPostService implements PostService {

  private PostRepository postRepository;
  private Source source;

  @Autowired
  public DefaultPostService(PostRepository postRepository, Source source) {
    this.postRepository = postRepository;
    this.source = source;
  }

  @Override
  public PostBo addPost(PostBo post) {
    post.setSummary(generateSummary(post.getContent()));
    PostBo saved = this.postRepository.addPost(post);

    log.info("Publishing summary for downstream systems postId {}", saved.getId());
    source.send(getSummary(saved.getId()));

    return saved;
  }

  @Override
  public PostBo findPostById(String id) {
    return this.postRepository.findPostById(id);
  }

  @Override
  public void deletePostById(String id) {
    this.postRepository.deletePostById(id);
  }

  @Override
  public List<PostBo> findAllPosts() {
    return this.postRepository.findAllPosts();
  }

  @Override
  public PostSummaryBo getSummary(String postId) {
    return PostSummaryBo.builder()
        .postId(postId)
        .summary(this.postRepository.getPostSummary(postId))
        .build();
  }

  private String generateSummary(String content) {
    log.info("Generating summary for : {}", content);

    int leftLimit = 97, rightLimit = 122, targetStringLength = 100;
    StringBuilder buffer = new StringBuilder(targetStringLength);

    for (int i = 0; i < targetStringLength; i++) {
      int randomLimitedInt = leftLimit + (int)
          (ThreadLocalRandom.current().nextFloat() * (rightLimit - leftLimit + 1));
      buffer.append((char) randomLimitedInt);
    }
    return buffer.toString();
  }
}
