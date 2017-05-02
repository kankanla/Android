package com.kankanla.m0417b;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


/**
 * Created by kankanla on 2017/04/28.
 */

public class Item_list extends Fragment {
    private View view;
    private ListView listView;
    private FileSQL fileSQL;

    public Item_list() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        LinearLayout linearLayout = new LinearLayout(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        listView = new ListView(getActivity());
        LinearLayout linearLayout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        listView.setLayoutParams(ll);

        linearLayout.addView(listView);
        view = linearLayout;
        return linearLayout;
    }

    @Override
    public void onStart() {
        super.onStart();
        listshow();
    }

    public void listshow() {
        fileSQL = new FileSQL(getActivity());
        Cursor cursor = fileSQL.info_sound_all_quiry();
        Myapa myapa = new Myapa(cursor);
        listView.setAdapter(myapa);

    }

    private class Myapa extends BaseAdapter {
        private Cursor cursor;

        public Myapa(Cursor cursor) {
            this.cursor = cursor;
        }

        @Override
        public int getCount() {
            System.out.println(cursor.getCount());
            return cursor.getCount();
        }

        @Override
        public Object getItem(int position) {
            return cursor.moveToPosition(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            cursor.moveToPosition(position);
            TextView textView = new TextView(getActivity());
            String s = cursor.getString(cursor.getColumnIndex("_display_name"));
            textView.setText(s);
            textView.setTextSize(22);
            return textView;
        }
    }


}
