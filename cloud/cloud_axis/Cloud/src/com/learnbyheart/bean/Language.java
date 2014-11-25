/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learnbyheart.bean;

import java.util.List;

/**
 *
 * @author lucas
 */
public class Language {

    private Long id;
    private String description;
    private String code;

    private List<Dictionary> dictionaries;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Dictionary> getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(List<Dictionary> dictionaries) {
        this.dictionaries = dictionaries;
    }
}
