package com.project.springBlog.services;

import com.project.springBlog.dtos.PublicacionDTO;
import com.project.springBlog.dtos.PublicacionDetailsDTO;
import com.project.springBlog.exceptions.EntityException;
import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.PostModel;
import com.project.springBlog.models.TagModel;
import com.project.springBlog.utils.ReflectionUtils;
import com.project.springBlog.utils.SortUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class PublicacionService {

    @Autowired
    PostService postService;

    @Autowired
    PostDetailsService detailsService;

    @Autowired
    SortUtils sortUtils;

    /**
     * Funcion que obtiene un listado de publicaciones ordenables por campo y en orden (asc, desc)
     *
     * @param field campo - puede ser null y se ordenará por defecto por ID
     * @param order orden | asc - desc | puede ser null y se ordenará por defecto desc
     * @param page  int pagina | obligatorio|
     * @param size  tamaño contenidos página | obligatorio | Su valor máximo será de 50
     * @return
     */
    public List<PublicacionDetailsDTO> getPublicacionesDetails(String field, String order, int page, int size) {
        //Lista datos a devolver
        List<PublicacionDetailsDTO> listPublicacionOrdered = new ArrayList<>(); //Lista datos a devolver

        //VALIDACIONES
        Sort.Direction sortOrder = sortUtils.directionPageContent(order);
        String sortField = (field != null && !field.isEmpty()) ? field : "id"; //Si field es null o vacio se pone por default id
        int limitSize = sortUtils.maxLimitsizePage(size); //Comprueba valor limite de pagina admitido por el servidor

        //Se crea un objeto con la página a mostrar:
        PageRequest pageRequest = PageRequest.of(page, limitSize, Sort.by(sortOrder, sortField)); //Nº pagina, tamaño, y orden
        try {
            if (ReflectionUtils.hasField(PostModel.class, field)) { //Filtra por atributos de postModel
                //Se obtiene una pagina de PostModels ordenada.
                Page<PostModel> postsPage = postService.getPostSorting(sortField, sortOrder, pageRequest);
                //Se convierte en un PublicacionDetails filtro por atributos de PostModel
                for (PostModel post : postsPage) {
                    try {
                        listPublicacionOrdered.add(getPublicacionDetails(post));
                    } catch (EntityNotFoundException ex) {
                        System.out.println(ex + " skipping that one");
                    }
                }
            } else if (ReflectionUtils.hasField(PostDetailsModel.class, field)) { //Filtra por atributos de postDetails
                Page<PostDetailsModel> detailsPage = detailsService.getPostSorting(sortField, sortOrder, pageRequest);
                //Se convierte en un PublicacionDetails filtrado por atriburos de PostDetailsModel
                for (PostDetailsModel details : detailsPage) {
                    listPublicacionOrdered.add(getPublicacionDetails(details));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching sorted posts " + e.getMessage());
        }
        return listPublicacionOrdered;
    }


    /**
     * Obtiene la informacion de una publicación en especifico
     *
     * @param id
     * @return PublicacionDetails => DTO que contiene Post + PostDetails
     */
    public PublicacionDetailsDTO getPublicacionDetails(long id) {
        PostModel post = postService.getPost(id);
        PostDetailsModel details = detailsService.getPostDetails(id);
        return new PublicacionDetailsDTO(post, details);
    }

    public PublicacionDetailsDTO getPublicacionDetails(PostModel post) {
        PostDetailsModel details = detailsService.getPostDetails(post.getId());
        return new PublicacionDetailsDTO(post, details);
    }

    public PublicacionDetailsDTO getPublicacionDetails(PostDetailsModel details) {
        PostModel post = postService.getPost(details.getId());
        return new PublicacionDetailsDTO(post, details);
    }

    @Transactional
    public PublicacionDetailsDTO addPublicacion(PublicacionDTO publicacionDTO) {
        try {
            PostModel post = null;
            PostDetailsModel details = null;

            //Se crea el contenido del post
            post = new PostModel(publicacionDTO.getTitulo(), publicacionDTO.getContenido());

            //Se añaden las tags:
            List<Long> etiquetas = publicacionDTO.getTags();
            if (etiquetas != null) {
                postService.insertTagsToList(post, etiquetas);
            }
            //Se añade el post a la base de datos empleando el servicio de Posts
            post = postService.addPost(post);
            //Se crean los postDetails
            details = new PostDetailsModel(new Date(), publicacionDTO.getCreador());
            details.setPost(post);
            details = detailsService.addPostDetails(details); //Se añaden los postDetails a la Base de Datos.

            return new PublicacionDetailsDTO(post, details);
        } catch (EntityException ex) {
            throw new EntityException("Error during updating publication: ", ex);
        }
    }

    @Transactional
    public boolean deletePublicacion(long id) {
        detailsService.deletePostDetails(id); //Se eliminan los details de un post
        return postService.deletePost(id); //Se elimina un post; si no se encuentra hace un throw entity not founded
    }

    @Transactional
    public PublicacionDetailsDTO updatePublicacion(long id, PublicacionDTO publicacionDTO) {
        PostModel dataPost = new PostModel(publicacionDTO.getTitulo(), publicacionDTO.getContenido());
        PostModel updatedPost = postService.updatePost(id, dataPost); //Datos del post actualizados

        List<Long> oldTagsList = postService.getPostTagsList(updatedPost); //Se obtienen las tags registradas que se deben actualizar
        List<Long> updatedtagsList = publicacionDTO.getTags(); //Se obtienen las tags con las que se va a actualizar el post

        //Se crean 2 nuevas listas con los valores a añadir, y los valores a eliminar:
        List<Long> addedTags = new ArrayList<>(updatedtagsList); //Listado con los ID a añadir
        addedTags.removeAll(oldTagsList);

        List<Long> removedTags = new ArrayList<>(oldTagsList); //Listado con los ID a eliminar
        removedTags.removeAll(updatedtagsList);

        postService.insertTagsToList(updatedPost, addedTags);
        postService.removeTagsFromList(updatedPost, removedTags);
        System.out.println("------------------------------------------");
        System.out.println("Actualizando la informacion");
        //Actualizar los post details
        PostDetailsModel details = new PostDetailsModel(null, publicacionDTO.getCreador());
        PostDetailsModel updatedDetails = detailsService.updatePostDetails(id, details);

        return new PublicacionDetailsDTO(updatedPost, updatedDetails);
    }

}
