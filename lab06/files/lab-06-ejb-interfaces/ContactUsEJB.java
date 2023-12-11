package it.unipi.dsmt.jakartaee.lab_10_ejb_interfaces.interfaces;

import it.unipi.dsmt.jakartaee.lab_10_ejb_interfaces.dto.ContactUsDTO;
import jakarta.ejb.Remote;

@Remote
public interface ContactUsEJB {
    void processContactUsDTO(ContactUsDTO contactUsDTO);
}
