package cl.emergya.mlopez.earthquakerestWStest.dto;

public class DatePair
{
    private String startTime;
    private String endTime;

    public DatePair()
    {
    }

    public void setStartTime(String st)
    {
        this.startTime = st;
    }

    public String getStartTime()
    {
        return this.startTime;
    }

    public void setEndTime(String et)
    {
        this.endTime = et;
    }

    public String getEndTime()
    {
        return this.endTime;
    }
}
