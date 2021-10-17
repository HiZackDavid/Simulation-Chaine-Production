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
            // Point de départ
            double xUsineSource = usineSource.getPosition().getX();
            double yUsineSource = usineSource.getPosition().getY();
            // Point de destination
            double xUsineDestination = usineDestination.getPosition().getX();
            double yUsineDestination = usineDestination.getPosition().getY();

            double m = (yUsineDestination-yUsineSource)/(xUsineDestination-xUsineSource); // Pente de la fonction affine
            double b = yUsineDestination - m * xUsineDestination; // Ordonnée à l'origine

            // Déplacer les composants à la destination
            for (Composant composant : this.composants) {
                // Point du composant
                double x = composant.getPosition().getX(); // La position x dans laquelle j'aimerais que mon composant se trouve
                if (xUsineDestination > xUsineSource) {
                    x += vitesse.getX();
                } else {
                    x -= vitesse.getX();
                }

                double y = m * x + b; // F(x)=mx+b

                composant.getPosition().setLocation(x, y);
            }

            // Faire diparaître les composants lorsqu'ils sont arrivés à destination
            for (int index=0; index < this.composants.size(); index++) { // On parcoure chacun des composants
                boolean hasReachedDestinationX = false;
                boolean hasReachedDestinationY = false;

                if (this.composants.get(index).getPosition().getY() != xUsineDestination) {
                    if (xUsineSource > xUsineDestination) {
                        hasReachedDestinationX = this.composants.get(index).getPosition().getX() <= xUsineDestination;
                    }
                    if (xUsineSource < xUsineDestination) {
                        hasReachedDestinationX = this.composants.get(index).getPosition().getX() >= xUsineDestination;
                    }
                } else {
                    hasReachedDestinationX = true;
                }

                if (this.composants.get(index).getPosition().getY() != yUsineDestination) {
                    if (yUsineSource > yUsineDestination) {
                        hasReachedDestinationY = this.composants.get(index).getPosition().getY() <= yUsineDestination;
                    }
                    if (yUsineSource < yUsineDestination) {
                        hasReachedDestinationY = this.composants.get(index).getPosition().getY() >= yUsineDestination;
                    }
                } else {
                    hasReachedDestinationY = true;
                }

                if (hasReachedDestinationX && hasReachedDestinationY) {
                    for(Entree entree : usineDestination.getEntrees()) {
                        if (entree.getType() == this.composants.get(index).getTypeComposant()) {
                            entree.incrementCompteur();
                        }
                    }

                    this.composants.remove(this.composants.get(index));
                }
            }
        }
    }
}
