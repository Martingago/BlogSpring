package com.project.springBlog.services;

import com.project.springBlog.dtos.ResponseDTO;
import com.project.springBlog.dtos.TagDTO;
import com.project.springBlog.exceptions.EntityException;
import com.project.springBlog.mapper.TagMapper;
import com.project.springBlog.models.TagModel;
import com.project.springBlog.repositories.TagRepository;
import com.project.springBlog.utils.SortUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;


    /**
     * Devuelve una list de objetos TagDTO filtrados y paginados
     * @param field
     * @param order
     * @param page
     * @param size
     * @return
     */
    public List<TagDTO> getTags(String field, String order, int page, int size) {
        List<TagDTO> listTags = new ArrayList<>(); //lista datos a devolver
        //Validaciones de filtro
        Sort.Direction sortOrder = SortUtils.directionPageContent(order);
        String sortField = (field != null && !field.isEmpty()) ? field : "id";
        int limitSize = SortUtils.maxLimitsizePage(size);

        //Creacion de la página a mostrar
        PageRequest pageRequest = PageRequest.of(page,limitSize, Sort.by(sortOrder,sortField));
        Page<TagModel> tagsPage = getTagsSorting(sortField, sortOrder, pageRequest);
        for(TagModel tag : tagsPage){
            listTags.add(TagMapper.toSimpleDTO(tag));
        }
        return listTags;
    }

    public Page<TagDTO> getTagsPaginated(String field, String order, int page, int size) {
        // Validaciones de filtro
        Sort.Direction sortOrder = SortUtils.directionPageContent(order);
        String sortField = (field != null && !field.isEmpty()) ? field : "id";
        int limitSize = SortUtils.maxLimitsizePage(size);

        // Creación de la página a mostrar
        PageRequest pageRequest = PageRequest.of(page, limitSize, Sort.by(sortOrder, sortField));
        Page<TagModel> tagsPage = getTagsSorting(sortField, sortOrder, pageRequest);

        // Convertir Page<TagModel> a Page<TagDTO>
        Page<TagDTO> tagsDtoPage = tagsPage.map(TagMapper::toSimpleDTO);

        return tagsDtoPage;
    }


    public Page<TagModel> getTagsSorting(String field, Sort.Direction direction, Pageable pageable){
        return tagRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize() ,Sort.by(direction,field)));
    }

    public TagModel getTag(long id) {
            Optional<TagModel> tagModel = tagRepository.findById(id);
            if(tagModel.isPresent()){
                return tagModel.get();
            }else{
                throw new EntityNotFoundException("Tag was not founded");
            }
    }

    public TagModel addTag(TagModel tag) {
        try {
            TagModel addedTag = tagRepository.save(tag);
            return addedTag;
        } catch (Exception ex) {
            throw new EntityException("An error occurred while tag was being added", ex);
        }
    }

    @Transactional
    public void deleteTag(long id) {
        //Comprueba que el elemento exista en la base de datos.
        if (!tagRepository.existsById(id)) {
            throw new EntityNotFoundException("Tag to be removed was not found");
        }
        try {
            tagRepository.deleteById(id);
        } catch (Exception e) {
            throw new EntityException("An error occurred while the tag was being removed: \n", e);
        }
    }

    @Transactional
    public TagModel updateTag(long id, TagModel tagUpdated) {
        Optional<TagModel> opTag = tagRepository.findById(id);
        if (opTag.isEmpty()) {
            throw new EntityNotFoundException("Error, tag was not found");
        }
        try {
            //Se actualizan los atributos del tag
            TagModel oldTag = opTag.get();
            oldTag.setNombre(tagUpdated.getNombre());
            return tagRepository.save(oldTag);
        } catch (Exception ex) {
            throw new EntityException("An error occurred while data was being updated: ", ex);
        }
    }
}
