package net.eightlives.periodic.core.parcel;

import static org.junit.Assert.assertEquals;
import net.eightlives.periodic.core.ParcelContainer;
import net.eightlives.periodic.core.test.MockParcelViewProvider;
import net.eightlives.periodic.core.test.TestConsumer;

import org.junit.Test;

/**
 * An abstract test which should be extended by classes testing implementations
 * of {@link AbstractParcel}.
 * 
 * @author Zack Brown
 *
 * @param <T>
 *            The view type. This needs to be the same type as the implementing
 *            Parcel's type.
 */
public abstract class AbstractParcelTest<T>
{
    /**
     * The mock view provider that should be bound to the Parcel implementation
     * during this test.
     */
    protected final MockParcelViewProvider viewProvider = new MockParcelViewProvider();

    private final AbstractParcel<T> parcel = createParcel();
    private final ParcelContainer parcelContainer = new ParcelContainer()
    {
    };

    /**
     * Tests {@link AbstractParcel#getParcelData()}.
     */
    @Test
    public void testGetParcelData()
    {
        ParcelData parcelData = parcel.getParcelData();

        assertEquals(parcel.getId(), parcelData.getParcelId());
        assertEquals(parcel.createDisplayText(), parcelData.getDisplayText());
        assertEquals(parcel.getIntent(), parcelData.getParcelIntent());
    }

    /**
     * Tests {@link AbstractParcel#showParcelContent(ParcelContainer)}.
     */
    @Test
    public void testShowParcelContent()
    {
        Object view = new Object();
        viewProvider.setView(view);
        viewProvider.setExpectedContainer(parcelContainer);

        parcel.showParcelContent(parcelContainer);
        assertEquals(1, getCreateParcelControllerCount());
        assertEquals(view, getCreateParcelControllerView());

        parcel.showParcelContent(parcelContainer);
        assertEquals(0, getCreateParcelControllerCount());
    }

    /**
     * Tests
     * {@link AbstractParcel#addParcelVisibilityListener(java.util.function.Consumer)}
     * ,
     * {@link AbstractParcel#removeParcelVisibilityListener(java.util.function.Consumer)}
     * , and {@link AbstractParcel#notifyVisibilityListeners(boolean)}.
     */
    @Test
    public void testVisibilityListeners()
    {
        TestConsumer<Boolean> listener = new TestConsumer<Boolean>();
        parcel.addParcelVisibilityListener(listener);

        for (boolean isVisible : new boolean[] { true, false })
        {
            listener.setExpectedArgument(isVisible);
            parcel.notifyVisibilityListeners(isVisible);

            listener.assertAndClearAcceptCount(1);
        }

        parcel.removeParcelVisibilityListener(listener);

        for (boolean isVisible : new boolean[] { true, false })
        {
            parcel.notifyVisibilityListeners(isVisible);

            listener.assertAndClearAcceptCount(0);
        }
    }

    /**
     * Creates the {@link AbstractParcel} implementation being tested.
     * 
     * @return a new instance of the implementation being tested
     */
    protected abstract AbstractParcel<T> createParcel();

    /**
     * Returns the number of times
     * {@link AbstractParcel#createParcelController(Object)} has been called.
     * 
     * @return the method call count
     */
    protected abstract int getCreateParcelControllerCount();

    /**
     * Returns the last view with which
     * {@link AbstractParcel#createParcelController(Object)} was called.
     * 
     * @return the last method argument
     */
    protected abstract Object getCreateParcelControllerView();
}
