package org.ict.boot_react.notice.jpa.entitiy;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNoticeEntity is a Querydsl query type for NoticeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoticeEntity extends EntityPathBase<NoticeEntity> {

    private static final long serialVersionUID = -836579954L;

    public static final QNoticeEntity noticeEntity = new QNoticeEntity("noticeEntity");

    public final DateTimePath<java.util.Date> impEndDate = createDateTime("impEndDate", java.util.Date.class);

    public final StringPath importance = createString("importance");

    public final StringPath noticeContent = createString("noticeContent");

    public final DateTimePath<java.util.Date> noticeDate = createDateTime("noticeDate", java.util.Date.class);

    public final NumberPath<Integer> noticeNo = createNumber("noticeNo", Integer.class);

    public final StringPath noticeTitle = createString("noticeTitle");

    public final StringPath noticeWriter = createString("noticeWriter");

    public final StringPath originalFilePath = createString("originalFilePath");

    public final NumberPath<Integer> readCount = createNumber("readCount", Integer.class);

    public final StringPath renameFilePath = createString("renameFilePath");

    public QNoticeEntity(String variable) {
        super(NoticeEntity.class, forVariable(variable));
    }

    public QNoticeEntity(Path<? extends NoticeEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNoticeEntity(PathMetadata metadata) {
        super(NoticeEntity.class, metadata);
    }

}

