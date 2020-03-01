package com.andyln;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MockCoachLegacyBuilderTest {

    /* START - Below is an example of how to generate mocks/whens/verifies
     * Needed to create one for the build_ReturnsMockCoachLegacy_success() test,
     * so thought it would be a good idea to show an example as well.
     *
     * Also shows how to create MockCoachLegacy, using either MockCoachLegacyBuilder or MockCoachLegacy
     * (MockCoachLegacyBuilder can use either vararg or array syntax. MockCoachLegacy constructor can only use arrays).
     *
     * MockCoachLegacy methodNameMockCoach = new MockCoachLegacyBuilder()
     *     .mock(
     *         exampleMock
     *     ).when(
     *         () -> {
     *             when(exampleMock.mock(any(Object.class))).thenReturn(exampleMock);
     *             when(exampleMock.when(any(MockCoachRunnable.class))).thenReturn(exampleMock);
     *             when(exampleMock.verify(any(MockCoachRunnable.class))).thenReturn(exampleMock);
     *         }
     *     ).verify(
     *         () -> {
     *             verify(exampleMock, times(1)).mock(any(Object.class));
     *             verify(exampleMock, times(1)).when(any(MockCoachRunnable.class));
     *             verify(exampleMock, times(1)).verify(any(MockCoachRunnable.class));
     *         }
     *     ).build();
     *
     * vs
     *
     * MockCoachLegacy methodNameMockCoach = new MockCoachLegacy(
     *     new Object[]{
     *         exampleMock
     *     },
     *     new MockCoachRunnable[]{
     *         () -> {
     *             when(exampleMock.mock(any(Object.class))).thenReturn(exampleMock);
     *             when(exampleMock.when(any(MockCoachRunnable.class))).thenReturn(exampleMock);
     *             when(exampleMock.verify(any(MockCoachRunnable.class))).thenReturn(exampleMock);
     *         }
     *     },
     *     new MockCoachRunnable[]{
     *         () -> {
     *             verify(exampleMock, times(1)).mock(any(Object.class));
     *             verify(exampleMock, times(1)).when(any(MockCoachRunnable.class));
     *             verify(exampleMock, times(1)).verify(any(MockCoachRunnable.class));
     *         }
     *     }
     * );
     *
     */
    private MockCoachLegacyBuilder exampleMock = mock(MockCoachLegacyBuilder.class);

    private Object[] mocks = { exampleMock };

    private MockCoachRunnable[] whens = {
            () -> {
                when(exampleMock.mock(any(Object.class))).thenReturn(exampleMock);
                when(exampleMock.when(any(MockCoachRunnable.class))).thenReturn(exampleMock);
                when(exampleMock.verify(any(MockCoachRunnable.class))).thenReturn(exampleMock);
            }
    };

    private MockCoachRunnable[] verifies = {
            () -> {
                verify(exampleMock, times(1)).mock(any(Object.class));
                verify(exampleMock, times(1)).when(any(MockCoachRunnable.class));
                verify(exampleMock, times(1)).verify(any(MockCoachRunnable.class));
            }
    };
    /* END - of example*/

    private MockCoachLegacyBuilder mockCoachLegacyBuilder = new MockCoachLegacyBuilder();

    @Test
    void mock_ReturnsSameBuilder_success() {
        MockCoachLegacyBuilder mockCoachBuilderWithMocks = mockCoachLegacyBuilder.mock(mocks);
        assertEquals(mockCoachLegacyBuilder, mockCoachBuilderWithMocks);
    }

    @Test
    void when_ReturnsSameBuilder_success() {
        MockCoachLegacyBuilder mockCoachBuilderWithMocks = mockCoachLegacyBuilder.when(whens);
        assertEquals(mockCoachLegacyBuilder, mockCoachBuilderWithMocks);
    }

    @Test
    void verify_ReturnsSameBuilder_success() {
        MockCoachLegacyBuilder mockCoachBuilderWithMocks = mockCoachLegacyBuilder.verify(verifies);
        assertEquals(mockCoachLegacyBuilder, mockCoachBuilderWithMocks);
    }

    @Test
    void build_ReturnsMockCoachLegacy_success() {
        MockCoach mockCoach = new MockCoachBuilder()
                .mock(
                        exampleMock
                ).when(
                    () -> {
                        when(exampleMock.mock(any(Object.class))).thenReturn(exampleMock);
                        when(exampleMock.when(any(MockCoachRunnable.class))).thenReturn(exampleMock);
                        when(exampleMock.verify(any(MockCoachRunnable.class))).thenReturn(exampleMock);
                    }
                ).verify(
                    () -> {
                        verify(exampleMock, times(1)).mock(any(Object.class));
                        verify(exampleMock, times(1)).when(any(MockCoachRunnable.class));
                        verify(exampleMock, times(1)).verify(any(MockCoachRunnable.class));
                    }
                ).build();
        assertNotNull(mockCoach);
    }
}


