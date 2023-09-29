package com.cleanarch.blog.domain.port.service;

import com.cleanarch.blog.domain.bo.MessageBo;

public interface Source<T extends MessageBo> {

  void send(T payload);
}
