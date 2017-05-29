package com.application.yasic.crazyofthestorm.Util

import android.media.Image
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import com.application.yasic.crazyofthestorm.Model.ApplicationModel
import com.application.yasic.crazyofthestorm.Model.RepositoryModel
import com.application.yasic.crazyofthestorm.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.more_list_item.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

class MoreListAdapter(val items: Array<String>, val itemClick: OnItemClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder -> {
                holder.bindView(items[position])
            }
            is SwitchViewHolder -> {
                holder.bindView(items[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            0 -> {
                return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.more_list_item, parent, false), itemClick)
            }
            1 -> {
                return SwitchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.image_enable_switch_item, parent, false))
            }
            else -> {
                return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.more_list_item, parent, false), itemClick)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        when(items[position]){
            ApplicationModel.instance().resources.getString(R.string.image_enable_switch) -> {
                return 1
            }
            else -> {return 0}
        }
    }

    class SwitchViewHolder(val view: View): RecyclerView.ViewHolder(view){
        private val imageEnabled: Boolean
        private val titleView: TextView
        private val imageEnableSwitch: Switch
        private val icon: ImageView

        init {
            titleView = view.find(R.id.tv_more_list_title)
            imageEnabled = RepositoryModel().isImageEnable()
            imageEnableSwitch = view.find(R.id.switch_image_enable)
            icon = view.find(R.id.iv_icon)
        }

        fun bindView(item: String){
            titleView.text = item
            icon.setBackgroundResource(R.drawable.ic_image_switch_24dp)
            imageEnableSwitch.isChecked = imageEnabled
            imageEnableSwitch.setOnCheckedChangeListener { compoundButton, b ->
                if(imageEnableSwitch.isChecked){
                    ApplicationModel.instance().toast("图片加载开关已开启")
                }else{
                    ApplicationModel.instance().toast("图片加载开关已关闭")
                }
                RepositoryModel().setImageEnable(imageEnableSwitch.isChecked)
            }
        }
    }

    class ViewHolder(val view: View, val itemClick: OnItemClickListener): RecyclerView.ViewHolder(view){
        private val titleView: TextView
        private val liItemView: LinearLayout
        private val icon: ImageView

        init {
            titleView = view.find(R.id.tv_more_list_title)
            liItemView = view.find(R.id.li_more_list_item)
            icon = view.find(R.id.iv_icon)
        }

        fun bindView(item: String){
            titleView.text = item
            when(item){
                ApplicationModel.instance().resources.getString(R.string.feedback_activity_title) -> {
                    icon.setBackgroundResource(R.drawable.ic_feedback_24dp)
                }
                ApplicationModel.instance().resources.getString(R.string.about_activity_title) -> {
                    icon.setBackgroundResource(R.drawable.ic_about_24dp)
                }
            }
            liItemView.setOnClickListener { itemClick(item) }
        }
    }

    interface OnItemClickListener{
        operator fun invoke(title: String){

        }
    }
}
