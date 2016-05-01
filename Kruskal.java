/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kruskal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author krista
 */
class Kruskal {

    class Vrchol {

        public Vrchol(int id) {
            this.id = id;
            this.parent=this;
        }
       Vrchol parent;
       int size=1;
        int id;
    }

    class Hrana {

        public Hrana(int u, int v, int price) {
            this.u = u;
            this.v = v;
            this.price = price;
        }
        int u;
        int v;
        int price;
    }

    static int sum;
    static int n;
    static ArrayList<Vrchol> mesta = new ArrayList<>();

    public static boolean FindSet(Vrchol u, Vrchol v, int cena) {
        Vrchol par_u = FindParent(u);
        Vrchol par_v = FindParent(v);
        if (!par_u.equals(par_v)) {
            sum += cena;
           if(par_u.size<par_v.size){
               par_u.parent = par_v;
               par_v.size+=par_u.size;
               if(par_v.size == n+1) return true;
           }else {
               par_v.parent = par_u;
               par_u.size+=par_v.size;
               if(par_u.size == n+1) return true;
           }
           
        }return false;
    }

    public static Vrchol FindParent(Vrchol v){
        if(v == v.parent) return v;
       
        return FindParent(v.parent);
    }
   /* public static void Union(Vrchol u, Vrchol v) {
        v.parent = u.parent;
        for (Integer i : u.dosah) {
            mesta.get(i).dosah.addAll(u.dosah);
        }
    }*/

    public static void main(String[] args) {
        Kruskal t = new Kruskal();
        Scanner s = new Scanner(System.in);
        n = s.nextInt(); //pocet miest
        
        int p_hran = Math.max(1, (n+1)*n/2);
        PriorityQueue<Hrana> vsetky_hrany = new PriorityQueue<>(p_hran, new Comparator<Hrana>() {
            @Override
            public int compare(Hrana hrana1, Hrana hrana2) {
                return hrana1.price - hrana2.price;
            }
        });
        
        for (int i = 0; i < n; i++) {
            int a = s.nextInt();
            mesta.add(t.new Vrchol(i));
            for (int j=0; j<n; j++){
                if (j==i)vsetky_hrany.add(t.new Hrana(n,j,a));
            }
        }
        mesta.add(t.new Vrchol(n));//novy tzv. studnovy vrchol
        
        
        int cena_hrany;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cena_hrany = s.nextInt();
                if (i<j)vsetky_hrany.add(t.new Hrana(i,j,cena_hrany));
            }
        }

        while (!vsetky_hrany.isEmpty()) {
           Hrana e = vsetky_hrany.poll();
            if(t.FindSet(mesta.get(e.u), mesta.get(e.v), e.price)) break;
           // if(mesta.get(e.u).dosah.size() == n+1 )break;
        }

        System.out.println(sum);
    }
}