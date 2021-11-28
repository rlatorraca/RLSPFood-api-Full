package ca.com.rlsp.rlspfoodapi.api.uri;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@UtilityClass // Transforma a CLASSE UTILIATARIA => Classe ser  FINAL, e Nao permite criacao de Construtores
public class UriResourceHelper {

    public static void addUriInResponseHeader(Object resource) {

        /* Ajuda a criar uma URI usando as informacoes da requisicao atual */
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{cityId}")
                .buildAndExpand(resource) // Adiciona o Id da Cidade Criada
                .toUri();

        /* Adiciona a URI no cabecalho LOCATION da resposta*/
        HttpServletResponse response = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes())
                .getResponse();
        response.setHeader(HttpHeaders.LOCATION, uri.toString());

    }
}
