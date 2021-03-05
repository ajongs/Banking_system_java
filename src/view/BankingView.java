package view;

import account.Account;
import account.AccountDAO;
import service.BankingService;
import serviceImpl.BankingServiceImpl;

import java.util.Scanner;

public class BankingView {
    Account account; //계좌
    BankingService bankingService = new BankingServiceImpl(new AccountDAO());
    Scanner scan = new Scanner(System.in);

    public BankingView() {
        bankingService.defaultSignUp();  //db가 없어서 고객 정보를 미리 저장해논다
    }

    //로그인 화면
    public void signInView() {
        System.out.println("안녕하세요. 공주대학교 은행입니다.");
        System.out.println("뱅킹 업무를 위해 계좌번호를 입력해 주십시오.");
        System.out.print("계좌번호 : ");
        account = bankingService.signIn(); //로그인이 되면 해당 계정정보 리턴

        if(account == null){
            System.out.println("해당하는 계좌가 없습니다. 회원가입을 진행해주십시오.");
            signUpView();  //회원가입 화면으로 이동
        }
        else{
            bankingMainView();  //메인 화면으로 이동
        }
    }

    //회원가입 화면
    public void signUpView() {
        System.out.println("\n-----------------------------------------------");
        System.out.println("회원가입 화면입니다");
        Long accountNum = bankingService.signUp();
        if(accountNum==null){
            System.out.println("!!오류!! 계좌가 생성되지않았습니다. 다시시도해주십시오");
            signUpView();  //회원가입 화면으로 다시 이동
        }
        System.out.println("계좌가 성공적으로 개설되었습니다.");
        System.out.println("생성된 고객님의 계좌번호는 "+accountNum+" 입니다.\n\n");
        signInView();  //로그인 화면으로 이동
    }

    // 메인화면
    public void bankingMainView() {
        System.out.println("\n-----------------------------------------------");
        System.out.println("\n안녕하세요. \""+ account.getUserName()+"\"님,");
        System.out.println("이용하고자 하는 서비스를 선택해 주십시오.");
        System.out.println("[1]입금\t[2]출금\t[3]잔액조회\t[4]이체\t[5]로그아웃\t[0]종료");
        int manul =bankingService.startBanking(); // 서비스 선택
        if (manul == 0) {
            System.out.println("\n프로그램을 종료합니다. 이용해주셔서 감사합니다.");
            return;
        }
        else if(manul==1){
            depositView();
        }
        else if(manul==5){
            System.out.println("\n로그아웃 되었습니다.");
            signInView();
        }
        else if (manul==2 || manul==3|| manul==4){
            while(true) {
                if (bankingService.checkPw()) {
                    if(manul==2){
                        withdrawView();
                        return;
                    }
                    else if(manul==3){
                        CheckBalanceView();
                        return;
                    }
                    else if (manul == 4) {
                        transferView();
                        return;
                    }
                }
            }
        }
        else {
            System.out.println("\n올바르게 입력해주십시오.");
            bankingMainView();  //메인 화면으로 복귀
        }
    }


    //입금 화면
    public void depositView() {
        System.out.println("\n-----------------------------------------------");
        System.out.println("입금 서비스 화면입니다");
        System.out.println("원하시는 금액을 선택해주십시오.");
        System.out.print("입금 금액 : ");
        //deposit 메소드 실행 후 이체금액 리턴
        System.out.println(bankingService.deposit()+"원이 성공적으로 입금되었습니다.");
        //checkBalance 메소드 실행 후 잔액 리턴
        System.out.println("잔액 : "+bankingService.CheckBalance()+"원");
        bankingMainView();  //메인 화면으로 복귀
    }


    //출금 화면
    public void withdrawView() {
        System.out.println("\n-----------------------------------------------");
        System.out.println("출금 서비스 화면입니다");
        System.out.println("원하시는 출금 금액을 입력해주십시오.");
        System.out.print("출금 금액 : ");
        int amount = bankingService.withdraw(); //출금 메소드 후 출금금액 리턴
        if(amount < 0) {  //출금 메소드에서 잔액이 부족하면 -1 리턴
            System.out.println("\n** 경고 !! 잔액이 부족합니다. **");
        }
        else {
            System.out.println("\n\""+amount+"원이 성공적으로 출금되었습니다.\"");
            System.out.println("잔액 : "+bankingService.CheckBalance()+"원");
        }
        bankingMainView();  //메인 화면으로 복귀
    }


    //잔액조회 화면
    public void CheckBalanceView() {
        System.out.println("\n-----------------------------------------------");
        System.out.println("잔액조회 서비스 화면입니다\n");
        System.out.println("고객님의 현재잔액은 ");
        System.out.println("\n\""+ bankingService.CheckBalance()+"원\"");
        System.out.println("\n입니다.");
        bankingMainView();   //메인 화면으로 복귀
    }


    //계좌이체 화면
    public void transferView(){
        Long AccountNum; //이체할 계좌번호

        System.out.println("\n-----------------------------------------------");
        System.out.println("계좌이체 서비스 화면입니다");
        System.out.println("원하시는 이체 금액을 입력해주십시오.");
        System.out.print("이체 금액 : ");
        int money = scan.nextInt();
        System.out.println("이체할 계좌번호를 입력해주십시오.");

        while(true){ //계좌번호 확인을 위한 루프
            System.out.print("계좌번호 입력 : ");
            AccountNum = scan.nextLong();
            if(!bankingService.checkAccount(AccountNum)){
                System.out.println("계좌번호가 올바르지 않습니다.");
            }
            else //계좌가 존재한다면 루프 탈출
                break;
        }
        //계좌이체 후 이체받을 계좌의 정보 리턴받음
        Account transferToAccount = bankingService.transfer(money, AccountNum);
        //이체할 금액이 부족하면 null 반환받음
        if(transferToAccount==null) {
            System.out.println("\n** 경고 !! 잔액이 부족합니다. **");
        }
        //성공적으로 이체 시
        else {
            System.out.println("\n\n받는이 : "+transferToAccount.getUserName());
            System.out.println("\""+money+"원이 성공적으로 이체되었습니다.\"\n");
            System.out.println("보내는이 : "+account.getUserName());
            System.out.println("잔액 : "+bankingService.CheckBalance()+"원\n");
        }
        bankingMainView(); //메인 화면으로 복귀
    }


}
