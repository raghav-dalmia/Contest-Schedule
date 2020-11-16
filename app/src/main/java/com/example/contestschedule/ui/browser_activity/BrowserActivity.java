package com.example.contestschedule.ui.browser_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.contestschedule.R;
import com.example.contestschedule.ui.base.BasicActivity;
import com.example.contestschedule.ui.main_activity.MainActivity;

import java.net.URI;

public class BrowserActivity extends BasicActivity {
    
    private WebView webView;
    
    private static final String TAG = "BrowserActivity";
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_browser );
        
        initActionBar();
        initWebview();
        refreshActivity();
    }
    
    private void initActionBar(){
        setSupportActionBar( findViewById( R.id.browserToolbar ) );
        getSupportActionBar().setHomeAsUpIndicator( R.drawable.ic_close );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
    }
    
    private void initWebview()
    {
        webView = findViewById( R.id.webView );
        webView.setWebViewClient( new WebViewClient(){
            @Override
            public void onPageStarted( WebView view, String url, Bitmap favicon ) {
                showProgressDialog("loading...");
                super.onPageStarted( view, url, favicon );
            }
        
            @Override
            public void onPageFinished( WebView view, String url ) {
                hideProgressDialog();
                super.onPageFinished( view, url );
            }
        } );
        webView.getSettings().setJavaScriptEnabled( true );
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setAppCacheEnabled( true );
        webView.setInitialScale(1);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
    
        String url = getIntent().getStringExtra( "URL" );
        webView.loadUrl( url );
    }
    
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.browser_menu, menu );
        return true;
    }
    
    
    
    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {
        String url = webView.getUrl();
        
        switch ( item.getItemId() ){
            case android.R.id.home:
                webView.stopLoading();
                hideProgressDialog();
                finish( );
                break;
            case R.id.browser_copyLink:
                if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(url);
                } else {
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    android.content.ClipData clip = android.content.ClipData.newPlainText("text label", url);
                    clipboard.setPrimaryClip(clip);
                }
                showMessage( "Copied" );
                break;
            case R.id.browser_shareLink:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT,url);
                startActivity(Intent.createChooser(shareIntent, "Share..."));
                break;
            default:
                break;
        }
        
        return super.onOptionsItemSelected( item );
    }
    
    @Override
    public void refreshActivity( ) {
        refreshLayout = findViewById( R.id.swipeRefreshBrowser );
        refreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener( ) {
            @Override
            public void onRefresh( ) {
                refreshLayout.setRefreshing( false );
                webView.reload();
            }
        } );
    }
    
    @Override
    public void onBackPressed( ) {
        webView.stopLoading();
        hideProgressDialog();
        if(webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed( );
    }
    
}