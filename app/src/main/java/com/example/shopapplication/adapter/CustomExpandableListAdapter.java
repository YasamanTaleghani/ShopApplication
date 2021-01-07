package com.example.shopapplication.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.shopapplication.R;

import java.util.List;
import java.util.Map;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<String> mListTitle;
    private Map<String,List<String>> mListItem;
    TextView mTextView, mTextViewChild;

    //Constructor
    public CustomExpandableListAdapter(
            Context context, List<String> listTitle, Map<String, List<String>> listItem) {
        mContext = context;
        mListTitle = listTitle;
        mListItem = listItem;
    }

    @Override
    public int getGroupCount() {
        return mListTitle.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mListItem.get(mListTitle.get(i)).size();
    }

    @Override
    public Object getGroup(int position) {
        return mListTitle.get(position);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mListItem.get(mListTitle.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String title = (String) getGroup(i);
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.list_group, null);
        }

        mTextView = view.findViewById(R.id.list_title);
        mTextView.setTypeface(null , Typeface.BOLD);
        mTextView.setText(title);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String title = (String) getChild(i, i1);
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.drawer_list_item, null);
        }

        mTextViewChild = view.findViewById(R.id.expandable_list_item);
        mTextViewChild.setText(title);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
