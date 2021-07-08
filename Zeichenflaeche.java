import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Hier können verschiedene Grafiken und Figuren dargestellt werden.
 *
 * @author Vivian Bär, Yannic Yu
 * @version 07.07.2021
 */

public class Zeichenflaeche extends JPanel {
    private ArrayList<Rechteck> hindernisse;
    private Roboter roboter;
    private Punkt[] poi;

    /**
     * Konstruktor der Klasse Zeichenflaeche
     */
    public Zeichenflaeche() {
    }

    /**
     * Zeichnet die Figuren
     *
     * @param g Grafik die gezeichnet wird
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Leinwand.getHgFarbe());
        g.fillRect(0, 0, Spielfeld.getLaenge(), Spielfeld.getBreite());
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, Spielfeld.getLaenge(), Spielfeld.getBreite());
        if (hindernisse != null) {
            for (Rechteck h : hindernisse) {
                g.setColor(h.getColor());
                g.fillRect(h.getX(), h.getY(), h.getLaenge(), h.getBreite());
                g.setColor(Color.BLACK);
                g.drawRect(h.getX(), h.getY(), h.getLaenge(), h.getBreite());
//                g.drawString(h.getBezeichnung(), h.getX(), h.getY());
            }
        }
        if (poi != null) {
            for (Punkt p : poi) {
                g.setColor(Color.BLACK);
                g.drawLine(p.getX() + 2, p.getY() + 2, p.getX() - 2, p.getY() - 2);
                g.drawLine(p.getX() - 2, p.getY() + 2, p.getX() + 2, p.getY() - 2);
                g.drawOval(p.getX() - 3, p.getY() - 3, 6, 6);
            }
        }
        if (roboter != null) {
            g.setColor(roboter.getColor());
            g.fillOval(roboter.getX(), roboter.getY(), roboter.getDurchmesser(), roboter.getDurchmesser());
        }
    }

    public void repaintFiguren(ArrayList<Rechteck> figuren, Punkt[] poi, Roboter roboter) {
        this.hindernisse = figuren;
        this.roboter = roboter;
        this.poi = poi;
        repaint();
    }
}