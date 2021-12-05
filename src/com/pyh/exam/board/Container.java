package com.pyh.exam.board;

import java.util.Scanner;

// Container 는 프로그램 전체에서 다 쓰이는(모든 곳에서 다 공유되는) 것들을 모아놓은 곳
public class Container {
  static Scanner sc = new Scanner(System.in); // static을 붙여주어 sc가 어느 class든 간에 다 접근할 수 있도록 해줌
  // 스캐너는 프로그램 전체에서 다 쓰이므로 Container로 옮겨주고, static을 붙임으로써 본사직원이 됨 => 본사직원은 무조건 모든 클래스에 접근 가능하므로 Container에서 호출해서 사용하면 됨
  // 즉, 모두가 사용 가능한 공공재가 되는 것임
}
