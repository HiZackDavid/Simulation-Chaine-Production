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
 */
public class XMLReader {
    private DocumentBuilderFactory dbf;
    private DocumentBuilder db;
    private Document doc;
    public static String FILE_PATH = "";
    private File xmlFile;

    public XMLReader() {
        // There is a path selected
        if (!FILE_PATH.isEmpty()) {
            this.xmlFile = new File(FILE_PATH);

            // File exists
            if (this.xmlFile.exists()) {
                // Instantiate the Factory
                this.dbf = DocumentBuilderFactory.newInstance();

                try {
                    // optional, but recommended
                    // process XML securely, avoid attacks like XML External Entities (XXE)
                    this.dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

                    // parse XML file
                    this.db = this.dbf.newDocumentBuilder();
                    this.doc = this.db.parse(this.xmlFile);

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
     * Returns the NodeList of a node according to the name of the node.
     *
     * @param nodeName The name of the Node. (Also known as the tag name in the XML file.)
     * @return The NodeList of the node which has the same name as the provided node name.
     */
    public NodeList getNodeList(String nodeName) {
        return this.doc.getElementsByTagName(nodeName);
    }

    /**
     * Return as list of nodes extracted from a source node according to the name of the node.
     *
     * @param source The node from which the nodes must be extracted.
     * @param nodeName The name of the node to be extracted. (Note that if multiple nodes have the same name inside
     *                 the source node, they will all be returned.)
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
     * @param source
     * @param nodeName
     * @return
     */
    public ArrayList<Node> getNodesFromSource(String source, String nodeName) {
        ArrayList<Node> nodes = new ArrayList<>();
        NodeList nodeList = this.doc.getElementsByTagName(source);

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
