package com.feng;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class HelloWorldProcessTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	
	@Test
	public void deploymentProcess() {
		Deployment deployment = processEngine.getRepositoryService().createDeployment()
			.name("helloworldDeployment")
			.addClasspathResource("diagrams/helloworld.bpmn")
			.addClasspathResource("diagrams/helloworld.png")
			.deploy();
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	
	@Test
	public void startProcess() {
		ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("myProcess");
		System.out.println(processInstance.getId());
		System.out.println(processInstance.getProcessDefinitionId());
	}
	
	@Test
	public void findMyPersonTask() {
		String assignee = "����";
		TaskService taskService = processEngine.getTaskService();
		List<Task> list = taskService.createTaskQuery().taskAssignee(assignee).list();
		if(list != null && list.size() > 0) {
			for(Task task : list) {
				System.out.println("����ID��" + task.getId());
				System.out.println("�������ƣ�" + task.getName());
				System.out.println("���񴴽�ʱ�䣺" + task.getCreateTime());
				System.out.println("����ִ���ˣ�" + task.getAssignee());
				System.out.println("����ID:" + task.getProcessDefinitionId());
				System.out.println("����ʵ��ID:" + task.getProcessInstanceId());
			}
		}
	}
	
	@Test
	public void completeTask() {
		String taskId = "302";
		TaskService taskService = processEngine.getTaskService();
		taskService.complete(taskId);
		System.out.println("complete task: taskId = " + taskId);
	}
}
