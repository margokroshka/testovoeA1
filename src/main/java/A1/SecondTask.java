package A1;

import java.math.BigDecimal;

public class SecondTask {
    public static BigDecimal calcUn(int n) {
        BigDecimal factorial = BigDecimal.valueOf(1);
        BigDecimal sum = BigDecimal.valueOf(0);

        for (int i = 1; i <= n; i++) {
            factorial = factorial.multiply(BigDecimal.valueOf(i));
            sum = sum.add(factorial);
        }

        BigDecimal un = sum.divide(factorial, 6, BigDecimal.ROUND_HALF_UP);
        return un;
    }

    public static void main(String[] args) {
        int n = 5;
        BigDecimal result = calcUn(n);

        System.out.println("un(" + n + ") = " + result);
    }
}