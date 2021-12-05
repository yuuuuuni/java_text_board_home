package com.pyh.exam.board;

import java.util.*;

public class Main {
  /* articles와 articlesLastId는 Main 안에서 만들어져서 List, Detail, Write이 얘네를 쓸때마다 넘겨받아야 했는데(괄호 안에 쓰는 것처럼)
  articles와 articlesLastId를 Main, List, Detail, Write 모두가 다 속한 Main위의 class에다가 static 변수로 만들어놓음으로써
  articles와 articlesLastId가 모두 싹 다 접근 가능할 수 있도록함 -> 넘겨받았던거 싹 다 지워도 됨. 바로바로 접근 가능하니깐 */

  static List<Article> articles = new ArrayList<>(); // 얘네 둘은 static 안에 있던 변수이므로 여기에도 static 붙여줌
  static int articlesLastId = 0;



  public static void main(String[] args) {
    Scanner sc = Container.sc; // Container에서 따로 Scanner를 만들었기 때문에 여기서 Scanner를 만들 필요 없고 호출만 해주면 됨

    System.out.println("== 게시판 v 0.1 ==");
    System.out.println("== 프로그램 시작 ==");


    makeTestData();

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
        actionUsrArticleList(rq);
      }
      else if(rq.getUrlPath().equals("/usr/article/detail")) {  // 게시물 상세보기
        actionUsrArticleDetail(rq);
      }
      else if (rq.getUrlPath().equals("/usr/article/write")) {  // 게시물 등록
        actionUsrArticleWrite(rq);
      }
      else {
        System.out.println("입력된 명령어 : " + cmd);
      }

    }

    System.out.println("== 프로그램 종료 ==");

    sc.close();
  }

  static void makeTestData() { // "게시물_관련_테스트_데이터_생성()"은 static 안에 있는데 이 메소드는 static 바깥이므로 앞에 static 붙여줌
    // for문으로 테스트 게시물 100개 만들기
    for(int i = 0; i < 100; i++) {
      int id = i + 1;
      articles.add(new Article(id, "제목" + id, "내용" + id));
    }

  }

  private static void actionUsrArticleWrite(Rq rq) {
    System.out.println("- 게시물 등록 -");
    System.out.printf("제목 : ");
    String title = Container.sc.nextLine();
    System.out.printf("내용 : ");
    String body = Container.sc.nextLine();

    int id = articlesLastId + 1;
    articlesLastId = id;

    // Article은 새로 지은 변수 타입이라고 생각하면 됨. int i 이런거에서 int와 같음. 변수는 article이고!
    // Article이라는 객체를 생성했으므로 이제 저거는 변수 타입을 Article 이라고 쓸 수 있는것
    // article 요 변수는 else if 이 괄호 안에서 밖에 못씀 괄호 밖으로 나가면 못씀 죽음...
    Article article = new Article(id, title, body);   // Article 객체 생성  // new Article에 들어온 값들은 스캐너로 입력 받은 값들 + 알아서 증가한 번호 변수
    // 게시물 객체가 생성되는 코드임. 즉, 게시물 생성

    articles.add(article);
    System.out.println("생성된 게시물 객체 : " + article);

    System.out.println(article.id + "번 게시물이 등록되었습니다.");
  }

  private static void actionUsrArticleDetail(Rq rq) {
    Map<String, String> params = rq.getParams();

    if(params.containsKey("id") == false) { // containsKey() : params 로 들어온 명령어 인자에 id가 있냐?라고 확인하는 함수
      System.out.println("id를 입력해주세요.");
      return; // 원래는 continue 였지만(반복문의 처음으로 올라가는 것) 이제는 반복문 안에 있는게 아니므로 return해줘야함
    }

    int id = 0; // id를 try 안에서 만들면 밑에서 id를 쓸 수 없으므로 바깥으로 빼줌

    // try-catch 문 사용하면 try 안에 감싸진 곳에서 프로그램 오류가 나도 뻗지 않음.
    // try 안에 감싸진 실행문이 오류가 나지 않으면 catch 실행 되지 않고 그냥 넘어감
    // try 안에 감싸진 실행문이 오류 날 경우, catch 안에 감싸진 실행문이 실행되고 넘어감
    try {
      // ex) 고객이 id=5라고 침, int id = 5가 됨
      id = Integer.parseInt(params.get("id")); // params 의 id 키를 꺼내면 값이 나오는데 그것이 String이므로 정수화 시킴
    }
    catch (NumberFormatException e) {
      System.out.println("id를 정수로 입력해주세요.");
      return;
    }

    if(id > articles.size()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return; // while 문의 첫번째로 다시 올라감
    }

    // params의 번호와 articles의 번호를 비교하는거 없이 한번에 id값을 넣어서 처리
    // 5-1은 4, get(4)는 articles의 번호 5에 해당되므로 5번 게시물이 나옴
    Article article = articles.get(id - 1); // articles가 0번째 인덱스에서 시작하므로 id값에서 하나 빼야함

    System.out.println("- 게시물 상세내용 -");
    System.out.println("번호 : "+ article.id);
    System.out.println("제목 : "+ article.title);
    System.out.println("내용 : "+ article.body);
  }

  private static void actionUsrArticleList(Rq rq) {
    System.out.println("- 게시물 리스트 -");
    System.out.println("--------------------");
    System.out.println("번호 / 제목");
    System.out.println("--------------------");

    Map<String, String> params = rq.getParams();

    // 검색시작
    List<Article> filteredArticles = articles;


    if(params.containsKey("searchKeyword")) {
      String searchKeyword = params.get("searchKeyword"); // 고객이 "searchKeyword=??"에서 ??으로 친 값을 searchKeyword에 넣어라

      filteredArticles = new ArrayList<>();

      for(Article article : articles) {
        boolean matched = article.title.contains(searchKeyword) || article.body.contains(searchKeyword);
        if(matched) { // matched가 맞으면
          filteredArticles.add(article);
        }
      }
    }
    // 검색끝


    // 정렬 순서 구현
    List<Article> sortedArticles = filteredArticles; // 원래 articles 안에는 오름차순된 게시물들이 존재


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
}

