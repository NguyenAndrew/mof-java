# Mock Coach

![GitHub](https://img.shields.io/github/license/NguyenAndrew/Mock-Coach)
[![Maven metadata URL](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Frepo1.maven.org%2Fmaven2%2Fcom%2Fandyln%2Fmock-coach%2Fmaven-metadata.xml)](https://search.maven.org/artifact/com.andyln/mock-coach)
[![javadoc](https://javadoc.io/badge2/com.andyln/mock-coach/javadoc.svg)](https://javadoc.io/doc/com.andyln/mock-coach)

The Java library implementing the Mock Coach design pattern. Used in tests to reduce complex logic and boilerplate code in overall codebase.

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
  <version>2.0.0</version>
  <scope>test</scope>
</dependency>
```

This library is on Maven Central: https://search.maven.org/artifact/com.andyln/mock-coach/

## Example Usages

### Creating MockCoach

Mock Coach

```
private AdditionService additionService = mock(AdditionService.class);
private MultiplicationService multiplicationService = mock(MultiplicationService.class);
private SubtractionService subtractionService = mock(SubtractionService.class);
private SubMultiService subMultiService = mock(SubMultiService.class);

private Calculator calculator = new Calculator(additionService, multiplicationService, subtractionService, subMultiService);

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
    },
    subMultiService,
    () -> {
        when(subMultiService.subtractThenMultiplyBy2(anyInt(), anyInt())).thenReturn(SAMPLE_SUB_MULTI_OUTPUT);
    },
    () -> {
        verify(subMultiService, times(1)).subtractThenMultiplyBy2(anyInt(), anyInt());
    }
};
```

### Unit Testing - Success Case

Success Case

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
        mockCoach.verifyEverything();
    }
```

### Unit Testing - Exception and Other Cases

Exception Case

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
