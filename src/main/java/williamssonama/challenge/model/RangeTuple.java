package williamssonama.challenge.model;

public class RangeTuple implements Comparable<RangeTuple>  {
    private Integer rangeValue;
    private RANGE_TYPE rangeType;
    private Integer id;

    public RangeTuple(Integer rangeValue, RANGE_TYPE rangeType, Integer id) {
        this.rangeValue = rangeValue;
        this.rangeType = rangeType;
        this.id = id;
    }

    public Integer getRangeValue() {
        return rangeValue;
    }

    public RANGE_TYPE getRangeType() {
        return rangeType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Guarantee a specific order according to the following priorities:
     * lower range values sort before higher ones.
     * if the range values are the same look at the type
     *    Start nodes always sort before end nodes with the same range value.
     *    If both type and range values are the same, the sort order is based on order of appearance in the raw list.
     * @param rt
     * @return
     */
    @Override
    public int compareTo(RangeTuple rt) {

        if (this.getRangeValue() < rt.getRangeValue()) {
            return -1;
        }

        if (this.getRangeValue() == rt.getRangeValue()) {
            if (this.getRangeType().equals(rt.getRangeType())) {
                return this.getId() - rt.getId();
            } else if (RANGE_TYPE.START.equals(this.getRangeType()) && RANGE_TYPE.END.equals(rt.getRangeType())) {
                return -1;
            }
        }
        return 1;
    }
}