package com.project.springBlog.utils;

import com.project.springBlog.config.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortUtils {

    @Autowired
    AppProperties appProperties;

    /**
     * Si el valor que recibe como tamaño de pagina es mayor al limite permitido, devuelve el valor maximo permitido
     * @param check
     * @return
     */
    public int maxLimitsizePage(int check){
        int limitPage = appProperties.getDefaultMaxPageSize();
        return Math.min(check, limitPage);
    }

    public Sort.Direction directionPageContent(String direction){
        Sort.Direction sortDirection;
        if(direction != null && direction.equals("asc")){
            return Sort.Direction.ASC;
        }else{
            return Sort.Direction.DESC;
        }
    }
}
