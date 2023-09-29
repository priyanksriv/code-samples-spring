package com.cleanarch.blog.rabbitmq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDto {

  private String title;
  private String content;
}
