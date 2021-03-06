package com.droidconsf.architectureagnosticuidevelopment

import android.app.Application
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import com.droidconsf.architectureagnosticuidevelopment.ui.common.di.AppComponent
import com.droidconsf.architectureagnosticuidevelopment.ui.common.di.AppModule
import com.droidconsf.architectureagnosticuidevelopment.ui.common.di.DaggerAppComponent
import com.droidconsf.architectureagnosticuidevelopment.core.di.NetModule


class ArchitectureAgnosticUiApplication: Application() {
    var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .netModule(NetModule())
            .build()
        SoLoader.init(this, false)
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            client.addPlugin(NetworkFlipperPlugin())
            client.start()
        }
    }
}