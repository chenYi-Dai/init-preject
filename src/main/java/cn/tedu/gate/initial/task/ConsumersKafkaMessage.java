package cn.tedu.gate.initial.task;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerGroupMetadata;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ConsumersKafkaMessage {

    @Value("${stop.case}")
    private boolean isCease;
    @KafkaListener(topics = "test-topic-name")
    public void listener(ConsumerRecord<String, String> data, Consumer consumer) {
        try {
            ConsumerGroupMetadata consumerGroupMetadata = consumer.groupMetadata();
            String groupId = consumerGroupMetadata.groupId();
            log.info("groupId | {}",groupId);
            if(!isCease){
                throw new RuntimeException("收到消息进行任务阻塞!");
            }
            String topic = data.topic();
            String value = data.value();
            log.info("listener topic value | {} |{}",topic,value);
            consumer.commitAsync();

        } catch (Exception e) {
            log.info("listener error | {}",e.getMessage(),e);
            // 抛出异常，以重试消费
            throw new RuntimeException(e.getMessage());
        }
    }

 /*   @Scheduled(cron = "0/5 * * * * ? ")
    public void receiveKafkaMessage(String str,Consumer consumer){
        JSONObject jsonObject =  new JSONObject();
        log.info("receiveKafkaMessage value | {}",str);
    }*/

}
