/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KakuroSolver;

import java.util.Comparator;

/**
 *
 * @author Sam
 */
public class HeaderRowComparator implements Comparator<Header>{
    @Override
    public int compare(Header h1, Header h2){
        int h1s = h1.rowpos.size();
        int h2s = h2.rowpos.size();

        if(h1s == h2s){
            return 0;
        } else if (h1s > h2s){
            return 1;
        } else {
            return -1;
        }
    }
}
