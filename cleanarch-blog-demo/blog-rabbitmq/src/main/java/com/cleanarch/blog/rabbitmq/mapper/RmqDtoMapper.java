package com.cleanarch.blog.rabbitmq.mapper;

import com.cleanarch.blog.domain.bo.PostBo;
import com.cleanarch.blog.domain.bo.PostSummaryBo;
import com.cleanarch.blog.rabbitmq.dto.PostDto;
import com.cleanarch.blog.rabbitmq.dto.PostSummaryDto;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
public class RmqDtoMapper {

  MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

  public BoundMapperFacade<PostDto, PostBo> postMapper() {
    return mapperFactory.getMapperFacade(PostDto.class, PostBo.class);
  }

  public BoundMapperFacade<PostSummaryDto, PostSummaryBo> postSummaryMapper() {
    return mapperFactory.getMapperFacade(PostSummaryDto.class, PostSummaryBo.class);
  }
}
