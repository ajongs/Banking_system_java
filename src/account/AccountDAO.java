package account;

import java.util.HashMap;
import java.util.Map;

public class AccountDAO {
    private Map<Long, Account> accountDB = new HashMap<Long, Account>();

    //전달받은 계좌번호에 해당하는 계정 return
    public Account getAccount(Long accountNum){
        return accountDB.get(accountNum);
    }
    // 계좌추가 or 업데이트
    public void setAccount(Account account){
        accountDB.put(account.getAccountNum(), account);
    }
    // 사용되어지지 않은 새로운 계좌번호 생성하기
    // 마지막으로 만들어진 계좌번호에 +1을 더해서 얻는 방식
    public Long nextAccountNum(){
        Long nextKey = 0L;
        for(Long key : accountDB.keySet()){
            if(nextKey < key)
                nextKey = key;
        }
        return nextKey+1L;
    }
}
