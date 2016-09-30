/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompleteCoverSolver;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;


/**
 *
 * @author Sam
 */
public class TestRunner {
    
    public static void main(String[] args){
        Result result = JUnitCore.runClasses(CCSolverTest.class, NodeMatrixTest.class);
        result.getFailures().stream().forEach((failure) -> {
            System.out.println(failure.toString());
        });
        System.out.println(result.wasSuccessful());
    }
}
