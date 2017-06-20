/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author rhau
 */
public class BuscaUtils {
    
    private static final String urlPadrao = "http://scholar.google.co.uk/scholar?hl=en&q=";
    
    public static String getUrl(String busca) {
        return urlPadrao + formataString(busca);
    }
    
    public static String formataString(String busca) {
        return busca.replaceAll(" ", "+");
    }
    
}
