package cl.emergya.mlopez.earthquakerestWStest.dto;

public class MagnitudePair
{
    private String minMagnitude;
    private String maxMagnitude;

    public MagnitudePair()
    {
    }

    public void setMinMagnitude(String mm)
    {
        this.minMagnitude = mm;
    }

    public String getMinMagnitude()
    {
        return this.minMagnitude;
    }

    public void setMaxMagnitude(String mm)
    {
        this.maxMagnitude = mm;
    }

    public String getMaxMagnitude()
    {
        return this.maxMagnitude;
    }
}
