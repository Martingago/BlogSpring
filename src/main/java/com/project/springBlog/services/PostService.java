package com.project.springBlog.services;

import com.project.springBlog.dtos.PostDTO;
import com.project.springBlog.exceptions.EntityException;
import com.project.springBlog.models.PostModel;
import com.project.springBlog.models.TagModel;
import com.project.springBlog.repositories.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    TagService tagService;


    /**
     * Obtiene una pagina con un listado de post
     * @param field atributo de filtrado de los post
     * @param direction asc | desc
     * @param pageable
     * @return
     */
    public Page<PostModel> getPostSorting(String field, Sort.Direction direction, Pageable pageable){
        return postRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, field)));
    }

    /**
     * Obtiene la información especifica de un post pasado como id
     * @param id
     * @return
     */
    public PostModel getPost(long id){
        Optional<PostModel>  post=  postRepository.findById(id);
        if(post.isPresent()){
            return post.get();
        }else{
            throw new EntityNotFoundException("Post with id: " + id + " was not founded");
        }
    }

    /**
     * Añade un objeto PostModel en la Base de datos
     * @param post
     * @return
     */
    public PostModel addPost(PostModel post){
        return postRepository.save(post);
    }

    /**
     * Elimina un post pasado como id de la base de datos
     * @param id
     * @return
     */
    public boolean deletePost(long id){
        if(!postRepository.existsById(id)){
            throw  new EntityNotFoundException("Post to delete with id " + id + " was not founded");
        }
        try{
            postRepository.deleteById(id);
            return true;
        }
        catch (Exception ex){
            throw  new EntityException("Error during deleting post ", ex);
        }
    }

    /**
     * Actualiza la informacion de un post
     * Añade las tags nuevas al oldPostData
     * Elimina las tags de oldPostData
     * @param oldPostData
     * @param newPost
     * @return
     */
    public PostModel updatePost(PostModel oldPostData, PostDTO newPost){

        //Se establecen el titulo y el contenido del post:
        oldPostData.setTitulo(newPost.getTitulo());
        oldPostData.setContenido(newPost.getContenido());

        //Obtener los 2 sets a comparar:
        Set<Long> oldTagsIdList = getPostTagsList(oldPostData); //Tags antiguas de la publicacion
        Set<Long> newTagsIdList = newPost.getTagsIdList(); //Tags nuevas de la publicacion

        //Se crean Set auxiliares: Sets tags a añadir
        Set<Long> tagsToAdd = new HashSet<>(newTagsIdList);
        tagsToAdd.removeAll(oldTagsIdList);

        //Se crean Set Auxiliares: Set tags a eliminar
        Set<Long> tagsToRemove = new HashSet<>(oldTagsIdList);
        tagsToRemove.removeAll(newTagsIdList);
        System.out.println("Se añaden tags al post");
        //Se llama a la funcion para añadir los tags:
        oldPostData = insertTagsToList(oldPostData,tagsToAdd);
        System.out.println("Se eliminan tags del post");
        //Se llama a la funcion para eliminar los tags:
        oldPostData =  removeTagsFromList(oldPostData, tagsToRemove);

        //Se guardan los datos y se devuelve el nuevo oldPostData
        postRepository.save(oldPostData);
        return oldPostData;
    }

    /**
     * Obtiene un listado con las ID de los tags existentes en un post.
     * @param post sobre el que se quieren obtener las tagsID
     * @return List con los ID de las tags asociados al post
     */
    public Set<Long> getPostTagsList(PostModel post){
        Set<Long> listTagsId = new HashSet<>();

        Set<TagModel> listTags =  post.getTagList();
        for(TagModel tag : listTags){
            listTagsId.add(tag.getId());
        }
        return listTagsId;
    }

    /**
     * Añade tags a un objeto PostModel
     * @param post
     * @param tagsToAdd
     * @return
     */
    public PostModel insertTagsToList(PostModel post, Set<Long> tagsToAdd){
        if(tagsToAdd != null && !tagsToAdd.isEmpty()){
            for(long id : tagsToAdd){
                try{
                    System.out.println("--- Añadida tag al post ---");
                    post.addTag(tagService.getTag(id));
                }catch (EntityNotFoundException ex){
                    System.out.println("Tag with id: " + id + " was not founded, skipping that one");
                }
            }
        }
        return post;
    }

    /**
     * Elimina tags de un post determinado
     * @param post         sobre el que se van a eliminar las etiquetas
     * @param tagsToRemove list de las ids con las etiquetas a eliminar
     */
    public PostModel removeTagsFromList(PostModel post, Set<Long> tagsToRemove) {
        if (tagsToRemove != null && !tagsToRemove.isEmpty()) { //Si existen valores en etiquetas se ejecuta
            Set<TagModel> tagsFromPost = post.getTagList(); //Se obtiene el Set de Tags contenientes en el post

            Iterator<TagModel> tagsToCompare = tagsFromPost.iterator(); // Iterator de las tags de un post
            while (tagsToCompare.hasNext()) {
                TagModel tag = tagsToCompare.next();
                if (tagsToRemove.contains(tag.getId())) {
                    post.deteleTag(tag);
                }
            }
        }
        return post;
    }

}
