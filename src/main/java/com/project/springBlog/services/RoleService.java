package com.project.springBlog.services;

import com.project.springBlog.models.RoleModel;
import com.project.springBlog.models.UserModel;
import com.project.springBlog.repositories.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    /**
     * Recibe como parámetro un ID y devuelve los datos correspondientes de ese rol
     * @param id rol a buscar
     * @return RoleModel o exception
     */
    public RoleModel getRole(Long id){
        Optional<RoleModel> rol = roleRepository.findById(id);
        if(rol.isPresent()){
            return rol.get();
        }else{
            throw  new EntityNotFoundException("Role was not founded");
        }
    }

    /**
     * Añade desde un listado de ID los roles correspondientes a un usuario especificado
     * @param usuario al que se le añaden los roles
     * @param roles List de IDs con los roles a añadir
     */
    public void insertRolesToUser(UserModel usuario, List<Long> roles) {
        if (roles != null && !roles.isEmpty()) {
            for (Long id : roles) {
                try {
                    RoleModel rol = getRole(id); //Se obtiene el rol
                    usuario.addRol(rol); //Se añade un rol al usuario
                }catch (EntityNotFoundException ex){
                    System.out.println("Role with id "+ id + " was not founded, skipping that one");
                }
                catch (Exception ex) {
                    System.out.println("An unexpected error occurred while adding role");
                }
            }
        }
    }


}
