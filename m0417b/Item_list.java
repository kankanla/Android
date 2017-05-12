package com.kankanla.m0417b;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.CoderResult;


/**
 * Created by kankanla on 2017/04/28.
 */

public class Item_list extends Fragment {
    private View view;
    private ListView listView;
    private FileSQL fileSQL;
    private String album_list_id;
    private final int requestCode_ADDURL = 99;
    private final int requestCode_SETCOVER = 98;

    public void setAlbum_list_id(String album_list_id) {
        this.album_list_id = album_list_id;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("Item_list--------------onAttach----------------");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Item_list--------------onCreate----------------");
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        System.out.println("Item_list--------------onCreateView----------------");
        view = inflater.inflate(R.layout.album_item_list, null);
        listView = (ListView) view.findViewById(R.id.album_item_listview);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("Item_list--------------onActivityCreated----------------");
        listshow();
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("Item_list--------------onStart----------------");
        setButton();
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("Item_list--------------onResume----------------");
        listshow();
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("Item_list--------------onPause----------------");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("Item_list--------------onStop----------------");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("Item_list--------------onDestroyView----------------");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Item_list--------------onDestroy----------------");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("Item_list--------------onDetach----------------");
    }

    public void listshow() {
        fileSQL = new FileSQL(getActivity());
        Cursor cursor = fileSQL.get_album_id_item(album_list_id);
        Myapa myapa = new Myapa(cursor);
        listView.setAdapter(myapa);
    }

    public void setButton() {
        Button button_play = (Button) view.findViewById(R.id.button_item_play);
        Button button_previous = (Button) view.findViewById(R.id.button_item_previous);
        Button button_next = (Button) view.findViewById(R.id.button_item_next);
        Button button_add = (Button) view.findViewById(R.id.button_add_item_uri);


        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("audio/*");
                startActivityForResult(intent, requestCode_ADDURL);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case requestCode_ADDURL:

                    break;
                case requestCode_SETCOVER:
                    break;
            }
        }
    }


    //
//    Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("audio/*");
//    //        intent.setType(DocumentsContract.Document.MIME_TYPE_DIR);
////        intent.setType("image/*");
//    startActivityForResult(intent, 99);


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
            View view;
            if (convertView == null) {
                view = getActivity().getLayoutInflater().inflate(R.layout.item_list, null);
                cursor.moveToPosition(position);
                TextView textView = (TextView) view.findViewById(R.id.ittess);
                textView.setText(cursor.getString(cursor.getColumnIndex("_display_name")));
                textView.setTextSize(22);
            } else {
                view = convertView;
                cursor.moveToPosition(position);
                TextView textView = (TextView) view.findViewById(R.id.ittess);
                textView.setText(cursor.getString(cursor.getColumnIndex("_display_name")));
                textView.setTextSize(22);
            }
            return view;
        }
    }
}
