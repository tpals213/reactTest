import React from 'react';
import { observer } from "mobx-react";
import { formatDistanceToNow, parseISO } from 'date-fns';
import { ko } from 'date-fns/locale';

const PostCard = observer(({ notice, isPinned = false, onNoticeClick  }) => {
    const relativeTime = (isoDate) => formatDistanceToNow(parseISO(isoDate), { addSuffix: true, locale: ko });

    return (
        <tr onClick={onNoticeClick} style={{ background: isPinned ? "skyblue" : "none" }}>
            <td style={{ width: "5vw", textAlign: "center" }}>{isPinned ? "공지" : "일반"}</td>
            <td style={{ textAlign: "left" }}>{notice.noticeTitle}</td>
            <td style={{ width: "10vw", textAlign: "center" }}>{notice.noticeWriter}</td>
            <td style={{ width: "7vw", textAlign: "center" }}>{relativeTime(notice.noticeDate)}</td>
            <td style={{ width: "4vw", textAlign: "center" }}>{notice.noticeContent}</td>
            <td style={{ width: "4vw", textAlign: "center" }}>{notice.readCount}</td>
        </tr>
    );
});

export default PostCard;
