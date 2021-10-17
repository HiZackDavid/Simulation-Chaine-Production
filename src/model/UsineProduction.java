package model;

import controller.Configuration;
import Patrons.Observateur.Observateur;

import java.awt.Point;
import java.util.ArrayList;

public abstract class UsineProduction extends Usine implements Observateur {
    private int intervalleProduction;
    private ArrayList<Composant> composants;

    public UsineProduction(int id, TypeUsine type, Icone icone, Point position, ArrayList<Chemin> chemins, ArrayList<Entree> entrees, ArrayList<Sortie> sorties, int intervalleProduction) {
        super(id, type, icone, position, chemins, entrees, sorties);
        this.intervalleProduction = intervalleProduction;
        this.composants = new ArrayList<>();
    }

    public int getIntervalleProduction() {
        return intervalleProduction;
    }

    public boolean canProduce() {
        boolean canProduce = true; // On assume qu'un usine peut produire

        // Dans le cas où une usine a plusieurs entrées.
        if (getEntrees().size() > 0) {
            // On parcoure chacune des entrées de l'usine
            for (Entree entree : getEntrees()) {
                /*
                 Si au moins l'une d'entre elles n'a pas atteint sa limite, ça veut dire qu'elle n'a pas le
                 nécessaire pour produire.
                 */
                if (!entree.hasReachedRequiredAmount()) {
                    canProduce = false;
                }
            }
        }

        return canProduce;
    }

    public void addComponent(Composant composant) {
        this.composants.add(composant);
    }

    public void moveComposants(Configuration configuration, Point vitesse) {
        for (Chemin chemin : this.getChemins()) {
            if (getId() == chemin.getSource()) {
                for (Usine usine : configuration.getUsines()) {
                    if (usine.getId() == chemin.getDestination()) {
                        // Point de départ
                        double xUsineSource = this.getPosition().getX();
                        double yUsineSource = this.getPosition().getY();
                        // Point de destination
                        double xUsineDestination = usine.getPosition().getX();
                        double yUsineDestination = usine.getPosition().getY();

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
                                for(Entree entree : usine.getEntrees()) {
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
        }
    }

    public ArrayList<Composant> getComposants() {
        return composants;
    }

    public abstract Composant produce();
}
