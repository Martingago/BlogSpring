package com.project.springBlog.services;

import com.project.springBlog.dtos.Publicacion;
import com.project.springBlog.dtos.PublicacionDetails;
import com.project.springBlog.exceptions.EntityException;
import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.PostModel;
import com.project.springBlog.repositories.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class PublicacionService {

    @Autowired
    PostService postService;

    @Autowired
    PostDetailsService detailsService;

    /**
     * Obtiene la informacion de todas las publicaciones existentes en la base de datos
     * @return
     */
    public List<PublicacionDetails> getPublicacionesDetails(){
        List<PostModel> posts = postService.getPosts();
        List<PublicacionDetails> listPublicaciones = new ArrayList<>();

        for(PostModel post : posts){
            PostDetailsModel details = detailsService.getPostDetails(post.getId());
            PublicacionDetails publicacion = new PublicacionDetails(post, details);
            listPublicaciones.add(publicacion);
        }
        return listPublicaciones;
    }

    public List<PublicacionDetails> getPublicacionesDetails(int from, int to){
        List<PublicacionDetails> listPublicaciones = new ArrayList<>();

        return listPublicaciones;
    }

    public List<PublicacionDetails> getPublicacionesDetails(String field){
        List<PublicacionDetails> listPublicaciones = new ArrayList<>();
        List<PostModel> posts = postService.getPostSorting(field);
        for(PostModel post  : posts){
            PostDetailsModel details = detailsService.getPostDetails(post.getId());
            PublicacionDetails publicacion = new PublicacionDetails(post, details);
            listPublicaciones.add(publicacion);
        }

        return listPublicaciones;
    }


    /**
     * Obtiene la informacion de una publicación en especifico
     *
     * @param id
     * @return PublicacionDetails => DTO que contiene Post + PostDetails
     */
    public PublicacionDetails getPublicacionDetails(long id) {
            PostModel post = postService.getPost(id);
            PostDetailsModel details = detailsService.getPostDetails(id);
            return new PublicacionDetails(post, details);
    }

    @Transactional
    public PublicacionDetails addPublicacion(Publicacion publicacion) {
        try {
            PostModel post = null;
            PostDetailsModel details = null;

            //Se crea el contenido del post
            post = new PostModel(publicacion.getTitulo(), publicacion.getContenido());

            //Se añaden las tags:
            List<Long> etiquetas = publicacion.getTags();
            if (etiquetas != null) {
                postService.insertTagsToList(post, etiquetas);
            }
            //Se añade el post a la base de datos empleando el servicio de Posts
            post = postService.addPost(post);
            //Se crean los postDetails
            details = new PostDetailsModel(new Date(), publicacion.getCreador());
            details.setPost(post);
            details = detailsService.addPostDetails(details); //Se añaden los postDetails a la Base de Datos.

            return new PublicacionDetails(post, details);
        }catch (EntityException ex){
            throw  new EntityException("Error during updating publication: ", ex);
        }
    }

    @Transactional
    public boolean deletePublicacion(long id) {
        detailsService.deletePostDetails(id); //Se eliminan los details de un post
        return postService.deletePost(id); //Se elimina un post
    }

    @Transactional
    public PublicacionDetails updatePublicacion(long id, Publicacion publicacion) {
        PostModel dataPost = new PostModel(publicacion.getTitulo(), publicacion.getContenido());
        PostModel updatedPost = postService.updatePost(id, dataPost); //Datos del post actualizados

        //Actualizar las tags del post:
        List<Long> oldTagsList = postService.getPostTagsList(updatedPost); //Se obtienen las tags registradas que se deben actualizar
        List<Long> updatedtagsList = publicacion.getTags(); //Se obtienen las tags con las que se va a actualizar el post

        //Se crean 2 nuevas listas con los valores a añadir, y los valores a eliminar:
        List<Long> addedTags = new ArrayList<>(updatedtagsList); //Listado con los ID a añadir
        addedTags.removeAll(oldTagsList);

        List<Long> removedTags = new ArrayList<>(oldTagsList); //Listado con los ID a eliminar
        removedTags.removeAll(updatedtagsList);

        postService.insertTagsToList(updatedPost, addedTags);
        postService.removeTagsFromList(updatedPost, removedTags);

        //Actualizar los post details
        PostDetailsModel details = new PostDetailsModel(null, publicacion.getCreador());
        PostDetailsModel updatedDetails = detailsService.updatePostDetails(id, details);

        return new PublicacionDetails(updatedPost, updatedDetails);
    }

}
