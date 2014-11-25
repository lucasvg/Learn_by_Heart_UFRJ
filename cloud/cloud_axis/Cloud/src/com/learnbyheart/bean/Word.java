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
public class Word {

    private Long id;
    private String word;
    private long dictionaryId;

    private Dictionary dictionary;
    private Long dictionary__resolvedKey;

    private List<Meaning> meanings;

    public Word(Long id, String word, long dictionaryId) {
        this.id = id;
        this.word = word;
        this.dictionaryId = dictionaryId;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public long getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(long dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public Long getDictionary__resolvedKey() {
        return dictionary__resolvedKey;
    }

    public void setDictionary__resolvedKey(Long dictionary__resolvedKey) {
        this.dictionary__resolvedKey = dictionary__resolvedKey;
    }

    public List<Meaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<Meaning> meanings) {
        this.meanings = meanings;
    }
    
    public String toXMLString(){
    	String str= "";
    	str += "<word>";
    	str += "<id>" + this.id + "</id>";
    	str += "<name>" + this.word + "</name>";
    	str += "<dictionaryId>" + this.dictionaryId + "</dictionaryId>";
    	
    	
    	str += "<meanings>";
    	// meanings
    	getMeanings();
    	for (Meaning meaning: meanings) {
				str += meaning.toXMLString();
		}
    	str += "</meanings>";
    	
    	str += "</word>";
    	return str;
    }

	/**
	 * @param node
	 * @return
	 * @throws NullPointerException
	 * <word><id>4</id><name>ss</name><dictionaryId>1</dictionaryId><meanings><meaning><id>1</id><name>sdasd</name><wordId>4</wordId><examples><example><id>1</id><name>example</name><meaningId>1</meaningId></example></examples></meaning><meaning><id>2</id><name>other meaning</name><wordId>4</wordId><examples></examples></meaning></meanings></word>
	 */
	public static Word fromXMLString(Node node) throws NullPointerException{
    	if(node == null)
    		return null;
    	NodeList nodeList = node.getChildNodes();
    	if(nodeList == null || nodeList.getLength() == 0)
    		return null;
    	
    	Word word = new Word(
    			Long.valueOf(nodeList.item(0).getTextContent()),
    			nodeList.item(1).getTextContent(),
    			Long.valueOf(nodeList.item(2).getTextContent())
    			);
    	
    	
    	if(nodeList.item(3).getTextContent() != null){
    		List<Meaning> meanings = new ArrayList<Meaning>();
    		NodeList meaningNodes = nodeList.item(3).getChildNodes();
    		for(int i = 0; i < meaningNodes.getLength(); i++){
    			meanings.add(Meaning.fromXMLString(meaningNodes.item(i)));
    		}
    		word.meanings = meanings;
    	}    	
    	
    	return word;
    }
    
}
