/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.Collection;
import model.Artigo;

/**
 *
 * @author rhau
 */
public class SelecaoLinkUtils {
    
    private final static Collection<String> comparadores = new ArrayList<>();
    static {
        comparadores.add("ieee");
        comparadores.add("acm");
        comparadores.add("springer");
    }
 
    
    public static void defineSites(Collection<Artigo> artigos) {
        for (Artigo a : artigos) 
            a.setDominio(getDominioSite(a.getLink()));
    }

    private static String getDominioSite(String link) {
        for (String c : comparadores) {
            if (link.contains(c))
                return c.toUpperCase();
        }
        
        return "Indefinido";
    }

    public static void defineSite(Artigo a) {
        a.setDominio(getDominioSite(a.getLink()));
    }

}
