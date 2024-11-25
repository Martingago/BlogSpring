package com.project.springBlog.services;

import com.project.springBlog.dtos.PostDTO;
import com.project.springBlog.dtos.PublicacionDTO;
import com.project.springBlog.dtos.PublicacionDetailsDTO;
import com.project.springBlog.exceptions.EntityException;
import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.PostModel;
import com.project.springBlog.repositories.PostDetailsRepository;
import com.project.springBlog.repositories.PostRepository;
import com.project.springBlog.utils.ReflectionUtils;
import com.project.springBlog.utils.SortUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

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
     * @return Page<PublicacionDetailsDTO>
     */
    public Page<PublicacionDetailsDTO> getPublicacionesDetails(String field, String order, int page, int size) {
        // VALIDACIONES
        Sort.Direction sortOrder = sortUtils.directionPageContent(order); // Si el order no es válido establece "asc"
        String sortField = (field != null && !field.isEmpty()) ? field : "id"; // Si field es null o vacío, por defecto id
        int limitSize = sortUtils.maxLimitsizePage(size); // Comprueba valor límite permitido

        // Crea un objeto de paginación
        PageRequest pageRequest = PageRequest.of(page, limitSize, Sort.by(sortOrder, sortField));

        // Determina si el campo pertenece a PostModel o PostDetailsModel
        try {
            if (ReflectionUtils.hasField(PostModel.class, field)) {
                // Filtra por PostModel
                Page<PostModel> postsPage = postService.getPostSorting(sortField, sortOrder, pageRequest);

                // Convierte a PublicacionDetailsDTO
                return postsPage.map(this::getPublicacionDetails);

            } else if (ReflectionUtils.hasField(PostDetailsModel.class, field)) {
                // Filtra por PostDetailsModel
                Page<PostDetailsModel> detailsPage = detailsService.getPostSorting(sortField, sortOrder, pageRequest);

                // Convierte a PublicacionDetailsDTO
                return detailsPage.map(this::getPublicacionDetails);
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching sorted posts: " + e.getMessage());
        }
        // Si el campo no pertenece a ninguno, realiza la consulta por "id"
        PageRequest fallbackRequest = PageRequest.of(page, limitSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<PostModel> fallbackPage = postService.getPostSorting("id", Sort.Direction.DESC, fallbackRequest);
        return fallbackPage.map(this::getPublicacionDetails);
    }

    /**
     * Obtiene la información de una publicación específica
     *
     * @param id El ID de la publicación
     * @return PublicacionDetailsDTO que contiene Post y PostDetails; valores nulos si no se encuentran.
     */
    public PublicacionDetailsDTO getPublicacionDetails(long id) {
        PostModel post = new PostModel();
        PostDetailsModel details = new PostDetailsModel();

        try {
            // Intentar obtener el PostModel
            post = postService.getPost(id);
        } catch (EntityNotFoundException ex) {
            System.err.println("Post not found for ID: " + id + " - Returning null for Post.");
        }
        try {
            // Intentar obtener el PostDetailsModel
            details = detailsService.getPostDetails(id);
        } catch (EntityNotFoundException ex) {
            System.err.println("PostDetails not found for ID: " + id + " - Returning null for PostDetails.");
        }

        // Retornar el DTO con valores que pueden ser nulos
        return new PublicacionDetailsDTO(post, details);
    }

    /**
     * Maneja la conversión segura de un PostModel a PublicacionDetailsDTO, retornando nulos si no encuentra datos relacionados.
     * @param post El PostModel a convertir.
     * @return PublicacionDetailsDTO con valores nulos en caso de error.
     */
    private PublicacionDetailsDTO getPublicacionDetails(PostModel post) {
        try {
            PostDetailsModel details = detailsService.getPostDetails(post.getId());
            return new PublicacionDetailsDTO(post, details);
        } catch (EntityNotFoundException ex) {
            System.err.println("PostDetails not found for Post ID: " + post.getId() + " - Returning null values.");
            return new PublicacionDetailsDTO(post, new PostDetailsModel());
        }
    }

    /**
     * Maneja la conversión segura de un PostDetailsModel a PublicacionDetailsDTO, retornando nulos si no encuentra datos relacionados.
     * @param details El PostDetailsModel a convertir.
     * @return PublicacionDetailsDTO con valores nulos en caso de error.
     */
    private PublicacionDetailsDTO getPublicacionDetails(PostDetailsModel details) {
        try {
            PostModel post = postService.getPost(details.getId());
            return new PublicacionDetailsDTO(post, details);
        } catch (EntityNotFoundException ex) {
            System.err.println("Post not found for PostDetails ID: " + details.getId() + " - Returning null values.");
            return new PublicacionDetailsDTO(new PostModel(), details);
        }
    }

    @Transactional
    public PublicacionDetailsDTO addPublicacion(PublicacionDTO publicacionDTO) {
        try {
            //Se crea un nuevo post
            PostModel post = new PostModel(publicacionDTO.getTitulo(), publicacionDTO.getContenido());

            // Se añaden las tags
            Set<Long> etiquetas = publicacionDTO.getTags();


            post = postService.addTagsToPost(post, etiquetas);

            // Se añade el post a la base de datos
            post = postService.addPost(post);

            // Se crean y persisten los detalles del post
            PostDetailsModel details = new PostDetailsModel(new Date(), publicacionDTO.getCreador());
            details.setPost(post); //Se establece el post asociado con los postDetails

            // Se añaden los postDetails a la Base de Datos.
            details = detailsService.addPostDetails(details);

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
        //Se comprueba que el post a actualizar existe
        PostModel postModel =  postService.getPost(id);
        //Se crea un DTO para actualizar datos de los post
        PostDTO postDTO = new PostDTO(publicacionDTO.getTitulo(), publicacionDTO.getContenido(), publicacionDTO.getTags());

        PostModel updatedPost = postService.updatePost(postModel,postDTO); //Datos del post actualizados

        //Actualizar los post details
        PostDetailsModel details = new PostDetailsModel(null, publicacionDTO.getCreador());
        PostDetailsModel updatedDetails = detailsService.updatePostDetails(id, details);

        //Se devuelven los datos
        return new PublicacionDetailsDTO(updatedPost, updatedDetails);
    }

}
