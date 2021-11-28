package ca.com.rlsp.rlspfoodapi.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemTypeEnum {

    BUSINESS_RULES_HAS_ERROR("/business-has-error", "Business rules was violated"),
    ENTITY_IN_USE("/entity-in-use", "Entity has been used by other entity"),
    RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
    INVALID_PARAMETER("/invalid-parameter", "Invalid parameter"),
    INVALID_DATA("/invalid-data", "Invalid data"),
    MALFORMED_JSON_REQUEST("/malformed-json-request", "Malformed JSON request. "),
    MALFORMED_URI_REQUEST("/malformed-uri-request", "Malformed URI request."),
    MAX_SIZE_EXCEEDED("/max-size-exceeded", "Maximum request size exceeded"),
    INTERNAL_SERVER_ERROR("/internal-server-error", "Internal Server Error"),
    ACCESS_DENIED("/access-denied", "Access Denied");

    private String uri;
    private String title;

    ProblemTypeEnum(String path, String title) {
        this.uri = "https://www.rlspfood.ca"+ path;
        this.title = title;
    }
}
