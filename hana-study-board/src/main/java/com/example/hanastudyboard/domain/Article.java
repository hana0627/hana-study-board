package com.example.hanastudyboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
//@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Setter @Column(nullable = false, length = 255)
    private String title; // 제목
    @Setter  @Column(nullable = false, length = 10000)
    private String content; // 본문
    @Setter
    private String hashtag; // 해시태그

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @OrderBy("id")
    @ToString.Exclude
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();




    // 사용자가 임의로 생성하는것을 막음
    protected Article() {
    }

    // private 선언이유?
    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }


    //factory method. new 키워드 사용없이 사용할 수 있게.
    public static Article of(String title, String content, String hashtag) {
        return new Article(title,content,hashtag);
    }

    // 컬렉션 등으로 사용시
    // 리스트에 넣거나 중복요소를 제거하거나, 정렬을 할때 사용할 equals and hashcode
    // id만 같다면 같은 객체임을 알 수 있으므로, id만 체크하였음
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id!= null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


//    @CreatedDate @Column(nullable = false)
//    private LocalDateTime createdAt; //생성일시
//    @CreatedBy @Column(nullable = false, length = 100)
//    private String createdBy; //생성자
//    @LastModifiedDate @Column(nullable = false)
//    private LocalDateTime modifiedAt; // 수정일시
//    @LastModifiedBy @Column(nullable = false, length = 100)
//    private String modifiedBy; //수정자
}
