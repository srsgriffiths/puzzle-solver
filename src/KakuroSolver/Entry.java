/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KakuroSolver;

import java.util.HashSet;
/**
 *
 * @author Sam
 */
public class Entry extends Cell implements Comparable<Entry>{
    HashSet<Byte> possibles;
    int size;
    Header colhead, rowhead;
    
    public Entry(int index){
        this.index = index;
    }
    
    public void setPossibles(HashSet p){
        possibles = p;
        size = p.size();
    }
       
    
        
    @Override
    public int compareTo(Entry e){
        if(size == e.size){
            return 0;
        } else if (size > e.size){
            return 1;
        } else {
            return -1;
        }
    }
}
