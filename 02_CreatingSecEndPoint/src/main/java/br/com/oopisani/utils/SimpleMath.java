package br.com.oopisani.utils;

import br.com.oopisani.exception.UnsupportedMathOperationException;

public class SimpleMath {

    public Double sum(Double numberOne, Double numberTwo) {
        return numberOne + numberTwo;
    }

    public Double subtraction(Double numberOne, Double numberTwo) {
        return numberOne - numberTwo;
    }

    public Double multiplication(Double numberOne, Double numberTwo) {
        return numberOne * numberTwo;
    }

    public Double division(Double numberOne, Double numberTwo) {
        if(numberTwo== 0)
            throw new UnsupportedMathOperationException("Denominator can't be 0!");

        return numberOne / numberTwo;
    }

    public Double mean(Double numberOne, Double numberTwo) {
        return (numberOne + numberTwo) / 2;
    }

    public Double squareRoot(Double numberOne) {
        return Math.sqrt(numberOne);
    }
}