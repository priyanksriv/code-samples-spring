package com.cleanarch.blog.rabbitmq.service;

import com.cleanarch.blog.domain.bo.PostSummaryBo;
import com.cleanarch.blog.domain.port.service.Sink;
import com.cleanarch.blog.rabbitmq.dto.PostSummaryDto;
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
public class SummaryDumperSink implements Sink<PostSummaryBo> {

  private BoundMapperFacade<PostSummaryDto, PostSummaryBo> postMapper;

  @Autowired
  public SummaryDumperSink(RmqDtoMapper mapper) {
    this.postMapper = mapper.postSummaryMapper();
  }

  @StreamListener(Sink.INPUT)
  public void listen(PostSummaryDto payload) {
    log.info("Payload: {}", payload);
    consume(this.postMapper.map(payload));
  }

  @Override
  public void consume(PostSummaryBo payload) {
    log.info("New post summary notification {}", payload);
  }

  public interface Sink {

    String INPUT = "post-summary-sink";

    @Input(INPUT)
    SubscribableChannel input();
  }
}