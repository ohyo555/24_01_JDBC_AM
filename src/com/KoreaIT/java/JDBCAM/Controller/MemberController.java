package com.KoreaIT.java.JDBCAM.Controller;

import java.sql.Connection;
import java.util.Scanner;

import com.KoreaIT.java.JDBCAM.dto.Member;
import com.KoreaIT.java.JDBCAM.service.MemberService;

public class MemberController {
	private Connection conn;
	private Scanner sc;

	private MemberService memberService;

	public MemberController(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc = sc;
		this.memberService = new MemberService(conn);
	}

	public void login() {
		String loginId = null;
		String loginPw = null;

		System.out.println("==로그인==");
		while (true) {
			System.out.print("로그인 아이디 : ");
			loginId = sc.nextLine().trim();

			if (loginId.length() == 0 || loginId.contains(" ")) {
				System.out.println("아이디 똑바로 입력해");
				continue;
			}

			boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

			if (isLoginIdDup == false) {
				System.out.println(loginId + "는(은) 없는놈이야");
				continue;
			}

			break;
		}

		Member member = memberService.getMemberByLoginId(loginId);

		int tryMaxCount = 3;
		int tryCount = 0;

		while (true) {
			if (tryCount >= tryMaxCount) {
				System.out.println("다시 확인하고 시도해라");
				break;
			}
			System.out.print("비밀번호 : ");
			loginPw = sc.nextLine().trim();

			if (loginPw.length() == 0 || loginPw.contains(" ")) {
				tryCount++;
				System.out.println("비밀번호 똑바로 입력해");
				continue;
			}

			if (member.getLoginPw().equals(loginPw) == false) {
				tryCount++;
				System.out.println("일치하지 않아");
				continue;
			}

			System.out.println(member.getName() + "님 환영");
			break;

		}

	}

	public void doJoin() {
		String loginId = null;
		String loginPw = null;
		String loginPwConfirm = null;
		String name = null;

		System.out.println("==회원가입==");
		while (true) {
			System.out.print("로그인 아이디 : ");
			loginId = sc.nextLine().trim();

			if (loginId.length() == 0 || loginId.contains(" ")) {
				System.out.println("아이디 똑바로 입력해");
				continue;
			}

			boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

			if (isLoginIdDup) {
				System.out.println(loginId + "는(은) 이미 사용중");
				continue;
			}

			break;
		}
		while (true) {
			System.out.print("비밀번호 : ");
			loginPw = sc.nextLine().trim();

			if (loginPw.length() == 0 || loginPw.contains(" ")) {
				System.out.println("비밀번호 똑바로 입력해");
				continue;
			}

			boolean loginPwCheck = true;

			while (true) {
				System.out.print("비밀번호 확인: ");
				loginPwConfirm = sc.nextLine().trim();

				if (loginPwConfirm.length() == 0 || loginPwConfirm.contains(" ")) {
					System.out.println("확인 똑바로 입력해");
					continue;
				}

				if (loginPw.equals(loginPwConfirm) == false) {
					System.out.println("일치하지 않아");
					loginPwCheck = false;
				}
				break;
			}

			if (loginPwCheck) {
				break;
			}
		}
		while (true) {
			System.out.print("이름  : ");
			name = sc.nextLine().trim();

			if (name.length() == 0 || name.contains(" ")) {
				System.out.println("이름 똑바로 입력해");
				continue;
			}
			break;
		}

		int id = memberService.doJoin(loginId, loginPw, name);

		System.out.println(name + "님, 가입 되었습니다");

	}

}