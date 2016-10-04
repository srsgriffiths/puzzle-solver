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
public class Header extends Cell {
    public int rowsum, colsum, rowsize = -1, colsize = -1;
    public HashSet<Byte> rowpos, colpos;
    
    public Header(int index){
        this.index = index;
    }
    
    public Header(int rowsum, int colsum){
        this.rowsum = rowsum;
        this.colsum = colsum;                
    }
}
