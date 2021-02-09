# TagFlexboxLayout
Android-TagFlexboxLayout
==========
之前本想着偷懒拿鸿洋大神的FlowLayout用，发现里面坑很多于是自己撸一个。这个库基于FlexboxLayout，所以很多特性直接用FlexboxLayout的就可以了。

#### 使用gradle 依赖:
```java
   implementation 'com.bigkoo:tagflexboxlayout:1.0.1'
   implementation 'com.google.android:flexbox:1.1.0+'
```

## Demo 图片
![](https://github.com/saiwu-bigkoo/Android-TagFlexboxLayout/tree/master/preview/preview.gif)

##### Config in xml

```xml
        <com.bigkoo.tagflexboxlayout.TagFlexboxLayout
            android:id="@+id/loTags"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:TagFlexboxLayoutMode="Single"
            app:flexWrap="wrap" />
```

##### config in java code

```java
        loTags.setAdapter(new TagFlexboxAdapter<String>(stringList) {
            @Override
            public View getView(TagView parent,String item, int position) {
                TextView textView = (TextView) LayoutInflater.from(getBaseContext()).inflate(R.layout.item_tag, parent,false);
                textView.setText(getItem(position));
                return textView;
            }
        });

```
>## 更新说明
>v1.0.0
 - 支持普通的tag展示、单选、多选流式布局 <br />

