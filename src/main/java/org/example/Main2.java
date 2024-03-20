package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Integer> nums = new ArrayList<>();

        while ( true ) {
            System.out.printf("명령어) ");
            String cmd = sc.nextLine();
            cmd = cmd.trim();

            if ( cmd.length() == 0 ) {
                continue;
            }

            if ( cmd.equals("exit") ) {
                break;
            }
            else if ( cmd.equals("num") ) {
                System.out.printf("숫자 : ");
                int num = sc.nextInt();
                sc.nextLine();

                nums.add(num);
            }
            else if ( cmd.equals("nums") ) {
                System.out.printf("숫자들 : %s\n", nums.toString());
            }
        }

        sc.close();
    }
}
