package com.llb.cxy.core.convert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class LongToDateSerializer extends JsonSerializer<Long>{
	
	@Override
    public void serialize(Long date, JsonGenerator jsonGenerator,
                SerializerProvider serializerProvider) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jsonGenerator.writeNumber("\""+sdf.format(date)+"\"");
    }
}
