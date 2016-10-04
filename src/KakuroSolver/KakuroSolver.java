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
import java.util.Collections;

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
     * stores all non-empty headers in ?? order
     */
    ArrayList<Header> headerlist = new ArrayList<>();
    ArrayList<Header> headerrowlist = new ArrayList<>();
    ArrayList<Header> headercollist = new ArrayList<>();
    
    /**
     * stores indices of all entries with 2 or more possibles in ?? order
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
                if (hl[i].rowsum != 0){
                    headerrowlist.add(hl[i]);
                } else if (hl[i].colsum != 0){
                    headercollist.add(hl[i]);
                }
            }
        }
        
        setupKPuzzle();
    }
    
    public void directSolve(){
        
    }
    
    
    /**
     * sorts entrylist into ascending order of possibles.size
     * removes any with size 1
     */
    public void sortEntryList(){
        Collections.sort(entrylist);
        
        Iterator<Entry> itr = entrylist.iterator();
        while (itr.hasNext()){
            Entry e = itr.next();
            if (e.size == 1){
                itr.remove();
                //System.out.println("Entry Removed");
            } else {
                break;
            }
        }
    }
    
    public void sortHeaderRowList(){
        Collections.sort(headerrowlist, new HeaderRowComparator());
    }
    
    public void sortHeaderColList(){
        Collections.sort(headercollist, new HeaderColComparator());        
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
                h.rowpos = getPossibleSummands(h.rowsum, h.rowsize);
                //System.out.println("Header rowpos: " + Arrays.toString(getPossibleSummands(h.rowsum, h.rowsize).toArray()));
            }
            if (h.colsum != 0){
                h.colpos = getPossibleSummands(h.colsum, h.colsize);
                //System.out.println("Header colpos: " + Arrays.toString(getPossibleSummands(h.colsum, h.colsize).toArray()));
            }
        }
    }
    
    /**
     * updates Entry.possibles for each entry in a row (ie all have same rowhead)
     * 
     * @param h header of row to be updated
     */
    public void updateRowPossibles(Header h) {
        int i = h.index + 1;
        
        while(kpuzzle[i] instanceof Entry){
            Entry e = (Entry) kpuzzle[i];
            if (e.possibles == null){
                e.setPossibles(getIntersect(e.rowhead.rowpos, e.colhead.colpos));
            } else if (e.size == 1){
                i++;
                if (i == m*n){
                    return;
                }
                continue;
            } else { 
                e.possibles.removeAll(getNotIntersect(h.rowpos, e.possibles));
                e.size = e.possibles.size();
            }
            if (e.size == 1){
                //System.out.println("updateRowPossibles: complete entry");
                removeCompletedEntry(e);
                //entrylist.remove(e);
                if (h.rowsize != 0){
                    updateRowPossibles(h);
                }
                if (e.colhead.colsize != 0){
                    updateColPossibles(e.colhead);
                }
            }
            i++;
            if (i == m*n){
                return;
            }
        }
    }
    
    public void updateColPossibles(Header h){
        int i = h.index + n;
        
        if (i >= m*n){
            return;
        }
        
        while(kpuzzle[i] instanceof Entry){
            Entry e = (Entry) kpuzzle[i];
            if (e.possibles == null){
                e.setPossibles(getIntersect(e.rowhead.rowpos, e.colhead.colpos));
            } else if (e.size == 1) {
                i += n;
                if (i >= m*n){
                    return;
                } 
                continue;
            } else {
                e.possibles.removeAll(getNotIntersect(h.colpos, e.possibles));
                e.size = e.possibles.size();
            }
            if (e.size == 1){
                //System.out.println("updateColPossibles: complete entry");
                removeCompletedEntry(e);
                //entrylist.remove(e);
                if (h.colsize != 0){
                    updateColPossibles(h);
                }
                if (e.rowhead.rowsize != 0){
                    updateRowPossibles(e.rowhead);
                }
            }
            i += n;
            if (i >= m*n){
                return;
            }
        }
    }
    
    /**
     * updates entry's row/colhead possibles and size
     * does not remove e from entrylist;
     * @param e entry to be removed
     */
    public void removeCompletedEntry(Entry e){
        assert(e.size == 1);
        e.rowhead.rowsize--;
        e.colhead.colsize--;
        for (Byte b : e.possibles){
            e.rowhead.rowsum -= b;
            e.colhead.colsum -= b;
        }
        if (e.rowhead.rowpos.size() == e.rowhead.rowsize + 1){
            e.rowhead.rowpos.removeAll(e.possibles);
        } else {
            e.rowhead.rowpos = getPossibleSummands(e.rowhead.rowsum, e.rowhead.rowsize);
        }
        if (e.colhead.colpos.size() == e.colhead.colsize + 1){
            e.colhead.colpos.removeAll(e.possibles);
        } else {
            e.colhead.colpos = getPossibleSummands(e.colhead.colsum, e.colhead.colsize);
        }
            
        
        
        assert(e.possibles.size() == 1);
        
        
        
    }
    
    /**
     * calculates and sets possibles for each entry in kpuzzle
     */
    public void setEntryPossibles(){
        Iterator<Entry> itr = entrylist.iterator();
        while (itr.hasNext()){
            Entry e = itr.next();
            if (e.possibles == null){
                //System.out.println("setting e pos");
                e.setPossibles(getIntersect(e.colhead.colpos, e.rowhead.rowpos));
                if (e.size == 1){
                    //System.out.println("setEntryPossibles: complete entry");
                    removeCompletedEntry(e);
                    
                    if (e.rowhead.rowsize != 0){ 
                        updateRowPossibles(e.rowhead);
                    }
                    if (e.colhead.colsize != 0){
                        updateColPossibles(e.colhead);
                    }
                    //itr.remove();
                }
            }
        }
        
    }
       
    /**
     * returns intersection of two HashSet containing bytes
     * @param s1 Hashset to be compared
     * @param s2 Hashset to be compared
     * @return intersection of s1, s2
     */
    public HashSet getIntersect(HashSet<Byte> s1, HashSet<Byte> s2){
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
     * returns negation of intersect between s1 and s2
     * @param s1 HashSet to be compared
     * @param s2 HashSet to be compared
     * @return intersection of s1, s2
     */
    public HashSet getNotIntersect(HashSet<Byte> s1, HashSet<Byte> s2){
        HashSet<Byte> ret = new HashSet<>();
        for (Byte b : s1){
            if (!s2.contains(b)){
                ret.add(b);
            }
        }
        for (Byte b : s2){
            if (!s1.contains(b)){
                ret.add(b);
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
            System.out.println("sum not possible, sum = " + sum + "size = " + size);
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
    public HashSet getPossibleSummands(int sum, int size){
        //System.out.println("sum = " + sum + " size = " + size);
        if (!checkSumPossible(sum, size)){
            //System.out.print("sum not possible");
            return null;
        }
        HashSet<Byte> ret = new HashSet<>();
        if (size == 1){
            //System.out.println("size = 1");
            //HashSet r = new HashSet();
            ret.add((byte) sum);
            return ret;
        } else {
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
                if (size != 2 && sum == sums.getSum(size) + 1){
                    ret.remove((byte) (size));
                }
            } else if (sum > upper){
                //System.out.println("greater than upper");
                for (byte i = (byte) domain ; i > sum - upper ; i --){
                    ret.add(i);
                }
                //ret.add(sums.getSum(domain, sum - upper));
                if (size != 2 && sum == sums.getSum(domain, domain - size) - 1){
                    ret.remove((byte) (domain-size+1));
                }
            }
            if (size == 2 && sum % 2 == 0) {
                ret.remove((byte) (sum/2));
            }
        
        
        }
        return ret;
    }
    
    /**
     * returns rowsize of h if exists, or calculates and returns otherwise
     * 
     * sets up Entry rowheads
     * 
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
     * 
     * sets up Entry colheads
     * 
     * @param h Header for which colsize is to be returned
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
    
    public void printPuzzle(){
        
        for (int i = 0 ; i < kpuzzle.length ; i++){
            if (i%11 == 0){
                System.out.println();
            }
            if (kpuzzle[i] instanceof Entry){
                if (((Entry) kpuzzle[i]).size == 1){
                    System.out.print(((Entry) kpuzzle[i]).possibles.toArray()[0] + " ");
                } else {
                    System.out.print("_ ");
                }
                
            } else {
                System.out.print("H ");
            }
        }
        System.out.println();
    }
}