package it.unipi.dsmt.jakartaee.lab_06_ejb_interfaces.dto;

import java.io.Serializable;

public class ContactUsDTO implements Serializable {
    private String email;
    private String comments;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "ContactUsDTO{" +
                "email='" + email + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
