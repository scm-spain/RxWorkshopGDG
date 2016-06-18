package com.scmspain.workshop.rxintroduction;

import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class RxBasicsUnitTest {
  @Test public void printInteger() throws Exception {
    System.out.println("printInteger()");

    Observable<Integer> integerObservableObservable = RxBasics.getIntegerObservable();
    System.out.println("printInteger() - Observable created");

    Observer<Integer> integerObserver = new Observer<Integer>() {
      @Override public void onCompleted() {
        System.out.println("printInteger() --- onCompleted");
      }

      @Override public void onError(Throwable e) {
        e.printStackTrace();
      }

      @Override public void onNext(Integer integer) {
        System.out.println("printInteger() --- onNext: " + integer);
      }
    };
    System.out.println("printInteger() - Observer created");

    System.out.println("printInteger() - Before Subscribe");
    integerObservableObservable.subscribe(integerObserver);
    System.out.println("printInteger() - After Subscribe");
  }

  @Test public void printAMap() throws Exception {
    Observable<Integer> integerObservableObservable = RxBasics.getIntegerObservable();

    Observable<Integer> observable = integerObservableObservable.map(RxBasics.multiplyByTen());

    observable.subscribe(new Action1<Integer>() {
      @Override public void call(Integer integer) {
        System.out.println(integer);
      }
    }/*, new Action1<Throwable>() {
      @Override
      public void call(Throwable throwable) {
        throwable.printStackTrace();
      }
    }*/);
  }

  @Test public void printWords() throws Exception {
    // TODO: Create the observable, and print each string it returns
    // Observable<String> stringObservableObservable = RxBasics.getStringObservable();
  }

  @Test public void printWordsLength() throws Exception {
    //TODO: Using RxBasics.getStringObservable()
    //stringObservableObservable.map(RxBasics.stringToLength());
  }

  @Test public void testIntegerObservableReturnsJust4() throws Exception {
    Integer result = RxBasics.getIntegerObservable().toBlocking().single();
    assertEquals(4, result.intValue());
  }
}