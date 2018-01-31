package com.aem.community.workflow;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;

@Component(service=WorkflowProcess.class,
property={
        Constants.SERVICE_DESCRIPTION  +"=Test Email workflow process implementation.",
        Constants.SERVICE_VENDOR +"=Adobe",
        "process.label=" + "Test Email Workflow Process",
})
public class CustomStep implements WorkflowProcess {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Reference
	private MessageGatewayService messageGatewayService;

	@Override
	public void execute(WorkItem arg0, WorkflowSession arg1, MetaDataMap arg2)
			throws WorkflowException {
		try {
			log.info("Here in execute method");    //ensure that the execute method is invoked    
			
			//Declare a MessageGateway service
			MessageGateway<Email> messageGateway; 
			      
			//Set up the Email message
			Email email = new SimpleEmail();
			
			
			      
			//Set the mail values
			String emailToRecipients = "si.huynh@sutrixsolutions.com"; 
			String emailCcRecipients = "si.huynh@sutrixsolutions.com"; 
			  
			email.addTo(emailToRecipients);
			email.addCc(emailCcRecipients);
			email.setSubject("AEM Custom Step");
			email.setFrom("scottm@adobe.com"); 
			email.setMsg("This message is to inform you that the CQ content has been deleted");
			  
			//Inject a MessageGateway Service and send the message
			messageGateway = messageGatewayService.getGateway(Email.class);
  
			// Check the logs to see that messageGateway is not null
			messageGateway.send((Email) email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
