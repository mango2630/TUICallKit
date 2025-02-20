package com.tencent.qcloud.tuikit.tuicallkit.viewmodel.component.videolayout

import com.tencent.cloud.tuikit.engine.call.TUICallDefine
import com.tencent.cloud.tuikit.engine.common.TUICommonDefine
import com.tencent.qcloud.tuikit.tuicallkit.data.User
import com.tencent.qcloud.tuikit.tuicallkit.state.TUICallState
import com.trtc.tuikit.common.livedata.LiveData

class SingleCallVideoLayoutViewModel {
    public var selfUser: User
    public var remoteUser: User
    public var isCameraOpen = LiveData<Boolean>()
    public var isFrontCamera = LiveData<TUICommonDefine.Camera>()
    public var currentReverseRenderView = false
    public var lastReverseRenderView = false
    public var isShowFullScreen = false
    public var enableBlurBackground = LiveData<Boolean>()

    init {
        selfUser = TUICallState.instance.selfUser.get()
        val remoteUserList = TUICallState.instance.remoteUserList.get()
        remoteUser = if (remoteUserList != null && remoteUserList.size > 0) {
            remoteUserList.first()
        } else {
            User()
        }
        isCameraOpen = TUICallState.instance.isCameraOpen
        isFrontCamera = TUICallState.instance.isFrontCamera
        lastReverseRenderView = TUICallState.instance.reverse1v1CallRenderView
        enableBlurBackground = TUICallState.instance.enableBlurBackground
    }

    public fun reverseRenderLayout(reverse: Boolean) {
        currentReverseRenderView = reverse
        TUICallState.instance.reverse1v1CallRenderView = reverse
    }

    public fun showFullScreen() {
        if (TUICallState.instance.selfUser.get().callStatus.get() != TUICallDefine.Status.Accept) {
            return
        }
        isShowFullScreen = !isShowFullScreen
        TUICallState.instance.isShowFullScreen.set(isShowFullScreen)
    }
}