package ca.com.rlsp.rlspfoodapi.api.exceptionhandler;

import ca.com.rlsp.rlspfoodapi.core.validation.ValidationPatchException;
import ca.com.rlsp.rlspfoodapi.domain.exception.EntityIsForeignKeyException;
import ca.com.rlsp.rlspfoodapi.domain.exception.EntityNotFoundException;
import ca.com.rlsp.rlspfoodapi.domain.exception.GenericBusinessException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

/*
    ResponseEntityExceptionHandler
     - Classe de conveniencia que trata Exception do SPRINGBOOT
 */
@Slf4j
@ControllerAdvice // faz com todas excecoes sejam tratadas nessa Classe
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MSG_MEDIA_TYPE_NOT_SUPPORTED = "Media Type not supported on RLSPFood";
    public static final String MALFORMED_JSON_REQUEST = "Malformed JSON request. Check JSON syntax";
    public static final String MALFORMED_URI_REQUEST = "Malformed URI request. Check URI syntax";
    public static final String MSG_FINAL_USER_GENERIC = "An unexpected internal system error has occurred. Please try again and if the problem persists, " +
            "contact the system administrator.";
    public static final String ONE_MORE_ATTRIBUTES_INVALIDS = "One ot more attribute is/are invalid(s). Fix it and try again";
    public static final String USER_DONT_HAVE_PERMISSION_TO_EXECUTE_THIS_ACTION = "User doesn't have permission to execute this action.";


    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    /*
            Trata a excecao de 404 (Bad request) nos filtros de ORDER
         */
    @Override
    protected ResponseEntity<Object> handleBindException(BindException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleValidationInternal(e, e.getBindingResult(), headers, status, request);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex, WebRequest request) {

        HttpStatus status = HttpStatus.FORBIDDEN;
        ProblemTypeEnum problemType = ProblemTypeEnum.ACCESS_DENIED;
        String detail = ex.getMessage();

        ApiHandleProblemDetail apiHandleProblem = createProblemDetailBuilder(status, problemType, detail,  OffsetDateTime.now())
                //.userMessage(detail)
                .userMessage(USER_DONT_HAVE_PERMISSION_TO_EXECUTE_THIS_ACTION)
                .build();

        return handleExceptionInternal(ex, apiHandleProblem, new HttpHeaders(), status, request);
    }

    /*
       Metodo que trata a Excecao Generica e Cria uma mensagem de Erro Customizada
    */
    // WebRequest request => injetado pelo proprio SPRINGBOOT
    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException e, WebRequest request){
        /*
        ApiHandleError handler = ApiHandleError.builder()
                .dateTime(LocalDateTime.now())
                .message(e.getReason())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(handler);
         */
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemTypeEnum problemType = ProblemTypeEnum.RESOURCE_NOT_FOUND;
        String detail = e.getReason();

        ApiHandleProblemDetail apiHandleProblem = createProblemDetailBuilder(status, problemType, detail, OffsetDateTime.now())
                .userMessage(MSG_FINAL_USER_GENERIC)
                .build();

        return handleExceptionInternal(e, apiHandleProblem, new HttpHeaders(), status, request);
    }

    // WebRequest request => injetado pelo proprio SPRINGBOOT
    @ExceptionHandler(GenericBusinessException.class)
    public ResponseEntity<?> handleGenericBusiness(GenericBusinessException e, WebRequest request){
        /*
        ApiHandleError handler = ApiHandleError.builder()
                .dateTime(LocalDateTime.now())
                .message(e.getReason())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(handler);
         */

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemTypeEnum problemType = ProblemTypeEnum.BUSINESS_RULES_HAS_ERROR;
        String detail = e.getReason().toString();

        ApiHandleProblemDetail apiHandleProblem = createProblemDetailBuilder(status, problemType, detail, OffsetDateTime.now())
                .userMessage(detail)
                .build();

        return handleExceptionInternal(e, apiHandleProblem, new HttpHeaders(), status, request);
    }

    // WebRequest request => injetado pelo proprio SPRINGBOOT
    @ExceptionHandler(EntityIsForeignKeyException.class)
    public ResponseEntity<?> handleEntityIsForeignKey(EntityIsForeignKeyException e, WebRequest request){
        /*
        ApiHandleError handler = ApiHandleError.builder()
                .dateTime(LocalDateTime.now())
                .message(e.getReason())
                .build();

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(handler);
        */
        HttpStatus status = HttpStatus.CONFLICT;
        ProblemTypeEnum problemType = ProblemTypeEnum.ENTITY_IN_USE;
        String detail = e.getReason();

        ApiHandleProblemDetail apiHandleProblem = createProblemDetailBuilder(status, problemType, detail, OffsetDateTime.now())
                .userMessage(detail)
                .build();

        return handleExceptionInternal(e, apiHandleProblem, new HttpHeaders(), status, request);
    }

    /*
        Manage SYTEMS ERROR Excepetion
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaught(Exception e, WebRequest request){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemTypeEnum problemType = ProblemTypeEnum.INTERNAL_SERVER_ERROR;
        String detail = "An unexpected internal system error has occurred. Please try again and if the problem persists," +
                " contact the system administrator.";
                // Important print printStackTrace (for while). Not exist logs yet.
        // So, We can print a Exception's stacktrace

        log.error(e.getMessage(), e);
        //e.printStackTrace();

        ApiHandleProblemDetail apiHandleProblem = createProblemDetailBuilder(status, problemType, detail, OffsetDateTime.now())
                .userMessage(detail)
                .build();

        return handleExceptionInternal(e, apiHandleProblem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return handleValidationInternal(e, e.getBindingResult(), headers, status, request);
    }

    @ExceptionHandler({ValidationPatchException.class})
    public ResponseEntity<?> handleValidationPatch(ValidationPatchException e,  WebRequest request){
        return handleValidationInternal(e, e.getBindingResult(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }



    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleFileSizeLimitExceededException(MaxUploadSizeExceededException e, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(e);
        String detail = String.format("The file size exceeds the maximum size of %d", e.getMaxUploadSize());

        if (rootCause instanceof FileSizeLimitExceededException) {
            return handleFileSizeLimitExceededException((FileSizeLimitExceededException) rootCause,  new HttpHeaders(), HttpStatus.PAYLOAD_TOO_LARGE, request);
        }

        ApiHandleProblemDetail apiHandleProblem = createProblemDetailBuilder(HttpStatus.PAYLOAD_TOO_LARGE,  ProblemTypeEnum.MAX_SIZE_EXCEEDED, detail, OffsetDateTime.now())
                .userMessage(MSG_FINAL_USER_GENERIC)
                .build();

        return handleExceptionInternal(e, apiHandleProblem, new HttpHeaders() , HttpStatus.BANDWIDTH_LIMIT_EXCEEDED, request);
    }


    private ResponseEntity<Object> handleFileSizeLimitExceededException(FileSizeLimitExceededException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = String.format("The file size exceeds the maximum size of %d", e
                .getPermittedSize());

        ApiHandleProblemDetail apiHandleProblem = createProblemDetailBuilder(status,  ProblemTypeEnum.MAX_SIZE_EXCEEDED, detail, OffsetDateTime.now())
                .userMessage(MSG_FINAL_USER_GENERIC)
                .build();
        return handleExceptionInternal(e, apiHandleProblem, headers , status, request);
    }

    private ResponseEntity<Object> handleValidationInternal(Exception e, BindingResult bindingResult, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemTypeEnum problemType = ProblemTypeEnum.INVALID_DATA;
        String detail = String.format(ONE_MORE_ATTRIBUTES_INVALIDS);

        List<ApiHandleProblemDetail.Object> problemFields = bindingResult.getAllErrors()
                .stream()
                .map(objectError ->{
                        String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                        String name = objectError.getObjectName();

                        if(objectError instanceof FieldError){
                            name =((FieldError) objectError).getField();
                        }
                        return ApiHandleProblemDetail.Object.builder()
                                    .name(name)
                                    .userMessage(message)
                                    .build();
                        }
                    )
                .collect((Collectors.toList()));

        ApiHandleProblemDetail apiHandleProblem = createProblemDetailBuilder(status, problemType, detail, OffsetDateTime.now())
                .userMessage(ONE_MORE_ATTRIBUTES_INVALIDS)
                .objects(problemFields)
                .build();

        return super.handleExceptionInternal(e, apiHandleProblem , headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemTypeEnum problemType = ProblemTypeEnum.RESOURCE_NOT_FOUND;
        String detail = String.format("Resourse '%s' , that your are trying to reach doesn't exist.",
                e.getRequestURL());

        ApiHandleProblemDetail apiHandleProblem = createProblemDetailBuilder(status, problemType, detail, OffsetDateTime.now())
                .userMessage(MSG_FINAL_USER_GENERIC)
                .build();

        return super.handleExceptionInternal(e, apiHandleProblem , headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        if(body == null){
            body = ApiHandleProblemDetail.builder()
                    .dateTime(OffsetDateTime.now())
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .userMessage(MSG_FINAL_USER_GENERIC)
                    .build();
        } else if(body instanceof String){
            body = ApiHandleProblemDetail.builder()
                    .dateTime(OffsetDateTime.now())
                    .title((String) body)
                    .status(status.value())
                    .userMessage(MSG_FINAL_USER_GENERIC)

                    .build();
        }
        return super.handleExceptionInternal(e, body , headers, status, request);
    }

    @Override
    protected ResponseEntity<Object>  handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {

        // Busca a RAIZ do problema (da formacao do JSON)
        // Throwable rootCause = e.getRootCause();
        Throwable rootCause = ExceptionUtils.getRootCause(e);

        if(rootCause instanceof InvalidFormatException){
            return handleInvalidJSONFormat((InvalidFormatException) rootCause, headers, status, request);
        }

        if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }

        if (rootCause instanceof UnrecognizedPropertyException) {
            return handleUnrecognizedProperty((UnrecognizedPropertyException) rootCause, headers, status, request);
        }

        ProblemTypeEnum problemType = ProblemTypeEnum.MALFORMED_JSON_REQUEST;
        String detail = MALFORMED_JSON_REQUEST;

        ApiHandleProblemDetail apiHandleProblem = createProblemDetailBuilder(status, problemType, detail, OffsetDateTime.now())
                .userMessage(MSG_FINAL_USER_GENERIC)
                .build();

        return super.handleExceptionInternal(e, apiHandleProblem, new HttpHeaders(), status, request);
    }

    /*
          // 1. MethodArgumentTypeMismatchException é um subtipo de TypeMismatchException

          // 2. ResponseEntityExceptionHandler já trata TypeMismatchException de forma mais abrangente

          // 3. Então, especializamos o método handleTypeMismatch e verificamos se a exception
                     é uma instância de MethodArgumentTypeMismatchException

          // 4. Se for, chamamos um método especialista em tratar esse tipo de exception

          // 5. Poderíamos fazer tudo dentro de handleTypeMismatch, mas preferi separar em outro método
     */

    /*
        Exception de parâmetro de URL inválido
     */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (e instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(
                    (MethodArgumentTypeMismatchException) e, headers, status, request);
        }
        return super.handleTypeMismatch(e, headers, status, request);
    }


    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemTypeEnum problemType = ProblemTypeEnum.ENTITY_IN_USE;
        String detail = String.format("URL parameter '%s' get a value '%s', that has an invalid type. Fix it and insert a compatible value to type %s.",
                e.getName(), e.getValue(), e.getRequiredType().getSimpleName());


        ApiHandleProblemDetail apiHandleProblem = createProblemDetailBuilder(status, problemType, detail, OffsetDateTime.now())
                .userMessage(MSG_FINAL_USER_GENERIC)
                .build();

        return handleExceptionInternal(e, apiHandleProblem, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidJSONFormat(InvalidFormatException e, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = joinPath(e.getPath());

        ProblemTypeEnum problemType = ProblemTypeEnum.MALFORMED_JSON_REQUEST;
        String detail = String.format("Attribute '%s' has got the value '%s', that is invalid type. Inform a compatible value having type as %s.",
                path, e.getValue(), e.getTargetType().getSimpleName());

        ApiHandleProblemDetail apiHandleProblem = createProblemDetailBuilder(status, problemType, detail, OffsetDateTime.now())
                .userMessage(MSG_FINAL_USER_GENERIC)
                .build();

        return handleExceptionInternal(e, apiHandleProblem, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException e,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        // Built joinPath method to reuse by all methods
        // concat the attributes names (split by ".")
        String path = joinPath(e.getPath());

        ProblemTypeEnum problemType = ProblemTypeEnum.MALFORMED_JSON_REQUEST;
        String detail = String.format("Attribute '%s' doesn't exist or can't be treated by API. Fix it or remove it, so try again.", path);

        ApiHandleProblemDetail apiHandleProblem = createProblemDetailBuilder(status, problemType, detail, OffsetDateTime.now())
                .userMessage(MSG_FINAL_USER_GENERIC)
                .build();

        return handleExceptionInternal(e, apiHandleProblem, headers, status, request);
    }

    private ResponseEntity<Object> handleUnrecognizedProperty(UnrecognizedPropertyException e,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        // Built joinPath method to reuse by all methods
        // concat the attributes names (split by ".")
        String path = joinPath(e.getPath());

        ProblemTypeEnum problemType = ProblemTypeEnum.MALFORMED_JSON_REQUEST;
        String detail = String.format("Attribute '%s' doesn't exist. Fix it  or remove that attribute e try again.", path);

        ApiHandleProblemDetail apiHandleProblem = createProblemDetailBuilder(status, problemType, detail, OffsetDateTime.now())
                .userMessage(MSG_FINAL_USER_GENERIC)
                .build();

        return handleExceptionInternal(e, apiHandleProblem, headers, status, request);
    }

    private String joinPath(List<Reference> references) {
        //references.forEach(r-> System.out.println(r.toString()));
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }


    private ApiHandleProblemDetail.ApiHandleProblemDetailBuilder createProblemDetailBuilder(
            HttpStatus status, ProblemTypeEnum problemType, String detail, OffsetDateTime localDateTime){

        return ApiHandleProblemDetail.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail)
                .dateTime(localDateTime);

    }

    


}
