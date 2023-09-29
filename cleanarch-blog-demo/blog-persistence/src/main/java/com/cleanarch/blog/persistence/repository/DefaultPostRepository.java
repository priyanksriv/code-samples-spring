package com.cleanarch.blog.persistence.repository;

import static java.util.stream.Collectors.toList;

import com.cleanarch.blog.domain.bo.PostBo;
import com.cleanarch.blog.domain.exception.PostNotFoundException;
import com.cleanarch.blog.domain.port.repository.PostRepository;
import com.cleanarch.blog.persistence.entity.Post;
import com.cleanarch.blog.persistence.mapper.EntityMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.BoundMapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class DefaultPostRepository implements PostRepository {

  private PostDataRepository dataRepository;
  private BoundMapperFacade<Post, PostBo> postMapper;

  @Autowired
  public DefaultPostRepository(PostDataRepository dataRepository, EntityMapper postMapper) {
    this.dataRepository = dataRepository;
    this.postMapper = postMapper.postMapper();
  }

  @Override
  public PostBo addPost(PostBo post) {
    Post entity = this.dataRepository.save(this.postMapper.mapReverse(post));
    return this.postMapper.map(entity);
  }

  @Override
  public PostBo findPostById(String id) {
    Post post = this.dataRepository.findById(id)
        .orElseThrow(() -> new PostNotFoundException("Not found"));
    return this.postMapper.map(post);
  }

  @Override
  public void deletePostById(String id) {
    Post post = this.dataRepository.findById(id)
        .orElseThrow(() -> new PostNotFoundException("Not found"));
    this.dataRepository.delete(post);
  }

  @Override
  public List<PostBo> findAllPosts() {
    return this.dataRepository.findAll().stream()
        .map(this.postMapper::map)
        .collect(toList());
  }

  @Override
  public String getPostSummary(String postId) {
    return findPostById(postId).getSummary();
  }
}
