package ru.cowberryteam.msa.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialize.util.UIUtils;

import ru.cowberryteam.msa.R;
import ru.cowberryteam.msa.fragment.AllMarksFragmentPager;
import ru.cowberryteam.msa.fragment.DiaryFragmentPager;
import ru.cowberryteam.msa.fragment.NewsFragmentPager;
import ru.cowberryteam.msa.fragment.TimetableFragmentPager;

public class DrawerActivity extends AppCompatActivity {
    private static final int NEWS = 1;
    private static final int PM = 2;
    private static final int DAIRY = 3;
    private static final int ALL_MARKS = 4;
    private static final int TIMETABLE = 5;
    private static final int HELP = 6;
    private static final int SETTINGS = 7;
    private static final int ADD_ACCOUNT = 8;
    private static final int ACCOUNT_MANAGER = 9;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        // Handle Toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_dairy);

        // Create a few sample profile
        final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(R.drawable.ic_drawer_profile);
        final IProfile profile2 = new ProfileDrawerItem().withName("Max Muster").withEmail("max.mustermann@gmail.com").withIcon(R.drawable.ic_drawer_profile);

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withHeaderBackground(R.drawable.ic_header)
                .addProfiles(
                        profile,
                        profile2,
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem().withName("Add Account").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add).actionBar().paddingDp(5).colorRes(R.color.material_drawer_primary_text)).withIdentifier(ADD_ACCOUNT),
                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(ACCOUNT_MANAGER)
                )
                .withSavedInstance(savedInstanceState)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        if (profile != null) {
                            Intent intent = null;
                            if (profile.getIdentifier() == ADD_ACCOUNT) {
                                intent = new Intent(DrawerActivity.this, AuthorizationActivity.class);
                                finish();
                            } else if (profile.getIdentifier() == ACCOUNT_MANAGER) {
                                intent = new Intent(DrawerActivity.this, AccManagerActivity.class);
                            }
                            if (intent != null) {
                                DrawerActivity.this.startActivity(intent);
                            }
                        }

                        return false;
                    }
                })
                .build();

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_news).withIcon(FontAwesome.Icon.faw_newspaper_o).withSelectable(true).withIdentifier(NEWS),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_pm).withIcon(FontAwesome.Icon.faw_envelope).withSelectable(true).withIdentifier(PM),
                        new SectionDrawerItem().withName(R.string.drawer_item_section_mrko),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_diary).withIcon(FontAwesome.Icon.faw_cog).withSelectable(true).withIdentifier(DAIRY),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_allmarks).withIcon(FontAwesome.Icon.faw_calendar).withSelectable(true).withIdentifier(ALL_MARKS),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_timetable).withIcon(FontAwesome.Icon.faw_times).withSelectable(true).withIdentifier(TIMETABLE),
                        new SectionDrawerItem().withName(R.string.drawer_item_settings),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_question).withSelectable(true).withIdentifier(HELP),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog).withSelectable(false).withIdentifier(SETTINGS)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Fragment mFragment = null;
                            FragmentManager mFragmentManager = getSupportFragmentManager();
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == NEWS) {
                                getSupportActionBar().setTitle(R.string.title_fragment_news);
                                mFragment = new NewsFragmentPager();
                            } else if (drawerItem.getIdentifier() == PM) {

                            } else if (drawerItem.getIdentifier() == DAIRY) {
                                getSupportActionBar().setTitle(R.string.title_fragment_diary);
                                mFragment = new DiaryFragmentPager();
                            } else if (drawerItem.getIdentifier() == ALL_MARKS) {
                                getSupportActionBar().setTitle(R.string.title_fragment_all_marks);
                                mFragment = new AllMarksFragmentPager();

                            } else if (drawerItem.getIdentifier() == TIMETABLE) {
                                getSupportActionBar().setTitle(R.string.title_fragment_timetable);
                                mFragment = new TimetableFragmentPager();
                            } else if (drawerItem.getIdentifier() == HELP) {

                            } else if (drawerItem.getIdentifier() == SETTINGS) {
                                intent = new Intent(DrawerActivity.this, SettingsActivity.class);
                            }

                            if (mFragment != null){
                                mFragmentManager.beginTransaction().replace(R.id.fragment_container, mFragment).commit();
                            }
                            else if (intent != null) {
                                DrawerActivity.this.startActivity(intent);
                            }
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        // set the selection to the item with the identifier 3 (Dairy)
        result.setSelection(DAIRY, false); //dafault page
        Fragment mFragment = null;
        FragmentManager mFragmentManager = getSupportFragmentManager();
        mFragment = new DiaryFragmentPager();
        mFragmentManager.beginTransaction().replace(R.id.fragment_container, mFragment).commit();
    }

    private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.diary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle the click on the back arrow click
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    class ActionBarCallBack implements ActionMode.Callback {

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {  //Это нам
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(UIUtils.getThemeColorFromAttrOrRes(DrawerActivity.this, R.attr.colorPrimaryDark, R.color.material_drawer_primary_dark));
            }

            //mode.getMenuInflater().inflate(R.menu.cab, menu);
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
    }
}
