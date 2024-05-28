package org.ict.boot_react.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.boot_react.common.SearchDate;
import org.ict.boot_react.member.model.dto.MemberDto;
import org.ict.boot_react.member.model.service.MemberService;
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
@RequestMapping("/members")
@RequiredArgsConstructor
@CrossOrigin
public class MemberController {

    private final MemberService memberService;

    // 요청 메소드 구현 부 ----------------------------
    
    @GetMapping("/list")    // RequestParam는 html의 ajax 부분 data 뒤에 붙여 오는 경우
    public ResponseEntity<List<MemberDto>> selectList(
            @RequestParam(name="page") int page, @RequestParam(name="limit") int limit
    ) {
        log.info("notices/list : " + page + ", " + limit);
        // JPA 가 제공하는 Pagealbe 객체 사용
        Pageable pageable = PageRequest.of(page-1, limit, Sort.by(Sort.Direction.DESC, "enrollDate"));
        ArrayList<MemberDto> list = memberService.selectList(pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/userid")    // RequestParam는 html의 ajax 부분 data 뒤에 붙여 오는 경우
    public ResponseEntity<List<MemberDto>> selectSearchUserid(
            @RequestParam(name="page") int page,
            @RequestParam(name="limit") int limit,
            @RequestParam(name="keyword") String keyword
    ) {
        log.info("notices/userid : " + keyword + ", " +  page + ", " + limit);
        // JPA 가 제공하는 Pageable 객체 사용
        Pageable pageable = PageRequest.of(page-1, limit, Sort.by(Sort.Direction.DESC, "enrollDate"));
        ArrayList<MemberDto> list = memberService.selectSearchUserid(keyword, pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/gender")    // RequestParam는 html의 ajax 부분 data 뒤에 붙여 오는 경우
    public ResponseEntity<List<MemberDto>> selectSearchGender(
            @RequestParam(name="page") int page,
            @RequestParam(name="limit") int limit,
            @RequestParam(name="keyword") String keyword
    ) {
        log.info("notices/gender : " +keyword + ", " + page + ", " + limit);
        // JPA 가 제공하는 Pagealbe 객체 사용
        Pageable pageable = PageRequest.of(page-1, limit, Sort.by(Sort.Direction.DESC, "enrollDate"));
        ArrayList<MemberDto> list = memberService.selectSearchGender(keyword, pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/age")    // RequestParam는 html의 ajax 부분 data 뒤에 붙여 오는 경우
    public ResponseEntity<List<MemberDto>> selectSearchAge(
            @RequestParam(name="page") int page,
            @RequestParam(name="limit") int limit,
            @RequestParam(name="keyword") int age
    ) {
        log.info("notices/age : " +age + ", " + page + ", " + limit);
        // JPA 가 제공하는 Pagealbe 객체 사용
        Pageable pageable = PageRequest.of(page-1, limit, Sort.by(Sort.Direction.DESC, "enrollDate"));
        ArrayList<MemberDto> list = memberService.selectSearchAge(age, pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/loginok")    // RequestParam는 html의 ajax 부분 data 뒤에 붙여 오는 경우
    public ResponseEntity<List<MemberDto>> selectSearchLoginOK(
            @RequestParam(name="page") int page,
            @RequestParam(name="limit") int limit,
            @RequestParam(name="keyword") String keyword
    ) {
        log.info("notices/loginok : " +keyword + ", " + page + ", " + limit);
        // JPA 가 제공하는 Pagealbe 객체 사용
        Pageable pageable = PageRequest.of(page-1, limit, Sort.by(Sort.Direction.DESC, "enrollDate"));
        ArrayList<MemberDto> list = memberService.selectSearchLoginOK(keyword, pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @GetMapping("/date")    // RequestParam는 html의 ajax 부분 data 뒤에 붙여 오는 경우
    public ResponseEntity<List<MemberDto>> selectSearchEnrollDate(
            @RequestParam(name="page") int page,
            @RequestParam(name="limit") int limit,
            @RequestParam SearchDate searchDate
    ) {
        log.info("notices/date : " + searchDate + ", " + page + ", " + limit);
        // JPA 가 제공하는 Pagealbe 객체 사용
        Pageable pageable = PageRequest.of(page-1, limit, Sort.by(Sort.Direction.DESC, "enrollDate"));
        ArrayList<MemberDto> list = memberService.selectSearchEnrollDate(searchDate, pageable);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    // 목록 갯수 조회용
    @GetMapping("/countmember")
    public ResponseEntity<Long> getCountList(){
        return new ResponseEntity<>(memberService.selectListCount(), HttpStatus.OK);
    }

    // 제목 검색 갯수 조회용
    @GetMapping("/countSearchUserid")
    public ResponseEntity<Long> selectSearchIDCount(@RequestParam(name="keyword") String keyword){
        return new ResponseEntity<>(memberService.selectSearchIDCount(keyword), HttpStatus.OK);
    }

    // 내용 검색 갯수 조회용
    @GetMapping("/countSearchGender")
    public ResponseEntity<Long> selectSearchGenderCount(@RequestParam(name="keyword") String keyword){
        return new ResponseEntity<>(memberService.selectSearchGenderCount(keyword), HttpStatus.OK);
    }

    @GetMapping("/countSearchAge")
    public ResponseEntity<Long> selectSearchAgeCount(@RequestParam(name="keyword") int age) {
        return new ResponseEntity<>(memberService.selectSearchAgeCount(age), HttpStatus.OK);
    }

    @GetMapping("/countSearchLoginOK")
    public ResponseEntity<Long> selectSearchLoginOKCount(@RequestParam(name="keyword") String keyword) {
        return new ResponseEntity<>(memberService.selectSearchLoginOKCount(keyword), HttpStatus.OK);
    }

    // 날짜 검색 갯수 조회용
    @GetMapping("/countSearchDate")
    public ResponseEntity<Long> selectSearchDateCount(
            @RequestParam SearchDate searchDate){
        return new ResponseEntity<>(memberService.selectSearchDateCount(searchDate), HttpStatus.OK);
    }




    @GetMapping("/{userId}")
    public ResponseEntity<MemberDto> selectDetail(@PathVariable("userId") String userId) {
        log.info("상세보기 조회");
        return new ResponseEntity<>(memberService.selectMember(userId), HttpStatus.OK);
    }

    @PostMapping    // RequestBody는 html의 ajax 부분 url 뒤에 붙여 오는 경우
    public ResponseEntity<MemberDto> insert(@RequestBody MemberDto member) {
        memberService.insertMember(member);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<MemberDto> delete(@PathVariable("userId") String userId) {
        memberService.deleteMember(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<MemberDto> update(@PathVariable("userId") MemberDto member) {
        log.info("컨트롤러");
        memberService.updateMember(member);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }


}
