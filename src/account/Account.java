package account;

public class Account {
    private String userName; //고객 이름
    private String password; //비밀번호
    private Long accountNum; //계좌번호 (학번을 사용)
    private int balance; //잔액

    public Account(String userName, String password, Long accountNum) {
        this.userName = userName;
        this.password = password;
        this.accountNum = accountNum;
        this.balance = 0;
    }

    public Account(String userName, String password, Long accountNum, int balance) {
        this.userName = userName;
        this.password = password;
        this.accountNum = accountNum;
        this.balance = balance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(Long accountNum) {
        this.accountNum = accountNum;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
