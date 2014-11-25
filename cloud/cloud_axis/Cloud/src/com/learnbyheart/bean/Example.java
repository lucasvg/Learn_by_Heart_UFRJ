/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learnbyheart.bean;

import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author lucas
 */
public class Example {
    private Long id;

    private String example;
    private long meaningId;

    private Meaning meaning;

    private List<LogExampleNotification> logExampleNotifications;
    
    public Example(Long id, String example, long meaningId) {
        this.id = id;
        this.example = example;
        this.meaningId = meaningId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public long getMeaningId() {
        return meaningId;
    }

    public void setMeaningId(long meaningId) {
        this.meaningId = meaningId;
    }

    public Meaning getMeaning() {
        return meaning;
    }

    public void setMeaning(Meaning meaning) {
        this.meaning = meaning;
    }

    public List<LogExampleNotification> getLogExampleNotifications() {
        return logExampleNotifications;
    }

    public void setLogExampleNotifications(List<LogExampleNotification> logExampleNotifications) {
        this.logExampleNotifications = logExampleNotifications;
    }
    
    public String toXMLString(){
    	String str= "";
    	str += "<example>";
    	str += "<id>" + this.id + "</id>";
    	str += "<name>" + this.example + "</name>";
    	str += "<meaningId>" + this.meaningId + "</meaningId>";
    	str += "</example>";
    	return str;
    }
    
    /**
     * @param node
     * @return
     * @throws NullPointerException
     * <example><id>1</id><name>example</name><meaningId>1</meaningId></example>
     */
    public static Example fromXMLString(Node node) throws NullPointerException{
    	if(node == null)
    		return null;
    	NodeList nodeList = node.getChildNodes();
    	if(nodeList == null || nodeList.getLength() == 0)
    		return null;
    	
    	return new Example(
    			Long.valueOf(nodeList.item(0).getTextContent()),
    			nodeList.item(1).getTextContent(),
    			Long.valueOf(nodeList.item(2).getTextContent())
    			);
    }
    
}
