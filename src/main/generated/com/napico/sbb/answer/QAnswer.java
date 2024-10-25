package com.napico.sbb.answer;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnswer is a Querydsl query type for Answer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnswer extends EntityPathBase<Answer> {

    private static final long serialVersionUID = 1799397642L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnswer answer = new QAnswer("answer");

    public final com.napico.sbb.user.QSiteUser author;

    public final ListPath<com.napico.sbb.comment.Comment, com.napico.sbb.comment.QComment> commentList = this.<com.napico.sbb.comment.Comment, com.napico.sbb.comment.QComment>createList("commentList", com.napico.sbb.comment.Comment.class, com.napico.sbb.comment.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final com.napico.sbb.question.QQuestion question;

    public final SetPath<com.napico.sbb.user.SiteUser, com.napico.sbb.user.QSiteUser> voter = this.<com.napico.sbb.user.SiteUser, com.napico.sbb.user.QSiteUser>createSet("voter", com.napico.sbb.user.SiteUser.class, com.napico.sbb.user.QSiteUser.class, PathInits.DIRECT2);

    public QAnswer(String variable) {
        this(Answer.class, forVariable(variable), INITS);
    }

    public QAnswer(Path<? extends Answer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnswer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnswer(PathMetadata metadata, PathInits inits) {
        this(Answer.class, metadata, inits);
    }

    public QAnswer(Class<? extends Answer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new com.napico.sbb.user.QSiteUser(forProperty("author")) : null;
        this.question = inits.isInitialized("question") ? new com.napico.sbb.question.QQuestion(forProperty("question"), inits.get("question")) : null;
    }

}

