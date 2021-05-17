import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Beschreiben Sie hier die Klasse Leinwand.
 * <p>
 * Das meiste ist abgeschaut!
 *
 * @author Yannic
 * @version 17.05.2021
 */


public class Leinwand {
    private static Leinwand leinwand;
    private static int fensterlaenge;
    private static int fensterbreite;
    private JFrame fenster;
    private Zeichenflaeche zeichenflaeche;
    private Graphics2D grafik;
    private Color hintergrundFarbe;
    private Image leinwandBild;
    private ArrayList<Rechteck> hindernisse;

    Leinwand(String titel, int laenge, int breite, Color hintergrundFarbe) {
        this.hintergrundFarbe = hintergrundFarbe;

        fensterlaenge = laenge;
        fensterbreite = breite;

        fenster = new JFrame();
        zeichenflaeche = new Zeichenflaeche();

        fenster.setContentPane(zeichenflaeche);
        fenster.setTitle(titel);
        fenster.setLocation(15, 15);

        zeichenflaeche.setPreferredSize(new Dimension(laenge, breite));
        fenster.pack();

        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        hindernisse = new ArrayList<>();
    }

    // um mehrfach auf der selben leinwand zu zeichnen, ohne eine neue zu erschaffen.
    public static Leinwand getLeinwand() {
        if (leinwand == null) { // wenn noch keine existiert, soll eine erstellt werden.
            leinwand = new Leinwand("Cooles Fenster 1", fensterlaenge, fensterbreite, Color.white);
        }
        leinwand.setVisible(true);
        return leinwand;
    }

    public void setVisible(boolean visible) {
        if (grafik == null) {
            Dimension size = zeichenflaeche.getSize();
            leinwandBild = zeichenflaeche.createImage(size.width, size.height);
            grafik = (Graphics2D) leinwandBild.getGraphics();
        }
        fenster.setVisible(visible);
    }

    public void draw(Roboter roboter){
        repaintComponent(roboter);
    }

    public void draw(ArrayList<Rechteck> hindernisse) {
        repaintComponent(hindernisse);
    }

    private void repaintComponent(Roboter r){
        grafik.setColor(r.getColor());
        grafik.drawOval(r.getX(), r.getY(), r.getDurchmesser(), r.getDurchmesser());
        grafik.fillOval(r.getX(), r.getY(), r.getDurchmesser(), r.getDurchmesser());
        grafik.setColor(Color.black);
        grafik.drawString(r.getBezeichnung(), r.getX(), r.getY());
    }

    private void repaintComponent(ArrayList<Rechteck> hindernisse) {
        leeren();
        for (Rechteck h : hindernisse) {
            grafik.setColor(h.getColor());
            grafik.drawRect(h.getX(), h.getY(), h.getLaenge(), h.getBreite());
            grafik.setColor(Color.black);
            grafik.drawString(h.getBezeichnung(), h.getX(), h.getY());
        }
        zeichenflaeche.repaint();
    }

    private void leeren() {
        Dimension size = zeichenflaeche.getSize();
        grafik.setColor(hintergrundFarbe);
        grafik.fillRect(0, 0, size.width, size.height);
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
            g.drawImage(leinwandBild, 0, 0, null);
        }
    }
}