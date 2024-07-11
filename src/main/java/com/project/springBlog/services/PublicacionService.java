package com.project.springBlog.services;

import com.project.springBlog.dtos.Publicacion;
import com.project.springBlog.dtos.PublicacionDetails;
import com.project.springBlog.exceptions.EntityException;
import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.PostModel;
import com.project.springBlog.models.TagModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PublicacionService {

    @Autowired
    PostService postService;

    @Autowired
    PostDetailsService detailsService;

    @Autowired
    TagService tagService;

    /**
     * Obtiene la informacion de una publicación en especifico
     *
     * @param id
     * @return PublicacionDetails => DTO que contiene Post + PostDetails
     */
    public PublicacionDetails getPublicacionDetails(long id) {
        try {
            PostModel post = postService.getPost(id); //Se busca el post
            if(post ==null){
                throw new EntityNotFoundException("Publicacion was not found");
            }
                PostDetailsModel details = detailsService.getPostDetails(id);
                return new PublicacionDetails(post, details);

        } catch (Exception ex) {
            throw new EntityException("Error during getting publicacion", ex);
        }
    }

    public PublicacionDetails addPublicacion(Publicacion publicacion) {
        PostModel post = null;
        PostDetailsModel details = null;

        //Se crea el contenido del post
        post = new PostModel(publicacion.getTitulo(), publicacion.getContenido());

        //Se añaden las tags:
        List<Integer> etiquetas = publicacion.getTags();

        for (Integer tagId : etiquetas) {
            TagModel tag = tagService.getTag(tagId);
            post.addTag(tag);
        }
        post = postService.addPost(post); //Se añade el post a la base de datos empleando el servicio de Posts

        //Se crean los postDetails
        details = new PostDetailsModel(new Date(), publicacion.getCreador());
        details.setPost(post);
        details = detailsService.addPostDetails(details); //Se añaden los postDetails a la Base de Datos.

        return new PublicacionDetails(post, details);
    }

    public boolean deletePublicacion(long id) {
        detailsService.deletePostDetails(id); //Se eliminan los details de un post
        Boolean delete = postService.deletePost(id); //Se elimina un post
        return delete;
    }

}
