package com.example.articleslistapp.ui.fragment.article

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.articleslistapp.R
import com.example.articleslistapp.data.db.model.ArticlesEntity
import com.example.articleslistapp.data.mapper.ArticlesContentMapper
import com.example.articleslistapp.data.network.model.ArticlesListNetModel
import com.example.articleslistapp.data.network.model.CategoriesEnum
import com.example.articleslistapp.ui.adapter.ArticlesAdapter
import com.example.articleslistapp.ui.interfaces.IArticlesNetworkData
import kotlinx.android.synthetic.main.fragment_articles.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ArticlesFragment : Fragment(), IArticlesNetworkData {

    private val adapterArticles = ArticlesAdapter()
    private val articlesViewModel: ArticlesViewModel by viewModel()
    private var pageNumber = 0
    private var pagesCount = 0
    private var isNeedToShowItemFromLocalStorage = true
    private val perPageLimitItems = 10

    companion object {
        private const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // set listener
        articlesViewModel.setListener(this)
        return inflater.inflate(R.layout.fragment_articles, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // save instance if change rotation of screen
        articlesViewModel.articlesLiveData.observe(viewLifecycleOwner, Observer { result ->
            for (item in result) {
                val categoryId = item.category
                // save items in adapter
                adapterArticles.setArticlesData(
                    result,
                    categoryId
                )

            }
            // save button color
            when (result[result.size - 1].category) {
                CategoriesEnum.SchrangTV.id -> {
                    sharingTvButton.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.scharang_tv
                        )
                    )
                }
                CategoriesEnum.Talk.id -> {
                    talkButton.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.talk
                        )
                    )
                }
                CategoriesEnum.Spirit.id -> {
                    spiritButton.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.spirit
                        )
                    )
                }
            }
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        changeDataForCategories()
        askPermissions()
    }

    private fun initAdapter() {
        rvArticles.adapter = adapterArticles
        rvArticles.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvArticles.layoutManager = layoutManager
    }

    private fun setRecyclerViewScrollListener(categoryId: Int) {

        rvArticles.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val totalItemCount = recyclerView.layoutManager!!.itemCount
                val ll = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPos = ll.findLastVisibleItemPosition()

                if (firstVisibleItemPos == (totalItemCount - 1)) {

                    // set data from network or local storage to adapter
                    if (isOnline(requireContext())) {
                        articlesViewModel.getArticlesDataFromNet(categoryId, pageNumber)
                    } else {
                        if (isNeedToShowItemFromLocalStorage) {
                            articlesViewModel.getArticlesDataFromDB(categoryId)
                        }
                    }

                    // control count of page for net query
                    if (pageNumber <= (pagesCount - 1)) {
                        pageNumber += 1
                    }

                }

            }
        })
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    private fun changeDataForCategories() {

        sharingTvButton.setOnClickListener {
            // init first page
            pageNumber = 0
            // check internet
            if (isOnline(requireContext())) {
                articlesViewModel.getArticlesDataFromNet(CategoriesEnum.SchrangTV.id, pageNumber)
            } else {
                articlesViewModel.getArticlesDataFromDB(CategoriesEnum.SchrangTV.id)
            }

            sharingTvButton.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.scharang_tv
                )
            )
            talkButton.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.button_background
                )
            )
            spiritButton.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.button_background
                )
            )
            setRecyclerViewScrollListener(CategoriesEnum.SchrangTV.id)
        }

        talkButton.setOnClickListener {
            // init first page
            pageNumber = 0
            // check internet
            if (isOnline(requireContext())) {
                articlesViewModel.getArticlesDataFromNet(CategoriesEnum.Talk.id, pageNumber)
            } else {
                articlesViewModel.getArticlesDataFromDB(CategoriesEnum.Talk.id)
            }

            talkButton.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.talk
                )
            )
            sharingTvButton.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.button_background
                )
            )
            spiritButton.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.button_background
                )
            )
            setRecyclerViewScrollListener(CategoriesEnum.Talk.id)
        }

        spiritButton.setOnClickListener {
            // init first page
            pageNumber = 0
            // check internet
            if (isOnline(requireContext())) {
                articlesViewModel.getArticlesDataFromNet(CategoriesEnum.Spirit.id, pageNumber)
            } else {
                articlesViewModel.getArticlesDataFromDB(CategoriesEnum.Spirit.id)
            }

            spiritButton.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.spirit
                )
            )
            talkButton.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.button_background
                )
            )
            sharingTvButton.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.button_background
                )
            )
            setRecyclerViewScrollListener(CategoriesEnum.Spirit.id)
        }

    }

    override fun onDataChange(
        articlesList: ArticlesListNetModel,
        category: Int
    ) {

        adapterArticles.setArticlesData(
            ArticlesContentMapper.setDataFromNetwork(
                articlesList,
                category
            ),
            category
        )

    }

    private fun askPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {

                AlertDialog.Builder(requireContext())
                    .setTitle("Permission required")
                    .setMessage("Permission required to save photos from the Web.")
                    .setPositiveButton("Allow") { _, _ ->
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                        )
                    }
                    .setNegativeButton("Deny") { dialog, _ -> dialog.cancel() }
                    .show()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                )
            }
        }
    }

    override fun onPageCountChange(count: Int?) {
        if (isOnline(requireContext())) {
            pagesCount = count!!
        }
    }

    override fun onFileUriChange(
        articlesList: ArrayList<ArticlesEntity>
    ) {
        articlesViewModel.insertDataToDB(
            articlesList
        )

    }

    override fun onDataFromDbChange(
        articlesEntityList: List<ArticlesEntity>,
        categoryId: Int
    ) {

        adapterArticles.setArticlesData(
            ArticlesContentMapper.setDataFromDB(articlesEntityList),
            categoryId
        )
        Log.d("localSizeList", articlesEntityList.size.toString())
        if (articlesEntityList.size <= perPageLimitItems) {
            isNeedToShowItemFromLocalStorage = false
        }
        // set pages count from local DataBase
        pagesCount = ArticlesContentMapper.getPagesCount(articlesEntityList, categoryId)

    }

}
