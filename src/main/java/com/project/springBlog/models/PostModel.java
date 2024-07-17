package com.project.springBlog.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name="posts")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PostModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String titulo;

    private String contenido;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "posts_tags",
            joinColumns = {@JoinColumn(name="post_id")},
            inverseJoinColumns = {@JoinColumn(name="tag_id")}
    )
    @JsonIgnoreProperties("postsList")
    private Set<TagModel> tagList = new HashSet<>();


    @JsonGetter("tagList")
    public Set<Object> serializedTagList() {
        Set<Object> serializedTags = new HashSet<>();
        for (TagModel tag : tagList) {
            Map<String, Object> tagInfo = new HashMap<>();
            tagInfo.put("id", tag.getId());
            tagInfo.put("nombre", tag.getNombre());
            serializedTags.add(tagInfo);
        }
        return serializedTags;
    }

    @PreRemove
    private void preRemove(){
        Set<TagModel> tags = new HashSet<>(tagList);
        for(TagModel tag :tags){
            deteleTag(tag);
        }
    }

    /**
     * Añade una tag al post y tambien añade un post al tag referenciado
     * @param tag
     */
    public void addTag(TagModel tag){
        this.tagList.add(tag);
        tag.getPostsList().add(this);
    }

    /**
     * Elimina una tag del post y tambien elimina un post del tag referenciado
     * @param tag
     */
    public void deteleTag(TagModel tag){
        this.tagList.remove(tag);
        tag.getPostsList().remove(this);
    }

    //Getters and setters

    public PostModel(String titulo, String contenido) {
        this.titulo = titulo;
        this.contenido = contenido;
    }

    public PostModel(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Set<TagModel> getTagList() {
        return tagList;
    }

    public void setTagList(Set<TagModel> tagList) {
        this.tagList = tagList;
    }

    @Override
    public String toString() {
        return "PostModel{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", contenido='" + contenido + '\'' +
                '}';
    }
}
