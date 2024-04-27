package dev.jbxchung.hsr.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import dev.jbxchung.hsr.entity.GachaEntity;

import java.io.IOException;

public class EntityClassNameSerializer extends JsonSerializer<Class<? extends GachaEntity>> {
    @Override
    public void serialize(Class<? extends GachaEntity> aClass, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String shortName = aClass.getSimpleName();
        jsonGenerator.writeString(shortName);
    }
}
