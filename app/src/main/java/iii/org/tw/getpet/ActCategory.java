package iii.org.tw.getpet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import model.Category;

public class ActCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_category);

        ArrayList<Category> myDataset = new ArrayList<>();
        //Category category1 = new Category("收容所",R.drawable.adoption_support);
        Category category1 = new Category("收容所",R.drawable.category_shelter);
        Category category2 = new Category("送養配對",R.drawable.category_adoptpair);
        myDataset.add(category1);
        myDataset.add(category2);

        MyAdapter myAdapter = new MyAdapter(myDataset);
        RecyclerView mList = (RecyclerView) findViewById(R.id.list_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager);
        mList.setAdapter(myAdapter);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<Category> mData;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public ImageView mImageView;
            public CardView cardView;

            public ViewHolder(View view) {
                super(view);
                cardView = (CardView) view;
                mTextView = (TextView) view.findViewById(R.id.info_text);
                mImageView = (ImageView) view.findViewById(R.id.img);
            }
        }

        public MyAdapter(List<Category> data) {
            mData = data;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.categoryitem, parent, false);
            final ViewHolder vholder = new ViewHolder(view);

            vholder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = vholder.getAdapterPosition();
                    Intent intent;
                    switch (position) {
                        case 0:
                            intent = new Intent(ActCategory.this, ActSearchShelter.class);
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(ActCategory.this, ActAdoptPairList.class);
                            startActivity(intent);
                            break;
                        default:
                            break;
                    }
                }
            });
            return vholder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Category category = mData.get(position);
            holder.mTextView.setText(category.getTitle());
            holder.mImageView.setImageDrawable(getDrawable(category.getImageID()));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }


}
