package ca.com.rlsp.rlspfoodapi.core.io;

import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.Base64;

/**
 * Faz a decodificacao da chave primaria do KeyStore, usando o padrao de Base64, toda vez que o Contexto do Spring
 * for iniciado atraves do Listener
 */
public class Base64ProtocolResolver implements ProtocolResolver,
                                               ApplicationListener<ApplicationContextInitializedEvent> {
    @Override
    public Resource resolve(String location, ResourceLoader resourceLoader) {
        if(location.startsWith("base64")) {
            byte[] decodeResource = Base64.getDecoder().decode(location.substring(7));
            return new ByteArrayResource(decodeResource);
        }
        return null;
    }

    /**
     * NO inicio da inicializcao do COntexto da aplicacao chamara esse Listener
     * @param event
     */
    @Override
    public void onApplicationEvent(ApplicationContextInitializedEvent event) {
        // Chama a propria instancia da aplicaaco (que eh um protcol resolver)
        event.getApplicationContext().addProtocolResolver(this);
    }
}
