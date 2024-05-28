package org.ict.boot_react.notice.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.boot_react.common.SearchDate;
import org.ict.boot_react.notice.jpa.entitiy.NoticeEntity;
import org.ict.boot_react.notice.jpa.repository.NoticeQueryRepository;
import org.ict.boot_react.notice.jpa.repository.NoticeRepository;
import org.ict.boot_react.notice.model.dto.NoticeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NoticeService {
    // JPA 가 제공하는 기본 메소드를 사용하기 위해서
    private final NoticeRepository noticeRepository;
    private final NoticeQueryRepository noticeQueryRepository;

    // 메소드 작성부 -------------------------------

    public ArrayList<NoticeDto> selectTop3() {
        // jpa 제공
        // 등록 날짜 기준으로 내림차순 정렬한 리스트를 받아서 0~2번 인덱스 추출
        List<NoticeEntity> entityList = noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "noticeNo"));;
        // List<NoticeEntity> entityList = noticeQueryRepository.findNewTop3();
        ArrayList<NoticeDto> list = new ArrayList<>();
        for(int i=0; i<3; i++){
            list.add(entityList.get(i).toDto());
        }
        return list;
    }

    // 페이징 처리를 위한 총 목록 갯수 조회
    public long selectListCount(){
        // jpa 제공 : count()
        return noticeRepository.count();
    }

    public ArrayList<NoticeDto> selectList(Pageable pageable) {
        Page<NoticeEntity> entities =  noticeRepository.findAll(pageable);
        ArrayList<NoticeDto> list = new ArrayList<>();
        for(NoticeEntity entity : entities){
            list.add(entity.toDto());
        }
        return list;
    }

    public NoticeDto selectNotice(int noticeNo) {
        // jpa 제공 : findById(id) : Optional<T>
        // 엔티티에 등록된 id에 해당하는 property 를 사용해서 조회함
        Optional<NoticeEntity> entity = noticeRepository.findById(noticeNo);
        noticeQueryRepository.updateReadCount(noticeNo);
        return entity.get().toDto();
    }


    public void insertNotice(NoticeDto noticeDto) {
        // save(entity) : return Entity
        // jpa 제공, insert문과 update문 처리용 메소드
        // 마지막  글 번호 조회
        NoticeDto notice = noticeQueryRepository.findLast().toDto();
        noticeDto.setNoticeNo(notice.getNoticeNo()+1);  // 새로 등록할 글 번호로 추가
        noticeRepository.save(noticeDto.toEntity());
    }

    public void updateNotice(NoticeDto noticeDto){
        noticeRepository.save(noticeDto.toEntity());
    }

    public int deleteNotice(int noticeNo) {
        try{
            noticeRepository.deleteById(noticeNo)
            ;
            return 1;
        }
        catch(Exception e){
            log.error(e.getMessage());
            return 0;
        }
    }

    // 검색 카운트 추가 메소드 사용 ------------------------
    public long getSearchTitleCount(String keyword){

        return noticeQueryRepository.countByTitle(keyword);
    }

    public long getSearchContentCount(String keyword){

        return noticeQueryRepository.countByContent(keyword);
    }

    public long getSearchDateCount(SearchDate searchDate){
        return noticeQueryRepository.countBySearchDate(searchDate);
    }


    // 검색 관련 추가 메소드 사용 --------------------------
    public ArrayList<NoticeDto> selectSearchTitle(String keyword, Pageable pageable){
        List<NoticeEntity> entities = noticeQueryRepository.findBySearchTitle(keyword, pageable);
        ArrayList<NoticeDto> list = new ArrayList<>();
        for(NoticeEntity entity : entities){
            list.add(entity.toDto());
        }
        return list;
    }

    public ArrayList<NoticeDto> selectSearchContent(String keyword, Pageable pageable){
        List<NoticeEntity> entities = noticeQueryRepository.findBySearchContent(keyword, pageable);
        ArrayList<NoticeDto> list = new ArrayList<>();
        for(NoticeEntity entity : entities){
            list.add(entity.toDto());
        }
        return list;
    }

    public ArrayList<NoticeDto> selectSearchDate(SearchDate searchDate, Pageable pageable){
        List<NoticeEntity> entities = noticeQueryRepository.findBySearchDate(searchDate, pageable);
        ArrayList<NoticeDto> list = new ArrayList<>();
        for(NoticeEntity entity : entities){
            list.add(entity.toDto());
        }
        return list;
    }




}
