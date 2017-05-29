package com.application.yasic.crazyofthestorm.Model

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.application.yasic.crazyofthestorm.Object.SimpleHeroItem
import com.application.yasic.crazyofthestorm.R
import com.application.yasic.crazyofthestorm.Util.WannaGet
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.squareup.picasso.Picasso
import okhttp3.*

class NetworkModel() {

    fun getHeroList(): MutableList<SimpleHeroItem> {
        val jsonParser = JsonParser()
        val response = WannaGet("heroes").run()
        val heroArray = jsonParser.parse(response).asJsonArray
        val heroList = mutableListOf<SimpleHeroItem>()
        for (item in heroArray) {
            heroList.add(Gson().fromJson(item, SimpleHeroItem::class.java))
        }
        return heroList
    }

    fun getHeroInformationAsJson(heroId: String): JsonObject {
        val response = WannaGet("hero/" + heroId).run()
        val heroJson = JsonParser().parse(response).asJsonObject
        return heroJson
    }

    fun getLeaderBoardAsJson(tag: String): JsonArray {
        val response = WannaGet(tag).run()
        return JsonParser().parse(response).asJsonArray
    }

    fun getPopularBoardAsJson(): JsonObject{
        val response = WannaGet("fetchdata").run()
        return JsonParser().parse(response).asJsonObject
    }

    fun getAboutText(): String {
        val response = WannaGet("heroapp/about").run()
        return response
    }

    fun postFeedback(context: Context, app_version: String, os_version: String, android_version: String, feedback: String, connect: String): String {
        val okHttpClient = OkHttpClient()
        val body = FormBody.Builder().add(context.resources.getString(R.string.form_app_version), app_version)
                .add(context.resources.getString(R.string.form_os_version), os_version)
                .add(context.resources.getString(R.string.form_android_version), android_version)
                .add(context.resources.getString(R.string.form_feedback), feedback)
                .add(context.resources.getString(R.string.form_connect), connect).build()
        val request = Request.Builder().url(WannaGet.htap() + "heroapp/feedback").post(body).build()
        val response = okHttpClient.newCall(request).execute()
        return response.body()!!.string()
    }

    fun loadImage(view: ImageView, url: String){
        if (RepositoryModel().isImageEnable()){
            Picasso.with(view.context).load(url).into(view)
        } else {
            Picasso.with(view.context).load(R.drawable.icon_holder).into(view)
        }
    }
}
