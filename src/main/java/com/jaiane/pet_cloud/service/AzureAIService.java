package com.jaiane.pet_cloud.service;


import com.azure.ai.vision.imageanalysis.ImageAnalysisClient;
import com.azure.ai.vision.imageanalysis.ImageAnalysisClientBuilder;
import com.azure.ai.vision.imageanalysis.models.ImageAnalysisResult;
import com.azure.ai.vision.imageanalysis.models.VisualFeatures;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;
import com.jaiane.pet_cloud.config.AzureAIConfigProperties;
import com.jaiane.pet_cloud.dto.AzureAIResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AzureAIService {


    private final ImageAnalysisClient client;



    // O Construtor agora injeta o DTO de Configuração, garantindo que os valores foram lidos
    public AzureAIService(AzureAIConfigProperties config) {

        System.out.println("DEBUG: Tentando inicializar Azure AI Client via ConfigurationProperties...");

        String endpoint = config.getEndpoint();
        String key = config.getKey();

        if (endpoint == null || endpoint.isEmpty() || key == null || key.isEmpty()) {
            System.err.println("--- ERRO FATAL: KEY ou ENDPOINT do Azure AI Vision estão vazios! ---");
            throw new IllegalArgumentException("CONFIGURAÇÃO DE CHAVE FALHOU. Verifique se o application.properties está na pasta 'resources'.");
        }

        this.client = new ImageAnalysisClientBuilder()
                .credential(new AzureKeyCredential(key))
                .endpoint(endpoint)
                .buildClient();

        System.out.println("DEBUG: Azure AI Client inicializado com sucesso.");
    }


    public AzureAIResponse analisarImagem (MultipartFile arquivo){

        System.out.println("---- Chamando o Azure AI para analise...");


        try (InputStream stream = arquivo.getInputStream()) {


            /// conversão para binary e VisualFeatures

            BinaryData binaryData = BinaryData.fromStream(stream);

            ImageAnalysisResult result = client.analyze(
                    binaryData,
                    Arrays.asList(VisualFeatures.TAGS, VisualFeatures.CAPTION),
                    null
            );

            List<String> tags = result.getTags().getValues().stream()
                    .map(tag -> tag.getName())
                    .collect(Collectors.toList());


            String descricao = result.getCaption().getText();
            Double confianca = result.getCaption().getConfidence();

            return new AzureAIResponse(
                    tags,
                    descricao,
                    confianca
            );

        } catch (IOException e) {

            throw new RuntimeException("Erro ao processar o arquivo de imagem para a IA.", e);
        } catch (Exception e) {

            System.err.println("FALHA GRAVE NA CONEXÃO OU AUTENTICAÇÃO DO AZURE AI VISION ");
            System.err.println("Mensagem do Azure: " + e.getMessage());

            throw new RuntimeException("Falha na chamada da IA do Azure. Verifique se a KEY  está corretas.", e);
        }
    }
}


