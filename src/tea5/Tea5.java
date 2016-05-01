/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package tea5;

import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author krista
 */
class Tea5 {

    static int find() {

        TreeSet<Integer> us = new TreeSet<>(Collections.reverseOrder());
        int[] zaplat = new int[suma + 1]; //staci tak

        while (!mince.isEmpty()) {
            int a = mince.poll();

            Set<Integer> nove = new TreeSet<>(Collections.reverseOrder());
            for (Integer i : us) {
                int kde = i + a;
                if (kde <= suma && zaplat[kde] <= zaplat[i]) {
                    zaplat[kde] = zaplat[i] + 1;
                    nove.add(kde);
                }
            }
            if (zaplat[a] == 0) zaplat[a]++;
            us.add(a);
            us.addAll(nove);
        }
      return zaplat[suma];
    }

    static int pocet, suma;
    static PriorityQueue<Integer> mince;

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        pocet = s.nextInt();
        suma = s.nextInt();
        int next;
        mince = new PriorityQueue<>();
        for (int i = 0; i < pocet; i++) {
            next = s.nextInt();
            if (next <= suma) {
                mince.add(next);
            }
        }
        if (suma == 0) {
            System.out.println("0");
            return;
        }
        int result = find();
        if (result > 0) {
            System.out.println(result);
        } else {
            System.out.println("-1");
        }

    }

}
