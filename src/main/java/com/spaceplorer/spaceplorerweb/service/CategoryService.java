package com.spaceplorer.spaceplorerweb.service;

import com.spaceplorer.spaceplorerweb.common.ApiResponseDto;
import com.spaceplorer.spaceplorerweb.common.Messages;
import com.spaceplorer.spaceplorerweb.domain.Category;
import com.spaceplorer.spaceplorerweb.dto.response.CategoryResponseDto;
import com.spaceplorer.spaceplorerweb.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto<>
                    (Messages.BAD_REQUEST_CATEGORY, HttpStatus.BAD_REQUEST.value()));
        }

        //카테고리 명이 중복됨
        Optional<Category> entityTemp = categoryRepository.findByCategoryName(categoryName);
        if(entityTemp.isPresent()){
            log.error("[Duplicated categoryName:{}]", categoryName);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto<>
                    (Messages.DUPLICATED_CATEGORY, HttpStatus.BAD_REQUEST.value()));
        }

        //카테고리 생성
        Category entity = new Category(categoryName);
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto(categoryRepository.save(entity));
        log.info("[Created categoryResponseDto:{}]", categoryResponseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto<>
                (categoryResponseDto, Messages.CREATED_CATEGORY, HttpStatus.CREATED.value()));
    }


    public ResponseEntity<ApiResponseDto<CategoryResponseDto>> deleteCategory(Long id) {

        Optional<Category> entity = categoryRepository.findById(id);
        //존재하지 않는 카테고리 id
        if(entity.isEmpty()){
            log.error("[Not found category id:{}]", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDto<>
                    (null, Messages.NOT_FOUND_CATEGORY, HttpStatus.NOT_FOUND.value()));
        }

        //카테고리 제거
        categoryRepository.deleteById(id);
        log.info("[Delete complete category id:{}]", id);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>
                (null, Messages.DELETE_CATEGORY, HttpStatus.OK.value()));
    }

    public ResponseEntity<ApiResponseDto<List<CategoryResponseDto>>> getCategoryAll() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponseDto> responseDto = categoryList.stream().map(CategoryResponseDto::new).toList();

        log.info("[Load to all categories:{}]", responseDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponseDto<>(
                        responseDto, Messages.FIND_ALL_CATEGORY, HttpStatus.OK.value()));
    }

    private List<Category> getCategoryAll_() {
        return categoryRepository.findAll();
    }
}

