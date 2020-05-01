package BIA.Business.Impact.Analysis.enums;

/**
 * Created at 3:56 PM, 25/04/2020
 */

public enum ProductionType {
    SEQUENTIAL("sequential", "Sequential"),
    PARALLEL("parallel", "Parallel");

    private String key;
    private String value;

    ProductionType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
