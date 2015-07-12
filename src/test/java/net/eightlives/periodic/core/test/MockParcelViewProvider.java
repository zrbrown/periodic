package net.eightlives.periodic.core.test;

import static org.junit.Assert.assertEquals;
import net.eightlives.periodic.core.ParcelContainer;
import net.eightlives.periodic.core.parcel.ParcelViewProvider;

/**
 * A mock implementation of {@link ParcelViewProvider} used for testing.
 * 
 * @author Zack Brown
 */
public class MockParcelViewProvider implements ParcelViewProvider<Object>
{
    private ParcelContainer expectedContainer;
    private Object view;

    @Override
    public Object showView(ParcelContainer container)
    {
        assertEquals(expectedContainer, container);

        return view;
    }

    public void setExpectedContainer(ParcelContainer expectedContainer)
    {
        this.expectedContainer = expectedContainer;
    }

    public void setView(Object view)
    {
        this.view = view;
    }
}
