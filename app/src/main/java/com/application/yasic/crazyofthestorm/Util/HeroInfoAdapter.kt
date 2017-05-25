package com.application.yasic.crazyofthestorm.Util

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.application.yasic.crazyofthestorm.Model.HeroDataModel
import com.application.yasic.crazyofthestorm.Model.NetworkModel
import com.application.yasic.crazyofthestorm.Object.HeroTrait
import com.application.yasic.crazyofthestorm.R
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_hero_info.*
import kotlinx.android.synthetic.main.layout_hero_property.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread
import org.w3c.dom.Text

class HeroInfoAdapter(val items: List<JsonObject>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val HEADERTEXT = 1
        val TRAIT = 2
        val STARTINGABILITYTEXT = 3
        val STARTINGABILITY = 4
        val HORICABILITYTEXT = 5
        val HORICABILITY = 6
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            HEADERTEXT -> {
                return HeaderTextViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.header_text_item, parent, false))
            }
            TRAIT -> {
                return TraitVIewHolder(LayoutInflater.from(parent.context).inflate(R.layout.hero_trait_item, parent, false))
            }
            STARTINGABILITYTEXT -> {
                return StartingAbilityTextViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.starting_ability_text_item, parent, false))
            }
            STARTINGABILITY -> {
                return StartingAbilityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.hero_trait_item, parent, false))
            }
            HORICABILITYTEXT -> {
                return HeroicAbilityTextViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.heroic_ability_text_item, parent, false))
            }
            HORICABILITY -> {
                return HeroicAbilityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.hero_trait_item, parent, false))
            }
            else -> {
                return HeaderTextViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.heroic_ability_text_item, parent, false))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (holder) {
            is HeaderTextViewHolder -> {
                (holder as HeaderTextViewHolder).bindView(items[position])
            }
            is TraitVIewHolder -> {
                (holder as TraitVIewHolder).bindView(items[position])
            }
            is StartingAbilityViewHolder -> {
                (holder as StartingAbilityViewHolder).bindView(items[position])
            }
            is HeroicAbilityViewHolder -> {
                (holder as HeroicAbilityViewHolder).bindView(items[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].asJsonObject.get("itemViewType").asInt
    }

    class HeroicAbilityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val iconView: ImageView
        private val titleView: TextView
        private val costView: TextView
        private val shortCutView: TextView
        private val cdView: TextView
        private val descriptionView: TextView

        init {
            iconView = view.find(R.id.iv_hero_trait)
            titleView = view.find(R.id.tv_hero_trait_title)
            costView = view.find(R.id.tv_cost)
            shortCutView = view.find(R.id.tv_shortcut)
            cdView = view.find(R.id.tv_cd)
            descriptionView = view.find(R.id.tv_description)
        }

        fun bindView(heroTraitJson: JsonObject) {
            val heroId = heroTraitJson.get("id").asString.replace("\"", "")
            val abilityTitle = heroTraitJson.get("title").asString.replace("\"", "")
            com.squareup.picasso.Picasso.with(iconView.context).load(WannaGet.htap() + "heroicability/$heroId/$abilityTitle").into(iconView)
            titleView.text = abilityTitle
            costView.text = if (heroTraitJson.get("skill-cost").isJsonNull) "N/A" else heroTraitJson.get("skill-cost").asString
            shortCutView.text = if (heroTraitJson.get("skill-shortcut").isJsonNull) "N/A" else heroTraitJson.get("skill-shortcut").asString
            cdView.text = if (heroTraitJson.get("skill-cd").isJsonNull) "N/A" else heroTraitJson.get("skill-cd").asString
            descriptionView.text = if (heroTraitJson.get("description").isJsonNull) "N/A" else heroTraitJson.get("description").asString
        }
    }

    class HeroicAbilityTextViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

    class StartingAbilityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val iconView: ImageView
        private val titleView: TextView
        private val costView: TextView
        private val shortCutView: TextView
        private val cdView: TextView
        private val descriptionView: TextView

        init {
            iconView = view.find(R.id.iv_hero_trait)
            titleView = view.find(R.id.tv_hero_trait_title)
            costView = view.find(R.id.tv_cost)
            shortCutView = view.find(R.id.tv_shortcut)
            cdView = view.find(R.id.tv_cd)
            descriptionView = view.find(R.id.tv_description)
        }

        fun bindView(heroTraitJson: JsonObject) {
            val heroId = heroTraitJson.get("id").asString.replace("\"", "")
            val abilityTitle = heroTraitJson.get("title").asString.replace("\"", "")
            com.squareup.picasso.Picasso.with(iconView.context).load(WannaGet.htap() +  "startingability/$heroId/$abilityTitle").into(iconView)
            titleView.text = abilityTitle
            costView.text = if (!heroTraitJson.has("skill-cost") || heroTraitJson.get("skill-cost").isJsonNull) "N/A" else heroTraitJson.get("skill-cost").asString
            shortCutView.text = if (heroTraitJson.get("skill-shortcut").isJsonNull) "N/A" else heroTraitJson.get("skill-shortcut").asString
            cdView.text = if (heroTraitJson.get("skill-cd").isJsonNull) "N/A" else heroTraitJson.get("skill-cd").asString
            descriptionView.text = if (heroTraitJson.get("description").isJsonNull) "N/A" else heroTraitJson.get("description").asString
        }
    }

    class StartingAbilityTextViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

    class TraitVIewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val iconView: ImageView
        private val titleView: TextView
        private val costView: TextView
        private val shortCutView: TextView
        private val cdView: TextView
        private val descriptionView: TextView

        init {
            iconView = view.find(R.id.iv_hero_trait)
            titleView = view.find(R.id.tv_hero_trait_title)
            costView = view.find(R.id.tv_cost)
            shortCutView = view.find(R.id.tv_shortcut)
            cdView = view.find(R.id.tv_cd)
            descriptionView = view.find(R.id.tv_description)
        }

        fun bindView(heroTraitJson: JsonObject) {
            val heroId = heroTraitJson.get("id").asString.replace("\"", "")
            val traitTitle = heroTraitJson.get("title").asString.replace("\"", "")
            com.squareup.picasso.Picasso.with(iconView.context).load(WannaGet.htap() + "herotrait/$heroId/$traitTitle").into(iconView)
            titleView.text = traitTitle
            costView.text = if (heroTraitJson.get("cost").isJsonNull) "N/A" else heroTraitJson.get("cost").asString
            shortCutView.text = if (heroTraitJson.get("shortcut").isJsonNull) "N/A" else heroTraitJson.get("shortcut").asString
            cdView.text = if (heroTraitJson.get("cd").isJsonNull) "N/A" else heroTraitJson.get("cd").asString
            descriptionView.text = if (heroTraitJson.get("description").isJsonNull) "N/A" else heroTraitJson.get("description").asString
        }
    }

    class HeaderTextViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val heroInfoIcon: ImageView
        private val heroInfoName: TextView
        private val heroInfoRole: TextView
        private val heroInfoStory: TextView
        private val game: ImageView

        private val heroInfoLVButton: ImageButton
        private val hpTextView: TextView
        private val hpRecoveryText: TextView
        private val mpTextView: TextView
        private val mpRecoveryView: TextView
        private val hpValue: TextView
        private val hpRecoveryValue: TextView
        private val mpValue: TextView
        private val mpRecoveryValue: TextView
        private val attackValue: TextView
        private val siegeValue: TextView
        private val attackFrequence: TextView
        private val attackRange: TextView
        private val walkSpeed: TextView
        private val rideSpeed: TextView

        private val heroInfoLv: TextView

        init {
            heroInfoIcon = view.find(R.id.iv_hero_info_icon)
            heroInfoName = view.find(R.id.tv_hero_info_name)
            heroInfoRole = view.find(R.id.tv_hero_info_role)
            heroInfoStory = view.find(R.id.tv_hero_info_description)
            game = view.find(R.id.iv_game)

            heroInfoLVButton = view.find(R.id.ib_hero_info_lv)
            heroInfoLv = view.find(R.id.tv_hero_info_lv)
            hpTextView = view.find(R.id.tv_hp_text)
            hpRecoveryText = view.find(R.id.tv_hp_recovery_text)
            mpTextView = view.find(R.id.tv_mp_text)
            mpRecoveryView = view.find(R.id.tv_mp_recovery_text)
            hpValue = view.find(R.id.tv_hp_value)
            hpRecoveryValue = view.find(R.id.tv_hp_recovery_value)
            mpValue = view.find(R.id.tv_mp_value)
            mpRecoveryValue = view.find(R.id.tv_mp_recovery_value)
            attackValue = view.find(R.id.tv_attack_value)
            siegeValue = view.find(R.id.tv_siege_value)

            attackFrequence = view.find(R.id.tv_attack_per_second_value)
            attackRange = view.find(R.id.tv_attack_range_value)
            walkSpeed = view.find(R.id.tv_walk_speed_value)
            rideSpeed = view.find(R.id.tv_ride_speed_value)
        }

        fun bindView(heroJson: JsonObject) {
            doAsync {
                val list = NetworkModel().getHeroList()
                uiThread {
                    list.forEach {
                        if (it.id == HeroDataModel().getHeroId(heroJson)) {
                            when (it.game) {
                                "wow" -> {
                                    game.setBackgroundResource(R.drawable.main_view_btn_warcraft_on)
                                }
                                "sc2" -> {
                                    game.setBackgroundResource(R.drawable.main_view_btn_starcraft_on)
                                }
                                "d3" -> {
                                    game.setBackgroundResource(R.drawable.main_view_btn_diablo_on)
                                }
                                "ow" -> {
                                    game.setBackgroundResource(R.drawable.main_view_btn_overwatch_on)
                                }
                                "other" -> {
                                    game.setBackgroundResource(R.drawable.main_view_btn_retro_on)
                                }
                            }
                        }
                    }
                }
            }
            Picasso.with(heroInfoIcon.context).load(WannaGet.htap() + "heroicon/" + HeroDataModel().getHeroId(heroJson)).into(heroInfoIcon)
            heroInfoName.text = HeroDataModel().getHeroName(heroJson) + HeroDataModel().getHeroTitle(heroJson)
            heroInfoRole.text = HeroDataModel().getHeroRole(heroJson)
            heroInfoStory.text = HeroDataModel().getHeroDescription(heroJson)

            heroInfoLv.text = "LV1"
            hpTextView.text = HeroDataModel().getHPText(heroJson) + "："
            hpRecoveryText.text = HeroDataModel().getHPRecoveryText(heroJson) + "："
            mpTextView.text = HeroDataModel().getMPText(heroJson) + "："
            mpRecoveryView.text = HeroDataModel().getMPRecoveryText(heroJson) + "："

            hpValue.text = HeroDataModel().getHPValue(heroJson, 1)
            hpRecoveryValue.text = HeroDataModel().getHPRecoveryValue(heroJson, 1)
            mpValue.text = HeroDataModel().getMPValue(heroJson, 1)
            mpRecoveryValue.text = HeroDataModel().getMPRecoveryValue(heroJson, 1)
            attackValue.text = HeroDataModel().getAttackValue(heroJson, 1)
            siegeValue.text = HeroDataModel().getSiegeValue(heroJson, 1)

            attackFrequence.text = HeroDataModel().getAttackPerSecondValue(heroJson)
            attackRange.text = HeroDataModel().getAttackRange(heroJson)

            walkSpeed.text = HeroDataModel().getWalkSpeed(heroJson)
            rideSpeed.text = HeroDataModel().getRideSpeed(heroJson)

            heroInfoLVButton.setOnClickListener {
                var lv: Int = 1
                when (heroInfoLv.text) {
                    "LV1" -> {
                        lv = 10
                        heroInfoLv.text = "LV10"
                    }
                    "LV10" -> {
                        lv = 20
                        heroInfoLv.text = "LV20"
                    }
                    "LV20" -> {
                        lv = 1
                        heroInfoLv.text = "LV1"
                    }
                }
                hpValue.text = HeroDataModel().getHPValue(heroJson, lv)
                hpRecoveryValue.text = HeroDataModel().getHPRecoveryValue(heroJson, lv)
                mpValue.text = HeroDataModel().getMPValue(heroJson, lv)
                mpRecoveryValue.text = HeroDataModel().getMPRecoveryValue(heroJson, lv)
                attackValue.text = HeroDataModel().getAttackValue(heroJson, lv)
                siegeValue.text = HeroDataModel().getSiegeValue(heroJson, lv)
            }
        }

    }
}


