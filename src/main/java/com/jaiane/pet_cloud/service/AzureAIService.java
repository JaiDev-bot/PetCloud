package com.jaiane.pet_cloud.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class AzureAIService {


    ///manipulação

    public List<String> analisarImagem (MultipartFile arquivo){

        System.out.println("---- Chamando o Azure AI para analise...");

        String nomeArquivo = arquivo.getOriginalFilename().toLowerCase();

        if (nomeArquivo.contains("cachorro")) {
            return List.of("cachorro", "pelo", "rebaixado", "feliz");
        } else if (nomeArquivo.contains("gato")) {
            return List.of("gato", "sofá", "sapeca", "branco");
        }
        else if(nomeArquivo.contains("gata")){
            return List.of("linda", "estudiosa", "criativa", "engraçada", "romantica demais, um perigo");
        } else {
            return List.of("animal de estimação", "desconhecido");
        }


    }
}
