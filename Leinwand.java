import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Beschreiben Sie hier die Klasse Leinwand.
 *
 * @author Yannic
 * @version 11.05.2021
 */
public class Leinwand {
    private final Spielfeld SPIELFELD = new Spielfeld();
    private JFrame fenster;
    private Zeichenflaeche zeichenflaeche;


    Leinwand(int laenge, int breite) {
        fenster = new JFrame("Cooles Fenster 1");
        zeichenflaeche = new Zeichenflaeche();
        fenster.setLocation(1920 / 2 + laenge / 2, 1080 / 2 - breite / 2);
        fenster.setSize(laenge, breite);


        zeichenflaeche.repaintFiguren(SPIELFELD.hindernislisteErzeugen());
        fenster.add(zeichenflaeche);
        fenster.setVisible(true);
    }

    public static class Zeichenflaeche extends JPanel {
        ArrayList<Rechteck> hindernisse;

        private ArrayList<Rechteck> getHindernisse() {
            return hindernisse;
        }

        @Override
        protected void paintComponent(Graphics g) {
            for (Rechteck h : getHindernisse()) {
                g.fillRect(h.getPositionX(), h.getPositionY(), h.getLaenge(), h.getBreite());
                g.setColor(h.getColor());
                g.fillOval(h.getPositionX() - 2, h.getPositionY() - 2, 4, 4);
                g.drawString(h.getBezeichnung(), h.getPositionX(), h.getPositionY());
            }
        }

        public void repaintFiguren(ArrayList<Rechteck> figuren) {
            this.hindernisse = figuren;
            for (Rechteck h : getHindernisse()) {
                repaint(h.getPositionX(), h.getPositionY(), h.getLaenge(), h.getBreite());
            }
        }
    }
}
