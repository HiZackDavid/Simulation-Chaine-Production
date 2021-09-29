package controller;

import model.*;
import org.w3c.dom.Node;

import java.awt.*;
import java.util.ArrayList;

public class Configuration {

    public static String FILE_PATH = "";
    private XMLReader xmlReader;

    public Configuration() {
        this.xmlReader = new XMLReader();
    }

    public ArrayList<Usine> showConfigurationFile() {
        ArrayList<Usine> usines = new ArrayList<>();

        // Récupérer les usines dans la simulation
        System.out.println("--------------------------USINES----------------------------");
        for (Node usine : this.xmlReader.getNodesFromSource("simulation", "usine")) {
            System.out.println("id : " + this.xmlReader.getNodeAttributes(usine).get("id") + "\ttype: " +  this.xmlReader.getNodeAttributes(usine).get("type") + "\tx: " + this.xmlReader.getNodeAttributes(usine).get("x") + "\ty: " + this.xmlReader.getNodeAttributes(usine).get("y"));
        }

        // Récuperer les chemins dans la simulation
        System.out.println("--------------------------CHEMINS----------------------------");
        for (Node chemin : this.xmlReader.getNodesFromSource("simulation", "chemin")) {
            System.out.println("source : " + this.xmlReader.getNodeAttributes(chemin).get("de") + "\tvers: " +  this.xmlReader.getNodeAttributes(chemin).get("vers"));
        }

        // Récupérer la metadonnée des usines
        System.out.println("--------------------------USINES | METADATA----------------------------");
        for (Node usine : this.xmlReader.getNodesFromSource("metadonnees", "usine")) {
            System.out.println("type : " + this.xmlReader.getNodeAttributes(usine).get("type"));
            String type = this.xmlReader.getNodeAttributes(usine).get("type");

            switch (type) {
                case "usine-matiere" :
                    // Récupérer les icônes
                    System.out.println("\t--------------------------ICONE----------------------------");
                    for (Node icone : this.xmlReader.getNodesFromSource(usine, "icone")) {
                        System.out.println("\ttype : " + this.xmlReader.getNodeAttributes(icone).get("type") + "\tpath : " + this.xmlReader.getNodeAttributes(icone).get("path"));
                    }

                    // Récupérer sortie
                    System.out.println("\t--------------------------SORTIE----------------------------");
                    for (Node sortie : this.xmlReader.getNodesFromSource(usine, "sortie")) {
                        System.out.println("\ttype : " + this.xmlReader.getNodeAttributes(sortie).get("type"));
                    }

                    // Récupérer intervalle
                    System.out.println("\t--------------------------INTERVALLE----------------------------");
                    for (Node intervalle : this.xmlReader.getNodesFromSource(usine, "interval-production")) {
                        System.out.println("\tintervalle : " + intervalle.getTextContent());
                    }
                    break;
                case "usine-aile" :
                case "usine-moteur" :
                case "usine-assemblage" :
                    // Récupérer les icônes
                    System.out.println("\t--------------------------ICONE----------------------------");
                    for (Node icone : this.xmlReader.getNodesFromSource(usine, "icone")) {
                        System.out.println("\ttype : " + this.xmlReader.getNodeAttributes(icone).get("type") + "\tpath : " + this.xmlReader.getNodeAttributes(icone).get("path"));
                    }

                    // Récupérer entrée
                    System.out.println("\t--------------------------ENTREE----------------------------");
                    for (Node entree : this.xmlReader.getNodesFromSource(usine, "entree")) {
                        System.out.println("\ttype : " + this.xmlReader.getNodeAttributes(entree).get("type") + "\tquantite : " + this.xmlReader.getNodeAttributes(entree).get("quantite"));
                    }

                    // Récupérer sortie
                    System.out.println("\t--------------------------SORTIE----------------------------");
                    for (Node sortie : this.xmlReader.getNodesFromSource(usine, "sortie")) {
                        System.out.println("\ttype : " + this.xmlReader.getNodeAttributes(sortie).get("type"));
                    }

                    // Récupérer intervalle
                    System.out.println("\t--------------------------INTERVALLE----------------------------");
                    for (Node intervalle : this.xmlReader.getNodesFromSource(usine, "interval-production")) {
                        System.out.println("\tintervalle : " + intervalle.getTextContent());
                    }
                    break;
                case "entrepot" :
                    // Récupérer les icônes
                    System.out.println("\t--------------------------ICONE----------------------------");
                    for (Node icone : this.xmlReader.getNodesFromSource(usine, "icone")) {
                        System.out.println("\ttype : " + this.xmlReader.getNodeAttributes(icone).get("type") + "\tpath : " + this.xmlReader.getNodeAttributes(icone).get("path"));
                    }

                    // Récupérer entrée
                    System.out.println("\t--------------------------ENTREE----------------------------");
                    for (Node entree : this.xmlReader.getNodesFromSource(usine, "entree")) {
                        System.out.println("\ttype : " + this.xmlReader.getNodeAttributes(entree).get("type") + "\tquantite : " + this.xmlReader.getNodeAttributes(entree).get("quantite"));
                    }
                    break;
            }
        }

        return usines;
    }

    public ArrayList<Usine> getUsines() {
        ArrayList<Usine> usines = new ArrayList<>();

        int id = -1;
        TypeUsine type = null;
        Icone icone = null;
        Point position = null;
        ArrayList<Chemin> chemins = null;
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
                icone = getIcone(type, TypeIcone.VIDE);
                entrees = getEntree(type);
                sortie = getSortie(usineMetadata);
                intervalle = getIntervalle(usineMetadata);
            }

            switch (type) {
                case MATIERE:
                    usines.add(new UsineMatiere(id, type, icone, position, chemins, intervalle));
                    break;
                case AILE:
                    usines.add(new UsineAile(id, type, icone, position, chemins, intervalle));
                    break;
                case MOTEUR:
                    usines.add(new UsineMoteur(id, type, icone, position, chemins, intervalle));
                    break;
                case ASSEMBLAGE:
                    usines.add(new UsineAssemblage(id, type, icone, position, chemins, intervalle));
                    break;
                case ENTREPOT:
                    usines.add(new Entrepot(id, type, icone, position, chemins));
                    break;
            }
        }

        return usines;
    }

    public ArrayList<Chemin> getChemins(int idUsine) {
        ArrayList<Chemin> chemins = new ArrayList<>();

        for (Node chemin : this.xmlReader.getNodesFromSource("simulation", "chemin")) {
            int source = Integer.parseInt(this.xmlReader.getNodeAttributes(chemin).get("de"));

            if (source == idUsine) {
                int destination = Integer.parseInt(this.xmlReader.getNodeAttributes(chemin).get("vers"));

                chemins.add(new Chemin(source, destination));
            }
        }

        return chemins;
    }

    public Icone getIcone(TypeUsine usineType, TypeIcone iconeType) {
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

    public Sortie getSortie(Node usineNode) {
        Sortie sortie = null;

        // Récupérer sortie
        for (Node sortieNode : this.xmlReader.getNodesFromSource(usineNode, "sortie")) {
            String type = this.xmlReader.getNodeAttributes(sortieNode).get("type");
            sortie = new Sortie(type);
        }

        return sortie;
    }

    public ArrayList<Entree> getEntree(TypeUsine typeUsine) {
        ArrayList<Entree> entrees = new ArrayList<>();

        // Récupérer les usines dans la metadonnee
        for (Node usineNode : this.xmlReader.getNodesFromSource("metadonnees", "usine")) {
            String metadataUsineType = this.xmlReader.getNodeAttributes(usineNode).get("type");

            if (metadataUsineType.equals(TypeUsine.getType(typeUsine))) {
                // Récupérer sortie
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

    public ArrayList<Entree> getEntreeEntrepot(Node usineNode) {
        ArrayList<Entree> entrees = new ArrayList<>();

        // Récupérer sortie
        for (Node entreeNode : this.xmlReader.getNodesFromSource(usineNode, "entree")) {
            String type = this.xmlReader.getNodeAttributes(entreeNode).get("type");
            int capacite = Integer.parseInt(this.xmlReader.getNodeAttributes(entreeNode).get("capacite"));

            entrees.add(new EntreeEntrepot(type, capacite));
        }

        return entrees;
    }

    private int getIntervalle(Node usineMetadata) {
        int intervalle = -1;

        // Récupérer intervalle
        for (Node intervalleNode : this.xmlReader.getNodesFromSource(usineMetadata, "interval-production")) {
            intervalle = Integer.parseInt(intervalleNode.getTextContent());
        }

        return intervalle;
    }
}
