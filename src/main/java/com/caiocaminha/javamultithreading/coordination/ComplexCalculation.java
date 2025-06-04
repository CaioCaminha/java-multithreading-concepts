package com.caiocaminha.javamultithreading.coordination;

import java.math.BigInteger;

public class ComplexCalculation {
    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) throws InterruptedException {
        BigInteger result;
        PowerCalculatingThread result1 = new PowerCalculatingThread(base1, power1);
        PowerCalculatingThread result2 = new PowerCalculatingThread(base2, power2);
        result1.start();
        result2.start();
        result1.join();
        result2.join();
        /*
            Calculate result = ( base1 ^ power1 ) + (base2 ^ power2).
            Where each calculation in (..) is calculated on a different thread
        */
        result = result1.getResult().add(result2.getResult());
        return result;
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            result = base.pow(power.intValue());
        }

        public BigInteger getResult() { return result; }
    }
}
