package com.spaceplorer.spaceplorerweb.controller;


import com.spaceplorer.spaceplorerweb.common.ApiResponseDto;
import com.spaceplorer.spaceplorerweb.dto.request.ReceiptRequestDto;
import com.spaceplorer.spaceplorerweb.dto.response.ReceiptResponseDto;
import com.spaceplorer.spaceplorerweb.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 *  옵션은 쿼리파라미터방식을 사용
 *
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/receipts")
public class ReceiptController {
    private final ReceiptService receiptService;

    //영수증 생성
    @PostMapping()
    public ResponseEntity<ApiResponseDto<ReceiptResponseDto>> createReceipt(@RequestBody ReceiptRequestDto requestDto){
        return receiptService.createReceipt(requestDto);
    }
    //영수증 조회
    @GetMapping("/{receipt_id}")
    public ResponseEntity<ApiResponseDto<ReceiptResponseDto>> getReceiptById(@PathVariable("receipt_id") Long id){

        log.info("[getReceiptById:]");
        return receiptService.getReceiptById(id);
    }
    //회원 별 영수증 조회
    @GetMapping()
    public ResponseEntity<ApiResponseDto<List<ReceiptResponseDto>>> getReceiptListByUser(){

        log.info("[getReceiptById:]");
        return receiptService.getReceiptListByUser();
    }
}
