package ca.com.rlsp.rlspfoodapi.core.web;

import lombok.Getter;
import org.springframework.http.MediaType;

@Getter
public enum MediaTypeEnum {

    MEDIA_TYPE_V1 ("application/vnd.rlspfood.v1+json") ,
    MEDIA_TYPE_V2 ("application/vnd.rlspfood.v2+json") ;

    private final MediaType url;

    MediaTypeEnum(String url){
        this.url = MediaType.parseMediaType(url);
    }


}
