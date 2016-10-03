/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KakuroSolver;

import CompleteCoverSolver.NaturalSum;
import java.util.ArrayList;
import java.util.HashSet;
//import java.lang.Byte;
import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author Sam
 */
public class KakuroSolver {
    //puzzle dims m rows, n cols
    //domain is max number allowed
    int m, n, domain; 
    
    /**
     * stores initial puzzle, nulls for empty headers
     * Header instance for each header, Entry for each empty cell
     */
    Cell[] kpuzzle;
    
    /**
     * stores indices of all non-empty headers in ?? order
     */
    ArrayList<Header> headerlist = new ArrayList<>();
    
    /**
     * stores indices of all entries in ?? order
     */
    ArrayList<Entry> entrylist = new ArrayList<>();
    
    NaturalSum sums;
 
    
    /**
     * constructs kpuzzle from a list of headers left to right, top to bottom
     * in hl : 
     *  -empty header (0 row/colsum) indicates empty header
     *  -null for each empty cell
     * 
     * sets up indices of Cells
     * sets up entrylist, headerlist
     * 
     * @param hl list of headers
     * @param m number of rows
     * @param n number of columns
     * @param domain max number allowed (usually 9)
     */
    public KakuroSolver(Header[] hl, int m, int n, int domain){
        this.m = m;
        this.n = n;
        this.domain = domain;
        sums = new NaturalSum(domain);
        
        kpuzzle = new Cell[hl.length];
        
        for (int i = 0 ; i < hl.length ; i++){
            if (hl[i] == null){
                Entry e = new Entry(i);
                kpuzzle[i] = e;
                entrylist.add(e);
            } else if (hl[i].rowsum != 0 || hl[i].colsum != 0){
                headerlist.add(hl[i]);
                kpuzzle[i] = hl[i];
                kpuzzle[i].index = i;
                
            }
        }
        
        setupKPuzzle();
    }
    
    public void solve(){
        
    }
    
    /**
     * private method bundles setup methods for constructor
     */
    private void setupKPuzzle() {
        setupRowColSize();
        setHeaderPossibles();
        setEntryPossibles();
    }
    
    /**
     * sets up each header row/colsize 
     * 
     * sets up each cell rowhead and colhead
     */
    public void setupRowColSize(){
        for (Header h : headerlist){
            //if (h.rowsize != 0){
            h.rowsize = getRowSize(h);
            //}
            //if (h.colsize != 0){
            h.colsize = getColSize(h);
            //}
            
        }
    }
    
    /**
     * sets row/colpos in each header in order of headerlist
     */
    public void setHeaderPossibles(){
        for(Header h : headerlist){
            //System.out.println("Header index i = " + i);
            if (h.rowsum != 0){
                h.rowpos = getPossibles(h.rowsum, h.rowsize);
                //System.out.println("Header rowpos: " + Arrays.toString(getPossibles(h.rowsum, h.rowsize).toArray()));
            }
            if (h.colsum != 0){
                h.colpos = getPossibles(h.colsum, h.colsize);
                //System.out.println("Header colpos: " + Arrays.toString(getPossibles(h.colsum, h.colsize).toArray()));
            }
        }
    }
    
    /**
     * calculates and sets possibles for each entry in kpuzzle
     */
    public void setEntryPossibles(){
        for (Entry e : entrylist){
            //System.out.println("Entry index i = " + i);
            e.possibles = getIntersection(e.colhead.colpos, e.rowhead.rowpos);
            //System.out.println("Entry possibles: " + Arrays.toString(getIntersection(e.colhead.colpos, e.rowhead.rowpos).toArray()));
        }
    }
       
    /**
     * returns intersection of two HashSet containing bytes
     * @param s1 Hashset to be compared
     * @param s2 Hashset to be compared
     * @return intersection of s1, s2
     */
    public HashSet getIntersection(HashSet<Byte> s1, HashSet<Byte> s2){
        HashSet<Byte> ret = new HashSet<>();
        boolean s1bigger = (s1.size() > s2.size()) ? true : false;
        Iterator itr = (s1bigger) ? s2.iterator() : s1.iterator();
        HashSet biglist = (s1bigger) ? s1 : s2;
        
        while (itr.hasNext()){
            Byte temp = (Byte) itr.next();
            if (biglist.contains(temp)){
                ret.add(temp);
            }
        }        
        return ret;
    }
    
    /**
     * checks if sum in size cells is possible
     * @param sum
     * @param size 
     * @return true if possible, false otherwise
     */
    public boolean checkSumPossible(int sum, int size){
        if (sum < sums.getSum(size) || sum > sums.getSum(domain, domain - size)){
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * returns possible summands for a given sum in size entries
     * @param sum
     * @param size
     * @return int[] of possible summands
     */
    public HashSet getPossibles(int sum, int size){
        //System.out.println("sum = " + sum + " size = " + size);
        if (!checkSumPossible(sum, size)){
            //System.out.print("sum not possible");
            return null;
        }
        HashSet<Byte> ret = new HashSet<>();
        switch (size) {
            case 2:
                //System.out.println("case size = 2");
                for (byte i = 1 ; i < sum ; i++){
                    ret.add(i);
                    //System.out.println("adding " + i);
                }   //ret.add(sums.getSum(sum-1));
                if (sum%2 == 0){
                    ret.remove((byte) (sum/2));
                    assert(ret.size() % 2 == 0);
                }   
                break;
        //int[] temp = ret.parallelStream().mapToInt(i->i).toArray();
        //byte [] bret = new byte[temp.length];
        //for (int i = 0; i < temp.length ; i++){
        //    bret[i] = (byte) temp[i];
        //}
            case 1:
                //System.out.println("size = 1");
                HashSet r = new HashSet();
                r.add(sum);
                return r;
            default:
                int lower = sums.getSum(size-1) + domain; // lower bound where whole domain possible
                int upper = sums.getSum(domain, domain - (size - 1)) + 1; //upper bound
                if (sum >= lower && sum <= upper){
                    //System.out.println("between upper and lower");
                    for (byte i = 1 ; i <= domain ; i++){
                        ret.add(i);
                    }
                    //ret.add(sums.getSum(domain));
                } else if (sum < lower) {
                    //System.out.println("less than lower");
                    for (byte i = 1 ; i < domain - (lower - sum) +1 ; i++){
                        ret.add(i);
                    }
                    //ret.add(sums.getSum(domain - (lower-sum)));
                    if (sum == sums.getSum(size) + 1){
                        ret.remove((byte) (size));
                    }
                } else if (sum > upper){
                    //System.out.println("greater than upper");
                    for (byte i = (byte) domain ; i > sum - upper ; i --){
                        ret.add(i);
                    }
                    //ret.add(sums.getSum(domain, sum - upper));
                    if (sum == sums.getSum(domain, domain - size) - 1){
                        ret.remove((byte) (domain-size+1));
                    }
                }
        }
        
        
        return ret;
    }
    
    /**
     * returns rowsize of h if exists, or calculates and returns otherwise
     * 
     * sets up Entry rowheads
     * @param h Header for which rowsize is to be returned
     * @return rowsize of h
     */
    public int getRowSize(Header h){
        if (h.rowsize != -1){
            return h.rowsize;
        } else {
            int size = 0;
            int i = h.index + 1;
            while(i < m*n){
                if (kpuzzle[i] instanceof Entry){
                    ((Entry) kpuzzle[i]).rowhead = h;
                    size++;
                    i++;
                } else {
                    break;
                }
            }
            //if (size == 0){
            //    return -1;
            //} else {
            //    return size;
            //}
            return size;
        }
    }
    
    /**
     * returns colsize of h if exists, or calculates and returns otherwise
     * @param h Header for which colsize is to be returned
     * 
     * sets up Entry colheads
     * @return colsize of h
     */
    public int getColSize(Header h){
        if (h.colsize != -1){
            return h.colsize;
        } else {
            int size = 0;
            int i = h.index + n;
            //System.out.print("index = " + i);
            while(i < m*n){
                if (kpuzzle[i] instanceof Entry){
                    ((Entry) kpuzzle[i]).colhead = h;
                    size++;
                    i += n;
                } else {
                    break;
                }
            }
            //System.out.println(" size = " +size);
            //if (size == 0){
            //    return -1;
            //} else {
            //    return size;
            //}
            return size;
        }
    }
}