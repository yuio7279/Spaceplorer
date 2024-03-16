package com.spaceplorer.spaceplorerweb.service;

import com.spaceplorer.spaceplorerweb.common.ApiResponseDto;
import com.spaceplorer.spaceplorerweb.common.Util;
import com.spaceplorer.spaceplorerweb.domain.Category;
import com.spaceplorer.spaceplorerweb.dto.response.CategoryResponseDto;
import com.spaceplorer.spaceplorerweb.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.spaceplorer.spaceplorerweb.common.Messages.*;
import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final Util util;


    //카테고리 생성
    public ResponseEntity<ApiResponseDto<CategoryResponseDto>> createCategory(String categoryName) {

        log.debug("[Category name:{}]", categoryName);
        //중복체크
        Optional<Category> entityTemp = categoryRepository.findByCategoryName(categoryName);
        if(entityTemp.isPresent()){
            log.error("[Duplicated categoryName:{}]", categoryName);
            return util.responseGenerator(BAD_REQUEST, null, DUPLICATED_CATEGORY, BAD_REQUEST.value());
        }

        Category savedEntity = categoryRepository.save(new Category(categoryName));
        return util.generateDtoResponse(log, CREATED, Optional.of(savedEntity), CategoryResponseDto.class, CREATED_CATEGORY);
        }


        //카테고리 제거
    public ResponseEntity<ApiResponseDto<CategoryResponseDto>> deleteCategory(Long id) {

        Optional<Category> entity = categoryRepository.findById(id);

        //중복 체크
        Optional<ResponseEntity<ApiResponseDto<CategoryResponseDto>>> notResponseDto = util.emptyCheckEntity(log, entity);
        if(notResponseDto.isPresent()){
            log.error("[Not found category id:{}]", id);
            return notResponseDto.get();
        }

        //카테고리 제거
        categoryRepository.deleteById(id);
        log.info("[Delete complete category id:{}]", id);
        return util.responseGenerator(OK, null, DELETE_CATEGORY, OK.value());
    }

    //카테고리 조회 ALL
    public ResponseEntity<ApiResponseDto<List<CategoryResponseDto>>> getCategoryAll() {
        List<Category> categoryList = categoryRepository.findAll();
        return util.generateDtoResponse(log, OK, categoryList, CategoryResponseDto.class, FIND_ALL_CATEGORY);
    }


    private List<Category> getCategoryAll_() {
        return categoryRepository.findAll();
    }
}

