/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import util.BuscaUtils;
import conexao.Parser;
import controller.MapeamentoController;
import util.SelecaoLinkUtils;


/**
 *
 * @author rhau
 */
public class Busca {
    
    public static void main(String[] args) {
        // IEEE
        String busca = "Containers and cloud: From lxc to docker to kubernetes";
                
        Parser p = new Parser(BuscaUtils.getUrl(busca));
        SelecaoLinkUtils.defineSites(p.getArtigos());   
        MapeamentoController map = new MapeamentoController(p.getArtigos());
        p.imprimirResultados();
    }
    
}

