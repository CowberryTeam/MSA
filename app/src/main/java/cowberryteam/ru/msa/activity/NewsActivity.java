package cowberryteam.ru.msa.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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

import java.util.ArrayList;
import java.util.List;

import cowberryteam.ru.msa.fragment.NewsOneFragment;
import cowberryteam.ru.msa.fragment.NewsTwoFragment;
import cowberryteam.ru.msa.R;

public class NewsActivity extends AppCompatActivity {
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
    private TabLayout tabLayout = null;
    private ViewPager viewPaper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Handle Toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_news);

        viewPaper = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPaper);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPaper);

        // Create a few sample profile
        final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(R.drawable.ic_drawer_profile);
        final IProfile profile2 = new ProfileDrawerItem().withName("Max Muster").withEmail("max.mustermann@gmail.com").withIcon(R.drawable.ic_drawer_profile);
        /*final IProfile profile3 = new ProfileDrawerItem().withName("Felix House").withEmail("felix.house@gmail.com").withIcon(R.drawable.profile3);
        final IProfile profile4 = new ProfileDrawerItem().withName("Mr. X").withEmail("mister.x.super@gmail.com").withIcon(R.drawable.profile4);
        final IProfile profile5 = new ProfileDrawerItem().withName("Batman").withEmail("batman@gmail.com").withIcon(R.drawable.profile5);*/

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
                        if (profile!= null) {
                            Intent intent = null;
                            if (profile.getIdentifier() == ADD_ACCOUNT) {
                                intent = new Intent(NewsActivity.this, AuthorizationActivity.class);
                            }
                            else if (profile.getIdentifier() == ACCOUNT_MANAGER) {
                                intent = new Intent(NewsActivity.this, AccManagerActivity.class);
                            }
                            if (intent != null) {
                                NewsActivity.this.startActivity(intent);
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
                        new PrimaryDrawerItem().withName(R.string.drawer_item_news).withIcon(FontAwesome.Icon.faw_newspaper_o).withSelectable(false).withIdentifier(NEWS),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_pm).withIcon(FontAwesome.Icon.faw_envelope).withSelectable(false).withIdentifier(PM),
                        new SectionDrawerItem().withName(R.string.drawer_item_section_mrko),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_dairy).withIcon(FontAwesome.Icon.faw_cog).withSelectable(false).withIdentifier(DAIRY),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_allmarks).withIcon(FontAwesome.Icon.faw_calendar).withSelectable(false).withIdentifier(ALL_MARKS),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_timetable).withIcon(FontAwesome.Icon.faw_github).withSelectable(false).withIdentifier(TIMETABLE),
                        new SectionDrawerItem().withName(R.string.drawer_item_settings),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_question).withSelectable(false).withIdentifier(HELP),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog).withSelectable(false).withIdentifier(SETTINGS)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == NEWS) {

                            } else if (drawerItem.getIdentifier() == PM) {

                            } else if (drawerItem.getIdentifier() == DAIRY) {
                                result.closeDrawer();
                                intent = new Intent(NewsActivity.this, DiaryActivity.class);
                                finish();
                            } else if (drawerItem.getIdentifier() == ALL_MARKS) {
                                result.closeDrawer();
                                intent = new Intent(NewsActivity.this, AllMarksActivity.class);
                                finish();

                            } else if (drawerItem.getIdentifier() == TIMETABLE) {
                                result.closeDrawer();
                                intent = new Intent(NewsActivity.this, TimetableActivity.class);
                                finish();
                            } else if (drawerItem.getIdentifier() == HELP) {

                            } else if (drawerItem.getIdentifier() == SETTINGS) {
                                intent = new Intent(NewsActivity.this, SettingsActivity.class);
                            }

                            if (intent != null) {
                                NewsActivity.this.startActivity(intent);
                            }
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        // set the selection to the item with the identifier 3 (Dairy)
        result.setSelection(NEWS, false);

        //set the back arrow in the toolbar
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(false);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewsOneFragment(), getString(R.string.title_fragment_news_one));
        adapter.addFragment(new NewsTwoFragment(), getString(R.string.title_fragment_news_two));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
            /*if (drawerItem instanceof Nameable) {
                Log.i("material-drawer", "DrawerItem: " + ((Nameable) drawerItem).getName() + " - toggleChecked: " + isChecked);
            } else {
                Log.i("material-drawer", "toggleChecked: " + isChecked);
            }*/
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
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
                getWindow().setStatusBarColor(UIUtils.getThemeColorFromAttrOrRes(NewsActivity.this, R.attr.colorPrimaryDark, R.color.material_drawer_primary_dark));
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

