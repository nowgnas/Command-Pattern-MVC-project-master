package controller;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlBeanFactory {

    HashMap<String, Command> beans;

    public XmlBeanFactory(String path) throws Exception {
        beans = new HashMap<>();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        parser.parse(path, new MyDefaultHandler());
    }

    public Command getBean(String id) {
        return beans.get(id);
    }

    public HashMap<String, Command> getBeans() {
        return beans;
    }

    class MyDefaultHandler extends DefaultHandler {
        ArrayList<String> cArgs;
        String id, className;
        Constructor c;

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes)
                throws SAXException {
            if ("bean".equals(qName)) {
                id = attributes.getValue("id");
                className = attributes.getValue("class");

                try {

                    cArgs = new ArrayList<>();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } else if ("constructor-arg".equals(qName)) {
                cArgs.add(attributes.getValue(0));
            }
        }//end of startElement()

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if ("bean".equals(qName)) {
                try {
                    //Java Refletion
                    //System.out.println(className+":"+cArgs.size());
                    Object o = null;

                    if (cArgs.size() == 0) {
                        c = Class.forName(className).getConstructor();
                        o = c.newInstance();
                    } else {
                        Constructor[] conList = Class.forName(className).getDeclaredConstructors();
                        for (Constructor c : conList) {
                            Class[] conParamTypes = c.getParameterTypes();

                            if (conParamTypes.length == cArgs.size()) {
                                Object[] args = new Object[conParamTypes.length];
                                for (int i = 0; i < conParamTypes.length; i++) {

                                    if (conParamTypes[i].getName().equals("java.lang.String")) {
                                        args[i] = cArgs.get(i);
                                    } else if (conParamTypes[i].getName().equals("int")) {
                                        args[i] = Integer.parseInt(cArgs.get(i));
                                    }

                                }
                                o = c.newInstance(args);

                            }
                        }
                    }

                    beans.put(id, (Command) o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}