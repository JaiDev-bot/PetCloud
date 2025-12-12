package com.jaiane.pet_cloud.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@Service
public class AzureBlobService {


    private final BlobContainerClient blobContainerClient;


    public  AzureBlobService(BlobContainerClient blobContainerClient){
        this.blobContainerClient = blobContainerClient;
    }

    public String enviarImagemParaAzure(MultipartFile arquivo) throws IOException {

        String nomeArquivo = UUID.randomUUID().toString() + "-" + arquivo.getOriginalFilename();

        BlobClient blobClient = blobContainerClient.getBlobClient(nomeArquivo);

        blobClient.upload(arquivo.getInputStream(), arquivo.getSize(), true);

        return  blobClient.getBlobUrl();
    }

    public void deletarFoto(String urlCompleta) {

        System.out.println("--- 🗑️ Deletando arquivo: " + urlCompleta + " ---");

    }
}
