package cn.tedu.gate.initial;

import cn.tedu.gate.initial.dto.BaseResp;
import cn.tedu.gate.initial.service.PositionService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.zip.ZipInputStream;

@Slf4j
@SpringBootTest(classes = InitialApplicationTest.class)
public class DeploymentTest {

/*    @Autowired
    private RepositoryService repositoryService;*/

    @Resource
    private PositionService positionService;


    /**
     * 流程部署

    @Test
    public void initDeployment() {
        String fileName = "bpmn/Part1_Deployment.bpmn20.xml";
        Deployment deployment = this.repositoryService.createDeployment()
                .addClasspathResource(fileName)
                .name("流程部署测试")
                .deploy();
        System.out.println("流程部署名称：" + deployment.getName());
    }
     */
    @Test
    public void initD() {
        String ip = "113.90.80.151";
        BaseResp location = positionService.getLocation(ip);
        log.info("location | {}",JSONObject.toJSONString(location));
    }

    /**
     * 流程部署（Zip包）

    @Test
    public void initDeploymentByZip() {
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("bpmn/Part1_Deployment.zip");
        assert inputStream != null;
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        Deployment deployment = this.repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .name("流程部署测试（Zip包）")
                .deploy();
        System.out.println("流部署名称：" + deployment.getName());
    }
     */
    /**
     * 查询流程部署列表

    @Test
    public void listDeployments() {
        List<Deployment> deployments = this.repositoryService.createDeploymentQuery().list();
        if (!deployments.isEmpty()) {
            deployments.forEach(deployment -> {
                System.out.println("Id：" + deployment.getId());
                System.out.println("Name：" + deployment.getName());
                System.out.println("DeploymentTime：" + deployment.getDeploymentTime());
                System.out.println("Key：" + deployment.getKey());
            });
        }
    }
     */
    /**
     * 删除对流程实例、历史流程实例和作业的给定部署和级联删除

    @Test
    public void deleteDeployment() {
        String deploymentId = "4c97f9ce-4774-11ed-930a-e4a8dfd43d4a";
        this.repositoryService.deleteDeployment(deploymentId, false);
    }  */

    //流程引擎
    private ProcessEngine processEngine;

    /**
     * @Description: 数据库初始化
     * @param:
     * @return: void
     */
    @BeforeEach
    public void testProcessEngineConfiguration() {

        ProcessEngineConfiguration config = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration ();
        config.setJdbcDriver ("com.mysql.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://9.134.94.134:3306/tx_activiti?characterEncoding=UTF-8&serverTimezone=UTC&nullCatalogMeansCurrent=true");
        config.setJdbcUsername("root");
        config.setJdbcPassword("ProjectFuture@2024");

        //在构建过程引擎时，执行检查并在必要时执行模式的更新. 如果没有表则创建，
        config.setDatabaseSchemaUpdate (ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        processEngine = config.buildProcessEngine ();
    }

    // 2501
    @Test
    public void testDeployment(){
        Deployment deploy = processEngine.getRepositoryService().createDeployment()
                .addClasspathResource("bpmn/test-demo.bpmn20.xml")
                .name("流程部署测试")
                .key("dem1o")
                .tenantId("cy")
                .category("test")
                .deploy();
        System.out.println(deploy.getId());
    }

    /**
     * 使用 RepositoryService 查询已部署的流程
     */
    @Test
    public void getRepositoryDeployments() {
        List<Deployment> list = processEngine.getRepositoryService().createDeploymentQuery().list();
        log.info("list size {}",list.size());
        list.forEach(deployment -> {
            log.info("deployment id {}",deployment.getId());
            log.info("deployment name {}",deployment.getName());
            log.info("deployment time {}",deployment.getDeploymentTime());
            log.info("deployment key {}",deployment.getKey());
            log.info("deployment tenantId {}",deployment.getTenantId());
        });
    }


    /**
     * 获取当前正在执行的task
     */
    @Test
    public void getTask(){
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery().list();
        for (Task task : list) {
            log.info("task id {}",task.getId());
            log.info("task name {}",task.getName());
            log.info("task assignee {}",task.getAssignee());
            System.out.println("Process Instance ID: " + task.getProcessInstanceId());
        }
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().singleResult();
        log.info("processInstance id {}",processInstance.getId());
        log.info("processInstance businessKey {}",processInstance.getBusinessKey());
        TaskQuery taskQuery = taskService.createTaskQuery().processInstanceId(processInstance.getId());
        log.info("taskQuery count {}",taskQuery.count());
        Task task = taskQuery.singleResult();
        log.info("taskQuery name {}",task.getName());
        log.info("task name {}",task.getId());
        if(task.getName().equals("提交")){
            taskService.complete(task.getId());
        }

        if(task.getName().equals("主管审批")){
            taskService.complete(task.getId());
        }
    }

    @Test
    public void endProesccInstance(){
        RepositoryService repositoryService1 = processEngine.getRepositoryService();
    }


    @Test
    public void startProcessInstance() {

        RepositoryService repositoryService1 = processEngine.getRepositoryService();

        List<Deployment> list = processEngine.getRepositoryService().createDeploymentQuery().list();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        list.forEach(deployment -> {
            log.info("deployment key {}",deployment.getName());
            long count = runtimeService.createProcessInstanceQuery().count();
            log.info("count {}",count);
            if(count == 0){
                log.info("not start processInstance | {}",deployment.getKey());
                ProcessDefinition definitionQuery = repositoryService1.createProcessDefinitionQuery()
                        .deploymentId(deployment.getId()).singleResult();

                ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId(definitionQuery.getKey(),definitionQuery.getTenantId());
                log.info("start processInstance | {}",processInstance.getId());
            }
        });


    }

}
