package ca.com.rlsp.rlspfoodapi.core.web;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.config.HypermediaMappingInformation;
import org.springframework.hateoas.mediatype.MessageResolver;
import org.springframework.hateoas.mediatype.hal.CurieProvider;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.hateoas.server.core.EvoInflectorLinkRelationProvider;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;
// fonte : https://github.com/spring-projects/spring-hateoas/issues/1253
//@Configuration
public class MediaTypeConfiguration implements HypermediaMappingInformation {

    //public static final MediaType MEDIATYPE = MediaType.parseMediaType("application/vnd.rlspfood.v1+json");

    /**
     * {@link MediaType}s this hypermedia can handle.
     */
    @Override
    public List<MediaType> getMediaTypes() {

        return Arrays.asList(MediaTypes.HAL_JSON, MediaTypeEnum.MEDIA_TYPE_V1.getUrl(), MediaTypeEnum.MEDIA_TYPE_V2.getUrl());
    }


    /**
     * Copy the incoming {@link ObjectMapper} and change it's output format along with disabling failure on unknown
     * properties.
     */
    @Override
    public ObjectMapper configureObjectMapper(ObjectMapper mapper) {

        mapper.registerModule(getJacksonModule());
        mapper.setHandlerInstantiator(new Jackson2HalModule.HalHandlerInstantiator(new EvoInflectorLinkRelationProvider(),
                CurieProvider.NONE, MessageResolver.DEFAULTS_ONLY));

        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        return mapper;
    }

    @Override
    public Module getJacksonModule() {
        return new Jackson2HalModule();
    }

}