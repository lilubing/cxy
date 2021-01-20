package com.llb.cxy.core.convert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 将long类型的值作为字符串写到json中。
 * 解决JavaScript数字值过大丢失精度的问题。
 */
public class IntegerToStringJsonSerializer extends JsonSerializer<Integer> {
    @Override
    public void serialize(Integer data, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(data.toString());
    }
}