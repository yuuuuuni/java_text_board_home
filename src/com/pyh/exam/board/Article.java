package com.pyh.exam.board;

public class Article {  // Article.java 안에 있는 클래스인데 파일명이랑 클래스명이랑 똑같으므로 얘는 앞에 public을 붙여줘야함
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