/*
package cn.tedu.gate.initial.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@Configuration
public class KafkaProducerConfig {

    // kafka 集群
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    // 批次大小
    @Value("${spring.kafka.producer.batch-size}")
    private Integer batchSize;
    // 重试次数
    @Value("${spring.kafka.producer.retries}")
    private Integer retries;
    // 缓冲区大小
    @Value("${spring.kafka.producer.buffer-memory}")
    private Integer bufferMemory;
    // 等待时间
    @Value("${spring.kafka.producer.linger}")
    private Integer linger;
}
*/
