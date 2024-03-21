package com.spaceplorer.spaceplorerweb.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    //내용 @Lob = 텍스트제한 X
    @Lob
    private String content;

    @Column(nullable = false)
    private Long views;

    private Integer likeCount;

    //상세 게시글 조회 시에만 필요하다.
    //imageUrl
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> imageList = new ArrayList<>();

    //userName, thumbnail
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private City city;

    //게시글 리스트 조회 시에만 필요하다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    //생성 시간
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    //수정 시간
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public Board(String title, String content, City city, User user, Category category) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.city = city;
        this.category = category;
        this.likeCount = 0;
        this.views = 0L;
    }

    //변경감지 더리체킹
    public void incrementViews(){
        views += 1;
    }

    public void addImage(Image image){
        this.imageList.add(image);
        image.setBoard(this);
    }

    //변경감지 더리체킹
    public void setLikeCount(Integer likeCount){
        this.likeCount = likeCount;
    }
}
