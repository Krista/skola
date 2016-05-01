package dijkstra;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

class Dijkstra {

   static long MAX = Long.MAX_VALUE;
   static long PLUS = (long) Math.pow(2.0, 32.0);

    static int n, m, q;
    static Vrchol[] zoznam;

    class Vrchol implements Comparable<Vrchol> {

        public Vrchol(int id) {
            this.id = id;
        }
        int id;
        long najkratsia_cesta = MAX;
        ArrayList<Hrana> susedia = new ArrayList<>();

        @Override
        public int compareTo(Vrchol t1) {
            return Long.compare(this.najkratsia_cesta,t1.najkratsia_cesta);
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
        zac.najkratsia_cesta = 0;
        PriorityQueue<Vrchol> vertexQueue = new PriorityQueue<>();
        vertexQueue.add(zac);

        while (!vertexQueue.isEmpty()) {
            Vrchol u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Hrana e : u.susedia) {
                Vrchol v = e.ciel;
                long weight = e.price;
                if (!e.safety) {
                    weight <<= 32;
                }
                long vzd = u.najkratsia_cesta + weight;
                if (vzd < v.najkratsia_cesta) {
                    v.najkratsia_cesta = vzd;
                    vertexQueue.add(v);
                    vertexQueue.remove(u);
                }
            }
        }
    }

    public static void main(String[] args) {
        Dijkstra di = new Dijkstra();
        Scanner s = new Scanner(System.in);
        n = s.nextInt(); //pocet krizovatiek
        m = s.nextInt(); //pocet ciest
        q = s.nextInt(); //pocet queries

        zoznam = new Vrchol[n];
        for (int i = 0; i < n; i++) {
            zoznam[i] = di.new Vrchol(i);
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
            for (int w = 0; w < n; w++) {
                zoznam[w].najkratsia_cesta = MAX;
            }
            najdi_cestu(zoznam[a], zoznam[b]);
            if (zoznam[b].najkratsia_cesta == MAX) {
                System.out.println("-1 " + "-1");
            } else {
                long sa = zoznam[b].najkratsia_cesta % PLUS;
                long ra = zoznam[b].najkratsia_cesta >> 32;
                System.out.println( ra + " " +  (sa+ra));
            }
        }
    }
}
