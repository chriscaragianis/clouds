import java.util.*;

int[][] mtx = new int[600][600];
int[][] next = new int[600][600];
int[][][] history = new int[600][600][100]; 
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

int histSum(int i, int j) {
  int sum = 0;
  for (int k = 0; k < 100; k++) {
    sum += history[i][j][k];
  }
  return sum;
}


void setup() {
  for (int i = 0; i < 600; i++) {
    for (int j = 0; j < 600; j++) {
      mtx[i][j] = 0;
      next[i][j] = 0;
      for (int k = 0; k < 100; k++) {
        history[i][j][k] = 0;
      }
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
  PImage img = createImage(600,600,ALPHA);
}

void draw() {
  int count = 0;
  int iter = 0;
  for (int i = 0; i < 600; i++) {
    for (int j = 0; j < 600; j++) {
      if (histSum(i,j > 50)) {
        img.pixels[count] = color(0);
      }
      else { 
        img.pixels[count] = color(5*histSum(i,j));
      }
      count++;
    }
  }
  count = 0;
  update();
  iter++;
  if (iter = 300) {
    img.save("~/Dropbox/research/corrina/heatmap/v2/outputimage.tif");
  }
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
