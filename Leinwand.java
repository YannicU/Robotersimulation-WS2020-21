import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Beschreiben Sie hier die Klasse Leinwand.
 *
 * @author Yannic
 * @version 23.05.2021
 */

public class Leinwand {
    private final JFrame FENSTER;
    private final Zeichenflaeche ZEICHENFLAECHE;

    Leinwand(String titel, int laenge, int breite) {
        FENSTER = new JFrame();
        ZEICHENFLAECHE = new Zeichenflaeche();

        FENSTER.setContentPane(ZEICHENFLAECHE);
        FENSTER.setTitle(titel);
        FENSTER.setLocation(-1920 / 2, 15);
        ZEICHENFLAECHE.setPreferredSize(new Dimension(laenge, breite));
        FENSTER.setSize(laenge + 275, breite + 300);
//        FENSTER.pack();
        FENSTER.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setVisible(boolean visible) {
        FENSTER.setVisible(visible);
    }

    public void zeichnen(ArrayList<Rechteck> hindernisse, Roboter roboter) {
        ZEICHENFLAECHE.repaintFiguren(hindernisse, roboter);
    }

    public void warten(int millisekunden) {
        try {
            Thread.sleep(millisekunden);
        } catch (Exception e) {
            System.out.println("Exception occured: " + e);
        }
    }

    public void close() {
        FENSTER.dispose();
    }

    public static class Zeichenflaeche extends JPanel {
        private ArrayList<Rechteck> hindernisse;
        private Roboter roboter;

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g); // zeichnet neue Komponeten und löscht die alten
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, 600, 600);
            for (Rechteck h : hindernisse) {
                g.setColor(h.getColor());
                g.fillRect(h.getX(), h.getY(), h.getLaenge(), h.getBreite());
                g.setColor(Color.BLACK);
                g.drawRect(h.getX(), h.getY(), h.getLaenge(), h.getBreite());
                g.drawString(h.getBezeichnung(), h.getX(), h.getY());
            }
            g.setColor(roboter.getColor());
            g.fillOval(roboter.getX(), roboter.getY(), roboter.getDurchmesser(), roboter.getDurchmesser());
        }

        public void repaintFiguren(ArrayList<Rechteck> figuren, Roboter roboter) {
            this.hindernisse = figuren;
            this.roboter = roboter;
            repaint();
        }
    }
}