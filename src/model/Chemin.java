package model;

import controller.Configuration;

import java.awt.Point;
import java.util.ArrayList;

public class Chemin {
    private int source;
    private int destination;
    private ArrayList<Composant> composants;

    public Chemin(int source, int destination) {
        this.source = source;
        this.destination = destination;
        this.composants = new ArrayList<>();
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public ArrayList<Composant> getComposants() {
        return composants;
    }

    public void addComposant(Composant composant) {
        this.composants.add(composant);
    }

    public void moveComposants(Configuration configuration, Point vitesse) {
        Usine usineSource = null;
        Usine usineDestination = null;

        for (Usine usine : configuration.getUsines()) {
            if (usine.getId() == this.source) {
                usineSource = usine;
            }

            if (usine.getId() == this.destination) {
                usineDestination = usine;
            }
        }

        if (usineSource != null && usineDestination != null) {
            // D�placer les composants � la destination
            for (Composant composant : this.composants) {
                // Point de d�part
                double x1 = usineSource.getPosition().getX();
                double y1 = usineSource.getPosition().getY();
                // Point de destination
                double x2 = usineDestination.getPosition().getX();
                double y2 = usineDestination.getPosition().getY();

                double m = (y2-y1)/(x2-x1); // Pente de la fonction affine
                double b = y2 - m * x2; // Ordonn�e � l'origine

                double x = composant.getPosition().getX(); // La position x dans laquelle j'aimerais que mon composant se trouve

                if (x2 > x1) {
                    x += vitesse.getX();
                } else {
                    x -= vitesse.getY();
                }

                double y = m * x + b; // F(x)=mx+b

                composant.getPosition().setLocation(x, y);
            }
        }
    }
}
