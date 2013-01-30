package com.raschka.android.bpa;

import com.raschka.android.bpa.domain.LocationFinder;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    private LocationFinder locationFinder;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    @Override
    public void setUp() throws Exception {
        locationFinder = mock(LocationFinder.class);
        when(locationFinder.findLastKnownPostleitzahl()).thenReturn(new Postleitzahl(12345));


    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
