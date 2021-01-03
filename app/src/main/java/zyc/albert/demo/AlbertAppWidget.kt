package zyc.albert.demo

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetManager.*
import android.appwidget.AppWidgetProvider
import android.appwidget.AppWidgetProviderInfo
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import zyc.albert.demo.R.layout.ablert_app_widget
import java.util.*


/**
 * Implementation of App Widget functionality.
 */
const val TAG = "ZycAlbert"
const val RANDOM_MAX_POSSIBILITY = 10
const val RANDOM_MIN_POSSIBILITY = 0

class AlbertAppWidget : AppWidgetProvider() {
    val CLICK_GRASS = "zyc.albert.action.CLICK_GRASS"
    private lateinit var views: RemoteViews

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {

        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)

            Log.i(TAG, "onUpdate -> $appWidgetId")
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        Log.i(TAG, "onEnabled")
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
        Log.i(TAG, "onDisabled")
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        Log.i(TAG, "onDeleted -> $appWidgetIds")
    }

    override fun onAppWidgetOptionsChanged(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        newOptions: Bundle
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        val appWidgetProvider: AppWidgetProviderInfo =
            appWidgetManager.getAppWidgetInfo(appWidgetId)

        Log.i(TAG, "OPTION_APPWIDGET_MIN_WIDTH -> ${newOptions.get(OPTION_APPWIDGET_MIN_WIDTH)}")
        Log.i(TAG, "OPTION_APPWIDGET_MIN_HEIGHT  -> ${newOptions.get(OPTION_APPWIDGET_MIN_HEIGHT)}")
        Log.i(TAG, "OPTION_APPWIDGET_MAX_WIDTH  -> ${newOptions.get(OPTION_APPWIDGET_MAX_WIDTH)}")
        Log.i(TAG, "OPTION_APPWIDGET_MAX_HEIGHT  -> ${newOptions.get(OPTION_APPWIDGET_MAX_HEIGHT)}")
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        Log.i(TAG, "action = ${intent.action}")
//        val intentT = Intent(context, MainActivity::class.java)
//        intentT.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        context.startActivity(intentT)
        var mMsg = ""
        when (intent.action) {
            CLICK_GRASS -> {
                when ((RANDOM_MIN_POSSIBILITY..RANDOM_MAX_POSSIBILITY).random()) {
                    0 -> mMsg = "发生了什么？"
                    1 -> mMsg = "我刚刚睡着了？"
                    2 -> {
                        mMsg = if (Calendar.getInstance().get(Calendar.AM_PM) == 0) "上午？" else "下午?"

                    }
                    3 -> mMsg = "我好困啊，别点我了？"
                    4 -> mMsg = "这是什么时候！？"
                    5 -> mMsg = "我的上帝啊？"
                    6 -> mMsg = "这不好，不是吗？"
                    7 -> mMsg = "悲伤的故事"
                    8 -> mMsg = "我可能遇到了一下不好的事情？"
                    9 -> mMsg = "心情沉重？"
                    10 -> mMsg = "怎么了？"
                }
            }

        }
        views = RemoteViews(context.packageName, ablert_app_widget)
        views.setTextViewText(R.id.tv_message,mMsg)
        setOnClickListener(context, R.id.iv_grass, CLICK_GRASS)
        val appWidgetManager = getInstance(context)
        val componentName = ComponentName(context, AlbertAppWidget::class.java)
        appWidgetManager.updateAppWidget(componentName, views)

    }

    override fun onRestored(context: Context?, oldWidgetIds: IntArray?, newWidgetIds: IntArray?) {
        super.onRestored(context, oldWidgetIds, newWidgetIds)
        Log.i(TAG, "onRestored")
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        views = RemoteViews(context.packageName, ablert_app_widget)
        setOnClickListener(context, R.id.iv_grass, CLICK_GRASS)
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
        Log.i(TAG, "updateAppWidget context->$context - appWidgetId->$appWidgetId")
    }

    private fun setOnClickListener(context: Context, mId: Int, mAction: String) {
        views.setOnClickPendingIntent(
            mId,
            PendingIntent.getBroadcast(context, 0, Intent().apply {
                setClass(context, AlbertAppWidget::class.java)
                action = mAction
            }, 0)
        )
    }
}

