package com.cleanarch.blog.rabbitmq.service;

import com.cleanarch.blog.domain.bo.PostBo;
import com.cleanarch.blog.domain.port.service.PostService;
import com.cleanarch.blog.domain.port.service.Sink;
import com.cleanarch.blog.rabbitmq.dto.PostDto;
import com.cleanarch.blog.rabbitmq.mapper.RmqDtoMapper;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.BoundMapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PostSink implements Sink<PostBo> {

  private BoundMapperFacade<PostDto, PostBo> postMapper;
  private PostService postService;

  @Autowired
  public PostSink(RmqDtoMapper mapper, PostService postService) {
    this.postMapper = mapper.postMapper();
    this.postService = postService;
  }

  @StreamListener(Sink.INPUT)
  public void listen(PostDto payload) {
    log.info("Payload: {}", payload);
    consume(this.postMapper.map(payload));
  }

  @Override
  public void consume(PostBo payload) {
    postService.addPost(payload);
  }

  public interface Sink {

    String INPUT = "post-sink";

    @Input(INPUT)
    SubscribableChannel input();
  }
}
