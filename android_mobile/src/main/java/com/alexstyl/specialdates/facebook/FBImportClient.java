package com.alexstyl.specialdates.facebook;

import android.graphics.Bitmap;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

class FBImportClient extends WebViewClient {
    private static final String HTTPS = "https://";
    private static final String HTTP = "http://";
    private static final String MOBILE_HOME = "m.facebook.com/home.php";
    private static final String DESKTOP_HOME = "www.facebook.com/home.php";

    private final WebView webView;
    private FacebookCallback listener;

    FBImportClient(WebView webview) {
        this.webView = webview;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (isHomePage(url)) {
            view.stopLoading();
            CookieManager.getInstance().setAcceptCookie(true);
            internalOnUserLoggedIn();
        }
    }

    private boolean isHomePage(String url) {
        return url.startsWith(HTTP + MOBILE_HOME) || url.startsWith(HTTPS + MOBILE_HOME) ||
                url.startsWith(HTTP + DESKTOP_HOME) || url.startsWith(HTTPS + DESKTOP_HOME);
    }

    private void internalOnUserLoggedIn() {
        switchToDesktopBrowsing();
        webView.loadUrl("http://www.facebook.com/events/birthdays");
        listener.onSignedInThroughWebView();
    }

    private void switchToDesktopBrowsing() {
        WebSettings settings = webView.getSettings();
        String newUA = "Mozilla/5.0 (X11; U; Linux i686; en   US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
        settings.setUserAgentString(newUA);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (url.contains("facebook.com/events/birthdays")) {
            webView.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
        }
    }

    public void setListener(FacebookCallback listener) {
        this.listener = listener;
    }
}
