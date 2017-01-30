package iii.org.tw.getpet;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import model.CMessage;

public class ActMsgBox extends AppCompatActivity {
    ArrayList<CMessage> myDataset = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_msg_box);

        CMessage msg1 = new CMessage("系統通知","您好! 歡迎使用GetPet系統","Administrator","testUser");
        CMessage msg2 = new CMessage("我是主旨1","您好! 我是testSender1 我是testSender1 我是testSender1","testSender1","testUser");
        CMessage msg3 = new CMessage("我是主旨2","您好! 我是testSender2 我是testSender2 我是testSender2","testSender2","testUser");
        CMessage msg4 = new CMessage("我是主旨3","您好! 我是testSender3 我是testSender3 我是testSender3","testSender3","testUser");
        CMessage msg5 = new CMessage("我是主旨4","您好! 我是testSender4 我是testSender4 我是testSender4","testSender4","testUser");
        CMessage msg6 = new CMessage("我是主旨5","您好! 我是testSender5 我是testSender5 我是testSender5","testSender5","testUser");
        CMessage msg7 = new CMessage("我是主旨6","您好! 我是testSender6 我是testSender6 我是testSender6","testSender6","testUser");
        CMessage msg8 = new CMessage("我是主旨7","您好! 我是testSender7 我是testSender7 我是testSender7","testSender7","testUser");

        myDataset.add(msg1);
        myDataset.add(msg2);
        myDataset.add(msg3);
        myDataset.add(msg4);
        myDataset.add(msg5);
        myDataset.add(msg6);
        myDataset.add(msg7);
        myDataset.add(msg8);

        ActMsgBox.MyAdapter myAdapter = new ActMsgBox.MyAdapter(myDataset);
        RecyclerView mList = (RecyclerView) findViewById(R.id.msgboxlist_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager);
        mList.setAdapter(myAdapter);
    }

    public class MyAdapter extends RecyclerView.Adapter<ActMsgBox.MyAdapter.ViewHolder> {
        private List<CMessage> mData;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvTitle, tvSender;
            public CardView cardView;

            public ViewHolder(View view) {
                super(view);
                cardView = (CardView) view;
                tvTitle = (TextView) view.findViewById(R.id.msglist_title);
                tvSender = (TextView) view.findViewById(R.id.msglist_sender);
            }
        }

        public MyAdapter(List<CMessage> data) {
            mData = data;
        }

        @Override
        public ActMsgBox.MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.msglist, parent, false);
            final ActMsgBox.MyAdapter.ViewHolder vholder = new ActMsgBox.MyAdapter.ViewHolder(view);

            vholder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = LayoutInflater.from(ActMsgBox.this);
                    final View view = inflater.inflate(R.layout.msgdetail_alertdialog, null);
                    TextView msg_subject = (TextView) view.findViewById(R.id.msg_subject);
                    TextView msg_sender = (TextView) view.findViewById(R.id.msg_sender);
                    TextView msg_content = (TextView) view.findViewById(R.id.msg_content);

                    int position = vholder.getAdapterPosition();
                    msg_subject.setText(myDataset.get(position).getSubject());
                    msg_sender.setText(myDataset.get(position).getSender());
                    msg_content.setText(myDataset.get(position).getContent());

                    AlertDialog.Builder dialog = new AlertDialog.Builder(ActMsgBox.this);
                    dialog.setView(view);
                    dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.create().show();

                }
            });
            return vholder;
        }

        @Override
        public void onBindViewHolder(ActMsgBox.MyAdapter.ViewHolder holder, int position) {
            CMessage message = mData.get(position);
            holder.tvTitle.setText(message.getSubject());
            holder.tvSender.setText(message.getSender());
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
}
