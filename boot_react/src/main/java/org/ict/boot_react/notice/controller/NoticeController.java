package org.ict.boot_react.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.boot_react.common.SearchDate;
import org.ict.boot_react.notice.model.dto.NoticeDto;
import org.ict.boot_react.notice.model.service.NoticeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notices")
@RequiredArgsConstructor
@CrossOrigin
public class NoticeController {

    private final NoticeService noticeService;

    // 요청 메소드 구현 부 ----------------------------
    @GetMapping("/ntop3")
    public ResponseEntity<List<NoticeDto>> selectTop3() {
        log.info("notices/ntop3");
        return new ResponseEntity<>(noticeService.selectTop3(), HttpStatus.OK);
    }

    @GetMapping("/list")    // RequestParam는 html의 ajax 부분 data 뒤에 붙여 오는 경우
    public ResponseEntity<List<NoticeDto>> selectList(
            @RequestParam(name="page") int page, @RequestParam(name="limit") int limit
    ) {
        log.info("notices/list : " + page + ", " + limit);
        // JPA 가 제공하는 Pagealbe 객체 사용
        Pageable pageable = PageRequest.of(page-1, limit, Sort.by(Sort.Direction.DESC, "noticeNo"));
        ArrayList<NoticeDto> list = noticeService.selectList(pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/title")    // RequestParam는 html의 ajax 부분 data 뒤에 붙여 오는 경우
    public ResponseEntity<List<NoticeDto>> selectSearchTitle(
            @RequestParam(name="page") int page,
            @RequestParam(name="limit") int limit,
            @RequestParam(name="keyword") String keyword
    ) {
        log.info("notices/title : " + keyword + ", " +  page + ", " + limit);
        // JPA 가 제공하는 Pageable 객체 사용
        Pageable pageable = PageRequest.of(page-1, limit, Sort.by(Sort.Direction.DESC, "noticeNo"));
        ArrayList<NoticeDto> list = noticeService.selectSearchTitle(keyword, pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/content")    // RequestParam는 html의 ajax 부분 data 뒤에 붙여 오는 경우
    public ResponseEntity<List<NoticeDto>> selectSearchcontent(
            @RequestParam(name="page") int page,
            @RequestParam(name="limit") int limit,
            @RequestParam(name="keyword") String keyword
    ) {
        log.info("notices/content : " +keyword + ", " + page + ", " + limit);
        // JPA 가 제공하는 Pagealbe 객체 사용
        Pageable pageable = PageRequest.of(page-1, limit, Sort.by(Sort.Direction.DESC, "noticeNo"));
        ArrayList<NoticeDto> list = noticeService.selectSearchContent(keyword, pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/date")    // RequestParam는 html의 ajax 부분 data 뒤에 붙여 오는 경우
    public ResponseEntity<List<NoticeDto>> selectSearchDate(
            @RequestParam(name="page") int page,
            @RequestParam(name="limit") int limit,
            @RequestParam SearchDate searchDate
    ) {
        log.info("notices/list : " + searchDate + ", " + page + ", " + limit);
        // JPA 가 제공하는 Pagealbe 객체 사용
        Pageable pageable = PageRequest.of(page-1, limit, Sort.by(Sort.Direction.DESC, "noticeNo"));
        ArrayList<NoticeDto> list = noticeService.selectSearchDate(searchDate, pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    // 목록 갯수 조회용
    @GetMapping("/countnotices")
    public ResponseEntity<Long> getCountList(){
        return new ResponseEntity<>(noticeService.selectListCount(), HttpStatus.OK);
    }

    // 제목 검색 갯수 조회용
    @GetMapping("/countSearchTitle")
    public ResponseEntity<Long> getcountSearchTitle(@RequestParam(name="keyword") String keyword){
        return new ResponseEntity<>(noticeService.getSearchTitleCount(keyword), HttpStatus.OK);
    }

    // 내용 검색 갯수 조회용
    @GetMapping("/countSearchcontent")
    public ResponseEntity<Long> getcountSearchcontent(@RequestParam(name="keyword") String keyword){
        return new ResponseEntity<>(noticeService.getSearchContentCount(keyword), HttpStatus.OK);
    }

    // 날짜 검색 갯수 조회용
    @GetMapping("/countSearchDate")
    public ResponseEntity<Long> getcountSearchDate(
            @RequestParam SearchDate searchDate){
        return new ResponseEntity<>(noticeService.getSearchDateCount(searchDate), HttpStatus.OK);
    }

    @GetMapping("/{noticeNo}")
    public ResponseEntity<NoticeDto> selectDetail(@PathVariable("noticeNo") int noticeNo) {
        log.info("상세보기 조회");
        return new ResponseEntity<>(noticeService.selectNotice(noticeNo), HttpStatus.OK);
    }

    @PostMapping    // RequestBody는 html의 ajax 부분 url 뒤에 붙여 오는 경우
    public ResponseEntity<NoticeDto> insert(@RequestBody NoticeDto notice) {
        noticeService.insertNotice(notice);
        return new ResponseEntity<>(notice, HttpStatus.OK);
    }

    @DeleteMapping("/{noticeNo}")
    public ResponseEntity<NoticeDto> delete(@PathVariable("noticeNo") int noticeNo) {
        noticeService.deleteNotice(noticeNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{noticeNo}")
    public ResponseEntity<NoticeDto> update(@PathVariable("noticeNo") int noticeNo, @RequestBody NoticeDto notice) {
        noticeService.updateNotice(notice);
        return new ResponseEntity<>(notice, HttpStatus.OK);
    }



}
