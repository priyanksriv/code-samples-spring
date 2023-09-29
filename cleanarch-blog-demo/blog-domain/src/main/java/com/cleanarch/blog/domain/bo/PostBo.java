package com.cleanarch.blog.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostBo implements MessageBo {

  private String id;
  private String title;
  private String content;
  private String summary;
}
