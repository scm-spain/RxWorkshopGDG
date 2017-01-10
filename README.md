Rx for Android Workshop
========================

<img src="https://pbs.twimg.com/profile_images/2878181313/5b485c33219fa84677556ab77971e824.png" align="left" width="168px" height="168px"/>
<img align="left" width="0" height="168px" hspace="10"/>

*The <a href="https://plus.google.com/103861605362659114750">GDG Barcelona</a> Rx for Android Workshop by Cristian García*


A series of exercises to learn how to develop applications using RxJava through tests.

<br/>

Instructions
------------
The workshop will have two main parts:
 - `basics`: it contains some small exercises to get familiar with RxJava
  - `RxBasicsUnitTest` it's the entry point, execute the tests and solve them if it's needed. You'll learn to create a simple observable, map and make simple assertions.
  You'll have to write code on `RxBasics` too.
  - `RxTimeTest`is the second step. It pairs with `RxTime`, and this time you'll learn about other ways to create and test an Observable.
  - `RXSchedulersTest` is an extra, just to show what's an Scheduler
 - `flights`: the funny stuff! We have been hired by a startup trying to win the flights comparators market, and our Product Owner have an assignment for us.
  - `FlightsUnitTest`we have a syncronous implementation to retrieve flights from a service, BUT..as the number of services grows the app goes slower, we need to do something to make ir faster.
  - `SuggestUnitTest` is great to have the flights but we need to choose where do we want to fly and to do it, why don't we implement a suggest?

the `frodo` module is just an example of the frodo library from Fernando Cejas.

Slides used at the workshop
---------------------------
https://docs.google.com/presentation/d/1cI88EoPKLrkivajuIpTLCdhBBYlPUZYHcuBiLu_Stug/edit?usp=sharing
