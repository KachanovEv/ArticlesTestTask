package com.example.articleslistapp.data.network.model

import com.google.gson.annotations.SerializedName

data class ArticlesListNetModel(
    @field:SerializedName("articles")
    var articles: List<Article>? = null,

    @field:SerializedName("pages")
    var pages: Pages? = null

)

data class Article(
    @field:SerializedName("id")
    var id: String? = null,

    @field:SerializedName("status")
    var status: String? = null,

    @field:SerializedName("date_created")
    var dateCreated: String? = null,

    @field:SerializedName("date_custom")
    var dateCustom: String? = null,

    @field:SerializedName("title")
    var title: String? = null,

    @field:SerializedName("second_title")
    var secondTitle: Any? = null,

    @field:SerializedName("content")
    var content: String? = null,

    @field:SerializedName("teaser")
    var teaser: String? = null,

    @field:SerializedName("slug")
    var slug: String? = null,

    @field:SerializedName("category")
    var category: Category? = null,

    @field:SerializedName("source")
    var source: Any? = null,

    @field:SerializedName("recommended")
    var recommended: String? = null,

    @field:SerializedName("tags")
    var tags: List<Tag>? = null,

    @field:SerializedName("picture")
    var picture: String? = null,

    @field:SerializedName("picture_mime")
    var pictureMime: String? = null,

    @field:SerializedName("picture_width")
    var pictureWidth: String? = null,

    @field:SerializedName("picture_height")
    var pictureHeight: String? = null,

    @field:SerializedName("picture_collection")
    var pictureCollection: String? = null,

    @field:SerializedName("video_duration")
    var videoDuration: String? = null,

    @field:SerializedName("video_youtube")
    var videoYoutube: String? = null,

    @field:SerializedName("video_vimeo")
    var videoVimeo: Any? = null,

    @field:SerializedName("video_bitchute")
    var videoBitchute: Any? = null,

    @field:SerializedName("video_other")
    var videoOther: Any? = null,

    @field:SerializedName("podcast")
    var podcast: Any? = null,

    @field:SerializedName("podcast_duration")
    var podcastDuration: String? = null,

    @field:SerializedName("podcast_mime")
    var podcastMime: Any? = null,

    @field:SerializedName("podcast_link")
    var podcastLink: Any? = null,

    @field:SerializedName("hit_counter")
    var hitCounter: String? = null,

    @field:SerializedName("hit_rating")
    var hitRating: String? = null
)

data class Category(
    @field:SerializedName("id")
    var id: String? = null,

    @field:SerializedName("title")
    var title: String? = null,

    @field:SerializedName("picture")
    var picture: String? = null
)


data class Pages(
    @field:SerializedName("articles")
    var articles: Int? = null,

    @field:SerializedName("count")
    var count: Int? = null,

    @field:SerializedName("first")
    var first: Int? = null,

    @field:SerializedName("last")
    var last: Int? = null
)

data class Tag(
    @field:SerializedName("tag")
    var tag: String? = null,

    @field:SerializedName("slug")
    var slug: String? = null
)

enum class CategoriesEnum(var id: Int) {
    SchrangTV(1),
    Talk(2),
    Spirit(3)
}