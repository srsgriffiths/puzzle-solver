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
public class Entry extends Cell{
    HashSet possibles;
    Header colhead, rowhead;
    
    public Entry(int index){
        this.index = index;
    }
}
