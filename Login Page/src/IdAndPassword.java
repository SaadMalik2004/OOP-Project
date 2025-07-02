import java.util.HashMap;

public class IdAndPassword {

    HashMap<String,String> loginInfo=new HashMap<String, String>();
    IdAndPassword(){
        loginInfo.put("Bro","Pizza");
        loginInfo.put("Broman","Pizzalato");
        loginInfo.put("Broguy","burgir");
    }
    protected HashMap getloginInfo(){
        return loginInfo;
    }
}