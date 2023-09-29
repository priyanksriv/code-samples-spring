package com.cleanarch.blog.persistence.mapper;

import com.cleanarch.blog.domain.bo.PostBo;
import com.cleanarch.blog.persistence.entity.Post;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;


@Component
public class EntityMapper {

  MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

  public BoundMapperFacade<Post, PostBo> postMapper() {
    return mapperFactory.getMapperFacade(Post.class, PostBo.class);
  }
}
