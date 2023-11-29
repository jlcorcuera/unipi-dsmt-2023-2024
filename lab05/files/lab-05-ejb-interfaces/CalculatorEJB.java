package it.unipi.dsmt.jakartaee.lab05.interfaces;

import jakarta.ejb.Remote;

@Remote
public interface CalculatorEJB {
    int sum(int a, int b);
    int sub(int a, int b);
    int mul(int a, int b);
    double div(int a, int b);
}