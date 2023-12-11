package it.unipi.dsmt.jakartaee.lab_06_ejb.producers;

import it.unipi.dsmt.jakartaee.lab_10_ejb_interfaces.dto.ContactUsDTO;
import it.unipi.dsmt.jakartaee.lab_10_ejb_interfaces.interfaces.ContactUsEJB;
import jakarta.ejb.Stateless;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import jakarta.jms.QueueConnectionFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Stateless(mappedName = "ContactUsProducerEJB")
public class ContactUsProducerEJB implements ContactUsEJB {
    static final String QC_FACTORY_NAME = "jms/__defaultConnectionFactory";
    static final String QUEUE_NAME = "jms/ContactUsQueue";
    private Queue queue;
    private JMSContext jmsContext;

    public ContactUsProducerEJB() {
        try{
            Context ic = new InitialContext();
            queue = (Queue) ic.lookup(QUEUE_NAME);
            QueueConnectionFactory qcf = (QueueConnectionFactory)ic.lookup(QC_FACTORY_NAME);
            jmsContext = qcf.createContext();
        }
        catch (NamingException e) {
            System.err.println("Unable to initialize ContactUsProducerEJB EJB.");
            e.printStackTrace();
        }
    }
    @Override
    public void processContactUsDTO(ContactUsDTO contactUsDTO) {
        System.out.println("call ContactUsProducerEJB.processContactUsDTO");
        jmsContext.createProducer().send(queue, contactUsDTO);
    }
}
