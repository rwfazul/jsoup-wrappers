/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author rhau
 */
public class Artigo {
    
    private String titulo;
    private String resumo;
    private String link;
    private String citacoes;
    private String linkRelacionados;
    private String dominio;
    private String linkReferencias;
    private Collection<Artigo> artigosReferenciados = new ArrayList<>();

    public Artigo() {
        
    }
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCitacoes() {
        return citacoes;
    }

    public void setCitacoes(String citacoes) {
        this.citacoes = citacoes;
    }

    public String getLinkRelacionados() {
        return linkRelacionados;
    }

    public void setLinkRelacionados(String linkRelacionados) {
        this.linkRelacionados = linkRelacionados;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public String getLinkReferencias() {
        return linkReferencias;
    }

    public void setLinkReferencias(String linkReferencias) {
        this.linkReferencias = linkReferencias;
    }

    public Collection<Artigo> getArtigosReferenciados() {
        return artigosReferenciados;
    }

    public void setArtigosReferenciados(Collection<Artigo> artigosReferenciados) {
        this.artigosReferenciados = artigosReferenciados;
    }
    
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("TITULO: ").append(getTitulo()).append("\n");
        b.append("LINK: ").append(getLink()).append("\n");
        //b.append("Resumo: ").append(getResumo()).append("\n");
        //b.append("Citacoes: ").append(getCitacoes()).append("\n");
        //b.append("Link Relacionados: ").append(getLinkRelacionados()).append("\n");
        b.append("DOMINIO LINK: ").append(getDominio()).append("\n");
        b.append("LINK REFERENCIAS: ").append(getLinkReferencias()).append("\n");
        b.append("TITULO ARTIGOS REFERENCIADOS: ").append("\n\t");
        int count = 1;
        for (Artigo r : getArtigosReferenciados()) 
            b.append(count++).append(". ").append(r.getTitulo()).append("\n\t");
        
        return b.toString();
    }
        
}
