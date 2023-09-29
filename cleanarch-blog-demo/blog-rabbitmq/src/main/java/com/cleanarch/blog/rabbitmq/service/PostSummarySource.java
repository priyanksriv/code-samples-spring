package com.cleanarch.blog.rabbitmq.service;

import com.cleanarch.blog.domain.bo.PostSummaryBo;
import com.cleanarch.blog.domain.port.service.Source;
import com.cleanarch.blog.rabbitmq.dto.PostSummaryDto;
import com.cleanarch.blog.rabbitmq.mapper.RmqDtoMapper;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.BoundMapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PostSummarySource implements Source<PostSummaryBo> {

  private BoundMapperFacade<PostSummaryDto, PostSummaryBo> postSummaryMapper;
  private PostSummarySource.Source source;

  @Autowired
  public PostSummarySource(RmqDtoMapper mapper, PostSummarySource.Source source) {
    this.postSummaryMapper = mapper.postSummaryMapper();
    this.source = source;
  }

  @Override
  public void send(PostSummaryBo payload) {
    log.info("Sending summary, payload = {}", payload);
    source.output()
        .send(MessageBuilder
            .withPayload(this.postSummaryMapper.mapReverse(payload))
            .build());
  }

  public interface Source {

    String OUTPUT = "post-summary-src";

    @Output(OUTPUT)
    MessageChannel output();
  }
}
