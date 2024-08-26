package com.project.springBlog.services;

import com.project.springBlog.config.CustomUserDetails;
import com.project.springBlog.models.UserModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    /**
     * Obtiene el identificador de la persona que está realizando la consulta
     * @return identificador usuario realiza consulta
     */
    public long getUserModelId(){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return customUserDetails.getId();
    }

    /**
     * Realiza una comprobacion de la authenticacion del usuario que realiza la petición, comprueba sus roles y
     * verifica si tiene rol de administrador.
     * @return boolean indicando si el usuario que realiza la consulta tiene roles de administración.
     */
    public boolean isAdmin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream().anyMatch(
                grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")
        );
    }

    /**
     * Realiza una comprobación de la authenticacion del usuario que realiza la petición
     * Comprueba si el rol es de editor o administrador
     * @return boolean indicando si el usuario tiene permisos de edicion: ADMIN o EDITOR
     */
    public boolean isEditor(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream().anyMatch(
                grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN" )
                        || grantedAuthority.getAuthority().equals("ROLE_EDITOR")
        );
    }
}
