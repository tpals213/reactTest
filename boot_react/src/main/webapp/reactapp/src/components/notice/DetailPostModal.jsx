// DetailPostModal.js
import React from 'react';

const DetailPostModal = ({ isOpen, onClose, post, onLike, onEdit, isAdmin }) => {
    if (!isOpen || !post) return null;

    return (
        <div style={{
            position: 'fixed',
            top: 0,
            left: 0,
            right: 0,
            bottom: 0,
            backgroundColor: 'rgba(0, 0, 0, 0.5)',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
        }}>
            <div style={{
                backgroundColor: 'white',
                padding: '20px',
                borderRadius: '10px',
                display: 'flex',
                flexDirection: 'column',
            }}>
                <h2>{post.title}</h2>
                <p>{post.content}</p>
                {post.isPinned && <p>이 포스트는 공지사항으로 등록되었습니다.</p>}
                <div style={{display: "flex"}}>
                    <button onClick={onLike} className="btn btn-success">추천하기</button>
                    {
                        isAdmin &&
                        <button onClick={onEdit} className="btn btn-warning">수정하기</button>
                    }
                    <button onClick={onClose} className="btn btn-primary">닫기</button>
                </div>
            </div>
        </div>
    );
};

export default DetailPostModal;
