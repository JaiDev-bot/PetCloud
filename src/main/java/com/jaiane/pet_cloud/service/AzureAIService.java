package com.jaiane.pet_cloud.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class AzureAIService {

    public List<String> analisarImagem (MultipartFile arquivo){

        System.out.println("---- Chamando o Azure AI para analise...");

        if(Math.random() < 0.5){

            return List.of("cachorro", "feliz", "deitado", "pelo branco" );

        }else {

            return  List.of("gato", "dormindo", "interno", "sofá");

        }

    }
}
