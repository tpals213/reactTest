package org.ict.boot_react.member.model.service;

import lombok.extern.slf4j.Slf4j;
import org.ict.boot_react.common.SearchDate;
import org.ict.boot_react.member.jpa.entity.MemberEntity;
import org.ict.boot_react.member.jpa.repository.MemberRepository;
import org.ict.boot_react.member.model.dto.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional

@Slf4j
public class MemberService {
	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository){
		this.memberRepository = memberRepository;
	}

	@Transactional
	public MemberDto insertMember(MemberDto member) {
		//save() -> 성공시 Entity, 실패시 null 리턴함, JPA 가 제공하는 메소드임
		return memberRepository.save(member.toEntity()).toDto();
	}

	public MemberDto selectMember(String userid) {
		//repository 에 메소드 추가함
		return memberRepository.findByUserId(userid).toDto();  //userId 를 이용한 회원 정보 select 요청
	}

	@Transactional
	public MemberDto updateMember(MemberDto member) {
		//패스워드 수정이 있는 경우
		//save() -> 성공시 Entity, 실패시 null 리턴함
		log.info("서비스");
		return memberRepository.save(member.toEntity()).toDto();
	}

	@Transactional
	public int deleteMember(String userid) {
		try {   //리턴 타입을 int 로 맞추기 위해서 처리함
			//deleteById() -> 리턴 타입이 void 임
			//전달인자인 userid 가 null 인 경우 IllegalArgumentException 발생함
			memberRepository.deleteById(userid);
			return 1;
		} catch (Exception e) {
			log.info(e.getMessage());
			return 0;
		}
	}

	//페이징 처리된 목록 조회
	public ArrayList<MemberDto> selectList(Pageable pageable) {
		Page<MemberEntity> entityPage = memberRepository.findAll(pageable);
		ArrayList<MemberDto> list = new ArrayList<>();
		for(MemberEntity entity : entityPage){
			list.add(entity.toDto());
		}
		return list;
	}

	public long selectListCount() {
		return memberRepository.count();
	}

	//	@Transactional
	public int updateLoginOK(MemberDto member) {
		//수정에 필요한 항목만 추출
		String userId = member.getUserId();
		String loginOk = member.getLoginOk();
		return memberRepository.updateLoginOK(userId, loginOk);
	}

	//반복 사용 코드는 메소드로 만들어서 이용함 -----------------------------------
	private ArrayList<MemberDto> toList(List<MemberEntity> entityList){
		ArrayList<MemberDto> list = new ArrayList<>();
		for(MemberEntity entity : entityList){
			list.add(entity.toDto());
		}
		return list;
	}

	//관리자용 검색 관련
	public ArrayList<MemberDto> selectSearchUserid(String keyword, Pageable pageable) {
		return toList(memberRepository.findBySearchUserid(keyword, pageable));
	}

	public ArrayList<MemberDto> selectSearchGender(String keyword, Pageable pageable) {
		return toList(memberRepository.findBySearchGender(keyword, pageable));
	}

	public ArrayList<MemberDto> selectSearchAge(int age, Pageable pageable) {
		return toList(memberRepository.findBySearchAge(age, pageable));
	}

	public ArrayList<MemberDto> selectSearchEnrollDate(SearchDate searchDate, Pageable pageable) {
		return toList(memberRepository.findBySearchEnrollDate(searchDate, pageable));
	}

	public ArrayList<MemberDto> selectSearchLoginOK(String keyword, Pageable pageable) {
		return toList(memberRepository.findBySearchLoginOK(keyword, pageable));
	}

	public boolean selectCheckId(String userId) {
		//log.info("MemberService.selectCheckId : " + userId);
		//가입회원 아이디 중복 검사용
		return memberRepository.existsById(userId);
		//존재하면 true, 존재하지 않으면 false 리턴함
	}

	//검색 목록 카운트 관련
	public long selectSearchIDCount(String keyword) {
		return (int)memberRepository.searchIDCount(keyword);
	}

	public long selectSearchGenderCount(String keyword) {
		return (int)memberRepository.searchGenderCount(keyword);
	}

	public long selectSearchAgeCount(int age) {
		return (int)memberRepository.searchAgeCount(age);
	}

	public long selectSearchDateCount(SearchDate searchDate) {
		return (int)memberRepository.searchDateCount(searchDate);
	}

	public long selectSearchLoginOKCount(String keyword) {
		return (int)memberRepository.searchLoginOKCount(keyword);
	}

	//로그인 관련
	public MemberDto selectLogin(MemberDto member) {
		return memberRepository.findByUserId(member.getUserId()).toDto();
	}

}
