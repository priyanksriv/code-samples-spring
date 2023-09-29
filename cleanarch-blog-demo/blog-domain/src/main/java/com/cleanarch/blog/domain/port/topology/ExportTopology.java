package com.cleanarch.blog.domain.port.topology;

import com.cleanarch.blog.domain.bo.PostBo;

public interface ExportTopology {

  PostBo getOriginalPost(String postId);

  String summarize(String content);
}
