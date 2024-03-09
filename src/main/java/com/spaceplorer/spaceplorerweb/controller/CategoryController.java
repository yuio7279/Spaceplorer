package com.spaceplorer.spaceplorerweb.controller;

import com.spaceplorer.spaceplorerweb.common.ApiResponseDto;
import com.spaceplorer.spaceplorerweb.dto.response.CategoryResponseDto;
import com.spaceplorer.spaceplorerweb.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    //카테고리 생성
    @PostMapping("/category/{category_name}")
    public ResponseEntity<ApiResponseDto<CategoryResponseDto>> createCategory(@PathVariable("category_name") String categoryName){


        return categoryService.createCategory(categoryName);
    }
    //카테고리 제거
    @DeleteMapping("/category/{id}")
    public ResponseEntity<ApiResponseDto<CategoryResponseDto>> deleteCategory(@PathVariable("id") Long id){
        return categoryService.deleteCategory(id);
    }

    //카테고리 조회 ALL
    @GetMapping()
    public ResponseEntity<ApiResponseDto<List<CategoryResponseDto>>> getCategoryAll(){
        return categoryService.getCategoryAll();
    }
}

