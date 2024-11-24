import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

public class XMLLoaderAndParser {
    public static List<ValidationRule> getXMLFormat(String Node, String ReqMethod){
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

        List<ValidationRule> rules = new ArrayList<>();

        //Load and read the XML file the XMLFormat - specified URI
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("src/main/xml-files/security-post.xml");

            //Get the Parameters for the specified URI
            NodeList paramList = document.getElementsByTagName(Node);

            for (int i = 0; i < paramList.getLength(); i++){
                Node node = paramList.item(i);

                if(node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    String name = element.getAttribute("name");
                    String regex = element.getAttribute("regex");
                    rules.add(new ValidationRule(name,regex));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rules;
    }
}
