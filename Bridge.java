/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package bridge;

import java.util.Comparator;
import java.util.Scanner;

/**
 *
 * @author krista
 */
import java.util.ArrayList;
import java.util.PriorityQueue;

class Bridge {

    static int n, m, k;
    static Vertex[] students;
    static ArrayList<int[]> allEdges;
    static Vertex[] virtualVertex;
    static ArrayList<Vertex> list;
    static int id = 0;
    static boolean[] visited;
    static int t = 0;
    static PriorityQueue<int[]> Edges ;

    static class Vertex {

        ArrayList<Vertex> neighbours;
        boolean know;
        int time;
        int ID;
        int LL;
        boolean visited;
        boolean bridge;
        int num;

        Vertex() {
            this.neighbours = new ArrayList();
            this.know = false;
            this.time = -1;
            ID = id++;
        }
    }

    static void dfs(Vertex v, Vertex parent) {
        v.visited = true;
        v.time = t++;
        v.LL = v.time;
        if(v.know)v.num++;
        //int num_child = 0;
        for (int i = 0; i < v.neighbours.size(); i++) {
            Vertex w = v.neighbours.get(i);
            if (w == parent) {continue;}
            if (w.visited) {
                v.LL = Math.min(v.LL, w.time);
                continue;
            }
        //    num_child++;
            dfs(w, v);
            v.num += w.num;
            v.LL = Math.min(v.LL, w.LL);
            //System.out.println(v.ID + " " + w.ID  + " :" + w.num + v.num);
            if (w.LL >= v.time && (w.num == 0 || w.num == k)) {
                v.bridge = true;}
        }
        
    }

    public static void main(String[] args) {
        list = new ArrayList<>();
        Scanner s = new Scanner(System.in);
        n = s.nextInt(); //num students
        m = s.nextInt(); // num canals
        k = s.nextInt(); // num of knowers

        students = new Vertex[n + 1];
        for (int i = 0; i < n + 1; i++) {
            students[i] = new Vertex();
        }
        for (int i = 0; i < k; i++) {
            students[s.nextInt()].know = true;
        }

        int a, b;
        allEdges = new ArrayList<>();
        virtualVertex = new Vertex[m];

        for (int i = 0; i < m; i++) {
            a = s.nextInt();
            b = s.nextInt();
            virtualVertex[i] = new Vertex();
            virtualVertex[i].neighbours.add(students[a]);
            virtualVertex[i].neighbours.add(students[b]);
            students[a].neighbours.add(virtualVertex[i]);
            students[b].neighbours.add(virtualVertex[i]);
          
        }

        Edges = new PriorityQueue<>(m, new Comparator<int[]>() {
            public int compare(int[] student1, int[] student2) {
                return (student1[0] == student2[0]) ? (Integer.valueOf(student1[1]).compareTo(student2[1]))
                        : (student1[0] - student2[0]);
            }
        });

        
        dfs(students[1], null);
       
        for(Vertex v: virtualVertex){
            if (v.bridge){
                 int[] e = new int[2];
                e[0]=Math.min(v.neighbours.get(0).ID, v.neighbours.get(1).ID);
                e[1]=Math.max(v.neighbours.get(0).ID, v.neighbours.get(1).ID);
                Edges.add(e);
            }
        }
         int[] e = new int[2];
        System.out.println(Edges.size());
        while (!Edges.isEmpty()) {
            e = Edges.poll();
            System.out.println(e[0] + " " + e[1]);
        }
    }

}
