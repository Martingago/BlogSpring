package com.project.springBlog.utils;

import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Sort;

public class SortUtils {

    /**
     * Si el valor que recibe como tamaño de pagina es mayor al limite permitido, devuelve el valor maximo permitido
     * @param check
     * @return
     */
    public static int maxLimitsizePage(int check){
        int limitPage = 50;
        return (check <= limitPage) ?  check :limitPage;
    }

    public static Sort.Direction directionPageContent(String direction){
        Sort.Direction sortDirection;
        if(direction != null && direction.equals("asc")){
            return Sort.Direction.ASC;
        }else{
            return Sort.Direction.DESC;
        }
    }
}
