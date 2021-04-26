/**
 * Beschreiben Sie hier die Klasse Punkt.
 * 
 * @author Yannic Yu
 * @version V1 25.04.2021
 */
public class Punkt 
{
    private int x = 0;
    private int y = 0;

    Punkt(){}

    Punkt (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX(){return x;}
    public int getY(){return y;}

    public void setXY(int neuX, int neuY){
        this.x = neuX;
        this.y = neuY;
    }
    public String ausgabeAttribute() {
       return "x: " + getX() + " | y: " + getY();
    }
    public void bewegeUm(int dx, int dy){
        this.x += dx;
        this.y += dy;
    }

    public double getAbstand(Punkt andererPunkt) {
        return Math.sqrt(Math.pow((andererPunkt.getX() - getX()), 2)
                + Math.pow((andererPunkt.getY() - getY()), 2));
    }
}
