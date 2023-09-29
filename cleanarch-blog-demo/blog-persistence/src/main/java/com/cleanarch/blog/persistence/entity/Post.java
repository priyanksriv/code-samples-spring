package com.cleanarch.blog.persistence.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

  @Id
  private String id;
  private String title;
  private String content;
  private String summary;

  @CreatedDate
  private LocalDateTime createdDate;
}
