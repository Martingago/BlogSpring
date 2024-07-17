package com.project.springBlog.services;

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

    public ArrayList<PostModel> getPosts(){
        return (ArrayList<PostModel>) postRepository.findAll();
    }

    public Page<PostModel> getPostSorting(String field, Sort.Direction direction, Pageable pageable){
        return postRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, field)));
    }

    public PostModel getPost(long id){
        Optional<PostModel>  post=  postRepository.findById(id);
        if(post.isPresent()){
            return post.get();
        }else{
            throw new EntityNotFoundException("Post with id: " + id + " was not founded");
        }
    }

    public PostModel addPost(PostModel post){
        return postRepository.save(post);
    }


    public boolean deletePost(long id){
        if(!postRepository.existsById(id)){
            throw new EntityNotFoundException("Error, post was not found");
        }
        try{
            postRepository.deleteById(id);
            return true;
        }
        catch (Exception ex){
            throw  new EntityException("Error during deleting post ", ex);
        }
    }

    public PostModel updatePost(long id, PostModel postUpdated){
        Optional<PostModel> entPost = postRepository.findById(id);
        if(entPost.isEmpty()){
            throw  new EntityNotFoundException("Error, the post to updated was not founded");
        }
        PostModel oldPost = entPost.get();
        oldPost.setTitulo(postUpdated.getTitulo());
        oldPost.setContenido(postUpdated.getContenido());

        return postRepository.save(oldPost);
    }

    /**
     * Obtiene un listado con las ID de los tags existentes en un post.
     * @param post sobre el que se quieren obtener las tagsID
     * @return List con los ID de las tags asociados al post
     */
    public List<Long> getPostTagsList(PostModel post){
        List<Long> listTagsId = new ArrayList<>();

        Set<TagModel> listTags =  post.getTagList();
        for(TagModel tag : listTags){
            listTagsId.add(tag.getId());
        }
        return listTagsId;
    }

    /**
     * Inserta tags en un post determinado
     * @param post sobre el que van a insertar las etiquetas
     * @param etiquetas list de ids con las etiquetas a insertar
     */
    public void insertTagsToList(PostModel post, List<Long> etiquetas){
        if(etiquetas != null && !etiquetas.isEmpty()){
            for(Long id : etiquetas){
                try {
                    TagModel tag = tagService.getTag(id);
                    post.addTag(tag);
                }catch (EntityNotFoundException ex){
                    System.out.println("Tag with id: " + id + " was not found, skipping that one");
                }
            }
            postRepository.save(post);
        }
    }

    /**
     * Elimina tags de un post determinado
     * @param post sobre el que se van a eliminar las etiquetas
     * @param etiquetas list de las ids con las etiquetas a eliminar
     */
    public void removeTagsFromList(PostModel post, List<Long> etiquetas){
        if(etiquetas != null && !etiquetas.isEmpty()){
            for(Long id : etiquetas){
                try{
                    TagModel tag = tagService.getTag(id);
                    post.deteleTag(tag);
                }catch (EntityNotFoundException ex){
                    System.out.println("Tag with id:" + id + " was not found, skipping that one");
                }
            }
            postRepository.save(post);
        }
    }

}
