import java.util.*;

public class Clouds {
  int[][] mtx;
  int[][] next;
  int[][][] history;   
  Random rand;

  /**
   *Default constructor sets up 300 by 300 automaton and history
   */
  public Clouds() {
    mtx = new int[300][300];
    next = new int[300][300];
    history = new int[300][300][100];
    rand = new Random();
    for (int i = 0; i < 300; i++) {
      for (int j = 0; j < 300; j++) {
        mtx[i][j] = rand.nextInt(2);
      }
    }
    for (int i = 0; i < 300; i++) {
      for (int j = 0; j < 300; j++) {
        mtx[i][j] = rand.nextInt(2);
      }
    }
  }  
  
  /**
   *Updates the CA to the next generation and stores the history
   */
  public void update() {
    int[] slices = new int[300];
    //first slice of slices
    for (int i = 0; i < 300; i++) {
      slices[i] = mtx[299][i] + mtx[0][i] + mtx[1][i];
    }
     //first sum 
     int sum = slices[299] + slices[0] + slices[1];
     //let's roll
     for (int i = 0; i < 300; i++) {
       for (int j = 0; j < 300; j++) {
         //make next gen
         if (sum > 4 || sum < 3) {
           next[i][j] = 0;
         }
         if (sum == 3) {
           next[i][j] = 1;
         }
         if (sum == 4) {
           next[i][j] = mtx[i][j];
         }
         //setup next sum
         sum += (slices[(j + 2) % 300] - slices[(j+299) % 300]);
         //get old bits ready for the next slice of slices 
         if (j > 0) {
           slices[(j + 299) % 300] = mtx[i][(j+299)%300] + mtx[(i+1)%300][(j+299)%300]
             + mtx[(i+2)%300][(j+299)%300]; 
         }
         for (int k = 99; k > 0; k--) {             
          history[i][j][k] = history[i][j][k-1];
         }
         if (mtx[i][j] != next[i][j]) {
           history[i][j][0] = 1;
         }
         else {
           history[i][j][0] = 0;
         }
       }
       //setup next slice of slices
       slices[299] = mtx[i][299] + mtx[(i+1) % 300][299] + mtx[(i+2) % 300][299];
       //setup next sum
       sum = slices[299] + slices[0] + slices[1];
    }
    //Update to next generation
    for (int i = 0; i < 300; i++) {
       for (int j = 0; j < 300; j++) {
         mtx[i][j] = next[i][j];
       }
    }
     
  } 

  public int histSum(int i, int j) {
    int sum = 0;
    for (int k = 0; k < 100; k++) {
      sum += history[i][j][k];
    }
    return sum;
  }
}
