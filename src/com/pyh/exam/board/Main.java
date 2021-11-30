package com.pyh.exam.board;

import java.util.*;

public class Main {

  static void makeTestData(List<Article> articles) { // "게시물_관련_테스트_데이터_생성()"은 static 안에 있는데 이 메소드는 static 바깥이므로 앞에 static 붙여줌
    articles.add(new Article(1, "제목1", "내용1"));
    articles.add(new Article(2, "제목2", "내용2"));
    articles.add(new Article(3, "제목3", "내용3"));
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

        //최근 게시물 부터 뽑아와야 하므로 역순으로 for문 돌리기
        for(int i = articles.size()-1; i >= 0; i--) {
          Article article = articles.get(i);
          System.out.println(article.id + " / " + article.title);
        }

      }
      else if(rq.getUrlPath().equals("/usr/article/detail")) {  // 게시물 상세보기

        int id = Integer.parseInt(params.get("id"));


        if(id > articles.size()) {
          System.out.println("게시물이 존재하지 않습니다.");
          continue;  // 즉시 아래 남아있는 반복문 다 스킵하고 바로 다시 처음으로 돌아감
        }


        Article article = articles.get(id - 1);

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