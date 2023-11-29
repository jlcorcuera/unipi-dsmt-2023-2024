package it.unipi.dsmt.jakartaee.lab05.ejb;

import it.unipi.dsmt.jakartaee.lab05.interfaces.CalculatorEJB;
import jakarta.ejb.Stateless;

@Stateless
public class CalculatorEJBImpl implements CalculatorEJB {
    @Override
    public int sum(int a, int b) {
        return a + b;
    }
    @Override
    public int sub(int a, int b) {
        return a - b;
    }
    @Override
    public int mul(int a, int b) {
        return a * b;
    }
    @Override
    public double div(int a, int b) {
        return a * 1.0 / b;
    }
}