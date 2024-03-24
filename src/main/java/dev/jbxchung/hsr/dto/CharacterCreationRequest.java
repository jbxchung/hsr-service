package dev.jbxchung.hsr.dto;

import dev.jbxchung.hsr.entity.Character;
import org.springframework.web.multipart.MultipartFile;

public class CharacterCreationRequest {
    private String name;
    private Integer rarity;
    private Character.Path path;
    private Character.Element element;
    private String description;
    private MultipartFile thumbnail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRarity() {
        return rarity;
    }

    public void setRarity(Integer rarity) {
        this.rarity = rarity;
    }

    public Character.Path getPath() {
        return path;
    }

    public void setPath(Character.Path path) {
        this.path = path;
    }

    public Character.Element getElement() {
        return element;
    }

    public void setElement(Character.Element element) {
        this.element = element;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(MultipartFile thumbnail) {
        this.thumbnail = thumbnail;
    }
}
