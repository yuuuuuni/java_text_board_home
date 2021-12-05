package com.pyh.exam.board;

import java.util.*;

public class Main {

  static void makeTestData(List<Article> articles) { // "게시물_관련_테스트_데이터_생성()"은 static 안에 있는데 이 메소드는 static 바깥이므로 앞에 static 붙여줌
     articles.add(new Article(1, "제목1", "내용1"));
     articles.add(new Article(2, "제목2", "내용2"));
     articles.add(new Article(3, "제목3", "내용3"));
     articles.add(new Article(4, "제목4", "내용4"));
     articles.add(new Article(5, "제목5", "내용5"));

  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);


    System.out.println("== 게시판 v 0.1 ==");
    System.out.println("== 프로그램 시작 ==");

    int articlesLastId = 0;  // article의 마지막 게시물 번호라는 의미의 변수임. 맨 처음에는 아직 게시물이 없을 때이므로 게시물 번호를 0으로 초기화
    List<Article> articles = new ArrayList<>();

    makeTestData(articles);

    if(articles.size() > 0) {
      articlesLastId = articles.get(articles.size() - 1).id;
    }



    while (true) {

      System.out.printf("명령) ");
      String cmd = sc.nextLine();

      Rq rq = new Rq(cmd);
      Map<String, String> params = rq.getParams();


      if (rq.getUrlPath().equals("exit")) {  // 프로그램 종료
        break;  // 반복문을 아예 빠져나가게 함. if문을 넘어 while문 까지 아예 벗어나게 함
      }
      else if(rq.getUrlPath().equals("/usr/article/list")) {

        System.out.println("- 게시물 리스트 -");
        System.out.println("--------------------");
        System.out.println("번호 / 제목");
        System.out.println("--------------------");


        List<Article> sortedArticles = articles; // 원래 articles 안에는 오름차순된 게시물들이 존재


        boolean orderByIdDesc = true; // orderByIdDesc 라는 변수를 선언하고 true로 놓기
        // params가 orderBy 키를 포함하고, 그 orderBy 키 값이 idAsc이면 if문 실행
        // orderBy 키 값이 idAsc라고 걸어놨는데, 만약 고객이 usr/article/list 까지만 쓰면, orderBy 값이 null이 되므로 오류 남
        // 따라서, 앞에 orderBy 키를 포함한다는 전제를 깔아줘야함
        // 아래의 if문은 &&이므로 params가 orderBy 값을 포함하지 않으면 바로 끝나버림 오른쪽으로 넘어가지도 않음
        if(params.containsKey("orderBy") && params.get("orderBy").equals("idAsc")) {
          orderByIdDesc = false; // 만약, 키 값이 idAsc가 된다면 반대가 되므로 orderByIdDesc를 false로 놔줌
        }

        if(orderByIdDesc) { // orderByIdDesc가 참이면
          sortedArticles = Util.reverseList(sortedArticles); // articles의 복사본을 뒤집어라(즉, 오름차순되어있는걸 내림차순으로)
        }

          for(Article article : sortedArticles) { // 뒤집은 다음 순차적으로 보여줘라
            System.out.println(article.id + " / " + article.title);
          }

      }
        else if(rq.getUrlPath().equals("/usr/article/detail")) {  // 게시물 상세보기
         if(params.containsKey("id") == false) { // containsKey() : params로 들어온 명령어 인자에 id가 있냐?라고 확인하는 함수
            System.out.println("id를 입력해주세요.");
            continue; // while문의 첫번째로 다시 올라감
          }

         int id = 0; // id를 try안에서 만들면 밑에서 id를 쓸 수 없으므로 바깥으로 빼줌

        // try-catch문 사용하면 try안에 감싸진 곳에서 프로그램 오류가 나도 뻗지 않음.
        // try 안에 감싸진 실행문이 오류가 나지 않으면 catch 실행 되지 않고 그냥 넘어감
        // try 안에 감싸진 실행문이 오류 날 경우, catch 안에 감싸진 실행문이 실행되고 넘어감
         try {
           // ex) 고객이 id=5라고 침, int id = 5가 됨
           id = Integer.parseInt(params.get("id")); // params의 id 키를 꺼내면 값이 나오는데 그것이 String이므로 정수화 시킴
         }
        catch (NumberFormatException e) {
          System.out.println("id를 정수로 입력해주세요.");
          continue;
        }

        if(id > articles.size()) {
          System.out.println("게시물이 존재하지 않습니다.");
          continue; // while문의 첫번째로 다시 올라감
        }
        
        // params의 번호와 articles의 번호를 비교하는거 없이 한번에 id값을 넣어서 처리
        // 5-1은 4, get(4)는 articles의 번호 5에 해당되므로 5번 게시물이 나옴
        Article article = articles.get(id - 1); // articles가 0번째 인덱스에서 시작하므로 id값에서 하나 빼야함

        System.out.println("- 게시물 상세내용 -");
        System.out.println("번호 : "+ article.id);
        System.out.println("제목 : "+ article.title);
        System.out.println("내용 : "+ article.body);

      }

      else if (rq.getUrlPath().equals("/usr/article/write")) {  // 게시물 등록
        System.out.println("- 게시물 등록 -");
        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();

        int id = articlesLastId + 1;
        articlesLastId++;

        // Article은 새로 지은 변수 타입이라고 생각하면 됨. int i 이런거에서 int와 같음. 변수는 article이고!
        // Article이라는 객체를 생성했으므로 이제 저거는 변수 타입을 Article 이라고 쓸 수 있는것
        // article 요 변수는 else if 이 괄호 안에서 밖에 못씀 괄호 밖으로 나가면 못씀 죽음...
        Article article = new Article(id, title, body);   // Article 객체 생성  // new Article에 들어온 값들은 스캐너로 입력 받은 값들 + 알아서 증가한 번호 변수
        // 게시물 객체가 생성되는 코드임. 즉, 게시물 생성


        articles.add(article);

        System.out.println("생성된 게시물 객체 : " + article);

        System.out.println(article.id + "번 게시물이 등록되었습니다.");


      }

      else {
        System.out.println("입력된 명령어 : " + cmd);
      }

    }


    System.out.println("== 프로그램 종료 ==");

    sc.close();
  }
}


class Article {  // 클래스 생성
  int id;
  String title;
  String body;

// 즉, 스캐너로 입력받은 값 title과 body와 알아서 증가하는 id를 class Article의 3개 변수에 넣어줘라
  Article(int id, String title, String body) {  // 생성자 생성. new 연산자를 이용해 객체 생성해준 것을 생성자 선언으로 인하여 4줄짜리를 1줄에 적게 해줌
  this.id = id;
  this.title = title;
  this.body = body;
  }


  @Override  // toString() 메서드로 오버라이드 하여 아래의 양식으로 return 해줌
  public String toString() {
    return String.format("{id : %d, title : \"%s\", body : \"%s\"}", id, title, body);
  }
}


class Rq {
  private String url;
  private String urlPath;
  private Map<String, String> params;

  // 필드추가가능

  // 수정가능
  Rq(String url) {
    this.url = url;
    urlPath = Util.getUrlPathFromUrl(url);
    params = Util.getParamsFromUrl(url);
  }

  // 수정가능, if문 금지
  public Map<String, String> getParams() {

    return params;
  }

  // 수정가능, if문 금지
  public String getUrlPath() {

    return urlPath;
  }
}

// 수정불가능
class Util {
  // 이 함수는 원본리스트를 훼손하지 않고, 새 리스트를 만듭니다. 즉 정렬이 반대인 복사본리스트를 만들어서 반환합니다.
  public static<T> List<T> reverseList(List<T> list) { // 입력받은 값을 복사해서 그 복사본을 뒤집음
    List<T> reverse = new ArrayList<>(list.size());

    for ( int i = list.size() - 1; i >= 0; i-- ) {
      reverse.add(list.get(i));
    }
    return reverse;
  }

  static Map<String, String> getParamsFromUrl(String url) {

    Map<String, String> params = new HashMap<>();
    String[] urlBits = url.split("\\?", 2);

    if (urlBits.length == 1) {
      return params;
    }

    String queryStr = urlBits[1];
    for (String bit : queryStr.split("&")) {
      String[] bits = bit.split("=", 2);
      if (bits.length == 1) {
        continue;
      }
      params.put(bits[0], bits[1]);
    }

    return params;
  }

  static String getUrlPathFromUrl(String url) {

    return url.split("\\?", 2)[0];
  }
}