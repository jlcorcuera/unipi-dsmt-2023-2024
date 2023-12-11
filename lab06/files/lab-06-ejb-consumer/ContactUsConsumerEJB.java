package it.unipi.dsmt.jakartaee.lab_10_ejb.consumer;

import it.unipi.dsmt.jakartaee.lab_10_ejb_interfaces.dto.ContactUsDTO;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;

@MessageDriven(name = "ConsumerEJB",
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/ContactUsQueue"),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")
        })
public class ContactUsConsumerEJB implements MessageListener {
        @Override
        public void onMessage(Message message) {
                ContactUsDTO contactUsDTO = null;
                try {
                        contactUsDTO = message.getBody(ContactUsDTO.class);
                        System.out.println("Message on consumer: " + contactUsDTO);
                } catch (JMSException e) {
                        e.printStackTrace();
                }
        }
}
