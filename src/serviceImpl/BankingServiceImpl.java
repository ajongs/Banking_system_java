package serviceImpl;

import account.Account;
import account.AccountDAO;
import service.BankingService;

import java.util.Scanner;


public class BankingServiceImpl implements BankingService {
    private AccountDAO accountDao;  //등록된 계좌 조회하는 클래스의 인스턴스
    private Account myAccount; // 로그인 되어지면 할당되는 내 계좌
    Scanner scan = new Scanner(System.in);

    public BankingServiceImpl(AccountDAO accountDao){
        this.accountDao = accountDao;
    }


    @Override // 로그인 메소드
    public Account signIn() {
        Long accountNum = scan.nextLong(); // 계좌번호 입력받기
        Account checkAccount = accountDao.getAccount(accountNum); //계좌번호로 회원정보 받아오기
        myAccount = checkAccount; // 내 정보 사용하기위해 변수할당
        if(checkAccount == null){ // 입력받은 계좌가 존재하지 않는다면
            return null;
        }
        else{
            return checkAccount; // 계좌가 존재하면 그 계좌 리턴
        }
    }

    @Override // 회원가입 메소드
    public Long signUp() {
        System.out.print("이름 : ");
        String name = scan.next();
        System.out.print("비밀번호 : ");
        String password = scan.next();
        Long accountNum = accountDao.nextAccountNum(); // 계좌번호 자동 생성
        int balance = 0; // 잔액 0
        accountDao.setAccount(new Account(name, password, accountNum, balance));  // DB(HashMap)에 등록
        return accountNum; //새로 만들어진 계좌번호 리턴
    }

    @Override  // 뱅킹 서비스 종류 리턴
    public int startBanking() {
        int service = scan.nextInt();
        return service;
    }

    @Override  // 입금 메소드
    public int deposit() {
        int amount = scan.nextInt();
        myAccount.setBalance(myAccount.getBalance()+amount); // 내 계좌의 잔고 수정
        accountDao.setAccount(myAccount); //DB에 내정보 업데이트
        return amount;
    }

    @Override  // 출금 메소드
    public int withdraw() {
        int amount = scan.nextInt(); //출금할 금액
        if(myAccount.getBalance()-amount < 0) // 잔고가 부족하다면
            return -1;
        else{
            myAccount.setBalance(myAccount.getBalance()-amount); // 문제없을 시 출금금액만큼 잔고금액 감소
            return amount; //출금 금액 리턴
        }
    }

    @Override //잔액조회 메소드
    public int CheckBalance() {
        return accountDao.getAccount(myAccount.getAccountNum()).getBalance();
    }

    @Override //계좌 이체 메소드
    public Account transfer(int money, Long accountNum) {
            if(myAccount.getBalance()-money < 0)
                return null;
            else{
                //내 계좌(account)에서 돈 송금 후 잔액(balance) 감소
                myAccount.setBalance(myAccount.getBalance()-money);
                //상대방 계좌(transferToAccount)에 잔액(balance) 증가
                Account transferToAccount = accountDao.getAccount(accountNum);
                transferToAccount.setBalance(transferToAccount.getBalance()+money);

                return transferToAccount;
            }
    }

    @Override  //비밀번호 체크 메소드
    public Boolean checkPw() {
        System.out.println("거래를 지속하기 위해 비밀번호를 입력해주십시오.");
        System.out.print("비밀번호 : ");
        String password = scan.next();
        if (password.equals(myAccount.getPassword())) {
            return true;
        } else { //비밀번호 오류시 다시 입력
            System.out.println("\n\n비밀번호가 다릅니다.");
            return false;
        }
    }
    @Override  // 이미 해당은행을 사용하고 있는 고객
    public void defaultSignUp() {
        accountDao.setAccount(new Account("박원종","dev123",201601821L, 500000));
        accountDao.setAccount(new Account("이민혁","456",201601822L, 100000));
    }

    @Override //유효한 계좌인지 체크
    public Boolean checkAccount(Long accountNum) {
        if(accountDao.getAccount(accountNum)==null)
            return false;
        else
            return true;
    }
}
