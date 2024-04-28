package dev.jbxchung.hsr.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import dev.jbxchung.hsr.entity.Item;
import dev.jbxchung.hsr.entity.UserItem;

import java.io.IOException;

// todo - there's probably a way to accomplish this with just annotations
public class UserItemSerializer extends JsonSerializer<UserItem> {
    @Override
    public void serialize(UserItem userItem, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Item item = userItem.getKey().getItem();
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("itemId", item.getId());
        jsonGenerator.writeStringField("itemName", item.getName());
        jsonGenerator.writeNumberField("quantity", userItem.getQuantity());
        jsonGenerator.writeEndObject();
    }
}
