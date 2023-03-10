package com.example.hanastudyboard.repository;

import com.example.hanastudyboard.domain.Article;
import com.example.hanastudyboard.domain.QArticle;
import com.querydsl.core.types.dsl.DateExpression;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>, // 기본적으로 Entity 안에있는 검색 기능을 추가해 줌
                                            // 대소문자 구분 X, EXACT Match. Like 기능 X
        QuerydslBinderCustomizer<QArticle> {

    @Override //QuerydslBinderCustomizer 으로 검색 상세 조건 설정
    default void customize(QuerydslBindings bindings, QArticle root) {
        // 검색 조건 설정
        bindings.excludeUnlistedProperties(true); // List되지 않은  프로퍼티는 검색에서 제외 (default : false)
        bindings.including(root.title, root.hashtag, root.content, root.createdAt, root.createdBy);


//        bindings.bind(root.title).first((path, value) -> path.eq(value)); //EXACT Match
//        bindings.bind(root.title).first(StringExpression::likeIgnoreCase); // like '${v}'
        // like 검색 조건 설정
        bindings.bind(root.title).first((path, value) -> path.containsIgnoreCase(value)); // like '%${}%'
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
