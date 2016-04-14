/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 *
 * @author krista
 */
class Dijkstra {

    static int MAX = Integer.MAX_VALUE;
    static int PLUS = 10000;
    static int n, m, q;
    static int[][] matrix;
   // static ArrayList<Hrana> nutne;
    static Vrchol[] zoznam;
   
    class Vrchol implements Comparable<Vrchol> {

        public Vrchol(int id) {
            this.id = id;
            this.parent = this;
        }
        Vrchol parent;
        int id;
        int najkratsia_cesta = MAX;
        ArrayList<Hrana> susedia = new ArrayList<>();
        int pocet_n = 0;
        int cena_n = 0;

        @Override
        public int compareTo(Vrchol t1) {
          return najkratsia_cesta - t1.najkratsia_cesta;
              }
    }

    class Hrana implements Comparator<Hrana> {

        public Hrana(Vrchol ciel, int price, boolean b) {
          
            this.ciel = ciel;
            this.price = price;
            this.safety = b;
        }
        Vrchol ciel;
        int price;
        boolean safety;

        @Override
        public int compare(Hrana hrana1, Hrana hrana2) {
            return hrana1.price - hrana2.price;
        }
    }

   
    private static void najdi_cestu(Vrchol zac, Vrchol b) {
//        if (matrix[zac.id][b.id] != MAX) {
//            return matrix[zac.id][b.id]; } else {
        matrix[zac.id][zac.id] = 0;
        zac.najkratsia_cesta = 0;
        PriorityQueue<Vrchol> vertexQueue = new PriorityQueue<>();
        vertexQueue.add(zac);

        while (!vertexQueue.isEmpty()) {
            Vrchol u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Hrana e : u.susedia) {
                Vrchol v = e.ciel;
                int weight = e.price;

               int vzd = u.najkratsia_cesta + weight;
               //int vzd = matrix[zac.id][u.id] + weight;
                if (!e.safety) {
                    vzd += PLUS;
                }
                if (vzd < v.najkratsia_cesta) {
                //if (vzd < matrix[zac.id][v.id]) {
                   // matrix[zac.id][v.id] = vzd;
                    v.najkratsia_cesta = vzd;
                    v.parent = u;
                  v.pocet_n = u.pocet_n;
                   v.cena_n = u.cena_n;
                    if (!e.safety) {
                        v.pocet_n++;
                        v.cena_n+=e.price;
                    }
                   
                    vertexQueue.add(v);
                    vertexQueue.remove(u);
                }
            }
        }
    }

//    public static int zisti_cenu(Vrchol v, Vrchol u) {
//        int suma= 0;
//        for (Vrchol vertex = u; vertex != v; vertex = vertex.parent) {
//            //suma += vertex.najkratsia_cesta;
//            suma += matrix[v.id][vertex.id];
//        }
//        return suma;
//    }


    public static void main(String[] args) {
        Dijkstra di = new Dijkstra();
        Scanner s = new Scanner(System.in);
        n = s.nextInt(); //pocet krizovatiek
        m = s.nextInt(); //pocet ciest
        q = s.nextInt(); //pocet queries

        
        zoznam = new Vrchol[n];
        matrix = new int[n][n];
       
        for (int i = 0; i < n; i++) {
            zoznam[i] = di.new Vrchol(i);
           // Arrays.fill(matrix[i], MAX);
           }

        int a, b, c;
        String x;
        for (int i = 0; i < m; i++) {
            a = s.nextInt();
            b = s.nextInt();
            c = s.nextInt();
            x = s.next();
            Hrana h1, h2, h4, h3;
            if (x.equals("B")) {
                h1 = di.new Hrana(zoznam[b], c, true);
                h2 = di.new Hrana(zoznam[a], c, true);
               } else {
                h1 = di.new Hrana(zoznam[b], c, false);
                h2 = di.new Hrana(zoznam[a], c, false);
            }
            zoznam[a].susedia.add(h1);
            zoznam[b].susedia.add(h2);

        }

        for (int j = 0; j < q; j++) {
            a = s.nextInt();
            b = s.nextInt();
            najdi_cestu(zoznam[a], zoznam[b]);
          if (zoznam[b].najkratsia_cesta == MAX) {
         // if (matrix[a][b] == MAX){
                System.out.println("-1 " + "-1");
            } else {
              int nebe = zoznam[b].pocet_n;
              //int cena = matrix[a][b] - 10000*nebe;
              int cena = zoznam[b].najkratsia_cesta - 10000*nebe;
                System.out.println(zoznam[b].cena_n + " " + cena);
            }
        }
    }
}
