package controller;

// Pour construire un DOM à partir du fichier XML
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
// Pour les exceptions levées lors de la lecture du fichier XML
import org.xml.sax.SAXException;
// Pour les définitions W3C du DOM et de son contenu
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import model.Usine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class XMLReader {
    private DocumentBuilderFactory dbf;
    private DocumentBuilder db;
    private Document doc;
    private String xmlFilePath;
    private File xmlFile;

    public XMLReader(String xmlFilePath) {
        // XML file path
        this.xmlFilePath = xmlFilePath;

        // Instantiate the Factory
        this.dbf = DocumentBuilderFactory.newInstance();

        try {
            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            this.dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            this.db = this.dbf.newDocumentBuilder();

            this.xmlFile = new File(this.xmlFilePath);

            this.doc = this.db.parse(this.xmlFile);

            // optional, but recommended
            // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            this.doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public String getXmlFilePath() {
        return xmlFilePath;
    }

    public void setXmlFilePath(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
        this.xmlFile = new File(this.xmlFilePath);
    }

    // NODE UTILITIES
    public NodeList getNodeList(String nodeName) {
        return this.doc.getElementsByTagName(nodeName);
    }

    public ArrayList<Node> getNodesFromSource(Node sourceNode, String nodeName) {
        ArrayList<Node> nodes = new ArrayList<>();

        if (sourceNode.hasChildNodes()) { // Possède des enfants
            NodeList children = sourceNode.getChildNodes();

            for (int index = 0; index < children.getLength(); index++) { // Pour tous les enfants
                Node child = children.item(index);

                if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals(nodeName)) { // Enfant est un élément et une usine
                    nodes.add(child);
                }

                if (child.getNodeType() == Node.ELEMENT_NODE && !child.getNodeName().equals(nodeName)) {
                    if (this.getNodesFromSource(child, nodeName).size() > 0) {
                        return this.getNodesFromSource(child, nodeName);
                    }
                }
            }
        }

        return nodes;
    }

    public ArrayList<Node> getNodesFromSource(String source, String nodeName) {
        ArrayList<Node> nodes = new ArrayList<>();
        NodeList nodeList = this.doc.getElementsByTagName(source);

        for (int sourceIndex = 0; sourceIndex < nodeList.getLength(); sourceIndex++) {
            Node node = nodeList.item(sourceIndex); // Metadonnées

            if (node.hasChildNodes()) { // Possède des enfants
                NodeList children = node.getChildNodes();

                for (int index = 0; index < children.getLength(); index++) { // Pour tous les enfants
                    Node child = children.item(index);

                    if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals(nodeName)) { // Enfant est un élément et une usine
                        nodes.add(child);
                    }

                    if (child.getNodeType() == Node.ELEMENT_NODE && !child.getNodeName().equals(nodeName)) {
                        if (this.getNodesFromSource(child, nodeName).size() > 0) {
                            return this.getNodesFromSource(child, nodeName);
                        }
                    }
                }
            }
        }
        return nodes;
    }

    public Map<String, String> getNodeAttributes(Node node) {
        Map<String, String> attributes = new HashMap<>();

        for (int index = 0; index < node.getAttributes().getLength(); index++) {
            String key = node.getAttributes().item(index).getNodeName();
            String value = node.getAttributes().item(index).getNodeValue();

            attributes.put(key, value);
        }

        return attributes;
    }

    public ArrayList<Usine> getUsines() {
        ArrayList<Usine> usines = new ArrayList<>();

        // Récupérer les usines dans la simulation
        System.out.println("--------------------------USINES----------------------------");
        for (Node usine : this.getNodesFromSource("simulation", "usine")) {
            System.out.println("id : " + this.getNodeAttributes(usine).get("id") + "\ttype: " +  this.getNodeAttributes(usine).get("type") + "\tx: " + this.getNodeAttributes(usine).get("x") + "\ty: " + this.getNodeAttributes(usine).get("y"));
        }

        // Récuperer les chemins dans la simulation
        System.out.println("--------------------------CHEMINS----------------------------");
        for (Node chemin : this.getNodesFromSource("simulation", "chemin")) {
            System.out.println("source : " + this.getNodeAttributes(chemin).get("de") + "\tvers: " +  this.getNodeAttributes(chemin).get("vers"));
        }

        // Récupérer la metadonnée des usines
        System.out.println("--------------------------USINES | METADATA----------------------------");
        for (Node usine : this.getNodesFromSource("metadonnees", "usine")) {
            System.out.println("type : " + this.getNodeAttributes(usine).get("type"));
            String type = this.getNodeAttributes(usine).get("type");

            switch (type) {
                case "usine-matiere" :
                    // Récupérer les icônes
                    System.out.println("\t--------------------------ICONE----------------------------");
                    for (Node icone : this.getNodesFromSource(usine, "icone")) {
                        System.out.println("\ttype : " + this.getNodeAttributes(icone).get("type") + "\tpath : " + this.getNodeAttributes(icone).get("path"));
                    }

                    // Récupérer sortie
                    System.out.println("\t--------------------------SORTIE----------------------------");
                    for (Node sortie : this.getNodesFromSource(usine, "sortie")) {
                        System.out.println("\ttype : " + this.getNodeAttributes(sortie).get("type"));
                    }

                    // Récupérer intervalle
                    System.out.println("\t--------------------------INTERVALLE----------------------------");
                    for (Node intervalle : this.getNodesFromSource(usine, "interval-production")) {
                        System.out.println("\tintervalle : " + intervalle.getTextContent());
                    }
                    break;
                case "usine-aile" :
                case "usine-moteur" :
                case "usine-assemblage" :
                    // Récupérer les icônes
                    System.out.println("\t--------------------------ICONE----------------------------");
                    for (Node icone : this.getNodesFromSource(usine, "icone")) {
                        System.out.println("\ttype : " + this.getNodeAttributes(icone).get("type") + "\tpath : " + this.getNodeAttributes(icone).get("path"));
                    }

                    // Récupérer entrée
                    System.out.println("\t--------------------------ENTREE----------------------------");
                    for (Node entree : this.getNodesFromSource(usine, "entree")) {
                        System.out.println("\ttype : " + this.getNodeAttributes(entree).get("type") + "\tquantite : " + this.getNodeAttributes(entree).get("quantite"));
                    }

                    // Récupérer sortie
                    System.out.println("\t--------------------------SORTIE----------------------------");
                    for (Node sortie : this.getNodesFromSource(usine, "sortie")) {
                        System.out.println("\ttype : " + this.getNodeAttributes(sortie).get("type"));
                    }

                    // Récupérer intervalle
                    System.out.println("\t--------------------------INTERVALLE----------------------------");
                    for (Node intervalle : this.getNodesFromSource(usine, "interval-production")) {
                        System.out.println("\tintervalle : " + intervalle.getTextContent());
                    }
                    break;
                case "entrepot" :
                    // Récupérer les icônes
                    System.out.println("\t--------------------------ICONE----------------------------");
                    for (Node icone : this.getNodesFromSource(usine, "icone")) {
                        System.out.println("\ttype : " + this.getNodeAttributes(icone).get("type") + "\tpath : " + this.getNodeAttributes(icone).get("path"));
                    }

                    // Récupérer entrée
                    System.out.println("\t--------------------------ENTREE----------------------------");
                    for (Node entree : this.getNodesFromSource(usine, "entree")) {
                        System.out.println("\ttype : " + this.getNodeAttributes(entree).get("type") + "\tquantite : " + this.getNodeAttributes(entree).get("quantite"));
                    }
                    break;
            }
        }

        return usines;
    }

    public static void main(String[] args) {
        XMLReader xmlReader = new XMLReader("src/ressources/configuration.xml");
        ArrayList<Usine> usines = xmlReader.getUsines();

        for (Usine usine : usines) {
            System.out.println(usine);
        }
    }
}
