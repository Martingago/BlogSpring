package com.project.springBlog.services;

import com.project.springBlog.exceptions.EntityException;

import com.project.springBlog.models.PostDetailsModel;

import com.project.springBlog.repositories.CommentRepository;
import com.project.springBlog.repositories.PostDetailsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;


@Service
public class PostDetailsService {
    @Autowired
    PostDetailsRepository postDetailsRepository;

    @Autowired
    CommentRepository commentRepository;
    /**
     * Añade PostDetailsModels asociado a un PostModel en la BBDD
     * @param postDetails
     * @return
     */
    public PostDetailsModel addPostDetails(PostDetailsModel postDetails){
        try {
            return postDetailsRepository.save(postDetails);
        }catch (Exception ex){
            throw new EntityException("Exception during adding post details", ex);
        }

    }

    public Page<PostDetailsModel> getPostSorting(String field, Sort.Direction direction, Pageable pageable){
        return postDetailsRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, field)));
    }

    public PostDetailsModel getPostDetails(long id){
            Optional<PostDetailsModel> details = postDetailsRepository.findById(id);
            if(details.isPresent()){
                return details.get();
            }else{
                throw new EntityNotFoundException("PostDetails with id: " + id + " where not founded");
            }
        }

    /**
     * Actualiza la información total de unos details: Autor, fecha creacion
      * @param id
     * @param newDetails
     * @return
     */
    public PostDetailsModel updatePostDetails(long id, PostDetailsModel newDetails){
            PostDetailsModel oldDetails  = getPostDetails(id);
            if(newDetails.getCreador() != null){
                oldDetails.setCreador(newDetails.getCreador());
            }

            if(newDetails.getFechaCreacion() != null){
                oldDetails.setFechaCreacion(newDetails.getFechaCreacion());
            }else{
                oldDetails.setFechaCreacion(new Date());
            }
            return postDetailsRepository.save(oldDetails);
    }

    /**
     * Elimina los detalles de un post pasado como id
     * @param id
     * @return
     */
    public boolean deletePostDetails(long id){
        if(!postDetailsRepository.existsById(id)){
            System.out.println("PostDetails to delete with id" + id + " was not founded, skipping that one");
            return false;
        }
        try {
            postDetailsRepository.deleteById(id);
            return true;
        }catch (Exception ex){
            throw  new EntityException("Error during deleting post details", ex);
        }
    }

}
