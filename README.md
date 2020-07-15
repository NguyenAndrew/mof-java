# Mock Coach

![GitHub](https://img.shields.io/github/license/NguyenAndrew/Mock-Coach)
[![Maven metadata URL](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Frepo1.maven.org%2Fmaven2%2Fcom%2Fandyln%2Fmock-coach%2Fmaven-metadata.xml)](https://search.maven.org/artifact/com.andyln/mock-coach)
[![javadoc](https://javadoc.io/badge2/com.andyln/mock-coach/javadoc.svg)](https://javadoc.io/doc/com.andyln/mock-coach)

The Java library implementing the Mock Coach design pattern. Used in tests to reduce complex logic and boilerplate code in overall codebase.

(Install Instructions, Usage, and FAQ below)

**Can reduce hours and days of writing unit tests into minutes!** 

Mock Coach orchestrates all the mocks used within a method, allowing you to only write setup and verification code relevant for each unit test.

* Q: Why mock orchestration? A: Without it, each unit test needs to maintain the state and verification of its own mocks, and may become brittle when there are multiple units tests for a method. In other words, **mock orchestration helps prevent initial and ongoing tech debt within unit tests**.

This library provides two libraries to use:

1. Mock Coach (Default), library used to quickly write tests, and reduce complexities in business logic. Takes a "test improves business logic, and business logic improves test" approach.
2. Mock Coach Legacy (Backup), library used to write tests quickly (slightly slower and less automated than default approach), where code can't be refactored or is too costly to refactor. 

**Both versions of Mock Coach are compatible with both TDD and non-TDD approaches**. This library helps augment the testing process, while letting the user choose "when" and "how often" to test. 

* Mock Coach (Default) provides cleaner unit tests, and takes a hands-on approach to cleaner business logic. 
* Mock Coach (Legacy) provides cleaner unit tests, and a hands-off approach on the business logic.

## Service Dipath Chain and Service Cyclic Graph

For testing methods and creating new business logic, Mock Coach (Default) helps encourage writing business logic as Service Dipath Chains, over Service Cyclic Graphs (SCG).

* For these design patterns, "Service" refers to any object that is @Autorwired, injected, or constructor injected into the object-under-test.

Examples of Service Dipath Chains:

A -> B -> C

A -> B -> C -> D -> E

A -> B -> C -> A

Example of Service Cyclic Graphs:

A -> B -> C -> B -> D -> E

A -> B -> C -> D -> E -> F -> ... -> X -> B -> Y -> Z

A -> B -> C -> B -> ... -> B -> C

A -> B -> C -> D -> C -> B -> A

Service Dipath Chains are recommended in most cases over Service Cyclic Graphs, because it is faster to understand and work with code when services are used in order one-by-one compared to when service usage is intertwined (both in business logic and in tests).

If the method you are testing happens to be a Service Cyclic Graph, you can either **1. Refactor using sample suggestions** or **2. Use Mock Code Legacy (Backup)** Here are more detailed explanations of these two options:

1. Separate and/or move the service calls into multiple methods within a facade. Call the facade's methods within the current method to achieve same functionality. Sample suggestions: 
    1. A -> B -> C -> B -> D -> E can be converted to A -> N -> D -> E (where N internally calls B -> C).
    2. A -> B -> C -> D -> E -> F -> ... -> X -> B -> Y -> Z can be coverted to A -> N -> Y -> Z (where N internally calls B -> ... -> B)
    3. A -> B -> C -> B -> ... -> B -> C can be coverted to A -> N (where N internally calls B -> C)
    4. A -> B -> C -> D -> C -> B -> A can be converted in the following ways:
        1. A -> N -> A (where N internally calls B -> C, D -> C, B as separate method calls). Useful if you want to create stricter boundaries between service layers, at the cost of creating more methods.
        2. A -> N -> D -> C -> B -> A (where N internally calls B -> C -> D). Useful if you want to reduce amount of methods, at the cost of using services between multiple service layers.  
2. Perform your mocks/when/verifies with Mock Coach Legacy. Mock Coach Legacy is 100% compatible with testing on previously existing codebases, and is encouraged for smaller cyclic graphs where splitting the code can cause more confusion than not. Mock Coach Legacy creates an additional cost and overhead of managing the directed graph of mock usage (compared to the default Mock Coach handling that for you).

## How to Install

Add the following line to your pom.xml

```
<dependency>
  <groupId>com.andyln</groupId>
  <artifactId>mock-coach</artifactId>
  <version>2.0.1</version>
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

MockCoach mockCoach = new MockCoach(
    additionService,
    () -> {
        when(additionService.add(anyInt(), anyInt())).thenReturn(SAMPLE_ADDITION_OUTPUT);
    },
    () -> {
        verify(additionService, times(1)).add(anyInt(), anyInt());
    }
    multiplicationService,
    () -> {
        when(multiplicationService.multiply(anyInt(), anyInt())).thenReturn(SAMPLE_MULTIPLICATION_OUTPUT);
    },
    () -> {
        verify(multiplicationService, times(1)).multiply(anyInt(), anyInt());
    }
    subtractionService,
    () -> {
        when(subtractionService.subtract(anyInt(), anyInt())).thenReturn(SAMPLE_SUBTRACTION_OUTPUT);
    },
    () -> {
        verifyZeroInteractions(subtractionService);
    }
);
```

### Unit Testing - Base Success Case

```
    @Test
    public void givenAnInput_whenCalculatorCalculates_thenWeExpectAnOutput() throws Exception {
        // Given (Setup)
        int expected = SAMPLE_CALCULATOR_OUTPUT;
        int x = 10;
        
        mockCoach.whenAll();

        // When (Run the thing that you want to test)
        int y = calculator.calculateY(x);

        // Then (Asserting what you want to be true, is actually true)
        assertEquals(expected, y);

        // Verify
        mockCoach.verifyAll();
    }
```

### Unit Testing - All Other Cases (Including Exceptions)

```
    @Test
    public void givenAnInput_whenMultiplicationServiceThrowsAnException_thenCalculatorBubblesThatExceptionUp() throws Exception {
        int x = 10;
        
        mockCoach.whenBefore(multiplicationService);
        when(multiplicationService.multiply(anyInt(), anyInt())).thenThrow(new RuntimeException(SAMPLE_EXCEPTION_MESSAGE));

        Exception actualException = assertThrows(Exception.class, () -> {
            calculator.calculateY(x);
        });
        
        assertEquals(SAMPLE_EXCEPTION_MESSAGE, actualException.getMessage());
        
        // Verify
        mockCoach.verify(multiplicationService);
        verifyZeroInteractions(subtractionService);
    }
```

## FAQ

Q: What problem(s) does this library solve.

A: This library solves the problem of delivering production code faster. By using an explicit when/verify structure it helps solve problems 3-6 from the [Unit Testing Guidelines](https://github.com/NguyenAndrew/Unit-Testing-Guidelines).

Q: Why should I use this dependency instead of making my own private methods to setup mocks?

A: Private methods can help abstract method call over mocks (such as abstracting whens and verifies), but doesn't help coordinate how your tests interacts with these mocks (which can be a major time sink). This library solves this problem.

Q: Won't this dependency create an additional maintance cost on my project?

A: This library is MIT licensed and deployed over Maven Central. The source code is fully available and is fully unit tested to help provide developer confidence. Also, this library is easy to seperate and remove, as mentioned later in the FAQ. 

Q: Wont this cause confusion with developers that are not familiar on how to use this library?

A: While there may be additional costs upfront to learn this library, it will save much time when creating new features to your code. This depedency has been shown to save many hours on business production code.

Q: I don't believe it is a good practice to couple a default set of verifies with whens. Doesn't this seem like an anti-pattern?

A: Your test code is already doing this coupling implicity. This dependency defines this structure explicitly, through the construction of the MockCoach object(s), and takes advantage of this defined structure to achieve testing intelligence capabilities. 

Q: What are these "testing intelligence capabilities"?

A: Intelligence capabilities include: Encouraging code to become straightforward through Service Dipath Chains (Not included in Legacy), Avoiding under and overmocking through clearly defined structures, Reusable code by reducing the amount of one-off private methods, and Only needing to create the whens and verifies for a specific test.

Q: My code is not a legacy codebase! Why call the object I am using MockCoachLegacy?

A: In this case, legacy isn't referring to your codebase. Legacy in MockCoachLegacy is an alias that means "don't affect the design of my business logic, improve my test code".

Q: Should I use MockCoach or MockCoachLegacy.

A: Start with MockCoach. If MockCoach doesn't work, MockCoachLegacy will work for any other use case.

Q: Does this library support TDD?

A: Yes. You can use this library using both TDD, and not TDD. After learning the library, it should make both TDD and non-TDD faster in the short term and long term.

Q: Should I made one MockCoach per class, or one MockCoach per method under test?

A: It depends on your test class. There may be advantages to one approach or the other, depending on what (and how many) unit tests you have.

Q: Shouldn't you have all methods in a class reuse the same predefined mocks and whens?

A: It should. This dependency allows for that use case, and additionally also allows for the case where methods may have different initial mock state depending on which method is called.

Q: Why not create separate whens and verifies objects to construct MockCoach?

A: This separate objected implementation was tested in initial POC, but there were auto-formatting issues with IDEs to construct these objects in a human readable format.

Q: I want to remove this library (Didn't like the user experience, found a better library, etc). How difficult is it to do so?

A: Using Mock Coach replaces the implicit whens and verifies between your different unit tests with explicit code. The process of removing the library is simple: Remove the explicit structure by copying and paste those extra whens and verifies back into each of your unit tests.

Q: Why support up 16 injects mocks?

A: A real world system should have 5 or less mocks constructor-injected or setter-injected. This functionality helps support older code bases.

## Changelog

2.0.1 - Hotfix for MockCoachRunnable to make it public instead of default

2.0.0 - Removal of builders and replace MockCoach constructor with overloaded constructors

1.1.0 - Enable usage of MockCoach as an interface for MockCoachLegacy

1.0.0 - GA Release of the project!

## Roadmap

3.0.0 - Details Below:
* ~~Refactor `mockCoach.verify(mockCoach)` to be `mockCoach.verifyThrough(mock)`. Better clarification that these methods run several verify lambdas up to a certain mock, instead of a single when/verify lambda.~~ Done
* ~~Refactor to use [Method Chaining](https://en.wikipedia.org/wiki/Method_chaining) with `.in(MockCoach mockCoach)`. Before: `mockCoach.verifyUpTo(mock);`, After: `verifyUpTo(mock).in(mockCoach);`. New style should allow for faster reading of code, since it reads actions left-to-right and closer resembles Mockito when and verifies.~~ Done
* ~~Introduce `whenTheRest()/whenTheRestAfter(mock)` and `verifyTheRest()/verifyTheRestAfter(mock)`, which runs the rest of when/verifies after the previous or specified `whenBefore(mock)/whenBeforeFirst(mock)` and `verifyUpTo(mock)/verifyBefore(mock)/verifyBeforeFirst()/verifyFirst(mock)`. Allows further reuse of already defined methods of whens/verifies in Mock Coach to remove noisy code, and continues to lower complexity when refactoring tests.~~ Done
* ~~Reduce constructor from 16 mocks (48 parameters) to 8 mocks (24 parameters). Current numbers of parameters is causing strange visual effects and lag on Intellij.~~ Done
* ~~Re-introduce Builder to allow N number of mocks. Support up to n number of mocks. This change is needed, because of the lowering of constructor mocks.~~ Done
* Replace MockCoachRunnable with WhenLambda and VerifyLambda for clearer API. **In Progress**
* Introduce verifyTheRestNoInterfactions and verifyNoInteractionsTheRestAfter(mock) to simplify no and no more interaction calls.
