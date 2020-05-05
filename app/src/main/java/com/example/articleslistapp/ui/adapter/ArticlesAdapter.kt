package com.example.articleslistapp.ui.adapter


import android.graphics.drawable.Drawable
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.articleslistapp.R
import com.example.articleslistapp.data.model.ArticlesUiModel
import com.example.articleslistapp.ui.fragment.article.ArticlesFragmentDirections
import kotlinx.android.synthetic.main.item_articles.view.*
import java.util.*
import kotlin.collections.ArrayList


class ArticlesAdapter :
    RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder>() {

    private val articlesList = ArrayList<ArticlesUiModel>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.item_articles, parent, false)
        return ArticlesViewHolder(root)
    }

    override fun getItemCount(): Int {
        return articlesList.size
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        holder.bind(articlesList[position])
    }

    inner class ArticlesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(article: ArticlesUiModel) {

            val widthImage = article.pictureWidth
            val heightImage = article.pictureHeight

            Glide.with(itemView.context)
                .asDrawable()
                .load(article.pictureUrl)
                .override(heightImage, widthImage)
                .into(object : CustomTarget<Drawable?>() {
                    override fun onLoadCleared(placeholder: Drawable?) {
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable?>?
                    ) {
                        itemView.iconIv.background = resource
                    }
                })

            itemView.titleTv.text = article.title
            val sdf = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            val date = sdf.format(article.dateCreated)

            itemView.dateTv.text = date.toString()

            itemView.setOnClickListener {
                val action =
                    ArticlesFragmentDirections.actionNavigationArticlesToNavigationDetailsArticles()
                        .setContent(article.content).setTitle(article.title)
                itemView.findNavController().navigate(action)
            }
        }
    }

    fun setArticlesData(
        articlesListNetMode: ArrayList<ArticlesUiModel>,
        category: Int
    ) {
        articlesList.clear()
        for (item in articlesListNetMode) {
            if (item.category == category) {
                articlesList.add(item)
            }
        }
        notifyDataSetChanged()
    }

}