package com.feng.persontask;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class PersonTaskTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	@Test
	public void deploymentProcess() {
		InputStream inBpmn = this.getClass().getResourceAsStream("personProcess.bpmn");
		InputStream inPng = this.getClass().getResourceAsStream("personProcess.png");
		processEngine.getRepositoryService().createDeployment()
				.name("��������")
				.addInputStream("personProcess.bpmn", inBpmn)
				.addInputStream("personProcess.png", inPng)
				.deploy();
	}

	@Test
	public void startProcess() {
		String key = "personProcess";
//		Map<String, Object> variables = new HashMap<String, Object>();
//		variables.put("userId", "������");
		processEngine.getRuntimeService()
				.startProcessInstanceByKey(key);
		System.out.println("start success");
	}
	
	@Test
	public void findTask() {
		String assignee = "���޼�";
		List<Task> list = processEngine.getTaskService().createTaskQuery().taskAssignee(assignee).list();
		if(list != null && !list.isEmpty()) {
			for(Task task : list) {
				System.out.println("assignee:"+task.getAssignee());
				System.out.println("processDefinitionId:"+task.getProcessDefinitionId());
				System.out.println("processINstanceId:"+task.getProcessInstanceId());
				System.out.println("taskId:" + task.getId());
			}
		}
	}
	
	@Test
	public void setExclusiveness() {
		String taskId = "1702";
		Map<String, Object> variables = new HashMap<String, Object>();
		//variables.put("message", "��Ҫ");
		processEngine.getTaskService().complete(taskId);
	}
}
