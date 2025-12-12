package com.jaiane.pet_cloud.service;


import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;

import com.jaiane.pet_cloud.dto.PetRequestDto;
import com.jaiane.pet_cloud.exception.RecursoNaoEncontradoException;
import com.jaiane.pet_cloud.handler.GlobalExceptionHandler;
import com.jaiane.pet_cloud.model.Pet;
import com.jaiane.pet_cloud.repository.PetRepository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class PetService {



    private final PetRepository petRepository;
    private final BlobContainerClient blobContainerClient;
    private final AzureAIService aiService;

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);





    public PetService(PetRepository petRepository, BlobContainerClient blobContainerClient, AzureAIService aiService){
        this.petRepository = petRepository;
        this.blobContainerClient = blobContainerClient;
        this.aiService = aiService;
    }

    public Pet addPet(PetRequestDto dados)  {

        try{
            String urlFoto = enviarImagemParaAzure(dados.arquivo());

            List<String> tags = aiService.analisarImagem(dados.arquivo());

            Pet novoPet = new Pet();
            novoPet.setName(dados.name());
            novoPet.setRaca(dados.raca());
            novoPet.setIdade(dados.idade());
            novoPet.setImagem(urlFoto);
            novoPet.setTags(tags);

            return petRepository.save(novoPet);


        }catch (IOException e) {
            logger.error(" Erro ao fazer upload da imagem", e );
            throw  new RuntimeException("Erro ao fazer upload da imagem", e);

        }
    }

    public List<Pet> listar(){

    return petRepository.findAll();

    }

    public void delete(Long id){
        if(petRepository.existsById(id)){
            petRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Pet não encontrado com o ID: " + id));
        }

        petRepository.deleteById(id);

    }

    private String enviarImagemParaAzure(MultipartFile arquivo) throws IOException{

        String nomeArquivo = UUID.randomUUID().toString() + "-" + arquivo.getOriginalFilename();

        BlobClient blobClient = blobContainerClient.getBlobClient(nomeArquivo);

        blobClient.upload(arquivo.getInputStream(), arquivo.getSize(), true);

        return  blobClient.getBlobUrl();
    }

    public Pet atualizar (Long id, PetRequestDto dadosNovos){
        Pet petExistente = petRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pet não encontrado para atualização com o ID: " + id));

        petExistente.setName(dadosNovos.name());
        petExistente.setIdade(dadosNovos.idade());
        petExistente.setRaca(dadosNovos.raca());

        return petRepository.save(petExistente);



    }



}
