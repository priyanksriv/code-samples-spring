package com.cleanarch.blog.domain.port.service;

import com.cleanarch.blog.domain.bo.MessageBo;

public interface Sink<T extends MessageBo> {

  void consume(T payload);
}
