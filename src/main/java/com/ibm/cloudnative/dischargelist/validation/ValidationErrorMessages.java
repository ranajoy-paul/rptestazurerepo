package com.ibm.cloudnative.dischargelist.validation;

public final class ValidationErrorMessages {

    private ValidationErrorMessages(){}
    public static final String PORT_CODE =  "Terminal Code must be between 5 to 10 alphanumeric characters";
    public static final String TERMINAL_CODE_VALUE = "Currently supported Terminal Codes are: ESALG, NLMSV, OMSLL, EGPSD, MYTPP, MAPTM, and MATNG";
    public static final String IMO_VESSEL_CODE = "IMO Vessel code must be up to 8 alphanumeric characters";
    public static final String VESSEL_OPERATOR_CODE =  "Vessel operator code must be up to 18 alphanumeric characters";
    public static final String ARRIVAL_VOYAGE_CODE = "Arrival voyage code must be up to 17 alphanumeric characters";
    public static final String DEPARTURE_VOYAGE_CODE = "Departure voyage code must be up to 17 alphanumeric characters";
    public static final String SERVICE_CODE = "Service Code must be up to 16 alphanumeric characters";
    public static final String VESSEL_VISIT_REFERENCE = "Vessel Visit Reference must be up to 30 alphanumeric characters";
    public static final String FORECAST_LOAD_MOVES = "Forecast Load Moves must be between 0 to 9999";
    public static final String FORECAST_DISCHARGE_MOVES = "Forecast Discharge Moves must be between 0 to 9999";
    public static final String FORECAST_RESTOW_MOVES = "Forecast Restow Moves must be between 0 to 9999";
    public static final String SECONDARY_VESSEL_OPERATOR = "Secondary Vessel Operator must be up to 18 alphanumeric characters";
    public static final String OPERATOR_ARRIVAL_VOYAGE = "Operator Arrival Voyage must be up to 18 alphanumeric characters";
    public static final String OPERATOR_DEPARTURE_VOYAGE =  "Operator Departure Voyage must be up to 18 alphanumeric characters";
    public static final String BERTH_SIDE = "Berth Side must be 1 to 12 alphanumeric characters";
    public static final String OPERATION_NUMBER = "Operation Number must be a numeric within negative 18 digits to positive 19 digits length";
    public static final String QUAY = "Quay must be an alpha numeric upto 35 char length";
    public static final String BOLLARD_AFTER_POSITION = "Bollard after position must be 1 to 8 alphanumeric characters";
    public static final String BOLLARD_BEFORE_POSITION = "Bollard before position must be 1 to 8 alphanumeric characters";
    public static final String BOLLARD_AFTER_POSITION_OFFSET = "Bollard after position offset must be a numeric between 1 to 10 digit long prefixed with + or - sign";
    public static final String BOLLARD_BEFORE_POSITION_OFFSET = "Bollard before position offset must be a numeric between 1 to 10 digit long prefixed with + or - sign";
}
