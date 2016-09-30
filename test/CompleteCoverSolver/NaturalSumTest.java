/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompleteCoverSolver;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sam
 */
public class NaturalSumTest {
    
    public NaturalSumTest() {
    }

    @Test
    public void testGetSum_int() {
        NaturalSum ns = new NaturalSum(5);
        assertEquals(15, ns.getSum(5));
        assertEquals(10, ns.getSum(4));
        assertEquals(6, ns.getSum(3));
        assertEquals(3, ns.getSum(2));
        assertEquals(1, ns.getSum(1));
    }

    @Test
    public void testGetSum_int_int() {
        NaturalSum ns = new NaturalSum(5);
        assertEquals(5,ns.getSum(5,4));
        assertEquals(9, ns.getSum(5,3));
        assertEquals(12, ns.getSum(5,2));
        assertEquals(14, ns.getSum(5,1));
        assertEquals(5, ns.getSum(3,1));
    }
    
}
