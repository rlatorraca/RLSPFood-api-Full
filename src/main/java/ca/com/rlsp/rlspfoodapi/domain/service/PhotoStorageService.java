package ca.com.rlsp.rlspfoodapi.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;


public interface PhotoStorageService {

    void storage(NewPhoto newPhoto);

    void remove(String fileName);

    RetrievePhoto retrieve(String fileName);

    default String generateUUIDFileName(String originalName, Long restaurantId, Long productId) {
        return UUID.randomUUID().toString() +"_" + restaurantId+ "_"+ + productId +"_"+ originalName;
    }

    default void switchOrSave(String oldFileName, NewPhoto newPhoto) {
        if(oldFileName != null) {
            this.remove(oldFileName);
        }
        this.storage(newPhoto);
    }


    // Classe interna que sera usada para pegarmos o que queremos armazenar nos arquivos no ambiente Local
    @Builder
    @Getter
    class NewPhoto {
        private String newFIleName;
        private String contentType;
        private InputStream inputStream; // Fluxo de leitura do arquivo para foi feito o upload (por meio deste salva-se a foto)
    }

    @Builder
    @Getter
    class RetrievePhoto {
        private InputStream inputStream;
        private String url;

        public boolean hasInputStream() {
            return this.inputStream != null;
        }

        public boolean hasURL() {
            return this.url != null;
        }
    }
}
