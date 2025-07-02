public class Main {
    public static void main(String[] args) {
        IdAndPassword id_pass=new IdAndPassword();

        LoginPage loginPage=new LoginPage(id_pass.getloginInfo());
    }
}