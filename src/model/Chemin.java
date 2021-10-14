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
        for (Usine usine : configuration.getUsines()) {
            if (usine.getId() == destination) {
                // Déplacer les composants à la destination
                for (Composant composant : this.composants) {
                    int pente = (usine.getPosition().y-composant.getPosition().y)/(usine.getPosition().x-composant.getPosition().x);

                    int x = composant.getPosition().x + vitesse.x;
                    int y = pente*vitesse.x + (usine.getPosition().y - pente*usine.getPosition().x);

                    // composant.getPosition().translate(x, y);
                    System.out.println(composant.getPosition());
                }
            }
        }
    }
}
