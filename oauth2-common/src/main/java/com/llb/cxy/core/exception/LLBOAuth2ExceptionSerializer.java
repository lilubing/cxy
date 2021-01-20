package com.llb.cxy.core.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * description: 自定义oauth2异常提示 <br>
 * date: 2019/12/18 8:43 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@Slf4j
public class LLBOAuth2ExceptionSerializer extends StdSerializer<LLBOAuth2Exception> {

    public LLBOAuth2ExceptionSerializer() {
        super(LLBOAuth2Exception.class);
    }

    @Override
    public void serialize(LLBOAuth2Exception ex, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("message", ex.getMessage());
        gen.writeStringField("data", "");
        gen.writeNumberField("timestamp", System.currentTimeMillis());
        if (ex.getAdditionalInformation() != null) {
            for (Map.Entry<String, String> entry : ex.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                if ("code".equals(key)) {
                    gen.writeNumberField(key, new BigDecimal(add));
                } else {
                    gen.writeStringField(key, add);
                }
            }
        }
        gen.writeEndObject();
    }
}
