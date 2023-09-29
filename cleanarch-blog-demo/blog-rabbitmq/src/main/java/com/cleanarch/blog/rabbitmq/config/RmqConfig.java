package com.cleanarch.blog.rabbitmq.config;

import com.cleanarch.blog.rabbitmq.service.PostSink;
import com.cleanarch.blog.rabbitmq.service.PostSummarySource;
import com.cleanarch.blog.rabbitmq.service.SummaryDumperSink;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding({
    PostSink.Sink.class,
    SummaryDumperSink.Sink.class,
    PostSummarySource.Source.class,
})
public class RmqConfig {

}
