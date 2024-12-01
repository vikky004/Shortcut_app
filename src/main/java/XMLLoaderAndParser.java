import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLLoaderAndParser {
    public static Map<String, String> getXMLFormat(String Node, String ReqMethod){
        String XMLFile = null;

        //Find the XML file
        switch (ReqMethod){
            case "GET":
                break;
            case "UPDATE":
                break;
            case "POST":
                XMLFile = "security-post.xml";
                break;
            case "DELETE":
                break;
        }

        Map<String, String> rules = new HashMap<>();

        //Load and read the XML file the XMLFormat - specified URI
        try {
//            File xmlFile = new File("src/main/xml-files/security-post.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("/Users/vignesh-tt0497/eclipse-workspace/shortcut/Shortcut_app/src/main/xml-files/" + XMLFile);

            document.getDocumentElement().normalize();
            //Get the Parameters for the specified URI
            NodeList nodeList = document.getElementsByTagName(Node);
            Node node = nodeList.item(0);
            Element nodeElement = (Element) node;
            NodeList paramList = nodeElement.getElementsByTagName("params");
            if(node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE){
                for (int i = 0; i < paramList.getLength(); i++){
                    Node params = paramList.item(i);
                    Element element = (Element) params;
                    String name = element.getAttribute("name");
                    String regex = element.getAttribute("regex");
                    rules.put(name,regex);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rules;
    }

    public static String getHandlerClass(String ServletPath) {
        String XMLFile = "servlet-path-map.xml";
        String handlerClass = null;
        try {
//            File xmlFile = new File("src/main/xml-files/security-post.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("/Users/vignesh-tt0497/eclipse-workspace/shortcut/Shortcut_app/src/main/xml-files/" + XMLFile);

            document.getDocumentElement().normalize();
            //Get the Parameters for the specified URI
            NodeList nodeList = document.getElementsByTagName("api");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node apiNode = nodeList.item(i);
                if (apiNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element apiElement = (Element) apiNode;
                    String path = apiElement.getElementsByTagName("path").item(0).getTextContent();
                    if (path.equals(ServletPath)) {
                        handlerClass = apiElement.getElementsByTagName("handler-class").item(0).getTextContent();
                        break;
                    }
                }
            }
            if(handlerClass == null){
                throw new Exception("No handler class found for the specified URI");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return handlerClass;
    }
}
