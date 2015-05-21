
import java.util.*;

int[][] mtx = new int[600][600];
int[][] next = new int[600][600];
ArrayList<ArrayList<ArrayList<Integer>>> history = new ArrayList<ArrayList<ArrayList<Integer>>>();  //make history an array of lists ***HA NO SUCH THING*** 
boolean go = false;
Random rand = new Random();

void update() {
  for (int i = 0; i < 600; i++) {
    for (int j = 0; j < 600; j++) {
      if (nbrSum(i,j) < 2 || nbrSum(i,j) > 3) {
        next[i][j] = 0;
      }
      else if (nbrSum(i,j) == 3) {
        next[i][j] = 1;
      }
      else {
        next[i][j] = mtx[i][j];
      }
      for (int k = 0; k > history.get(i).get(j).size(); k++) {             //history is a sparse array
        history.get(i).get(j).set(k, history.get(i).get(j).get(k) + 1);
        if (history.get(i).get(j).get(k) > 99) {
	  history.get(i).get(j).remove(k);
        }	                                                   //do not need to copy every elt.
      }
      if (mtx[i][j] != next[i][j]) {
        history.get(i).get(j).add(1);
      }
    }
  }
  for (int i = 0; i < 600; i++) {
    for (int j = 0; j < 600; j++) {
      mtx[i][j] = next[i][j];
    }
  }
}
 
int nbrSum(int i, int j) {
  return mtx[(i+1)%600][j] + mtx[(i+599)%600][j] + mtx[(i+1)%600][(j+1)%600] + mtx[(i+599)%600][(j+599)%600] 
         + mtx[(i+1)%600][(j+599)%600] + mtx[(i+599)%600][(j+1)%600] + 
         mtx[i][(j+1)%600] + mtx[i][(j+599)%600];
}

void setup() {
  for (int i = 0; i < 600; i++) {
    history.add(new ArrayList<ArrayList<Integer>>());
    for (int j = 0; j < 600; j++) {
      mtx[i][j] = 0;
      next[i][j] = 0;
      history.get(i).add(new ArrayList<Integer>());
    }   
  }
  for (int i = 0; i < 600; i++) {
    for (int j = 0; j < 600; j++) {
      mtx[i][j] = rand.nextInt(2);
    }
  }
  size(600, 600);
  frameRate(100);
  mtx[10][12] = 1;
  mtx[10][10] = 1;
  mtx[11][11] = 1;
  mtx[9][11] = 1;
  mtx[10][11] = 1;
  noStroke();
 
}

void draw() {
  int count = 0;
  for (int i = 0; i < 600; i++) {
    for (int j = 0; j < 600; j++) { 
      fill(5*history.get(i).get(j).size());
      if (history.get(i).get(j).size() > 50) {
        fill(0);
      }
      rect(i, j, 1,1); 
    }
    count++;
  }
  
  count = 0;
  update();
}

void keyPressed() {
  if (go) {
    go = false;
  }
  else {
    go = true;
  }
}

void mouseClicked() {
  if (mouseX/3 < 200 && mouseY/3 < 200) {
    if (mtx[mouseX/3][mouseY/3] == 1) {
      mtx[mouseX/3][mouseY/3] = 0;
    }
    else {
      mtx[mouseX/3][mouseY/3] = 1;
    }
  }
}
