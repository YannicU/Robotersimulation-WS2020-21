import java.util.*;
import java.awt.Color;

/**
 * Beschreiben Sie hier die Klasse Spielfeld.
 *
 * @author Yannic Yu
 * @version 30.04.2021
 */

public class Spielfeld {
    private static final Random ZUFALLSGENERATOR = new Random();
    private static Punkt roboterPunkt = new Punkt();
    private static Scanner scanner = new Scanner(System.in);
    private static int laenge = 1000;
    private static int breite = 1000;
    private static int x;
    private static int y;
    private static Punkt[] userPoi;
    private static Punkt[] poiSortiert;

    Spielfeld() {
        Roboter roboter = new Roboter();
    }

    public static void main(String[] args) {
        Roboter roboter = new Roboter();
        System.out.println("Hey, was willst du machen?" +
                "\n  (a) Points-of-Interest abfahren" +
                "\n  (b) Hindernisse umfahren" +
                "\n  (c) Stichwörter erkennen und antworten");
        String userEingabe = "";
        while (!userEingabe.equalsIgnoreCase("ende")) {
            System.out.print("(a/b/c) > ");
            userEingabe = scanner.nextLine();
            if (userEingabe.equalsIgnoreCase("a")) {
                poiAbfahren();
            } else if (userEingabe.equalsIgnoreCase("b")) {
                System.out.println("...Ist noch in bearbeitung");
            } else if (userEingabe.equalsIgnoreCase("c")) {
                roboter.spracherkennung();
            }
        }
    }

    /**
     * Methode punktEingabe
     * <p>
     * Die zuvor angegebene Anzahl der Punkte wird als obere Grenze des loops verwendet.
     * In dem loop gibt der Nutzer die x und y Werte der Punkte an.
     * Diese werden mithilfe einer anderen Methode ("checkGrenzen") auf ihre Grenzen geprüft.
     * Sollten die Grenzen nicht stimmen, muss der Nutzer die Werte für den aktuellen Punkt erneut eingeben.
     */
    private static Punkt[] punktEingabe() {
        System.out.print("Anzahl der Punkte: ");
        int punktAnzahl = scanner.nextInt(); // Nutzer gibt an wie viele Punkte er erstellen möchte
        userPoi = new Punkt[punktAnzahl]; // werden hier gespeichert!

        for (int i = 0; i < punktAnzahl; i++) {
            System.out.println("Koordinaten von P" + (i + 1) + ": ");
            userPunktEingabe();
            while (!checkGrenzen(x, y)) { // solange checkGrenzen false gibt, müssen werte erneut eingegeben werden
                userPunktEingabe();
            }
            Punkt neuerPunkt = new Punkt(x, y); // neuer Punkt wird erstellt, der die eingegebenen Werte besitzt

            System.out.println("==> P" + (i + 1) + "(" + neuerPunkt.getX() + ", " + neuerPunkt.getY() + ")");

            userPoi[i] = neuerPunkt; // neuer Punkt wird zum Array hinzugefügt
        }
        return userPoi;
    }

    private static void userPunktEingabe() {
        System.out.print("x: ");
        x = scanner.nextInt();
        System.out.print("y: ");
        y = scanner.nextInt();
    }

    private static boolean checkGrenzen(int x, int y) {
        if (x < 0 || x > breite) {
            System.out.println("x-Wert liegt ausehalb des engegebenen Bereichs (0 <= x <= " + breite + ")");
            return false;
        }
        if (y < 0 || y > laenge) {
            System.out.println("y-Wert liegt ausehalb des angegebenen Bereichs (0 <= y <= " + laenge + ")");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Methode poiSortieren
     * <p>
     * Hier werden die unsortierten Punkte ihrem Abstand nach sortiert und einem neuen Array (this.poiSortiert)
     * hinzugefügt um die sortierten Punkte zu speichern
     * <p>
     * keine Ahnung was eine HashMap, TreeMap ist und wieso man genau die verwenden muss, aber im Grunde
     * werden in der HashMap poiUnsortiert die unsortierten Punkte mit ihren Abstandswerten zum Roboterpunkt
     * gespeichert.
     * <p>
     * Allgemein hat eine Map keys und values. Jedem key ist ein value zugeordnet und man kann über die keys
     * die values bekommen und andersrum. Hier werden die Punkte aus einem Array Punkte[] als Keys verwendet
     * und denen werden ihre Abstandswerte (zu dem aktuellen Roboterpunkt) zugeordnet.
     * <p>
     * Dann wird ein neuer Punkt erstellt, der die Werte von der HashMap hat (siehe Klasse Punkt) und dann wird
     * der als Grundlage genommen um die die HashMap nach ihren values (das sind die Abstandswerte) zu
     * sortieren.. irgendwie so.
     * Diese sortierten values mit ihren keys werden dann alle in poiSortiert gespeichert
     * <p>
     * Dann werden die values aus der TreeMap poiSortiert in dem Array poiSortiert gespeichert, weil ich nicht
     * wusste wie man mit der TreeMap weiter gehen sollte.
     */
    private static void poiSortieren(Punkt[] poi) {
        HashMap<Punkt, Double> poiUnsortiert = new HashMap<>();
        Punkt basisZumVergleichen = new Punkt(poiUnsortiert);
        TreeMap<Punkt, Double> poiSortiertTM = new TreeMap<>(basisZumVergleichen);
        poiSortiert = new Punkt[userPoi.length];

        for (Punkt punkt : poi) {
            poiUnsortiert.put(punkt, roboterPunkt.getAbstand(punkt));
        }
        poiSortiertTM.putAll(poiUnsortiert);

        int i = 0; // speichert die sortierten Punkte im Array this.poiSortiert
        for (Punkt punkt : poiSortiertTM.keySet()) {
            poiSortiert[i] = punkt;
            i++;
        }
    }

    /**
     * Methode poiAbfahren
     * <p>
     * es wird ein neuer Punkt naechsterPunkt erzeugt, der angibt, welchen Punkt der Roboter als nächstes anfahren kann.
     * Dieser wird nur dann erzeugt, wenn es nicht der Roboterpunkt selbst ist und er nicht schon vom Roboter angefahren
     * wurde.
     * Das wird geprüft, indem der erzeugten naechstenPunkt in dem leeren Array poiAbgefahren gespeichert wird und
     * zukünftig ausgeschlossen wird.
     * Sobald ein naechsterPunkt erzeugt wurde, wird von ihm der Verschiebungsvektor ausgerechnet und der Roboterpunkt
     * um diesen verschoben.
     * Dann werden die Punkte aus dem Array poiSortiert erneut mit den neuen Koordinaten des Roboterpunktes, nach ihrem
     * sortiert kürzesten Abstand sortiert.
     */
    public static void poiAbfahren() {
        poiSortieren(punktEingabe());
        Punkt[] poiAbgefahren = new Punkt[userPoi.length];
        Punkt naechsterPunkt = new Punkt();
        for (int i = 0; i < poiSortiert.length; i++) {
            boolean neuerPunkt = false;
            for (Punkt punkt : poiSortiert) {
                if (!poiSortiert[0].equals(new Punkt(0, 0)) && !neuerPunkt && !Arrays.asList(poiAbgefahren).contains(punkt)) {
                    naechsterPunkt.setXY(punkt.getX(), punkt.getY());
                    poiAbgefahren[i] = punkt;
                    neuerPunkt = true;
                } else if (!neuerPunkt && !Arrays.asList(poiAbgefahren).contains(punkt)) {
                    naechsterPunkt.setXY(poiSortiert[1].getX(), poiSortiert[1].getX());
                    poiAbgefahren[i] = poiSortiert[1];
                }
            }
            // ab hier wird viel an die Konsole geschrieben und der Roboter zum nächsten Punkt bewegt
            System.out.println("Ausgangspunkt: (" + roboterPunkt.getX() + ", " + roboterPunkt.getY() + ")");
            for (Punkt punkte : poiSortiert) {
                System.out.println("Abstand zu Punkt" + (getIndex(userPoi, punkte) + 1) + " (" + punkte.getX() + ", " +
                        punkte.getY() + ") = " + roboterPunkt.getAbstand(punkte));
            }

            int dx = naechsterPunkt.getX() - roboterPunkt.getX();
            int dy = naechsterPunkt.getY() - roboterPunkt.getY();

            System.out.println("---\nnächster Punkt = Punkt (" + naechsterPunkt.getX() + ", " + naechsterPunkt.getY() +
                    ")\nVerschiebungsvektor: (" + dx + ", " + dy + ") = " +
                    roboterPunkt.getAbstand(naechsterPunkt));

            roboterPunkt.bewegeUm(dx, dy);
            poiSortieren(poiSortiert);

            System.out.println("...Neuer Ausganspunkt: (" + roboterPunkt.getX() + ", " + roboterPunkt.getY() + ")\n----------");
        }
    }

    private static <T> int getIndex(T[] arr, T val) { // vorsicht ist geklaut! ist für den code aber auch nicht wichtig.
        return Arrays.asList(arr).indexOf(val);
    }

    /**
     * Methode hindernislisteErzeugen
     * <p>
     * Hier kann der Nutzer angeben wie viele Hindernisse er auf dem Spielfeld haben möchte.
     * Dann werden für die genannte Anzahl, unter bestimmten bestimmungen, Hindernisse ersellt und in einer
     * ArrayList gespeichert und am ende zurückgegeben.
     * Die Hindernisse sind Rechtecke, die vollständig auf dem Spielfeld sein müssen und dieses nicht verlassen dürfen.
     */
    public ArrayList<Rechteck> hindernislisteErzeugen() {
        System.out.print("Anzahl der Hindernisse: ");
        int hindernisseAnzahl = scanner.nextInt(); // Nutzereingabe um Anzahl der Hindernisse festzulegen
        ArrayList<Rechteck> hindernisliste = new ArrayList<>(); // hier werden die Hindernisse gespeichert werden

        float r = ZUFALLSGENERATOR.nextFloat(); // zufällige r, g, b Werte für die Farbe
        float g = ZUFALLSGENERATOR.nextFloat();
        float b = ZUFALLSGENERATOR.nextFloat();

        // hier wird der zuerst ein Rechteck der Liste hinzugefügt, damit beim while loop
        // bestimmt werden kann, ob sich das neue Rechteck mit den in der Liste gespeicherten Rechteck
        // überschneidet.
        Punkt zufallsPunkt = new Punkt(zufallszahl(1, (laenge - 1)), zufallszahl(1, (breite - 1)));
        Rechteck zufallsRechteck = new Rechteck(zufallsPunkt, zufallszahl(1, 100), zufallszahl(1, 100),
                "Rechteck " + 1, new Color(r, g, b));
        hindernisliste.add(zufallsRechteck);

        // hier wird geprüft ob sich das Rechteck innerhalb des Spielfeldes befindet, sich nicht mit den forhandenen
        // Rechtecken überlappt und ob es neu ist. Falls diese Bedingungen erfüllt sind, wird ein neues Rechteck erstellt
        // Das wird entweder so oft wiederhohlt, bis die Anzahl an gewünschten Hindernissen erreicht ist,
        // oder sich die Rechtecke 50 mal hintereinander überlappt haben.
        int i = 2;
        int ueberlappungZaehler = 0;
        while (hindernisliste.size() < hindernisseAnzahl && ueberlappungZaehler < 50) { // Hier werden zufällige Hindernisse erzeugt.
            zufallsPunkt = new Punkt(zufallszahl(1, (laenge - 1)), zufallszahl(1, (breite - 1)));
            zufallsRechteck = new Rechteck(zufallsPunkt, zufallszahl(1, 100), zufallszahl(1, 100),
                    "Rechteck " + i, new Color(r, g, b));
            // wrid nur dann ausgeführt, wenn das zufallsRechteck die Grenzen des Spielfeldes nicht überschreitet/berührt
            if ((laenge - zufallsPunkt.getX()) < zufallsRechteck.getLaenge() || (breite - zufallsPunkt.getY()) < zufallsRechteck.getBreite()) {
                for (int j = 0; j < hindernisliste.size(); j++) {
                    // überprüft aktuelles Rechteck mit vorhandenen Rechtecken, ob diese sich überlappen.
                    // der Überlappungszähler erhöht sich jedes mal, wenn sich kein neues Rechteck, aufgrund von
                    // Überlappung erzeugen lies, oder das Rechteck bereits existiert.
                    if (!hindernisliste.get(j).ueberlappt(zufallsRechteck) && !hindernisliste.contains(zufallsRechteck)) {
                        hindernisliste.add(zufallsRechteck);
                        i++;
                        ueberlappungZaehler = 0;
                    }
                    ueberlappungZaehler++;
                }
            }
        }
        return hindernisliste;
    }

    private int zufallszahl(int von, int bis) { // generiert zufallszahl, in vorgegebenen Grenzen
        return ZUFALLSGENERATOR.nextInt(bis + 1 - von) + von;
    }

}