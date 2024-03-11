package com.spaceplorer.spaceplorerweb.service;

import com.spaceplorer.spaceplorerweb.common.ApiResponseDto;
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
import static com.spaceplorer.spaceplorerweb.common.Util.responseGenerator;
import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ResponseEntity<ApiResponseDto<CategoryResponseDto>> createCategory(String categoryName) {

        //카테고리 명이 잘못됨
        log.info("[Category name:{}]", categoryName);
        if (categoryName == null || categoryName.isEmpty()) {
            log.error("[Invalid input categoryName:{}]", categoryName);

            return responseGenerator(BAD_REQUEST, null, BAD_REQUEST_CATEGORY, BAD_REQUEST.value());
        }

        //카테고리 명이 중복됨
        Optional<Category> entityTemp = categoryRepository.findByCategoryName(categoryName);
        if(entityTemp.isPresent()){
            log.error("[Duplicated categoryName:{}]", categoryName);
            return responseGenerator(BAD_REQUEST, null, DUPLICATED_CATEGORY, BAD_REQUEST.value());
        }

        //카테고리 생성
        Category entity = new Category(categoryName);
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto(categoryRepository.save(entity));
        log.info("[Created categoryResponseDto:{}]", categoryResponseDto);
        return responseGenerator(CREATED, categoryResponseDto, CREATED_CATEGORY, CREATED.value());
    }


    public ResponseEntity<ApiResponseDto<CategoryResponseDto>> deleteCategory(Long id) {

        Optional<Category> entity = categoryRepository.findById(id);
        //존재하지 않는 카테고리 id
        if(entity.isEmpty()){
            log.error("[Not found category id:{}]", id);
            return responseGenerator(
                    NOT_FOUND, null, NOT_FOUND_CATEGORY, NOT_FOUND.value());
        }

        //카테고리 제거
        categoryRepository.deleteById(id);
        log.info("[Delete complete category id:{}]", id);
        return responseGenerator(OK, null, DELETE_CATEGORY, OK.value());
    }

    public ResponseEntity<ApiResponseDto<List<CategoryResponseDto>>> getCategoryAll() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponseDto> responseDto = categoryList.stream().map(CategoryResponseDto::new).toList();

        log.info("[Load to all categories:{}]", responseDto);
        return responseGenerator(OK, responseDto, FIND_ALL_CATEGORY, OK.value());
    }


    private List<Category> getCategoryAll_() {
        return categoryRepository.findAll();
    }
}

