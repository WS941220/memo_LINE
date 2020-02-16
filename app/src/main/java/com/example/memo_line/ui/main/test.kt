//import java.util.*
//
//mTasksRepository.getTasks(object : LoadTasksCallback() {
//    fun onTasksLoaded(tasks: List<Task>) {
//        val tasksToShow: MutableList<Task> = ArrayList<Task>()
//        // This callback may be called twice, once for the cache and once for loading
//// the data from the server API, so we check before decrementing, otherwise
//// it throws "Counter has been corrupted!" exception.
//        if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
//            EspressoIdlingResource.decrement() // Set app as idle.
//        }
//        // We filter the tasks based on the requestType
//        for (task in tasks) {
//            when (mCurrentFiltering) {
//                ALL_TASKS -> tasksToShow.add(task)
//                ACTIVE_TASKS -> if (task.isActive()) {
//                    tasksToShow.add(task)
//                }
//                COMPLETED_TASKS -> if (task.isCompleted()) {
//                    tasksToShow.add(task)
//                }
//                else -> tasksToShow.add(task)
//            }
//        }
//        // The view may not be able to handle UI updates anymore
//        if (mTasksView == null || !mTasksView.isActive()) {
//            return
//        }
//        if (showLoadingUI) {
//            mTasksView.setLoadingIndicator(false)
//        }
//        processTasks(tasksToShow)
//    }
//
//    fun onDataNotAvailable() { // The view may not be able to handle UI updates anymore
//        if (!mTasksView.isActive()) {
//            return
//        }
//        mTasksView.showLoadingTasksError()
//    }
//})