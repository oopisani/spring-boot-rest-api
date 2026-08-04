package br.com.oopisani.utils;

import br.com.oopisani.exception.UnsupportedMathOperationException;

public class MathUtils {
    public static boolean isNumeric(String strNumber) {
        if(strNumber == null || strNumber.isEmpty()) {
            return false; }
        // else
        String number = strNumber.replace(",","."); // R$ 5,80 USD 5.80
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

    public static Double convertToDouble(String strNumber) {
        if(!isNumeric(strNumber)) {
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return Double.parseDouble(strNumber);
    }

}
