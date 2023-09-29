package com.cleanarch.blog.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSummaryDto {

  private String postId;
  private String summary;
}

