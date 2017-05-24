package com.application.yasic.crazyofthestorm.Model

import android.util.Log
import com.application.yasic.crazyofthestorm.Object.HeroTrait
import com.google.gson.JsonArray
import com.google.gson.JsonObject

class HeroDataModel(){
    fun getHeroId(heroJson: JsonObject): String{
        return heroJson.get("id").asString
    }

    fun getHeroName(heroJson: JsonObject):String {
        return heroJson.get("name").asString
    }

    fun getHeroTitle(heroJson: JsonObject):String {
        return if(heroJson.get("title").isJsonNull) "" else "--" + heroJson.get("title").asString
    }

    fun getHeroRole(heroJson: JsonObject):String {
        return heroJson.get("role-cn").asString
    }

    fun getHeroDescription(heroJson: JsonObject):String{
        return heroJson.get("description").asString
    }

    fun getHPText(heroJson: JsonObject): String{
        return heroJson.get("property").asJsonObject.get("HP").asString
    }

    fun getHPValue(heroJson: JsonObject, lv: Int): String{
        return Math.floor(heroJson.get("property").asJsonObject.get("HP-start").asFloat * Math.pow((1 + heroJson.get("property").asJsonObject.get("HP-grown-ratio").asFloat).toDouble(), (lv - 1).toDouble())).toString()
    }

    fun getHPRecoveryText(heroJson: JsonObject): String{
        return heroJson.get("property").asJsonObject.get("HP").asString + "回复"
    }

    fun getHPRecoveryValue(heroJson: JsonObject, lv: Int): String{
        return Math.floor(heroJson.get("property").asJsonObject.get("HP-recover-start").asFloat * Math.pow((1 + heroJson.get("property").asJsonObject.get("HP-recover-grown-ratio").asFloat).toDouble(), (lv - 1).toDouble())).toString()
    }

    fun getMPText(heroJson: JsonObject): String{
        return heroJson.get("property").asJsonObject.get("MP").asString
    }

    fun getMPValue(heroJson: JsonObject, lv: Int): String{
        if (heroJson.get("property").asJsonObject.get("MP-start").asString == "N/A" || heroJson.get("property").asJsonObject.get("MP-grown").asString == "N/A"){
            return heroJson.get("property").asJsonObject.get("MP-start").asString
        }
        return Math.floor((heroJson.get("property").asJsonObject.get("MP-start").asFloat + (lv - 1) * heroJson.get("property").asJsonObject.get("MP-grown").asFloat).toDouble()).toString()
    }

    fun getMPRecoveryText(heroJson: JsonObject): String{
        return heroJson.get("property").asJsonObject.get("MP").asString + "回复"
    }

    fun getMPRecoveryValue(heroJson: JsonObject, lv: Int): String{
        if (heroJson.get("property").asJsonObject.get("MP-recover-start").asString == "N/A" || heroJson.get("property").asJsonObject.get("MP-recover-grown").asString == "N/A"){
            return heroJson.get("property").asJsonObject.get("MP-recover-start").asString
        }
        return Math.floor((heroJson.get("property").asJsonObject.get("MP-recover-start").asFloat + (lv - 1) * (1 + heroJson.get("property").asJsonObject.get("MP-recover-grown").asFloat)).toDouble()).toString()
    }

    fun getAttackValue(heroJson: JsonObject, lv: Int): String{
        return Math.floor(heroJson.get("property").asJsonObject.get("attack-damage-start").asFloat * Math.pow((1 + heroJson.get("property").asJsonObject.get("attack-damage-grown-ratio").asFloat).toDouble(), (lv - 1).toDouble())).toString()
    }

    fun getSiegeValue(heroJson: JsonObject, lv: Int): String{
        if (heroJson.get("property").asJsonObject.get("siege-damage-grown-ratio").isJsonNull) {
            return Math.floor(heroJson.get("property").asJsonObject.get("siege-damage-start").asFloat.toDouble()).toString()
        }
        return Math.floor(heroJson.get("property").asJsonObject.get("siege-damage-start").asFloat * Math.pow((1 + heroJson.get("property").asJsonObject.get("siege-damage-grown-ratio").asFloat).toDouble(), (lv - 1).toDouble())).toString()
    }

    fun getAttackPerSecondValue(heroJson: JsonObject): String{
        return heroJson.get("property").asJsonObject.get("attack-per-second").asString
    }

    fun getAttackRange(heroJson: JsonObject): String{
        return heroJson.get("property").asJsonObject.get("attack-range").asString
    }

    fun getWalkSpeed(heroJson: JsonObject): String{
        return heroJson.get("property").asJsonObject.get("walk-speed").asString
    }

    fun getRideSpeed(heroJson: JsonObject): String{
        return heroJson.get("property").asJsonObject.get("ride-speed").asString
    }

    fun getHeroTrait(heroJson: JsonObject): JsonArray {
        return heroJson.get("trait").asJsonArray
    }

    fun getHeroStartingAbility(heroJson: JsonObject): JsonArray{
        return heroJson.get("ability").asJsonArray
    }

    fun getHeroicAbility(heroJson: JsonObject): JsonArray{
        return heroJson.get("big-ability").asJsonArray
    }

    fun getTalent(heroJson: JsonObject): JsonObject{
        return heroJson.get("talent").asJsonObject
    }

    fun getPrimaryTalent(heroJson: JsonObject): String{
        return heroJson.get("primary-talent").asString
    }
}