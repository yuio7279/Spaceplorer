package com.spaceplorer.spaceplorerweb.service;

import com.spaceplorer.spaceplorerweb.common.ApiResponseDto;
import com.spaceplorer.spaceplorerweb.common.Util;
import com.spaceplorer.spaceplorerweb.domain.Option;
import com.spaceplorer.spaceplorerweb.domain.Receipt;
import com.spaceplorer.spaceplorerweb.domain.SelectedOption;
import com.spaceplorer.spaceplorerweb.dto.request.ReceiptRequestDto;
import com.spaceplorer.spaceplorerweb.dto.response.ReceiptResponseDto;
import com.spaceplorer.spaceplorerweb.repository.OptionRepository;
import com.spaceplorer.spaceplorerweb.repository.ReceiptRepository;
import com.spaceplorer.spaceplorerweb.repository.SelectedOptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.spaceplorer.spaceplorerweb.common.Messages.*;
import static org.springframework.http.HttpStatus.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReceiptService {

    private final OptionRepository optionRepository;
    private final ReceiptRepository receiptRepository;
    private final SelectedOptionRepository selectedOptionRepository;
    private final Util util;


    @Transactional
    public ResponseEntity<ApiResponseDto<ReceiptResponseDto>> createReceipt(ReceiptRequestDto receiptRequestDto) {
        
        List<Option> selectedOptionEntityList = optionRepository.findByIdIn(receiptRequestDto.getOptionIdList());

        if(selectedOptionEntityList.size() != receiptRequestDto.getOptionIdList().size())
        {
            log.error("[Does not match id list :{} != {}]", selectedOptionEntityList.size(), receiptRequestDto.getOptionIdList().size());
            return util.responseGenerator(BAD_REQUEST, null, NOT_MATCH_OPTION, BAD_REQUEST.value());
        }

        log.info("[SelectedOptionEntityList:{}]",selectedOptionEntityList);

        //총 금액 계산
        Long calculatedTotalPrice = calculateTotalPrice(selectedOptionEntityList);
        log.info("[CalculatedTotalPrice:{}]",calculatedTotalPrice);

        //총 계산금액 비교
        if(!calculatedTotalPrice.equals(receiptRequestDto.getTotalPrice())){
            log.error("[Does not match total price :{} != {}]", receiptRequestDto.getTotalPrice(), calculatedTotalPrice);
            return util.responseGenerator(BAD_REQUEST, null, NOT_MATCH_TOTAL_PRICE, BAD_REQUEST.value());
        }

        Receipt createdReceipt = receiptRepository.save(new Receipt(calculatedTotalPrice));
        Receipt entity = createSelectedOptionEntity(selectedOptionRepository, selectedOptionEntityList, createdReceipt);
        log.info("[Created receipt:{}]",entity);

        return util.generateDtoResponse(log, CREATED, Optional.of(entity), ReceiptResponseDto.class, CREATED_RECEIPT);
    }



    private  Receipt createSelectedOptionEntity(SelectedOptionRepository selectedOptionRepository, List<Option> selectedOptionEntityList, Receipt createdReceipt) {
        for (Option option : selectedOptionEntityList) {
            SelectedOption savedOption = selectedOptionRepository.save(new SelectedOption(createdReceipt, option));
            log.info("[Complete to insert options:{}]",savedOption.getOptionName());
            createdReceipt.addSelectedOptionList(savedOption);
        }

        return createdReceipt;
    }

    private  Long calculateTotalPrice(List<Option> selectedOptionEntityList) {
        Long totalPrice = 0L;
        for (Option option : selectedOptionEntityList) {
            totalPrice += option.getCost();
        }

        return totalPrice;
    }
    public ResponseEntity<ApiResponseDto<ReceiptResponseDto>> getReceiptById(Long id) {
        Optional<Receipt> entity = receiptRepository.findById(id);
        return util.generateDtoResponse(log, OK, entity, ReceiptResponseDto.class, FOUND_RECEIPT);
    }

    public ResponseEntity<ApiResponseDto<List<ReceiptResponseDto>>> getReceiptListByUser() {
        return null;
    }
}
