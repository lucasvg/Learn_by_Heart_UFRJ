package com.learnbyheart.greenDao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class BeanDaoTestGenerator {
	public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "gen");

        addUsuarioDicionario(schema);

        new DaoGenerator().generateAll(schema, "gen");
    }

    private static void addUsuarioDicionario(Schema schema) {
        
//    	IT IS WRONG, SOMETHING NOT WORKING TO GENERATE CODE:

    	//    	Invalid layout of java.lang.String at value
//    	#
//    	# A fatal error has been detected by the Java Runtime Environment:
//    	#
//    	#  Internal Error (javaClasses.cpp:136), pid=15894, tid=139802461927168
//    	#  fatal error: Invalid layout of preloaded class
//    	#
//    	# JRE version:  (7.0_45-b18) (build )
//    	# Java VM: Java HotSpot(TM) 64-Bit Server VM (24.45-b08 mixed mode linux-amd64 compressed oops)
//    	# Failed to write core dump. Core dumps have been disabled. To enable core dumping, try "ulimit -c unlimited" before starting Java again
//    	#
//    	# An error report file with more information is saved as:
//    	# /home/lucas/Development/Learn by Heart app/LearnByHeart/hs_err_pid15894.log
//    	#
//    	# If you would like to submit a bug report, please visit:
//    	#   http://bugreport.sun.com/bugreport/crash.jsp
//    	#

    	
    	// userType
    	Entity userType = schema.addEntity("UserType");
        userType.addIdProperty();
        userType.addStringProperty("type").notNull();
    	
    	// user
    	Entity user = schema.addEntity("User");
        user.addIdProperty();
        user.addStringProperty("name").notNull();
        user.addStringProperty("login").notNull();
        user.addStringProperty("password").notNull();
        user.addStringProperty("email").notNull();
        
        // language
        Entity language = schema.addEntity("Language");
        language.addIdProperty();
        language.addStringProperty("description").notNull();
        language.addStringProperty("code").notNull();
        
        // dictionary
        Entity dictionary = schema.addEntity("Dictionary");
        dictionary.addIdProperty();
        dictionary.addStringProperty("name").notNull();
        dictionary.addBooleanProperty("isPublic").notNull();
        
        // word
        Entity word = schema.addEntity("Word");
        word.addIdProperty();
        word.addStringProperty("word").notNull();
        
        // meaning
        Entity meaning = schema.addEntity("Meaning");
        meaning.addIdProperty();
        meaning.addStringProperty("meaning").notNull();
        
        // example
        Entity example = schema.addEntity("Example");
        example.addIdProperty();
        example.addStringProperty("example").notNull();

        // notification
        Entity notification = schema.addEntity("Notification");
        notification.addIdProperty();
        notification.addStringProperty("notification");
        
        // LOG_EXAMPLE_NOTIFICATION
        Entity logExampleNotification = schema.addEntity("LogExampleNotification");
        logExampleNotification.addIdProperty();
        logExampleNotification.addStringProperty("result");
        logExampleNotification.addDateProperty("date");

        // RELATION USER-TYPE -> USER (1-M)
        Property userType_Id = user.addLongProperty("userType_Id").notNull().getProperty();
        user.addToOne(userType, userType_Id);
        ToMany userTypeToUser = userType.addToMany(user, userType_Id);
        userTypeToUser.setName("users");
        
        // RELATION USER -> DICTIONARY (1-M)
        Property userId = dictionary.addLongProperty("userId").notNull().getProperty();
        dictionary.addToOne(user, userId);
        ToMany userToDictionary = user.addToMany(dictionary, userId);
        userToDictionary.setName("dictionaries");
        
        // RELATION LANGUAGE -> DICTIONARY (1-M)
        Property languageId = dictionary.addLongProperty("languageId").notNull().getProperty();
        dictionary.addToOne(language, languageId);
        ToMany languageToDictionary = language.addToMany(dictionary, languageId);
        languageToDictionary.setName("dictionaries");
        
        // RELATION DICTIONERY -> WORD (1-M) 
        Property dictionaryId = word.addLongProperty("dictionaryId").notNull().getProperty();
        word.addToOne(dictionary, dictionaryId);
        ToMany dictionaryToWord = dictionary.addToMany(word, dictionaryId);
        dictionaryToWord.setName("words");
        
        // RELATION WORD -> MEANING (1-M) 
        Property wordId = meaning.addLongProperty("wordId").notNull().getProperty();
        meaning.addToOne(word, wordId);
        ToMany wordToMeaning = word.addToMany(meaning, wordId);
        wordToMeaning.setName("meanings");
        
        // RELATION MEANING -> EXAMPLE (1-M) 
        Property meaningId = example.addLongProperty("meaningId").notNull().getProperty();
        example.addToOne(meaning, meaningId);
        ToMany meaningToExample = meaning.addToMany(example, meaningId);
        meaningToExample.setName("examples");
        
        // RELATION EXAMPLE -> LOG_EXAMPLE_NOTIFICATION (1-M)
        Property exampleId = logExampleNotification.addLongProperty("exampleId").notNull().getProperty();
        logExampleNotification.addToOne(example, exampleId);
        ToMany exampleToLogExampleNotification = example.addToMany(logExampleNotification, exampleId);
        exampleToLogExampleNotification.setName("logExampleNotifications");
        
        // RELATION NOTIFICATION -> LOG_EXAMPLE_NOTIFICATION (1-M)
        Property notificacaoId = logExampleNotification.addLongProperty("notificacaoId").notNull().getProperty();
        logExampleNotification.addToOne(notification, notificacaoId);
        ToMany notificacaoToLogExampleNotification = notification.addToMany(logExampleNotification, notificacaoId);
        notificacaoToLogExampleNotification.setName("logExampleNotifications");
    }

}