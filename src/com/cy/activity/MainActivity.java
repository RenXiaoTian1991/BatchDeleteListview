package com.cy.activity;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cy.R;
import com.cy.adapter.MyAdapter;
import com.cy.entity.Dog;

public class MainActivity extends Activity implements OnClickListener {

	private ArrayList<Dog> mDogs;
	private ListView mListView;
	private MyAdapter mAdapter;
	private RelativeLayout mDeleteRl;
	private LinearLayout mEmpty_ll;;
	private TextView mSelectAll;
	private TextView mAddDog;
	private boolean mIsDeleteModel = false; // 当前listView是否处于删除模式 ,默认是正常显示模式.
	private boolean mIsSelectAll = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViews();
		initData();
		setListener();
	}

	private void findViews() {
		mEmpty_ll = (LinearLayout) findViewById(R.id.empty_ll);
		mAddDog = (TextView) findViewById(R.id.add_tv);
		mSelectAll = (TextView) findViewById(R.id.tv_select_all);
		mListView = (ListView) findViewById(R.id.list);
		mDeleteRl = (RelativeLayout) findViewById(R.id.main_delete_rl);

	}

	private void initData() {
		if (mAdapter == null) {
			mDogs = getDogs();
			mAdapter = new MyAdapter(this, mDogs);
			mListView.setAdapter(mAdapter);
			mListView.setEmptyView(mEmpty_ll);
		} else {
			mAdapter.setDogs(mDogs);
			mAdapter.notifyDataSetChanged();
		}
	}

	private void setListener() {
		mAddDog.setOnClickListener(this);
		mSelectAll.setOnClickListener(this);
		mDeleteRl.setOnClickListener(this);
		mListView.setOnItemClickListener(new MyOnItemClickListener());
		mListView.setOnItemLongClickListener(new MyOnItemLongClickListener());
	}

	// 初始化数据
	private ArrayList<Dog> getDogs() {
		ArrayList<Dog> dogs = new ArrayList<Dog>();
		for (int i = 0; i < 30; i++) {
			dogs.add(new Dog("狗" + i, false));
		}
		return dogs;
	}

	// 增加一些狗
	private void getMoreDogs() {
		for (int i = 0; i < 15; i++) {
			mDogs.add(new Dog("狗" + i, false));
		}
		initData();
	}

	// 展示编辑模式
	private void showDeleteModel(int position) {
		mSelectAll.setVisibility(View.VISIBLE);
		mDeleteRl.setVisibility(View.VISIBLE);
		Dog dog = (Dog) mDogs.get(position);
		dog.setChecked(true);
		mAdapter.setDogs(mDogs);
		mAdapter.showDeleteCheckBox();
		mIsDeleteModel = true;
	}

	// 隐藏编辑模式，回到正常显示listView
	private void hideDeleteModel() {
		mSelectAll.setVisibility(View.GONE);
		mDeleteRl.setVisibility(View.GONE);
		Iterator<Dog> iterator = mDogs.iterator();
		while (iterator.hasNext()) {
			Dog dog = iterator.next();
			dog.setChecked(false);
		}
		mAdapter.hideDeleteCheckBox();
		initData();
		mIsDeleteModel = false;
	}

	// 添加删除item
	private void addDeleteDog(int position) {
		Dog dog = (Dog) mDogs.get(position);
		if (dog.isChecked()) {
			dog.setChecked(false);
		} else {
			dog.setChecked(true);
		}
		mAdapter.setDogs(mDogs);
		mAdapter.showDeleteCheckBox();
		mIsDeleteModel = true;
	}

	// 全部选中
	private void addDeleteAllDogs() {
		Iterator<Dog> iterator = mDogs.iterator();
		while (iterator.hasNext()) {
			Dog dog = iterator.next();
			dog.setChecked(true);
		}
		mAdapter.setDogs(mDogs);
		mAdapter.showDeleteCheckBox();
		mIsDeleteModel = true;
		mIsSelectAll = true;
	}

	// 清空全部
	private void clearCheckItem() {
		Iterator<Dog> iterator = mDogs.iterator();
		while (iterator.hasNext()) {
			Dog dog = iterator.next();
			dog.setChecked(false);
		}
		mAdapter.setDogs(mDogs);
		mAdapter.showDeleteCheckBox();
		mIsDeleteModel = true;
		mIsSelectAll = false;
	}

	// 删除
	private void deleteCheckItem() {
		Iterator<Dog> iterator = mDogs.iterator();
		while (iterator.hasNext()) {
			Dog dog = iterator.next();
			if (dog.isChecked()) {
				iterator.remove();
			}
		}
	}

	private void changeSelectText() {
		if (mIsSelectAll) {
			mSelectAll.setText("清空全选");
		} else {
			mSelectAll.setText("全部选中");
		}
	}

	private class MyOnItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (mIsDeleteModel) {
				addDeleteDog(position);
				return;
			}
		}
	}

	private class MyOnItemLongClickListener implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			if (!mIsDeleteModel) {
				showDeleteModel(position);
			}
			return true;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (mAdapter == null) {
			return super.onKeyDown(keyCode, event);
		}
		if (keyCode == KeyEvent.KEYCODE_BACK && mIsDeleteModel) {
			hideDeleteModel();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.add_tv:
			getMoreDogs();
			break;
		case R.id.tv_select_all:
			if (mIsSelectAll) {
				clearCheckItem();
			} else {
				addDeleteAllDogs();
			}
			changeSelectText();
			break;
		case R.id.main_delete_rl:
			deleteCheckItem();
			hideDeleteModel();
			break;
		}
	}

}
