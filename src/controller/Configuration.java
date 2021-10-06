package controller;

import model.Usine;
import model.TypeUsine;
import model.Icone;
import model.Chemin;
import model.Entree;
import model.Sortie;
import model.TypeIcone;
import model.UsineMatiere;
import model.UsineAile;
import model.UsineMoteur;
import model.UsineAssemblage;
import model.Entrepot;
import model.EntreeEntrepot;
import model.EntreeProduction;
import org.w3c.dom.Node;
import java.awt.Point;
import java.util.ArrayList;

public class Configuration {
    private final XMLReader xmlReader;

    public Configuration() {
        this.xmlReader = new XMLReader();
    }

    /**
     * Returns a list of "Usines" from the configurationFile.
     *
     * @return A list of "Usines" from the configurationFile.
     */
    public ArrayList<Usine> getUsines() {
        ArrayList<Usine> usines = new ArrayList<>();

        int id;
        TypeUsine type;
        Icone icone;
        Point position;
        ArrayList<Chemin> chemins;
        ArrayList<Entree> entrees = null;
        Sortie sortie = null;
        int intervalle = -1;

        // Récupérer les usines dans la simulation
        for (Node usineSimulation : this.xmlReader.getNodesFromSource("simulation", "usine")) {
            id = Integer.parseInt(this.xmlReader.getNodeAttributes(usineSimulation).get("id"));
            type = TypeUsine.getType(this.xmlReader.getNodeAttributes(usineSimulation).get("type"));

            int x = Integer.parseInt(this.xmlReader.getNodeAttributes(usineSimulation).get("x"));
            int y = Integer.parseInt(this.xmlReader.getNodeAttributes(usineSimulation).get("y"));

            position = new Point(x, y);
            chemins = getChemins(id);

            // Récupérer la metadonnée des usines
            for (Node usineMetadata : this.xmlReader.getNodesFromSource("metadonnees", "usine")) {
                entrees = getEntree(type);
                sortie = getSortie(usineMetadata);
                intervalle = getIntervalle(usineMetadata);
            }

            // Instancier des Usines selon leur type
            if (type != null) {
                icone = getIcone(type, TypeIcone.VIDE); // Icone vide des usines

                switch (type) {
                    case MATIERE -> usines.add(new UsineMatiere(id, type, icone, position, chemins, intervalle));
                    case AILE -> usines.add(new UsineAile(id, type, icone, position, chemins, intervalle));
                    case MOTEUR -> usines.add(new UsineMoteur(id, type, icone, position, chemins, intervalle));
                    case ASSEMBLAGE -> usines.add(new UsineAssemblage(id, type, icone, position, chemins, intervalle));
                    case ENTREPOT -> usines.add(new Entrepot(id, type, icone, position, chemins));
                }
            }
        }

        return usines;
    }

    /**
     * Returns a list of "Chemin" where the provided "usineID" is the "source".
     *
     * @param usineID The "id" of the "Usine".
     * @return A list of "Chemin" where the provided "usineID" is the "source".
     */
    private ArrayList<Chemin> getChemins(int usineID) {
        ArrayList<Chemin> chemins = new ArrayList<>();

        for (Node chemin : this.xmlReader.getNodesFromSource("simulation", "chemin")) {
            int source = Integer.parseInt(this.xmlReader.getNodeAttributes(chemin).get("de"));

            if (source == usineID) {
                int destination = Integer.parseInt(this.xmlReader.getNodeAttributes(chemin).get("vers"));

                chemins.add(new Chemin(source, destination));
            }
        }

        return chemins;
    }

    /**
     * Returns an icon based on the provided type of "Usine" and type of "Icon".
     *
     * @param usineType The type of "Usine".
     * @param iconeType The type of "Icon"
     * @return An icon based on the provided type of "Usine" and type of "Icon".
     */
    private Icone getIcone(TypeUsine usineType, TypeIcone iconeType) {
        Icone icone = null;

        // Récupérer les usines dans la metadonnee
        for (Node usineNode : this.xmlReader.getNodesFromSource("metadonnees", "usine")) {
            String metadataUsineType = this.xmlReader.getNodeAttributes(usineNode).get("type");
            if (metadataUsineType.equals(TypeUsine.getType(usineType))) {
                // Récupérer l'icône correspondant au type d'icône cherché
                for (Node iconeNode : this.xmlReader.getNodesFromSource(usineNode, "icone")) {
                    String metadataIconeType = this.xmlReader.getNodeAttributes(iconeNode).get("type");

                    if (metadataIconeType.equals(TypeIcone.getType(iconeType))) {
                        String path = this.xmlReader.getNodeAttributes(iconeNode).get("path");

                        icone = new Icone(iconeType, path);
                    }
                }
            }
        }

        return icone;
    }

    /**
     * Returns the "Sortie" of an "Usine".
     *
     * @param usineNode The "Usine" node.
     * @return The "Sortie" of an "Usine".
     */
    private Sortie getSortie(Node usineNode) {
        Sortie sortie = null;

        // Récupérer sortie
        for (Node sortieNode : this.xmlReader.getNodesFromSource(usineNode, "sortie")) {
            String type = this.xmlReader.getNodeAttributes(sortieNode).get("type");
            sortie = new Sortie(type);
        }

        return sortie;
    }

    /**
     * Returns the list of "Entree" according to a type of "Usine".
     *
     * @param typeUsine The type of "Usine".
     * @return The list of "Entree" according to a type of "Usine".
     */
    private ArrayList<Entree> getEntree(TypeUsine typeUsine) {
        ArrayList<Entree> entrees = new ArrayList<>();

        // Récupérer les usines dans la metadonnee
        for (Node usineNode : this.xmlReader.getNodesFromSource("metadonnees", "usine")) {
            String metadataUsineType = this.xmlReader.getNodeAttributes(usineNode).get("type");

            if (metadataUsineType.equals(TypeUsine.getType(typeUsine))) {
                // Récupérer entree
                for (Node entreeNode : this.xmlReader.getNodesFromSource(usineNode, "entree")) {
                    String type = this.xmlReader.getNodeAttributes(entreeNode).get("type");

                    if (typeUsine.equals(TypeUsine.ENTREPOT)) {
                        int capacite = Integer.parseInt(this.xmlReader.getNodeAttributes(entreeNode).get("capacite"));
                        entrees.add(new EntreeEntrepot(type, capacite));
                    } else {
                        int quantite = Integer.parseInt(this.xmlReader.getNodeAttributes(entreeNode).get("quantite"));
                        entrees.add(new EntreeProduction(type, quantite));
                    }
                }
            }
        }

        return entrees;
    }

    /**
     * Returns the interval of an "Usine".
     *
     * @param usineMetadata
     * @return The interval of an "Usine".
     */
    private int getIntervalle(Node usineMetadata) {
        int intervalle = -1;

        // Récupérer intervalle
        for (Node intervalleNode : this.xmlReader.getNodesFromSource(usineMetadata, "interval-production")) {
            intervalle = Integer.parseInt(intervalleNode.getTextContent());
        }

        return intervalle;
    }
}
