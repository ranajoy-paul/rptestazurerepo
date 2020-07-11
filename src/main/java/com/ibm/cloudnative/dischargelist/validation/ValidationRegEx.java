package com.ibm.cloudnative.dischargelist.validation;

public final class ValidationRegEx {


    private ValidationRegEx(){}
    public static final String PORT_CODE = "^[a-zA-Z0-9]{5,10}$";
    public static final String TERMINAL_CODE_VALUE = "ESALG|NLMSV|OMSLL|EGPSD|MYTPP|MAPTM|MATNG";
    public static final String IMO_VESSEL_CODE = "^[\\s\\S]{0,8}$";
    public static final String VESSEL_OPERATOR_CODE = "^[a-zA-Z0-9]{0,18}$";
    public static final String ARRIVAL_VOYAGE_CODE = "^[a-zA-Z0-9]{0,17}$";
    public static final String DEPARTURE_VOYAGE_CODE = "^[a-zA-Z0-9]{0,17}$";
    public static final String SERVICE_CODE = "^[a-zA-Z0-9]{0,16}$";
    public static final String VESSEL_VISIT_REFERENCE = "^[a-zA-Z0-9_-]{0,30}$";
    public static final String SECONDARY_VESSEL_OPERATOR = "^[a-zA-Z0-9]{0,18}$";
    public static final String OPERATOR_ARRIVAL_VOYAGE = "^[a-zA-Z0-9]{0,18}$";
    public static final String OPERATOR_DEPARTURE_VOYAGE = "^[a-zA-Z0-9]{0,18}$";
    public static final String BERTH_SIDE = "^[a-zA-Z0-9-]{1,12}$";
    public static final String OPERATION_NUMBER = "^((\\d{1,19})|(\\-?[1-9]\\d{0,17}))$";
    public static final String QUAY = "^[a-zA-Z0-9]{1,35}$";
    public static final String BOLLARD_AFTER_POSITION = "^[a-zA-Z0-9]{1,8}$";
    public static final String BOLLARD_BEFORE_POSITION = "^[a-zA-Z0-9]{1,8}$";
}
