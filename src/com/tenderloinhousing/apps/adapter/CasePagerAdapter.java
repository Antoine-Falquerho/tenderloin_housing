package com.tenderloinhousing.apps.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tenderloinhousing.apps.fragment.AllCasesFragment;

public class CasePagerAdapter extends FragmentPagerAdapter {
    private static final String[] CONTENT = new String[] { "All Cases", "My Cases"};
    
  	//private static int NUM_ITEMS = 3;
  		
          public CasePagerAdapter(FragmentManager fragmentManager) {
              super(fragmentManager);
          }
          
//          // Returns total number of pages
//          @Override
//          public int getCount() {
//              return NUM_ITEMS;
//          }
//   
//          // Returns the fragment to display for that page
//          @Override
//          public Fragment getItem(int position) {
//              switch (position) {
//              case 0: // Fragment # 0 - This will show FirstFragment
//                  return FirstFragment.newInstance(0, "Page # 1");
//              case 1: // Fragment # 0 - This will show FirstFragment different title
//                  return FirstFragment.newInstance(1, "Page # 2");
//              case 2: // Fragment # 1 - This will show SecondFragment
//                  return SecondFragment.newInstance(2, "Page # 3");
//              default:
//              	return null;
//              }
//          }
          
//          // Returns the page title for the top indicator
//          @Override
//          public CharSequence getPageTitle(int position) {
//          	return "Page " + position;
//          }
//          

          @Override
          public Fragment getItem(int position) {
              return AllCasesFragment.newInstance(CONTENT[position % CONTENT.length]);
          }

          @Override
          public CharSequence getPageTitle(int position) {
              return CONTENT[position % CONTENT.length].toUpperCase();
          }

          @Override
          public int getCount() {
              return CONTENT.length;
          }
}
