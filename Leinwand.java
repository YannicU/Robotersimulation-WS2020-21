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
    private int laenge;
    private int breite;
    private int verstaerkung = 100;
    private ArrayList<Rechteck> hindernisse;
    private JFrame fenster;
    private Zeichenflaeche zeichenflaeche = new Zeichenflaeche();

    Leinwand(String titel, int laenge, int breite) {
        this.laenge = laenge;
        this.breite = breite;
        fenster = new JFrame();
        fenster.setSize(this.laenge + verstaerkung, this.breite + verstaerkung);
        fenster.setTitle(titel);
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.setLocation(15, 15);
    }

    public void zeichnen(ArrayList<Rechteck> hindernisse) {
        fenster.setSize(laenge + verstaerkung + 1, breite + verstaerkung);
        this.hindernisse = hindernisse;
        zeichenflaeche.repaintFiguren(hindernisse);
        zeichenflaeche.setLocation(50, 50);
        fenster.add(zeichenflaeche);
        fenster.setSize(laenge + verstaerkung, breite + verstaerkung);
    }

    public void setLeinwandVisible(boolean visible) {
        fenster.setVisible(visible);
    }

    public void closeLeinwand() {
        fenster.dispose();
    }

    public void warten(int millisekunden) {
        try {
            Thread.sleep(millisekunden);
        } catch (Exception e) {
            System.out.println("Exception occured: " + e);
        }
    }

    public class Zeichenflaeche extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            g.drawRect(0, 0, laenge, breite);
            g.setColor(Color.black);
            for (Rechteck h : hindernisse) {
                g.setColor(h.getColor());
                g.fillRect(h.getPositionX(), h.getPositionY(), h.getLaenge(), h.getBreite());
                g.setColor(Color.black);
                g.drawRect(h.getPositionX(), h.getPositionY(), h.getLaenge(), h.getBreite());
                g.drawString(h.getBezeichnung(), h.getPositionX(), h.getPositionY());
            }
        }

        private void repaintFiguren(ArrayList<Rechteck> figuren) {
            for (Rechteck h : figuren) {
                zeichenflaeche.repaint(h.getPositionX(), h.getPositionY(), h.getLaenge(), h.getBreite());
            }
//            zeichenflaeche.repaint();
        }
    }

}