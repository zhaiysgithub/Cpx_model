package com.base.muslim.share

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import com.base.muslim.login.common.constants.LoginConstants.Companion.FACEBOOK
import com.base.muslim.login.common.constants.LoginConstants.Companion.TWITTER
import com.base.muslim.share.common.listener.OnShareListener
import com.base.muslim.share.facebook.FacebookShareManager
import com.base.muslim.share.twitter.TwitterShareManager


/**
 * Description:
 * 分享管理类
 *
 * 1.Facebook分享：支持facebook分享文字、链接、图片、视频、多媒体
 *  facebook分享链接时，通过tag添加文字，通过quote添加引文
 *  facebook只支持分享本地视频
 *  facebook分享多媒体时，图片+视频总数最多6个
 *  分享图片、视频、多媒体时，可以通过 tag 添加文字
 *
 * 2.Twitter分享：支持Twitter分享文字、图片
 *  分享图片时，可以通过 tag 添加文字
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
class ShareManager(private val activity: Activity, private val onShareListener: OnShareListener) {
    private val facebookShareManager by lazy { FacebookShareManager(activity, onShareListener) }
    private val twitterShareManager by lazy { TwitterShareManager(activity, onShareListener) }

    fun shareText(type: String, text: String) {
        when (type) {
            FACEBOOK -> facebookShareManager.shareText(text)
            TWITTER -> twitterShareManager.shareText(text)
        }
    }

    fun shareLink(type: String, link: String, tag: String, quote: String) {
        when (type) {
            FACEBOOK -> facebookShareManager.shareLink(link, tag, quote)
            TWITTER -> onShareListener.onShareFail(TWITTER, "Twitter share does not support link yet")
        }
    }

    fun shareImage(type: String, image: Bitmap, tag: String = "") {
        when (type) {
            FACEBOOK -> facebookShareManager.shareImage(image, tag)
            TWITTER -> twitterShareManager.shareImage(image, tag)
        }
    }

    fun shareImage(type: String, image: Uri, tag: String = "") {
        when (type) {
            FACEBOOK -> facebookShareManager.shareImage(image, tag)
            TWITTER -> twitterShareManager.shareImage(image, tag)
        }
    }

    fun shareVideo(type: String, videoUri: Uri, tag: String = "") {
        when (type) {
            FACEBOOK -> facebookShareManager.shareVideo(videoUri, tag)
            TWITTER -> onShareListener.onShareFail(TWITTER, "Twitter share does not support video yet")
        }
    }

    fun shareMedia(type: String, imageList: List<Bitmap>, videoUriList: List<Uri>, tag: String = "") {
        when (type) {
            FACEBOOK -> facebookShareManager.shareMedia(imageList, videoUriList, tag)
            TWITTER -> onShareListener.onShareFail(TWITTER, "Twitter share does not support media yet")
        }
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        facebookShareManager.handleActivityResult(requestCode, resultCode, data)
    }

    fun release() {
        twitterShareManager.release()
    }
}