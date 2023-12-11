package it.unipi.dsmt.jakartaee.lab_10_ejb.producers;

import it.unipi.dsmt.jakartaee.lab_10_ejb_interfaces.dto.ContactUsDTO;
import it.unipi.dsmt.jakartaee.lab_10_ejb_interfaces.interfaces.ContactUsEJB;
import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

@Stateless(mappedName = "ContactUsProducerEJBV2")
public class ContactUsProducerEJBV2 implements ContactUsEJB {

    @Resource(lookup = "jms/ContactUsQueue")
    private Queue queue;
    @Inject
    private JMSContext jmsContext;
    @Override
    public void processContactUsDTO(ContactUsDTO contactUsDTO) {
        System.out.println("call ContactUsProducerEJBV2.processContactUsDTO");
        jmsContext.createProducer().send(queue, contactUsDTO);
    }
}
