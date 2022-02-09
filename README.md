# Mock Orchestration Framework (Mof) (Java)

![GitHub](https://img.shields.io/github/license/NguyenAndrew/mof-java)
[![Maven metadata URL](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Frepo1.maven.org%2Fmaven2%2Fcom%2Fandyln%2Fmof%2Fmaven-metadata.xml)](https://search.maven.org/artifact/com.andyln/mof)
[![javadoc](https://javadoc.io/badge2/com.andyln/mof/javadoc.svg)](https://javadoc.io/doc/com.andyln/mof)

The Java library implementing the Mock Orchestration Framework (Mof). Used in tests to reduce complex logic and boilerplate code in overall codebase.

([Install Instructions](#how-to-install), [Usage](#example-usages), and [FAQ](#faq) below)

**Can reduce hours and days of writing unit tests into minutes!** 

Mof orchestrates all the mocks used within a method, allowing you to only write setup and verification code relevant for each unit test.

* **Q:** Why Mof? **A:** Without it, each unit test needs to maintain the state and verification of its own mocks, and may become brittle when there are multiple units tests for a method. In other words, **mock orchestration helps prevent initial and ongoing tech debt within unit tests**.

This library provides two libraries to use:

1. Mof (Default), library used to quickly write tests, and reduce complexities in business logic. Takes a "test improves business logic, and business logic improves test" approach.
2. Nof (Backup), library used to write tests quickly (slightly slower and less automated than default approach), where code can't be refactored or is too costly to refactor. 

**Both Mof and Nof are compatible with both TDD and non-TDD approaches**. This library helps augment the testing process, while letting the user choose "when" and "how often" to test. 

* Mof (Default) provides cleaner unit tests, and takes a hands-on approach to cleaner business logic. 
* Nof (Backup) provides cleaner unit tests, and a hands-off approach on the business logic.

## Simple Programming Paradigm (Explaining how Mof works with Simple Curves and Non-Simple Curves)

For testing methods and creating new business logic, Mof helps encourage writing business logic using the Simple Programming Paradigm: Using Simple Curves over Non-Simple Curves. 

* Link that describes Simple Programming: https://github.com/NguyenAndrew/Simple-Programming

* For the below description, "Service" refers to any object that is @Autorwired, injected, or constructor injected into the object-under-test.

Examples of Simple Curves:

A -> B -> C

A -> B -> C -> D -> E

A -> B -> C -> A (Note: A Simple Curve that starts and ends with the same service is also called a Simple Closed Curve)

Example of Non-Simple Curves:

A -> B -> C -> B -> D -> E

A -> B -> C -> D -> E -> F -> ... -> X -> B -> Y -> Z

A -> B -> C -> B -> ... -> B -> C

A -> B -> C -> D -> C -> B -> A (Note: A Non-Simple Curve that starts and ends with the same service is also called a Non-Simple Closed Curve)

Simple Curves are recommended in most cases over Non-Simple Curves, because it is faster to understand and work with code when services are used in order one-by-one compared to when service usage is intertwined (both in business logic and in tests).

If the method you are testing happens to be a Non-Simple Curve, you can either **1. Refactor using sample suggestions** or **2. Use Nof (Backup)** Here are more detailed explanations of these two options:

1. Separate and/or move the service calls into multiple methods within a facade. Call the facade's methods within the current method to achieve same functionality. Sample suggestions: 
    1. A -> B -> C -> B -> D -> E can be converted to A -> N -> D -> E (where N internally calls B -> C -> B).
    2. A -> B -> C -> D -> E -> F -> ... -> X -> B -> Y -> Z can be coverted to A -> N -> Y -> Z (where N internally calls B -> ... -> B)
    3. A -> B -> C -> B -> ... -> B -> C can be coverted to A -> N (where N internally calls B -> C)
    4. A -> B -> C -> D -> C -> B -> A can be converted in the following ways:
        1. A -> N -> A (where N internally calls B -> C, D -> C, B as separate method calls). Useful if you want to create stricter boundaries between service layers, at the cost of creating more methods.
        2. A -> N -> D -> C -> B -> A (where N internally calls B -> C -> D). Useful if you want to reduce amount of methods, at the cost of using services between multiple service layers.  
2. Perform your mocks/when/verifies with Nof. Nof is 100% compatible with testing on previously existing codebases, and is encouraged for smaller Non-Simple Curves where splitting the code can cause more confusion than not. Nof creates an additional cost and overhead of managing the directed graph of mock usage (compared to the Mof handling that automatically for you).

## How to Install

Add the following line to your pom.xml

```
<dependency>
  <groupId>com.andyln</groupId>
  <artifactId>mof</artifactId>
  <version>1.0.0</version>
  <scope>test</scope>
</dependency>
```

This library is on Maven Central: https://search.maven.org/artifact/com.andyln/mof/

## Example Usages

### Creating Mof

```
private Timer timer = mock(Lock.class);
private Motor motor = mock(Motor.class);
private Magnetron magnetron = mock(Magnetron.class);

private Microwave microwave = new Microwave(noiseMaker, motor, magnetron);

private final Mof mof = new Mof.Builder()
    .add(
        timer,
        () -> when(timer.makeNoise(anyInt()).thenReturn(SAMPLE_ALARM_NOISE),
        () -> verify(noiseMaker, times(1)).makeNoise(anyInt())
    )
    .add(
        motor,
        () -> when(motor.spinTurntable(any(Timer.class)).thenReturn(SAMPLE_SPINNING_TURNTABLE),
        () -> verify(motor, times(1)).spinTurnTabl(anyInt())
    )
    .add(
        magnetron,
        () -> when(magnetron.heatFood(any(Food.class), any(Timer.class))).thenReturn(SAMPLE_HEATED_FOOD),
        () -> verify(magnetron, times(1)).heatFood(any(Food.class), any(Timer.class))
    )
    .enableVerifyNoInteractions(verifyNoInteractionLambda)
    .build();
```

### Unit Testing - Base Success Case

```
import static com.andyln.Mof.ALL;

// ...

    @Test
    public void success() throws Exception {
        // Given (Setup data)
        Food expected = SAMPLE_HOT_FOOD;
        
        // Given (Setup processors)
        mof.when(ALL);

        // When (Run the thing that you want to test)
        Food actual = microwave.heatFood(SAMPLE_COLD_FOOD, SAMPLE_SECONDS);

        // Then (Asserting what you want to be true, is actually true)
        assertEquals(expected, actual);

        // Verify (Asserting the processors are called in the way you want)
        mof.verify(ALL);
    }
```

### Unit Testing - An Exception Case

```
import static com.andyln.Mof.REMAINING;

// ...

@Test
public void whenMotorFails_ThenThrowAnException() throws Exception {
    // Given (Setup data)
    // No data to setup

    // Given (Setup processors)
    mof.whenBefore(motor);
    when(motor.spinTurntable(any(Timer.class))).thenThrow(new RuntimeException(SAMPLE_EXCEPTION_MESSAGE));

    // When (Run the thing that you want to test)
    Exception actualException = assertThrows(Exception.class, () -> microwave.heatFood(SAMPLE_COLD_FOOD, SAMPLE_SECONDS));

    // Then (Asserting what you want to be true, is actually true)
    assertEquals(SAMPLE_EXCEPTION_MESSAGE, actualException.getMessage());
    
    // Verify (Asserting the processors are called in the way you want)
    mof.verifyThrough(motor);
    mof.verifyNoInteractions(REMAINING);
}
```

## FAQ

**Q:** What problem(s) does this library solve.

**A:** This library solves the problem of delivering production code faster. By using an explicit when/verify structure it helps solve problems 3-6 from the [Unit Testing Guidelines](https://github.com/NguyenAndrew/Unit-Testing-Guidelines).

---

**Q:** Why should I use this dependency instead of making my own private methods to setup mocks?

**A:** Private methods can help abstract method call over mocks (such as abstracting whens and verifies), but doesn't help coordinate how your tests interacts with these mocks (which can be a major time sink). This library solves this problem.

---

**Q:** Won't this dependency create an additional maintance cost on my project?

**A:** This library is MIT licensed and deployed over Maven Central. The source code is fully available and is fully unit tested to help provide developer confidence. Also, this library is easy to seperate and remove, as mentioned later in the FAQ. 

---

**Q:** Wont this cause confusion with developers that are not familiar on how to use this library?

**A:** While there may be additional costs upfront to learn this library, it will save much time when creating new features to your code. This depedency has been shown to save many hours on business production code.

---

**Q:** I don't believe it is a good practice to couple a default set of verifies with whens. Doesn't this seem like an anti-pattern?

**A:** Your test code is already doing this coupling implicity. This dependency defines this structure explicitly, through the construction of the Mof object(s), and takes advantage of this defined structure to achieve testing intelligence capabilities. 

---

**Q:** What are these "testing intelligence capabilities"?

**A:** Intelligence capabilities include: 
* Encouraging code to become more straightforward through the Simple Programming paradigm
* Avoiding under and overmocking through the usage of a framework (Mock Orchestration Framework)
* Reusable code by reducing the amount of one-off private methods, and only needing to create the whens and verifies for a specific test.

---

**Q:** Should I use Mof (Default) or Nof (Backup).

**A:** Start with Mof (Default). If Mof (Default) doesn't work, Nof (Backup) will work for any other use case.

---

**Q:** Does this library support TDD?

**A:** Yes. You can use this library using both TDD, and not TDD. After learning the library, it should make both TDD and non-TDD faster in the short term and long term.

---

**Q:** Should I made one Mof per class, or one Mof per method-under-test?

**A:** Mof and Nof supports both styles. I have seen this library works best by making a Mof object for each method-under-test, where that method has multiple unit tests.

---

**Q:** Shouldn't you have all methods in a class reuse the same predefined mocks and whens?

**A:** It should. This dependency allows for that use case, and additionally also allows for the case where methods may have different initial mock state depending on which method is called.

---

**Q:** Why not create separate whens and verifies objects to construct Mof?

**A:** This separate objected implementation was tested in initial POC, but there were auto-formatting issues with IDEs to construct these objects in a human readable format.

---

**Q:** In the above "Unit Testing - An Exception Case example", by using `verifyThrough(REMAINING)`, are you forgetting to verify that the magnetron is not running? I think you should add code for noVerifyInteractions on the magnetron, so that we can guarantee it doesn't run and cover all of the test cases.

**A:** This test case (and other similar combinations) are explicitly covered when you create the Mof object, as you are stating that the timer (methods runs first) -> motor (methods run second) -> magnetron (methods run third).

---

**Q:** Following up on the previous question, what if I accidentally constructed the Mof object in the wrong order (such as timer -> magnetron -> motor)? Wouldn't that miss verifying unexpected behavior?

**A:** You would be notified by your unit tests, and you would be able to fix the misorder. Mof prevents overmocking and verifies all methods that need to be verified. 

In the test case where you ran `mof.whenBefore(magnetron); ... mof.verifyBefore(magnetron); ... mof.verifyNoInteractions(REMAINING)`, and the business logic has the motor running before magnetron, then an exception would be thrown. 

The reason is that motor would have been called, but by stating `mof.verifyNoInteractions(REMAINING)`, the test would state something like: "Expected no calls to this method, but this method is called 1 time".

In summary: The combination of your business logic, Mof, and your unit tests, ensures your tests are testing what they need to, and helps keep your business logic and unit tests up-to-date with each other.

---

**Q:** I want to remove this library (Didn't like the user experience, found a better library, etc). How difficult is it to do so?

**A:** Using Mof replaces the implicit whens and verifies between your different unit tests with explicit code. The process of removing the library is simple: Remove the explicit structure by copying and paste those extra whens and verifies back into each of your unit tests.

## Changelog

1.1.0 - Update Maven Central links. Point to Mof instead of Mock Coach

1.0.0 - GA Release of the project!

0.1.0 - Initial release to Maven
