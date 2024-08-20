package com.project.springBlog.utils;

import com.project.springBlog.config.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortUtils {

    @Autowired
    AppProperties appProperties;

    public Pageable createPageable(Integer page, Integer size){
        int pageNumber = (page !=null) ? page : appProperties.getDefaultpPageNumber();
        int pageTmp = (size != null) ? size : appProperties.getDefaultPageSize();
        int pageSize = maxLimitsizePage(pageTmp);

        return PageRequest.of(pageNumber, pageSize);
    }


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
