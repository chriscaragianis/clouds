import processing.core.*;

public class CloudPApplet extends PApplet {

  private Clouds clouds;
  long startTime;
  long endTime;
  public void setup() {
    clouds = new Clouds();
    size(300, 300);
    frameRate(100);
    noStroke();
  }
  
  public void draw() {
      startTime = System.currentTimeMillis();
      for (int i = 0; i < 300; i++) {
        for (int j = 0; j < 300; j++) {
          int sum = clouds.histSum(i,j);    
          if (sum > 50) {
            fill(0);
            rect(i, j, 1, 1);
          }
          else {
            fill(5*sum);
            rect(i, j, 1, 1);
          } 
        } 
      }
      endTime = System.currentTimeMillis();
      System.out.println(endTime - startTime);
      //saveFrame("vid/####.TIF"); 
      clouds.update();
  }
  public static void main(String[] args) {
    PApplet.main(new String[] {"--present", "CloudPApplet"});
  }
}
