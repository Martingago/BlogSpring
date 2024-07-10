package com.project.springBlog.services;

import com.project.springBlog.dtos.Publicacion;
import com.project.springBlog.dtos.PublicacionDetails;
import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.PostModel;
import com.project.springBlog.models.TagModel;
import com.project.springBlog.repositories.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    PostDetailsService detailsService;

    @Autowired
    TagService tagService;


    public ArrayList<PostModel> getPosts(){
        return (ArrayList<PostModel>) postRepository.findAll();
    }

    public PostModel getPost(long id){
        Optional<PostModel>  post=  postRepository.findById(id);
        return post.orElse(null);
    }

    public PostModel addPost(PostModel post){
        return postRepository.save(post);
    }

    @Transactional
    public boolean deletePost(long id){
        if(!postRepository.existsById(id)){
            return false;
        }
        try{
            postRepository.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * Obtiene la informacion de una publicación en especifico
     * @param id
     * @return PublicacionDetails => DTO que contiene Post + PostDetails
     */
    public PublicacionDetails getPublicacionDetails(long id){
        PublicacionDetails publicacion = null;
        PostModel post = getPost(id); //Se busca el post
        PostDetailsModel details = null;
        if(post != null){
            details = detailsService.getPostDetails(id);
            return publicacion = new PublicacionDetails(post, details);
        }else{
            return null;
        }
    }

    public PublicacionDetails addPublicacion(Publicacion publicacion){
        PostModel post = null;
        PostDetailsModel details = null;

        //Se crea el contenido del post
        post = new PostModel(publicacion.getTitulo(), publicacion.getContenido());

        //Se añaden las tags:
        List<Integer> etiquetas = publicacion.getTags();

        for(Integer tagId : etiquetas){
            TagModel tag = tagService.getTag(tagId);
            post.addTag(tag);
        }
        post = addPost(post); //Se añade el post a la base de datos empleando el servicio de Posts

        //Se crean los postDetails
        details = new PostDetailsModel(new Date(), publicacion.getCreador());
        details.setPost(post);
        details = detailsService.addPostDetails(details); //Se añaden los postDetails a la Base de Datos.

        return new PublicacionDetails(post, details);
    }

    public boolean deletePublicacion(long id){
        detailsService.deletePostDetails(id); //Se eliminan los details de un post
        Boolean delete = deletePost(id); //Se elimina un post
        return delete;
    }



}
