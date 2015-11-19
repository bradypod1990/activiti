package com.feng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller  
@RequestMapping("/main")  
public class MainController {  
    /** 
     * ��ת��commonpageҳ�� 
     *  
     * @return 
     */  
    @RequestMapping(value = "/common", method = RequestMethod.GET)  
    public String getCommonPage() {  
        return "commonpage";  
    }  
  
    /** 
     * ��ת��adminpageҳ�� 
     *  
     * @return 
     */  
    @RequestMapping(value = "/admin", method = RequestMethod.GET)  
    public String getAadminPage() {  
        return "adminpage";  
  
    }  
  
}  