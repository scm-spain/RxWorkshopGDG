package com.scmspain.workshop.frodo;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.fernandocejas.frodo.annotation.RxLogSubscriber;
import rx.Observable;
import rx.Subscriber;

public class FrodoDemo {
  @RxLogObservable
  public static Observable<Integer> getObservable() {
    return Observable.just(1, 3, 5, 7);
  }

  @RxLogSubscriber
  static class MySubscriber extends Subscriber<Integer> {
    @Override
    public void onCompleted() {
      //Log.d("getSubscriber", "onCompleted");
    }

    @Override
    public void onError(Throwable e) {
      //Log.d("getSubscriber", e.getLocalizedMessage());
    }

    @Override
    public void onNext(Integer integer) {
      //Log.d("getSubscriber", "onNext: " + integer);
    }
  }

  public static MySubscriber getSubscriber() {
    return new MySubscriber();
  }
}
