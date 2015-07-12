package net.eightlives.periodic.core.test;

import static org.junit.Assert.assertEquals;

import java.util.function.Consumer;

/**
 * An implementation of {@link Consumer} used for testing.
 * 
 * @author Zack Brown
 * 
 * @param <T>
 *            The type of the consumer.
 */
public class TestConsumer<T> implements Consumer<T>
{
    private T expectedArgument;
    private int acceptCount;

    @Override
    public void accept(T t)
    {
        assertEquals(expectedArgument, t);
        acceptCount++;
    }

    public void setExpectedArgument(T expectedArgument)
    {
        this.expectedArgument = expectedArgument;
    }

    public void assertAndClearAcceptCount(int expectedAcceptCount)
    {
        assertEquals(expectedAcceptCount, acceptCount);
        acceptCount = 0;
    }
}
