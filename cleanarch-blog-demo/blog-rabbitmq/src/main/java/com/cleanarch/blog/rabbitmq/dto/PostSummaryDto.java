package com.cleanarch.blog.rabbitmq.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostSummaryDto {

  private String postId;
  private String summary;
}
