import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;

public class ArticleManager {
    private List<Article> articles = new ArrayList<>();
    private int articleCount = 0;
    private UserManager userManager = new UserManager();
    private User currentUser = null; // 현재 로그인한 사용자 정보를 저장하는 변수
    
    public ArticleManager() {
        // ArticleManager 객체가 생성될 때 초기화 작업을 수행할 수 있도록 생성자를 사용합니다.
//        makeTestData();
    }


//    private void makeTestData() {
//        // 게시글 테스트 데이터 3개를 생성하여 articles 리스트에 추가합니다.
//        Article article1 = new Article(1, "제목1", "내용1", new Date(), 0);
//        Article article2 = new Article(2, "제목2", "내용2", new Date(), 0);
//        Article article3 = new Article(3, "제목3", "내용3", new Date(), 0);
//
//        articles.add(article1);
//        articles.add(article2);
//        articles.add(article3);
//
//        articleCount = 3; // 기본 게시글 개수를 업데이트합니다.
//        
//        System.out.println("테스트를 위한 데이터 3개 생성 완료");
//    }
    
 // 오버로딩된 makeTestData 메서드 (매개변수를 받는 버전)
    private void makeTestData(int count) {
        for (int i = 1; i <= count; i++) {
            Article article = new Article(i, "제목" + i, "내용" + i, new Date(), 0);
            articles.add(article);
        }

        articleCount += count;

        System.out.println("테스트를 위한 데이터 " + count + "개 생성 완료");
    }



public void start() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("==프로그램 시작==");

    while (true) {
        System.out.print("명령어) ");
        String command = scanner.nextLine();

        if (command.equals("article list")) {
            listArticles();
        } else if (command.equals("article write")) {
            writeArticle(scanner);
        } else if (command.startsWith("article detail ")) {
            int articleNumber = extractArticleNumber(command);
            viewArticleDetail(articleNumber);
        } else if (command.startsWith("article delete ")) {
            int articleNumber = extractArticleNumber(command);
            deleteArticle(articleNumber);
        } else if (command.startsWith("article modify ")) {
            int articleNumber = extractArticleNumber(command);
            modifyArticle(scanner, articleNumber);
        } else if (command.equals("register")) {
            // "register" 명령어 처리
            registerUser(scanner); // registerUser 메서드 호출
        } else if (command.equals("login")) {
            // "login" 명령어 처리
            currentUser = loginUser(scanner);
        } else if (command.startsWith("makeTestData")) {
            // "generateTestData" 명령어 처리
            processGenerateTestData(command);
        } else if (command.equals("exit")) {
            break;
        } else {
            System.out.println("존재하지 않는 명령어 입니다.");
        }

        // 로그인한 사용자 정보를 이용하여 다른 기능을 수행할 수 있도록 처리
        if (currentUser != null) {
            // 현재 로그인한 사용자 정보를 사용하여 다른 기능 수행
            // 예: 게시물 작성, 삭제, 수정 등
        }
    }

    System.out.println("==프로그램 종료==");
}
        
        private void processGenerateTestData(String command) {
            // 명령어에서 숫자 추출 및 처리
            String[] parts = command.split(" ");
            if (parts.length == 2) {
                try {
                    int count = Integer.parseInt(parts[1]);
                    makeTestData(count);
                } catch (NumberFormatException e) {
                    System.out.println("올바른 숫자를 입력하세요.");
                }
            } else {
                System.out.println("올바른 명령어 형식을 입력하세요.");
            }
        }
        
        
    private void listArticles() {
        if (articles.isEmpty()) {
            System.out.println("게시글이 없습니다");
        } else {
            for (int i = 0; i < articles.size(); i++) {
                Article article = articles.get(i);
                System.out.println((i + 1) + "번글 - 제목: " + article.getTitle() + ", 조회수: " + article.getViews());
            }
        }
    }

    private void writeArticle(Scanner scanner) {
        System.out.print("제목: ");
        String title = scanner.nextLine();
        System.out.print("내용: ");
        String content = scanner.nextLine();

        Article article = new Article(articleCount + 1, title, content, new Date(), 0);
        articles.add(article);

        articleCount++;
        System.out.println(articleCount + "번글이 생성되었습니다");
    }

    private int extractArticleNumber(String command) {
        String[] parts = command.split(" ");
        if (parts.length == 3) {
            try {
                return Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                // 숫자로 변환할 수 없는 경우
            }
        }
        return -1; // 잘못된 형식의 명령어
    }

    private void viewArticleDetail(int articleNumber) {
        if (articleNumber < 1 || articleNumber > articles.size()) {
            System.out.println(articleNumber + "번 게시글은 없습니다.");
        } else {
            Article article = articles.get(articleNumber - 1);
            article.incrementViews(); // 조회수 증가
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = sdf.format(article.getCreateDate());

            System.out.println("번호: " + articleNumber);
            System.out.println("작성날짜: " + dateStr);
            System.out.println("수정날짜: " + sdf.format(article.getModifyDate()));
            System.out.println("제목: " + article.getTitle());
            System.out.println("내용: " + article.getContent());
            System.out.println("조회수: " + article.getViews());
        }
    }

    private void deleteArticle(int articleNumber) {
        if (articleNumber < 1 || articleNumber > articles.size()) {
            System.out.println(articleNumber + "번 게시글은 없습니다.");
        } else {
            articles.remove(articleNumber - 1);
            articleCount--;
            System.out.println(articleNumber + "번 게시글을 삭제했습니다.");
        }
    }

    private void modifyArticle(Scanner scanner, int articleNumber) {
        if (articleNumber < 1 || articleNumber > articles.size()) {
            System.out.println(articleNumber + "번 게시글은 없습니다.");
        } else {
            Article article = articles.get(articleNumber - 1);
            System.out.print("새로운 제목: ");
            String newTitle = scanner.nextLine();
            System.out.print("새로운 내용: ");
            String newContent = scanner.nextLine();
            article.modifyArticle(newTitle, newContent);
            System.out.println(articleNumber + "번 게시글을 수정했습니다.");
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private boolean isValidLength(String str, int minLength) {
        return str.length() >= minLength;
    }
    
    private boolean isPasswordValid(String username, String password) {
        // 비밀번호의 최소 길이 검사
        int passwordMinLength = 5;
        if (password.length() < passwordMinLength) {
            System.out.println("비밀번호는 최소 " + passwordMinLength + "글자 이상이어야 합니다.");
            return false;
        }

        // 아이디와 비밀번호가 유사한지 검사
        if (isSimilarToUsername(username, password)) {
            System.out.println("비밀번호가 아이디와 유사합니다.");
            return false;
        }

        // 연속된 문자를 사용하는지 검사
        if (hasConsecutiveCharacters(password)) {
            System.out.println("연속된 문자를 사용할 수 없습니다.");
            return false;
        }

        // 영어 대문자, 소문자, 숫자, 특수문자 중 하나 이상 포함되어야 함
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        String specialCharacters = "!@#$%^&*()";

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowercase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (specialCharacters.contains(String.valueOf(ch))) {
                hasSpecialChar = true;
            }
        }

        if (!hasUppercase || !hasLowercase || !hasDigit || !hasSpecialChar) {
            System.out.println("비밀번호는 다음 조건을 모두 충족해야 합니다:");
            System.out.println("- 영어 대문자, 소문자, 숫자, 특수문자 중 하나 이상 포함");
            return false;
        }

        return true;
    }

    private boolean isSimilarToUsername(String username, String password) {
        // 아이디와 비밀번호가 유사한지 검사
        int maxSubstringLength = 3; // 허용되는 최대 연속 글자 수
        for (int i = 0; i <= username.length() - maxSubstringLength; i++) {
            String substring = username.substring(i, i + maxSubstringLength);
            if (password.contains(substring)) {
                return true; // 아이디와 유사한 부분이 비밀번호에 포함된 경우
            }
        }
        return false; // 유사하지 않은 경우
    }

    private boolean hasConsecutiveCharacters(String password) {
        // 연속된 문자를 사용하는지 검사
        String keyboardRows = "1234567890qwertyuiopasdfghjklzxcvbnm";
        for (int i = 0; i < password.length() - 1; i++) {
            char currentChar = password.charAt(i);
            char nextChar = password.charAt(i + 1);

            if (isAdjacentKeyboardCharacters(currentChar, nextChar)) {
                return true; // 연속된 문자 사용
            }
        }
        return false; // 연속된 문자를 사용하지 않음
    }

    private boolean isAdjacentKeyboardCharacters(char char1, char char2) {
        // 키보드 배열에서 인접한 키 여부 검사
        String keyboardRows = "1234567890qwertyuiopasdfghjklzxcvbnm";
        int index1 = keyboardRows.indexOf(char1);
        int index2 = keyboardRows.indexOf(char2);

        return Math.abs(index1 - index2) == 1;
    }



    private void registerUser(Scanner scanner) {
        System.out.print("사용자 이름 (최소 5글자): ");
        String username = scanner.nextLine().trim(); // 입력값 양 끝 공백 제거

        // 사용자 이름의 최소 길이 검사
        int usernameMinLength = 5;
        if (username.length() < usernameMinLength) {
            System.out.println("사용자 이름은 최소 " + usernameMinLength + "글자 이상이어야 합니다.");
            return; // 회원가입 중단
        }

        System.out.print("비밀번호 (최소 5글자, 영어 대소문자, 숫자, 특수문자( !@#$%^&*()) 포함): ");
        String password = scanner.nextLine().trim(); // 입력값 양 끝 공백 제거

        if (!isPasswordValid(password, password)) {
            return; // 비밀번호 조건을 만족하지 않는 경우 회원가입 중단
        }

        if (userManager.registerUser(username, password)) {
            System.out.println("회원 가입이 완료되었습니다.");
        } else {
            System.out.println("이미 존재하는 사용자 이름입니다.");
        }
    }



    private User loginUser(Scanner scanner) {
        System.out.print("사용자 이름: ");
        String username = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        User user = ((UserManager) userManager).login(username, password);

        if (user != null) {
            System.out.println("로그인 성공: " + user.getUsername() + "님");
        } else {
            System.out.println("로그인 실패: 잘못된 사용자 이름 또는 비밀번호입니다.");
        }
        return user;
    }
}
