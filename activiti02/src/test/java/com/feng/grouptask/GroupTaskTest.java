package com.feng.grouptask;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class GroupTaskTest {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	@Test
	public void deploymentProcess() {
		InputStream inBpmn = this.getClass().getResourceAsStream("groupProcess.bpmn");
		InputStream inPng = this.getClass().getResourceAsStream("groupProcess.png");
		processEngine.getRepositoryService().createDeployment()
				.name("������")
				.addInputStream("groupProcess.bpmn", inBpmn)
				.addInputStream("groupProcess.png", inPng)
				.deploy();
	}

	@Test
	public void startProcess() {
		String key = "groupProcess";
//		Map<String, Object> variables = new HashMap<String, Object>();
//		variables.put("userId", "������");
		processEngine.getRuntimeService()
				.startProcessInstanceByKey(key);
		System.out.println("start success");
	}
	
	@Test
	public void findTask() {
		String candidateUser = "wade";
		List<Task> list = processEngine.getTaskService()
				.createTaskQuery()
//				.taskAssignee(candidateUser)
				.taskCandidateUser(candidateUser)
				.list();
		if(list != null && !list.isEmpty()) {
			for(Task task : list) {
				System.out.println("assignee:"+task.getAssignee());
				System.out.println("processDefinitionId:"+task.getProcessDefinitionId());
				System.out.println("processINstanceId:"+task.getProcessInstanceId());
				System.out.println("taskId:" + task.getId());
			}
		}
	}
	
	/**
	 * ʰ����������������䵽��������,ʰ���ת�ɸ�������, ���õİ����˿���Ϊ�������Ա��Ҳ����Ϊ����
	 */
	@Test
	public void claim() {
		String taskId = "6304";
		String userId = "james";
		processEngine.getTaskService().claim(taskId, userId);
	}
	
	/**
	 * �ɸ�������ת��������ǰ��Ϊ�������ԭ����������
	 */
	@Test
	public void setGroup() {
		String taskId = "5504";
		processEngine.getTaskService().setAssignee(taskId, null);
	}
	
	/**
	 * ������Ա 
	 */
	@Test
	public void addGroupUser() {
		String taskId = "5504";
		String userId = "��ʲ";
		processEngine.getTaskService().addCandidateUser(taskId, userId);
	}
	
	/**
	 * ɾ�����Ա 
	 */
	@Test
	public void delGroupUser() {
		String taskId = "5504";
		String userId = "��ʲ";
		processEngine.getTaskService().deleteCandidateUser(taskId, userId);
	}
	
	@Test
	public void setExclusiveness() {
		String taskId = "6304";
		Map<String, Object> variables = new HashMap<String, Object>();
		//variables.put("message", "��Ҫ");
		processEngine.getTaskService().complete(taskId);
	}
}
