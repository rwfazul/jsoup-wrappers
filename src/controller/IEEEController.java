/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Artigo;
import org.jsoup.Jsoup;

/**
 *
 * @author rhau
 */
public class IEEEController {
    private final static String base = "http://ieeexplore.ieee.org/xpl/dwnldReferences?arnumber=";
    
    static Collection<String> getReferencias(Artigo a) {
        String arNumber = a.getLink().replaceAll("[^0-9]", "");  
        a.setLinkReferencias(base + arNumber);
        return criarRefTitulos(a);
    }

    private static Collection<String> criarRefTitulos(Artigo a) {
        Collection<String> titulos = new ArrayList<>();

        try {
            String marcador = "\"";

            URL url = new URL(a.getLinkReferencias());
            URLConnection con = url.openConnection();

            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);

            String linha = br.readLine();
            while (linha != null) {
                String b = Jsoup.parse(linha).text();
                if (b.contains(marcador)) { // titulo
                    //remover tag numeros
                    if (b.contains("<")) {
                        b = b.replaceAll("<subscript>", " ");
                        b = b.replaceAll("</subscript>", "");
                    }
                    b = b.replaceAll("\"", ""); // remover aspas
                    titulos.add(b);
                }
                linha = br.readLine();
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(MapeamentoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MapeamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return titulos;
    }

}
