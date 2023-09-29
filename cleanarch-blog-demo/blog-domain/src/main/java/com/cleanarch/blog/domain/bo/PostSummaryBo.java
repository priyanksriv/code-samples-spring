package com.cleanarch.blog.domain.bo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostSummaryBo implements MessageBo {

  private String postId;
  private String summary;
}
