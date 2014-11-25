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
public class Dictionary {

    private Long id;
    private String name;
    private boolean isPublic;
    private long userId;
    private long languageId;

    private User user;

    private Language language;

    private List<Word> words;

    public Dictionary(Long id, String name, boolean isPublic, long userId, long languageId) {
        this.id = id;
        this.name = name;
        this.isPublic = isPublic;
        this.userId = userId;
        this.languageId = languageId;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(long languageId) {
        this.languageId = languageId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
    
    public String toXMLString(){
    	String str= "";
    	str += "<dictionary>";
    	str += "<id>" + this.id + "</id>";
    	str += "<name>" + this.name + "</name>";
    	str += "<isPublic>" + this.isPublic + "</isPublic>";
    	str += "<userId>" + this.userId + "</userId>";
    	str += "<languageId>" + this.languageId + "</languageId>";
    	
    	str += "<words>";
    	getWords();
        if(words != null)
		for (Word word: words) {
    			str += word.toXMLString();
		}
		str += "</words>";
		
    	str += "</dictionary>";
    	return str;
    }

    /**
     * @param node
     * @return
     * @throws NullPointerException
     * 
     * <dictionary><id>1</id><name>MyDic</name><isPublic>true</isPublic><userId>1</userId><languageId>9</languageId><words><word><id>4</id><name>ss</name><dictionaryId>1</dictionaryId><meanings><meaning><id>1</id><name>sdasd</name><wordId>4</wordId><examples><example><id>1</id><name>example</name><meaningId>1</meaningId></example></examples></meaning><meaning><id>2</id><name>other meaning</name><wordId>4</wordId><examples></examples></meaning></meanings></word><word><id>5</id><name>sss</name><dictionaryId>1</dictionaryId><meanings></meanings></word></words></dictionary>
     */
    public static Dictionary fromXMLString(Node node) throws NullPointerException{
    	if(node == null)
    		return null;
    	NodeList nodeList = node.getChildNodes();
    	if(nodeList == null || nodeList.getLength() == 0)
    		return null;
    	
    	Dictionary dictionary = new Dictionary(
    			Long.valueOf(nodeList.item(0).getTextContent()),
    			nodeList.item(1).getTextContent(),
    			Boolean.valueOf(nodeList.item(2).getTextContent()),
    			Long.valueOf(nodeList.item(3).getTextContent()),
    			Long.valueOf(nodeList.item(4).getTextContent()));
    	
    	if(nodeList.item(5).getTextContent() != null){
    		List<Word> words = new ArrayList<Word>();
    		NodeList wordNodes = nodeList.item(5).getChildNodes();
    		for(int i = 0; i < wordNodes.getLength(); i++){
    			words.add(Word.fromXMLString(wordNodes.item(i)));
    		}
    		dictionary.words = words;
    	}    	
    	
    	return dictionary;
    } 
    
    public static List<Dictionary> fromXMLString(NodeList nodeList) throws NullPointerException{
        if(nodeList == null)
            return null;
        
        List<Dictionary> dicList = new ArrayList<Dictionary>();
        for(int i = 0; i < nodeList.getLength(); i++){
            dicList.add(fromXMLString(nodeList.item(i)));
        }
        
        return dicList;        
    }
}
