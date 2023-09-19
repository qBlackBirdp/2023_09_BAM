import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserManager {
    private List<User> users = new ArrayList<>();

    public boolean registerUser(String username, String password) {
        // 중복 회원 확인 및 회원 등록
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false; // 중복된 사용자 이름
            }
        }

        User newUser = new User(username, password);
        users.add(newUser);
        return true; // 회원 등록 성공
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user; // 로그인 성공
            }
        }
        return null; // 로그인 실패
    }
    
}
