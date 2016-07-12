package com.scmspain.workshop.rxintroduction;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertTrue;

public class RxTimeTest {

  @Test
  public void printInterval() throws Exception {
    System.out.println("printInterval()");

    System.out.println("printInterval() - Observable created");

    Observer<Long> longObserver = new Observer<Long>() {
      @Override
      public void onCompleted() {
        System.out.println("printInterval() --- onCompleted");
      }

      @Override
      public void onError(Throwable e) {
        e.printStackTrace();
      }

      @Override
      public void onNext(Long aLong) {
        System.out.println("printInterval() --- onNext: " + aLong);
      }
    };
    System.out.println("printInterval() - Observer created");

    System.out.println("printInterval() - Before Subscribe");
    RxTime.interval.subscribe(longObserver);
/*
    TODO: Replace RxTime.interval.subscribe(longObserver) with this code:
    TestSubscriber<Long> testSubscriber = new TestSubscriber<>(longObserver);
    RxTime.interval.subscribe(testSubscriber);
    testSubscriber.awaitTerminalEvent();
*/
    System.out.println("printInterval() - After Subscribe");
  }

  @Test
  public void testDateEverySecond() throws Exception {
    Date oldDate = new Date();

    Observable<Date> dateObservable = RxTime.getDateEverySecond();
    Iterable<Date> dates = dateObservable.take(3).toBlocking().toIterable();

    for (Date date : dates) {
      System.out.println("newDate: " + date);
      assertTrue(
        "New date should later than old\n new: " + date + "\n oldDate:" + oldDate,
        date.after(oldDate)
      );
      oldDate = date;
    }
  }

/*
@Test
  public void printTimerOfTimers() throws Exception {
    System.out.println("testTimerOfTimers");
    TestSubscriber<String> testSubscriber = new TestSubscriber<>(new Observer<String>() {
      @Override
      public void onCompleted() {
        System.out.println("Completed");
      }
      @Override
      public void onError(Throwable e) {
        e.printStackTrace();
      }
      @Override
      public void onNext(String string) {
        System.out.println(string);
      }
    });
  Observable.interval(0, 500, TimeUnit.MILLISECONDS)
        .map(new Func1<Long, Integer>() {
          @Override
          public Integer call(Long aLong1) {
            return aLong1.intValue();
          }
        })
        .take(10)
        .concatMap(new Func1<Integer, Observable<String>>() {
          @Override
          public Observable<String> call(final Integer aInt) {
            return Observable.interval(0, 500, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, String>() {
                  @Override
                  public String call(Long aLong) {
                    return new Date().getTime() + " " + aInt + "->" + aLong;
                  }
                })
                .doOnSubscribe(new Action0() {
                  @Override
                  public void call() {
                    System.out.println("Emit " + aInt);
                  }
                })
                .take(aInt);
          }
        })
        .subscribe(testSubscriber);
    testSubscriber.awaitTerminalEvent();
    System.out.println();
  }
*/
}