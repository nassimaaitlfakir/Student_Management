package org.example.studentslist;

import jakarta.faces.context.FacesContext;

import java.io.Serializable;
import java.util.Locale;

public class LanguageBean implements Serializable {

    private Locale locale = Locale.ENGLISH;

    public Locale getLocale() {
        return locale;
    }

    public void setLanguage(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }

    public String getDatePattern() {
        if ("fr".equals(locale.getLanguage())) {
            return "dd/MM/yyyy";
        }
        return "yyyy-MM-dd";
    }
}
