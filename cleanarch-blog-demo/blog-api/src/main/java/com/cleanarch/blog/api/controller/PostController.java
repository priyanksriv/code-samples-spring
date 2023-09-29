package com.cleanarch.blog.api.controller;

import static java.util.stream.Collectors.toList;

import com.cleanarch.blog.api.dto.PostDto;
import com.cleanarch.blog.api.dto.PostSummaryDto;
import com.cleanarch.blog.api.mapper.ApiDtoMapper;
import com.cleanarch.blog.domain.bo.PostBo;
import com.cleanarch.blog.domain.bo.PostSummaryBo;
import com.cleanarch.blog.domain.port.service.PostService;
import java.net.URI;
import java.util.List;
import ma.glasnost.orika.BoundMapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping(value = "/api/posts", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PostController {

  private PostService postService;
  private BoundMapperFacade<PostDto, PostBo> postMapper;
  private BoundMapperFacade<PostSummaryDto, PostSummaryBo> postSummaryMapper;

  @Autowired
  public PostController(PostService postService, ApiDtoMapper mapper) {
    this.postService = postService;
    this.postMapper = mapper.postMapper();
    this.postSummaryMapper = mapper.postSummaryMapper();
  }

  @GetMapping
  public ResponseEntity<List<PostDto>> all() {
    List<PostDto> posts = this.postService.findAllPosts().stream()
        .map(this.postMapper::mapReverse)
        .collect(toList());

    return ResponseEntity.ok(posts);
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody PostDto post) {

    PostDto saved = this.postMapper.mapReverse(
        this.postService.addPost(this.postMapper.map(post)));

    return ResponseEntity.created(createUri(saved)).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<PostDto> get(@PathVariable("id") String id) {
    PostDto post = this.postMapper.mapReverse(this.postService.findPostById(id));
    return ResponseEntity.ok(post);
  }

  @GetMapping("/{id}/summary")
  public ResponseEntity<PostSummaryDto> getSummary(@PathVariable("id") String id) {
    PostSummaryDto post = this.postSummaryMapper.mapReverse(this.postService.getSummary(id));
    return ResponseEntity.ok(post);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") String id) {
    this.postService.deletePostById(id);
    return ResponseEntity.ok().build();
  }

  private static URI createUri(PostDto dto) {
    return ServletUriComponentsBuilder.fromCurrentRequest()
        .buildAndExpand(dto.getId())
        .toUri();
  }
}
