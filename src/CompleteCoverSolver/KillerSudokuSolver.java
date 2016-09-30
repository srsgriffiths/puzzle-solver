/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompleteCoverSolver;


import java.util.ArrayList;
//import java.util.HashSet;

/**
 *
 * @author Sam
 */
public class KillerSudokuSolver extends SudokuSolver {
    //list of sum blocks, each int[0] is the sum total
    //int[1] onwards are the indicies of the cells in that block
    protected ArrayList<int[]> cellspec;
    //protected int nblocks;
    private NaturalSum sums;
    
    public KillerSudokuSolver(){};
    public KillerSudokuSolver(ArrayList<int[]> cellspec, int m, int n, int x, int y){
        this.cellspec = cellspec;
        this.m = m;
        this.n = n;
        this.x = x;
        this.y = y;
        size = x*y;
        dims = size*m*n;
        sums = new NaturalSum(size);
        spuzzle = new byte[dims];
    }
    
    @Override
    public void toCCProblem(){
        ArrayList<boolean[]> temparray = new ArrayList<>();
        
        for (int[] block : cellspec){
            System.out.println("NEXT CELL");
            int[] rowposs = getPossibleSumands(block[0], block.length-1);
            System.out.println("blocksize = " + (block.length - 1));
            System.out.println("n row poss = " + rowposs.length);
            for (int i = 1 ; i < block.length ; i++){
                for (int p : rowposs){
                    System.out.println();
                    System.out.println("index = " + block[i]);
                    System.out.println("possible = " + p);
                    temparray.add(makeCCProblemRow(block[i], p));
                }
            }
        }
        boolean[][] ret = new boolean[temparray.size()][dims*4];
        System.out.println("num of cc prob rows = " + temparray.size());
        for (int r = 0 ; r < temparray.size() ; r++){
            ret[r] = temparray.get(r);
        }
        
        ccproblem = new NodeMatrix(ret);
    }
    
    
    
    
    protected int[] getPossibleSumands(int sum, int blocksize){
        
        System.out.println("getting possible summands");
        if (sum < sums.getSum(blocksize) || sum > sums.getSum(size, size - blocksize)){
            System.out.println("sum not possible");
            return null;
        }
        ArrayList<Integer> possibles = new ArrayList<>();
        //System.out.print("possibles - ");
        for (int i = size ; i > 0 ; i--){
            
            //check if too big
            if (i + sums.getSum(blocksize-1) > sum){
                continue;
            } else if (i + sums.getSum(size, size - blocksize + 1) < sum) {    //check if too small
                continue;
            } else {
                possibles.add(i);
                //System.out.print(i);
            }
        }
        //System.out.println();
        //System.out.println("n possibles = "+ possibles.size());
        //here
        //HashSet<Integer> ret = new HashSet<>();
        //mask
        boolean[] mask = new boolean[possibles.size()];
        //mask for 
        boolean[] retmask = new boolean[possibles.size()];
        
        
        //for (int i = possibles.size() - 1 ; i > possibles.size() - nblocks - 1  ; i--){
        //    mask [i] = true;
        //}
        int[] pos = possibles.stream().mapToInt(i->i).toArray();
        int[] temp = new int[blocksize];
        
        assert(pos.length == possibles.size());
        
        while (true){
            mask = incrementMaskNTrue(mask, blocksize);
            //System.out.print("mask = ");
            //printMask(mask);
            //System.out.print("retmask = ");
            //printMask(retmask);
            if (mask == null){
                //System.out.println("checked all");
                break;
            }
            int c = 0;
            for (int i = 0 ; i < pos.length ; i++){
                if (mask[i]){
                    temp[c] = pos[i];
                    c++;
                }   
            }
            int tempsum = 0;
            for (int i : temp){
                tempsum += i;
            }
            if (tempsum == sum){
                retmask = updateRetMask(retmask, mask);
            }
            
        }
        
        int[] ret = new int[nTrue(retmask)];
        int c = 0;
        System.out.print("possibles - ");
        for (int i = 0 ; i < pos.length ; i ++){
            if(retmask[i]){
                ret[c] = pos[i];
                System.out.print(ret[c]);
                c++;
            }
            
        }
        System.out.println();
        System.out.println();
        
        return ret;//.stream().mapToInt(i->i).toArray();
    }
    
    public void printMask(boolean[] mask){
        if (mask == null){
            System.out.println("null");
            return;
        }
        for (boolean b : mask){
            System.out.print(b + " ");
        }
        System.out.println();
    }
    
    
    protected boolean[] updateRetMask(boolean[] retmask, boolean[] mask){
        assert (retmask.length == mask.length);
        
        for (int i = 0 ; i < retmask.length ; i++){
            if (mask[i]){
                if(!retmask[i]){
                    retmask[i] = true;
                }
            }
        }
        return retmask;
    }
    
    protected boolean[] incrementMaskNTrue(boolean[] mask, int n){
        do {
            mask = incrementMask(mask);
            if (mask == null){
                return null;
            }
        } while (nTrue(mask) != n);
        return mask;
    }
    
    protected int nTrue(boolean[] mask){
        int count = 0;
        for (boolean b : mask){
            if (b) count++;
        }
        return count;
    }
    
    protected boolean[] incrementMask(boolean[] mask){
        for (int i = mask.length - 1 ; i >= 0 ; i--){
            if (mask[i]){
                mask[i] = false;
            } else {
                mask[i] = true;
                return mask;
            }
        }
        return null;
    }
    
}
