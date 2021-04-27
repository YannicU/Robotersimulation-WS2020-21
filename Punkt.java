/**
 * Beschreiben Sie hier die Klasse Punkt.
 *
 * @author Yannic Yu
 * @version V1 25.04.2021
 */
public class Punkt {
    private int x = 0;
    private int y = 0;

    Punkt(){}
    Punkt (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX(){return this.x;}
    public int getY(){return this.y;}

    public double getAbstand(Punkt andererPunkt) {
        return Math.sqrt(Math.pow((andererPunkt.getX() - getX()), 2)
                + Math.pow((andererPunkt.getY() - getY()), 2));
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void bewegeUm(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
    public void bewegeUm(Punkt punkt){
        this.x = punkt.getX();
        this.y = punkt.getY();
    }

    public void ausgabeAttribute() {
        System.out.println("Koordinaten des Punktes: x = " + getX() + ", y = " + getY());
    }

}
