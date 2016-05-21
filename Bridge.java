/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package bridges;

import java.util.Comparator;
import java.util.Scanner;

/**
 *
 * @author krista
 */
import java.util.ArrayList;
import java.util.PriorityQueue;

class Bridges {
static int n,m,k;
static Vertex[] students;
static ArrayList<int[]> allEdges;
static ArrayList<Vertex> list;
static int id=0;

static class Vertex{
     ArrayList<Vertex> neighbours; 
     boolean know;
     int color;
     int ID;
     
     Vertex(){
         this.neighbours = new ArrayList();
         this.know = false;
         this.color = 0;
         ID=id++;
     }         
 }
 
 static boolean findCritical(Vertex a, Vertex b){  
     boolean exist;
       a.neighbours.remove(b);
       b.neighbours.remove(a);
       a.color = 1; b.color = 2;
       exist = lookAt(a);
       if (exist){
           a.color = 2; b.color = 3;
           list.clear();
           /* for(Vertex v: students) {
              if(v.color != 2) v.color = 0;
           }//reset
           exist=lookAt(b);*/
           exist=lookAt3(b);
       }
       a.neighbours.add(b);
       b.neighbours.add(a);
    return !exist;
 }
 
 static boolean lookAt(Vertex v){ //find someone who know the answer
     if (v.know == true) return true;
     list.add(v);

     while (!list.isEmpty()) {
         Vertex u = list.remove(0);
         for (Vertex x : u.neighbours) { //only color = 0
             if (x.color == 0) {
                 if (x.know)return true;
                 list.add(x);
                 x.color = 1;
             }
             if (x.color == 2) {
                 return true;
             }
         }
     }
     return false;
 }
 
 static boolean lookAt3(Vertex v){ //find someone who know the answer
     if (v.know == true) return true;
     list.add(v);

     while (!list.isEmpty()) {
         Vertex u = list.remove(0);
         for (Vertex x : u.neighbours) { //only color = 0
             if (x.color == 0) {
                 if (x.know)return true;
                 list.add(x);
                 x.color = 3;
             }
             else if(x.color < 3) {
                 return true;
             }
         }
     }
     return false;
 }
 
     
    public static void main(String[] args) {
        list = new ArrayList<>();
        Scanner s = new Scanner(System.in);
        n = s.nextInt(); //num students
        m = s.nextInt(); // num canals
        k = s.nextInt(); // num of knowers
        
        students = new Vertex[n+1];
        for( int i = 0; i< n+1; i++) students[i]= new Vertex();
        for (int i = 0; i< k; i++){
            students[s.nextInt()].know = true;
        }
        
        int a,b;
        allEdges = new ArrayList<>();
        
        
        for (int i = 0; i < m; i++){
            a = s.nextInt();
            b = s.nextInt();
            students[a].neighbours.add(students[b]);
            students[b].neighbours.add(students[a]);
            int[] z= new int[2];
            if (a < b) {
                z[0]= a; z[1]=b;
            } else {
                z[0]= b; z[1]=a;
            }
            allEdges.add(z);
        }
        
       
        PriorityQueue<int[]> Edges = new PriorityQueue<>(m, new Comparator<int[]>() {
        public int compare(int[] student1, int[] student2) {
            return (student1[0] == student2[0]) ? (Integer.valueOf(student1[1]).compareTo(student2[1]))
                 : (student1[0]- student2[0]);
        }
    });
        
        for(int[] i: allEdges){
            if (findCritical(students[i[0]], students[i[1]])){
                Edges.add(i);
            }
            list.clear();
            for(Vertex v: students) v.color = 0;//reset
        }
        
        int[] e = new int[2];
        System.out.println(Edges.size());
        while(!Edges.isEmpty()){
            e= Edges.poll();
            System.out.println(e[0]+" "+e[1]);
        }
    }
    
}