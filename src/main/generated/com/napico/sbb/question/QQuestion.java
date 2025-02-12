package com.napico.sbb.question;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuestion is a Querydsl query type for Question
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestion extends EntityPathBase<Question> {

    private static final long serialVersionUID = -1718215670L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuestion question = new QQuestion("question");

    public final ListPath<com.napico.sbb.answer.Answer, com.napico.sbb.answer.QAnswer> answerList = this.<com.napico.sbb.answer.Answer, com.napico.sbb.answer.QAnswer>createList("answerList", com.napico.sbb.answer.Answer.class, com.napico.sbb.answer.QAnswer.class, PathInits.DIRECT2);

    public final com.napico.sbb.user.QSiteUser author;

    public final QCategory category;

    public final ListPath<com.napico.sbb.comment.Comment, com.napico.sbb.comment.QComment> commentList = this.<com.napico.sbb.comment.Comment, com.napico.sbb.comment.QComment>createList("commentList", com.napico.sbb.comment.Comment.class, com.napico.sbb.comment.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> modifyDate = createDateTime("modifyDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> readCount = createNumber("readCount", Integer.class);

    public final StringPath subject = createString("subject");

    public final SetPath<com.napico.sbb.user.SiteUser, com.napico.sbb.user.QSiteUser> voter = this.<com.napico.sbb.user.SiteUser, com.napico.sbb.user.QSiteUser>createSet("voter", com.napico.sbb.user.SiteUser.class, com.napico.sbb.user.QSiteUser.class, PathInits.DIRECT2);

    public QQuestion(String variable) {
        this(Question.class, forVariable(variable), INITS);
    }

    public QQuestion(Path<? extends Question> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuestion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuestion(PathMetadata metadata, PathInits inits) {
        this(Question.class, metadata, inits);
    }

    public QQuestion(Class<? extends Question> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new com.napico.sbb.user.QSiteUser(forProperty("author")) : null;
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
    }

}

