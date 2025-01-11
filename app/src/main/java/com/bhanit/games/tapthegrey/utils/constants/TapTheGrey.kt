package com.bhanit.games.tapthegrey.utils.constants

interface TapTheGrey {
    interface NotificationConstants {
        companion object {
            const val BUILDER: String = "builder"
        }
    }

    interface Color {
        companion object {
            const val GREY: String = "grey"
        }
    }

    interface LevelChange {
        companion object {
            const val LEVEL_CHANGE_SCORE_FOR_LEVEL_ONE: Int = 50
            const val LEVEL_CHANGE_SCORE_FOR_LEVEL_TWO: Int = 50
            const val LEVEL_CHANGE_SCORE_FOR_LEVEL_THREE: Int = 50
            const val LEVEL_CHANGE_SCORE_FOR_LEVEL_FOUR: Int = 50

            const val LEVEL_CHANGE_SCORE: Int = 80
        }
    }

    interface Time {
        companion object {
            const val FIVE_SECOND: Int = 5000
            const val ONE_SECOND: Int = 1000
            const val TWO_SECOND: Int = 2000
            const val NINE_HUNDERED_MILLI_SECOND: Int = 900
            const val EIGHT_HUNDERED_MILLI_SECOND: Int = 800
            const val SEVEN_HUNDERED_MILLI_SECOND: Int = 700
            const val SIX_HUNDERED_MILLI_SECOND: Int = 600
            const val FIVE_HUNDERED_MILLI_SECOND: Int = 500
            const val FOUR_HUNDERED_MILLI_SECOND: Int = 400
            const val THREE_HUNDERED_MILLI_SECOND: Int = 300
            const val TWO_HUNDERED_MILLI_SECOND: Int = 200
            const val ONE_HUNDERED_MILLI_SECOND: Int = 100
        }
    }

    interface Level {
        companion object {
            const val ONE: String = "one"
            const val TWO: String = "two"
            const val THREE: String = "three"
            const val FOUR: String = "four"
        }
    }

    interface LevelInInteger {
        companion object {
            const val ONE: Int = 1
            const val TWO: Int = 2
            const val THREE: Int = 3
            const val FOUR: Int = 4
        }
    }

    interface SharedPreferences {
        companion object {
            const val CITY_LIST: String = "city_list"
            const val LOCALITY_LIST: String = "locality_list"
            const val IS_NEARBY_SELECTED: String = "is_nearby_selected"
            const val LATITUDE: String = "latitude"
            const val LONGITUDE: String = "longitude"
            const val USER_SAVE: String = "patient_save"
        }
    }

    interface Count {
        companion object {
            const val SEVEN: Int = 7
            const val TEN: Int = 10
            const val TWENTY: Int = 20
            const val THIRTY: Int = 30
            const val FORTY: Int = 40
            const val FIFTY: Int = 50
            const val SIXTY: Int = 60
            const val SEVENTY: Int = 70
            const val EIGHTY: Int = 80
            const val NNETY: Int = 90
            const val HUNDRED: Int = 100
        }
    }

    companion object {
        const val PREFERENCE_CITY_KEY: String = "city"
        const val PREFERENCE_LOCATION_KEY: String = "location"
        const val WEBSITE: String = "https://bhanitgaurav.com/tapthegrey.html"
    }
}
