package com.napico.sbb;

import lombok.Getter;
//import lombok.Setter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
//@Setter
public class HelloLombok {
    /*@Setter를 쓸 경우*/
    //private String hello;
    //private int lombok;

    /*@RequiredArgsConstructor를 쓸 경우 필요한 생성저가 자동으로 생성된다
        final은 뒤에 따라오는 자료형과 변수 등을 변경할 수 없게 만드는 키워드이다.
        클래스 속성을 정의한 코드에 final이 없다면 생성자에 포함되지 않는다.
        final을 적용하면 속성값을 변경할 수 없기 때문에 @Setter는 의미가 없어지고, Setter 메서드 또한 사용할수 없다.*/
    private final String hello;
    private final int lombok;

    /* @Setter, @Getter 가 없으면 정의해 줘야 할것. 무조건 lombok을 쓰자
    public void setHello(String hello) {
        this.hello = hello;
    }

    public void setLombok(int lombok) {
        this.lombok = lombok;
    }

    public String getHello() {
        return this.hello;
    }

    public int getLombok() {
        return this.lombok;
    }*/

    public static void main(String[] args) {
        //HelloLombok helloLombok = new HelloLombok();
        //helloLombok.setHello("Hello");
        //helloLombok.setLombok(5);
        HelloLombok helloLombok = new HelloLombok("Hello", 5);

        System.out.println(helloLombok.getHello());
        System.out.println(helloLombok.getLombok());
    }
}
