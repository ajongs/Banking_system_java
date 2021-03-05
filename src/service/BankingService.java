package service;

import account.Account;

public interface BankingService {
    public Account signIn();  //로그인
    public Long signUp();  //회원가입
    public int startBanking(); //뱅킹 서비스 종류 선택
    public int deposit(); //입금
    public int withdraw(); //출금
    public int CheckBalance(); //잔액조회
    public Account transfer(int money, Long accountNum); //이체
    public Boolean checkPw(); //비밀번호 검사
    public void defaultSignUp(); //기존 회원
    public Boolean checkAccount(Long accountNum); //계좌번호가 유효한 계좌인지 확인인
}
