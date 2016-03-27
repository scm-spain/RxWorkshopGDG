package com.scmspain.workshop.rxintroduction;

import android.support.annotation.NonNull;
import java.util.Date;
import rx.Observable;
import rx.functions.Func1;

public class RxBasics {
  public static Observable<Integer> getIntegerObservable() {
    return Observable.just(4);
    //return Observable.just(2,3,4,5);
    //return Observable.range(1,6);
    //return Observable.from(new Integer[]{2,3,4});
  }

  public static Func1<Integer, Integer> multiplyByTen() {
    return null;
    //TODO: Return a Func1 to map x -> x * 10
  }

  public static @NonNull Observable<Date> getNowObservable() {
    return null;
    //TODO: Return an observable with the Date
  }
}
