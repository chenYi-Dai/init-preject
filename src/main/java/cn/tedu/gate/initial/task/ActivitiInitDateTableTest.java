package cn.tedu.gate.initial.task;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;

/**
 * 测试类
 * 作用：测试activiti所需的25张表的生成
 */
public class ActivitiInitDateTableTest {
    public static void main(String[] args) {
        //1.创建processEngineConfiguration对象
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        //2.创建processengine对象；
        ProcessEngine processEngine = configuration.buildProcessEngine();
        System.out.println(processEngine);
        String name = processEngine.getName();
        System.out.println(name);

    }

}