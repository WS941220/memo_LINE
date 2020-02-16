package com.example.memo_line.ui.main

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject


@ActivityScoped
class NewsPresenter @Inject internal constructor(
    newsRepository: NewsRepository, private val disposables: CompositeDisposable,
    tabsWrapper: ChromeTabsWrapper
) : BasePresenter<NewsContract.View?>(), NewsContract.Presenter {
    private val mNewsRepository: NewsRepository
    private val mTabsWrapper: ChromeTabsWrapper
    // inject separately ImageLoader client so that tests do not have to care about it
    @Inject
    var mPicasso: Picasso? = null

    /**
     * retrieve all unarchived news (items) from repository
     */
    fun loadNews(category: String?) { // The request might be handled in a different thread so make sure Espresso knows
// that the app is busy until the response is handled.
        notifyEspressoAppIsBusy()
        mNewsRepository.getNews(category, object : LoadNewsCallback() {
            fun onDisposableAcquired(disposable: Disposable) {
                addDisposable(disposable)
            }

            fun onNewsLoaded(news: List<News>) {
                notifyEspressoAppIsIdle()
                processDataToBeDisplayed(news)
            }

            fun onDataNotAvailable() {
                notifyEspressoAppIsIdle()
                processEmptyDataList()
            }
        })
    }

    /**
     * retrieve only archived news (items) from repository
     */
    fun loadSavedNews() {
        mNewsRepository.getArchivedNews(object : LoadSavedNewsCallback() {
            fun onNewsLoaded(news: List<News>) {
                processDataToBeDisplayed(news)
            }

            fun onDataNotAvailable() {
                processEmptyDataList()
            }
        })
    }

    /**
     * archive {@param newsItem} into repository for possible future reading
     */
    fun archiveNews(newsItem: News) {
        newsItem.setArchived(true)
        mNewsRepository.updateNews(newsItem)
        view.showSuccessfullyArchivedNews()
    }

    private fun processDataToBeDisplayed(news: List<News>) {
        if (news.isEmpty()) {
            processEmptyDataList()
        } else {
            view.getImageLoaderService(mPicasso)
            view.showNews(SortUtils.orderNewsByNewest(news))
        }
    }

    private fun processEmptyDataList() {
        if (view == null) return
        view.showNoNews()
    }

    fun showNewsDetail(newsItem: News) {
        mTabsWrapper.openCustomtab(newsItem.getUrl())
    }

    fun subscribe(view: NewsContract.View?) {
        super.subscribe(view)
        mTabsWrapper.bindCustomTabsService()
    }

    fun unsubscribe() {
        super.unsubscribe()
        mTabsWrapper.unbindCustomTabsService()
        disposables.clear()
        disposables.dispose()
    }

    private fun notifyEspressoAppIsIdle() { // let's make sure the app is still marked as busy then decrement
        if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
            EspressoIdlingResource.decrement() // Set app as idle.
        }
    }

    private fun notifyEspressoAppIsBusy() {
        EspressoIdlingResource.increment() // App is busy until further notice
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    init {
        mNewsRepository = newsRepository
        mTabsWrapper = tabsWrapper
    }
}
