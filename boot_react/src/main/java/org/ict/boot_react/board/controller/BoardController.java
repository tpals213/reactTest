package org.ict.boot_react.board.controller;


import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.boot_react.board.model.dto.BoardDto;
import org.ict.boot_react.board.model.service.BoardService;
import org.ict.boot_react.board.model.service.ReplyService;
import org.ict.boot_react.common.FileNameChange;
import org.ict.boot_react.member.model.dto.MemberDto;
import org.ict.boot_react.member.model.service.MemberService;
import org.ict.boot_react.security.jwt.util.JWTUtil;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
@CrossOrigin    //리액트 애플리케이션(포트가 다름)의 자원 요청을 처리하기 위함
public class BoardController {
    private final BoardService boardService;
    private final ReplyService replyService;

    // 로그인 확인을 위해
    private final JWTUtil jwtUtil;
    private final MemberService memberService;


    @GetMapping("btop3")
    public ResponseEntity<List<BoardDto>> selectTop3() {
        log.info("/boards/btop3 => selectTop3()");
        ArrayList<BoardDto> boardList = boardService.selectTop3();
        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BoardDto>> selectList(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        log.info("/boards/list :  " + page + "," + limit);
        //JPA가 제공하는 Pageable 객체를 사용함
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "boardNum"));
        //페이지에 출력할 목록 조회해옴  => 응답 처리
        // 총 페이지 수 계산
        int t = boardService.countTotalRecords();
        double tt = Math.ceil((double) t / limit);
        int totalPage = (int) tt;

        // 페이지에 출력할 목록 조회해옴  => 응답 처리
        List<BoardDto> boardList = boardService.selectList(pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("totalPage", String.valueOf(totalPage));
        log.info(headers + "1");
        return ResponseEntity.ok().headers(headers).body(boardList);
    }

    @GetMapping("/title")
    public ResponseEntity<List<BoardDto>> selectSearchTitle(@RequestParam(name = "page") int page, @RequestParam(name = "limit") int limit, @RequestParam(name = "keyword") String keyword) {
        log.info("/boards/list :  " + page + "," + limit);
        //JPA가 제공하는 Pageable 객체를 사용함
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "boardNum"));
        //페이지에 출력할 목록 조회해옴  => 응답 처리
        return new ResponseEntity<>(boardService.selectSearchTitle(keyword, pageable), HttpStatus.OK);
    }

    @GetMapping("/writer")
    public ResponseEntity<List<BoardDto>> selectSearchWriter(@RequestParam(name = "page") int page, @RequestParam(name = "limit") int limit, @RequestParam(name = "keyword") String keyword) {
        log.info("/boards/list :  " + page + "," + limit);
        //JPA가 제공하는 Pageable 객체를 사용함
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "boardNum"));
        //페이지에 출력할 목록 조회해옴  => 응답 처리
        return new ResponseEntity<>(boardService.selectSearchWriter(keyword, pageable), HttpStatus.OK);
    }

    @GetMapping("/date")
    public ResponseEntity<List<BoardDto>> selectSearchDate(@RequestParam(name = "page") int page, @RequestParam(name = "limit") int limit, @RequestParam(name = "begin") java.sql.Date begin, @RequestParam(name = "end") java.sql.Date end) {
        log.info("/boards/list :  " + page + "," + limit);
        //JPA가 제공하는 Pageable 객체를 사용함
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "boardNum"));
        //페이지에 출력할 목록 조회해옴  => 응답 처리
        return new ResponseEntity<>(boardService.selectSearchDate(pageable, begin, end), HttpStatus.OK);
    }

    //목록 갯수 조회용
    @GetMapping("/countBoards")
    public ResponseEntity<Long> getCountBoards() {
        return new ResponseEntity<Long>(boardService.getCountBoards(), HttpStatus.OK);
    }

    //제목 검색 목록 갯수 조회용
    @GetMapping("/countSearchTitle")
    public ResponseEntity<Long> getCountSearchTitle(@RequestParam(name = "keyword") String keyword) {
        return new ResponseEntity<Long>(boardService.getCountSearchTitle(keyword), HttpStatus.OK);
    }

    //작성자 검색 목록 갯수 조회용
    @GetMapping("/countBoardsWriter")
    public ResponseEntity<Long> getCountSearchWriter(@RequestParam(name = "keyword") String keyword) {
        return new ResponseEntity<Long>(boardService.getCountSearchWriter(keyword), HttpStatus.OK);
    }

    //날짜 검색 목록 갯수 조회용
    @GetMapping("/countBoardsDate")
    public ResponseEntity<Long> getCountSearchDate(@RequestParam(name = "begin") java.sql.Date begin, @RequestParam(name = "end") java.sql.Date end) {
        return new ResponseEntity<>(boardService.getCountSearchDate(begin, end), HttpStatus.OK);
    }


    @GetMapping("/{boardNum}")
    public ResponseEntity<BoardDto> selectOne(@PathVariable(name = "boardNum") int boardNum) {
        BoardDto board = boardService.selectBoard(boardNum);
        log.info(board.toString());
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @PostMapping    // HTTP POST 요청을 "/boards" 로 매핑함
    public ResponseEntity<?> insertBoard(HttpServletRequest request,
                                         @RequestBody BoardDto boardDto,
                                         @RequestParam("upfile") MultipartFile multipartFile) throws IOException {

        // 게시글 등록 요청 처리용 메소드
        // 로그인한 회원만 게시글 등록 가능
        log.info("insertBoard : " + boardDto);

        // 로그인한 회원이 요청한 것인지 확인 처리
        String token = request.getHeader("Authorization").substring("Bearer".length()); // substring(7)
        String userId = jwtUtil.getUserIdFromToken(token);  // 토큰에서 사용자 아이디 추출
        // 참고 : Role 이 ADMIN인지 확인도 필요
        // String adminYN = jwtUtil.getAdminFromToken(token);   // 토큰에서 관리자 여부 추출
        // if(adminYN.equals("N")){    // 관리자가 아니라면
        //     return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근 불가");
        // }

        // 아이디로 회원 정보 조회
        MemberDto loginMember = memberService.selectMember(userId);

        if (loginMember == null) {    // 조회된 사용자 정보가 없다면
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404 Not Found");
        }

        // 업로드된 첨부파일이 있다면 저장 및 등록 처리
        if (multipartFile.isEmpty()) {
            // 저장 폴더 경로 만들기
            String savePath = request.getServletContext().getRealPath("/board_upfiles");
            // 업로드된 파일 이름 추출
            String fileName = multipartFile.getOriginalFilename();
            // 폴더에 저장 시 사용할 변경된 파일 이름 만들기
            String renameFileName = FileNameChange.change(fileName, "yyyyMMddHHmmdd");
            // java.io.File 로 파일 객체 만들기
            File file = new File(savePath + "\\" + renameFileName);
            // 저장 폴더에 바뀐 이름 이름으로 저장 처리
            multipartFile.transferTo(file);

            boardDto.setBoardOriginalFilename(fileName);
            boardDto.setBoardRenameFilename(renameFileName);
        }

        boardDto.setBoardWriter(loginMember.getUserId());

        //정상 로그인 상태이면, 글 등록 처리
        boardService.insertBoard(boardDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{boardNum}")
    public ResponseEntity<BoardDto> updateBoard(@PathVariable("boardNum") int boardNum, @RequestBody BoardDto board) {
        log.info(board.toString());
        board.setBoardNum(boardNum);
        return boardService.updateBoard(board);
    }

    @DeleteMapping("/{boardNum}")
    public void deleteBoard(@PathVariable int boardNum) {
        boardService.deleteBoard(boardNum);
    }

    // 파일 다운로드 처리
    @GetMapping("/fdown")
    public ResponseEntity<InputStreamResource> fileDownload(
            @RequestParam("ofile") String originalFileName,
            @RequestParam("rfile") String renamedFileName,
            HttpServletRequest request) throws IOException {

        log.info(originalFileName, renamedFileName);
        // 저장 경로 지정
        String savePath = request.getServletContext().getRealPath("/board_upfiles");
        // 파일 객체 생성
        File readfile = new File(savePath + "\\" + renamedFileName);
        // 파일 읽기용 스트림 준비
        FileInputStream fileInputStream = new FileInputStream(readfile);
        InputStreamResource resource = new InputStreamResource(fileInputStream);

        // 파일의 미디어 타입 얻기
        ServletContext servletContext = request.getServletContext();
        String mimeType = servletContext.getMimeType(originalFileName);
        MediaType mediaType = MediaType.parseMediaType(mimeType);


        // ResponseEntity : HTTP 응답 객체, HTTP 응답의 상태 코드, 헤더, 본문 등 등록 설정
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + originalFileName)
                .contentType(mediaType)
                .contentLength(readfile.length())
                .body(resource);

    }


}
