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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is tasked of reading files of XML extension. It also provides utility functions to browse Nodes which in
 * this context represents XML tags inside the files.
 *
 * Inspired by : https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 */
public class XMLReader {
    private Document doc;
    public static String FILE_PATH = "";

    public XMLReader() {
        // There is a path selected
        if (!FILE_PATH.isEmpty()) {
            File xmlFile = new File(FILE_PATH);

            // File exists
            if (xmlFile.exists()) {
                // Instantiate the Factory
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

                try {
                    // optional, but recommended
                    // process XML securely, avoid attacks like XML External Entities (XXE)
                    dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

                    // parse XML file
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    this.doc = db.parse(xmlFile);

                    // optional, but recommended
                    // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
                    this.doc.getDocumentElement().normalize();
                } catch (ParserConfigurationException | SAXException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Return as list of nodes extracted from a source node according to the name of the node.
     *
     * @param source    The node from which the nodes must be extracted.
     * @param nodeName  The name of the node to be extracted. (Note that if multiple nodes have the same name inside
     *                  the source node, they will all be returned.)
     * @return The node or nodes extracted from the source node.
     */
    public ArrayList<Node> getNodesFromSource(Node source, String nodeName) {
        ArrayList<Node> nodes = new ArrayList<>();

        if (source.hasChildNodes()) {
            NodeList children = source.getChildNodes();

            for (int index = 0; index < children.getLength(); index++) {
                Node child = children.item(index);

                if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals(nodeName)) {
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

    /**
     * Return as list of nodes extracted from a source node according to the name of the node.
     *
     * @param sourceName    The name of the node from which the nodes must be extracted.
     * @param nodeName      The name of the node to be extracted. (Note that if multiple nodes have the same name inside
     *                      the source node, they will all be returned.)
     * @return The node or nodes extracted from the source node.
     */
    public ArrayList<Node> getNodesFromSource(String sourceName, String nodeName) {
        ArrayList<Node> nodes = new ArrayList<>();
        NodeList nodeList = this.doc.getElementsByTagName(sourceName);

        for (int sourceIndex = 0; sourceIndex < nodeList.getLength(); sourceIndex++) {
            Node node = nodeList.item(sourceIndex);

            if (node.hasChildNodes()) {
                NodeList children = node.getChildNodes();

                for (int index = 0; index < children.getLength(); index++) {
                    Node child = children.item(index);

                    if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equals(nodeName)) {
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

    /**
     * Returns a list of attributes belonging to a node.
     *
     * @param node The node from which the attributes will be searched.
     * @return The list of attributes belonging to a node.
     */
    public Map<String, String> getNodeAttributes(Node node) {
        Map<String, String> attributes = new HashMap<>();

        for (int index = 0; index < node.getAttributes().getLength(); index++) {
            String key = node.getAttributes().item(index).getNodeName();
            String value = node.getAttributes().item(index).getNodeValue();

            attributes.put(key, value);
        }

        return attributes;
    }
}
