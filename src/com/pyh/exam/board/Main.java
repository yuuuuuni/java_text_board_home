package com.pyh.exam.board;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);


    System.out.println("== 게시판 v 0.1 ==");
    System.out.println("== 프로그램 시작 ==");

    int articlesLastId = 0;  // article의 마지막 게시물 번호라는 의미의 변수임. 맨 처음에는 아직 게시물이 없을 때이므로 게시물 번호를 0으로 초기화

    // lastArticle이라는 마지막 게시물이라는 의미의 변수를 만들고 처음에는 null값으로 줌.
    // 왜? 이때 기준으로 아직 게시물을 만들기 전이고 아래에서 게시물을 만들때 그제서야 값이 들어갈테니까
    Article lastArticle = null;


    while (true) {

      System.out.printf("명령) ");
      String cmd = sc.nextLine();

      if (cmd.equals("exit")) {  // 프로그램 종료
        break;  // 반복문을 아예 빠져나가게 함. if문을 넘어 while문 까지 아예 벗어나게 함
      }
      else if(cmd.equals("/usr/article/detail")) {  // 게시물 상세보기 

        if(lastArticle == null) {
          System.out.println("게시물이 존재하지 않습니다.");
          continue;  // 즉시 아래 남아있는 반복문 다 스킵하고 바로 다시 처음으로 돌아감
        }

        // 1. article.id, article.title, article.body을 받기 위해 먼저 null설정
        // 2. null에서 lastArticle로 값을 나중에 바꿔주는데 왜 바꿔주는지 모르겠음....
        Article article = lastArticle;  // null -> lastArticle

        System.out.println("- 게시물 상세내용 -");
        System.out.println("번호 : "+ article.id);
        System.out.println("제목 : "+ article.title);
        System.out.println("내용 : "+ article.body);

      }
      else if (cmd.equals("/usr/article/write")) {  // 게시물 등록
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



        // Article article = new Article(id, title, body); 여기에서 article에 입력받은 값(객체)들이 다 있었는데, article은 지금 이 부분 밖에 못씀
        // 즉, 이 else if문을 빠져나가면 죽기 때문에 lastArticle이라는 변수로 article이 가지고 있던 값들을 다 옮겨주는 것임.
        // lastArticle의 경우 맨 위에 선언해놨으므로 어디서든지 쓸 수 있기 때문
        // 그래서 article이 사라지기 전에 lastArticle한테 값을 옮겨줘야 하므로 이 else if문의 write 부분 안에서 대입해주는 것임.
        // 그래야 article도 살아있는 상태에서 값을 넘겨줄 수 있기 때문
        // 그러므로 입력받은 값(정보)들은 이때부터는 article이 아닌 lastArticle에 다 저장되게 됨.
        lastArticle = article;

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