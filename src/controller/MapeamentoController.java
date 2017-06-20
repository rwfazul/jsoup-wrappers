/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Artigo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.SelecaoLinkUtils;

/**
 *
 * @author rhau
 */
public class MapeamentoController {

    private Collection<Artigo> artigos;

    public MapeamentoController() {

    }

    public MapeamentoController(Collection<Artigo> artigos) {
        this.artigos = artigos;
        verificarDominio();
    }

    public Collection<Artigo> getArtigos() {
        return artigos;
    }

    public void setArtigos(Collection<Artigo> artigos) {
        this.artigos = artigos;
    }

    private void verificarDominio() {
        for (Artigo a : artigos) {
            SelecaoLinkUtils.defineSite(a);
            switch (a.getDominio()) {
                case "IEEE":
                    criarArtigoTitulos(a, IEEEController.getReferencias(a));
                    break;
                case "ACM":
                    getReferenciasACM(a);
                    break;
                case "SPRINGER":
                    getReferenciasSpringer(a);
                    break;
                default:
                    break;
            }
        }
    }

    private void getReferenciasACM(Artigo a) {
        String url = "http://dl.acm.org/tab_references.cfm?id=";
        String urlArtigo = a.getLink();

        String arNumber = urlArtigo.substring(urlArtigo.indexOf("=") + 1,
                urlArtigo.length());

        a.setLinkReferencias(url + arNumber);

        Collection<String> titulos = getTitulosACM(a.getLinkReferencias());

        for (String t : titulos) {
            Artigo referencia = new Artigo();
            referencia.setTitulo(t);
            a.getArtigosReferenciados().add(referencia);
        }

    }

    private Collection getTitulosACM(String link) {
        Collection<String> titulos = new ArrayList<>();

        try {
            URL url = new URL(link);

            URLConnection con = url.openConnection();
            con.addRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.100 Safari/537.36");
            con.addRequestProperty("Accept", "text/html");
            HttpURLConnection connection = null;

            if (con instanceof HttpURLConnection) {
                connection = (HttpURLConnection) con;
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            String linha = br.readLine();

            while (linha != null) {
                String b = Jsoup.parse(linha).text();

                //so pra nao pegar a bibliografia
                if (b.equals("BIBLIOGRAPHY")) {
                    linha = br.readLine();
                } else if (!b.equals("") && !b.equals(" ") && !b.matches("^[0-9]*$")) {
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

    public void getReferenciasSpringer(Artigo a) {
        final String ref = "#Bib1";
        a.setLinkReferencias(a.getLink() + ref);
        Document document = null;
        try {
            document = Jsoup.connect(a.getLinkReferencias()).get();
        } catch (IOException ex) {
            Logger.getLogger(MapeamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Elements elements = document.getElementsByClass("CitationContent");
        criarArtigoTitulos(a, getTitulosSpringer(elements));
    }

    private Collection<String> getTitulosSpringer(Elements elements) {
        Collection<String> titulos = new ArrayList<>();
        for (Element e : elements) {
            String tituloComAutor = e.html();
            int index = tituloComAutor.indexOf("<span");
            tituloComAutor = tituloComAutor.substring(0, index);
            if (tituloComAutor.contains("<")) {
                tituloComAutor = tituloComAutor.replaceAll("<em class=\"EmphasisTypeItalic \">", "");
                tituloComAutor = tituloComAutor.replaceAll("</em>", "");
            }
            index = tituloComAutor.indexOf(")") + 1;
            String titulo = tituloComAutor.substring(++index, tituloComAutor.length());
            index = titulo.indexOf(".");
            titulo = titulo.substring(0, index);
            titulos.add(titulo);
        }
        return titulos;
    }

    public void criarArtigoTitulos(Artigo a, Collection<String> titulos) {
        for (String t : titulos) {
            Artigo referencia = new Artigo();
            referencia.setTitulo(t);
            a.getArtigosReferenciados().add(referencia);
        }
    }

}
