package cn.tedu.gate.initial.task;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;
import java.util.Properties;
import java.util.Random;

@Slf4j
@Component
public class ProductKafkaMessage {

    @Value("${spring.kafka.template.default-topic}")
    private String topicName;

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Scheduled(cron = "0/2 * * * * ? ")
    public void sendKafkaMessage(){
        JSONObject jsonObject =  new JSONObject();
        jsonObject.put("name", genStr(5));
        Random random = new Random();
        jsonObject.put("age",random.nextInt());
        kafkaTemplate.send(topicName,"testGroup",jsonObject.toJSONString());
        log.info("send ok");
    }




    public String genStr(int size){
        String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        char[] c = s.toCharArray();
        String str="";
        Random random = new Random();
        for( int i = 0; i < size; i ++) {
            str = str +  c[random.nextInt(c.length)];
        }
        return str;
    }
}
