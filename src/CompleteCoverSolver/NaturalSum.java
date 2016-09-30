/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompleteCoverSolver;

/**
 *
 * @author Sam
 */
public class NaturalSum {
    private final int size;
    private final int[] sums;
    
    public NaturalSum(int size){
        this.size = size;
        sums = new int[size];
        
        for (int i = 1 ; i <= size ; i++){
            sums[i-1] = (i*(i+1))/2;
        }
    }
    
    public int getSum(int i){
        return sums[i-1];
    }
    
    //return series sum from b to a
    public int getSum(int a, int b){
        return sums[a-1] - sums[b-1];
    }
    
}
