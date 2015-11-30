package com.scmspain.workshop.rxintroduction;

import java.util.Date;
import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class RxBasicsUnitTest {
  @Test
  public void print4() throws Exception {
    System.out.println("print4()");

    Observable<Integer> just4Observable = RxBasics.getJust4Observable();
    System.out.println("print4() - Observable created");

    Observer<Integer> integerObserver = new Observer<Integer>() {
      @Override
      public void onCompleted() {
        System.out.println("print4() --- onCompleted");
      }

      @Override
      public void onError(Throwable e) {
        e.printStackTrace();
      }

      @Override
      public void onNext(Integer integer) {
        System.out.println("print4() --- onNext: "+integer);
      }
    };
    System.out.println("print4() - Observer created");

    System.out.println("print4() - Before Subscribe");
    just4Observable.subscribe(integerObserver);
    System.out.println("print4() - After Subscribe");
  }

  @Test
  public void printAMap() throws Exception {
    Observable<Integer> mockObservable = Observable.just(4);

    Observable<Integer> observable = mockObservable.map(RxBasics.multiplyByTen());
    // RxBasics.mapToVerboseString

    observable.subscribe(new Action1<Integer>() {
      @Override
      public void call(Integer integer) {
        System.out.println(integer);
      }
    }/*, new Action1<Throwable>() {
      @Override
      public void call(Throwable throwable) {
        throwable.printStackTrace();
      }
    }*/);
  }

  @Test
  public void testJust4() throws Exception {
    Integer result = RxBasics.getJust4Observable().toBlocking().single();
    assertEquals(4,result.intValue());
  }

  @Test
  public void testNowObservable() throws Exception {
    Date result = RxBasics.getNowObservable().toBlocking().single();
    assertEquals(new Date(), result);
  }
}