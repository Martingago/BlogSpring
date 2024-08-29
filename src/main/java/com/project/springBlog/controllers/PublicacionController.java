package com.project.springBlog.controllers;

import com.project.springBlog.config.AppProperties;
import com.project.springBlog.dtos.PublicacionDTO;
import com.project.springBlog.dtos.PublicacionDetailsDTO;
import com.project.springBlog.dtos.ResponseDTO;
import com.project.springBlog.models.UserModel;
import com.project.springBlog.services.PublicacionService;
import com.project.springBlog.services.UserService;
import com.project.springBlog.utils.ValidationErrorUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PublicacionController {

    @Autowired
    AppProperties appProperties;

    @Autowired
    PublicacionService publicacionService;

    @Autowired
    UserService userService;

    /**
     * Obtiene todos los post existentes en la base de datos. Devuelve una lista de la paginación de los post encontrados
     * @param page número de página de la lista total
     * @param size tamaño de la página (máximo 24)
     * @return
     */
    @GetMapping("/public/post")
    public ResponseEntity<ResponseDTO> getPublicaciones(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ){
        int pageNumber = (page != null) ? page : appProperties.getDefaultpPageNumber(); //Establece pagina
        int pageSize = (size != null) ? size : appProperties.getDefaultPageSize(); //Establece tamaño pagina
        List<PublicacionDetailsDTO> listPublicacion =  publicacionService.getPublicacionesDetails(null, null, pageNumber, pageSize);
        return new ResponseEntity<>(new ResponseDTO(true, "List of publicaciones founded", listPublicacion), HttpStatus.OK);
    }

    /**
     * Obtiene todos los post existentes en la base de datos filtrados  por el atributo indicado y ordenados de forma ascendete o descendete.
     * Devuelve una lista paginada de los post encontrados
     * @param field campo por el que se quiere ordenar
     * @param order orden de como se muestran los resultados
     * @param page pagina
     * @param size tamaño de página
     * @return lista paginada resultados encontrados
     */
    @GetMapping ("/public/post/filter")
    public ResponseEntity<ResponseDTO> getPublicacionesSorted(
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String order,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size){

        int pageNumber = (page != null) ? page : appProperties.getDefaultpPageNumber();
        int pageSize = (size != null) ? size : appProperties.getDefaultPageSize();

        List<PublicacionDetailsDTO> listPublicacion = publicacionService.getPublicacionesDetails(field, order, pageNumber, pageSize);
        return new ResponseEntity<>(new ResponseDTO(true, "List of sorted publicaciones founded", listPublicacion), HttpStatus.OK);
    }

    @GetMapping("/public/post/{id}")
    public ResponseEntity<ResponseDTO> getPublicacion(@PathVariable("id") long id){
        PublicacionDetailsDTO publicacion =  publicacionService.getPublicacionDetails(id);
        return new ResponseEntity<>(new ResponseDTO(true, "Post was successfully founded", publicacion), HttpStatus.OK);
    }

    @PostMapping("/editor/post")
    public ResponseEntity<ResponseDTO> addPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO, BindingResult result) {
        if (result.hasErrors()) {
            String err = ValidationErrorUtil.processValidationErrors(result);
            return new ResponseEntity<>(new ResponseDTO(false, err, null), HttpStatus.BAD_REQUEST);
        }
        //Se validan los datos del usuario:
        UserModel creador = userService.getUserAuth();
        publicacionDTO.setCreador(creador);
        PublicacionDetailsDTO pub = publicacionService.addPublicacion(publicacionDTO);
        return new ResponseEntity<>(new ResponseDTO(true, "Post succesfully upload", pub), HttpStatus.OK);
    }

    /**
     * Actualiza una publicación de la base de datos. Sólo los editores/administradores pueden editar publicaciones
     * @param id
     * @param publicacionDTO
     * @param result
     * @return
     */
    @PutMapping("/editor/post/{id}")
    public ResponseEntity<ResponseDTO> updatePublicacion(@PathVariable("id") long id, @Valid @RequestBody PublicacionDTO publicacionDTO, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(new ResponseDTO(false, result.toString(), null), HttpStatus.BAD_REQUEST);
        }
        PublicacionDetailsDTO pub = publicacionService.updatePublicacion(id, publicacionDTO);
        return new ResponseEntity<>(new ResponseDTO(true, "Publicacion was successfully updated", pub), HttpStatus.OK);
    }

    /**
     * Elimina una publicacion de la base de datos - Elimina tambien comentarios asociados a dicha publicación.
     * @param id
     * @return
     */
    @DeleteMapping("/admin/post/{id}")
    public ResponseEntity<ResponseDTO> removePublicacion(@PathVariable("id") long id){
        publicacionService.deletePublicacion(id);
        return new ResponseEntity<>(new ResponseDTO(true, "Post was successfully deleted", null), HttpStatus.OK);
    }

}
