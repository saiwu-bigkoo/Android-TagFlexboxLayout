package com.bigkoo.tagflexboxlayoutdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.tagflexboxlayout.TagFlexboxAdapter;
import com.bigkoo.tagflexboxlayout.TagFlexboxLayout;
import com.bigkoo.tagflexboxlayout.TagView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TagFlexboxLayout loShow,loTags, loTagsMutiple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initListener();

    }

    private void initView() {
        loShow = findViewById(R.id.loShow);
        loTags = findViewById(R.id.loTags);
        loTagsMutiple = findViewById(R.id.loTagsMutiple);
    }


    private void initData() {
        List<String> stringList = Arrays.asList(getResources().getStringArray(R.array.testSingle));
        loShow.setAdapter(new TagFlexboxAdapter<String>(stringList) {
            @Override
            public View getView(TagView parent,String item, int position) {
                TextView textView = (TextView) LayoutInflater.from(getBaseContext()).inflate(R.layout.itemshow, parent,false);
                textView.setText(getItem(position));
                return textView;
            }
        });
        loTags.setAdapter(new TagFlexboxAdapter<String>(stringList) {
            @Override
            public View getView(TagView parent,String item, int position) {
                TextView textView = (TextView) LayoutInflater.from(getBaseContext()).inflate(R.layout.item_tag, parent,false);
                textView.setText(getItem(position));
                return textView;
            }
        });


        ArrayList<TagBean> tags = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            TagBean tagBean = new TagBean();
            tagBean.setTag("哈哈👌--"+i);
            tagBean.setP(i);
            tags.add(tagBean);
        }

        loTagsMutiple.setAdapter(new TagFlexboxAdapter<TagBean>(tags, 3,4,10,11,12,15,19,22) {
            @Override
            public View getView(TagView parent,TagBean item, int position) {
                TextView textView = (TextView) LayoutInflater.from(getBaseContext()).inflate(R.layout.item_tag, parent,false);
                textView.setText(item.getTag());
                return textView;
            }
        });
    }

    private void initListener() {
        loTags.setOnCheckChangeListener(new TagFlexboxLayout.OnCheckChangeListener() {
            @Override
            public void onCheckChange(boolean isCheck,int position) {
                Toast.makeText(getApplicationContext(),"第"+position+"个",Toast.LENGTH_SHORT).show();
            }
        });
        loTagsMutiple.setOnCheckChangeListener(new TagFlexboxLayout.OnCheckChangeListener() {
            @Override
            public void onCheckChange(boolean isCheck, int position) {
                List<TagBean> list = loTagsMutiple.getCheckedItems();
                StringBuilder s=new StringBuilder();
                for (TagBean tag:list) {
                    s.append(tag.getP()+",");
                }
                Toast.makeText(getApplicationContext(),"选中的有："+s.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
