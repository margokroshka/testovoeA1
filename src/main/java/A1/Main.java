package A1;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        main2();
    }
    public static void main1() {
        StringBuilder IPv4 = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        String ipv4 = scanner.nextLine();
        String[] words = ipv4.split("\\.");
        for (String word : words) {
            StringBuilder IP = new StringBuilder(Integer.toBinaryString(Integer.parseInt(word)));
            if (IP.length() < 8) {
                while (IP.length() != 8) {
                    IP.insert(0, "0");
                }
            }
            IPv4.append(IP);
        }
        System.out.println("int32: ");
        System.out.println(new BigInteger(IPv4.toString(), 2).toString(10));
    }
    public static void main2() {
        String IPv4;
        Scanner scanner = new Scanner(System.in);
        String ipv4 = scanner.nextLine();
        StringBuilder IP = new StringBuilder(new BigInteger(ipv4).toString(2));
        while (IP.length() != 32) {
            IP.insert(0, "0");
        }
        while (IP.length() >= 8) {
            IPv4 = IP.substring(0, 8);
            System.out.print(new BigInteger(IPv4, 2).toString(10) + ".");
            IP.delete(0, 8);
        }
    }
}
