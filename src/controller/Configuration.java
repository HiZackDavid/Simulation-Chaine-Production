package controller;

import model.*;
import org.w3c.dom.Node;
import java.awt.Point;
import java.util.ArrayList;

public class Configuration {
    private XMLReader xmlReader;
    private ArrayList<Usine> usines;

    public Configuration() {
        this.usines = new ArrayList<>();
    }

    public ArrayList<Usine> getUsines() {
        if (usines.size() == 0 && !XMLReader.FILE_PATH.isEmpty()) {
            usines = readUsines();
        }

        return usines;
    }

    /**
     * Returns a list of "Usines" from the configurationFile.
     *
     * @return A list of "Usines" from the configurationFile.
     */
    private ArrayList<Usine> readUsines() {
        this.xmlReader = new XMLReader();
        ArrayList<Usine> usines = new ArrayList<>();

        int id;
        TypeUsine type;
        Icone icone;
        Point position;
        ArrayList<Chemin> chemins;
        ArrayList<Entree> entrees = null;
        ArrayList<Sortie> sorties = null;
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
                entrees = getEntrees(type);
                sorties = getSorties(type);
                if (type == TypeUsine.getType(this.xmlReader.getNodeAttributes(usineMetadata).get("type"))) {
                    intervalle = getIntervalle(usineMetadata);
                }
            }

            // Instancier des Usines selon leur type
            if (type != null) {
                icone = getIcone(type); // Icone vide des usines

                switch (type) {
                    case MATIERE -> usines.add(new UsineMatiere(id, type, icone, position, chemins, entrees, sorties, intervalle));
                    case AILE -> usines.add(new UsineAile(id, type, icone, position, chemins, entrees, sorties, intervalle));
                    case MOTEUR -> usines.add(new UsineMoteur(id, type, icone, position, chemins, entrees, sorties, intervalle));
                    case ASSEMBLAGE -> usines.add(new UsineAssemblage(id, type, icone, position, chemins, entrees, sorties, intervalle));
                    case ENTREPOT -> usines.add(new Entrepot(id, type, icone, position, chemins, entrees, sorties));
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
     * @return An icon based on the provided type of "Usine" and type of "Icon".
     */
    private Icone getIcone(TypeUsine usineType) {
        Icone icone = null;

        // Récupérer les usines dans la metadonnee
        for (Node usineNode : this.xmlReader.getNodesFromSource("metadonnees", "usine")) {
            String metadataUsineType = this.xmlReader.getNodeAttributes(usineNode).get("type");
            if (metadataUsineType.equals(TypeUsine.getType(usineType))) {
                // Récupérer l'icône correspondant au type d'icône cherché
                for (Node iconeNode : this.xmlReader.getNodesFromSource(usineNode, "icone")) {
                    String metadataIconeType = this.xmlReader.getNodeAttributes(iconeNode).get("type");

                    if (metadataIconeType.equals(TypeIcone.getType(TypeIcone.VIDE))) {
                        String path = this.xmlReader.getNodeAttributes(iconeNode).get("path");

                        icone = new Icone(TypeIcone.VIDE, path);
                    }
                }
            }
        }

        return icone;
    }

    public static Icone getIcone(TypeUsine usineType, TypeIcone iconeType) {
        XMLReader xmlReader = new XMLReader();
        Icone icone = null;

        // Récupérer les usines dans la metadonnee
        for (Node usineNode : xmlReader.getNodesFromSource("metadonnees", "usine")) {
            String metadataUsineType = xmlReader.getNodeAttributes(usineNode).get("type");
            if (metadataUsineType.equals(TypeUsine.getType(usineType))) {
                // Récupérer l'icône correspondant au type d'icône cherché
                for (Node iconeNode : xmlReader.getNodesFromSource(usineNode, "icone")) {
                    String metadataIconeType = xmlReader.getNodeAttributes(iconeNode).get("type");

                    if (metadataIconeType.equals(TypeIcone.getType(iconeType))) {
                        String path = xmlReader.getNodeAttributes(iconeNode).get("path");

                        icone = new Icone(iconeType, path);
                    }
                }
            }
        }

        return icone;
    }

    /**
     * Returns the list of "Entree" according to a type of "Usine".
     *
     * @param typeUsine The type of "Usine".
     * @return The list of "Entree" according to a type of "Usine".
     */
    private ArrayList<Sortie> getSorties(TypeUsine typeUsine) {
        ArrayList<Sortie> sorties = new ArrayList<>();

        // Récupérer les usines dans la metadonnee
        for (Node usineNode : this.xmlReader.getNodesFromSource("metadonnees", "usine")) {
            String metadataUsineType = this.xmlReader.getNodeAttributes(usineNode).get("type");

            if (metadataUsineType.equals(TypeUsine.getType(typeUsine))) {
                // Récupérer les sorties
                for (Node sortieNode : this.xmlReader.getNodesFromSource(usineNode, "sortie")) {
                    // Si ce n'est pas un entrepot, il possède une sortie
                    if (!typeUsine.equals(TypeUsine.ENTREPOT)) {
                        String type = this.xmlReader.getNodeAttributes(sortieNode).get("type");
                        sorties.add(new Sortie(TypeComposant.getType(type)));
                    }
                }
            }
        }

        return sorties;
    }

    /**
     * Returns the list of "Entree" according to a type of "Usine".
     *
     * @param typeUsine The type of "Usine".
     * @return The list of "Entree" according to a type of "Usine".
     */
    private ArrayList<Entree> getEntrees(TypeUsine typeUsine) {
        ArrayList<Entree> entrees = new ArrayList<>();

        // Récupérer les usines dans la metadonnee
        for (Node usineNode : this.xmlReader.getNodesFromSource("metadonnees", "usine")) {
            String metadataUsineType = this.xmlReader.getNodeAttributes(usineNode).get("type");

            if (metadataUsineType.equals(TypeUsine.getType(typeUsine))) {
                // Récupérer les entrees
                for (Node entreeNode : this.xmlReader.getNodesFromSource(usineNode, "entree")) {
                    String type = this.xmlReader.getNodeAttributes(entreeNode).get("type");

                    if (typeUsine.equals(TypeUsine.ENTREPOT)) {
                        int capacite = Integer.parseInt(this.xmlReader.getNodeAttributes(entreeNode).get("capacite"));
                        entrees.add(new Entree(TypeComposant.getType(type), capacite));
                    } else {
                        int quantite = Integer.parseInt(this.xmlReader.getNodeAttributes(entreeNode).get("quantite"));
                        entrees.add(new Entree(TypeComposant.getType(type), quantite));
                    }
                }
            }
        }

        return entrees;
    }

    /**
     * Returns the interval of an "Usine".
     *
     * @param usineMetadata The "Usine" metadata node.
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
