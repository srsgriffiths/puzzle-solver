
package CompleteCoverSolver;

/**
 * represents first line of nodes in a doubly linked array used in manipulating columns in doubly linked array
 * @author Sam
 */
public class ColumnHeader extends Node {
    public int index;


    public ColumnHeader(int index) {
        this.index = index;
        size = 0;
    }
    
}
