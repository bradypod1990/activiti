package com.feng;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class ProcessDefinitionTest {

	
ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/*
	 * ������Դ�ļ���������
	 */
	@Test
	public void deploymentProcess_classpath() {
		Deployment deployment = processEngine.getRepositoryService().createDeployment()
			.name("helloworld���̶���")
			.addClasspathResource("diagrams/helloworld.bpmn")
			.addClasspathResource("diagrams/helloworld.png")
			.deploy();
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	
	/**
	 * ����zip����������
	 */
	@Test
	public void deploymentProcess_zip() {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloworld.zip");
		ZipInputStream zipInputStream = new ZipInputStream(in);
		Deployment deployment = processEngine.getRepositoryService().createDeployment()
					.name("helloworld ����")
					.addZipInputStream(zipInputStream)
					.deploy();
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	
	/**
	 * ��ѯ���̶�����Ϣ
	 */
	@Test
	public void queryProcessDefinition() {
		ProcessDefinitionQuery pro =  processEngine.getRepositoryService().createProcessDefinitionQuery();
		List<ProcessDefinition> list = pro
//			.processDefinitionId("")
//			.processDefinitionName("processDefinitionName")
			.orderByProcessDefinitionVersion()
			.asc()
			.list();
		if(list != null && list.size() > 0) {
			for(ProcessDefinition pd : list) {
				System.out.println("���̶���ID��" +pd.getId());
				System.out.println("���̶������ƣ�"+pd.getName()); //��Ӧactiviti.cfg.xml�е�name
				System.out.println("���̶���key:"+pd.getKey());//��Ӧactiviti.cfg.xml�е�id
				System.out.println("���̶�����Դpng���ƣ�"+pd.getDiagramResourceName());
				System.out.println("���̶�����Դbpmn����"+pd.getResourceName());
				System.out.println("���̶���汾��"+pd.getVersion());
				System.out.println("---------------------------------------------------");
			}
		}
	}
	
	/**
	 * ɾ�����̶���
	 */
	@Test
	public void deleteProcessDefinition() {
		String deploymentId = "601";
		RepositoryService repositoryService = processEngine.getRepositoryService();
		/**
		 * ������ɾ��
		 * ֻ��ɾ��û�в�������̣�ɾ����������̻ᱨ��
		 */
		repositoryService.deleteDeployment(deploymentId);
		
		
		//repositoryService.deleteDeployment("601", true);
		
		
		System.out.println("ɾ�����̶���ɹ�");
	}
	
	@Test
	public void getPic() throws IOException {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		String deploymentId = "601";
		String resourceName = "";
		List<String> list = repositoryService.getDeploymentResourceNames(deploymentId);
		if(list != null && list.size() > 0) {
			for(String str : list) {
				if(str.indexOf(".png") > 0) {
					resourceName = str;
					break;
				}
			}
		}
		
		InputStream in = repositoryService.getResourceAsStream(deploymentId, resourceName);
		FileUtils.copyInputStreamToFile(in, new File("F:/" + resourceName));
	}
	
	/**
	 * ɾ�����̶���
	 */
	@Test
	public void deleteProcessDefinitionByKey() {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		String key = "myProcess";
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey(key)
				.list();
		for(ProcessDefinition pd : list) {
			String id = pd.getDeploymentId();
			repositoryService.deleteDeployment(id, true);
		}
			
	}
}
