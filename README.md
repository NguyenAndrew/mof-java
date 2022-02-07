# Mock Orchestration Framework (Mof) (Java)

![GitHub](https://img.shields.io/github/license/NguyenAndrew/Mock-Coach)
[![Maven metadata URL](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Frepo1.maven.org%2Fmaven2%2Fcom%2Fandyln%2Fmock-coach%2Fmaven-metadata.xml)](https://search.maven.org/artifact/com.andyln/mock-coach)
[![javadoc](https://javadoc.io/badge2/com.andyln/mock-coach/javadoc.svg)](https://javadoc.io/doc/com.andyln/mock-coach)

The Java library implementing the Mock Orchestration Framework (Mof). Used in tests to reduce complex logic and boilerplate code in overall codebase.

(Install Instructions, Usage, and FAQ below)

**Can reduce hours and days of writing unit tests into minutes!** 

Mof orchestrates all the mocks used within a method, allowing you to only write setup and verification code relevant for each unit test.

* **Q:** Why Mof? **A:** Without it, each unit test needs to maintain the state and verification of its own mocks, and may become brittle when there are multiple units tests for a method. In other words, **mock orchestration helps prevent initial and ongoing tech debt within unit tests**.

This library provides two libraries to use:

1. Mof (Default), library used to quickly write tests, and reduce complexities in business logic. Takes a "test improves business logic, and business logic improves test" approach.
2. Nof (Backup), library used to write tests quickly (slightly slower and less automated than default approach), where code can't be refactored or is too costly to refactor. 

**Both Mof and Nof are compatible with both TDD and non-TDD approaches**. This library helps augment the testing process, while letting the user choose "when" and "how often" to test. 

* Mof (Default) provides cleaner unit tests, and takes a hands-on approach to cleaner business logic. 
* Nof (Backup) provides cleaner unit tests, and a hands-off approach on the business logic.

## Simple Curves and Non-Simple Curves

For testing methods and creating new business logic, Mof helps encourage writing business logic using the Simple Programming Paradigm: Using Simple Curves over Non-Simple Curves

* For these design patterns, "Service" refers to any object that is @Autorwired, injected, or constructor injected into the object-under-test.

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

This library is on Maven Central: https://search.maven.org/artifact/com.andyln/mock-coach/

## Example Usages

### Creating MockCoach

```
private AdditionService additionService = mock(AdditionService.class);
private MultiplicationService multiplicationService = mock(MultiplicationService.class);
private SubtractionService subtractionService = mock(SubtractionService.class);

private Calculator calculator = new Calculator(additionService, multiplicationService, subtractionService);

private final Mof mof = new Mof.Builder()
    .add(
        additionService,
        () -> when(additionService.add(anyInt(), anyInt())).thenReturn(SAMPLE_ADDITION_OUTPUT),
        () ->verify(additionService, times(1)).add(anyInt(), anyInt())
    )
    .add(
        multiplicationService,
        () -> when(multiplicationService.multiply(anyInt(), anyInt())).thenReturn(SAMPLE_MULTIPLICATION_OUTPUT),
        () -> verify(multiplicationService, times(1)).multiply(anyInt(), anyInt())
    )
    .add(
        subtractionService,
        () -> when(subtractionService.subtract(anyInt(), anyInt())).thenReturn(SAMPLE_SUBTRACTION_OUTPUT),
        () -> verifyZeroInteractions(subtractionService)
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
        // Given (Setup Data)
        int expected = SAMPLE_CALCULATOR_OUTPUT;
        int x = 10;
        
        // Given (Setup Data Processors)
        mof.when(ALL);

        // When (Run the thing that you want to test)
        int y = calculator.calculateY(x);

        // Then (Asserting what you want to be true, is actually true)
        assertEquals(expected, y);

        // Verify (Asserting the Data Processors are called in the way you want)
        mof.verify(ALL);
    }
```

### Unit Testing - All Other Cases (Including Exceptions)

```
    @Test
    public void whenMultiplicationServiceThrowsAnException_thenCalculatorBubblesThatExceptionUp() throws Exception {
        // Given (Setup Data)
        int x = 10;
    
        // Given (Setup Data Processors)
        mof.whenBefore(multiplicationService);
        when(multiplicationService.multiply(anyInt(), anyInt())).thenThrow(new RuntimeException(SAMPLE_EXCEPTION_MESSAGE));
    
        // When (Run the thing that you want to test)
        Exception actualException = assertThrows(Exception.class, () -> {
            calculator.calculateY(x);
        });
    
        // Then (Asserting what you want to be true, is actually true)
        assertEquals(SAMPLE_EXCEPTION_MESSAGE, actualException.getMessage());
        
        // Verify (Asserting the Data Processors are called in the way you want)
        mof.verifyThrough(multiplicationService);
        mof.verifyNoInteractionsTheRest();
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

**A:** Intelligence capabilities include: Encouraging code to become straightforward through Service Dipath Chains (Not included in Legacy), Avoiding under and overmocking through clearly defined structures, Reusable code by reducing the amount of one-off private methods, and Only needing to create the whens and verifies for a specific test.

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

**Q:** Why not create separate whens and verifies objects to construct MockCoach?

**A:** This separate objected implementation was tested in initial POC, but there were auto-formatting issues with IDEs to construct these objects in a human readable format.

---

**Q:** I want to remove this library (Didn't like the user experience, found a better library, etc). How difficult is it to do so?

**A:** Using Mof replaces the implicit whens and verifies between your different unit tests with explicit code. The process of removing the library is simple: Remove the explicit structure by copying and paste those extra whens and verifies back into each of your unit tests.

## Changelog

6.0.0 - Details below

* Start converting project name from MockCoach to Mock Orchestration Framework
* Start replacing outdated terminology with the ones in the Simple Programming Paradigm - https://github.com/NguyenAndrew/Simple-Programming
* Replace MockCoach with Mof, which is (Simple) Mock Orchestration Framework
* Replace MockCoachLegacy with Nof, which is (Non Simple Mock) Orchestration Framework
* "Inheritance Removal" between Nof and Mof to support deviating usage of operation
* verifyNoInteractions is removed from Nof, an unsupported operation that can now be removed, supported by inheritance removal
* Nof only allows enums for its Mock Markers to make library usage clearer, supported by inheritance removal
* Mof allows any objects to be used for its mocks to make usage more flexible
* Remove public constructors Mof and Nof and only allow construction through Builder to simplify and make construction process consistent
* Add copy method to builders for easier reusability
* Error messages are updated to match updated functionality

5.0.0 - Remove methodables to reduce maintenance and usability complexity

4.0.0 - Details below

* Condense methodables statics into class. Improves IDE auto completion for these statics.
* Fix MockCoachLegacy.Builder to include missing override annotation.
* Refactor `setVerifyNoInteractions(...)` to `putVerifyNoInteractions(...)`.

3.1.0 - Add `withVerifyNoInteractions(...)` to builders

3.0.0 - Details below

* Introduce `whenTheRest()/whenTheRestAfter(mock)` and `verifyTheRest()/verifyTheRestAfter(mock)`
* Introduce `verifyNoInteractionsTheRest()` and `verifyNoInteractionsTheRestAfter(mock)` to simplify no and no more interaction calls
* Re-introduce Builder to allow arbitrary number of mocks
* Reduce constructor from 16 mocks (48 parameters) to 8 mocks (24 parameters)
* Replace MockCoachRunnable with WhenLambda and VerifyLambda for clearer API
* Refactor `mockCoach.verify(mockCoach)` to be `mockCoach.verifyThrough(mock)`
* Refactor to use [Method Chaining](https://en.wikipedia.org/wiki/Method_chaining) with `.in(MockCoach mockCoach)`

2.0.1 - Hotfix for MockCoachRunnable to make it public instead of default

2.0.0 - Removal of builders and replace MockCoach constructor with overloaded constructors

1.1.0 - Enable usage of MockCoach as an interface for MockCoachLegacy

1.0.0 - GA Release of the project!
