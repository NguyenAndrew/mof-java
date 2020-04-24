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

MockCoach mockCoach = new MockCoach() {
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
};
```

### Unit Testing - Success Case

```
    @Test
    public void givenAnInput_whenCalculatorCalculates_thenWeExpectAnOutput() throws Exception {
        // Given (Setup)
        int expected = SAMPLE_SUB_MULTI_OUTPUT;
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

### Unit Testing - Exception and Other Cases

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

Q: Why should I use this dependency instead of making my own private methods to setup mocks?

A: Private methods can help abstract method call over mocks (such as abstracting whens and verifies), but doesn't help coordinate how your tests interacts with these mocks (which can be a major time sink). This library solves this problem.

Q: Won't this dependency create an additional maintance cost on my project?

A: This library is MIT licensed and deployed over Maven Central. The source code is fully available and is fully unit tested to help provide developer confidence. 

Q: Wont this cause confusion with developers that are not familiar on how to use this library?

A: This library helps implement a go-forward testing methodology. While there may be additional costs upfront learning new advancements in unit testing, it will save much time when creating new features to your code. This depedency has been shown to save many hours on business production code.

Q: I don't believe it is a good practice to couple a default set of verifies with whens. Doesn't this seem like an anti-pattern?

A: Your test code is already doing this coupling implicity. This dependency helps make it explicit, and takes advantage of this explicitly defined structure to achieve testing intelligence capabilities.

Q: Should I made one MockCoach per class, or one MockCoach per method under test?

A: It depends on your test class. There may be advantages to one approach or the other, depending on what (and how many) unit tests you have.

Q: Shouldn't you have all methods in a class reuse the same predefined mocks and whens?

A: It should. This dependency allows for that use case, and additionally also allows for the case where methods may have different initial mock state depending on which method is called.

Q: Why not create separate whens and verifies objects to construct MockCoach?

A: This separate objected implementation was tested in initial POC, but there were auto-formatting issues with IDEs to construct these objects in a human readable format.

Q: Why support up 16 injects mocks?

A: A real world system should have 5 or less mocks constructor-injected or setter-injected. This functionality helps support older code bases.

## Changelog

2.0.1- Hotfix for MockCoachRunnable to make it public instead of default

2.0.0 - Removal of builders and replace MockCoach constructor with overloaded constructors

1.1.0 - Enable usage of MockCoach as an interface for MockCoachLegacy

1.0.0 - GA Release of the project!
