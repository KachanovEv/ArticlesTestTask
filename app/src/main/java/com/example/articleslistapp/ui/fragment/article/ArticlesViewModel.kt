package com.example.articleslistapp.ui.fragment.article

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.articleslistapp.data.db.ArticlesListAppDataBase
import com.example.articleslistapp.data.db.model.ArticlesEntity
import com.example.articleslistapp.data.mapper.ArticlesContentMapper
import com.example.articleslistapp.data.model.ArticlesUiModel
import com.example.articleslistapp.data.network.ApiManager
import com.example.articleslistapp.data.network.model.Article
import com.example.articleslistapp.ui.interfaces.IArticlesNetworkData
import com.example.articleslistapp.utils.schedulersIOtoUI
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.koin.android.ext.android.inject
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class ArticlesViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService: ApiManager by application.inject()
    private val articlesListAppDataBase: ArticlesListAppDataBase by application.inject()
    private var listener: IArticlesNetworkData? = null
    private val compositeDisposable = CompositeDisposable()
    private val context = application.applicationContext
    val articlesLiveData = MutableLiveData<ArrayList<ArticlesUiModel>>()


    fun setListener(listener: IArticlesNetworkData) {
        this.listener = listener
    }

    fun getArticlesDataFromNet(category: Int, page: Int) {

        val perPage = 10
        val order = "desc"

        compositeDisposable.add(
            apiService.create().getAllArticlesList(category, page, perPage, order)
                .schedulersIOtoUI()
                .subscribe(
                    { result ->
                        articlesLiveData.value =
                            ArticlesContentMapper.setDataFromNetwork(result, category)

                        listener?.onDataChange(result, category)
                        listener?.onPageCountChange(result.pages?.count)
                        for (item in result.articles!!) {
                            downloadFileFromRetrofit(item.picture, item, result.pages!!.count)
                        }

                    },
                    { getDefaultErrorConsumer() },
                    {
                    }
                )
        )
    }

    fun insertDataToDB(articlesList: ArrayList<ArticlesEntity>) {

        compositeDisposable.add(
            Completable.fromCallable {
                articlesListAppDataBase.articlesDao().insertArticlesData(articlesList)
            }.subscribeOn(Schedulers.io())
                .subscribe({}, {})
        )
    }

    fun getArticlesDataFromDB(categoryId: Int) {
        compositeDisposable.add(
            articlesListAppDataBase.articlesDao().getAllArticlesData(categoryId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listener?.onDataFromDbChange(it, categoryId)
                    articlesLiveData.value = ArticlesContentMapper.setDataFromDB(it)
                }, { Log.d("error get data ", it.message!!) })
        )
    }

    private fun downloadFileFromRetrofit(
        picture: String?,
        articles: Article,
        pagesCount: Int?
    ) {
        compositeDisposable.add(
            apiService.create().downloadFile(picture).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe({

                    Observable.fromCallable {
                        if (it.isSuccessful) {
                            val fileName = picture?.substring(
                                picture.lastIndexOf("/") + 1
                            )
                            val directory = File(context?.filesDir!!.absolutePath)
                            if (!directory.exists()) {
                                directory.mkdirs()
                            }

                            val pathFile =
                                directory.absolutePath + "/" + fileName

                            listener?.onFileUriChange(
                                ArticlesContentMapper.setLocalDataForDB(
                                    articles,
                                    pathFile,
                                    pagesCount
                                )
                            )

                            println("file download was a success:  $pathFile")
                            saveFile(it.body()!!, pathFile)
                        }

                    }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                        }, {}
                        )

                }, {
                    print(it.message)
                })
        )

    }

    private fun saveFile(body: ResponseBody?, pathWhereYouWantToSaveFile: String): String {
        if (body == null)
            return ""
        var inputStream: InputStream? = null
        try {
            inputStream = body.byteStream()

            val fileOutputStream = FileOutputStream(pathWhereYouWantToSaveFile)
            fileOutputStream.use { output ->
                val buffer = ByteArray(4 * 1024)
                var read: Int
                while (inputStream.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
            return pathWhereYouWantToSaveFile
        } catch (e: Exception) {
            Log.e("saveFile", e.toString())
        } finally {
            inputStream?.close()
        }
        return ""
    }


    private fun getDefaultErrorConsumer(): Consumer<Throwable> {
        return Consumer { throwable ->
            handleDefaultNetError(throwable)
        }
    }

    private fun handleDefaultNetError(throwable: Throwable) {
        when (throwable) {
            is HttpException -> {
                throwable.code()
                try {
                    val jsonError = throwable.response()?.errorBody()?.string()
                    jsonError?.let {
                        val error = ApiManager().createGson().fromJson(it, Error::class.java)
                        if (!error.message?.isNotEmpty()!!) {
                            Log.e("Error Json", error.message.toString())
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            is SocketTimeoutException -> {
                throwable.printStackTrace()
            }
            is UnknownHostException -> {
                throwable.printStackTrace()
            }
        }
    }
}

