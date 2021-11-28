package ca.com.rlsp.rlspfoodapi.util;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class ResourcesUtils {

    public static String getContentFromResource(String resourceName){

        InputStream inputStraem = ResourcesUtils.class.getResourceAsStream(resourceName);
        try {
            return StreamUtils.copyToString(inputStraem, Charset.forName("UTF-8") );
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
