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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.spaceplorer.spaceplorerweb.common.Messages.CREATED_RECEIPT;
import static com.spaceplorer.spaceplorerweb.common.Messages.NOT_MATCH_TOTAL_PRICE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

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
        
        List<Option> optionEntityList = optionRepository.findAll();
        List<Long> selectedOptionIdList = receiptRequestDto.getOptionIdList();
        log.info("[selectedOptionIdList:{}]",selectedOptionIdList.toString());

        List<Option> selectedOptionEntityList = getSelectedOptionFromOptionList(selectedOptionIdList, optionEntityList);
        log.info("[selectedOptionEntityList:{}]",selectedOptionEntityList);
        
        //옵션 리스트에서 선택된 옵션꺼내 총 금액 계산
        Long calculatedTotalPrice = calculateTotalPrice(selectedOptionEntityList);
        log.info("[calculatedTotalPrice:{}]",calculatedTotalPrice);
        //총 계산금액 비교
        if(!checkTotalPrice(receiptRequestDto, calculatedTotalPrice)){
            log.error("[Does not match total price :{} != {}]", receiptRequestDto.getTotalPrice(), calculatedTotalPrice);
            return util.responseGenerator(BAD_REQUEST, null, NOT_MATCH_TOTAL_PRICE, BAD_REQUEST.value());
        }

        Receipt createdReceipt = receiptRepository.save(new Receipt(calculatedTotalPrice));
        Receipt entity = createSelectedOptionEntity(selectedOptionRepository, selectedOptionEntityList, createdReceipt);
        log.info("[Created receipt:{}]",entity);

        return util.responseGenerator(CREATED, null, CREATED_RECEIPT, CREATED.value());
    }


    private  Receipt createSelectedOptionEntity(SelectedOptionRepository selectedOptionRepository, List<Option> selectedOptionEntityList, Receipt createdReceipt) {
        for (Option option : selectedOptionEntityList) {
            SelectedOption savedOption = selectedOptionRepository.save(new SelectedOption(createdReceipt, option));
            log.info("[Complete to insert options:{}]",savedOption.getOptionName());
            createdReceipt.addSelectedOptionList(savedOption);
        }


        return createdReceipt;
    }

    private boolean checkTotalPrice(ReceiptRequestDto receiptRequestDto, Long calculetedTotalPrice) {
        return calculetedTotalPrice.equals(receiptRequestDto.getTotalPrice());
    }

    private  Long calculateTotalPrice(List<Option> selectedOptionEntityList) {
        Long totalPrice = 0L;
        for (Option option : selectedOptionEntityList) {
            totalPrice += option.getCost();
        }

        return totalPrice;
    }

    private  List<Option> getSelectedOptionFromOptionList(List<Long> selectedOptionIdList, List<Option> optionEntityList) {
        List<Option> selectedOptionEntityList = new ArrayList<>();

        for (Long selectOptionId : selectedOptionIdList) {
            int idx = (int) (selectOptionId - 1);
            Optional<Option> selectedOption = Optional.of(optionEntityList.get(idx));
            selectedOption.ifPresent(selectedOptionEntityList::add);
        }

        return selectedOptionEntityList;
    }

}
