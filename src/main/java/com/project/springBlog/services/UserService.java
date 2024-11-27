package com.project.springBlog.services;

import com.project.springBlog.config.AppProperties;
import com.project.springBlog.dtos.CommentDTO;
import com.project.springBlog.dtos.UserDTO;
import com.project.springBlog.dtos.user.UserRequestDTO;
import com.project.springBlog.dtos.user.UserResponseDTO;
import com.project.springBlog.mapper.CommentMapper;
import com.project.springBlog.mapper.UserMapper;
import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.RoleModel;
import com.project.springBlog.models.TagModel;
import com.project.springBlog.models.UserModel;
import com.project.springBlog.repositories.PostDetailsRepository;
import com.project.springBlog.repositories.RoleRepository;
import com.project.springBlog.repositories.UserRepository;
import com.project.springBlog.utils.ReflectionUtils;
import com.project.springBlog.utils.SortUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostDetailsRepository detailsRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    SortUtils sortUtils;

    /**
     * Funcion que obtiene los datos de un usuario pasando como parametro su nombre
     * @param username
     * @return
     */
    public UserModel findByUsername(String username){
        Optional<UserModel> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            return user.get();
        }else{
            throw  new UsernameNotFoundException("Username was not founded");
        }
    }

    /**
     * Funcion que obtiene un usuario por su Identificador.
     * Si el usuario no es encontrado devuelve una exception de entity not found
     * @param id del usuario a buscar
     * @return UserModel del usuario encontrado || EntityNotFoundException
     */
    public UserModel findUserById(long id){
        Optional<UserModel> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw  new EntityNotFoundException("User with id " + id + " was not founded");
        }
        return user.get();
    }

    /**
     * Función que obtiene un listado de usuarios paginados ordenados
     * @param field
     * @param order
     * @param page
     * @param size
     * @return
     */
    public Page<UserResponseDTO> getUsersPaginated(String field, String order, int page, int size){
        // Validaciones de filtro
        Sort.Direction sortOrder = sortUtils.directionPageContent(order);

        //Valida que el field que recibe exista dentro de userModel, en caso de no existir será id por defecto
        String sortField = (field != null && !field.isEmpty() && ReflectionUtils.hasField(UserModel.class, field)) ? field : "id";
        int limitSize = sortUtils.maxLimitsizePage(size); //limita el tamaño maximo de paginacion

        // Creación de la página a mostrar
        PageRequest pageRequest = PageRequest.of(page, limitSize, Sort.by(sortOrder, sortField));
        Page<UserModel> usersPage = getUsersSorting(sortField, sortOrder, pageRequest);

        //Convertir el UserModel en un UserResponseDTO
        return usersPage.map(UserMapper::toUserResponseDTO);
    }


    /**
     * Obtiene un objeto Page de usuarios ordenados
     * @param field
     * @param direction
     * @param pageable
     * @return
     */
    public Page<UserModel> getUsersSorting(String field, Sort.Direction direction, Pageable pageable){
        return userRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize() ,Sort.by(direction,field)));
    }


    /**
     * Busca en la base de datos a un usuario tanto por su identificador(id) tanto como por su username
     * @param identificador String que puede ser tanto su id, como su username
     * @return usermodel del usuario encontrado o null si no existe
     */
    public UserModel findUserByIdOrUsername(String identificador){
        UserModel userModel;
        try{
            long id = Long.parseLong(identificador);
            userModel = findUserById(id);
        }catch (NumberFormatException ex){
            userModel = findByUsername(identificador);
        }
        return  userModel;
    }

    /**
     * Obtiene un listado de todos los comentarios que ha escrito un usuario en específico
     * @param user UserModel del cual se quiere extraer los comentarios escritos
     * @return Set con los commentDTO escritos por el usuario
     */
    public Set<CommentDTO> getUserComments(UserModel user){
        return user.getComentariosList().stream().map(CommentMapper::toDTO).collect(Collectors.toSet());
    }

    /**
     * Funcion que emplea la sobrecarga del método createUser(UserModel, boolean) para crear un usuario de roll USER
     * Inyecta y sobreescribe automácticamente el roll de USER al objeto recibido para evitar vulnerabilidades de roles.
     * @param newUser datos del usuario a crear
     * @return objeto creado en la BBDD con el roll fijado en USER
     */
    public UserModel createUser(UserRequestDTO newUser){
        return createUser(newUser, false);
    }

    /**
     * Funcion que permite crear un usuario con roll de USER, o roles de ADMINISTRACION
     * Los roles deben ser recibidos en el objeto newUser, no se produce ninguna inyección por defecto
     * @param newUserDTO información del usuario a crear
     * @param isAdmin boolean indicando si es ADMINISTRADOR o no
     * @return objeto creado en la BBDD con el roll recibido
     */
    public UserModel createUser(UserRequestDTO newUserDTO, boolean isAdmin){
        //Comprueba que el usuario no exista en la BBDD
        Optional<UserModel> existingUser =  userRepository.findByUsername(newUserDTO.getUsername());
        if(existingUser.isPresent()){
            throw  new DuplicateKeyException("Username already exists");
        }
        newUserDTO.setPassword(passwordEncoder.encode(newUserDTO.getPassword())); //Se codifica el string de password
        //Se crea un modelo de usuario
        UserModel newUser = new UserModel(newUserDTO.getUsername(), newUserDTO.getPassword(), newUserDTO.getName());
        RoleModel userRol = roleService.getRole("USER");
        newUser.addRol(userRol);
        if(isAdmin){
            roleService.insertRolesToUser(newUser, newUserDTO.getRoles()); //Le añade los roles correspondientes
        }
        userRepository.save(newUser);
        return newUser;
    }

    /**
     * Elimina los datos de un usuario con un ID especificado.
     * ESTA FUNCION NO COMPRUEBA PERMISOS DE USUARIO. Es necesario comprobar previamente el AUTH de quien está eliminando datos
     * @param id del usuario a eliminar
     * @return boolean si ha tenido éxito, error si el usuario no se ha encontrado.
     */
    @Transactional
    public boolean deleteUser(Long id) {
        UserModel userToDelete = findUserById(id);
        if (!userToDelete.getPostList().isEmpty()) {
            //Se busa la cuenta de la administracion
            UserModel userAdmin = userRepository.findById(appProperties.getAdminAccountId())
                    .orElseThrow(() -> new RuntimeException("Admin user not found")
                    );
            //Se modifican los post asociados a dicho usuario y se establece su creador en la administracion
            for (PostDetailsModel details : userToDelete.getPostList()) {
                details.setCreador(userAdmin);
                detailsRepository.save(details);
            }
        }
        //Se elimina el usuario
        userRepository.delete(userToDelete);
        return true;
    }

    /**
     * Actualiza información básica de un usuario (nombre y contraseña)
     * @param oldData
     * @param updatedData
     * @return
     */
    public UserDTO updateUser(UserModel oldData, UserDTO updatedData){
        if(updatedData.getPassword() != null){ //Actualiza contraseña
            oldData.setPassword(passwordEncoder.encode(updatedData.getPassword())); //Actualiza password
        }
        oldData.setName(updatedData.getName()); //Actualiza nombre
        userRepository.save(oldData);
        return UserMapper.toDTO(oldData);
    }

    /**
     * Actualiza los roles de usuario
     * @param user
     * @param basicRoles
     * @return
     */
    public UserDTO updateUserRoles(UserModel user , List<Long> basicRoles){
        if(!basicRoles.contains(1L)) basicRoles.add(1L); //Añade rol de User si no está incluido por defecto
        List<RoleModel> roles = roleRepository.findAllById(basicRoles);
        Set<RoleModel> rolesSet = new HashSet<>(roles);
        user.setRolesList(rolesSet); //Actualiza roles de usuario
        userRepository.save(user);
        return UserMapper.toDTO(user);
    }

    /**
     * Realiza una authenticacion del usuario que realiza una solicitud y devuelve los datos de dicho usuario
     * @return
     */
    public UserModel getUserAuth(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return findByUsername(auth.getName());
    }
}
