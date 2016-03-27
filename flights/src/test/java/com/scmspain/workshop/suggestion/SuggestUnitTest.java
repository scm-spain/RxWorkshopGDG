package com.scmspain.workshop.suggestion;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Ignore;
import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;
import rx.observers.TestSubscriber;

public class SuggestUnitTest {
  Observable<CharSequence> textInputObservable = Observable.create(new Observable.OnSubscribe<CharSequence>() {
    @Override
    public void call(Subscriber<? super CharSequence> subscriber) {
      subscriber.onNext("a");
      try {
        Thread.sleep(160);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      subscriber.onNext("ab");
      subscriber.onNext("abc");
      subscriber.onNext("abcd");
      try {
        Thread.sleep(160);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      subscriber.onNext("abcde");
      try {
        Thread.sleep(160);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      subscriber.onNext("abcdef");

      subscriber.onCompleted();
    }
  });

  TestSubscriber<CharSequence> charSequenceSubscriber = new TestSubscriber<>(new Observer<CharSequence>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(CharSequence charSequence) {
          System.out.println(charSequence);
        }
      });

  @Test
  public void printAllInput() throws Exception {
    System.out.println("printInput");
    textInputObservable
        .subscribe(charSequenceSubscriber);

    List<CharSequence> expected = new ArrayList<>();
    expected.add("a");
    expected.add("ab");
    expected.add("abc");
    expected.add("abcd");
    expected.add("abcde");
    expected.add("abcdef");
    charSequenceSubscriber.awaitTerminalEvent();
    charSequenceSubscriber.assertReceivedOnNext(expected);
  }

  @Test
  public void printAtLeastThreeChars() throws Exception {
    System.out.println("printAtLeastThreeChars");
    textInputObservable
        //TODO: Try filtering
        .subscribe(charSequenceSubscriber);

    List<CharSequence> expected = new ArrayList<>();
    expected.add("abc");
    expected.add("abcd");
    expected.add("abcde");
    expected.add("abcdef");
    charSequenceSubscriber.awaitTerminalEvent();
    charSequenceSubscriber.assertReceivedOnNext(expected);
  }

  @Test
  public void printOnlyWhenPauseFor150Milliseconds() throws Exception {
    System.out.println("printAtLeastWhenPause");
    textInputObservable
        //TODO: Do you want a tip? try debounce
        .subscribe(charSequenceSubscriber);

    List<CharSequence> expected = new ArrayList<>();
    expected.add("a");
    expected.add("abcd");
    expected.add("abcde");
    expected.add("abcdef");
    charSequenceSubscriber.awaitTerminalEvent();
    charSequenceSubscriber.assertReceivedOnNext(expected);
  }

  @Test
  public void printFlatMapRequests() throws Exception {
    textInputObservable
        .flatMap(new Func1<CharSequence, Observable<CharSequence>>() {
          @Override
          public Observable<CharSequence> call(final CharSequence charSequence) {
            return request(charSequence);
          }
        })
        .subscribe(charSequenceSubscriber);

    List<CharSequence> expected = new ArrayList<>();
    expected.add("a->fast");
    expected.add("abcde->fast");
    expected.add("abcdef->slooooow");
    charSequenceSubscriber.awaitTerminalEvent();
    charSequenceSubscriber.assertReceivedOnNext(expected);
    //TODO: IT`S OK? TRY WITH .switchMap()
  }

  Observable<CharSequence> fastRequest = Observable.<CharSequence>just("fast")
      .delay(10, TimeUnit.MILLISECONDS);
  Observable<CharSequence> slowRequest = Observable.<CharSequence>just("slooooow")
      .delay(300, TimeUnit.MILLISECONDS);

  @NonNull
  private Observable<CharSequence> request(final CharSequence charSequence) {
    return (charSequence.length()%2==0)?
        slowRequest
            .map(new Func1<CharSequence, CharSequence>() {
              @Override
              public CharSequence call(CharSequence speed) {
                return charSequence + "->" + speed;
              }
            })
        : fastRequest
            .map(new Func1<CharSequence, CharSequence>() {
              @Override
              public CharSequence call(CharSequence speed) {
                return charSequence + "->" + speed;
              }
            });
  }


  @Test
  public void testWithExternalRequests() throws Exception {
    final SuggestionBusiness suggestionBusiness = new SuggestionBusiness();
    TestSubscriber<List<String>> testSubscriber = new TestSubscriber<>(new Observer<List<String>>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(List<String> strings) {
        for (String string : strings) {
          System.out.println(string);
        }
      }
    });

    Observable.<CharSequence>just("Barc")
        .switchMap(new Func1<CharSequence, Observable<List<String>>>() {
          @Override
          public Observable<List<String>> call(CharSequence charSequence) {
            return suggestionBusiness.requestSuggestions(charSequence);
          }
        })
        .subscribe(testSubscriber);
    testSubscriber.awaitTerminalEvent();
  }

  @Test @Ignore
  public void exampleInApp() throws Exception {
/*
    Observable.just(Collections.singletonList("Barcelona"))
        .subscribe(new Action1<List<String>>() {
          @Override
          public void call(List<String> strings) {
            adapter.setData(strings);
          }
        }, new Action1<Throwable>() {
          @Override
          public void call(Throwable throwable) {
            fragment.setErrorView(throwable.getLocalizedMessage());
          }
        }, new Action0() {
          @Override
          public void call() {
            fragment.removeLoading();
          }
        });
*/
  }
}
