package com.project.springBlog.services;

import com.project.springBlog.dtos.Publicacion;
import com.project.springBlog.dtos.PublicacionDetails;
import com.project.springBlog.exceptions.EntityException;
import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.PostModel;
import com.project.springBlog.utils.ReflectionUtils;
import com.project.springBlog.utils.SortUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PublicacionService {

    @Autowired
    PostService postService;

    @Autowired
    PostDetailsService detailsService;

    /**
     * Funcion que obtiene un listado de publicaciones ordenables por campo y en orden (asc, desc)
     * @param field campo - puede ser null y se ordenará por defecto por ID
     * @param order orden | asc - desc | puede ser null y se ordenará por defecto desc
     * @param page int pagina | obligatorio|
     * @param size tamaño contenidos página | obligatorio | Su valor máximo será de 50
     * @return
     */
    public List<PublicacionDetails> getPublicacionesDetails(String field, String order, int page, int size){
        //Lista datos a devolver
        List<PublicacionDetails> listPublicacionOrdered = new ArrayList<>(); //Lista datos a devolver

        //VALIDACIONES
        Sort.Direction sortOrder = SortUtils.directionPageContent(order);
        String sortField = (field != null && !field.isEmpty()) ? field : "id"; //Si field es null o vacio se pone por default id
        int limitSize = SortUtils.maxLimitsizePage(size); //Comprueba valor limite de pagina admitido por el servidor

        //Se crea un objeto con la página a mostrar:
        PageRequest pageRequest = PageRequest.of(page,limitSize, Sort.by(sortOrder, sortField)); //Nº pagina, tamaño, y orden
        try {
        if(ReflectionUtils.hasField(PostModel.class, field)){

                //Se obtiene una pagina de PostModels ordenada.
                Page<PostModel> postsPage = postService.getPostSorting(sortField, sortOrder, pageRequest);
                //Se convierte en postDetails:
                for(PostModel post : postsPage){
                    listPublicacionOrdered.add(getPublicacionDetails(post));
                }
            }
        else if(ReflectionUtils.hasField(PostDetailsModel.class, field)){
               Page<PostDetailsModel> detailsPage = detailsService.getPostSorting(sortField, sortOrder, pageRequest);
               //Se convierte en postDetails:
                for(PostDetailsModel details : detailsPage){
                    listPublicacionOrdered.add(getPublicacionDetails(details));
                }
        }
        }catch (Exception e){
            throw  new RuntimeException("An error occurred while fetching sorted posts " +e.getMessage());
        }
        return listPublicacionOrdered;
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

    public PublicacionDetails getPublicacionDetails(PostModel post){
            PostDetailsModel details = detailsService.getPostDetails(post.getId());
            return new PublicacionDetails(post, details);
    }

    public PublicacionDetails getPublicacionDetails(PostDetailsModel details){
        PostModel post = postService.getPost(details.getId());
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
