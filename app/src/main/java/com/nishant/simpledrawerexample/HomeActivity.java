package com.nishant.simpledrawerexample;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    ArrayList<NavigationDrawerItemPOJO> pojoArrayList;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initToolbar();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        pojoArrayList = new ArrayList<>();
        NavigationDrawerItemPOJO nav = new NavigationDrawerItemPOJO();
        nav.setIcon(R.mipmap.ic_launcher);
        nav.setName("Name 1");
        pojoArrayList.add(nav);
        NavigationDrawerItemPOJO nav1 = new NavigationDrawerItemPOJO();
        nav1.setIcon(R.mipmap.ic_launcher);
        nav1.setName("Name 2");
        pojoArrayList.add(nav1);
        mDrawerList.setAdapter(new NavigationDrawerAdapter(this, R.layout.item_navigation_drawer, pojoArrayList));

        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(), "Item clicked: " + position, Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                syncState();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                syncState();
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        initFragment(new HomeFragment());
    }

    //Set the custom toolbar
    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView txtvw = (TextView) findViewById(R.id.toolbar_title);
        txtvw.setText("Nishant");
    }

    public void initFragment(Fragment ft) {
        Fragment fragment = ft;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit();
    }

    /*@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }*/

    public class NavigationDrawerAdapter extends ArrayAdapter<NavigationDrawerItemPOJO> {
        private final Context context;
        private final int layoutResourceId;
        private ArrayList<NavigationDrawerItemPOJO> arrayList;

        public NavigationDrawerAdapter(Context context, int layoutResourceId, ArrayList<NavigationDrawerItemPOJO> arrayList) {
            super(context, layoutResourceId, arrayList);
            this.context = context;
            this.layoutResourceId = layoutResourceId;
            this.arrayList = arrayList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View v = inflater.inflate(layoutResourceId, parent, false);
            ImageView imageView = (ImageView) v.findViewById(R.id.navDrawerImageView);
            TextView textView = (TextView) v.findViewById(R.id.navDrawerTextView);
            NavigationDrawerItemPOJO choice = arrayList.get(position);
            imageView.setImageResource(choice.getIcon());
            textView.setText(choice.getName());
            return v;
        }
    }

    public class NavigationDrawerItemPOJO {
        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int icon;
        public String name;

        public NavigationDrawerItemPOJO() {
        }


    }
}
