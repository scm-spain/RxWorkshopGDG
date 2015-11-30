package com.scmspain.workshop.frodo;

import android.util.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;
import rx.Observable;

@Config(sdk = 18, constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class FrodoUnitTest {

  @Before
  public void setUp() throws Exception {
    ShadowLog.stream = System.out;
  }

  @Test
  public void frodoObservableDemo() throws Exception {
    Log.d("FrodoUnitTest","frodoObservableDemo");
    Observable<Integer> observable = FrodoDemo.getObservable();
    observable.subscribe();
  }

  @Test
  public void frodoSubcriberDemo() throws Exception {
    Log.d("FrodoUnitTest","frodoSubcriberDemo");
    Observable.just(2,4,6,8)
        .subscribe(FrodoDemo.getSubscriber());
  }
}