package edu.neu.coe.info6205.union_find;

import java.util.Random;

public class UF_Client {
    public static int count(int n) {
        UF_HWQUPC UF= new UF_HWQUPC(n);
        Random random= new Random();
        int connections =0;
        while(UF.components() > 1){
            int i= random.nextInt(n);
            int j = random.nextInt(n);
            connections++;
            if(!UF.isConnected(i, j)){
                UF.union(i, j);
            }
        }
        return connections;
    }

    public static void main(String args[]){
        StringBuilder N= new StringBuilder();
        StringBuilder M= new StringBuilder();
        for(int i=100; i<= 500000; i=2*i ){
            double mean=0.00;
            for(int j=0; j< 100; j++){
                mean+= UF_Client.count(i);
            }
            System.out.println("Number of objects N="+i
                    +"\t Number of pairs M="+ mean/100);
        }

        System.out.println(N.toString());
        System.out.println(M.toString());
    }
}
