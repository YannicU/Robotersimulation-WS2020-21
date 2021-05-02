import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Beschreiben Sie hier die Klasse Leinwand.
 *
 * @author Yannic
 * @version 30.04.2021
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

        fenster.add(zeichenflaeche);
        fenster.setVisible(true);
    }

    private void warten(int millisekunden) {
        try {
            Thread.sleep(millisekunden);
        } catch (Exception ignored) {
        }
    }

    private void zeichnen(ArrayList<Rechteck> hindernisse) {
        zeichenflaeche.repaintFiguren(hindernisse);
    }

    private class Zeichenflaeche extends JPanel {
        ArrayList<Rechteck> hindernisse = new ArrayList<>();

        @Override
        protected void paintComponent(Graphics g) {
            repaintFiguren(SPIELFELD.hindernislisteErzeugen());
            if (hindernisse.size() != 0) {
                for (Rechteck h : hindernisse) {
                    g.fillRect(h.getPositionX(), h.getPositionY(), h.getLaenge(), h.getBreite());
                    g.setColor(h.getColor());
//                g.fillOval(h.getPositionX() - 2, h.getPositionY() - 2, 4, 4);
//                g.drawString(h.getBezeichnung(), h.getPositionX(), h.getPositionY());
                }
            }
        }

        protected void repaintFiguren(ArrayList<Rechteck> figuren) {
            hindernisse = figuren;
            zeichenflaeche.revalidate();
            zeichenflaeche.repaint();
        }
    }
}
