package com.cy.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cy.R;
import com.cy.entity.Dog;

public class MyAdapter extends BaseAdapter {

	private Context mContext;
	private List<Dog> mDogs;

	private boolean mIsShowDeleteCheckBox;

	public MyAdapter(Context context, List<Dog> dogs) {
		super();
		this.mContext = context;
		this.mDogs = dogs;
	}

	public void setDogs(List<Dog> dogs) {
		this.mDogs = dogs;
	}

	@Override
	public int getCount() {
		return mDogs.size();
	}

	@Override
	public Object getItem(int position) {
		return mDogs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.item, null);
			viewHolder = new ViewHolder();
			viewHolder.tv = (TextView) convertView.findViewById(R.id.item_tv);
			viewHolder.cb = (CheckBox) convertView.findViewById(R.id.item_cb);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (mIsShowDeleteCheckBox) {
			viewHolder.cb.setVisibility(View.VISIBLE);
			viewHolder.cb.setChecked(mDogs.get(position).isChecked());
		} else {
			viewHolder.cb.setVisibility(View.GONE);
		}
		viewHolder.tv.setText(mDogs.get(position).getName());
		return convertView;
	}

	public class ViewHolder {
		public TextView tv;
		public CheckBox cb;
	}

	public void showDeleteCheckBox() {
		mIsShowDeleteCheckBox = true;
		notifyDataSetChanged();
	}

	public void hideDeleteCheckBox() {
		mIsShowDeleteCheckBox = false;
	}

	public boolean getDeleteCheckBoxState() {
		return mIsShowDeleteCheckBox;
	}

}
