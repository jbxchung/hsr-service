package dev.jbxchung.hsr.configuration;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import dev.jbxchung.hsr.entity.GachaEntity;

import java.io.IOException;

public class EntityClassNameDeserializer extends JsonDeserializer<Class<? extends GachaEntity>> {
    private static final String namespacePrefix = "dev.jbxchung.hsr.entity.";

    @Override
    public Class<? extends GachaEntity> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String shortName = jsonParser.readValueAs(String.class);
        try {
            return Class.forName(namespacePrefix + shortName).asSubclass(GachaEntity.class);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
