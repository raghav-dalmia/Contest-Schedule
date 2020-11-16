package com.example.contestschedule.ui.setting_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.contestschedule.R;
import com.example.contestschedule.ui.base.BasicActivity;
import com.example.contestschedule.ui.main_activity.MainActivity;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;

public class SettingActivity extends BasicActivity {
    
    CheckBox showCodeforcesCheckbox, showAtcoderCheckbox, showCodechefCheckbox, show24hrFormatCheckbox;
    Slider shortContestDurationSlider;
    AutoCompleteTextView timeZomeDropdownMenu;
    TextView sliderLabel;
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_setting );
        initActionBar();
        
        showCodeforcesCheckbox = findViewById( R.id.setting_showCodeforces );
        showCodechefCheckbox = findViewById( R.id.setting_showCodechef );
        showAtcoderCheckbox = findViewById( R.id.setting_showAtcoder );
        show24hrFormatCheckbox = findViewById( R.id.setting_24hrTimeFormat );
        shortContestDurationSlider = findViewById( R.id.setting_shortContestDuration );
        timeZomeDropdownMenu = findViewById( R.id.setting_timeZone );
        sliderLabel = findViewById( R.id.setting_label );
        
        shortContestDurationSlider.addOnChangeListener( new Slider.OnChangeListener( ) {
            @Override
            public void onValueChange( @NonNull Slider slider, float value, boolean fromUser ) {
                sliderLabel.setText( String.valueOf( (int)value ) + " minutes");
            }
        } );
        
        refreshActivity();
    }
    
    void initDropdown(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, R.layout.dropdown_menu_item, timeZones );
        timeZomeDropdownMenu.setAdapter( adapter );
    }
    
    @Override
    public void refreshActivity( ) {
        showCodeforcesCheckbox.setChecked( dao.getCodeforcesStatus() );
        showCodechefCheckbox.setChecked( dao.getCodechefStatus() );
        showAtcoderCheckbox.setChecked( dao.getAtcoderStatus() );
        show24hrFormatCheckbox.setChecked( dao.getTimeFormat() );
        shortContestDurationSlider.setValue( dao.getShortContestDuration() );
        timeZomeDropdownMenu.setText( dao.getTimeZone() );
        sliderLabel.setText( String.valueOf( (int)shortContestDurationSlider.getValue() )+ " minutes");
        initDropdown();
    }
    
    private void initActionBar(){
        setSupportActionBar( findViewById( R.id.settingToolbar ) );
        getSupportActionBar().setHomeAsUpIndicator( R.drawable.ic_back );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
    }
    
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.setting_menu, menu );
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {
        switch ( item.getItemId() ){
            case android.R.id.home:
                finish();
                break;
            case R.id.setting_update:
                updateSettings();
                startMainAtcivity();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected( item );
    }
    
    void updateSettings(){
        dao.setCodeforcesStatus( showCodeforcesCheckbox.isChecked() );
        dao.setCodechefStatus( showCodechefCheckbox.isChecked() );
        dao.setAtcoderStatus( showAtcoderCheckbox.isChecked() );
        dao.setTimeFormat( show24hrFormatCheckbox.isChecked() );
        dao.setShortContestDuration( shortContestDurationSlider.getValue() );
        dao.setTimeZone( timeZomeDropdownMenu.getText().toString() );
        showMessage( "Settings updated" );
    }
    
    public void setDefaultValues( View view ){
        dao.setDefault();
        refreshActivity();
        showMessage( "Setting reseted" );
    }
    
    public void startMainAtcivity(){
        Intent intent = new Intent( this, MainActivity.class );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        shortContestList.clear();
        longContestList.clear();
        startActivity( intent );
        finish();
    }
    
}