package com.spaceplorer.spaceplorerweb.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "receipt")
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long totalPrice;

    //선택된 옵션 리스트
    @OneToMany(mappedBy = "receipt")
    @Column(nullable = false)
    private final List<SelectedOption> selectedOptionList = new ArrayList<>();


    public Receipt(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void addSelectedOptionList(SelectedOption selectedOption){
        selectedOptionList.add(selectedOption);
        selectedOption.setReceipt(this);
    }
}
