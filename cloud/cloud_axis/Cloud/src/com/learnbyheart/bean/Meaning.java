/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learnbyheart.bean;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author lucas
 */
public class Meaning {
    private Long id;
    private String meaning;
    private long wordId;

    private Word word;

    private List<Example> examples;
    
    public Meaning(Long id, String meaning, long wordId) {
        this.id = id;
        this.meaning = meaning;
        this.wordId = wordId;
    }
     

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public long getWordId() {
        return wordId;
    }

    public void setWordId(long wordId) {
        this.wordId = wordId;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public List<Example> getExamples() {
        return examples;
    }

    public void setExamples(List<Example> examples) {
        this.examples = examples;
    }
    
    public String toXMLString(){
    	String str= "";
    	str += "<meaning>";
    	str += "<id>" + this.id + "</id>";
    	str += "<name>" + this.meaning + "</name>";
    	str += "<wordId>" + this.wordId + "</wordId>";
    	
//    	examples
    	
    	str += "<examples>";
    	getExamples();
    	for (Example example: examples) {
				str += example.toXMLString();
		}
    	str += "</examples>";
    	
    	str += "</meaning>";
    	return str;
    }
    
    /**
     * @param node
     * @return
     * @throws NullPointerException
     * <meaning><id>1</id><name>sdasd</name><wordId>4</wordId><examples><example><id>1</id><name>example</name><meaningId>1</meaningId></example></examples></meaning>
     */
    public static Meaning fromXMLString(Node node) throws NullPointerException{
    	if(node == null)
    		return null;
    	NodeList nodeList = node.getChildNodes();
    	if(nodeList == null || nodeList.getLength() == 0)
    		return null;
    	
    	Meaning meaning = new Meaning(
    			Long.valueOf(nodeList.item(0).getTextContent()),
    			nodeList.item(1).getTextContent(),
    			Long.valueOf(nodeList.item(2).getTextContent())
    			);
    	
    	
    	if(nodeList.item(3).getTextContent() != null){
    		List<Example> examples = new ArrayList<Example>();
    		NodeList exampleNodes = nodeList.item(3).getChildNodes();
    		for(int i = 0; i < exampleNodes.getLength(); i++){
    			examples.add(Example.fromXMLString(exampleNodes.item(i)));
    		}
    		meaning.examples = examples;
    	}    	
    	
    	return meaning;
    }
    
}
