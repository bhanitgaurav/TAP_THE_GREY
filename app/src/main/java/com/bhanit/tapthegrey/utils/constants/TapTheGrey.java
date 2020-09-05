package com.bhanit.tapthegrey.utils.constants;

public interface TapTheGrey {
    String PREFERENCE_CITY_KEY = "city";
    String PREFERENCE_LOCATION_KEY = "location";

    interface NotificationConstants {
        String BUILDER = "builder";
    }

    interface Color {
        String GREY = "grey";
    }

    interface Time {
        int FIVE_SECOND = 5000;
        int ONE_SECOND = 1000;
        int NINE_HUNDERED_MILLI_SECOND = 900;
        int EIGHT_HUNDERED_MILLI_SECOND = 800;
        int SEVEN_HUNDERED_MILLI_SECOND = 700;
        int SIX_HUNDERED_MILLI_SECOND = 600;
        int FIVE_HUNDERED_MILLI_SECOND = 500;
        int FOUR_HUNDERED_MILLI_SECOND = 400;
        int THREE_HUNDERED_MILLI_SECOND = 300;
        int TWO_HUNDERED_MILLI_SECOND = 200;
        int ONE_HUNDERED_MILLI_SECOND = 100;

    }

    interface SharedPreferences {
        String CITY_LIST = "city_list";
        String LOCALITY_LIST = "locality_list";
        String IS_NEARBY_SELECTED = "is_nearby_selected";
        String LATITUDE = "latitude";
        String LONGITUDE = "longitude";
        String USER_SAVE = "patient_save";

    }
}
