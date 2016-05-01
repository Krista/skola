/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package segmenttree;

import java.util.Scanner;

/**
 *
 * @author krista
 */
class SegmentTree {

    int[] Tree;
    int leafs;
    int number;
    public int[][] intervaly;

    public SegmentTree(int num) {
        leafs = 1;
        this.number = num;
        
        // najdeme najblizsiu mocninu dvojky
        while (leafs < num) {
            leafs = leafs * 2;}
     
        this.Tree = new int[leafs * 2];
        this.intervaly = new int[leafs*2][2];
        intervaly[1] = set_interval(1);
        
    }
    
    public int[] set_interval(int i){
        int[] pom = new int[2];
        int value;
        if (i >= leafs){
           // pom[0]= i - leafs + 1;pom[1]= i - leafs + 1;
           value = (Math.min(number, i - leafs + 1)); //ak poharov neni druha mocnina, tak sa oplati
          pom[0]= value;
          pom[1]= value;

            return pom;
        }
            if (intervaly[i*2][0]!=0 && intervaly[i*2 +1][0]!=0){
                pom[0]=intervaly[i*2][0];
                pom[1]=intervaly[i*2+1][1];
            }else{
                intervaly[i*2] = set_interval(i*2);
                intervaly[i*2 + 1] = set_interval(i*2 + 1);
                pom[0]= intervaly[i*2][0];
                pom[1]= intervaly[i*2+1][1];
            }
        return pom;
    }

    public int sum(int index) {
        int sum = 0;
        int i = leafs + index - 1; //pozicia hladaneho pohara v strome
        while (i > 0) {
            sum += Tree[i];
            i /= 2;
        }
        return sum;
    }
    
    public void update2(int root, int a, int b, int value){
        
        if (a == b) { //ak je interval jednotkovy
            Tree[leafs + a - 1]+= value;
            return;}

        if (intervaly[root][0]==a && intervaly[root][1]==b) { //ak interval pokryva vsetky pohariky patriace root
            Tree[root]+=value;
            return;}
        
        if (root >= leafs){//zaciatok intervalu je pravy! list
           Tree[root]+= value; 
           update2(1,a+1, b, value );
           return;}
    
        if (intervaly[root][0] <  a){ 
            if (intervaly[root*2][1] < a){ //lavy syn dedi lavy interval od otca
                update2(root*2+1,a,b, value);//ideme do praveho syna
                return;
            }}
        
        if(intervaly[root][0] != a){
            update2(root*2,a,b, value); //navstivime laveho syna
                return;}
        
        if (intervaly[root][1] > b){ //interval root zacina na a
            if(intervaly[root*2+1][0] > a){
            update2(root*2, a, b, value);//posuvame sa do laveho syna
            return;}
        }
        if(intervaly[root][1] < b){ //lavy podstrom uz cely patri do intervalu
            Tree[root]+= value;
            update2(1, intervaly[root][1]+1, b, value);
            return;
        }
        
        
        System.out.println("si povedala, ze sem sa nedostanes!");
        return;
    } 
  
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt(); //pocet poharov
        int q = s.nextInt(); //pocet queries
        SegmentTree stree = new SegmentTree(n);

        for (int i = 0; i < q; i++) {
            int op = s.nextInt();
            if (op == 1) {
                System.out.println(stree.sum(s.nextInt()));
            } else {
                stree.update2(1, s.nextInt(), s.nextInt(), 1);
            }
        }

    }

}
